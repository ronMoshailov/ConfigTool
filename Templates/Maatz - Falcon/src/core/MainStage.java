package core;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 8                                                                      	*
 *  Junction Name  : Hertzel / Bar Kohva / Bilu													*
 *  Equipmentno.   : h8                                                                     	*
 *                                                                                              *
 ************************************************************************************************/

import m0547.Var;
import vt.*;

abstract public class MainStage extends Stage {
	private Phase nextPhase;
	private Stage outOfMapOnly;

	protected int[] startLengths;
	protected int[] endLengths;

	public static boolean isStartCycleFunction;
	private static boolean isCycleStart;

	private int requiredFreezeDuration = 0;
	private boolean programChanged = false;
	   //                      1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18
    protected int A_Len [] ;//= {0,11, 8, 1,10, 1,10,10,10, 1,10, 1,10, 1,10,10,10,11,14};
	public boolean getIsCycleStart() {
		return isStartCycleFunction;
	}

	public MainStage(Node node, String name, int number, int[] startLengths, int[] endLengths, boolean isStopInPolice, Move[] moves) {
		super(node, name, number, startLengths[0], isStopInPolice, moves);
		this.node = node;
		this.startLengths = startLengths;
		this.endLengths = endLengths;
		if (Var.controller.isMaintenance()) {
			outOfMapOnly = new OutOfMapOnlyStage(node, moves); 
		}
	}
    
    public MainStage setSpNumber(int sp) {
        super.setSpNumber(sp);
        return this;
    }

	abstract public Phase phasenFunktionCycleStart();
	abstract public Phase phasenFunktionCycleEnd();
	abstract protected void startOfCycleEvent();
	abstract protected void endOfCycleEvent();
	abstract protected void beforeSkeletonDoneCycleStart();
	abstract protected void beforeSkeletonDoneCycleEnd();

    public abstract int getMinTypeCycleStart();
    public abstract int getMaxTypeCycleStart();
    public abstract int getMinExtCycleStart();
    public abstract int getMaxExtCycleStart();
    public abstract int getMinTypeCycleEnd();
    public abstract int getMaxTypeCycleEnd();
    public abstract int getMinExtCycleEnd();
    public abstract int getMaxExtCycleEnd();


    public int getMinType() {
        return isStartCycleFunction ? getMinTypeCycleStart() : getMinTypeCycleEnd();
    }
    
    public int getMaxType() {
        return isStartCycleFunction ? getMaxTypeCycleStart() : getMaxTypeCycleEnd();
    }
    
    public int getMinExt() {
        return isStartCycleFunction ? getMinExtCycleStart() : getMinExtCycleEnd();
    }

    public int getMaxExt() {
        return isStartCycleFunction ? getMaxExtCycleStart() : getMaxExtCycleEnd();
    }
    
    public boolean isStartCycleFunction() {
        return isStartCycleFunction;
    }

	private void updatePhLen() {
	    int structIndex;
	    if (node.currStructure < 1 || node.currStructure > Math.min(startLengths.length, endLengths.length)) {
	        structIndex = 1;
	    } else {
	        structIndex = node.currStructure;
	    }
	    
		if (isStartCycleFunction) {
			phLen = startLengths[structIndex - 1];
		} else {
			phLen = endLengths[structIndex - 1];
			if (A_Len != null && node.sk_number > 0 && node.sk_number <= A_Len.length) {
			    phLen += A_Len[node.sk_number - 1];
			}
		}
	}

	public void ResetCounters() {
//		phasenZeit = 0;
		isStartCycleFunction = true;
		isCycleStart = true;
		requiredFreezeDuration = 0;
		programChanged = false;
		node.isWaitingForSy = false;
	}
	
	private void startOfCycleEventBase() {
		node.startOfCycleEvent();
	}

	private void endOfCycleEventBase() {
	    // TODO: update preemption logic
	    
//		if (node.preempJ != null && node.preempJ instanceof PreemptionJLRT) {
//			((PreemptionJLRT)node.preempJ).memCat = node.getAktProg().getNummer() == SpecialProgram.Catastrophe.getProgramNumber();
//			if (((PreemptionJLRT)node.preempJ).switchProgram && node.MainPhase[0].isAktiv() && (node.getAktStgEbene() != StgEbene.STG_NUM_MANUELL)) {
//				node.CycleCount = 0;
//			}
//		}
		node.endOfCycleEvent();
		endOfCycleEvent();
	}
	
//	protected int phasenZeit;
//	public static boolean forceMainStageSwitch;

    /**
     * This method is called before the skeleton time of the stage is done (and during it too)
     */
    protected void beforeSkeletonDone() {
        
    }
	
	public Phase phasenFunktion()
	{
		programChanged = (node.getAktProg().getNummer() != node.lastActiveProgram);
		if (programChanged) {
			ResetCounters();
		}
		
		updatePhLen();
		
//		if (getPhasenZeit() != phasenZeit) {
//			setPhasenZeit(phasenZeit);
//		}
		
		if (isStartCycleFunction) {
			if (isCycleStart) {
				startOfCycleEventBase();
				isCycleStart = false;
			}

//			if (node.lastActiveProgram != node.getAktProg().getNummer()) {
//				if ((node.lastActiveProgram >= 1 && node.lastActiveProgram <= Var.controller.getMaxProgramsNumber()) ||
//					(node.lastActiveProgram >= SpecialProgram.MaintenanceFirst.getProgramNumber() && node.lastActiveProgram <= SpecialProgram.MaintenanceFirst.getProgramNumber() + Var.controller.getMaxProgramsNumber() - 1)) {
//					if (node.getPrevStage().getNummer() != getNummer()) {
//						forceMainStageSwitch = true;
//						return node.getPrevStage();
//					} else {
//						forceMainStageSwitch = false;
//					}
//				} else {
//					forceMainStageSwitch = false;
//				}
//			} else {
//				forceMainStageSwitch = false;
//			}
//			
//			if (forceMainStageSwitch) {
//				if (getPhasenSek() >= 1) {
//					if (getPhasenZeit() != node.getProgZeit()) {
//						setPhasenZeit(node.getProgZeit());
//					} else {
//						forceMainStageSwitch = false;
//					}
//				}
//			}
			
			if (Var.controller.isAppJerusalem()) {
				if (programChanged) {
					if (!isGtEllapsed(getPhLenMS())) {
						requiredFreezeDuration = getPhLenMS();
					} else {
						requiredFreezeDuration = 0;
					}
				}
				
				if (requiredFreezeDuration > 0) {
					node.freezeTime();
					requiredFreezeDuration -= node.getZyklDauer();
//					phasenZeit += node.getZyklDauer();
					return KEINE_UMSCHALTUNG;
				}
			}
			
			if (node.isMapOnlyProgram() && !node.isTkInFlash() && node.isMaintenanceProgramRequest()) {
				if (node.timeToStartMapOnly < 0) {
					node.timeToStartMapOnly = getPhasenSek();//node.getProgSek();
				}
				node.setMapOnly();
			} else {
				node.timeToStartMapOnly = -1;
			}
			
	        if (Var.controller.central != null && Var.controller.central.isFire() && Var.controller.central.getFire() == getNummer()) {
	            freezeTime();
	            node.freezeTime();
	            return KEINE_UMSCHALTUNG;
	        }
			
			nextPhase = phasenFunktionCycleStart();
			if (nextPhase != KEINE_UMSCHALTUNG/* && !forceMainStageSwitch*/) {
				isStartCycleFunction = false;
			}
		} else {
			if (node.isMapOnlyProgram() && node.isTkInFlash() && !node.isMaintenanceProgramRequest()) {
				if (node.timeToStartMapOnly < 0) {
					node.timeToStartMapOnly = 0;
				}
				node.setExitMapOnly();
//				phasenZeit = 0;
				return outOfMapOnly;
			}
			nextPhase = phasenFunktionCycleEnd();
			if (nextPhase != KEINE_UMSCHALTUNG) {
				endOfCycleEventBase();
				isStartCycleFunction = true;
				isCycleStart = true;
				node.prevStage = this;
			}
		}
		
		if (nextPhase != KEINE_UMSCHALTUNG) {
			node.timeToStartMapOnly = -1;
			if (nextPhase.getNummer() == getNummer()) {
			    if (isStartCycleFunction) {
			        node.setProgZeit(0);
			    }
                setPhasenZeit(0);
			    nextPhase = KEINE_UMSCHALTUNG;
			}
		}
		return nextPhase;
	}

	public boolean isDoneCycleStart() {
	    return isDoneCycleStart(true);
	}
	
	public boolean isDoneCycleStart(boolean isExtensionActive) {
	    if (!node.isFullSecond()) {
            return false;
        }
	    
	    beforeSkeletonDoneCycleStart();
        stopInPolice();
        
	    if (getPhasenSek() < node.getLenPhue() + getPhLen()) {
	        return false;
	    }

	    if (node.startmark) {
	        if (getPhasenSek() < getPhLen() + node.firstCycleExt) {
	            node.freezeTime();
	            return false;
	        }
	        node.firstCycleActions();
	        node.startmark = false;
	    }
        
        if (isMinimumDone()) {
            if (!isExtensionActive || isMaximumDone()) {
                return true;
            }
        }
        
        return false;
	}
    
    public boolean isDoneCycleStartDxE(boolean isExtensionActive) {
        if (!node.isFullSecond()) {
            return false;
        }
        
        if (getPhasenSek() < node.getLenPhue() + getPhLen()) { 
            return false;
        }
        
        if (isDxWait()) {
            return false;
        }
        
        return isDoneCycleStart(isExtensionActive);
    }
    
    public boolean isDoneCycleStartEDx(boolean isExtensionActive) {
        if (isDoneCycleStart(isExtensionActive)) {
            return !isDxWait();
        }
        
        return false;
    }
    
    public boolean isDoneCycleEnd() {
        return isDoneCycleEnd(true);
    }
    
    public boolean isDoneCycleEnd(boolean isExtensionActive) {
        if (!node.isFullSecond()) {
            return false;
        }
        
        beforeSkeletonDoneCycleEnd();
        
        if (getPhasenSek() < node.getLenPhue() + getPhLen()) {
            return false;
        }
        
        if (!node.isExitConditionsDone()) {
            return false;
        }

        if (isMinimumDone()) {
            if (!isExtensionActive || isMaximumDone()) {
                resetStagesCounters();
                activated();
                if (node.preemption != null) {
                    node.preemption.startOfCycleActions();
                    node.preemption.resetStagesCounters();
                }
                return true;
            }
        }
        return false;
    }
    
    private void resetStagesCounters() {
        
    }
}
