package core.programs;

import m0547.Var;
import core.Node;
import core.Node.SpecialProgram;
import core.central.Dvi35Haifa;
import vt.*;

public class HandProgramm extends FestProg{
	private Node node;

	// For the None Fixed Time Police Program this class can be skipped
	public HandProgramm(Node node, String name, int nr, int tu, int gwpa, int gwpb, int wait, int offset) {
		super(node, name, nr, tu, gwpa, gwpb, wait, offset);
		this.node = node;
	}

	private void stopCycleTime (int stoppoint) {
        if  (node.getProgZeit() == stoppoint * Var.ONE_SEC_MS - node.getZyklDauer()) {
            if  (((Var.controller.policePanel.FS.getPosFlanken() == 0) && (!Dvi35Haifa.hand_mode_auto || !Dvi35Haifa.hand_advance)) &&
        		 ((this.getNummer() == node.getProgAnf().getProg().getNummer()) || (Dvi35Haifa.hand_mode_auto))) {
                node.freezeTime();
            }
        }
    }
	
	public void programmFunktion(){
		
		node.setFixedPoliceSG();
		
		if (Var.controller.policeStopPoints != null) {
            for (int i=0; i < Var.controller.policeStopPoints.length; i++) {
            	stopCycleTime(Var.controller.parameters.getParameterAt(Programm.findByNum(SpecialProgram.Police.getProgramNumber(), node.getNummer()), i));
            }
		}
	}
}