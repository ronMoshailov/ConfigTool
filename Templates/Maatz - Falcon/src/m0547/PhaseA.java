package m0547;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 239                                                                     	*
 *  Junction Name  : Varburg / Rains / HaTkuma Yahiam - Maavar Hazaya							*
 *  Equipmentno.   : h239                                                                    	*
 *                                                                                              *
 ************************************************************************************************/
import core.MainStage;
import core.Move;
import parameters.NetiveiIsraelParameters;
import vt.Phase;

public class PhaseA extends MainStage {
    protected Tk1 node;

    /**
     * Constructor for Haifa applications or for Jerusalem preemption applications
     * @param node - the node to which the stage refers
     * @param name - the name of the stage
     * @param number - the number of the stage
     * @param startLengths - array of length of this stage in the beginning of the program cycle (array size = number of structs)
     * @param endLengths - array of length of this stage in the end of the program cycle (array size = number of structs)
     * @param isStopInPolice - whether a non-fixed police program should stop in this stage
     * @param sgs - list of moves that must open in this stage
     */
    public PhaseA(Tk1 node, String name, int number, int[] startLengths, int[] endLengths, boolean isStopInPolice, Move[] sgs) {
        super(node, name, number, startLengths, endLengths, isStopInPolice, sgs);
        this.node = node;
        // sk_number       1   2   3   4   5   6   7   8   9  10
        A_Len = new int[] {0,  0,  0,  0,  0,  0,  0,  0,  0,  0};    
    }
    
    public int getMinTypeCycleStart() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMinTypeBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getMinType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_TIMERS));
        }
    }
    
    public int getMaxTypeCycleStart() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMaxTypeBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getMaxType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_TIMERS));
        }
    }
    
    public int getMinExtCycleStart() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMinBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_MIN);
        }
    }

    public int getMaxExtCycleStart() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMaxBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_MAX);
        }
    }

    public int getMinTypeCycleEnd() {
        if (node.greenwaveIsSYError()) {
            switch (Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_ISMASTER)) {
                case 2:
                case 4:
                    return Var.controller.parameters.getMinType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_TIMERS_ALT)); 
            }
        }
        return Var.controller.parameters.getMinType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_TIMERS));
    }
    
    public int getMaxTypeCycleEnd() {
        if (node.greenwaveIsSYError()) {
            switch (Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_ISMASTER)) {
                case 2:
                case 4:
                    return Var.controller.parameters.getMaxType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_TIMERS_ALT)); 
            }
        }
        return Var.controller.parameters.getMaxType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_TIMERS));
    }
    
    public int getMinExtCycleEnd() {
        if (node.greenwaveIsSYError()) {
            switch (Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_ISMASTER)) {
                case 2:
                case 4:
                    return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_MIN_ALT); 
            }
        }
        return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_MIN);
    }

    public int getMaxExtCycleEnd() {
        if (node.greenwaveIsSYError()) {
            switch (Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_ISMASTER)) {
                case 2:
                case 4:
                    return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_MAX_ALT); 
            }
        }
        return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SY_MAX);
    }

    /**
     * This method is called when the stage is started
     */
    protected void activated() {
        
    }

    /**
     * This method is called when the stage is terminated
     */
    public void deactivated(Phase targetPhase) {
        
    }

    /**
     * called once per program cycle at the beginning of the cycle
     */
    public void startOfCycleEvent() {
        
    }

    /**
     * called once per program cycle at the end of the cycle
     */
    public void endOfCycleEvent() {

    }

    /**
     * This method is called before the skeleton time of the stage is done (and during it too)
     */
    protected void beforeSkeletonDoneCycleStart() {
    }

    /**
     * This method is called before the skeleton time of the stage is done (and during it too)
     */
    protected void beforeSkeletonDoneCycleEnd() {
    }

    /**
     * This method is called every scan-cycle while the stage is active (in the beginning of the cycle)
     */
    public Phase phasenFunktionCycleStart() {
        gaps            = true;
        isStageDoneFlag = isDoneCycleStart(gaps);
//        isStageDoneFlag = isDoneCycleStartDxE(gaps);
        
        if (isStageDoneFlag) {
            return switchStage(node.PhEQA);
        }

        return KEINE_UMSCHALTUNG;
    }

    /**
     * This method is called every scan-cycle while the stage is active (in the end of the cycle)
     */
    public Phase phasenFunktionCycleEnd() {
        gaps            = !node.greenwaveIsSY();
        isStageDoneFlag = isDoneCycleEnd(gaps);
        
        
        if (isStageDoneFlag) {
            node.ps1 .turnOff();
            return switchStage(node.PhA);
        }

        return KEINE_UMSCHALTUNG;
    }
}