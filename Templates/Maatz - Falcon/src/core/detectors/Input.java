package core.detectors;

import m0547.Var;
import core.Move;
import core.Node;
import core.parameters.ParametersIndex;
import det.Detektor;

/**
 * @author Ilia Butvinnik
 */
public class Input extends Detektor {
	
	protected static int inputId = 1;
	protected static int mapInputId = 121;
	
	protected static int remoteDetectorId = 0;
	
	public Node node;
	public Move move;
	public Input mapInput;
	protected boolean hasMapInput;
	
//	protected int detectorId;
	protected int parametersIndex = -1;
	
    protected boolean isActive;
    protected boolean isActivated;
    protected boolean isDeactivated;
	protected boolean currentState;
	protected boolean previousState;
	protected int stateTime;
	
	protected boolean set;
	protected boolean disabled;
	
	protected int setFromCenter = Var.NONE;
	
	protected boolean setFromCC;
	protected boolean disabledFromCC;

	protected boolean isCallAll;
	protected boolean isPulseFromControlCenter = false;
	protected boolean isRemoteDetector = false;
    protected int remoteDetectorIndex = 0;
	
	protected boolean isManualDemand;
	protected boolean isManuallySetThisCycle;
	protected boolean isSettableFromCC = true;
	protected ParametersIndex paramIndex = null;
	
	public static void skipId() {
		inputId++;
	}
	
	public static int getCurrentId() {
		return inputId;
	}
	
	public Input(Node node, String name, int onTimeSecError, int flutterCountPerSecondError, int offTimeSecError, int id) {
	    super(node, name, onTimeSecError, flutterCountPerSecondError, offTimeSecError, id);
	    this.node = node;
        node.inputs.add(this);
        node.allInputs.add(this);
	}
	
    /**
     * 
     * @param node
     * @param name
     */
    public Input(Node node, String name) {
        super(node, name, 0, 10, 0, inputId++);
        this.node = node;
        node.inputs.add(this);
        node.allInputs.add(this);
    }

    /**
     * 
     * @param node
     * @param name
     * @param id
     */
    public Input(Node node, String name, int id) {
        super(node, name, 0, 10, 0, id);
        this.node = node;
        node.inputs.add(this);
        node.allInputs.add(this);
    }
    
	/**
	 * 
	 * @param node
	 * @param name
	 * @param move
	 */
    public Input(Node node, String name, Move move, int staticGap) {
        super(node, name, 0, 10, 0, inputId++, move, staticGap, 0);
        this.node = node;
        this.move = move;
        node.allInputs.add(this);
    }
    
    /**
     * 
     * @param node
     * @param name
     * @param move
     * @param id
     */
    public Input(Node node, String name, Move move, int staticGap, int id) {
        super(node, name, 0, 10, 0, id, move, staticGap, 0);
        this.node = node;
        this.move = move;
        node.allInputs.add(this);
    }
    
    public void initializeMapInput() {
        if (Var.controller.isSynopticMap() && hasMapInput) {
            mapInput = new Input(node, "DSY" + getName(), mapInputId++);
        }
    }
    
    /**
     * 
     * @param param
     * @return
     */
    public Input setModeFromParametersIndex(ParametersIndex param) {
        this.paramIndex = param;
        return this;
    }
    
    public boolean isRemoteDetector() {
        return this.isRemoteDetector;
    }
    
    public Input setRemoteDetector() {
        this.isRemoteDetector = true;
        this.remoteDetectorIndex = remoteDetectorId++;
        return this;
    }
    
    public int getRemoteDetectorIndex() {
        return this.remoteDetectorIndex;
    }
    
    public boolean isPulseFromControlCenter() {
        return this.isPulseFromControlCenter;
    }
    
    public Input setPulseFromControlCenter() {
        this.isPulseFromControlCenter = true;
        return this;
    }
	
    protected boolean isSetDemandFromParameters() {
        if (parametersIndex < 0 || (!node.isRegularProgram() && !node.isInMapOnly()))
            return false;
        
        if ((this instanceof DDetector)
                || (this instanceof DEDetector)
                || (this instanceof TPDetector)) {
            return Var.controller.parameters.isSetDemandDetector(node.getNummer(), parametersIndex);
        } else {
            return false;
        }
    }
    
    protected boolean isSetExtensionFromParameters() {
        if (parametersIndex < 0 || (!node.isRegularProgram() && !node.isInMapOnly()))
            return false;
        
        if ((this instanceof EDetector)
                || (this instanceof DEDetector)
                || (this instanceof QDetector)) {
            return Var.controller.parameters.isSetExtensionDetector(node.getNummer(), parametersIndex);
        } else {
            return false;
        }
    }
    
    protected boolean isDisabledDetectorFromParameters() {
        if (parametersIndex < 0 || (!node.isRegularProgram() && !node.isInMapOnly()))
            return false;
        
        return Var.controller.parameters.isDisabledDetector(node.getNummer(), parametersIndex);
    }
    
    protected boolean isDetectorSetFromCC() {
        return Var.controller.central.isDetectorSet(this);
    }
    
    public void updateState(boolean isInputActive) {
        set = isDetectorSetFromCC() || isSetDemandFromParameters() || isSetExtensionFromParameters() || isManualDemandOn();
        disabled = isDisabledDetectorFromParameters() || isManualDemandOff();
        
        currentState = isInputActive;
        if (disabled) {
            currentState = false;
        } else if (set) {
            currentState = true;
        } else if (mapInput != null) {
            currentState |= mapInput.belegt();
        }
        
        isActive = currentState;
        isActivated = (isActive && !previousState) || (isActivated && node.isFullSecond());
        isDeactivated = (!isActive && previousState) || (isDeactivated && node.isFullSecond());

        if (currentState == previousState) {
            if (stateTime < Var.MAX_COUNTER_VALUE) {
                stateTime += node.getZyklDauer(); 
            }
        } else {
            stateTime = 0;
        }

        previousState = currentState;
    }
	
	public void updateState() {
	    updateState(belegt());
	}
    
    public int getStateTime() {
        return getStateTimeMS() / Var.ONE_SEC_MS;
    }
    
    public int getStateTimeMS() {
        return stateTime;
    }
    
    public int GT() {
        return isActive ? getStateTime() : 0;
    }
    
    public int GTMS() {
        return isActive ? getStateTimeMS() : 0;
    }
    
    public int RT() {
        return (!isActive) ? getStateTime() : 0;
    }
    
    public int RTMS() {
        return (!isActive) ? getStateTimeMS() : 0;
    }
	
	public boolean IsActive() {
		return isActive;
	}
	
	public boolean IsInactive() {
		return !isActive;
	}
	
	public boolean IsActivated() {
		return isActivated;
	}
	
	public boolean IsDeactivated() {
		return isDeactivated;
	}
	
	public void setManualDemand(boolean isSet) {
	    if (isSet) {
	        setFromCenter = 1;
	    } else {
	        setFromCenter = 0;
	    }
	}
	
	public boolean isManualDemandOn() {
	    return setFromCenter == 1;
	}
	
	public boolean isManualDemandOff() {
	    return setFromCenter == 0;
	}
	
	public void resetManualDemand() {
	    setFromCenter = -1;
	}

	public boolean isSetOnJLRT() {
		if (!Var.controller.isAppJerusalem() || !Var.controller.isPreemption())
			return false;
		
		if (paramIndex == null)
			return false;
		
//		if (((PreemptionJLRT)node.preempJ).getParameterValue(paramIndex) == 1)
//			return true;
		
		return false;
	}

	public boolean isSetOffJLRT() {
		if (!Var.controller.isAppJerusalem() || !Var.controller.isPreemption())
			return false;
		
		if (paramIndex == null)
			return false;
		
//		if (((PreemptionJLRT)node.preempJ).getParameterValue(paramIndex) == 0)
//			return true;
		
		return false;
	}
}
