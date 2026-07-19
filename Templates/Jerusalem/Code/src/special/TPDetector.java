/**
 * 
 */
package special;

import j200.ParametersJerusalem;
import j200.Var;
import sg.Zustand;

/**
 * @author Ilia Butvinnik
 *
 */
public class TPDetector extends Input {
	public Move move;
	public Output feedback;
	
	public TPDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(name, 0, 10, 0, move, 0, isCallAllable, isSetFromParamsEnabled);
		this.move = move;
		
		feedback = new Output(move.node, "SK" + name.substring(1));
		
		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input("DSY" + name, 0, 10, 0, move, 0, false, false);
		}
	}
	
	public void UpdateState() {
		setOnFromParams = isSetOnFromParameters();
		setFromCC = getSetFromCC();
		
		currentState = (getAnforderung() || (mapInput != null && mapInput.getAnforderung()) || setOnFromParams || setFromCC) && (!node.isOffline() && !(((ParametersJerusalem)Var.controller.dvi35Parameters).getDetectors(node, detectorId - 1) == Var.INPUT_DISABLED && isSetFromParamsEnabled));
		
		isActive = currentState;
		isActivated = isActive && !previousState;
		isDeactivated = !isActive && previousState;
		
		if (currentState == previousState) {
			if (stateTime < Var.MAX_COUNTER_VALUE) {
				stateTime += node.getZyklDauer(); 
			}
		} else {
			stateTime = 0;
		}
		
		previousState = currentState;
	}
	
	/**
	 * This method updates the push-buttons feedbacks according to signal group state and demand (with memory) status
	 */
	public void skSet() {
		if  ((getAnforderung() || (mapInput != null ? mapInput.getAnforderung() : getAnforderung())) && (move.getZustand() != Zustand.GRUEN )) {
			feedback.setAusgang();
		} else {
			feedback.resetAusgang();
		}
	}

	/**
	 * This method resets feedbacks of push-buttons (in blink program, dark program, etc.)
	 */
	public void skReset() {
		feedback.resetAusgang();
	}
}
