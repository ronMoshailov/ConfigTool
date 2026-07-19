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
import core.Node;
import core.Node.SpecialProgram;
import m0547.Var;
import vt.*;
import sg.*;


public class DarkProg extends FestProg{
	private boolean firstcycle;
	private Node node;
	private int startMovesSecond = 8;

	public DarkProg(Node node, String name, int nr, int tu, int gwpa, int gwpb, int wait, int offset) {
		super(node, name, nr, tu, gwpa, gwpb, wait, offset);
		this.node = node;
	}

	public void programmFunktion(){
		if  ((!firstcycle) && (node.getProgSek() < 1))
		{
			node.setProgSek(0);
			//"turning off" all traffic and pedestrian sg
			node.setTrafficSG        (Zustand.DUNKEL, 0);
			node.allPedestrianSGDunkel(0);
			node.setPreemptionTriangles(0);
			//"turning off" all blinks
			node.setConditionedBlinks(Zustand.AUS, 0);
			node.setRegularBlinks    (Zustand.AUS, 0);

			firstcycle = true;
		}

		if  (node.getProgSek() >= 1) 
		{
			firstcycle = false;
		}

		if  ((node.getProgZeit() >= 5 * Var.ONE_SEC_MS) && (node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber()))
		{
			node.freezeTime(startMovesSecond - 1);;
			return ;
		}

		//"EinProgramm" (Knisa LePeula)
		node.setSGtoEinProg(startMovesSecond);

		if  (node.getProgSek() == startMovesSecond + node.startprog.getUmlaufZeit() - 1)
		{
			node.startNode();
			node.resetCounters();
		}
	}
}