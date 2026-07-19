package core.greenwave;

import m0547.Var;
import core.Node;
import core.detectors.Output;
import core.detectors.TimedOutput;

public abstract class iGreenWave {
    
    private int maxWaitTime;
    private int waitTime;
    
    protected Node node;
    
    public static TimedOutput Led_SyOffset;
    public static Output Led_GwFail;
    public static Output Led_GwActive;

    public static boolean GW_Offset;
    public static boolean GW_Fail;
    public static boolean GW_Active;
    
    public abstract boolean isError();
    public abstract boolean isActive();
    public abstract boolean isSY();
    public abstract boolean isSYError();
    public abstract boolean isGreenWaveProgram(int programNumber);

    public abstract void initialisiereDet();
    public abstract void initialisiereParameter();

    public abstract void operateGW();
    public abstract void postOperateGW();
    public abstract void reset();
    
    public iGreenWave(Node node) {
        this.node = node;
    }
    
    /**
     * This method checks whether waiting for SY is required.
     * If it needs to wait, the wait time will be limited by a third of the active program's maximum cycle time
     * It is possible to set whether the controller should or shouldn't freeze the program and stage time when waiting occurs
     * @param isFreezeTime - if waiting for SY is required, the node's cycle time counter and the active stage's counter will be froze if this flag is set to true
     * @return true if no wait is needed, false otherwise
     */
    public boolean waitForSYByCycleTime(boolean isFreezeTime) {
        // maxWaitTime should be in seconds, not milliseconds
        maxWaitTime = Var.controller.parameters.getCycle(node.getNummer()) / 3;
        
        if (isSY() || waitTime >= maxWaitTime) {
            waitTime = 0;
            node.isWaitingForSy = false;
            return true;
        } else {
            waitTime++;
            node.isWaitingForSy = true;
            if (isFreezeTime) {
                node.freezeTime();
                node.currStage.freezeTime();
            }
            return false;
        }
    }
}
