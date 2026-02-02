/**
 * 
 */
package special;

import enums.AppType;
import ta172.ParSets;
import ta172.ParametersJerusalem;
import ta172.Var;
import vtvar.VarInt;

/**
 * @author Ilia Butvinnik
 *
 */
public class EDetector extends Input {
	public VarInt gapUnit;
	protected int gapInt;
	
	/**
	 * Constructor of an Extension detector
	 * @param name - name of the detector
	 * @param move - the move to which the Extension detector refers
	 * @param isUseMapInput - whether an input on a synoptic map exists
	 */
	public EDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(name, 0, 10, 0, move, 4000, isCallAllable, isSetFromParamsEnabled);
		
		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input(node, "DSY" + name, mapInputId++, false, false);
		}
	}
	
	public EDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled, int detectorId) {
		super(name, 0, 10, 0, move, 4000, isCallAllable, isSetFromParamsEnabled, detectorId);
		
		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input(node, "DSY" + name, mapInputId++, false, false);
		}
	}
	
	/**
	 * Depending on the application type, returns the gap unit of this extension detector
	 * @return the gap unit (in MS) of this extension detector
	 */
	protected int getGap() {
		if (Var.controller.getAppType().equals(AppType.Haifa)) {
			if (detectorId <= Var.controller.getMaximalDetectorId()) {
				return ParSets.Det_Param[node.gapSet + detectorId - 1] * 100;
			} else {
				if (Var.isPrintWarnings) {
					System.out.println("Warning! This application's parameters support only up to " + Var.controller.getMaximalDetectorId() + " parameters. Value for " + detectorId + " is invalid.");
				}
				return Controller.DEFAULT_GAP_UNIT;
			}
		} else {
			return gapUnit.get();
		}
	}
	
	
	public void UpdateState() {
		gapInt = getGap();
		setFromParams = getSetFromParameters();
		setFromCC = getSetFromCC();
		currentState = (!dynLuecke(gapInt) || (mapInput != null && !mapInput.dynLuecke(gapInt)) || setFromParams || setFromCC) && (!Var.controller.isAppJerusalem() || (!node.isOffline() && !(((ParametersJerusalem)Var.controller.dvi35Parameters).getDetectors(node, detectorId - 1) == Var.INPUT_DISABLED  && this.isSetFromParamsEnabled)));
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
}
