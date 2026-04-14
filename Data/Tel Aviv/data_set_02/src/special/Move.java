package special;

import lmp.LmpBlinkOhneUeb;
import lmp.LmpBlinkUge;
import lmp.LmpEinfachUga;
import lmp.LmpTyp;
import sg.Sg;
import sg.SgTyp;
import sg.Zustand;
import sg.typen.Blinker;
import sg.typen.RoGeGn;
import sg.typen.RoGeGnGnBl;
import sg.typen.RoGn;
import tk.Var;
import vt.Phase;

public class Move extends Sg {
	public static class MoveType {
		public static final MoveType BRT        		 = new MoveType(0); // Bus Rapid Transit
		public static final MoveType LRT                 = new MoveType(1); // Light Rail Train
		public static final MoveType Traffic             = new MoveType(2); // Traffic without flashing green
		public static final MoveType Traffic_Flashing    = new MoveType(3); // Traffic with flashing green
		public static final MoveType Pedestrian          = new MoveType(4); // Pedestrian
		public static final MoveType Blinker_Conditional = new MoveType(5); // Conditional blinker
		public static final MoveType Blinker             = new MoveType(6); // Blinker
		
		private int id;
		
		public MoveType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}

	//types of lamps
	public static LmpTyp lred   = new LmpEinfachUga("Red", LmpTyp.FARBE_ROT);
	public static LmpTyp lamber = new LmpBlinkUge("Amber", LmpTyp.FARBE_GELB);
	public static LmpTyp lgreen = new LmpBlinkUge("Green", LmpTyp.FARBE_GRUEN);
	//next lamp type is for blink without UGE (foreign voltage fault)
	public static LmpTyp lamber_nuge = new LmpBlinkOhneUeb ("Amber", LmpTyp.FARBE_GELB);

	// signal group types
	//                                                                  rotgelb, gelb, dunkelgelb, gruenblink 
	public static SgTyp car     = RoGeGnGnBl.init(lred, lamber, lgreen,       2,    3,          6,          3);	// car with green blink
	public static SgTyp car_ngb = RoGeGn    .init(lred, lamber, lgreen,       2,    3,          6            );	// car without green blink
	public static SgTyp ped     = RoGn      .init(lred, lgreen, true);
	public static SgTyp blink   = Blinker   .init(lamber_nuge, false);

	private static SgTyp getSgTypByType(MoveType moveType) {
		if (moveType.equals(MoveType.BRT) || moveType.equals(MoveType.LRT) || moveType.equals(MoveType.Traffic)) {
			return car_ngb;
		} else if (moveType.equals(MoveType.Traffic_Flashing)) {
			return car;
		} else if (moveType.equals(MoveType.Pedestrian)) {
			return ped;
		} else if (moveType.equals(MoveType.Blinker) || moveType.equals(MoveType.Blinker_Conditional)) {
			return blink;
		} else {
			return car;
		}
	}

	private static int getApproachPriorityByType(MoveType moveType) {
		if (moveType.equals(MoveType.BRT                ) ||
			moveType.equals(MoveType.LRT                ) || 
			moveType.equals(MoveType.Traffic            ) ||
			moveType.equals(MoveType.Traffic_Flashing   ) ||
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

	public static int moveId = 1;

	public Node node = null;
	public MoveType moveType;
	public boolean isMainMove = false;
	public Output rm_gr = null;

	protected int stateTime;
	protected int greenStateTime;
	protected int currentState;
	protected int previousState;

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
	 */
	public Move(Node node, String name, MoveType moveType, int minGreen, int minRed, boolean isMainMove) {
		super(
				node,
				name,
				getSgTypByType(moveType),
				minGreen,
				minRed,
				getApproachPriorityByType(moveType),
				moveId++
		);

		this.node = node;
		this.moveType = moveType;
		this.isMainMove = isMainMove;

		if (isTraffic()) {
			initAbschUGA(Move.lred, "UGA 1");
		}

		if (isPedestrian()) {
			initAbschUGA(Move.lred, "Keine");
		}
		
		if (Var.controller.isSynopticMap() && isMoveRmGrOutput(moveType)) {
			rm_gr = new Output(node, "RM_Gr" + name.substring(1)); 
		}
	}

	/**
	 * Updates the state of the synoptic map's LED according to the moves green state
	 */
	private void UpdateRmGrState() {
		if (rm_gr == null) {
			return;
		}

		if (node.getAktProg() == node.getDunkelProgramm()) {
			rm_gr.resetAusgang();
		} else {
			if (hw.AnlagenZustand.isAbschaltgrad1(node) ||
					hw.AnlagenZustand.isAbschaltgrad2(node)) {
				rm_gr.resetAusgang();
			} else if (currentState == Zustand.GRUEN) {
				rm_gr.setAusgang();
			} else if (currentState == Zustand.GRUENBLINKUEB) {
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

	/**
	 * Updates the state counters of the move
	 */
	public void UpdateStateCounters() {
		currentState = getZustand();
		UpdateRmGrState();
		if (currentState == Zustand.GRUEN ||
				currentState == Zustand.GRUENBLINKUEB ||
				currentState == Zustand.GELBBLINKEN) {
			currentState = Zustand.GRUEN;
		} else if (currentState == Zustand.AUS ||
				currentState == Zustand.DUNKEL ||
				currentState == Zustand.GELB ||
				currentState == Zustand.ROT ||
				currentState == Zustand.ROTGELB) {
			currentState = Zustand.ROT;
		}

		if (currentState == previousState) {
			if (stateTime < 999000) {
				stateTime += node.getZyklDauer();
			}
		} else {
			stateTime = 0;
		}

		previousState = currentState;

		if (currentState == Zustand.GRUEN) {
			greenStateTime = stateTime;
		}
	}

	/**
	 * Gets the move's green time (in milliseconds)
	 * @return	returns the move's green time (in milliseconds) or 0 if not green
	 */
	public int GT() {
		return (currentState == Zustand.GRUEN) ? stateTime : 0; 
	}

	/**
	 * Gets the move's red time (in milliseconds)
	 * @return	returns the move's red time (in milliseconds) or 0 if not red
	 */
	public int RT() {
		return (currentState == Zustand.ROT) ? stateTime : 0; 
	}

	public boolean isTraffic() {
		if (moveType.equals(MoveType.BRT    ) ||
			moveType.equals(MoveType.LRT    ) ||
			moveType.equals(MoveType.Traffic) ||
			moveType.equals(MoveType.Traffic_Flashing)) {
			return true;
		} else if (moveType.equals(MoveType.Pedestrian) ||
				   moveType.equals(MoveType.Blinker) ||
				   moveType.equals(MoveType.Blinker_Conditional)) {
		
			return false;
		} else {
			return true;
		}
	}

	public boolean isPedestrian() {
		if (moveType.equals(MoveType.Pedestrian)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBlinker() {
		if (moveType.equals(MoveType.Blinker) ||
			moveType.equals(MoveType.Blinker_Conditional)) {
			return true;
		} else if (moveType.equals(MoveType.BRT) ||
				   moveType.equals(MoveType.LRT) ||
				   moveType.equals(MoveType.Traffic) ||
				   moveType.equals(MoveType.Traffic_Flashing) ||
				   moveType.equals(MoveType.Pedestrian)) {
			return false;
		} else {
			return false;
		}
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

	private Stage activePhase = null;

	/**
	 * Changes the move's state to its inactive state (ROT if move/pedestrian or AUS if blinker)
	 * according to minimum green time check
	 */
	public void TurnOff() {
		if (node.isInMaintenance()) {
			return;
		}
		
		activePhase = (Stage)Phase.getAktivePhase(node);
		if (activePhase == null) {
			return;
		}

		if (moveType.equals(MoveType.Blinker) ||
			moveType.equals(MoveType.Blinker_Conditional)) {
				setSg(Zustand.AUS, activePhase.getPhasenSek());
			
		} else { 
			if (isSchaltenErlaubt(Zustand.ROT)) {
				setSg(Zustand.ROT, activePhase.getPhasenSek());
			}
		}
	}

	/**
	 * Changes the move's state to its inactive state (ROT if move/pedestrian or AUS if blinker)
	 * according to given parameter (if move has flashing green, the duration of the flashing green will be REDUCED from the parameter)
	 * @param stageSec	the second of the interstage on which the move should be turned off (if has green flash, 3 seconds will be reduced from the parameter)
	 */
	public void TurnOff(int stageSec) {
		if (node.isInMaintenance()) {
			return;
		}
		
		if (moveType.equals(MoveType.Blinker) ||
			moveType.equals(MoveType.Blinker_Conditional)) {
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
	public void TurnOn() {
		if (node.isInMaintenance()) {
			return;
		}
		
		activePhase = (Stage)Phase.getAktivePhase(node);
		if (activePhase == null) {
			return;
		}

		if (moveType.equals(MoveType.Blinker) || moveType.equals(MoveType.Blinker_Conditional)) {
			setSg(Zustand.GELBBLINKEN, activePhase.getPhasenSek());
			
		} else {
			if (isSchaltenErlaubt(Zustand.GRUEN)) {
				setSg(Zustand.GRUEN, activePhase.getPhasenSek());
			}
		}
	}

	/**
	 * Changes the move's state to its active state (GRUEN if move/pedestrian or GELBBLINKEN if blinker)
	 * according to given parameter (if move has red-yellow, the duration of the red-yellow will be REDUCED from the parameter)
	 * @param stageSec	the second of the interstage on which the move should be turned on (if has red-yellow, 2 seconds will be reduced from the parameter)
	 */
	public void TurnOn(int stageSec) {
		if (node.isInMaintenance()) {
			return;
		}
		
		if (moveType.equals(MoveType.Blinker) ||
			moveType.equals(MoveType.Blinker_Conditional)) {
			setSg(Zustand.GELBBLINKEN, stageSec);
		} else if (moveType.equals(MoveType.Pedestrian)) {
			setSg(Zustand.GRUEN, stageSec);
		} else {
			if (stageSec < 2) {
				stageSec = 2;
			}
			stageSec -= 2;
			setSg(Zustand.GRUEN, stageSec);
		}
	}

	/**
	 * Compares the actual green time of a signal group with a parameter
	 * @param time: value of the parameter in milliseconds
	 * @return true, when greentime of the signal group is grater or equals the value of the parameter 
	 */
	public boolean greencheck(int time) {
		if ((getZustand() == Zustand.GRUEN) && (getZustZeit() >= time)) {
			return true;
		}
		return false;
	}
	
	public boolean activecheck(int time) {
		if ((getZustand() == Zustand.GRUEN || getZustand() == Zustand.GELBBLINKEN) && (getZustZeit() >= time)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the SG was not GREEN at least as much time as passed in "time"
	 * @param time - time (in msec) that is required to be checked
	 */
	public boolean notgreencheck(int time) {
		if (getTyp() == Move.ped) { //if type is ped
			if ((getZustand() == Zustand.ROT) && (getZustZeit() >= time)) {
				return true;
			}
		}
		else if (getTyp()== Move.car) { //if type is car with green blink
			if ((getZustand() == Zustand.GRUENBLINKUEB) && (getZustZeit() >= time)) {
				return true;
			}
			if ((getZustand() == Zustand.GELB) && (getZustZeit() + 3000 >= time)) {
				return true;
			}
			if ((getZustand() == Zustand.ROT) && (getZustZeit() + 3000 + 3000 >= time)) {
				return true;
			}
		}
		else if (getTyp() == Move.car_ngb) //if type is car without green blink
		{
			if ((getZustand() == Zustand.GELB) && (getZustZeit() >= time)) {
				return true;
			}
			if ((getZustand() == Zustand.ROT) && (getZustZeit() + 3000 >= time)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isElementDeactivated() {
		if (getTyp() == Move.ped) { //if type is ped
			if ((getZustand() == Zustand.ROT) && (getZustSek() == 0)) {
				return true;
			}
		}
		else if (getTyp()== Move.car) { //if type is car with green blink
			if ((getZustand() == Zustand.GRUENBLINKUEB) && (getZustSek() == 0)) {
				return true;
			}
			if ((getZustand() == Zustand.GELB) && (getZustSek() + 3000 == 0)) {
				return true;
			}
			if ((getZustand() == Zustand.ROT) && (getZustSek() + 3000 + 3000 == 0)) {
				return true;
			}
		}
		else if (getTyp() == Move.car_ngb) { //if type is car without green blink
			if ((getZustand() == Zustand.GELB) && (getZustSek() == 0)) {
				return true;
			}
			if ((getZustand() == Zustand.ROT) && (getZustSek() + 3000 == 0)) {
				return true;
			}
		}
		return false;
	}
}