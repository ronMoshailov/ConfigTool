/**
 * 
 */
package special;

import det.Detektor;
import enums.AppType;
import ta172.ParSets;
import ta172.ParametersJerusalem;
import ta172.Var;

/**
 * @author Ilia Butvinnik
 */
public class Input extends Detektor {
	
	protected static int inputId = 1;
	private static final int mapInputIdStart = 121;
	protected static int mapInputId = mapInputIdStart;
	
	
	public Node node;
	public Input mapInput;
	
	protected int detectorId;
//	protected int inputParamId;
	protected boolean currentState;
	protected boolean previousState;
	protected int stateTime;
	protected boolean isActive;
	protected boolean isActivated;
	protected boolean isDeactivated;
	protected boolean setFromParams;
	protected boolean setFromCC;
	protected boolean isSetFromParamsEnabled;
	protected boolean isCallAllable;
	
	public boolean getCallAllable() {
		return isCallAllable;
	}
	
	/**
	 * 
	 * @param node - the node to which the detector is assigned
	 * @param name - the name of the detector
	 */
	public Input(Node node, String name, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled){
		super(node, name, 0, 10, 0, inputId++);
		this.node = node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
			
		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input(node, "DSY" + name, mapInputId++, false, false);
		}
	}
	
	/**
	 * 
	 * @param node - the node to which the detector is assigned
	 * @param name - the name of the detector
	 * @param detOnThreshold - detector stuck ON threshold (in seconds)
	 * @param chatterThreshold - minimum number of pulses for detector chattering error
	 * @param detOffThreshold - detector stuck OFF threshold (in seconds)
	 */
	public Input(Node node, String name, int detOnThreshold, int chatterThreshold, int detOffThreshold, boolean isCallAllable, boolean isSetFromParamsEnabled){
		super(node, name, detOnThreshold, chatterThreshold, detOffThreshold, inputId++);
		this.node = node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}

	/**
	 * 
	 * @param node - the node to which the detector is assigned
	 * @param name - the name of the detector
	 * @param index - the channel of the detector
	 */
	public Input(Node node, String name, int index, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(node, name, 0, 10, 0, index);
		this.node = node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}
	
	/**
	 * 
	 * @param node - the node to which the detector is assigned
	 * @param name - the name of the detector
	 * @param detOnThreshold - detector stuck ON threshold (in seconds)
	 * @param chatterThreshold - minimum number of pulses for detector chattering error
	 * @param detOffThreshold - detector stuck OFF threshold (in seconds)
	 * @param index - the channel of the detector
	 */
	public Input(Node node, String name, int detOnThreshold, int chatterThreshold, int detOffThreshold, int index, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(node, name, detOnThreshold, chatterThreshold, detOffThreshold, index);
		this.node = node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}
	
	/**
	 * 
	 * @param name - the name of the detector
	 * @param move - the move to which the detector is referring
	 * @param gap - time interval after end of release, during which demand is detected (gap) (in milliseconds)
	 */
	public Input(String name, Move move, int gap, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(move.node, name, 0, 10, 0, inputId++, move, gap, 0);
		this.node = move.node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}
	
	/**
	 * 
	 * @param name - the name of the detector
	 * @param detOnThreshold - detector stuck ON threshold (in seconds)
	 * @param chatterThreshold - minimum number of pulses for detector chattering error
	 * @param detOffThreshold - detector stuck OFF threshold (in seconds)
	 * @param move - the move to which the detector is referring
	 * @param gap - time interval after end of release, during which demand is detected (gap) (in milliseconds)
	 */
	public Input(String name, int detOnThreshold, int chatterThreshold, int detOffThreshold, Move move, int gap, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(move.node, name, detOnThreshold, chatterThreshold, detOffThreshold, inputId++, move, gap, 0);
		this.node = move.node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}
	
	/**
	 * 
	 * @param name - the name of the detector
	 * @param detOnThreshold - detector stuck ON threshold (in seconds)
	 * @param chatterThreshold - minimum number of pulses for detector chattering error
	 * @param detOffThreshold - detector stuck OFF threshold (in seconds)
	 * @param move - the move to which the detector is referring
	 * @param gap - time interval after end of release, during which demand is detected (gap) (in milliseconds)
	 * @param jam - time interval since detection till presence is accepted (jam) (in milliseconds)
	 */
	public Input(String name, int detOnThreshold, int chatterThreshold, int detOffThreshold, Move move, int gap, boolean isCallAllable, boolean isSetFromParamsEnabled, int detectorId) {
		super(move.node, name, detOnThreshold, chatterThreshold, detOffThreshold, inputId++, move, gap, 0);
		this.node = move.node;
		this.detectorId = detectorId;
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}
	
	/**
	 * 
	 * @param name - the name of the detector
	 * @param detOnThreshold - detector stuck ON threshold (in seconds)
	 * @param chatterThreshold - minimum number of pulses for detector chattering error
	 * @param detOffThreshold - detector stuck OFF threshold (in seconds)
	 * @param move - the move to which the detector is referring
	 * @param gap - time interval after end of release, during which demand is detected (gap) (in milliseconds)
	 * @param jam - time interval since detection till presence is accepted (jam) (in milliseconds)
	 */	

	public Input(String name, int detOnThreshold, int chatterThreshold, int detOffThreshold, Move move, int gap, int jam, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(move.node, name, detOnThreshold, chatterThreshold, detOffThreshold, inputId++, move, gap, jam);
		this.node = move.node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}
	
	/**
	 * 
	 * @param name - the name of the detector
	 * @param detOnThreshold - detector stuck ON threshold (in seconds)
	 * @param chatterThreshold - minimum number of pulses for detector chattering error
	 * @param detOffThreshold - detector stuck OFF threshold (in seconds)
	 * @param index - the channel of the detector
	 * @param move - the move to which the detector is referring
	 * @param gap - time interval after end of release, during which demand is detected (gap) (in milliseconds)
	 * @param jam - time interval since detection till presence is accepted (jam) (in milliseconds)
	 */
	public Input(String name, int detOnThreshold, int chatterThreshold, int detOffThreshold, int index, Move move, int gap, int jam, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(move.node, name, detOnThreshold, chatterThreshold, detOffThreshold, inputId++, move, gap, jam);
		this.node = move.node;
		this.detectorId = getNummer();
		this.isCallAllable = isCallAllable;
		this.isSetFromParamsEnabled = isSetFromParamsEnabled;
	}
	
	/**
	 * Depending on the application type, returns whether this detector is set via parameters
	 * @return whether the detector is set via parameters
	 */
	protected boolean getSetFromParameters() {
		if (getNummer() >= mapInputIdStart)
			return false;
		
		if (Var.controller.getAppType().equals(AppType.Haifa)) {
			if (detectorId <= Var.controller.getMaximalDetectorId()) {
				return (ParSets.Det_Param[node.modeSet + detectorId - 1] > 0);
			} else {
				if (Var.isPrintWarnings) {
					System.out.println("Warning! This application's parameters support only up to " + Var.controller.getMaximalDetectorId() + " parameters. Value for " + detectorId + " is invalid.");
				}
				return false;
			}
		} else if (Var.controller.getAppType().equals(AppType.Jerusalem)) {
			if (!Var.controller.isPreemption()) {
				if (detectorId > Var.controller.getMaximalDetectorId()) {
					if (Var.isPrintWarnings) {
						System.out.println("Warning! This application's parameters support only up to " + Var.controller.getMaximalDetectorId() + " parameters. Value for " + detectorId + " is invalid.");
					}
					return false;
				} else if (node.isOffline() || !node.isRegularProgram() || !this.isSetFromParamsEnabled) {
					return false;
				} else {
					return ((ParametersJerusalem)Var.controller.dvi35Parameters).getDetectors(node, detectorId - 1) == Var.INPUT_FIXED;
				}
			} else {
				return false;
			}
		} else if (Var.controller.isAppTelAviv()) {
			return false;
		} else {
			if (detectorId > Var.controller.getMaximalDetectorId()) {
				if (Var.isPrintWarnings) {
					System.out.println("Warning! This application's parameters support only up to " + Var.controller.getMaximalDetectorId() + " parameters. Value for " + detectorId + " is invalid.");
				}
				return false;
			} else if (node.isOffline() || !node.isRegularProgram()) {
				return false;
			} else if (node.getNummer() == 2) {
				return ParSets.pars_det2.isExtensionSet(detectorId);
			} else if (node.getNummer() == 3) {
				return ParSets.pars_det3.isExtensionSet(detectorId);
			} else {
				return ParSets.pars_det1.isExtensionSet(detectorId);
			}
		}
	}
	
	/**
	 * Depending on the application type, returns whether the detector is set via the control center
	 * @return whether the detector is set via control center
	 */
	protected boolean getSetFromCC() {
		if (Var.controller.getAppType().equals(AppType.Haifa)) {
			if (detectorId <= Dvi35Haifa.MAX_SET_DETECTORS) {
				return Dvi35Haifa.setDet[detectorId];
			} else {
				if (Var.isPrintWarnings) {
					System.out.println("Warning! Dvi35 supports setting only up to 22 parameters! Value for " + detectorId + " is invalid.");
				}
				return false;
			}
		} else if (Var.controller.getAppType().equals(AppType.Jerusalem)) {
			return isCallAllable && ((Dvi35Jerusalem)Var.controller.dvi35).isCallAll;
		} else {
			return false;
		}
	}
	
	public void UpdateState() {
		setFromParams = getSetFromParameters();
		setFromCC = getSetFromCC();
		
		currentState = (belegt() || (mapInput != null && mapInput.belegt()) || setFromParams || setFromCC) && (!Var.controller.isAppJerusalem() || (!node.isOffline() && !(((ParametersJerusalem)Var.controller.dvi35Parameters).getDetectors(node, detectorId - 1) == Var.INPUT_DISABLED && this.isSetFromParamsEnabled)));
		
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
	
	public int GT() {
		return isActive ? stateTime : 0;
	}
	
	public int RT() {
		return (!isActive) ? stateTime : 0;
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
}
