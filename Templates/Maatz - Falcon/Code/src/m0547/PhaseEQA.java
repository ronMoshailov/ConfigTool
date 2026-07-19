package m0547;

import core.Move;
import core.Stage;
import vt.Phase;
import parameters.NetiveiIsraelParameters;

public class PhaseEQA extends Stage {
    protected Tk1 node;

    /**
     * Constructor for Haifa applications or for Jerusalem preemption applications
     * @param node - the node to which the stage refers
     * @param name - the name of the stage
     * @param number - the number of the stage
     * @param length - the minimal (skeleton) length of the stage
     * @param isStopInPolice - whether a non-fixed police program should stop in this stage
     * @param sgs - list of moves that must open in this stage
     */
    public PhaseEQA(Tk1 node, String name, int number, int length, boolean isStopInPolice, Move[] sgs) {
        super(node, name, number, length, isStopInPolice, sgs);
        this.node = node;
    }
    
    public int getMinType() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMinTypeBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getMinType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_TIMERS));
        }
    }
    
    public int getMaxType() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMaxTypeBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getMaxType(Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_TIMERS));
        }
    }
    
    public int getMinExt() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMinBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_MIN);
        }
    }

    public int getMaxExt() {
        // TODO: include according to the application. If not required - return 0
        if (spNumber == 0) {
            return 0;
        } else if (spNumber != Var.NONE) {
            return Var.controller.parameters.getMaxBySP(node.getNummer(), spNumber);
        } else {
            return Var.controller.parameters.getParameterValue(NetiveiIsraelParameters.PARAM_SP01_MAX);
        }
    }

    protected void deactivated(Phase targetStage) {
    } 

    protected void activated() {
        
    }

    /**
     * This method is called before the skeleton time of the stage is done (and during it too)
     */
    protected void beforeSkeletonDone() {
        
    }
    
    /**
     * This method is called every scan-cycle while the stage is active (and after skeleton time is done)
     */
    public Phase phasenFunktion() {
        gaps            = node.e_1.IsActive() || node.e_2.IsActive();
        isStageDoneFlag = isDone(gaps); 
        
        if (isStageDoneFlag) {
            node.ps1.turnOn();
            if (node.d_4.IsActive()) {
                return switchStage(node.PhueEQA_B);
            }
            
            return switchStage(node.PhEQA1);
        }
        return KEINE_UMSCHALTUNG;
    }
}
