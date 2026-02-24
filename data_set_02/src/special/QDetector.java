package special;
import enums.AppType;
import tk.ParSets;
import tk.ParametersJerusalem;
import tk.Var;
import vtvar.VarInt;

/**
 * @author Ilia Butvinnik
 *
 */
public class QDetector extends Input {
	public VarInt jamUnit;
	protected int jamInt;
	
	/**
	 * Constructor of an Queue detector
	 * @param name - name of the detector
	 * @param move - the move to which the Queue detector refers
	 * @param isUseMapInput - whether an input on a synoptic map exists
	 */
	public QDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(name, 0, 10, 0, move, 4000, isCallAllable, isSetFromParamsEnabled);
		
		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input(node, "DSY" + name, mapInputId++, false, false);
		}
	}
	
	public QDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled, int detectorId) {
		super(name, 0, 10, 0, move, 4000, isCallAllable, isSetFromParamsEnabled, detectorId);
		
		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input(node, "DSY" + name, mapInputId++, false, false);
		}
	}
	
	/**
	 * Depending on the application type, returns the jam unit of this queue detector
	 * @return the jam unit (in MS) of this queue detector
	 */
	protected int getJam() {
		if (Var.controller.getAppType().equals(AppType.Haifa)) {
			if (detectorId <= Var.controller.getMaximalDetectorId()) {
				return ParSets.Det_Param[node.gapSet + detectorId - 1] * 100;
			} else {
				if (Var.isPrintWarnings) {
					System.out.println("Warning! This application's parameters support only up to " + Var.controller.getMaximalDetectorId() + " parameters. Value for " + detectorId + " is invalid.");
				}
				return Controller.DEFAULT_JAM_UNIT;
			}
		} else {
			return jamUnit.get();
		}
	}
	
	public void UpdateState() {		
		jamInt = getJam();
		setFromParams = getSetFromParameters();
		setFromCC = getSetFromCC();
		
		currentState = belegt() && (!node.isOffline() && (!Var.controller.isAppJerusalem() || !(((ParametersJerusalem)Var.controller.dvi35Parameters).getDetectors(node, detectorId - 1) == Var.INPUT_DISABLED && this.isSetFromParamsEnabled)));
		if (mapInput != null)
			currentState = currentState || mapInput.belegt();
		currentState = currentState || setFromParams || setFromCC;
		
		if (currentState == previousState) {
			if (stateTime < Var.MAX_COUNTER_VALUE) {
				stateTime += node.getZyklDauer(); 
			}
		} else {
			stateTime = 0;
		}
			
//		System.out.println(" jamInt: " + jamInt +  " previousState: " + previousState + " stateTime: " + stateTime + " 2: " + (stateTime  >= jamInt));
		isActive = currentState && (stateTime >= jamInt);
		isActivated = isActive && !previousState;
		isDeactivated = !isActive && previousState;
		previousState = currentState;
	}
}
