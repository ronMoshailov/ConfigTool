package core.programs;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 201                                                                     	*
 *  Junction Name  : HaHistadrut / Ramp-Off North Bridge										*
 *  Equipmentno.   : h201                                                                    	*
 *                                                                                              *
 ************************************************************************************************/
import m0547.Var;
import core.Node;
import core.Node.SpecialProgram;
import vt.*;

public class EinProgramm extends EinProg{
	protected Node node;

	public EinProgramm(Node node, String name, int nr, int tu) {
		super(node, name, nr, tu);
		this.node = node;
	}

	public void programmFunktion(){

        if ((node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) && (node.getProgSek() == 0))
        {
        	node.setProgZeit((node.startprog.getUmlaufZeit() * Var.ONE_SEC_MS) - node.getZyklDauer());
            return;
        }

        node.setSGtoEinProg(0);
        
        if  (node.getProgSek() ==  node.startprog.getUmlaufZeit() - 1)
        {
        	if (node.getProgWunsch(node, StgEbene.STG_MANUELL) != Vt.KEIN_PROGRAMMWUNSCH) {
        		if (node.getProgWunsch(node, StgEbene.STG_MANUELL).getNummer() == SpecialProgram.Police.getProgramNumber()) {
        			node.setProgWunsch(null, StgEbene.STG_MANUELL);
        		}
        	}
        	if (node.getProgWunsch(node, StgEbene.STG_ZENTRALE) != Vt.KEIN_PROGRAMMWUNSCH) {
        		if (node.getProgWunsch(node, StgEbene.STG_ZENTRALE).getNummer() == SpecialProgram.Police.getProgramNumber()) {
        			node.setProgWunsch(null, StgEbene.STG_ZENTRALE);
        		}
        	}
        	node.startNode();
        	node.resetCounters();
        }

        node.greenwaveReset();
	}
}