package core.detectors;

import m0547.Var;
import core.Node;

public class TimedOutput extends Output {

    protected int threshold = Var.NONE;
    protected int counter = Var.NONE;
    
    public TimedOutput(Node node, String name, boolean isRemoteOutput) {
        super(node, name, isRemoteOutput);
    }
    
	public TimedOutput(Node node, String name, int index, boolean isRemoteOutput) {
		super(node, name, index, isRemoteOutput);
	}
	
	/**
	 * Sets the threshold (in seconds) after which the output should automatically be turned off
	 * Note that calling the turnOn function resets the timer
	 * @param time - the time since the last turnOn command after which this output should automatically be turned off (seconds)
	 * @return
	 */
	public TimedOutput setAutomaticTurnOff(int timeS) {
	    this.threshold = timeS;
	    return this;
	}
	
	public void updateState() {
	    super.updateState();
	    if (isActive() && threshold > Var.NONE) {
	        if (node.isFullSecond()) {
                counter++;
            }
	        
	        if (counter >= threshold) {
	            turnOff();
	        }
	    }
	}
	
    public void turnOn() {
        super.turnOn();
        counter = 0;
    }
    
    public void turnOff() {
        super.turnOff();
        counter = Var.NONE;
    }
}
