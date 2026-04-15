package core;

import m0547.Var;
import sg.Zustand;
import vt.*;

abstract public class Stage extends Phase {
	protected Node node;
	
	protected int phLen;
	protected boolean isStopInPolice;
	protected Move[] sgs;
	protected int spNumber = Var.NONE;

	protected boolean gaps = false;
	protected boolean isStageDoneFlag = false;
	protected boolean isVirtual = false;
	
	protected int lastExtension = 0;

	public boolean isLRT;
    public boolean isLIG;
	public boolean isCompatible;
	public boolean isInserted;
	public int GGTGroup;
	public int GGTStage;

	private int _iterator;

	protected int longestPhLen = phLen;

	/**
	 * This constructor should only be used to initialize a special type of stage - the stage that's used during interstages!
	 * Avoid using this constructor for any other reason!
	 * @param tk - the node that this stage belongs to
	 * @param name - the name of this stage
	 * @param nr - the number of this stage
	 */
	protected Stage(Node node, String name, int nr) {
	   super(node, name, nr);
	   this.node = node;
	}
	
	/**
	 * 
	 * @param node
	 * @param name
	 * @param nr
	 * @param len
	 * @param isStopInPolice
	 * @param sgs
	 */
	public Stage(Node node, String name, int nr, int len, boolean isStopInPolice, Move[] sgs) {
		super(node, name, nr);
		phLen = len;
		this.node = node;
		this.isStopInPolice = isStopInPolice;
		this.sgs = sgs;
		Controller.stagesAndInterstagesCounter++;
	}
	
	public Stage setVirtual() {
	    this.isVirtual = true;
	    return this;
	}
	
	public Stage setSpNumber(int sp) {
	    this.spNumber = sp;
	    return this;
	}

    abstract public int getMinType();
    abstract public int getMaxType();
    abstract public int getMinExt();
    abstract public int getMaxExt();
    abstract protected void deactivated(Phase phase);
    abstract protected void activated();
    abstract protected void beforeSkeletonDone();

	protected void stopInPolice() {
	    if (!Var.controller.isPoliceDoor()) {
	        return;
	    }
	    
		if (!isStopInPolice) {
			return;
		}

		Var.controller.policePanel.setAdvanced(node, this, node.getLenPhue() + getPhLen());
	}

	protected boolean isDxWait()
	{
	    if (node.isUseDx && !node.isDxDone && !node.isDx() && node.getAktProg().getNummer() == node.getProgAnf().getProg().getNummer()) {
            freezeTime();
            node.freezeTime();
            return true;
        }
        node.isDxDone = true;
        return false;
	}
	
	/********************************************************** BELOW ********************************************************************************/

    /**
     * Gets the stage's skeleton length (in seconds)
     * @return  returns the stage's skeleton length (minimal required time) (in seconds)
     */
    public int getPhLen(){
        return phLen;
    }

    /**
     * Gets the stage's skeleton length (in milliseconds)
     * @return  returns the stage's skeleton length (minimal required time) (in milliseconds)
     */
    public int getPhLenMS() {
        return phLen * Var.ONE_SEC_MS;
    }
	
    /**
     * Returns the stage time since it got activated
     * @return returns the stage's "green time" - the time since the stage was activated
     */
    public int GT() {
        return getPhasenSek() - node.getLenPhue();
    }

    /**
     * Returns the stage time (in seconds) since it got activated
     * @return returns the stage's "green time" - the time (in seconds) since the stage was activated
     */
    public int getCurrStageDuration() {
        return getPhasenSek() - node.getLenPhue();
    }

    /**
     * Returns the stage time (in milliseconds) since it got activated
     * @return returns the stage's "green time" - the time (in milliseconds) since the stage was activated
     */
    public int getCurrStageDurationMS() {
        return getPhasenZeit() - node.getLenPhueMS();
    }

    /**
     * Returns the program cycle time (in seconds) of this stage's node
     * @return the program cycle time (in seconds) of this stage's node
     */
    public int getCurrCycleSec() {
        return node.getProgSek();
    }

    /**
     * Returns the program cycle time (in milliseconds) of this stage's node
     * @return the program cycle time (in milliseconds) of this stage's node
     */
    public int getCurrCycleSecMS() {
        return node.getProgZeit();
    }

    /**
     * Checks whether all the moves of the stage have been green longer than a given parameter
     * @param time - the time required (in seconds) for the moves to be green
     * @return returns true when all moves have been green for at least time seconds
     */
    public boolean isGtEllapsed(int time) {
        return isGtEllapsedMS(time * Var.ONE_SEC_MS);
    }

    /**
     * Checks whether all the moves of the stage have been green longer than a given parameter
     * @param timeMS - the time required (in milliseconds) for the moves to be green
     * @return returns true when all moves have been green for at least timeMS milliseconds
     */
    public boolean isGtEllapsedMS(int timeMS) {
        for (_iterator = 0; _iterator < sgs.length; _iterator++) {
            if (sgs[_iterator].getZustand() != Zustand.GRUEN || !sgs[_iterator].greencheck(timeMS)) {
                return false;
            }
        }
        return true;
    }
	
	public boolean isDone() {
	    return isDone(true);
	}
	
	public boolean isDone(boolean isExtensionActive) {
	    if (node.getLenPhue() < InterStage.PHUE_MAX_LENGTH) {
	        GGTStage = GT();
	    }
        
        if (getPhasenZeit() == node.getLenPhueMS()) {
            activated();
        }
	    if (!node.isFullSecond()) {
	        return false;
	    }
        
        stopInPolice();
        
        beforeSkeletonDone();
	    
	    if (getPhasenSek() < node.getLenPhue() + getPhLen()) {
	        return false;
	    }
        
        if (node.isInPolice()) {
            return true;
        }
        
        if (Var.controller.central != null && Var.controller.central.isFire() && Var.controller.central.getFire() == getNummer()) {
            freezeTime();
            node.freezeTime();
            return false;
        }
	    
	    if (isMinimumDone()) {
	        if (!isExtensionActive || isMaximumDone()) {
	            return true;
	        }
	    }
	    return false;
	}
    
    public boolean isDoneVirtual(boolean isExtensionActive) {
        node.lenPhue = 0;
        return isDone(isExtensionActive);
    }
    
    public boolean isMinimumDone() {
        if (Var.hand_active) {
            return true;
        }
        
        int type = getMinType();
        
        if (Var.controller.parameters.isAbsolute(type)) {
            if (node.isFixedCycleProgram()) {
                return node.getProgSek() == getMinExt();
            } else {
                return node.getProgSek() >= getMinExt();
            }
        }
        
        if (Var.controller.parameters.isComplement(type)) {
            return GT() >= getMinExt() - node.getPrevStage().getLastExtension() + ((Var.controller.parameters.isIncludePhLen() || isVirtual) ? 0 : getPhLen());
        }
        
        // Duration or unknown extension type
        return GT() >= getMinExt() + (Var.controller.parameters.isIncludePhLen() ? 0 : getPhLen());
    }
    
    public boolean isMaximumDone() {
        if (Var.hand_active) {
            return true;
        }
        
        int type = getMaxType();
        
        if (Var.controller.parameters.isAbsolute(type)) {
            if (node.isFixedCycleProgram()) {
                return node.getProgSek() == getMaxExt();
            } else {
                return node.getProgSek() >= getMaxExt();
            }
        }
        
        if (Var.controller.parameters.isComplement(type)) {
            return GT() >= getMaxExt() - node.getPrevStage().getLastExtension() + ((Var.controller.parameters.isIncludePhLen() || isVirtual) ? 0 : getPhLen());
        }
        
        if ((type = Var.controller.parameters.isByMove(type)) > 0) {
            if (type <= node.movesCount) {
                Move move = node.moves.get(type - 1);
                if (move.isTraffic() || move.isPedestrian()) {
                    return move.GT() >= getMaxExt();
                }
            }
        }
        
        // Duration or unknown extension type
        return GT() >= getMaxExt() + (Var.controller.parameters.isIncludePhLen() ? 0 : getPhLen());
    }
    
    protected int getLastExtension() {
        return lastExtension;
    }
    
    /**
     * 
     */
    public void freezeTime() {
        if (node.isStageFrozen) {
            return;
        }
        
        node.isStageFrozen = true;
        
        if (getPhasenZeit() > 0) {
            setPhasenZeit(getPhasenZeit() - node.getZyklDauer());
        }
    }

    /**
     * 
     * @param phase
     * @return
     */
    public Stage restartPhase(Stage phase) {
        node.setProgZeit(0);
        return phase;
    }

    protected void baseDeactivated(InterStage interstage, Phase targetStage) {
        deactivated(targetStage);
        node.prevStage = this;
        lastExtension = getPhasenSek() - getPhLen() - node.getLenPhue();
        
        // TODO: finish preemption related logic
        
//      if (Var.controller.isAppJerusalem() && Var.controller.isPreemption()) {
//            if (((PreemptionJLRT)node.preempJ) != null && isLIG && !((PreemptionJLRT)node.preempJ).checkLIG1() ){ 
//                ((PreemptionJLRT)node.preempJ).resetLIG1();
//            }
//        }

//      if (Var.controller.isAppHaifa()&& Var.controller.isPreemption() && !node.isOffline() && node.isRegularProgram() && node.preempH != null) {
//          node.preempH.updateDetMem();
//      }
    }
    
    public Phase switchStage(InterStage interstage, int skNumber) {
        node.sk_number = skNumber;
        return switchStage(interstage);
    }
    
    /**
     * 
     * @param interstage
     * @return
     */
    public Phase switchStage(InterStage interstage) {
        if (!interstage.getZielPhase().isInPhasenListe()) {
            baseDeactivated(interstage, interstage.getZielPhase());

            node.isInInterstage = true;
            node.activeStageOrInterstageNumber = interstage.localNumber;
            node.lenPhue = ((Stage)interstage.getZielPhase()).isVirtual ? 0 : InterStage.PHUE_MAX_LENGTH;
            node.currStage = ((Stage)interstage.getZielPhase());
            startPhase(interstage);
            return interstage.getZielPhase();   
        } else {
            return KEINE_UMSCHALTUNG;
        }
    }
    
    public Phase switchStage(Stage stage, int skNumber) {
        node.sk_number = skNumber;
        return switchStage(stage);
    }
    
    /**
     * 
     * @param stage
     * @return
     */
    public Phase switchStage(Stage stage) {
        if (!stage.isInPhasenListe() || (stage.getTeilKnoten().getNummer() == getTeilKnoten().getNummer() && stage.getNummer() == getNummer())) {
            baseDeactivated(null, stage);

            node.isInInterstage = false;
            node.activeStageOrInterstageNumber = stage.getNummer();
            node.lenPhue = 0;
            node.currStage = stage;
            return stage;
        } else {
            return KEINE_UMSCHALTUNG;
        }
    }

    /**
     * Checks whether a certain move is defined to be in this stage
     * @param sg - the move to be checked
     * @return true if the requested move is defined to be part of this stage. returns false otherwise.
     */
    public boolean isSgInStage(Move sg) {
        for (_iterator = 0; _iterator < sgs.length; _iterator++) {
            if (sgs[_iterator].getNr() == sg.getNr()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return
     */
    public boolean isStageBuilt()
    {
        for (_iterator = 0; _iterator < sgs.length; _iterator++) {
            if (node.isMapOnlyProgram()) {
                if (sgs[_iterator].isTraffic() || sgs[_iterator].isBlinker()) {
                    if (sgs[_iterator].getZustand() != Zustand.GELBBLINKEN) {
                        return false;
                    }
                } else if (sgs[_iterator].isPedestrian()) {
                    if (sgs[_iterator].getZustand() != Zustand.DUNKEL) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (sgs[_iterator].isTraffic() || sgs[_iterator].isPedestrian()) {
                    if (sgs[_iterator].getZustand() != Zustand.GRUEN) {
                        return false;
                    }
                } else if (sgs[_iterator].isBlinker()) {
                    if (sgs[_iterator].getZustand() != Zustand.GELBBLINKEN) {
                        return false;
                    }
                } else  {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the current stage has been built.
     * Meaning, this methods checks whether all the moves that have been defined in this stage are
     * in their active states
     * @return returns true if all the defined moves are active
     */
    public boolean isStageGreenBuilt()
    {
        for (_iterator = 0; _iterator < sgs.length; _iterator++) {
            if (sgs[_iterator].isTraffic() || sgs[_iterator].isPedestrian()) {
                if (sgs[_iterator].getZustand() != Zustand.GRUEN) {
                    return false;
                }
            } else if (sgs[_iterator].isBlinker()) {
                if (sgs[_iterator].getZustand() != Zustand.GELBBLINKEN) {
                    return false;
                }
            } else  {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether this stage was activated during the current traffic plan cycle
     * @return true if this stage was active during the current traffic plan cycle
     */
    public boolean wasActive()
    {
        return node.wasStageActive(this);
    }
}
