/**
 * 
 */
package core.detectors;

import core.Move;
import core.parameters.ParametersIndex;
import sg.Zustand;

/**
 * @author Ilia Butvinnik
 *
 */
public class TPDetector extends Input {
	public Output feedback;

    /**
     * 
     * @param name
     * @param move
     */
    public TPDetector(String name, Move move, ParametersIndex param) {
        super(move.node, name, move, 0);
        hasMapInput = true;
        node.pushButtons.add(this);
        parametersIndex = inputId - 1;
    }
    
    /**
     * 
     * @param name
     * @param move
     * @param id
     */
    public TPDetector(String name, Move move, ParametersIndex param, int id) {
        super(move.node, name, move, 0, id);
        hasMapInput = true;
        node.pushButtons.add(this);
        parametersIndex = id;
    }
    
    public TPDetector setRemoteDetector() {
        return (TPDetector)super.setRemoteDetector();
    }
    
    public void initializeFeedback() {
        feedback = new Output(move.node, "SKP" + getName().substring(2), false);
    }
	
	public void updateState() {
	    updateState(getAnforderung());
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
