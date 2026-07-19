package core.programs;

import m0547.Var;
import itc.ExtendedUdp;
import core.Node;
import core.Scheduler;
import core.central.iCentral;
import vt.*;

public class OperationalMain extends MainFkt{
	protected Node node;
	
	public OperationalMain(Node node) {
		super(node);
		this.node = node;
	}
    
	public void inJedemZyklus() {
	    node.updateActiveStage();
        node.updateMovesStates();
        node.checkDetectors();
        
        if  (node.getAktProg() == node.getDunkelProgramm())
        {
            return;
        }
		
        node.scheduler.executeScheduler();
		
		if ((!node.isRegularProgram() && !node.isMapOnlyProgram()) || node.isOffline())
		{
			node.resetCounters();
			node.timeToStartMapOnly = -1;
		}
		
		// TODO: check if map only is required
		/*
		if ((node.isMapOnlyProgram() || node.isMaintenanceProgramRequest()) &&
			node.centralProgram == Var.NONE) {
			if (node.getProgWunsch(node, StgEbene.STG_MANUELL) != Vt.KEIN_PROGRAMMWUNSCH &&
				node.getProgWunsch(node, StgEbene.STG_MANUELL).getNummer() != (node.getProgWunsch(node, StgEbene.STG_UHR).getNummer() + SpecialProgram.MaintenanceFirst.getProgramNumber() - 1)) {
				
				node.setProgWunsch(Vt.findProgByNum(node.getProgWunsch(node, StgEbene.STG_UHR).getNummer() + SpecialProgram.MaintenanceFirst.getProgramNumber() - 1, node.getNummer()), StgEbene.STG_MANUELL);
			}
		}
		*/
		
		Var.controller.parameters.updateParameters(node);

        if (node.isRegularProgram()) {
            node.currStructure = Var.controller.parameters.getStructure(node.getNummer());
        } else {
            node.currStage = null;
        }
        
	    if (node.getNummer() == 1 && Var.controller.central != null) {
	    	Var.controller.central.monitorTelegram();
	    }

		node.updateCumulativeState();
	    if (node.getNummer() == 1) {
	        if (Var.controller.autoReset != null) {
	            Var.controller.autoReset.run();
	        }
            if (Var.controller.isUseSIT()) {
                Var.controller.sit.receiveMessage();
            }
	    	Var.controller.policePanel.checkEvent();
            ExtendedUdp.receiveAll();
	    }
        node.greenwaveOperateGW();
        if (node.itc != null) {
            node.itc.runITCLogic();
        }
	    
	    node.operationMainFunction();
        
        iCentral .activateProgram(node);
        Scheduler.activateProgram(node);
	}
}