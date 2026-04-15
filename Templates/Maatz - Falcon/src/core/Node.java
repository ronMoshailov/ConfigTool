package core;

import itc.ITC;
import java.util.ArrayList;
import core.detectors.LRTInterface;
import core.detectors.DDetector;
import core.detectors.DEDetector;
import core.detectors.EDetector;
import core.detectors.Input;
import core.detectors.InputInternal;
import core.detectors.Output;
import core.detectors.QDetector;
import core.detectors.TPDetector;
import core.greenwave.GreenWaveClock;
import core.greenwave.GreenWaveHW;
import core.greenwave.iGreenWave;
import core.preemption.Preemption;
import core.programs.DarkProg;
import core.programs.AusProgramm;
import core.programs.BlinkProgramm;
import core.programs.EinProgramm;
import core.programs.HandProgramm;
import core.programs.PostMain;
import core.programs.Umschalt;
import core.programs.OperationalMain;
import core.programs.VAProg;
import det.Detektor;
import m0547.Var;
import hw.tk.HwTeilknoten;
import sg.Zustand;
import sg.ZwzMatrix;
import vt.*;

abstract public class Node extends TeilKnoten {
	public static class SpecialProgram {
		public static final SpecialProgram MaintenanceFirst = new SpecialProgram(51); // number of first maintenance (map only) program
		public static final SpecialProgram Flash            = new SpecialProgram(90); // number of blink program                       
		public static final SpecialProgram Ein              = new SpecialProgram(91); // number of ein program                         
		public static final SpecialProgram Aus              = new SpecialProgram(92); // number of aus program                         
		public static final SpecialProgram Police           = new SpecialProgram(95); // number of police program                      
		public static final SpecialProgram Dark             = new SpecialProgram(99); // number of dark program                          
		public static final SpecialProgram Catastrophe      = new SpecialProgram(16); // number of catastrophe program                        

		private final int programNumber;

		SpecialProgram(int programNumber) {
			this.programNumber = programNumber;
		}

		public int getProgramNumber() {
			return this.programNumber;
		}
	}

	public InitTk nodeInitializer; // the InitTk of this node

	protected boolean isCheckExitConditionsRequired; // whether main stage's exit conditions should be checked

	public int movesCount = 0;
	public ArrayList<Move> moves                      = new ArrayList<Move>(); // all moves of this node
	public ArrayList<Move> mainTrafficSgs             = new ArrayList<Move>(); // main traffic moves of the node
	public ArrayList<Move> notMainTrafficSgs          = new ArrayList<Move>(); // secondary traffic moves of the node
	public ArrayList<Move> mainPedestrianSgs          = new ArrayList<Move>(); // main pedestrian moves of the node
	public ArrayList<Move> notMainPedestrianSgs       = new ArrayList<Move>(); // secondary pedestrian moves of the node
	public ArrayList<Move> regularBlinkSgs            = new ArrayList<Move>(); // regular blinkers (not conditional)
	public ArrayList<Move> mainConditionedBlinkSgs    = new ArrayList<Move>(); // main conditional blinkers (that are active during main stage)
	public ArrayList<Move> notMainConditionedBlinkSgs = new ArrayList<Move>(); // secondary conditional blinkers (that are not active during main stage)
	public ArrayList<Move> preemptionLampSgs          = new ArrayList<Move>(); // preemption lamps for LRT moves
	
	public ArrayList<TPDetector>    pushButtons       = new ArrayList<TPDetector   >(); // pedestrian push buttons
	public ArrayList<Input>         inputs            = new ArrayList<Input        >(); // regular inputs (pulses and etc.)
	public ArrayList<DDetector>     demands           = new ArrayList<DDetector    >(); // demand detectors
	public ArrayList<EDetector>     extensions        = new ArrayList<EDetector    >(); // extension detectors
	public ArrayList<DEDetector>    demandExtensions  = new ArrayList<DEDetector   >(); // combined demand and extension detectors
	public ArrayList<QDetector>     queues            = new ArrayList<QDetector    >(); // queue detectors
	public ArrayList<LRTInterface>  brts              = new ArrayList<LRTInterface >(); // BRT (Bus Rapid Transit) detectors
	public ArrayList<InputInternal> internals         = new ArrayList<InputInternal>();
    public ArrayList<Output>        outputs           = new ArrayList<Output       >(); // outputs
    public ArrayList<Output>        outputsRemote     = new ArrayList<Output       >(); // outputs
    public ArrayList<Input>         allInputs         = new ArrayList<Input        >(); // outputs
    public ArrayList<Output>        allOutputs        = new ArrayList<Output       >(); // outputs
	
	private int moveIterator, detIterator;    // auxiliary iterator

	public Stage prevStage;
	public Stage currStage;
	public int activeStageNumber;
	
	public int prevStructure;
	public int currStructure;
	
    public boolean isCycleFrozen = false;
    public boolean isStageFrozen = false;
	
	public int lastActiveProgram; 				// number of the program that was active in the last scan cycle

	public int firstCycleExt;					// longest minimum green time for phase - EQA's length = firstCycleExt_tk1 (in MS)
	public boolean isWaitingForSy;          	// true while controller is waiting for syncronization pulse

	public int timeToStartMapOnly = -1;			// time (of program cycle) when to start transition to or from Map Only (Maintenance) mode
	public int centralProgram = Var.NONE;
	public int clockProgram   = Var.NONE;
	public boolean isClockReset;

    public ITC itc;
    
	public int longestIntergreenTime = 0;
	
	public Scheduler scheduler;
	protected ArrayList<iGreenWave> greenwaves = new ArrayList<iGreenWave>();
	public Preemption preemption;
	
	public boolean isInFire = false, isSwitchingToFire = false, isDetectorsError = false;
	
	// additional programs (fixed cycle and logic programs)
	public VAProg p01, p02, p03, p04, p05, p06, p07, p08, p09, p10;
	public VAProg p11, p12, p13, p14, p15, p16, p17, p18, p19, p20;
	public VAProg p21, p22, p23, p24, p25, p26, p27, p28, p29, p30;
	public VAProg p31, p32;

	public VAProg p01MO, p02MO, p03MO, p04MO, p05MO, p06MO, p07MO, p08MO, p09MO, p10MO;
	public VAProg p11MO, p12MO, p13MO, p14MO, p15MO, p16MO, p17MO, p18MO, p19MO, p20MO;
	public VAProg p21MO, p22MO, p23MO, p24MO, p25MO, p26MO, p27MO, p28MO, p29MO, p30MO;
	public VAProg p31MO, p32MO;

//	public LRTDetector SITOK;
	
	public Output OOOTLC, Catastrophe;

	public Node(String name, HwTeilknoten hwTk, boolean dx, int firstCycleExt) {
		super(name, hwTk);
		isUseDx = dx;
		this.firstCycleExt = firstCycleExt; // convert to ms if needed
	}

	public void registerMove(Move move) {
		if (move.isMainTraffic()) {
			mainTrafficSgs.add(move);
		} else if (move.isSecondaryTraffic()) {
			notMainTrafficSgs.add(move);
		} else if (move.isMainPedestrian()) {
			mainPedestrianSgs.add(move);
		} else if (move.isSecondaryPedestrian()) {
			notMainPedestrianSgs.add(move);
		} else if (move.isRegularBlinker()) {
			regularBlinkSgs.add(move);
		} else if (move.isMainConditionalBlinker()) {
			mainConditionedBlinkSgs.add(move);
		} else if (move.isSecondaryConditionalBlinker()) {
			notMainConditionedBlinkSgs.add(move);
		} else if (move.isPreemptionTriangle()) {
			preemptionLampSgs.add(move);
		}
	}
	
	public void initializeDet() {
        for (int i = 0; i < mainTrafficSgs.size(); i++) {
            mainTrafficSgs.get(i).initializeDet();
        }
        
        for (int i = 0; i < notMainTrafficSgs.size(); i++) {
            notMainTrafficSgs.get(i).initializeDet();
        }
        
        for (int i = 0; i < mainPedestrianSgs.size(); i++) {
            mainPedestrianSgs.get(i).initializeDet();
        }
        
        for (int i = 0; i < notMainPedestrianSgs.size(); i++) {
            notMainPedestrianSgs.get(i).initializeDet();
        }
        
        for (detIterator = 0; detIterator < pushButtons.size(); detIterator++) {
            pushButtons.get(detIterator).initializeFeedback();
        }
        
        for (detIterator = 0; detIterator < demands.size(); detIterator++) {
            demands.get(detIterator).initializeMapInput();
        }

        for (detIterator = 0; detIterator < extensions.size(); detIterator++) {
            extensions.get(detIterator).initializeMapInput();
        }

        for (detIterator = 0; detIterator < demandExtensions.size(); detIterator++) {
            demandExtensions.get(detIterator).initializeMapInput();
        }

        for (detIterator = 0; detIterator < queues.size(); detIterator++) {
            queues.get(detIterator).initializeMapInput();
        }

        for (detIterator = 0; detIterator < pushButtons.size(); detIterator++) {
            pushButtons.get(detIterator).initializeMapInput();
        }
	}
	
	public boolean isRegularProgramFixedCycle() {
		return (isRegularProgram()
		        && (getAktProg().getUmlaufZeit() != Var.PROGRAM_NONE_FIXED_CYCLE)
		        && (getAktProg().getUmlaufZeit() != 0));
	}
	
	public boolean isMapOnlyProgramFixedCycle() {
	    return (isMapOnlyProgram()
                && (getAktProg().getUmlaufZeit() != Var.PROGRAM_NONE_FIXED_CYCLE)
                && (getAktProg().getUmlaufZeit() != 0));
	}
	
	public boolean isProgramFixedCycle() {
	    return isRegularProgramFixedCycle()
	            || isMapOnlyProgramFixedCycle();
	}

	public void firstCycleActions() {
	    // TODO: update when preemption is implemented
	    
//	    if (preemption != null) {
//	        preemption.firstCycleActions();
//	    }
	}

	public void updateMovesStates() {
	    if (!isFullSecond()) {
	        return;
	    }
	    
	    for (int i = 0; i < movesCount; i++) {
	        moves.get(i).updateStateCounters();
	    }
	}

	public int[] dpt2FirstChannels = new int[2];
	public int dpt2SecondChannelIndex = 0;
	public int[] dpt2SecondChannels = {121, 122};

	public boolean startmark;				// first cycle of main stage in the beginning of the program cycle after return from ein/dark/blink/police
	public boolean firstSec; 				// if first second of program (1 to Var.maxProgNumber) (after startup or return from dark/blink/police)
	public int CycleCount;					// number of cycles stayed in same program
	public int CycleSecond;		 			// program clock (in ms)
	protected boolean isInInterstage;		// whether the node has an active interstage
	protected int activeStageOrInterstageNumber;
	public int lenPhue;			       	    // length of interstage till stage is built (only moves, not defined duration of interstage)
	public int sk_number; 					// branch number
	public boolean isUseDx; 				// if main stage of this node has Dx
	public int gapSet, modeSet;				// indexes for gap and mode sets for Haifa intersections
	public boolean inPolice;				// flag for Haifa intersections to ignore parameters if in police program
	public boolean dem_dx;					// whether there is some demand or no demands whatsoever (if use_dx is true)
	public boolean isDxDone;				// if current PROGRAM cycle dx demand status check was finished
	public boolean sync_tk = true;			// for controllers with more than 1 node: true when controller is in end of PROGRAM cycle
	public int detQuantity;					// number of detectors and push-buttons (demand, extension, queue, push-button)

	public boolean isWaitingForFO; // true while controller is waiting for an FO input

	public int MAX_STAGES = 0; // maximum number of stages
	public int[] _activeStages;
	public int _active_stage_iterator = 0;
	public int _lastStageDuration = 0;

	// TODO:
//	public PreemptionJ preempJ;
//	public PreemptionH preempH;
	
	public int getLenPhue() {
	    return lenPhue * Var.ONE_SEC;
	}
	
	public int getLenPhueMS() {
	    return lenPhue * Var.ONE_SEC_MS;
	}

	public void initializeStagesArray(int maxStages) {
		MAX_STAGES = maxStages;
		_activeStages = new int[MAX_STAGES];
	}

	public boolean wasStageActive(Stage stage) {
		for (int i = 0; i < MAX_STAGES; i++) {
			if (stage.getNummer() == _activeStages[i]) {
				return true;
			}
		}
		return false;
	}

	public void updateStageDuration(Stage ph) {
		_lastStageDuration = ph.getPhasenSek() - ph.getPhLen();
	}

	public int getLastStageDuration() {
		return _lastStageDuration;
	}

	public void updateActiveStages() {
		if (!Var.controller.isPreemption()) {
			return;
		}
		// TODO:
//		int phaseNum = Phase.getAktivePhase(this).getNummer();
//		if (_currStage == phaseNum || _active_stage_iterator >= MAX_STAGES) { // current stage was already updated
//			return;
//		}
//		_currStage = phaseNum;
//		_activeStages[_active_stage_iterator] = _currStage;
//		_active_stage_iterator++;
	}

	public void resetActiveStages() {
		if (!Var.controller.isPreemption() || _active_stage_iterator >= MAX_STAGES) {
			return;
		}
		// TODO:
//		while (_active_stage_iterator > 0) {
//			_active_stage_iterator--;
//			_activeStages[_active_stage_iterator] = PreemptionH.NONE;
//		}
//
//		_currStage = PreemptionH.NONE;
	}

	public ZwzMatrix zwz;					// intergreen matrix

	public MainStage[] MainPhase;			// reference to the main stage of the intersection
	private int mainPhaseIterator;

	public OperationalMain opmain;			// operational main (beginning of cycle)
	public PostMain postmain;		  	    // post main (end of cycle)
	public Umschalt prog_transfer;		    // program switching

	public EinProgramm startprog;			// starting program (ein prog)
	public AusProgramm stopprog;			// stopping program (aus prog)
	public BlinkProgramm blinkprog;		// blink/flash program

	public DarkProg all_dark;

	//police program
	public HandProgramm police_fixed;		//For the Fixed Time Police Program
	public VAProg police_non_fixed;			//For the None Fixed Time Police Program

	public void setTK() {
		detQuantity = demands.size() + extensions.size() + demandExtensions.size() + queues.size() + pushButtons.size();
		System.out.println("Node number: " + getNummer());
		System.out.println("  - Signalgroups: T: " + (mainTrafficSgs.size() + notMainTrafficSgs.size()) + " | P: " + (mainPedestrianSgs.size() + notMainPedestrianSgs.size()) + " | B: " + (mainConditionedBlinkSgs.size() + notMainConditionedBlinkSgs.size() + regularBlinkSgs.size()) + " | FFT: " + (preemptionLampSgs.size()));
        System.out.println("  - Detectors: D: " + demands.size() + " | DE: " + demandExtensions.size() + " | E: " + extensions.size() + " | Q: " + queues.size() + " | P: " + pushButtons.size());
	}

	public void resetCounters() {
//		startmark = true;
		firstSec  = true;
		CycleCount = 0;
		isWaitingForSy = false;
		isInInterstage = false;
		
		// TODO:
//		if (preemption != null) {
//		    preemption.init();
//			if (preempJ instanceof PreemptionJLRT) {
//				PreemptionJLRT.memCat = false;
//			}
//		}
	}
	
	public boolean isMainPhase() {
		if (MainPhase[0].isStageBuilt())
			return true;
		return false;
	}

	public boolean isMainSg(Move sg) {
		/*for (mainPhaseIterator = 0; mainPhaseIterator < MainPhase.length; mainPhaseIterator++)
			if (MainPhase[mainPhaseIterator].isSgInStage(sg))
				return true;*/
		if (MainPhase[0].isSgInStage(sg))
			return true;
		return false;
	}

	public void setTrafficSG(int zust, int time) {
		for (int i = 0; i < mainTrafficSgs.size(); i++) {
			mainTrafficSgs.get(i).setSgDirekt(zust, time, 1, 1);
		}
		for (int i = 0; i < notMainTrafficSgs.size(); i++) {
			notMainTrafficSgs.get(i).setSgDirekt(zust, time, 1, 1);
		}
	}
	
	public void setPreemptionTriangles(int time) {
		for (int i = 0; i < preemptionLampSgs.size(); i++) {
			preemptionLampSgs.get(i).setSgDirekt(Zustand.AUS, time, 1, 1);
		}
	}

	/**
	 * Method sets all pedestrian SG to DUNKEL
	 * @param time	program time when to make the state change
	 */
	public void allPedestrianSGDunkel(int time)
	{
		//all pedestrian sg
		for (int i = 0; i < mainPedestrianSgs.size(); i++) {
			mainPedestrianSgs.get(i).setSgDirekt(Zustand.DUNKEL, time, 1, 1);
		} 
		for (int i = 0; i < notMainPedestrianSgs.size(); i++) {
			notMainPedestrianSgs.get(i).setSgDirekt(Zustand.DUNKEL, time, 1, 1);
		} 
	}

	/**
	 * Method sets all regular blinks to "zust" status (NO BLINK MUTNE)
	 * @param zust	the status to which to change the signalgroups
	 * @param time	program time when to make the state change
	 */
	public void setRegularBlinks(int zust, int time)
	{
		//regular blinks
		for (int i = 0; i < regularBlinkSgs.size(); i++) {
			regularBlinkSgs.get(i).setSgDirekt(zust, time, 1, 1);  
		}      
	}

	/**
	 * Method sets all conditioned blinks to "zust" status (BLINK MUTNE only)
	 * @param zust	the status to which to change the signalgroups
	 * @param time	program time when to make the state change
	 */
	public void setConditionedBlinks(int zust, int time)
	{
		//blink mutne
		for (int i = 0; i < mainConditionedBlinkSgs.size(); i++) {
			mainConditionedBlinkSgs.get(i).setSgDirekt(zust, time, 1, 1);  
		}
		for (int i = 0; i < notMainConditionedBlinkSgs.size(); i++) {
			notMainConditionedBlinkSgs.get(i).setSgDirekt(zust, time, 1, 1);  
		}     
	}

	/**
	 * Checks whether a node is flashing (by checking the status of the moves and NOT by the program)
	 * @return	returns TRUE when node is flashing
	 */
	public boolean isTkInFlash() {
		for (int i = 0; i <mainTrafficSgs.size(); i++) {
			if (mainTrafficSgs.get(i).getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < notMainTrafficSgs.size(); i++) {
			if (notMainTrafficSgs.get(i).getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < mainPedestrianSgs.size(); i++) {
			if (mainPedestrianSgs.get(i).getZustand() != Zustand.DUNKEL) {
				return false;
			}
		}

		for (int i = 0; i < notMainPedestrianSgs.size(); i++) {
			if (notMainPedestrianSgs.get(i).getZustand() != Zustand.DUNKEL) {
				return false;
			}
		}

		for (int i = 0; i < regularBlinkSgs.size(); i++) {
			if (regularBlinkSgs.get(i).getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < mainConditionedBlinkSgs.size(); i++) {
			if (mainConditionedBlinkSgs.get(i).getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < notMainConditionedBlinkSgs.size(); i++) {
			if (notMainConditionedBlinkSgs.get(i).getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}
		
		for (int i = 0; i < preemptionLampSgs.size(); i++) {
			if (preemptionLampSgs.get(i).getZustand() != Zustand.AUS) {
				return false;
			}
		}

		return true;
	}
	
	public boolean isInMapOnly() {
	    return getAktProg().getNummer() >= SpecialProgram.MaintenanceFirst.getProgramNumber() &&
	           getAktProg().getNummer() <= SpecialProgram.MaintenanceFirst.getProgramNumber() + Var.controller.getMaxProgramsNumber() - 1;
	}

	public void setMapOnly() {
		int endTime = timeToStartMapOnly + 1;
		if (isTkInFlash() || !isMapOnlyProgram()) {
			timeToStartMapOnly = -1;
			return;
		}
		
		if (timeToStartMapOnly == -1) {
			return;
		}

		//set all traffic sg to dark
		setTrafficSG        (Zustand.DUNKEL, timeToStartMapOnly);
		//set all traffic sg to blink
		setTrafficSG        (Zustand.GELBBLINKEN, endTime);
		// set all triangles sg to aus
		setPreemptionTriangles(timeToStartMapOnly);
		//"turning off" all pedestrian sg
		allPedestrianSGDunkel(timeToStartMapOnly);
		//set all blinks to blink
		setConditionedBlinks(Zustand.GELBBLINKEN, timeToStartMapOnly);
		setRegularBlinks    (Zustand.GELBBLINKEN, timeToStartMapOnly);
	}

	public void setExitMapOnly() {
		if (!isMapOnlyProgram() || isMaintenanceProgramRequest()) {
			timeToStartMapOnly = -1;
			return;
		}

		if (timeToStartMapOnly == -1) {
			return;
		}

		setSGtoEinProg(timeToStartMapOnly);
		freezeTime();
	}

	public void setSGtoEinProg(int startAction) {
		int endAction = startAction + startprog.getUmlaufZeit() - 1;

		//main traffic sg
		for (int i = 0; i < mainTrafficSgs.size(); i++) {
			mainTrafficSgs.get(i).setSgDirekt (Zustand.GELBBLINKEN, startAction, 1, 1);
		}

		//not main traffic sg
		for (int i = 0; i < notMainTrafficSgs.size(); i++) {
			notMainTrafficSgs.get(i).setSg (Zustand.ROT, startAction);
		}

		//all pedestrian sg
		for (int i = 0; i < mainPedestrianSgs.size(); i++) {
			mainPedestrianSgs.get(i).setSgDirekt (Zustand.ROT, startAction, 1, 1);
		} 
		for (int i = 0; i < notMainPedestrianSgs.size(); i++) {
			notMainPedestrianSgs.get(i).setSgDirekt (Zustand.ROT, startAction, 1, 1);
		}
		//preemption triangles
		setPreemptionTriangles(startAction);
		
		//regular blinkers
		setRegularBlinks(Zustand.GELBBLINKEN, startAction);
		setConditionedBlinks(Zustand.GELBBLINKEN, startAction);

		//main traffic sg
		for (int i = 0; i < mainTrafficSgs.size(); i++) {
			mainTrafficSgs.get(i).setSgDirekt (Zustand.GRUEN, endAction  , 1, 1);
		}

		//main pedestrian sg
		for (int i = 0; i < mainPedestrianSgs.size(); i++) {
			mainPedestrianSgs.get(i).setSgDirekt (Zustand.GRUEN, endAction  , 1, 1);
		}

		for (int i = 0; i < mainConditionedBlinkSgs.size(); i++) {
			mainConditionedBlinkSgs.get(i).setSgDirekt(Zustand.GELBBLINKEN, endAction, 1, 1);
		}

		for (int i = 0; i < notMainConditionedBlinkSgs.size(); i++) {
			notMainConditionedBlinkSgs.get(i).setSgDirekt(Zustand.AUS, endAction, 1, 1);
		}

		for (int i = 0; i < preemptionLampSgs.size(); i++) {
			preemptionLampSgs.get(i).setSgDirekt(Zustand.AUS, endAction, 1, 1);
		}
	}

	public void setBlink() {
		for (int i = 0; i < regularBlinkSgs.size(); i++) {
			regularBlinkSgs.get(i).schalteSofort(Zustand.GELBBLINKEN);
		}
	}

	public boolean SetGapModeDetectors(){
		if (!isRegularProgram())
		{
			inPolice = true;
			gapSet = 0;
			modeSet = 25;
		}
		else
		{
			inPolice = false;
			if(Var.controller.isAppHaifa())
			{
				gapSet = Var.controller.parameters.getParameterAt(getAktProg(), getNummer() == 1 ? 98 : 89);  //GapSet number
				modeSet = Var.controller.parameters.getParameterAt(getAktProg(), getNummer() == 1 ? 99 : 90); //ModeSet number

				if ((gapSet < 1) || (gapSet > 4))
					gapSet = 1;
				gapSet = (gapSet - 1) * 50;

				if ((modeSet < 1) || (modeSet > 4))
					modeSet = 1;
				modeSet = (modeSet - 1) * 50 + 25;
			}
			else 
			{
				gapSet = 0;
				modeSet = 0;
			}
		}
		return true;
	}

	public void skSet() {
		for (detIterator = 0; detIterator < pushButtons.size(); detIterator++) {
			pushButtons.get(detIterator).skSet();
		}
	}

	public void skReset() {
		for (detIterator = 0; detIterator < pushButtons.size(); detIterator++) {
			pushButtons.get(detIterator).skReset();
		}
	}

	public void startOfCycleEvent() {
		if (CycleCount < Var.MAX_COUNTER_VALUE) {
			CycleCount++;
		}
	}
	
	public void resetCumulativeCycles() {
		for (int i = 0; i < mainTrafficSgs.size(); i++) {
			mainTrafficSgs.get(i).resetCumulativeCycles();
		}
		
		for (int i = 0; i < notMainTrafficSgs.size(); i++) {
			notMainTrafficSgs.get(i).resetCumulativeCycles();
		}
		
		for (int i = 0; i < mainPedestrianSgs.size(); i++) {
			mainPedestrianSgs.get(i).resetCumulativeCycles();
		}
		
		for (int i = 0; i < notMainPedestrianSgs.size(); i++) {
			notMainPedestrianSgs.get(i).resetCumulativeCycles();
		}
	}
	
	public void updateCumulativeState() {
		if ((getAktProg().getUmlaufZeit() != 900 && getProgZeit() == 0) ||
			(getAktProg().getUmlaufZeit() == 900 && getProgZeit() <= getZyklDauer())) 
		{
			for (int i = 0; i < mainTrafficSgs.size(); i++) {
				mainTrafficSgs.get(i).startNewCycle();
			}
			
			for (int i = 0; i < notMainTrafficSgs.size(); i++) {
				notMainTrafficSgs.get(i).startNewCycle();
			}
			
			for (int i = 0; i < mainPedestrianSgs.size(); i++) {
				mainPedestrianSgs.get(i).startNewCycle();
			}
			
			for (int i = 0; i < notMainPedestrianSgs.size(); i++) {
				notMainPedestrianSgs.get(i).startNewCycle();
			}
		}
		
		for (int i = 0; i < mainTrafficSgs.size(); i++) {
			mainTrafficSgs.get(i).updateCumulativeGreen();
		}
		
		for (int i = 0; i < notMainTrafficSgs.size(); i++) {
			notMainTrafficSgs.get(i).updateCumulativeGreen();
		}
		
		for (int i = 0; i < mainPedestrianSgs.size(); i++) {
			mainPedestrianSgs.get(i).updateCumulativeGreen();
		}
		
		for (int i = 0; i < notMainPedestrianSgs.size(); i++) {
			notMainPedestrianSgs.get(i).updateCumulativeGreen();
		}
	}
	
	public boolean isProgramFixedCycle(ProgBase prog) {
	    if (prog == null) {
	        return false;
	    }
	    
	    return prog.getUmlaufZeit() != 900;
	}

	public void endOfCycleEvent() {
		for (mainPhaseIterator = 0; mainPhaseIterator < MainPhase.length; mainPhaseIterator++) {
			MainPhase[mainPhaseIterator].setPhasenZeit(0);
		}
		
		if (!isProgramFixedCycle(getAktProg())) {
			setProgZeit(0);
		}
		sk_number = 0;
		isDxDone = false;
	}

	abstract public void setFixedPoliceSG();
	abstract public void operationMainFunction();
	abstract public boolean isExitConditionsDone();
	abstract public void postMainFunction();
	abstract public boolean isDx();

	public boolean isError() {
	    return hw.AnlagenZustand.isAbschaltgrad1(this) || hw.AnlagenZustand.isAbschaltgrad2(this);
	}
    
    public boolean isDarkProgram() {
        return getAktProg() == getDunkelProgramm() || (all_dark != null && getAktProg().getNummer() == all_dark.getNummer());
    }

	public boolean isMapOnlyProgram() {
		return getAktProg().getNummer() >= SpecialProgram.MaintenanceFirst.getProgramNumber() && getAktProg().getNummer() <= SpecialProgram.MaintenanceFirst.getProgramNumber() + Var.controller.getMaxProgramsNumber() - 1;
	}

	public boolean isRegularProgram() {
		return getAktProg().getNummer() >= 1 && getAktProg().getNummer() <= Var.controller.getMaxProgramsNumber();
	}

	public boolean isMaintenanceProgramRequest() {
		return (getProgAnf().getProg().getNummer() >= SpecialProgram.MaintenanceFirst.getProgramNumber()) && (getProgAnf().getProg().getNummer() <= SpecialProgram.MaintenanceFirst.getProgramNumber() + Var.controller.getMaxProgramsNumber() - 1);
	}

	/**
	 * Method checks if a tk is in dark/blink modes or with errors (i.e. "offline")
	 * @param tk: the junction
	 * @return true, when tk is "offline" 
	 */
	public boolean isOffline() {
	    return isDarkProgram()
	            || getAktProg() == getBlinkProgramm()
	            || isError();
	}
	
	/**
	 * Updates the status of the node's detectors (inputs, demand, extension, queue, push-buttons and etc.)
	 */
	public void checkDetectors() {
		if(!SetGapModeDetectors())
			return;
        
        int inputsCount = allInputs.size();
        for (detIterator = 0; detIterator < inputsCount; detIterator++) {
            allInputs.get(detIterator).updateState();
        }
        
        int outputsCount = allOutputs.size();
        for (detIterator = 0; detIterator < outputsCount; detIterator++) {
            allOutputs.get(detIterator).updateState();
        }

		// constant for all applications
		if (isRegularProgram() && (Var.controller.isAppHaifa() || Var.controller.isAppNetiveiAyalon())) {
			if (Var.controller.parameters.isGWEnable(getAktProg().getNummer())) {
				dem_dx = false; 
			}
		}
	}
	
	public void updateSynopticMapGreenLED() {
	    if (!Var.controller.isSynopticMap()) {
	        return;
	    }
	    
        for (moveIterator = 0; moveIterator < mainTrafficSgs.size(); moveIterator++) {
            mainTrafficSgs.get(moveIterator).updateSynopticMapGreenLED();
        }

        for (moveIterator = 0; moveIterator < notMainTrafficSgs.size(); moveIterator++) {
            notMainTrafficSgs.get(moveIterator).updateSynopticMapGreenLED();
        }

        for (moveIterator = 0; moveIterator < mainPedestrianSgs.size(); moveIterator++) {
            mainPedestrianSgs.get(moveIterator).updateSynopticMapGreenLED();
        }

        for (moveIterator = 0; moveIterator < notMainPedestrianSgs.size(); moveIterator++) {
            notMainPedestrianSgs.get(moveIterator).updateSynopticMapGreenLED();
        }
	}

	public void startNode() {
		for (mainPhaseIterator = 0; mainPhaseIterator < MainPhase.length; mainPhaseIterator++)
			MainPhase[mainPhaseIterator].ResetCounters();
        this.startmark = true;
        this.lenPhue = 0;
	}

	public void freezeTime() {
		if (isCycleFrozen)
			 return;
		
		isCycleFrozen = true;
		if (getProgZeit() > 0) {
			setProgZeit(getProgZeit() - getZyklDauer());
		} else {
			setProgZeit(getAktProg().getUmlaufZeit() * Var.ONE_SEC_MS - getZyklDauer());
		}
	}
    
    /**
     * Freeze program cycle to a specific time
     * @param timeS the time which should be frozen (in seconds)
     */
    public void freezeTime(int timeS) {
        if (isCycleFrozen)
             return;
        
        isCycleFrozen = true;
        if (timeS > 0) {
            setProgZeit(timeS * Var.ONE_SEC_MS - getZyklDauer());
        } else {
            setProgZeit(getAktProg().getUmlaufZeit() * Var.ONE_SEC_MS - getZyklDauer());
        }
    }

	public boolean isFixedCycleProgram() {
		return (getAktProg().getUmlaufZeit() != 900) && (getAktProg().getUmlaufZeit() != 0);
	}
	
	public boolean isInInterStage() {
		return isInInterstage;
	}
	
	public Stage getPrevStage() {
		return prevStage;
	}
	
	public Stage getCurrStage() {
		return currStage != null ? currStage : ((Stage)Phase.getAktivePhase(this));
	}
	
	public int getCurrStageDuration() {
		return getCurrStage().getCurrStageDuration();
	}
	
	public int getCurrCycleSec() {
		return getProgSek();
	}

	public boolean isFullSecond() {
		return ((getProgZeit() % Var.ONE_SEC_MS) == 0);
	}

    public void operateOOOTLC() {
        if (OOOTLC != null) {
            if (isError()) {
                OOOTLC.resetAusgang();
            } else {
                OOOTLC.setAusgang();
            }
        }
    }
    
    public void updateActiveStage() {
        if (Phase.getAktivePhase(this) != null) {
            activeStageNumber = activeStageOrInterstageNumber;
            // Normally, this code should be active
            // However, due to the FDPP restriction of only one byte per stage number,
            // an interstage number cannot be sent.
            // Instead, it is marked that an interstage is active
            /*
            if (Phase.getAktivePhase(this).equals(interstageStage)) {
                activeStageNumber = interstageStage.interstage.getNummer();
            } else {
                activeStageNumber = interstageStage.getNummer();
            }
            */
        } else {
            activeStageNumber = Var.NONE;
        }
    }
    
    public void greenwaveAdd(iGreenWave greenwave) {
        if (greenwave == null)
            return;
        
        greenwaves.add(greenwave);
    }
    
    public void greenwaveReset() {
        for (int i = 0; i < greenwaves.size(); i++) {
            greenwaves.get(i).reset();
        }
    }
    
    private boolean isGWError;
    public void greenwaveOperateGW() {
        isGWError = false;
        for (int i = 0; i < greenwaves.size(); i++) {
            greenwaves.get(i).operateGW();
            isGWError |= greenwaves.get(i).isError(); 
        }
        
        iGreenWave.GW_Fail = isGWError;
        if (iGreenWave.Led_GwFail != null) {
            iGreenWave.Led_GwFail.operate(isGWError);
        }
    }
    
    public void greenwavePostOperateGW() {
        for (int i = 0; i < greenwaves.size(); i++) {
            greenwaves.get(i).postOperateGW();
        }
    }
    
    public boolean greenwaveIsActive() {
        for (int i = 0; i < greenwaves.size(); i++) {
            if (greenwaves.get(i).isActive()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean greenwaveIsGWEnabledProgram(int programNumber) {
        for (int i = 0; i < greenwaves.size(); i++) {
            if (!greenwaves.get(i).isGreenWaveProgram(programNumber)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean greenwaveIsSYError() {
        for (int i = 0; i < greenwaves.size(); i++) {
            if (greenwaves.get(i).isSYError()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean greenwaveIsSY() {
        for (int i = 0; i < greenwaves.size(); i++) {
            if (!greenwaves.get(i).isSY()) {
                isWaitingForSy = true;
                return false;
            }
        }
        isWaitingForSy = false;
        return true;
    }
    
    public boolean greenwaveIsSyErrorHW() {
        for (int i = 0; i < greenwaves.size(); i++) {
            if ((greenwaves.get(i) instanceof GreenWaveHW)
                    && greenwaves.get(i).isSYError()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean greenwaveIsSyErrorClock() {
        for (int i = 0; i < greenwaves.size(); i++) {
            if ((greenwaves.get(i) instanceof GreenWaveClock)
                    && greenwaves.get(i).isSYError()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isDetectorsError() {
        boolean result = false;
        for (int i = 0; i < demands.size(); i++) {
            if (demands.get(i).getError() != Detektor.KEIN_FEHLER) {
                result = true;
            }
        }
        for (int i = 0; i < demandExtensions.size(); i++) {
            if (demandExtensions.get(i).getError() != Detektor.KEIN_FEHLER) {
                result = true;
            }
        }
        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).getError() != Detektor.KEIN_FEHLER) {
                result = true;
            }
        }
        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getError() != Detektor.KEIN_FEHLER) {
                result = true;
            }
        }
        for (int i = 0; i < pushButtons.size(); i++) {
            if (pushButtons.get(i).getError() != Detektor.KEIN_FEHLER) {
                result = true;
            }
        }
        isDetectorsError = result;
        return isDetectorsError;
    }
    
    public boolean isInPolice() {
        return getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber();
    }
}
