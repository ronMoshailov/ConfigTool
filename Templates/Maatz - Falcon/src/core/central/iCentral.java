package core.central;

import m0547.Var;
import core.Node;
import core.detectors.Input;
import core.detectors.Output;
import vt.StgEbene;
import vt.Vt;

/**
 * This abstract class defines the basic behavior of a central control system 
 * @author Ilia Butvinnik
 * @version 2.0.0
 * @since 01/07/2025
 */
public abstract class iCentral {
   
    public static boolean CC_isConnected;
    public static Output LED_CC_isConnected;
    
    public String name;
    public String version;
    
    public iCentral(String name, String version) {
        this.name = name;
        this.version = version;
    }
    
    public static void activateProgram(Node node) {
        if (node.centralProgram > Var.NONE) {
            if (node.getProgWunsch(node, StgEbene.STG_ZENTRALE) == Vt.KEIN_PROGRAMMWUNSCH
                    || node.getProgWunsch(node, StgEbene.STG_ZENTRALE).getNummer() != node.centralProgram) {
                node.setProgWunsch(Vt.findProgByNum(node.centralProgram, node.getNummer()), StgEbene.STG_ZENTRALE);
            }
        } else if (node.getProgWunsch(node, StgEbene.STG_ZENTRALE) != Vt.KEIN_PROGRAMMWUNSCH) {
            node.setProgWunsch(null, StgEbene.STG_ZENTRALE);
        }
    }
    
	public abstract void monitorTelegram();
	public abstract void setReplyToCenter();
    
	public abstract boolean isConnected();
    public abstract boolean isOperateController();
    public abstract boolean isOperateInCentralFlash();
    public abstract boolean isOperateInCentralDark();
	public abstract boolean isOperateSigns();
	public abstract boolean isOperateGreenWaveSY();
	public abstract boolean isGreenWaveSY();
	public abstract boolean isDetectorSet(Input input);
	public abstract boolean isFire();
	public abstract int getFire();
	
}
