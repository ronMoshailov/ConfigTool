/**
 * 
 */
package core.detectors;

import m0547.Var;
import core.Node;
import vtvar.VtVarStrukt;
import det.DetektorIntern;

/**
 * @author Ilia Butvinnik
 *
 */
public class InputInternal extends DetektorIntern {
	
	public VtVarStrukt detSetParam;
	public Node node;
	public boolean isActive, isActivated, isDeactivated;
	
	protected boolean currentState, previousState;
	protected int stateTime;
	
	public InputInternal(String name, Node node) {
	    super(node, name, 0, 10, 0, Input.getCurrentId());
        Input.skipId();
        this.node = node;
        node.internals.add(this);
        
        detSetParam = VtVarStrukt.init(Var.tk1, "SIT_SET_" + name, new int[] { 2 }, true, true, true);
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

	public void updateState() {
	    boolean isSetOnJLRT = detSetParam != null && detSetParam.get(0) == 1;
        boolean isSetOffJLRT = detSetParam != null && detSetParam.get(0) == 0;
        
        if (Var.controller.sit.isConnected()) {
            if (Var.controller.sit.getSitOk()) {
                set();
            } else {
                reset();
            }
        } else {
            reset();
        }
        
        currentState = (belegt() || isSetOnJLRT) && !isSetOffJLRT;
        
        isActive = currentState;
        isActivated = isActive && !previousState;
        isDeactivated = !isActive && previousState;

        if (node.isFullSecond()) {
            if (currentState == previousState) {
                if (stateTime < Var.MAX_COUNTER_VALUE) {
                    stateTime++; 
                }
            } else {
                stateTime = 0;
            }
            
            previousState = currentState;
        }
	}
	
	public int getStateTime() {
	    return stateTime;
	}
	
	public int getStateTimeMS() {
	    return stateTime * Var.ONE_SEC_MS;
	}

    public void resetStateTime() {
        stateTime = 0;
    }
    
    public boolean isLocalDetector() {
        return true;
    }
}
