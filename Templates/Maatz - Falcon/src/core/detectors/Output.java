package core.detectors;

import m0547.Var;
import core.Node;
import det.Ausgang;

public class Output extends Ausgang {
	
	private static int outputId = 1;
	
	protected Node node;

	protected int stateTime;
	protected boolean currentState;
	protected boolean previousState;
	protected boolean isActive;
	protected boolean isActivated;
	protected boolean isDeactivated;
	protected boolean isPulseToControlCenter = false;
    
    public Output(Node node, String name) {
        super(node, name, outputId++);
        this.node = node;
        this.isPulseToControlCenter = false;
        node.outputs.add(this);
        node.allOutputs.add(this);
    }
    
    public Output(Node node, String name, boolean isRemoteOutput) {
        super(node, name, outputId++);
        this.node = node;
        if (!isRemoteOutput) {
            this.isPulseToControlCenter = false;
            node.outputs.add(this);
        } else {
            this.isPulseToControlCenter = true;
            node.outputsRemote.add(this);
        }
        node.allOutputs.add(this);
    }
    
	public Output(Node node, String name, int index, boolean isRemoteOutput) {
		super(node, name, index);
		this.node = node;
        if (!isRemoteOutput) {
            this.isPulseToControlCenter = false;
            node.outputs.add(this);
        } else {
            this.isPulseToControlCenter = true;
            node.outputsRemote.add(this);
        }
        node.allOutputs.add(this);
	}
	
    public boolean isPulseToControlCenter() {
        return isPulseToControlCenter;
    }
    
	public int getStateTime() {
	    return getStateTimeMS() / Var.ONE_SEC_MS;
	}
	
	public int getStateTimeMS() {
	    return this.stateTime;
	}
	
	public void updateState() {
		currentState = getAusgang();
		
		isActive = getAusgang();
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
	
	public boolean isActive() {
		return isActive;
	}
	
	public boolean isInactive() {
		return !isActive;
	}
	
	public boolean isActivated() {
		return isActivated;
	}
	
	public boolean isDeactivated() {
		return isDeactivated;
	}
	
	public void turnOn() {
	    setAusgang();
	}
	
	public void turnOff() {
	    resetAusgang();
	}
	
	/**
	 * Toggles the output according to the passed parameter
	 * @param isSet - the state to set the output to. true means turn the output on, false means turn it off
	 */
	public void operate(boolean isSet) {
	    if (isSet) {
	        turnOn();
	    } else {
	        turnOff();
	    }
	}
}
