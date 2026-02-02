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
import enums.AppType;
import special.Node.SpecialProgram;
import ta172.GreenWave;
import ta172.ParamSet;
import ta172.ParametersJerusalem;
import ta172.ParametersTelAviv;
import ta172.Var;
import vt.*;

abstract public class MainStage extends Stage {
	private Node node;
	private Phase nextPhase;
	private Stage outOfMapOnly;

	protected int[] startLengths;
	protected int[] endLengths;

	public boolean isStartCycleFunction;
	private boolean isCycleStart;

	private int structureNumber = 0;
	private int oldStructureNumber = 0;

	private int requiredFreezeDuration = 0;
	private boolean programChanged = false;

	public MainStage(Node node, String name, int number, int[] startLengths, int[] endLengths, boolean isStopInPolice, Move[] moves) {
		super(node, name, number, startLengths[0], 0, isStopInPolice, moves);
		this.node = node;
		this.startLengths = startLengths;
		this.endLengths = endLengths;
		if (Var.controller.isMaintenance()) {
			outOfMapOnly = new OutOfMapOnlyStage(node, moves); 
		}
	}

	public final int PC_NONE = Preemption.CT_NONE;

	protected void stopInPolice() {
		if (!isStopInPolice) {
			return;
		}

		Tableau.setAdvanced(node, this, node.lenPhue + getPhLenMS()); //for stopping in non-fixed police
	}

	abstract public Phase phasenFunktionCycleStart();
	abstract public Phase phasenFunktionCycleEnd();
	abstract public int getMinExt();
	abstract public int getMaxExt();
	abstract protected void deactivated();
	abstract protected void startOfCycleEvent();
	abstract protected void endOfCycleEvent();

	public int getStructureNumber() {
		if (!node.isRegularProgram() && !node.isInMaintenance()) {
			if (Var.controller.getPoliceStructure() == 0) {
				return structureNumber;
			} else {
				return Var.controller.getPoliceStructure();
			}
		}

		if (Var.controller.getAppType().equals(AppType.Haifa)) {
			structureNumber = ParamSet.structure;
		} else if (Var.controller.getAppType().equals(AppType.Jerusalem)) {
			structureNumber = ParamSet.structure;
		} else if (Var.controller.getAppType().equals(AppType.TelAviv)) {
			structureNumber = ((ParametersTelAviv)Var.controller.dvi35Parameters).getStructure();
		} else {
			structureNumber = Func.getStructureParam(node.getAktProg().getNummer() - (node.isInMaintenance() ? (SpecialProgram.MaintenanceFirst.getProgramNumber() - 1) : 0));
		}

		if (structureNumber < 1 || structureNumber > startLengths.length || structureNumber > endLengths.length) {
			structureNumber = 1;
		}

		return structureNumber;
	}

	private void updatePhLen() {
		structureNumber = getStructureNumber();
		if (structureNumber != oldStructureNumber) {
			node.firstCycleActions();
		}
		if (isStartCycleFunction) {
			phLen = startLengths[structureNumber - 1];
		} else {
			phLen = endLengths[structureNumber - 1];
		}
		oldStructureNumber = structureNumber;
	}

	public void ResetCounters() {
		phasenZeit = 0;
		isStartCycleFunction = true;
		isCycleStart = true;
		requiredFreezeDuration = 0;
		programChanged = false;
		node.lenPhue = 0;
		node.isWaitingForSy = false;
		node.waitForSyDuration = 0;
		node.startmark = true;
	}

	private void startOfCycleEventBase() {
		node.startOfCycleEvent();

		startOfCycleEvent();
	}

	private void endOfCycleEventBase() {
		node.endOfCycleEvent();
		endOfCycleEvent();
	}

	protected int phasenZeit;

	public Phase phasenFunktion()
	{
		programChanged = (node.getAktProg().getNummer() != node.lastActiveProgram);
		if (programChanged) {
			ResetCounters();
		}

		updatePhLen();

		if (getPhasenZeit() != phasenZeit && node.getAktProg().getNummer() != SpecialProgram.Police.getProgramNumber()) {
			setPhasenZeit(phasenZeit);
		}

		if (isStartCycleFunction) {

			if (isCycleStart) {
				startOfCycleEventBase();
				isCycleStart = false;
			}

			if (Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv()) {
				if (programChanged) {
					if (!isGtEllapsed(getPhLenMS())) {
						requiredFreezeDuration = getPhLenMS();
					} else {
						requiredFreezeDuration = 0;
					}
				}

				if (requiredFreezeDuration > 0) {
					node.FreezeProgramCycle();
					requiredFreezeDuration -= node.getZyklDauer();
					phasenZeit += node.getZyklDauer();
					return KEINE_UMSCHALTUNG;
				}
			}

			if (node.getProgZeit() >= 2 * Var.ONE_SEC) {
				Node.ResetSync(node.getNummer());
			}

			if (node.isInMaintenance() && !node.isTkInFlash() && node.isMaintenanceProgramRequest()) {
				if (node.timeToStartMapOnly < 0) {
					node.timeToStartMapOnly = getPhasenSek();//node.getProgSek();
				}
				node.setMapOnly();
			} else {
				node.timeToStartMapOnly = -1;
			}
			nextPhase = phasenFunktionCycleStart();
			if (nextPhase != KEINE_UMSCHALTUNG) {
				isStartCycleFunction = false;
			}
		} else {
			if (node.isInMaintenance() && node.isTkInFlash() && !node.isMaintenanceProgramRequest()) {
				if (node.timeToStartMapOnly < 0) {
					node.timeToStartMapOnly = 0;
				}
				node.setExitMapOnly();
				phasenZeit = 0;
				return outOfMapOnly;
			}
			nextPhase = phasenFunktionCycleEnd();
			if (nextPhase != KEINE_UMSCHALTUNG) {
				endOfCycleEventBase();
				isStartCycleFunction = true;
				isCycleStart = true;
			}
		}

		if (nextPhase != KEINE_UMSCHALTUNG) {
			node.timeToStartMapOnly = -1;
			if (nextPhase.getNummer() != getNummer()) {
				phasenZeit = 0;
			} else {
				phasenZeit += node.getZyklDauer();
			}
		} else {
			phasenZeit += node.getZyklDauer();
		}
		return nextPhase;
	}

	protected void baseDeactivated() {
		deactivated();
		if (Var.controller.isAppHaifa()&& Var.controller.isPreemption() && !node.isOffline() && node.isRegularProgram() && node.preemp != null) {
			node.preemp.updateDetMem();
		}
	}

	private boolean isGreenWaveDone() {
		if (Var.controller.isAppHaifa()) {
			return Dvi35Haifa.checkExitFromA();
		} else if (Var.controller.isAppJerusalem()) {
			return ((Dvi35Jerusalem)Var.controller.dvi35).checkExitFromA();
		} else if (Var.controller.isAppTelAviv()) {
			return ((Dvi35TelAviv)Var.controller.dvi35).checkExitFromA(node);
		} else {
			return GreenWave.checkExitFromA_GW();
		}
	}

	/**
	 * Checks whether a stage finished its skeleton time (interstage time + phLen time)
	 * @return
	 */
	protected boolean isSkeletonDone() {
		return (getPhasenZeit() >= node.lenPhue + getPhLenMS());
	}

	/**
	 * Checks whether the stage finished first cycle extension if required
	 * @return
	 */
	protected boolean isFirstCycleExtensionDone() {
		return (!node.startmark || (GT() >= getPhLenMS() + node.firstCycleExt));
	}

	public boolean isStartStageDoneBase() {
		if (!isSkeletonDone()) {
			if (node.startmark)
				node.FreezeProgramCycle();
			return false;
		}

		if (!isFirstCycleExtensionDone()) {
			node.FreezeProgramCycle();
			return false;
		}

		return true;
	}

	private void SetFlagsAfterStartStageDone() {
		node.isWaitingForSy = false;
		node.startmark = false;
		if (node.timeToStartMapOnly >= 0) {
			node.FreezeProgramCycle();
			return;
		}
		node.updateStageDuration(this);
	}

	/**
	 * Method handling end of main stage (of the start of cycle, before starting any interstages)
	 * Handles the case where the structure is:
	 * 1. Not working in green-wave
	 * 2. Not a fixed cycle
	 * @return
	 */
	public boolean isStartStageDoneNoGwNonFixed() {
		node.SetNoGwNoFixed();

		if (!isStartStageDoneBase())
			return false;

		SetFlagsAfterStartStageDone();
		return true;
	}

	/**
	 * Method handling end of main stage (of the start of cycle, before starting any interstages)
	 * Handles the case where the structure is:
	 * 1. Not working in green-wave
	 * 2. Fixed cycle
	 * @return
	 */
	public boolean isStartStageDoneNoGwFixed() {
		node.SetNoGwFixed();

		if (!isStartStageDoneBase())
			return false;

		if (node.getProgZeit() == ((ParametersJerusalem)Var.controller.dvi35Parameters).getOffset() * Var.ONE_SEC){
			SetFlagsAfterStartStageDone();
			return true;
		}

		return false;
	}

	/**
	 * Method handling end of main stage (of the start of cycle, before starting any interstages)
	 * Handles the case where the structure is:
	 * 1. Green-wave
	 * 2. Fixed cycle
	 * @return
	 */
	public boolean isStartStageDoneGwFixed() {
		node.SetGwFixed();

		if (!isStartStageDoneBase())
			return false;
		if (node.isClockGreenWaveSynced()) {
			SetFlagsAfterStartStageDone();
			return true;
		}
		return false;
	}
	
	private int waitCounter = 0;
	public boolean isStartStageDoneTA() {
		if (!isStartStageDoneBase())
			return false; 
		
		if (isMinimumDone() || (node.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE && ((Dvi35TelAviv)Var.controller.dvi35).isSy(node))) {
			
			if (isMaximumDone() || (node.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE && (((Dvi35TelAviv)Var.controller.dvi35).isSy(node)))) {
				if (((Dvi35TelAviv)Var.controller.dvi35).checkExitFromA(node) || waitCounter >= ((int)Math.ceil(((ParametersTelAviv)Var.controller.dvi35Parameters).getCycle() / 3.0)) * Var.ONE_SEC || !Var.controller.isClockSync() || Var.hand_active) {
					waitCounter = 0;
					SetFlagsAfterStartStageDone();
					if (node.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE && !Var.hand_active &&
						((ParametersTelAviv)Var.controller.dvi35Parameters).getCycle() > 0 &&
						((ParametersTelAviv)Var.controller.dvi35Parameters).getCycle() < 255) {
						node.setProgZeit(((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(spNum));
					}
					return true;
				} else {
					node.FreezeProgramCycle();
					waitCounter += node.getZyklDauer();
				}
			}
		}
		
		return false;
	}

	// TODO: none fixed + gw (breathing gw)


	public boolean isEndStageDone() {
		if (getPhasenZeit() >= node.lenPhue + getPhLenMS())
		{
			readStageParameters();

			if (node.isExitConditionsDone()) {
				if (!node.isFixedCycleProgram()) {
					node.setProgZeit(0);
				}
				setPhasenZeit(0);
				phasenZeit = 0;
				return true;
			}
		}

		return false;
	}

	protected boolean isDxWait()
	{
		if (node.useDx && node.dem_dx && !node.dx_done)
		{
			if (!Var.hand_active && node.getAktProg().getNummer() == node.getProgAnf().getProg().getNummer()) {
				node.FreezeProgramCycle();
				if (this.getPhasenZeit() > 0) {
					this.setPhasenZeit(this.getPhasenZeit() - node.getZyklDauer());
				} else {
					this.setPhasenZeit(0);
				}
				return true;
			}
		}
		node.dx_done = true;
		return false;
	}
}
