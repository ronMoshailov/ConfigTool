package core.greenwave;

import m0547.Var;
import vt.StgEbene;
import core.Node;
import core.detectors.Output;
import core.detectors.TimedOutput;

/**
 * 
 * @author ilia
 *
 */
public class GreenWaveClock extends iGreenWave {
    public static final int SYNC_MIDNIGHT = 0;
    public static final int SYNC_1980     = 1;
    public static final int SYNC_1970     = 2;
    
    protected boolean isActiveInManualMode;
    protected int syncBase;
    
    public GreenWaveClock(Node node, boolean isActiveInManualMode, int syncBase) {
        super(node);
        if (this.node == null) {
            throw new NullPointerException("Green Wave (Clock) node cannot be null!");
        }
        this.isActiveInManualMode = isActiveInManualMode;
        this.syncBase = syncBase;
    }
    
    public boolean isActive() {
        if (!isActiveInManualMode && (node.getAktStgEbene() == StgEbene.STG_NUM_MANUELL || node.getAktStgEbene() == StgEbene.STG_NUM_MANUELL_EXTERN)) {
            return false;
        }
        
        return Var.controller.parameters.isClockSync(node.getNummer(), node.getAktProg().getNummer());
    }

    public boolean isError() {
        return false;
    }

    public boolean isSY() {
        if (isError() || !isActive()) {
            return true;
        }
        
        boolean isSynchronized = false;
        
        long timePassed = 0;
        if (this.syncBase == SYNC_1970) {
            timePassed = Var.controller.getSecondsFrom1970();
        } else if (this.syncBase == SYNC_1980) {
            timePassed = Var.controller.getSecondsFrom1980();
        } else {
            timePassed = Var.controller.getSecondsFromMidnight();
        }
        
        int cycle = Var.controller.parameters.getCycle(node.getNummer());
        int offset = Var.controller.parameters.getOffset(node.getNummer());
        
        if (offset == cycle) {
            offset = 0;
        } else if (offset > cycle && cycle > 0) {
            offset = offset % cycle;
        }
        
        isSynchronized = (timePassed % cycle == offset);
        if (isSynchronized) {
            Led_SyOffset.setAutomaticTurnOff(1).turnOn();
        }
        return isSynchronized;
    }
    
    public boolean isSYError() {
        return false;
    }
    
    public boolean isGreenWaveProgram(int programNumber) {
        return true;
    }

    public void initialisiereDet() {
        if (Led_SyOffset == null) {
            Led_SyOffset = new TimedOutput(node, "GW_SyOffset", GreenWaveHW.ID_LED_SY_OFFSET_OUT, false);
        }
        
        if (Led_GwFail == null) {
            Led_GwFail   = new Output(node, "GW_Fail"    , GreenWaveHW.ID_LED_GW_FAIL_OUT, false);
        }
    }

    public void initialisiereParameter() { }

    public void operateGW() { }

    public void reset() { }

    public void postOperateGW() {
        
    }
}
