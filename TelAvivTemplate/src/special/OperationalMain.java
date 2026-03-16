package special;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 201                                                                     	*
 *  Junction Name  : HaHistadrut / Ramp-Off North Bridge										*
 *  Equipmentno.   : h201                                                                    	*
 *                                                                                              *
 ************************************************************************************************/
import special.Node.SpecialProgram;
import ta172.GreenWave;
import ta172.Var;
import vt.*;

public class OperationalMain extends MainFkt{
	private Node node;
	
	public OperationalMain(Node node) {
		super(node);
		this.node = node;
	}
    
	public void inJedemZyklus() {
		if (SpecialInOuts.reset != null) {
			SpecialInOuts.reset.UpdateState();
		}
		
		if ((!node.isRegularProgram() && !node.isInMaintenance() && !node.isInPoliceProgram()) || node.isOffline())
		{
			Node.Sync(node.getNummer());
			node.MainPhase.ResetCounters();
			node.timeToStartMapOnly = -1;
		}
		
	    node.getCurrCycleSec = node.getProgZeit();
	    
		vt.Programm actProg;
		actProg = node.getAktProg();
		if  (actProg == node.getDunkelProgramm()) {return;}
		
		++node.countVtVarUpdate;
		if (node.countVtVarUpdate >= node.checkVtVarUpdate)
		{
			vtvar.VtVar.update(node);
			node.countVtVarUpdate = 0;
		}

		Func.GlobalUpdateHaifa(node, actProg);

	    if (node.getNummer() == 1 && (Var.controller.isAppHaifa() || Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv())) {
	    	Var.controller.dvi35.getId1();
	    }
	    
		Func.GlobalParam_update(node);
		
		if ((node.isInMaintenance() || node.isMaintenanceProgramRequest()) &&
			node.getProgWunsch(node, StgEbene.STG_ZENTRALE) == Vt.KEIN_PROGRAMMWUNSCH) {
			if (node.getProgWunsch(node, StgEbene.STG_MANUELL) != Vt.KEIN_PROGRAMMWUNSCH &&
				node.getProgWunsch(node, StgEbene.STG_MANUELL).getNummer() != (node.getProgWunsch(node, StgEbene.STG_UHR).getNummer() + SpecialProgram.MaintenanceFirst.getProgramNumber() - 1)) {
				
				node.setProgWunsch(Vt.findProgByNum(node.getProgWunsch(node, StgEbene.STG_UHR).getNummer() + SpecialProgram.MaintenanceFirst.getProgramNumber() - 1, node.getNummer()), StgEbene.STG_MANUELL);
			}
		}

	    if (node.getNummer() == 1) {
	    	GreenWave.operateGW();
	    	Tableau.checkEvent();
	    	Var.controller.dvi35.setStatus();
	    	GreenWave.SetGWactiveLed();
	    	SpecialInOuts.setCheckPulse();
	    }
	    
	    node.checkMap();
	    node.checkDetectors();
	    node.operationMainFunction();
	    
	    if (Var.controller.autoReset != null) {
	    	Var.controller.autoReset.run();
	    }
	}
}