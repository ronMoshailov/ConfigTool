package special;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 8                                                                      	*
 *  Junction Name  : Hertzel / Bar Kohva / Bilu													*
 *  Equipmentno.   : h8                                                                     	*
 *                                                                                              *
 ************************************************************************************************/
import det.Det;
import det.Detektor;
import enums.HaifaParametersIndexes;
import hw.AnlagenZustand;
import hw.tk.HwTeilknoten;
import sg.Sg;
import sg.Zustand;
import sg.ZwzMatrix;
import ta172.ParamSet;
import ta172.ParametersJerusalem;
import ta172.ParametersTelAviv;
import ta172.Var;
import uhr.Zeit;
import vt.*;

abstract public class Node extends TeilKnoten {
	public static class SpecialProgram {
		public static final SpecialProgram MaintenanceFirst = new SpecialProgram(51); // number of first maintenance (map only) program
		public static final SpecialProgram Flash            = new SpecialProgram(90); // number of blink program                       
		public static final SpecialProgram Ein              = new SpecialProgram(91); // number of ein program                         
		public static final SpecialProgram Aus              = new SpecialProgram(92); // number of aus program                         
		public static final SpecialProgram Police           = new SpecialProgram(95); // number of police program                      
		public static final SpecialProgram Dark             = new SpecialProgram(99); // number of dark program                        

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

	/*
	 * Arrays to hold traffic information of the node (traffic moves, pedestrians, blinkers, various detectors, etc.)
	 */
	public Move[] mainTrafficSgs;				// main traffic moves of the node
	public Move[] notMainTrafficSgs;			// secondary traffic moves of the node
	public Move[] mainPedestrianSgs;			// main pedestrian moves of the node
	public Move[] notMainPedestrianSgs;			// secondary pedestrian moves of the node
	public Move[] regularBlinkSgs;				// regular blinkers (not conditional)
	public Move[] mainConditionedBlinkSgs;		// main conditional blinkers (that are active during main stage)
	public Move[] notMainConditionedBlinkSgs;	// secondary conditional blinkers (that are not active during main stage)
	
	private TPDetector[] pushButtons; 			// pedestrian push buttons
	private Input[] inputs;						// regular inputs (pulses and etc.)
	private DDetector[] demands;				// demand detectors
	private EDetector[] extensions;				// extension detectors
	private DEDetector[] demandExtensions;		// combined demand and extension detectors
	private QDetector[] queues;					// queue detectors
	
	private Detektor[] detsTmp;					// auxiliary array				
	private int detIterator;					// auxiliary iterator

	public int lastActiveProgram; 				// number of the program that was active in the last scan cycle

	public int firstCycleExt;					// longest minimum green time for phase - EQA's length = firstCycleExt_tk1 (in MS)
	public boolean isWaitingForSy;          	// true while controller is waiting for syncronization pulse

	public int timeToStartMapOnly = -1;			// time (of program cycle) when to start transition to or from Map Only (Maintenance) mode
	
	private int mapOnlyAccesses = 0;
	private boolean settingMapOnly = false;
	

	// additional programs (fixed cycle and logic programs)
	public VAProg p01, p02, p03, p04, p05, p06, p07, p08, p09, p10;
	public VAProg p11, p12, p13, p14, p15, p16, p17, p18, p19, p20;
	public VAProg p21, p22, p23, p24, p25, p26, p27, p28, p29, p30;
	public VAProg p31, p32;

	public VAProg p01MO, p02MO, p03MO, p04MO, p05MO, p06MO, p07MO, p08MO, p09MO, p10MO;
	public VAProg p11MO, p12MO, p13MO, p14MO, p15MO, p16MO, p17MO, p18MO, p19MO, p20MO;
	public VAProg p21MO, p22MO, p23MO, p24MO, p25MO, p26MO, p27MO, p28MO, p29MO, p30MO;
	public VAProg p31MO, p32MO;

	public Node(String name, HwTeilknoten hwTk, boolean dx, int firstCycleExt) {
		super(name, hwTk);
		useDx = dx;
		this.firstCycleExt = firstCycleExt * Var.ONE_SEC; // convert to ms if needed
	}

	public void UpdateMovesArraysAndSafety() {
		Sg[] sgs = Sg.getSgArray();
		int sgsIterator = 0;
		int mainTraffic = 0, t1 = 0, secondaryTraffic = 0, t2 = 0;
		int mainPedestrian = 0, p1 = 0, secondaryPedestrian = 0, p2 = 0;
		int regularBlinkers = 0, rb = 0;
		int conditionalMainBlinkers = 0, cb1 = 0, conditionalSecondaryBlinkers = 0, cb2 = 0;
		Move move = null;

		sgs = Sg.getSgArray();

		for (sgsIterator = 0; sgsIterator < sgs.length; sgsIterator++) {
			move = (Move)sgs[sgsIterator];

			if (move.node.getNummer() == getNummer()) {
				if (move.isMainTraffic()) {
					mainTraffic++;
				} else if (move.isSecondaryTraffic()) {
					secondaryTraffic++;
				} else if (move.isMainPedestrian()) {
					mainPedestrian++;
				} else if (move.isSecondaryPedestrian()) {
					secondaryPedestrian++;
				} else if (move.isRegularBlinker()) {
					regularBlinkers++;
				} else if (move.isMainConditionalBlinker()) {
					conditionalMainBlinkers++;
				} else if (move.isSecondaryConditionalBlinker()) {
					conditionalSecondaryBlinkers++;
				}
			}
		}

		mainTrafficSgs = new Move[mainTraffic];
		notMainTrafficSgs = new Move[secondaryTraffic];
		mainPedestrianSgs = new Move[mainPedestrian];
		notMainPedestrianSgs = new Move[secondaryPedestrian];
		mainConditionedBlinkSgs = new Move[conditionalMainBlinkers];
		notMainConditionedBlinkSgs = new Move[conditionalSecondaryBlinkers];
		regularBlinkSgs = new Move[regularBlinkers];

		for (sgsIterator = 0; sgsIterator < sgs.length; sgsIterator++) {
			move = (Move)sgs[sgsIterator];
			if (move.node.getNummer() == getNummer()) {
				if (move.isMainTraffic()) {
					mainTrafficSgs[t1++] = move;
				} else if (move.isSecondaryTraffic()) {
					notMainTrafficSgs[t2++] = move;
				} else if (move.isMainPedestrian()) {
					mainPedestrianSgs[p1++] = move;
				} else if (move.isSecondaryPedestrian()) {
					notMainPedestrianSgs[p2++] = move;
				} else if (move.isRegularBlinker()) {
					regularBlinkSgs[rb++] = move;
				} else if (move.isMainConditionalBlinker()) {
					mainConditionedBlinkSgs[cb1++] = move;
				} else if (move.isSecondaryConditionalBlinker()) {
					notMainConditionedBlinkSgs[cb2++] = move;
				}
			}
		}
	}

	public void UpdateDetectors() {
		int inputsCount = 0;
		int demandsCount = 0;
		int extensionsCount = 0;
		int demandExtensionsCount = 0;
		int queuesCount = 0;
		int pushButtonsCount = 0;
		detsTmp = Det.get_detArray();

		if (detsTmp != null) {
			for (detIterator = 0; detIterator < detsTmp.length; detIterator++) {
				if (detsTmp[detIterator].getTk().getNummer() == getNummer()) {
					if (detsTmp[detIterator] instanceof DDetector) {
						demandsCount++;
					} else if (detsTmp[detIterator] instanceof EDetector) {
						extensionsCount++;
					} else if (detsTmp[detIterator] instanceof DEDetector) {
						demandExtensionsCount++;
					} else if (detsTmp[detIterator] instanceof QDetector) {
						queuesCount++;
					} else if (detsTmp[detIterator] instanceof TPDetector) {
						pushButtonsCount++;
					} else if (detsTmp[detIterator] instanceof Input) {
						inputsCount++;
					}
				}
			}
		}

		inputs = new Input[inputsCount];
		demands = new DDetector[demandsCount];
		extensions = new EDetector[extensionsCount];
		demandExtensions = new DEDetector[demandExtensionsCount];
		queues = new QDetector[queuesCount];
		pushButtons = new TPDetector[pushButtonsCount];

		inputsCount = 0;
		demandsCount = 0;
		extensionsCount = 0;
		demandExtensionsCount = 0;
		queuesCount = 0;
		pushButtonsCount = 0;

		if (detsTmp != null) {
			for (detIterator = 0; detIterator < detsTmp.length; detIterator++) {
				if (detsTmp[detIterator].getTk().getNummer() == getNummer()) {
					if (detsTmp[detIterator] instanceof DDetector) {
						demands[demandsCount++] = (DDetector)detsTmp[detIterator];
					} else if (detsTmp[detIterator] instanceof EDetector) {
						extensions[extensionsCount++] = (EDetector)detsTmp[detIterator];
					} else if (detsTmp[detIterator] instanceof DEDetector) {
						demandExtensions[demandExtensionsCount++] = (DEDetector)detsTmp[detIterator];
					} else if (detsTmp[detIterator] instanceof QDetector) {
						queues[queuesCount++] = (QDetector)detsTmp[detIterator];
					} else if (detsTmp[detIterator] instanceof TPDetector) {
						pushButtons[pushButtonsCount++] = (TPDetector)detsTmp[detIterator];
					} else if (detsTmp[detIterator] instanceof Input) {
						inputs[inputsCount++] = (Input)detsTmp[detIterator];
					}
				}
			}
		}
	}

	public void UpdatePushButtons() {
		int pushButtonsCount = 0;
		detsTmp = Det.get_detArray();

		if (detsTmp != null) {
			for (detIterator = 0; detIterator < detsTmp.length; detIterator++) {
				if (detsTmp[detIterator].getTk().getNummer() == getNummer() && (detsTmp[detIterator] instanceof TPDetector)) {
					pushButtonsCount++;
				}
			}
		}

		pushButtons = new TPDetector[pushButtonsCount];
		pushButtonsCount = 0;

		if (detsTmp != null) {
			for (detIterator = 0; detIterator < detsTmp.length; detIterator++) {
				if (detsTmp[detIterator].getTk().getNummer() == getNummer() && (detsTmp[detIterator] instanceof TPDetector)) {
					pushButtons[pushButtonsCount++] = (TPDetector)detsTmp[detIterator];
				}
			}
		}
	}

	public boolean isRegularProgramFixedCycle() {
		return (isRegularProgram() && (getAktProg().getUmlaufZeit() != Var.PROGRAM_NONE_FIXED_CYCLE));
	}

	public void firstCycleActions() {
		// TODO: startmark and so on....
	}






	public static boolean[] SyncTks = new boolean[4]; 

	public int[] dpt2FirstChannels = new int[2];
	public int dpt2SecondChannelIndex = 0;
	public int[] dpt2SecondChannels = {121, 122};

	public final int checkVtVarUpdate = 4; // once per how many cycles time should parameters be checked for update (via BDI, Access...)

	public boolean startmark;				// first cycle of main stage in the beginning of the program cycle after return from ein/dark/blink/police
	public boolean firstSec; 				// if first second of program (1 to Var.maxProgNumber) (after startup or return from dark/blink/police)
	public int CycleCount;					// number of cycles stayed in same program
	public int getCurrCycleSec; 			// program clock (in ms)
	public int lenPhue; 					// length of interstage
	//	public int lenPhueSG;					// length of interstage till stage is built (only moves, not defined duration of interstage)
	public int sk_number; 					// branch number
	public boolean useDx; 					// if main stage of this node has Dx
	public int countVtVarUpdate;			// counts how many cycles passed since last parameters update has been done
	public int exitConditionsCounter = 0; 	// counts the time main stage was extended due to exit conditions
	public int gapSet, modeSet;				// indexes for gap and mode sets for Haifa intersections
	public boolean inPolice;				// flag for Haifa intersections to ignore parameters if in police program
	public boolean dem_dx;					// whether there is some demand or no demands whatsoever (if use_dx is true)
	public boolean dx_done;					// if current PROGRAM cycle dx demand status check was finished
	public boolean sync_tk = true;			// for controllers with more than 1 node: true when controller is in end of PROGRAM cycle
	public int sgQuantity;					// number of traffic and pedestrian signal groups
	public int blinkQuantity;				// number of blinkers (regular + conditional)
	public int detQuantity;					// number of detectors and push-buttons (demand, extension, queue, push-button)

	public boolean isWaitingForFO; // true while controller is waiting for an FO input

	public int MAX_STAGES = 0; // maximum number of stages
	public int[] _activeStages;
	public int _currStage = Preemption.NONE;
	public int _active_stage_iterator = 0;
	public int _lastStageDuration = 0;

	public Preemption preemp;

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
		_lastStageDuration = ph.getPhasenZeit() - lenPhue - ph.phLen*Var.ONE_SEC;
	}

	public int getLastStageDuration() {
		return _lastStageDuration;
	}

	public void updateActiveStages() {
		if (!Var.controller.isPreemption()) {
			return;
		}
		int phaseNum = Phase.getAktivePhase(this).getNummer();
		if (_currStage == phaseNum || _active_stage_iterator >= MAX_STAGES) { // current stage was already updated
			return;
		}
		_currStage = phaseNum;
		_activeStages[_active_stage_iterator] = _currStage;
		_active_stage_iterator++;
	}

	public void resetActiveStages() {
		if (!Var.controller.isPreemption() || _active_stage_iterator >= MAX_STAGES) {
			return;
		}

		while (_active_stage_iterator > 0) {
			_active_stage_iterator--;
			_activeStages[_active_stage_iterator] = Preemption.NONE;
		}

		_currStage = Preemption.NONE;
	}

	public ZwzMatrix zwz;					// intergreen matrix

	public MainStage MainPhase;			// reference to the main stage of the intersection

	public OperationalMain opmain;			// operational main (beginning of cycle)
	public MyPostMainFkt postmain;			// post main (end of cycle)
	public MyUmschaltFkt prog_transfer;		// program switching

	public MyEinProgramm startprog;			// starting program (ein prog)
	public MyAusProgramm stopprog;			// stopping program (aus prog)
	public MyBlinkProgramm blinkprog;		// blink/flash program

	public DarkProg all_dark;

	//police program
	public MyHandProgramm police_fixed;		//For the Fixed Time Police Program
	public VAProg police_non_fixed;			//For the None Fixed Time Police Program

	public void SetTK() {
		sgQuantity = mainTrafficSgs.length + notMainTrafficSgs.length + notMainPedestrianSgs.length + mainPedestrianSgs.length;
		blinkQuantity = regularBlinkSgs.length + mainConditionedBlinkSgs.length + notMainConditionedBlinkSgs.length;
		detQuantity = inputs.length + demands.length + extensions.length + demandExtensions.length + queues.length + pushButtons.length;
		System.out.print  ("Tk Number: "+ getNummer());
		System.out.print  (" - sgQuantity: "+sgQuantity);
		System.out.print  (" - blinkQuantity: "+blinkQuantity);
		System.out.println(" - detQuantity: "+detQuantity + " (" + pushButtons.length + " pushbuttons)");
	}

	public void resetCounters() {
		startmark = true;
		firstSec  = true;
		CycleCount = 0;
		exitConditionsCounter = 0;
		isWaitingForSy = false;
		mapOnlyAccesses = 0;
		settingMapOnly = false;
		waitForSyTime = 0;
	}

	public boolean isMainPhase() {
		return MainPhase.isStageBuilt();
	}

	public boolean isMainSg(Move sg) {
		return MainPhase.isSgInStage(sg);
	}

	public void setTrafficSG(int zust, int time) {
		for (int i = 0; i < mainTrafficSgs.length; i++) {
			mainTrafficSgs[i].setSgDirekt(zust, time, 1, 1);
		}
		for (int i = 0; i < notMainTrafficSgs.length; i++) {
			notMainTrafficSgs[i].setSgDirekt(zust, time, 1, 1);
		}
	}

	/**
	 * Method sets all pedestrian SG to DUNKEL
	 * @param time	program time when to make the state change
	 */
	public void allPedestrianSGDunkel(int time)
	{
		//all pedestrian sg
		for (int i = 0; i < mainPedestrianSgs.length; i++) {
			mainPedestrianSgs[i].setSgDirekt(Zustand.DUNKEL, time, 1, 1);
		} 
		for (int i = 0; i < notMainPedestrianSgs.length; i++) {
			notMainPedestrianSgs[i].setSgDirekt(Zustand.DUNKEL, time, 1, 1);
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
		for (int i = 0; i < regularBlinkSgs.length; i++) {
			regularBlinkSgs[i].setSgDirekt(zust, time, 1, 1);  
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
		for (int i = 0; i < mainConditionedBlinkSgs.length; i++) {
			mainConditionedBlinkSgs[i].setSgDirekt(zust, time, 1, 1);  
		}
		for (int i = 0; i < notMainConditionedBlinkSgs.length; i++) {
			notMainConditionedBlinkSgs[i].setSgDirekt(zust, time, 1, 1);  
		}     
	}

	/**
	 * Checks whether a node is flashing (by checking the status of the moves and NOT by the program)
	 * @return	returns TRUE when node is flashing
	 */
	public boolean isTkInFlash() {
		for (int i = 0; i <mainTrafficSgs.length; i++) {
			if (mainTrafficSgs[i].getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < notMainTrafficSgs.length; i++) {
			if (notMainTrafficSgs[i].getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < mainPedestrianSgs.length; i++) {
			if (mainPedestrianSgs[i].getZustand() != Zustand.DUNKEL) {
				return false;
			}
		}

		for (int i = 0; i < notMainPedestrianSgs.length; i++) {
			if (notMainPedestrianSgs[i].getZustand() != Zustand.DUNKEL) {
				return false;
			}
		}

		for (int i = 0; i < regularBlinkSgs.length; i++) {
			if (regularBlinkSgs[i].getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < mainConditionedBlinkSgs.length; i++) {
			if (mainConditionedBlinkSgs[i].getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		for (int i = 0; i < notMainConditionedBlinkSgs.length; i++) {
			if (notMainConditionedBlinkSgs[i].getZustand() != Zustand.GELBBLINKEN) {
				return false;
			}
		}

		return true;
	}

	public void setMapOnly() {
		int endTime = timeToStartMapOnly + 1;
		System.out.println("set map");
		if (isTkInFlash() || !isInMaintenance()) {
			timeToStartMapOnly = -1;
			return;
		}
		System.out.println();
		
		if (timeToStartMapOnly == -1) {
			return;
		}

		//set all traffic sg to dark
		setTrafficSG        (Zustand.DUNKEL, timeToStartMapOnly);
		//set all traffic sg to blink
		setTrafficSG        (Zustand.GELBBLINKEN, endTime);
		//"turning off" all pedestrian sg
		allPedestrianSGDunkel(timeToStartMapOnly);
		//set all blinks to blink
		setConditionedBlinks(Zustand.GELBBLINKEN, timeToStartMapOnly);
		setRegularBlinks    (Zustand.GELBBLINKEN, timeToStartMapOnly);
	}

	public void setExitMapOnly() {
		if (!isInMaintenance() || isMaintenanceProgramRequest()) {
			timeToStartMapOnly = -1;
			return;
		}

		if (timeToStartMapOnly == -1) {
			return;
		}

		setSGtoEinProg(timeToStartMapOnly);
		FreezeProgramCycle();
	}

	public void setSGtoEinProg(int startAction) {
		int endAction = startAction + 6;

		//main traffic sg
		for (int i = 0; i < mainTrafficSgs.length; i++) {
			mainTrafficSgs[i].setSgDirekt (Zustand.GELBBLINKEN, startAction, 1, 1);
		}

		//not main traffic sg
		for (int i = 0; i < notMainTrafficSgs.length; i++) {
			notMainTrafficSgs[i].setSg (Zustand.ROT, startAction);
		}

		//all pedestrian sg
		for (int i = 0; i < mainPedestrianSgs.length; i++) {
			mainPedestrianSgs[i].setSgDirekt (Zustand.ROT, startAction, 1, 1);
		} 
		for (int i = 0; i < notMainPedestrianSgs.length; i++) {
			notMainPedestrianSgs[i].setSgDirekt (Zustand.ROT, startAction, 1, 1);
		}
		//regular blinkers
		setRegularBlinks(Zustand.GELBBLINKEN, startAction);
		setConditionedBlinks(Zustand.GELBBLINKEN, startAction);

		//main traffic sg
		for (int i = 0; i < mainTrafficSgs.length; i++) {
			mainTrafficSgs[i].setSgDirekt (Zustand.GRUEN, endAction  , 1, 1);
		}

		//main pedestrian sg
		for (int i = 0; i < mainPedestrianSgs.length; i++) {
			mainPedestrianSgs[i].setSgDirekt (Zustand.GRUEN, endAction  , 1, 1);
		}

		for (int i = 0; i < mainConditionedBlinkSgs.length; i++) {
			mainConditionedBlinkSgs[i].setSgDirekt(Zustand.GELBBLINKEN, endAction, 1, 1);
		}

		for (int i = 0; i < notMainConditionedBlinkSgs.length; i++) {
			notMainConditionedBlinkSgs[i].setSgDirekt(Zustand.AUS, endAction, 1, 1);
		}
	}

	public void setBlink() {
		for (int i = 0; i < regularBlinkSgs.length; i++) {
			regularBlinkSgs[i].schalteSofort(Zustand.GELBBLINKEN);
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
				gapSet = Func.readParam(getAktProg(), getNummer() == 1 ? HaifaParametersIndexes.indGapSet : HaifaParametersIndexes.indGapSet2);  //GapSet number
				modeSet = Func.readParam(getAktProg(), getNummer() == 1 ? HaifaParametersIndexes.indModeSet : HaifaParametersIndexes.indModeSet2); //ModeSet number

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
		for (detIterator = 0; detIterator < pushButtons.length; detIterator++) {
			pushButtons[detIterator].skSet();
		}
	}

	public void skReset() {
		for (detIterator = 0; detIterator < pushButtons.length; detIterator++) {
			pushButtons[detIterator].skReset();
		}
	}

	public void startOfCycleEvent() {
		if (CycleCount < Var.MAX_COUNTER_VALUE) {
			CycleCount++;
		}
	}

	public void endOfCycleEvent() {
		exitConditionsCounter = 0;
		MainPhase.setPhasenZeit(0);
		if (!Func.isProgramFixedCycle(getAktProg())) {
			setProgZeit(0);
		}
		lenPhue = 0;
	}

	abstract public void setFixedPoliceSG();
	abstract public void operationMainFunction();
	abstract public boolean isExitConditionsDone();
	abstract public void postMainFunction();

	public static boolean Sync(int tkIndex) {
		SyncTks[tkIndex] = true;

		// checking that all intersections (before the current one) are ready to sync
		for (int i = 1; i < tkIndex; i++) {
			if(SyncTks[i] == false) {
				return false;
			}
		}
		// checking that all intersections (after the current one) are ready to sync
		for (int i = tkIndex + 1; i <= TeilKnoten.getAnzahl(); i++) {
			Node _tk = ((Node)(TeilKnoten.getTeilKnoten(i)));
			if (SyncTks[i] == false) {
				return false;
			}
		}
		return true;
	}

	public static void ResetSync(int tkIndex) {
		SyncTks[tkIndex] = false;
	}

	public int getNumberOfPreviousSGs()
	{
		int sgs = 0;
		for (int i = 1; i < getNummer(); i++)
		{
			Node tk = (Node)getTeilKnoten(i);
			sgs += (tk.sgQuantity + tk.blinkQuantity);
		}
		return sgs;
	}

	public boolean isInPoliceProgram() {
		return getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber();
	}

	public boolean isInMaintenance() {
		return getAktProg().getNummer() >= SpecialProgram.MaintenanceFirst.getProgramNumber() && getAktProg().getNummer() <= SpecialProgram.MaintenanceFirst.getProgramNumber() + Var.controller.getMaxProgramsNumber() - 1;
	}
	
	public boolean isDoorOpen() {
		return (AnlagenZustand.getTuerZustand() == AnlagenZustand.TUER_GEOEFFNET);
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
		if ((all_dark == getAktProg() ||
			(getBlinkProgramm() == getAktProg())) ||
			(getDunkelProgramm() == getAktProg()) ||
			(getAktProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) ||
			AnlagenZustand.isAbschaltgrad1(this) ||
			AnlagenZustand.isAbschaltgrad2(this))
		{
			return true;
		}
		return false;
	}
	
	public void checkMap() {
		for (detIterator = 0; detIterator < mainTrafficSgs.length; detIterator++) {
			mainTrafficSgs[detIterator].UpdateStateCounters();
		}
		for (detIterator = 0; detIterator < notMainTrafficSgs.length; detIterator++) {
			notMainTrafficSgs[detIterator].UpdateStateCounters();
		}
		for (detIterator = 0; detIterator < mainPedestrianSgs.length; detIterator++) {
			mainPedestrianSgs[detIterator].UpdateStateCounters();
		}
		for (detIterator = 0; detIterator < notMainPedestrianSgs.length; detIterator++) {
			notMainPedestrianSgs[detIterator].UpdateStateCounters();
		}
	}

	/**
	 * Updates the status of the node's detectors (inputs, demand, extension, queue, push-buttons and etc.)
	 */
	public void checkDetectors() {
		if(!SetGapModeDetectors())
			return;

		for (detIterator = 0; detIterator < inputs.length; detIterator++) {
			inputs[detIterator].UpdateState();
		}
		for (detIterator = 0; detIterator < demands.length; detIterator++) {
			demands[detIterator].UpdateState();
		}
		for (detIterator = 0; detIterator < extensions.length; detIterator++) {
			extensions[detIterator].UpdateState();
		}
		for (detIterator = 0; detIterator < demandExtensions.length; detIterator++) {
			demandExtensions[detIterator].UpdateState();
		}
		for (detIterator = 0; detIterator < queues.length; detIterator++) {
			queues[detIterator].UpdateState();
		}
		for (detIterator = 0; detIterator < pushButtons.length; detIterator++) {
			pushButtons[detIterator].UpdateState();
		}

		// constant for all applications
		if (isRegularProgram() && Var.controller.isAppHaifa()) {
			if (Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indIsCrossMaster.getIndex()] != 0) {
				dem_dx = false; 
			}
		}
	}

	public void startNode() {
		MainPhase.ResetCounters();
	}

	/**
	 * Gets the maximal cycle time of the currently active program
	 * @return	returns the current program's cycle time (in milliseconds)
	 */
	public int getCycleParam() {
		if (isOffline() || (!isRegularProgram() && (!Var.controller.isMaintenance() || !isInMaintenance()))) {
			return 0;
		}

		if (Var.controller.isAppHaifa()) {
			if (getNummer() == 2) {
				return ParamSet.pFixedCycleTime_ProgAtt2;
			} else {
				return ParamSet.pFixedCycleTime_ProgAtt;
			}
		} else if (Var.controller.isAppJerusalem()) {
			if (Var.controller.isPreemption()) {
				// TODO: update value
				return 0;
			} else {
				return ((ParametersJerusalem)Var.controller.dvi35Parameters).getCycle();
			}
		} else if (Var.controller.isAppTelAviv()) {
			return ((ParametersTelAviv)Var.controller.dvi35Parameters).getCycle();
		} else {
			return Func.getCycleParam(getAktProg().getNummer());
		}
	}

	/**
	 * Gets the maximal cycle time of the currently active program
	 * @return	returns the current program's cycle time (in seconds)
	 */
	public int getOffsetParam() {
		if (isOffline() || (!isRegularProgram() && (!Var.controller.isMaintenance() || !isInMaintenance()))) {
			return 0;
		}

		if (Var.controller.isAppHaifa()) {
			if (getNummer() == 2) {
				return ParamSet.pOffset_ProgAtt2;
			} else {
				return ParamSet.pOffset_ProgAtt;
			}
		} else if (Var.controller.isAppJerusalem()) {
			if (Var.controller.isPreemption()) {
				// TODO: update value
				return 0;
			} else {
				return ParamSet.Sync_Offset;
			}
		} else if (Var.controller.isAppTelAviv()) {
			return ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(0);
		} else {
			return Func.getOffsetParam(getAktProg().getNummer());
		}
	}

	public boolean isAlreadyFrozen = false;
	
	public void FreezeProgramCycle() {
		if (isAlreadyFrozen)
			 return;
		
		isAlreadyFrozen = true;
		if (getProgZeit() > 0) {
			setProgZeit(getProgZeit() - getZyklDauer());
		} else {
			setProgZeit(getAktProg().getUmlaufZeit() * Var.ONE_SEC - getZyklDauer());
		}
	}
	
	/**
	 * Freeze program cycle to a specific time
	 * @param timeS	the time which should be frozen (in seconds)
	 */
	public void FreezeProgramCycle(int timeS) {
		if (isAlreadyFrozen)
			 return;
		
		isAlreadyFrozen = true;
		if (timeS > 0) {
			setProgZeit(timeS * Var.ONE_SEC - getZyklDauer());
		} else {
			setProgZeit(getAktProg().getUmlaufZeit() * Var.ONE_SEC - getZyklDauer());
		}
	}

	private int getSecondsFromMidnight() {
		return (Zeit.getStunde() * 60 + Zeit.getMinute()) * 60 + Zeit.getSekunde();
	}

	private boolean isPulseReceived() 
	{
		return ((getSecondsFromMidnight() % getAktProg().getUmlaufZeit()) == getAktProg().getGwpB());
	}
	
	public boolean isNotGW() {
		if (!isRegularProgram() && !isInMaintenance()) {
			return true;
		}
		
		return !Var.controller.isClockSync(); // TODO: is this enough?
	}

	private int waitForSyTime = 0;
	private boolean isPulseWaitElapsed() {
		return waitForSyTime <= 0;
	}

	public int waitForSyDuration = 0;
	private boolean isMinDone = false;
	private boolean isCycle = false;
	private boolean isSynced = false;
	private int offset = 0;
	private int cycle = 0;
	
	private int maxWaitForSyDuration = 0;
	
	public boolean isClockGreenWaveSynced() {
		if (Var.controller.isAppJerusalem())
			return isClockGreenWaveSyncedJ();
		else if (Var.controller.isAppTelAviv())
			return isClockGreenWaveSyncedTA();
		else
			return false;
	}
	
	private boolean isClockGreenWaveSyncedJ() {
		if (!isFixedCycleProgram() || isNotGW()) {
			return true;
		}
		
		isSynced = (!Func.ifClockCoordination(this, getAktProg()) || isPulseReceived());
		cycle = ((ParametersJerusalem)Var.controller.dvi35Parameters).getCycle();
		offset = ((ParametersJerusalem)Var.controller.dvi35Parameters).getOffset();
		
		// handling validity of offset: 0 <= offset < max cycle length
		if (Var.tk1.isFixedCycleProgram()) {
			if (offset == cycle) {
				offset = 0;
			} else if (offset > cycle && cycle > 0) {
				offset = offset % cycle;
			}
		}

		if (isSynced && (getProgZeit() == offset * Var.ONE_SEC)) {
			setProgSek(offset);
			waitForSyTime = 0;
			return true;
		} else {
			if (getProgZeit() != offset * Var.ONE_SEC) {
				return false;
			}
			
			maxWaitForSyDuration = ((int)(cycle / 3)) * Var.ONE_SEC;
			if (maxWaitForSyDuration % Var.ONE_SEC != 0)
				maxWaitForSyDuration += getZyklDauer();
			
			if (waitForSyTime >= maxWaitForSyDuration) 
			{
				waitForSyTime = 0;
				return true;
			}
			
			if (waitForSyTime < 999000)
				waitForSyTime += getZyklDauer();
			
			FreezeProgramCycle();
			return false;
		}
	}
	
	public boolean isClockGreenWaveSyncedTA() {
		if (!isFixedCycleProgram() || isNotGW()) {
			return true;
		}
		
		isSynced = (!Func.ifClockCoordination(this, getAktProg()) || isPulseReceived());
		cycle = ((ParametersTelAviv)Var.controller.dvi35Parameters).getCycle();
		offset = ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(0) / Var.ONE_SEC;
		
		// handling validity of offset: 0 <= offset < max cycle length
		if (Var.tk1.isFixedCycleProgram()) {
			if (offset == cycle) {
				offset = 0;
			} else if (offset > cycle && cycle > 0) {
				offset = offset % cycle;
			}
		}

		if (isSynced && (getProgZeit() == offset * Var.ONE_SEC)) {
			setProgSek(offset);
			waitForSyTime = 0;
			return true;
		} else {
			if (getProgZeit() != offset * Var.ONE_SEC) {
				return false;
			}
			
			maxWaitForSyDuration = ((int)(cycle / 3)) * Var.ONE_SEC;
			if (maxWaitForSyDuration % Var.ONE_SEC != 0)
				maxWaitForSyDuration += getZyklDauer();
			
			if (waitForSyTime >= maxWaitForSyDuration) 
			{
				waitForSyTime = 0;
				return true;
			}
			
			if (waitForSyTime < 999000)
				waitForSyTime += getZyklDauer();
			
			FreezeProgramCycle();
			return false;
		}
	}
	
	public boolean isFixedCycleProgram() 
	{
		return getAktProg().getUmlaufZeit() != 900 && getAktProg().getUmlaufZeit() > 0;
	}
	
	private boolean isNoGwNoFixed = true;
    private boolean isNoGwFixed = false;
    private boolean isGwFixed = false;
    private boolean isGwNoFixed = false;
    
    public void SetNoGwNoFixed() {
    	isNoGwNoFixed = true;
    	isNoGwFixed = false; 
    	isGwFixed = false;   
    	isGwNoFixed = false; 
    }
    
    public void SetNoGwFixed() {
    	isNoGwNoFixed = false;
    	isNoGwFixed = true; 
    	isGwFixed = false;   
    	isGwNoFixed = false; 
    }
    
    public void SetGwFixed() {
    	isNoGwNoFixed = false;
    	isNoGwFixed = false; 
    	isGwFixed = true;   
    	isGwNoFixed = false; 
    }
    
    public void SetGwNoFixed() {
    	isNoGwNoFixed = false;
    	isNoGwFixed = false; 
    	isGwFixed = false;   
    	isGwNoFixed = true; 
    }
    
    public boolean IsNoGwNoFixed() {
    	return isNoGwNoFixed; 
    }
    
    public boolean IsNoGwFixed() {
    	return isNoGwFixed; 
    }
    
    public boolean IsGwFixed() {
    	return isGwFixed;   
    }
    
    public boolean IsGwNoFixed() {
    	return isGwNoFixed; 
    }
}
