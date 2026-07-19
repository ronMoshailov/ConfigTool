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
import j200.Var;
import special.Node.SpecialProgram;
import vt.*;

public class MyHandProgramm extends FestProg{
	private Node node;

	// For the None Fixed Time Police Program this class can be skipped
	public MyHandProgramm(Node node, String name, int nr, int tu, int gwpa, int gwpb, int wait, int offset) {
		super(node, name, nr, tu, gwpa, gwpb, wait, offset);
		this.node = node;
	}

	private void stopCycleTime (int stoppoint) {
        if  (node.getProgZeit() == stoppoint) {
            if  (((PoliceKeyboard.FS.getPosFlanken() == 0) && (!Dvi35Haifa.hand_mode_auto || !Dvi35Haifa.hand_advance)) &&
        		 ((this.getNummer() == node.getProgAnf().getProg().getNummer()) || (Dvi35Haifa.hand_mode_auto))) {
                if  (node.getProgZeit() > 0) {
                    node.setProgZeit(stoppoint - node.getZyklDauer());
                    return ;
                }
                node.setProgZeit(this.umlaufZeit - node.getZyklDauer());
            }
        }
    }
	
	public void programmFunktion(){
		
		node.setFixedPoliceSG();
		
		// assigns the stop points:
        for (int i=0; i < Var.STOP_PUNKTE.length; i++) 
        {
        	stopCycleTime(Func.readParam(SpecialProgram.Police.getProgramNumber(), i));
        }
	}
}