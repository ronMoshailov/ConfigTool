package special;

import tk.Var;

/**
 * @author Ilia Butvinnik
 *
 */
public class DDetector extends Input {
	/**
	 * Constructor of an Demand detector
	 * @param name - name of the detector
	 * @param move - the move to which the Extension detector refers
	 * @param isUseMapInput - whether an input on a synoptic map exists
	 */
	public DDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(name, 0, 10, 0, move, 4000, isCallAllable, isSetFromParamsEnabled);
		
		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input(node, "DSY" + name, mapInputId++, false, false);
		}
	}
	
	public DDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled, int detectorId) {
		super(name, 0, 10, 0, move, 4000, isCallAllable, isSetFromParamsEnabled, detectorId);

		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input("DSY" + name, 0, 10, 0, move, 4000, false, false);
		}
	}
}