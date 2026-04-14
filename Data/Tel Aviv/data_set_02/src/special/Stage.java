package special;

import enums.ExtensionType;
import sg.Zustand;
import ta118.ParametersTelAviv;
import tk.Var;
import vt.*;

abstract public class Stage extends Phase {
	private Node node;
	
	protected int phLen;
	protected int spNum;
	protected boolean isStopInPolice;
	protected Move[] sgs;

	protected ExtensionType minType;
	protected ExtensionType maxType;
	
	protected boolean gaps;
	protected boolean isStageDoneFlag = false;
	
	/**
	 * For applications based on stop-points, reads the minimum, maximum and type parameters of this stage's stop-point
	 */
	protected void readStageParameters() {
		if (Var.controller.isAppHaifa()) {
			// Haifa has global parameters. no special read is required
		} else if (Var.controller.isAppJerusalem()) {
			if (Var.controller.isPreemption()) {
				// Jerusalem preemption application has global parameters. no special read is required
			} else {
				// node.extension.checkMinMaxExtension(spNum);
			}
		} else {
			// node.extension.checkMinMaxExtension(spNum);
		}
	}
	
	public int getMinExt() {
		minType = getMinType();
		return ((ParametersTelAviv)Var.controller.dvi35Parameters).getMinimum(spNum);
	}
	
	public int getMaxExt() {
		maxType = getMaxType();
		return ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(spNum);
	}
	
	public ExtensionType getMinType() {
		return ExtensionType.DURATION;
	}
	
	public ExtensionType getMaxType() {
		return ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;
	}
	
	/**
	 * For applications based on global parameters (Haifa application and Jerusalem preemption applications),
	 * checks whether an extension is done
	 * @param value - the value for the extension
	 * @param type - type of the extension: duration / absolute / complement
	 * @return returns TRUE when the extension has elapsed
	 */
	protected boolean isExtDone(int value, ExtensionType type) {
		if (type.equals(ExtensionType.DURATION)) {
			return GT() >= value;
		} else if (type.equals(ExtensionType.ABSOLUTE)) {
			if (node.isRegularProgramFixedCycle()) {
				return node.getProgZeit() == value;
			} else {
				return node.getProgZeit() >= value;
			}
		} else if(type.equals(ExtensionType.COMPLEMENT)) {
			return GT() >= (value - node.getLastStageDuration());
		} else {
			return true;
		}
	}
	
	/**
	 * Checks whether the minimum extension of this stage is done
	 * @return returns TRUE when the minimum extension has elapsed
	 */
	protected boolean isMinimumDone() {
		if (Var.hand_active) {
			return true;
		}
		
		if (Var.controller.isAppHaifa()) {
			return isExtDone(getMinExt(), minType); // TODO: check if sk length is needed
		} else if (Var.controller.isAppJerusalem()) {
			if (Var.controller.isPreemption()) {
				return isExtDone(getMinExt(), minType); // TODO: check if sk length is needed
			} else {
				return MinMaxExtension(Var.STAGE_DURATION_MIN);
			}
		} else if (Var.controller.isAppTelAviv()) {
			return MinMaxExtension(Var.STAGE_DURATION_MIN);
		} else {
			// return node.extension.isMinDone();
			return true;
		}
	}

	/**
	 * Checks whether the maximum extension of this stage is done
	 * @return returns TRUE when the maximum extension has elapsed
	 */
	protected boolean isMaximumDone() {
		if (Var.hand_active) {
			return true;
		}
		
		if (Var.controller.isAppHaifa()) {
			return isExtDone(getMaxExt(), maxType); // TODO: check if sk length is needed
		} else if (Var.controller.isAppJerusalem()) {
			if (Var.controller.isPreemption()) {
				return isExtDone(getMaxExt(), maxType); // TODO: check if sk length is needed
			} else {
				return MinMaxExtension(Var.STAGE_DURATION_MAX);
			}
		} else if (Var.controller.isAppTelAviv()) {
			return MinMaxExtension(Var.STAGE_DURATION_MAX);
		} else {
			// return node.extension.isMaxDone();
			return true;
		}
	}

	private int ext;
	private ExtensionType type;
    /**
     * Method reads the current time of the phase
     * @return current phase time in milliseconds
     */
    public boolean MinMaxExtension(int min_max) {
    	int min = getMinExt();
    	int max = getMaxExt();
    	if (min_max == Var.STAGE_DURATION_MIN) {
    		ext = min;
    		type = minType;
    	} else {
    		ext = max;
    		type = maxType;
    	}
    	if (type.getId() == Var.TYPE_ABSOLUTE) { // absolute extension: depending on program cycle time
    		if (ext >= node.getCycleParam() * Var.ONE_SEC){ // if extension is equal or greater then cycle time
    			ext = 0;
    		}
    		return (node.getProgZeit() == ext); // return if program time (ms) is greater or equal to parameter
    	} else if (type.getId() == Var.TYPE_COMPLEMENT) { // complement extension: like regular extension but minus the extension time of previous stage
    		return (getCurrStageDuration() >= (ext - node.getLastStageDuration())); // return if stage time (ms) is greater or equal to parameter minus previous stage extension time
    	} else { // regular extension (default)
    		return getCurrStageDuration() >= ext + getPhLenMS(); // return if stage time (ms) is greater or equal to parameter)
    	}
    }
	
	/**
	 * Checks whether the stage has finished its extension
	 * @param isExtensionActive - whether the extension for this stage should continue (true if any of the extension detectors of this stage are active)
	 * @return returns true when the stage is ready to finish
	 */
	public boolean isStageDone(boolean isExtensionActive) {
		if (getPhasenZeit() >= node.lenPhue + getPhLenMS()) {
			readStageParameters();
			if (isMinimumDone()) {
				if (!isExtensionActive || isMaximumDone()) {
					node.updateStageDuration(this);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Calculates the stage's "Green Time" - the time since the interstage that constructed this stage till now
	 * @return returns the stage's green-time
	 */
	public int GT() {
		if (node.lenPhue == MAX_INTERSTAGE_DURATION) {
			return 0;
		}
		return getPhasenZeit() - node.lenPhue;
	}
	
	public static final int MAX_INTERSTAGE_DURATION = 99 * Var.ONE_SEC;
	private static final int MIN_INTERSTAGE_DURATION = 0;
	private int _iterator;

	protected int longestPhLen = phLen;
	
	public Stage(Node tk, String name, int nr, int len, boolean isStopInPolice, Move[] sgs) {
		super(tk, name, nr);
		phLen = len;
		spNum = 0;
		this.node = tk;
		this.isStopInPolice = isStopInPolice;
		this.sgs = sgs;
	}

	public Stage(Node tk, String name, int nr, int len, int sp, boolean isStopInPolice, Move[] sgs) {
		super(tk, name, nr);
		phLen = len;
		spNum = sp;
		this.node = tk;
		this.isStopInPolice = isStopInPolice;
		this.sgs = sgs;
	}

	/**
	 * Gets the stage's skeleton length (in seconds)
	 * @return	returns the stage's skeleton length (minimal required time) (in seconds)
	 */
	public int getPhLen() {
		return phLen;
	}
	/**
	 * Sets the stage's skeleton length (in seconds)
	 */
	public void setPhLen(int sec) {
		phLen  = sec;
	}

	/**
	 * Gets the stage's skeleton length (in milliseconds)
	 * @return	returns the stage's skeleton length (minimal required time) (in milliseconds)
	 */
	public int getPhLenMS() {
		return phLen * Var.ONE_SEC;
	}
	
	protected void stopInPolice() {
		if (!isStopInPolice) {
			return;
		}
		Tableau.setAdvanced(node, this, node.lenPhue + getPhLenMS()); //for stopping in non-fixed police
	}
	

	abstract protected void deactivated();
	
	protected void baseDeactivated() {
		deactivated();
		if (Var.controller.isAppHaifa()&& Var.controller.isPreemption() && !node.isOffline() && node.isRegularProgram() && node.preemp != null) {
			node.preemp.updateDetMem();
		}
	}
	
	public boolean wasStageActive() {
		return node.wasStageActive(this);
	}
	
	public boolean isStageBuilt() {
		for (_iterator = 0; _iterator < sgs.length; _iterator++) {
			if (node.isInMaintenance()) {
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
	
	public boolean isStageGreenBuilt() {
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
	
	public boolean isSgInStage(Move sg) {
		for (_iterator = 0; _iterator < sgs.length; _iterator++) {
			if (sgs[_iterator].getNr() == sg.getNr()) {
				return true;
			}
		}
		return false;
	}
	
	public Stage startNextPhase(InterStage phue) {
		baseDeactivated();
		node.lenPhue = MAX_INTERSTAGE_DURATION;
		startPhase(phue);
		return (Stage)(phue.getZielPhase());
	}
	
	public Stage startNextPhase(Stage phase) {
		baseDeactivated();
		node.lenPhue = MIN_INTERSTAGE_DURATION;
		return phase;
	}

	public int getCurrCycleSec() {
		return node.getProgZeit();
	}

    /**
     * Method reads the current time of the phase
     * @return current phase time in milliseconds
     */
    public int getCurrStageDuration() {
//        return getPhasenZeit() - ((ExtendedTK)getTeilKnoten()).lenPhueSG;
        return getPhasenZeit() - ((Node)getTeilKnoten()).lenPhue;
    }

    protected void freezeStage() {
    	node.FreezeProgramCycle();
    	if (getPhasenZeit() > 0) {
    		setPhasenZeit(getPhasenZeit() - node.getZyklDauer());
    	}
    }
    
	protected boolean isDxWait() {
		if (node.useDx && node.dem_dx && !node.dx_done) {
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

	/**
	 * Checks whether all the moves of the stage have been green longer than a given parameter
	 * @param timeMS	the time required (in milliseconds) for the moves to be green
	 * @return	returns true when all moves have been green for at least timeMS milliseconds
	 */
	public boolean isGtEllapsed(int timeMS) {
		for (_iterator = 0; _iterator < sgs.length; _iterator++) {
			if ((sgs[_iterator].getZustand() != Zustand.GRUEN && sgs[_iterator].getZustand() != Zustand.GELBBLINKEN) ||
				!sgs[_iterator].activecheck(timeMS)) {
				return false;
			}
		}
		return true;
	}
}
