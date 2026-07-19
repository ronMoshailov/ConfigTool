package core;

import m0547.Var;
import core.detectors.Output;
import fehlerhandler.FehlerHandler;
import lmp.LmpBlinkOhneUeb;
import lmp.LmpBlinkUge;
import lmp.LmpEinfachUga;
import lmp.LmpTyp;
import sg.Sg;
import sg.SgTyp;
import sg.Zustand;
import sg.typen.Blinker;
import sg.typen.Gelb;
import sg.typen.RoGeGn;
import sg.typen.RoGeGnGnBl;
import sg.typen.RoGn;
import vt.Phase;

public class Move extends Sg {
    public static class MoveType {
        public static final MoveType BRT        		 = new MoveType(0); // Bus Rapid Transit
        public static final MoveType LRT                 = new MoveType(1); // Light Rail Train
        public static final MoveType Preemption          = new MoveType(2); // Preemption light for LRT
        public static final MoveType Traffic             = new MoveType(3); // Traffic without flashing green
        public static final MoveType Traffic_Flashing    = new MoveType(4); // Traffic with flashing green
        public static final MoveType Pedestrian          = new MoveType(5); // Pedestrian
        public static final MoveType Blinker_Conditional = new MoveType(6); // Conditional blinker
        public static final MoveType Blinker             = new MoveType(7); // Blinker

        private int id;

        public MoveType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static int LRTCount = 0;
    public static int BRTCount = 0;

    //types of lamps
    public static LmpTyp lred   = new LmpEinfachUga("Red"  , LmpTyp.FARBE_ROT  );
    public static LmpTyp lamber = new LmpBlinkUge  ("Amber", LmpTyp.FARBE_GELB );
    public static LmpTyp lgreen = new LmpBlinkUge  ("Green", LmpTyp.FARBE_GRUEN);
    //next lamp type is for blink without UGE (foreign voltage fault)
    public static LmpTyp lamber_nuge = new LmpBlinkOhneUeb ("Amber", LmpTyp.FARBE_GELB);

    // signal group types
    //                                                                  rotgelb, gelb, dunkelgelb, gruenblink 
    public static SgTyp car         = RoGeGnGnBl.init(lred, lamber, lgreen,       2,    3,          6,          3);	// car with green blink
    public static SgTyp car_ngb     = RoGeGn    .init(lred, lamber, lgreen,       2,    3,          6            );	// car without green blink
    public static SgTyp ped         = RoGn      .init(lred, lgreen, true);
    public static SgTyp blink       = Blinker   .init(lamber_nuge, false);
    public static SgTyp preemption  = Gelb      .init(lamber);

    private static SgTyp getSgTypByType(MoveType moveType) {
        if (moveType.equals(MoveType.BRT) || moveType.equals(MoveType.LRT) || moveType.equals(MoveType.Traffic)) {
            return car_ngb;
        } else if (moveType.equals(MoveType.Traffic_Flashing)) {
            return car;
        } else if (moveType.equals(MoveType.Pedestrian)) {
            return ped;
        } else if (moveType.equals(MoveType.Blinker) || moveType.equals(MoveType.Blinker_Conditional)) {
            return blink;
        } else if (moveType.equals(MoveType.Preemption)) {
            return preemption;
        } else {
            return car;
        }
    }

    private static int getApproachPriorityByType(MoveType moveType) {
        if (moveType.equals(MoveType.BRT                ) ||
                moveType.equals(MoveType.LRT                ) || 
                moveType.equals(MoveType.Traffic            ) ||
                moveType.equals(MoveType.Traffic_Flashing   ) ||
                moveType.equals(MoveType.Preemption         ) ||
                moveType.equals(MoveType.Blinker_Conditional) ||
                moveType.equals(MoveType.Blinker            )) {
            return Sg.NR;
        } else if (moveType.equals(MoveType.Pedestrian)) {
            return Sg.HR;
        } else {
            return Sg.NR;
        }
    }

    private static boolean isMoveRmGrOutput(MoveType moveType) {
        if (moveType.equals(MoveType.BRT                ) ||
                moveType.equals(MoveType.LRT                ) || 
                moveType.equals(MoveType.Traffic            ) ||
                moveType.equals(MoveType.Traffic_Flashing   ) ||
                moveType.equals(MoveType.Pedestrian)) {
            return true;
        } else {
            return false;
        }
    }
    
    private static int calculateMinGreenTimeBySgType(MoveType type, boolean isMain, int ogMin) {
        int min = ogMin;
        
        if (type.equals(MoveType.Traffic_Flashing)) {
            min -= 3;
        }
        
        if (!isMain && min > 5 && (
                type.equals(MoveType.LRT) ||
                type.equals(MoveType.BRT) ||
                type.equals(MoveType.Traffic) ||
                type.equals(MoveType.Traffic_Flashing)
                )) {
            min = 5;
        }
        
        return min;
    }

    public static final int MAX_GREEN_TIME = 200;
    private static final int MAX_COMPENSATION_CYCLES = 10;
    
    
    public static int moveId = 1;
    private static int lrtId = 0;
    private static int brtId = 0;

    public Node node = null;
    public MoveType moveType;
    public boolean isMainMove = false;
    public Output rm_gr = null;
    public Move preemptionTriangle = null;
    private boolean isDL, isDP, isDM, isDS, isDQ, isDF;
//    public LRTDetector dl, dp, dm, ds, dq, df;
//    public LRTDetectorInternal dlv, dpv, dmv, dsv, dqv, dfv;
    public Output L1, L2, L3;
    public boolean l1, l2, l3;
    protected int lrtIndex;
    protected int brtIndex;

    protected int stateTime;
    protected int greenStateTime;
    protected int currentState;
    protected int previousState;
    protected boolean is200Check;
    protected boolean isActive;
    protected boolean isActivated;
    protected boolean isDeactivated;
    public boolean isUGARed, isUGAAmber, isUGAGreen;
    public boolean isUGERed, isUGEAmber, isUGEGreen;
    

    //    private static int MAX_CUMULATIVE_CYCLES = 10;
    //    private int[] cumulativeGreenTime = new int[MAX_CUMULATIVE_CYCLES];

    /**
     * Constructor for Move
     * @param node			the node to which the move relates
     * @param name			the name of the move
     * @param moveType		the type of the move (traffic, brt, pedestrian, etc.)
     * @param minGreen		minimum green time (in seconds)
     * @param minRed		minimum red time (in seconds)
     * @param isMainMove	marks whether the move is in the main stage or not
     * @param is200Check	marks whether the controller should fail (to flash) if greencheck >= 200[sec]
     */
    public Move(Node node, String name, MoveType moveType, int minGreen, int minRed, boolean isMainMove, boolean is200Check) {
        super(
                node,
                name,
                getSgTypByType(moveType),
                (Var.isProduction || minGreen == 0) ? calculateMinGreenTimeBySgType(moveType, isMainMove, minGreen) : 1,
                minRed,
                getApproachPriorityByType(moveType),
                moveId++
        );

        this.node = node;
        this.moveType = moveType;
        this.isMainMove = isMainMove;
        this.is200Check = Var.controller.isAppJerusalem() && Var.controller.isPreemption() && is200Check;
        
        this.node.registerMove(this);

        if (isTraffic()) {
            initAbschUGA(Move.lred, "UGA 1");
        }

        if (isPedestrian()) {
            initAbschUGA(Move.lred, "Keine");
        }

        if (this.moveType == MoveType.LRT) {
            LRTCount++;
            lrtIndex = lrtId++;
            preemptionTriangle = new Move(node, name + "p", MoveType.Preemption, 0, 0, false, false);
        }
        
        if (this.moveType == MoveType.BRT) {
            BRTCount++;
            brtIndex = brtId++;
        }
        
        Var.controller.moves.add(this);
        this.node.moves.add(this);
        this.node.movesCount++;
    }
    
    public void initializeDet() {
        if (Var.controller.isSynopticMap() && isMoveRmGrOutput(moveType)) {
            rm_gr = new Output(node, "RM_Gr" + getName().substring(1), false); 
        }
    }
    
    public int getState() {
        return currentState;
    }
    
    public int getStateTime() {
        return stateTime;
    }
    
    public int getElapsedTime() {
        return greenStateTime;
    }

    /**
     * Updates the state of the synoptic map's LED according to the moves green state
     */
    public void updateSynopticMapGreenLED() {
        if (!Var.controller.isSynopticMap() || rm_gr == null) {
            return;
        }

        if (node.isDarkProgram()
                || node.isError()
                || (Var.controller.isSynopticMap() && (Var.controller.isUseMapActiveInput && Var.controller.isMapActive != null && !Var.controller.isMapActive.belegt()))) {
            rm_gr.resetAusgang();
        } else {
            if (getZustand() == Zustand.GRUEN) {
                rm_gr.setAusgang();
            } else if (getZustand() == Zustand.GRUENBLINKUEB) {
                if (node.isProgHalbeSek()) {
                    rm_gr.resetAusgang();
                } else {
                    rm_gr.setAusgang();
                }
            } else {
                rm_gr.resetAusgang();
            }
        }
    }

    public void InitializeLRTChannels(boolean isDL, boolean isDP, boolean isDM, boolean isDS, boolean isDQ, boolean isDF) {
        if (this.moveType != MoveType.LRT)
            return;
        
        this.isDL = isDL;
        this.isDP = isDP;
        this.isDM = isDM;
        this.isDS = isDS;
        this.isDQ = isDQ;
        this.isDF = isDF;
        
        // BRT detectors
        if (!Var.controller.isUseSIT()) {
            // TODO:
            //                             Name                         | SG   | Has "Out of Order" channel
//            if (this.isDL) {
//                dl       = new LRTDetector("DL" + getName().substring(1), this ,   false , true );
//            }
//            if (this.isDP) {
//                dp       = new LRTDetector("DP" + getName().substring(1), this ,    true , true );
//            }
//            if (this.isDM){ 
//                dm       = new LRTDetector("DM" + getName().substring(1), this ,    true , true );
//            }
//            if (this.isDS) {
//                ds       = new LRTDetector("DS" + getName().substring(1), this ,   false , true );
//            }
//            if (this.isDQ) {
//                dq       = new LRTDetector("DQ" + getName().substring(1), this ,    true , true );
//            }
//            if (this.isDF) {
//                df       = new LRTDetector("DF" + getName().substring(1), this ,   false , true );
//            }
    
            L1       = new Output(node, "L1_" + getName().substring(1), false);
            L2       = new Output(node, "L2_" + getName().substring(1), false);
            L3       = new Output(node, "L3_" + getName().substring(1), false);
        } else {
            // TODO:
//            if (this.isDL) {
//                dlv  = new LRTDetectorInternal("DL" + getName().substring(1), this ,   false , true , PreemptionDetectorType.TYPE_DL  );
//            }
//            if (this.isDP) {
//                dpv  = new LRTDetectorInternal("DP" + getName().substring(1), this ,    true , true , PreemptionDetectorType.TYPE_DP  );
//            }
//            if (this.isDM){ 
//                dmv  = new LRTDetectorInternal("DM" + getName().substring(1), this ,    true , true , PreemptionDetectorType.TYPE_DM  );
//            }
//            if (this.isDS) {
//                dsv  = new LRTDetectorInternal("DS" + getName().substring(1), this ,   false , true , PreemptionDetectorType.TYPE_DS  );
//            }
//            if (this.isDQ) {
//                dqv  = new LRTDetectorInternal("DQ" + getName().substring(1), this ,    true , true , PreemptionDetectorType.TYPE_DQ  );
//            }
//            if (this.isDF) {
//                dfv  = new LRTDetectorInternal("DF" + getName().substring(1), this ,   false , true , PreemptionDetectorType.TYPE_DF  );
//            }
        }
    }
    
    public void InitializeVirtualLRTChannels() {
        if (this.moveType != MoveType.LRT || !Var.controller.isUseSIT())
            return;
        // TODO:
//        if (!this.isDL) {
//            dlv  = new LRTDetectorInternal("DL" + getName().substring(1), this ,   false , false , PreemptionDetectorType.TYPE_DL  );
//        }
//        if (!this.isDP) {
//            dpv  = new LRTDetectorInternal("DP" + getName().substring(1), this ,    true , false , PreemptionDetectorType.TYPE_DP  );
//        }
//        if (!this.isDM){ 
//            dmv  = new LRTDetectorInternal("DM" + getName().substring(1), this ,    true , false , PreemptionDetectorType.TYPE_DM  );
//        }
//        if (!this.isDS) {
//            dsv  = new LRTDetectorInternal("DS" + getName().substring(1), this ,   false , false , PreemptionDetectorType.TYPE_DS  );
//        }
//        if (!this.isDQ) {
//            dqv  = new LRTDetectorInternal("DQ" + getName().substring(1), this ,    true , false , PreemptionDetectorType.TYPE_DQ  );
//        }
//        if (!this.isDF) {
//            dfv  = new LRTDetectorInternal("DF" + getName().substring(1), this ,   false , false , PreemptionDetectorType.TYPE_DF  );
//        }
    }
    
    private void updateLRTLampsStatus() {
        if (this.moveType == MoveType.LRT) {
            if (currentState == Zustand.ROT) {
                l1 = true;
                l2 = false;
                l3 = false;
            } else if (currentState == Zustand.ROTGELB) {
                l1 = true;
                l2 = true;
                l3 = false;
            } else if (currentState == Zustand.GRUEN || currentState == Zustand.GRUENBLINKUEB) {
                l1 = false;
                l2 = false;
                l3 = true;
            } else if (currentState == Zustand.GELB || currentState == Zustand.GELBBLINKEN) {
                l1 = false;
                l2 = true;
                l3 = false;
            } else {
                l1 = false;
                l2 = false;
                l3 = false;
            }

            if (l1) {
                API.setElementActive   (L1);
            } else {
                API.setElementNotActive(L1);
            }
            
            if (l2) {
                API.setElementActive   (L2);
            } else {
                API.setElementNotActive(L2);
            }
            
            if (l3) {
                API.setElementActive   (L3);
            } else {
                API.setElementNotActive(L3);
            }
        }
    }

    /**
     * Updates the state counters of the move
     */
    public void updateStateCounters() {
        currentState = getZustand();
        
        if (this.moveType == MoveType.LRT) {
            updateLRTLampsStatus();
        }
        
        if (currentState == Zustand.GRUEN
                || currentState == Zustand.GRUENBLINKUEB
                || currentState == Zustand.GELBBLINKEN
                || currentState == Zustand.EIN) {
            currentState = Zustand.GRUEN;
        } else if (currentState == Zustand.AUS
                || currentState == Zustand.DUNKEL
                || currentState == Zustand.GELB
                || currentState == Zustand.ROT
                || currentState == Zustand.ROTGELB) {
            currentState = Zustand.ROT;
        }
        
        if (node.isFullSecond()) {
            if (currentState == previousState) {
                if (stateTime < Var.MAX_COUNTER_VALUE) {
                    stateTime++;
                }
            } else {
                stateTime = 0;
            }
            
            isDeactivated = previousState != currentState && currentState == Zustand.ROT;
            isActivated = previousState != currentState && currentState == Zustand.GRUEN;
            previousState = currentState;
        }

        if (currentState == Zustand.GRUEN) {
            greenStateTime = stateTime;
        }

        if (is200Check
                && getZustand() == Zustand.GRUEN
                && stateTime >= MAX_GREEN_TIME) {
            FehlerHandler.writeMsg(FehlerHandler.errFatal, true, node.getNummer(), "Signalgroup " + getName() + ": green state too long");
        }
        
        if (moveType == MoveType.LRT && Var.controller.isUseSIT()) {
            Var.controller.sit.getLRTState(this);
        }

    }

    /**
     * Gets the move's green time (in seconds)
     * @return	returns the move's green time (in seconds) or 0 if not green
     */
    public int GT() {
        return (currentState == Zustand.GRUEN) ? stateTime : Var.NONE; 
    }

    /**
     * Gets the move's green time (in milliseconds)
     * @return  returns the move's green time (in milliseconds) or 0 if not green
     */
    public int GTMS() {
        return GT() * Var.ONE_SEC_MS; 
    }

    /**
     * Gets the move's red time (in milliseconds)
     * @return	returns the move's red time (in milliseconds) or 0 if not red
     */
    public int RT() {
        if (moveType.equals(MoveType.Pedestrian)) {
            if (getZustand() == Zustand.ROT) {
                return stateTime;
            }
        } else if (moveType.equals(MoveType.Traffic_Flashing)) {
            if (getZustand() == Zustand.GRUENBLINKUEB) {
                return getZustSek();
            } else  if (getZustand() == Zustand.GELB) {
                return 3 + stateTime;
            } else if (getZustand() == Zustand.ROT) {
                return 3 + stateTime;
            }
        } else if (moveType.equals(MoveType.Traffic)
                || moveType.equals(MoveType.LRT)
                || moveType.equals(MoveType.BRT)) {
            if (getZustand() == Zustand.GELB) {
                return stateTime;
            } else if (getZustand() == Zustand.ROT) {
                return stateTime;
            }
        }
        return Var.NONE;
    }

    /**
     * Gets the move's red time (in milliseconds)
     * @return  returns the move's red time (in milliseconds) or 0 if not red
     */
    public int RTMS() {
        return RT() * Var.ONE_SEC_MS;
    }

    public boolean isLRT() {
        return (moveType.equals(MoveType.LRT             ));
    }

    public boolean isBRT() {
        return (moveType.equals(MoveType.BRT             ));
    }

    public boolean isTraffic() {
        return (moveType.equals(MoveType.BRT             ) ||
                moveType.equals(MoveType.LRT             ) ||
                moveType.equals(MoveType.Traffic         ) ||
                moveType.equals(MoveType.Traffic_Flashing));
    }

    public boolean isPedestrian() {
        return (moveType.equals(MoveType.Pedestrian));
    }

    public boolean isBlinker() {
        return (moveType.equals(MoveType.Blinker            ) ||
                moveType.equals(MoveType.Blinker_Conditional));
    }

    public boolean isPreemptionTriangle() {
        return (moveType == MoveType.Preemption);
    }

    public boolean isMainTraffic() {
        return isTraffic() && isMainMove;
    }

    public boolean isSecondaryTraffic() {
        return isTraffic() && !isMainMove;
    }

    public boolean isMainPedestrian() {
        return isPedestrian() && isMainMove;
    }

    public boolean isSecondaryPedestrian() {
        return isPedestrian() && !isMainMove;
    }

    public boolean isRegularBlinker() {
        return moveType == MoveType.Blinker;
    }

    public boolean isMainConditionalBlinker() {
        return (moveType == MoveType.Blinker_Conditional) && isMainMove;
    }

    public boolean isSecondaryConditionalBlinker() {
        return (moveType == MoveType.Blinker_Conditional) && !isMainMove;
    }

    /**
     * Changes the move's state to its inactive state (ROT if move/pedestrian or AUS if blinker)
     * according to minimum green time check
     */
    public void turnOff() {
        if (node.isMapOnlyProgram()) {
            return;
        }

        if (node.currStage == null) {
            return;
        }

        if (moveType.equals(MoveType.Blinker) ||
                moveType.equals(MoveType.Blinker_Conditional) ||
                moveType.equals(MoveType.Preemption)) {

        } else { 
            if (greencheck(getMindFreiSek())) {
                setSg(Zustand.ROT, node.currStage.getPhasenSek());
            }
        }
    }

    /**
     * Changes the move's state to its inactive state (ROT if move/pedestrian or AUS if blinker)
     * according to given parameter (if move has flashing green, the duration of the flashing green will be REDUCED from the parameter)
     * @param stageSec	the second of the interstage on which the move should be turned off (if has green flash, 3 seconds will be reduced from the parameter)
     */
    public void turnOff(int stageSec) {
        if (node.isMapOnlyProgram()) {
            return;
        }

        if (moveType.equals(MoveType.Blinker) ||
                moveType.equals(MoveType.Blinker_Conditional) ||
                moveType.equals(MoveType.Preemption)) {
            setSg(Zustand.AUS, stageSec);
        } else if (moveType.equals(MoveType.BRT       ) ||
                moveType.equals(MoveType.LRT       ) ||
                moveType.equals(MoveType.Pedestrian) ||
                moveType.equals(MoveType.Traffic   )) {
            setSg(Zustand.ROT, stageSec);
        } else if (moveType.equals(MoveType.Traffic_Flashing)) {
            if (stageSec < 3) {
                stageSec = 3;
            }
            stageSec -= 3;
            setSg(Zustand.ROT, stageSec);
        }
    }

    /**
     * Changes the move's state to its active state (GRUEN if move/pedestrian or GELBBLINKEN if blinker)
     * according to inter-green time check
     */
    public void turnOn() {
        if (node.isMapOnlyProgram()) {
            return;
        }

        if (node.currStage == null) {
            return;
        }

        if (moveType.equals(MoveType.Blinker) ||
                moveType.equals(MoveType.Blinker_Conditional) ||
                moveType.equals(MoveType.Preemption)) {

        } else {
            if (isSchaltenErlaubt(Zustand.GRUEN)) {
                setSg(Zustand.GRUEN, node.currStage.getPhasenSek());
            }
        }
    }

    /**
     * Changes the move's state to its active state (GRUEN if move/pedestrian or GELBBLINKEN if blinker)
     * according to given parameter (if move has red-yellow, the duration of the red-yellow will be REDUCED from the parameter)
     * @param stageSec	the second of the interstage on which the move should be turned on (if has red-yellow, 2 seconds will be reduced from the parameter)
     */
    public void turnOn(int stageSec) {
        if (node.isMapOnlyProgram()) {
            return;
        }

        if (moveType.equals(MoveType.Blinker) ||
                moveType.equals(MoveType.Blinker_Conditional)) {
            setSg(Zustand.GELBBLINKEN, stageSec);
        } else if (moveType.equals(MoveType.Blinker_Conditional)) {
            setSg(Zustand.GELB, stageSec);
        } else if (moveType.equals(MoveType.Pedestrian)) {
            setSg(Zustand.GRUEN, stageSec);
        } else if (moveType.equals(MoveType.Preemption)) {
            setSg(Zustand.EIN, stageSec);
        } else {
            if (stageSec < 2) {
                stageSec = 2;
            }
            stageSec -= 2;
            setSg(Zustand.GRUEN, stageSec);
        }
    }
    
    /**
     * Changes the move's state to its active and inactive states according to the given parameters
     * @param onTimeS  the second of the interstage on which the move should be turned on
     * @param offTimeS  the second of the interstage on which the move should be turned off
     */
    public void turnOnAndOff(int onTimeSec, int offTimeSec) {
        turnOn(onTimeSec);
        turnOff(offTimeSec);
    }

    /**
     * Compares the actual green time of a signal group with a parameter
     * @param time: value of the parameter in milliseconds
     * @return true, when greentime of the signal group is grater or equals the value of the parameter 
     */
    public boolean greencheck(int time) {
        return greencheckMS(time * Var.ONE_SEC_MS);
    }

    public boolean greencheckMS(int time) {
        if ((getZustand() == Zustand.GRUEN) && (getZustZeit() >= time)) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the SG was not GREEN at least as much time as passed in "time"
     * @param time - time (in msec) that is required to be checked
     */
    public boolean notgreencheck(int time) {
        return notgreencheckMS(time * Var.ONE_SEC_MS);
    }

    public boolean notgreencheckMS(int time) {
        return RTMS() >= time;
    }

    protected int[] greenTimes = new int[MAX_COMPENSATION_CYCLES];

    public void resetCumulativeCycles() {
        for (int i = 0; i < greenTimes.length; i++) {
            greenTimes[i] = 0;
        }
    }

    public void startNewCycle() {
        for (int i = greenTimes.length - 1; i > 0; i--) {
            greenTimes[i] = greenTimes[i - 1];
        }
        greenTimes[0] = 0;
    }

    public void updateCumulativeGreen() {
        if ((getZustand() == Zustand.GRUEN && getZustZeit() > 0) || getZustand() == Zustand.GRUENBLINKUEB)
        {
            greenTimes[0] += node.getZyklDauer();
        }

        if (getTyp() == car && getZustand() == Zustand.GELB && getZustZeit() < node.getZyklDauer())
        {
            greenTimes[0] += node.getZyklDauer();
        }
    }

    public int getCumulativeCycleGT(int cycles) {
        int sum = 0;
        for (int i = 0; i < Math.min(cycles, greenTimes.length); i++) {
            sum += greenTimes[i];
        }
        return sum;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isDeactivated() {
        return isDeactivated;
    }
    
    public boolean isInactive() {
        return !isActive;
    }

    // TODO:
//    public LRTInterface getLRTDetector(PreemptionDetectorType detType) {
//        if (moveType != MoveType.LRT) {
//            return null;
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_DL.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dlv;
//            } else {
//                return dl;
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_DP.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dpv;
//            } else {
//                return dp;
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_DM.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dmv;
//            } else {
//                return dm;
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_DS.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dsv;
//            } else {
//                return ds;
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_DQ.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dqv;
//            } else {
//                return dq;
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_DF.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dfv;
//            } else {
//                return df;
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_OOODP.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dpv.getOutOfOrderInput();
//            } else {
//                return dp.getOutOfOrderInput();
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_OOODM.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dmv.getOutOfOrderInput();
//            } else {
//                return dm.getOutOfOrderInput();
//            }
//        }
//        
//        if (detType.getDetectorType() == PreemptionDetectorType.TYPE_OOODQ.getDetectorType()) {
//            if (Var.controller.isUseSIT()) {
//                return dqv.getOutOfOrderInput();
//            } else {
//                return dq.getOutOfOrderInput();
//            }
//        }
//        
//        return null;
//    }
    
    public int getLRTIndex() {
        if (moveType.equals(MoveType.LRT))
            return lrtIndex;
        return -1;
    }
    
    public int getBRTIndex() {
        if (moveType.equals(MoveType.BRT))
            return brtIndex;
        return -1;
    }
}
