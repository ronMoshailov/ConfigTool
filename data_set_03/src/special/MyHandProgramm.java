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
import ta54.Var;
import vt.*;

public class MyHandProgramm extends FestProg{
	private Node node;

	// For the None Fixed Time Police Program this class can be skipped
	public MyHandProgramm(Node node, String name, int nr, int tu, int gwpa, int gwpb, int wait, int offset) {
		super(node, name, nr, tu, gwpa, gwpb, wait, offset);
		this.node = node;
	}

	private void stopCycleTime (int stoppoint, int stoppointIndex) {
        if  (node.getProgZeit() == stoppoint) {
            if  (((PoliceKeyboard.FS.getPosFlanken() == 0)) &&
        		 ((this.getNummer() == node.getProgAnf().getProg().getNummer()))) {
                if  (node.getProgZeit() > 0) {
                    node.setProgZeit(stoppoint - node.getZyklDauer());
                } else {
                	node.setProgZeit(this.umlaufZeit * Var.ONE_SEC - node.getZyklDauer());
                }
                return;
            } else {
            	node.setProgZeit(Func.readParamPolice(stoppointIndex, true) - node.getZyklDauer());
            }
        }
		System.out.println();
    }
	
	public void programmFunktion(){
		
		node.setFixedPoliceSG();
		
		// assigns the stop points:
        for (int i=0; i < Var.STOP_PUNKTE.length; i++) 
        {
        	stopCycleTime(Func.readParamPolice(i, false), i);
        }
		
		node.setFixedPoliceSG();
	}
}