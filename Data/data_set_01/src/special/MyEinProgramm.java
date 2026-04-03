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
import vt.*;

public class MyEinProgramm extends EinProg{
	private Node node;

	public MyEinProgramm(Node node, String name, int nr, int tu) {
		super(node, name, nr, tu);
		this.node = node;
	}

	public void programmFunktion(){

        if  ((node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) && (node.getProgSek() == 0))
        {
        	node.setProgZeit((node.startprog.getUmlaufZeit()*1000)-500);
            return;
        }

        //"EinProgramm" (Knisa LePeula)
        node.setSGtoEinProg(0);

        if  (node.getProgSek() ==  6)
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

		//for green wave
		GreenWave.oldebene = node.getAktStgEbene();
    	GreenWave.ResetFaultCounter();
	}
}