package special;

import enums.AppType;
import tk.ParSets;
import tk.ParamSet;
import tk.ParametersJerusalem;
import tk.Var;
import vtvar.VarInt;

/**
 * @author Ilia Butvinnik
 *
 */
public class DEDetector extends Input {
	public VarInt gapUnit;
	protected int gapInt;
	protected boolean setFromParametersDemandOnly;

	public boolean demandOnly;

	/**
	 * Constructor of an Demand & Extension detector
	 * @param name - name of the detector
	 * @param move - the move to which the Extension detector refers
	 * @param isUseMapInput - whether an input on a synoptic map exists
	 */
	public DEDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled) {
		super(name, 0, 10, 0, move, 4000, isCallAllable, isSetFromParamsEnabled);

		if (isUseMapInput && Var.controller.isSynopticMap()) {
			mapInput = new Input(node, "DSY" + name, mapInputId++, false, false);
		}
	}
	
	public DEDetector(String name, Move move, boolean isUseMapInput, boolean isCallAllable, boolean isSetFromParamsEnabled, int detectorId) {
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

	/**
	 * Depending on the application type, returns whether this detector is set via parameters
	 * @return whether the detector is set via parameters
	 */
	protected boolean getSetFromParametersDemandOnly() {
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
				} else {
					return ParamSet.Dm_Ex_Det[detectorId - 1] == Var.INPUT_FIXED && this.isSetFromParamsEnabled;
				}
			} else {
				return false;
			}
		} else {
			if (detectorId > Var.controller.getMaximalDetectorId()) {
				if (Var.isPrintWarnings) {
					System.out.println("Warning! This application's parameters support only up to " + Var.controller.getMaximalDetectorId() + " parameters. Value for " + detectorId + " is invalid.");
				}
				return false;
			} else if (node.getNummer() == 2) {
				return ParSets.pars_det2.isDemandSet(detectorId);
			} else if (node.getNummer() == 3) {
				return ParSets.pars_det3.isDemandSet(detectorId);
			} else {
				return ParSets.pars_det1.isDemandSet(detectorId);
			}
		}
	}

	public void UpdateState() {
		gapInt = getGap();
		setFromParams = getSetFromParameters();
		setFromParametersDemandOnly = getSetFromParametersDemandOnly();
		setFromCC = getSetFromCC();

		currentState = (!dynLuecke(gapInt) || (mapInput != null && !mapInput.dynLuecke(gapInt)) || setFromParams || setFromCC) && (!Var.controller.isAppJerusalem() || (!node.isOffline() && !(((ParametersJerusalem)Var.controller.dvi35Parameters).getDetectors(node, detectorId - 1) == Var.INPUT_DISABLED && this.isSetFromParamsEnabled)));
		demandOnly = (belegt() || (mapInput != null && mapInput.belegt()) || setFromParametersDemandOnly || setFromCC) && (!Var.controller.isAppJerusalem() || (!node.isOffline() && !(((ParametersJerusalem)Var.controller.dvi35Parameters).getDetectors(node, detectorId - 1) == Var.INPUT_DISABLED && this.isSetFromParamsEnabled)));

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