package core.programs;

import itc.ExtendedUdp;
import m0547.Var;
import core.Node;
import core.Node.SpecialProgram;
import vt.*;
import vtvar.*;

public class PostMain extends PostMainFkt {
	protected Node node;

	public PostMain (Node node){
		super(node);
		this.node = node;
	}
	
	protected void inJedemZyklus() {
		node.isCycleFrozen = false;
		node.isStageFrozen   = false;
        node.lastActiveProgram = node.getAktProg().getNummer();
        node.operateOOOTLC();
        if (Var.controller.isAppJerusalem() && Var.controller.isPreemption() && node.Catastrophe != null) {
            if (node.getAktProg().getNummer() == SpecialProgram.Catastrophe.getProgramNumber())
                node.Catastrophe.setAusgang();
            else
                node.Catastrophe.resetAusgang();
        }
//		if (!MainStage.forceMainStageSwitch) {
//			node.lastActiveProgram = node.getAktProg().getNummer();
//		}
		
	    if (node.getNummer() == 1) {
	    	Var.controller.signs.operateSigns();
	    	
            if (Var.controller.sit != null) {
                Var.controller.sit.writeMessage();
            }
            
            if (Var.controller.ups != null) {
                Var.controller.ups.updateState();
            }
            
            if (node.getNummer() == 1) {
                ExtendedUdp.sendAll();
            }
	    }
	    
	    node.updateSynopticMapGreenLED();
        node.greenwavePostOperateGW();
		node.postMainFunction();

        DebugVar.updateAll();
        
		if  (node.isError()
		        || node.isDarkProgram()
		        || (node.getAktProg() == node.getBlinkProgramm())
		        || ((node.getAktProg().getNummer() == SpecialProgram.Ein.getProgramNumber()) && (node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber()))) {
			node.skReset();
		} else {
	    	node.setBlink();
		    
		    if  (node.isRegularProgram() || (node.getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber())) {
		    	node.skSet();
		    } else {
		    	node.skReset();
		    }
		}

	    if (node.getNummer() == 1 && (Var.controller.central != null)) {
            Var.controller.updateMovesErrorStates();
	    	Var.controller.central.setReplyToCenter();
	    	Var.controller.resetMovesErrorStates();
	    }
	}
}
