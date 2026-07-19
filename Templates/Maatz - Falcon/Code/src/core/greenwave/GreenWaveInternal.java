package core.greenwave;

import m0547.Var;
import core.Node;

public class GreenWaveInternal extends iGreenWave {
    
    public static GreenWaveInternal InitializeMaster(Node node) {
        return new GreenWaveInternal(node, true, false, false, null);
    }
    
    public static GreenWaveInternal InitializeSlave(Node node, GreenWaveInternal masterNode) {
        return new GreenWaveInternal(node, false, true, false, new GreenWaveInternal[] { masterNode });
    }
    
    public static GreenWaveInternal InitializePeer(Node node, GreenWaveInternal[] peers) {
        return new GreenWaveInternal(node, false, false, true, peers);
    }
    
    protected GreenWaveInternal[] peers;
    private int peersIndex;
    protected boolean previousSY;
    protected int syOffsetCounter = -1;
    protected int syDuration;
    
    protected boolean isMaster;
    protected boolean isSlave;
    protected boolean isPeer;
    
    protected boolean sy;
    
    protected GreenWaveInternal(Node node, boolean isMaster, boolean isSlave, boolean isPeer, GreenWaveInternal[] peers) {
        super(node);
        this.isMaster = isMaster;
        this.isSlave = isSlave;
        this.isPeer = isPeer;
        this.peers = peers;
    }

    public boolean isError() {
        return false;
    }

    public boolean isActive() {
        return false;
    }

    public boolean isSY() {
        if (peers == null || (!this.isSlave && !this.isPeer)) {
            return true;
        }
        
        if (isSlave) {
            return peers[0].node.isOffline() || syOffsetCounter == 0;
        }
        
        if (isPeer) {
            for (peersIndex = 0; peersIndex < peers.length; peersIndex++) {
                if (!peers[peersIndex].node.isOffline() && !peers[peersIndex].sy) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public boolean isSYError() {
        return false;
    }
    
    public boolean isGreenWaveProgram(int programNumber) {
        return true;
    }

    public void initialisiereDet() { }

    public void initialisiereParameter() { }

    public void operateGW() {
        if (peers != null && peers.length == 1 && peers[0] != null && isSlave) {
            if (syOffsetCounter >= 0) {
                syOffsetCounter--;
            }
            
            if (peers[0].sy && !previousSY) {
                syOffsetCounter = Var.controller.parameters.getOffset(node.getNummer());
                syDuration = 0;
            }
            
            if (peers[0].sy) {
                syDuration++;
            }
            
            previousSY = peers[0].sy;
        }
    }

    public void reset() {
        syDuration = 0;
        previousSY = false;
    }

    public void postOperateGW() {
        
    }
}
