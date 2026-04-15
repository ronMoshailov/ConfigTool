package core;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 8                                                                      	*
 *  Junction Name  : Hertzel / Bar Kohva / Bilu													*
 *  Equipmentno.   : h8                                                                     	*
 *                                                                                              *
 ************************************************************************************************/
import vt.*;

public class OutOfMapOnlyStage extends Stage {
	private Node node; 
	
	public OutOfMapOnlyStage(Node node, Move[] moves) {
		super(node, "OutOfMapOnly", 1, node.getEinschaltProgramm().getUmlaufZeit(), false, moves);
		this.node = node;
	}

	protected void deactivated(Phase targetPhase) {
		
	}
	
	protected void activated() {
		
	}

    public int getMaxType() {
        return 0;
    }

    public int getMinType() {
        return 0;
    }

	public int getMaxExt() {
		return 0;
	}

	public int getMinExt() {
		return 0;
	}

    protected void beforeSkeletonDone() {
        
    }
    
	public Phase phasenFunktion() {
		if (getPhasenZeit() >= getPhLenMS()) {
			return switchStage(node.MainPhase[0]);
		}
		node.setExitMapOnly();
		return KEINE_UMSCHALTUNG;
	}
}
