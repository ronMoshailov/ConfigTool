package core.greenwave;

import m0547.Var;
import core.MainStage;
import core.Node;
import core.detectors.Input;
import core.detectors.Output;
import core.detectors.TimedOutput;
import vt.StgEbene;
import vtvar.VtVarStrukt;

/**
 * 
 * @author Ilia Butvinnik
 *
 */
public class GreenWaveHW extends iGreenWave {
    public static final int ID_UL_IN             = 234;
    public static final int ID_SY_IN             = 235;
    public static final int ID_SY2_IN            = 237;
    public static final int ID_PR_START_IN       = 238;

    public static final int ID_LED_SY_OFFSET_OUT = 231;
    public static final int ID_LED_GW_FAIL_OUT   = 232;
    public static final int ID_LED_GW_ACTIVE_OUT = 233;
    public static final int ID_UL_OUT            = 234;
    public static final int ID_SY_OUT            = 235;
    public static final int ID_SY2_OUT           = 236;
    public static final int ID_PR_START_OUT      = 237;
    
    private static final int SY_AT_END_OF_CYCLE     = 0;
    private static final int SY_AT_END_OF_STAGE     = 1;
    private static final int SY_AFTER_MIN_OF_STAGE  = 2;  
    private static final int SY_AT_SPECIFIC_SEC     = 3;  
    private static final int SY_AT_PARAMETER_SEC    = 4;
    
    /**
     * This class is used to mark which type of green wave pulse is used for a Master or Submaster node
     * Currently, these options are supported:
     *   - Program Switch Point - sending the pulse at the program switching second (i.e. at GWPA/GWPB)
     *   - End of Stage - sending the pulse at the end of a specific stage
     *   - Minimum of Stage - sending a long pulse that starts after the completion of minimum extension time of a stage
     *   - Sec in Cycle - sending the pulse at a specific (fixed) second of the program
     *   - Parameter Index - sending the pulse at a certain second of the program defined by a parameter
     * @author Ilia Butvinnik
     * @version 1.0.0
     */
    public static class MasterPulseType {
        public static final MasterPulseType ProgramSwitchPointSec = new MasterPulseType(SY_AT_END_OF_CYCLE   );
        public static final MasterPulseType EndOfStage            = new MasterPulseType(SY_AT_END_OF_STAGE   );
        public static final MasterPulseType MinOfStage            = new MasterPulseType(SY_AFTER_MIN_OF_STAGE);
        public static final MasterPulseType SecInCycle            = new MasterPulseType(SY_AT_SPECIFIC_SEC   );
        public static final MasterPulseType ParameterIndex        = new MasterPulseType(SY_AT_PARAMETER_SEC  );

        private final int type;
        private int data;

        private MasterPulseType(int type) {
            this.type = type;
        }
        
        /**
         * Sets the data required by each type, as follows:
         *   - Program Switch Point - not used
         *   - End of Stage - the number of the required stage
         *   - Min of Stage - the number of the required stage
         *   - Sec in Cycle - the specific fixed second at which the pulse has to be sent
         *   - Parameter Index - the index (starting from 0) of the parameter by which to send the pulse
         * @param data - the data that corresponds to the chosen type 
         * @return this object
         */
        public MasterPulseType setData(int data) {
            this.data = data;
            return this;
        }

        /**
         * Returns the type of pulse used. See also {@link core.greenwave.GreenWaveHW.MasterPulseType}.
         * @return returns the type of pulse
         */
        public int getType() {
            return this.type;
        }
        
        /**
         * Returns the data required by each type. See also {@link #setData(int)}. 
         * @return
         */
        public int getData() {
            return this.data;
        }
    }
    
    /**
     * 
     * @param node
     * @param numberOfProgramLines
     * @param pulseType
     * @param firstGreenWaveProgram
     * @return
     */
    public static GreenWaveHW initializeMaster(Node node, int numberOfProgramLines, MasterPulseType pulseType, int firstGreenWaveProgram) {
        return initializeMaster(node, numberOfProgramLines, pulseType, firstGreenWaveProgram, Var.NONE, new String[] { "mSY" });
    }

    public static GreenWaveHW initializeMaster(Node node, int numberOfProgramLines, MasterPulseType pulseType, int firstGreenWaveProgram, String[] pulsesNames) {
        return initializeMaster(node, numberOfProgramLines, pulseType, firstGreenWaveProgram, Var.NONE, pulsesNames);
    }
    
    /**
     * 
     * @param node
     * @param numberOfProgramLines
     * @param pulseType
     * @param firstGreenWaveProgram
     * @param firstIndependentProgram
     * @return
     */
    public static GreenWaveHW initializeMaster(Node node, int numberOfProgramLines, MasterPulseType pulseType, int firstGreenWaveProgram, int firstIndependentProgram, String[] pulsesNames) {
        return new GreenWaveHW(node)
                .setMaster(numberOfProgramLines, pulseType, firstGreenWaveProgram, firstIndependentProgram, pulsesNames);
    }
    
    /**
     * 
     * @param node
     * @param numberOfProgramLines
     * @param firstGreenWaveProgram
     * @return
     */
    public static GreenWaveHW initializeSlave(Node node, int numberOfProgramLines, int firstGreenWaveProgram) {
        return new GreenWaveHW(node)
                .setSlave(numberOfProgramLines, firstGreenWaveProgram);
    }
    
    /**
     * 
     * @param node
     * @param numberOfProgramLines
     * @param firstGreenWaveProgram
     * @return
     */
    public static GreenWaveHW initializeSlave(Node node, int numberOfProgramLines, int firstGreenWaveProgram, String[] pulsesNames) {
        return new GreenWaveHW(node)
                .setSlave(numberOfProgramLines, firstGreenWaveProgram, pulsesNames);
    }
    
    /**
     * 
     * @param node
     * @param numberOfProgramLines
     * @param firstGreenWaveProgram
     * @param firstIndependentProgram
     * @return
     */
    public static GreenWaveHW initializeSubmaster(Node node, int numberOfProgramLines, int firstGreenWaveProgram, int firstIndependentProgram) {
        return new GreenWaveHW(node)
                .setSubmaster(numberOfProgramLines, firstGreenWaveProgram, firstIndependentProgram);
    }
    
    protected boolean isMaster;
    protected boolean isSlave;
    protected boolean isSubmaster;
    
    protected boolean isBreathing;
    
    public static boolean isProgramFault, isULinFault, isSyFault;
    protected final int maxCyclesForFault = 2;
    protected int gracePeriodCounter;
    protected int cycleLengthOnError = Var.NONE;
    
    protected int programOffsetForMasterError = 0;
    protected int programRequest;
    protected int programLinesCount;
    protected int firstGreenWaveProgram, firstIndependentProgram = Var.NONE;
    
    protected boolean isUseULin, isUseULout;
    public static Input ULin;
    public static Output ULout;
    public VtVarStrukt ULfailParam;
    
    public static int syOffsetCounter;
    public static int syDuration;

    protected MasterPulseType pulseTypeMaster;
    protected String[] pulsesNamesMBreathing = null;
    protected String[] pulsesNamesMRegular   = null;
    protected String[] pulsesNamesSBreathing = null;
    protected String[] pulsesNamesSRegular   = null;
    public static Input[] SYpulses, SY2pulses;
    public static TimedOutput[] mSYpulses, mSY2pulses;
    
    public static Input[] prInputs;
    public static Output[] prOutputs;
    
    protected GreenWaveHW(Node node) {
        super(node);
    }
    
    protected GreenWaveHW setMaster(int programLinesCount, MasterPulseType pulseType, int firstGreenWaveProgram, int firstIndependentProgram) {
        return setMaster(programLinesCount, pulseType, firstGreenWaveProgram, firstIndependentProgram, new String[] { "mSY" });
    }
    
    protected GreenWaveHW setMaster(int programLinesCount, MasterPulseType pulseType, int firstGreenWaveProgram, int firstIndependentProgram, String[] pulsesNames) {
        this.isMaster = true;
        this.programLinesCount = programLinesCount;
        this.firstGreenWaveProgram = firstGreenWaveProgram;
        this.firstIndependentProgram = firstIndependentProgram;
        this.pulseTypeMaster = pulseType;
        this.pulsesNamesMRegular = pulsesNames;
        
        return this;
    }
    
    protected GreenWaveHW setSlave(int programLinesCount, int firstGreenWaveProgram) {
        return setSlave(programLinesCount, firstGreenWaveProgram, new String[] { "SY" });
    }
    
    protected GreenWaveHW setSlave(int programLinesCount, int firstGreenWaveProgram, String[] pulsesNames) {
        this.isSlave = true;
        this.programLinesCount = programLinesCount;
        this.firstGreenWaveProgram = firstGreenWaveProgram;
        this.pulsesNamesSRegular = pulsesNames;
        
        return this;
    }
    
    protected GreenWaveHW setSubmaster(int programLinesCount, int firstGreenWaveProgram, int firstIndependentProgram) {
        return setSubmaster(programLinesCount, firstGreenWaveProgram, firstIndependentProgram, new String[] { "mSY" }, new String[] { "SY" });
    }
    
    protected GreenWaveHW setSubmaster(int programLinesCount, int firstGreenWaveProgram, int firstIndependentProgram, String[] masterPulsesNames, String[] slavePulsesNames) {
        this.isMaster = true;
        this.isSubmaster = true;
        this.isSlave = true;
        this.programLinesCount = programLinesCount;
        this.firstGreenWaveProgram = firstGreenWaveProgram;
        this.firstIndependentProgram = firstIndependentProgram;
        this.pulsesNamesMRegular = masterPulsesNames;
        this.pulsesNamesSRegular = slavePulsesNames;
        
        return this;
    }
    
    /**
     * Marks this type of green wave as breathing, meaning the controller is sending and receiving an SY pulse
     * @return this green wave instance
     */
    public GreenWaveHW setBreathingGW() {
        this.isBreathing = true;
        this.pulseTypeMaster = MasterPulseType.ProgramSwitchPointSec;
        if (isMaster) {
            this.pulsesNamesMBreathing = new String[] { "SY2" };
        }
        
        if (isSlave) {
            this.pulsesNamesSBreathing = new String[] { "mSY2" };
        }
        
        return this;
    }
    
    /**
     * Marks this type of green wave as breathing, meaning the controller is sending and receiving an SY pulse
     * @return this green wave instance
     */
    public GreenWaveHW setBreathingGW(String[] breathingPulsesNames) {
        this.isBreathing = true;
        this.pulseTypeMaster = MasterPulseType.ProgramSwitchPointSec;
        if (isMaster) {
            this.pulsesNamesMBreathing = breathingPulsesNames;
        }
        
        if (isSlave) {
            this.pulsesNamesSBreathing = breathingPulsesNames;
        }
        
        return this;
    }
    
    /**
     * Marks this type green wave as requiring a ULin input, meaning the controller requires an active ULin input
     * to operate in green wave mode
     * @return this green wave instance
     */
    public GreenWaveHW setULin() {
        this.isUseULin = true;
        
        return this;
    }
    
    /**
     * Makes it so that as long as this node is working in green wave mode, it will send a ULout output
     * @return this green wave instance
     */
    public GreenWaveHW setULout() {
        this.isUseULout = true;
        
        return this;
    }
    
    /**
     * In case of a greenwave error, a master node might need to have a separate operating schedule
     * This method sets the delta/offset/difference between the green wave and the independent programs numbers
     * Note that a master should always have its green wave scheduler set as default
     * @param programsOffset - number of first independent program minus the number of the first green wave program
     * @return this object
     */
    public GreenWaveHW setNoGWSchedule(int programsOffset) {
        this.programOffsetForMasterError = programsOffset;
        
        return this;
    }
    
    public boolean isError() {
        return (isUseULin && isULinFault) || isProgramFault || isSyFault;
    }
    
    public boolean isSYError() {
        return isSyFault;
    }

    public boolean isActive() {
        return !isError() && isGreenWaveProgram(node.getAktProg().getNummer()) && isInActiveProgram();
    }
    
    public boolean isInActiveProgram() {
        if (node.getAktStgEbene() == StgEbene.STG_NUM_UHR) {
            return (node.clockProgram == programRequest || (node.itc != null && node.itc.isExternalControl() && node.itc.getRequestedProgram() == node.clockProgram));
        } else if (node.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE) {
            return node.centralProgram == node.getAktProg().getNummer();
        }
        return false;
    }

    public boolean isSY() {
        if (!isMaster && !isSubmaster && !isSlave) {
            return true;
        }
        
        if (isProgramFault || isSyFault || isULinFault) {
            return true;
        }
        
        if ((isMaster || isSubmaster) && !isSlave && !isBreathing) {
            return true;
        }
        
        if (node.getProgAnf().getProg().getNummer() != node.getAktProg().getNummer()) {
            return true;
        }
        
        if (node.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE
                && node.centralProgram > Var.NONE
                && node.centralProgram == node.getAktProg().getNummer()) {
            if (Var.controller.central.isOperateGreenWaveSY()) {
                if (Var.controller.central.isGreenWaveSY()) {
                    return true;
                }
            } else {
                return syOffsetCounter == 0;
            }
        }
        
        if (node.getAktStgEbene() == StgEbene.STG_NUM_UHR
                && node.clockProgram > Var.NONE
                && ((node.clockProgram == programRequest || (isMaster && isBreathing)) || (node.itc != null && node.itc.isExternalControl() && node.itc.getRequestedProgram() == node.clockProgram))
                && syOffsetCounter == 0) {
            return true;
        }
        
        if (node.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE
                && node.centralProgram > Var.NONE
                && node.centralProgram == node.getAktProg().getNummer()
                && syOffsetCounter == 0) {
            return true;
        }
        
        return false;
    }
    
    public void initialisiereDet() {
        if (Led_SyOffset == null) {
            Led_SyOffset = new TimedOutput(node, "GW_SyOffset", ID_LED_SY_OFFSET_OUT, false);
        }
        
        if (Led_GwFail == null) {
            Led_GwFail   = new Output(node, "GW_Fail"    , ID_LED_GW_FAIL_OUT, false);
        }
        
        if (Led_GwActive == null) {
            Led_GwActive = new Output(node, "GW_Active"  , ID_LED_GW_ACTIVE_OUT, false);
        }
        
        if (ULin == null && isUseULin) {
            ULin         = new Input (node, "GW_UL_In"   , ID_UL_IN);
        }
        
        if (ULout == null && isUseULout) {
            ULout        = new Output(node, "GW_UL_Out"  , ID_UL_OUT, false);
        }
        
        if (this.programLinesCount < 4) {
            this.programLinesCount = 4;
        }
        
        if (isMaster || isSubmaster) {
            if (mSYpulses == null) {
                mSYpulses = new TimedOutput[pulsesNamesMRegular.length];
                for (int i = 0; i < pulsesNamesMRegular.length; i++) {
                    mSYpulses[i] = new TimedOutput(node, pulsesNamesMRegular[i], ID_SY_OUT + i, false).setAutomaticTurnOff(2);
                }
            }
            if (isBreathing) {
                if (SY2pulses == null) {
                    SY2pulses = new Input[pulsesNamesMBreathing.length];
                    for (int i = 0; i < pulsesNamesMBreathing.length; i++) {
                        SY2pulses[i] = new Input(node, pulsesNamesMBreathing[i], ID_SY2_IN + i);
                    }
                }
            }
            
            if (prOutputs == null) {
                prOutputs = new Output[this.programLinesCount];
                
                for (int i = 0; i < this.programLinesCount; i++) {
                    prOutputs[i] = new Output(node, "mPR_" + ((int)Math.pow(2, i)), ID_PR_START_OUT + i, false);
                }
            }
        }
        
        if (isSlave || isSubmaster) {
            if (SYpulses == null) {
                SYpulses = new Input[this.pulsesNamesSRegular.length];
                for (int i = 0; i < this.pulsesNamesSRegular.length; i++) {
                    SYpulses[i] = new Input(node, this.pulsesNamesSRegular[i], ID_SY_IN + i);
                }
            }
            if (isBreathing) {
                if (mSY2pulses == null) {
                    mSY2pulses = new TimedOutput[this.pulsesNamesSBreathing.length];
                    for (int i = 0; i < this.pulsesNamesSBreathing.length; i++) {
                        mSY2pulses[i] = new TimedOutput(node,  this.pulsesNamesSBreathing[i], ID_SY2_OUT + i, false).setAutomaticTurnOff(2);
                    }
                }
            }
            
            if (prInputs == null) {
                prInputs = new Input[this.programLinesCount];

                for (int i = 0; i < this.programLinesCount; i++) {
                    prInputs[i] = new Input(node, "PR_" + ((int)Math.pow(2, i)), ID_PR_START_IN + i);
                }
            }
        }
    }

    public void initialisiereParameter() {
        if (isUseULin) {
            ULfailParam = VtVarStrukt.init(node, "GW_UL_Fail", new int[] { 20 }, true, true, true);
        }
    }

    public void operateGW() {
        handleULin();
        checkReceivedPulse();
        operateGWSlave();
        operateGWMaster();
        
        handleULout();
        
        if (node.isFullSecond()) {
            gracePeriodCounter++;
        }
        
        if (!isError()) {
            cycleLengthOnError = Var.NONE;
        }
    }
    
    public void postOperateGW() {
        operateOutgoingPulse();
    }
    
    private boolean oldULinFault;
    private void handleULin() {
        if (isUseULin) {
            if (ULin.IsActivated()) {
                gracePeriodCounter = 0;
            }
            oldULinFault = isULinFault;
            isULinFault = isGracePeriodOver() && (ULin.IsInactive() && ULin.RT() >= ULfailParam.get(0));

            if (isULinFault && !oldULinFault) {
                cycleLengthOnError = Var.controller.parameters.getCycle(node.getNummer());
            }
        } else {
            isULinFault = false;
        }
    }
    
    private void handleULout() {
        if (isUseULout && ULout != null) {
            if ((!isError() && isGreenWaveProgram(node.getAktProg().getNummer()))
                || (!isGracePeriodOver() && node.isRegularProgram())) {
                ULout.turnOn();
            } else {
                ULout.turnOff();
            }
        }
    }
    
    protected boolean isGracePeriodOver() {
        if (node.isRegularProgram() || node.isInMapOnly()) {
            return gracePeriodCounter >= maxCyclesForFault * Math.min(Var.controller.parameters.getCycle(node.getNummer()), cycleLengthOnError > 0 ? cycleLengthOnError : 120);
        } else {
            return false;
        }
    }
    
    protected void operateGWSlave() {
        if (!isSlave && !isSubmaster) {
            return;
        }
        
        checkReceivedProgram();
        
        if (!isError() && isGreenWaveProgram(programRequest)) {
            node.clockProgram = programRequest;
        }
        
        if (iGreenWave.Led_GwActive != null) {
            iGreenWave.Led_GwActive.operate(!isError() && node.isRegularProgram() && (
                    Var.controller.parameters.isSlave(node.getNummer(), node.getAktProg().getNummer()) ||
                    Var.controller.parameters.isSubmaster(node.getNummer(), node.getAktProg().getNummer())
            ));
        }
    }
    
    protected void operateGWMaster() {
        if (!isMaster) {
            return;
        }
        
        if (isError() && isGracePeriodOver() && programOffsetForMasterError != 0) {
            if (node.clockProgram + programOffsetForMasterError > 0
                    && node.clockProgram + programOffsetForMasterError <= Var.controller.getMaxProgramsNumber()) {
                node.clockProgram += programOffsetForMasterError;
            }
        }

        if (iGreenWave.Led_GwFail != null) {
            if (isError()) {
                iGreenWave.Led_GwFail.turnOn();
            } else {
                iGreenWave.Led_GwFail.turnOff();
            }
        }
        
        if (iGreenWave.Led_GwActive != null) {
            if (!isError() && node.isRegularProgram() && Var.controller.parameters.isMaster(node.getNummer(), node.getAktProg().getNummer())) {
                iGreenWave.Led_GwActive.turnOn();
            } else {
                iGreenWave.Led_GwActive.turnOff();
            }
        }
        
        operateProgramsIO();
    }
    
    private void operateProgramsIO() {
        if (isSubmaster || isSlave) {
            
        }
        
        if (isMaster || isSubmaster) {
            int activeProgramNumber = node.getAktProg().getNummer();
            for (int i = 0; i < prOutputs.length; i++) {
                if (node.isRegularProgram() && ((activeProgramNumber - firstGreenWaveProgram + 1) & (0x01 << i)) != 0x00) {
                    prOutputs[i].setAusgang();
                } else {
                    prOutputs[i].resetAusgang();
                }
            }
        }
    }
    
    private void operateOutgoingPulse() {
        if ((!isMaster && !isSubmaster && !(isBreathing && isSlave))
                || !node.isFullSecond()
                || (!node.isRegularProgram() && !node.isInMapOnly())) {
            return;
        }
        
        if (isBreathing) {
            if (pulseTypeMaster.getType() != SY_AFTER_MIN_OF_STAGE) {
                pulseTypeMaster = MasterPulseType.MinOfStage.setData(node.MainPhase[0].getNummer());
            }
        }
        
        if (mSY2pulses != null) {
            for (int i = 0; i < mSY2pulses.length; i++) {
                mSY2pulses[i].setAutomaticTurnOff(2);
            }
        }
        
        switch (pulseTypeMaster.getType()) {
            case SY_AT_END_OF_CYCLE:
                if (node.getProgSek() == node.getAktProg().getGwpB()) {
                    if (mSYpulses != null) {
                        for (int i = 0; i < mSYpulses.length; i++) {
                            mSYpulses[i].turnOn();
                        }
                    }
                    if (mSY2pulses != null) {
                        for (int i = 0; i < mSY2pulses.length; i++) {
                            mSY2pulses[i].turnOn();
                        }
                    }
                }
                break;
            case SY_AT_END_OF_STAGE:
                if (node.getPrevStage().getNummer() == pulseTypeMaster.getData()
                        && node.getCurrStage().getPhasenSek() < 1) {
                    if (mSYpulses != null) {
                        for (int i = 0; i < mSYpulses.length; i++) {
                            mSYpulses[i].turnOn();
                        }
                    }
                    if (mSY2pulses != null) {
                        for (int i = 0; i < mSY2pulses.length; i++) {
                            mSY2pulses[i].turnOn();
                        }
                    }
                }
                break;
            case SY_AFTER_MIN_OF_STAGE:
                if (isBreathing) {
                    if ((node.getCurrStage().getNummer() == pulseTypeMaster.getData() && node.getCurrStage().isMinimumDone() && (node.getCurrStage() instanceof MainStage && !((MainStage)node.getCurrStage()).isStartCycleFunction()))
                            || (!(node.getPrevStage() instanceof MainStage) && node.getPrevStage().getNummer() == pulseTypeMaster.getData() && node.getCurrStage().getPhasenSek() < 1)
                            || ( (node.getPrevStage() instanceof MainStage) && (node.getCurrStage() instanceof MainStage) && node.getPrevStage().getNummer() == pulseTypeMaster.getData() && node.getCurrStage().getPhasenSek() < 1)) {
                        if (mSYpulses != null) {
                            for (int i = 0; i < mSYpulses.length; i++) {
                                mSYpulses[i].turnOn();
                            }
                        }
                        if (mSY2pulses != null) {
                            for (int i = 0; i < mSY2pulses.length; i++) {
                                mSY2pulses[i].turnOn();
                            }
                        }
                    }  
                } else {
                    if ((node.getCurrStage().getNummer() == pulseTypeMaster.getData() && node.getCurrStage().isMinimumDone())
                            || (node.getPrevStage().getNummer() == pulseTypeMaster.getData() && node.getCurrStage().getPhasenSek() < 1)) {
                        if (mSYpulses != null) {
                            for (int i = 0; i < mSYpulses.length; i++) {
                                mSYpulses[i].turnOn();
                            }
                        }
                        if (mSY2pulses != null) {
                            for (int i = 0; i < mSY2pulses.length; i++) {
                                mSY2pulses[i].turnOn();
                            }
                        }
                    }
                }
                break;
            case SY_AT_SPECIFIC_SEC:
                if (node.getProgSek() == pulseTypeMaster.getData()) {
                    if (mSYpulses != null) {
                        for (int i = 0; i < mSYpulses.length; i++) {
                            mSYpulses[i].turnOn();
                        }
                    }
                    if (mSY2pulses != null) {
                        for (int i = 0; i < mSY2pulses.length; i++) {
                            mSY2pulses[i].turnOn();
                        }
                    }
                }
                break;
            case SY_AT_PARAMETER_SEC:
                if (pulseTypeMaster.getData() >= 0
                        && pulseTypeMaster.getData() < Var.controller.parameters.getParametersCount()
                        && node.getProgSek() == Var.controller.parameters.getParameterValue(pulseTypeMaster.getData())) {
                    if (mSYpulses != null) {
                        for (int i = 0; i < mSYpulses.length; i++) {
                            mSYpulses[i].turnOn();
                        }
                    }
                    if (mSY2pulses != null) {
                        for (int i = 0; i < mSY2pulses.length; i++) {
                            mSY2pulses[i].turnOn();
                        }
                    }
                }
                break;
        }
    }
    
    protected void checkReceivedProgram() {
        int tmpProgram = 0;

        for (int i = 0; i < this.programLinesCount; i++) {
            if (prInputs[i].IsActive()) {
                tmpProgram += (int)Math.pow(2, i);
            }
        }
        
        isProgramFault = !Var.controller.central.isOperateGreenWaveSY()
                            && (tmpProgram == 0 || tmpProgram + firstGreenWaveProgram - 1 > Var.controller.getMaxProgramsNumber());
        programRequest = (!isProgramFault) ? (tmpProgram + firstGreenWaveProgram - 1) : Var.NONE;
    }
    
    private boolean oldSyFault;
    protected void checkReceivedPulse() {
        if (!isGracePeriodOver()) {
            isSyFault = false;
        } else if (isSlave) {
            if (SYpulses != null) {
                oldSyFault = isSyFault;
                isSyFault = true;
                for (int i = 0; i < SYpulses.length; i++) {
                    if (SYpulses[i].getStateTime() < maxCyclesForFault * Math.min(Var.controller.parameters.getCycle(node.getNummer()), cycleLengthOnError > 0 ? cycleLengthOnError : 900)) {
                        isSyFault = false;
                    }
                }
                if (isSyFault && !oldSyFault) {
                    cycleLengthOnError = Var.controller.parameters.getCycle(node.getNummer());
                }

            }
        } else if (isBreathing && (isMaster || isSubmaster)) {
            oldSyFault = isSyFault;
            if (SY2pulses != null) {
                isSyFault = true;
                for (int i = 0; i < SY2pulses.length; i++) {
                    if (SY2pulses[i].getStateTime() < maxCyclesForFault * Math.min(Var.controller.parameters.getCycle(node.getNummer()), cycleLengthOnError > 0 ? cycleLengthOnError : 900)) {
                        isSyFault = false;
                    }
                }
                if (isSyFault && !oldSyFault) {
                    cycleLengthOnError = Var.controller.parameters.getCycle(node.getNummer());
                }
            }
        }
        
        if (syOffsetCounter > 0) {
            syOffsetCounter -= node.getZyklDauer();
        } else if (syDuration > 1) {
            syDuration--;
        } else {
            syDuration = 0;
            syOffsetCounter = Var.NONE;
        }
        
        if (isSlave) {
            if (SYpulses.length == 1) {
                if (SYpulses[0].IsActivated()) {
                    syOffsetCounter = Var.controller.parameters.getOffset(node.getNummer()) * Var.ONE_SEC_MS;
                    syDuration = 0;
                }
                
                if (SYpulses[0].IsActive()) {
                    syDuration++;
                }
            } else {
                syOffsetCounter = 0;
                for (int i = 0; i < SYpulses.length; i++) {
                    if (!SYpulses[i].IsActive()) {
                        syOffsetCounter = Var.NONE;
                    }
                }
            }
        } else if (isBreathing && (isMaster || isSubmaster)) {
            if (SY2pulses.length == 1) {
                if (SY2pulses[0].IsActivated()) {
                    syOffsetCounter = Var.controller.parameters.getOffset(node.getNummer()) * Var.ONE_SEC_MS;
                }
                
                if (SY2pulses[0].IsActive()) {
                    syDuration++;
                }
            } else {
                syOffsetCounter = 0;
                for (int i = 0; i < SY2pulses.length; i++) {
                    if (!SY2pulses[i].IsActive()) {
                        syOffsetCounter = Var.NONE;
                    }
                }
            }
        }
        
        GW_Offset = syOffsetCounter == 0;
        if (Led_SyOffset != null) {
            Led_SyOffset.operate(syOffsetCounter == 0);
        }
    }
    
    public boolean isGreenWaveProgram(int programNumber) {
        return programNumber >= firstGreenWaveProgram
            && programNumber <= Math.min(Var.controller.getMaxProgramsNumber(), firstGreenWaveProgram + Math.pow(2, programLinesCount) - 2)
            && Var.controller.parameters.isGWEnable(node.getNummer(), programNumber);
    }

    public void reset() {
        gracePeriodCounter = 0;
    }
}
