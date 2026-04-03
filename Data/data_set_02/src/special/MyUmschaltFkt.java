package special;
import special.Node.SpecialProgram;
import vt.*;

public class MyUmschaltFkt extends UmschaltFkt {
	private Node node;
	
	public MyUmschaltFkt(Node node) {
		super(node.getId());
		this.node = node;
	}

	private boolean Default() {
		int t41tk1 = node.getProgSek();
		int gwpatk1= node.getAktProg().getGwpA();
		int gwpbtk1= node.getAktProg().getGwpB();

		if (gwpbtk1 > gwpatk1) {
			if ((t41tk1 >= gwpatk1) && (t41tk1 <= gwpbtk1)) { return true; }
			return false; 
		} else if (gwpatk1 > gwpbtk1) {
			if ((t41tk1 >= gwpatk1) || (t41tk1 <= gwpbtk1)) { return true; }
			return false;
		} else {
			if (t41tk1 == gwpatk1) { return true; }
			return false;
		}
	}

	public boolean umschFkt() {
		//for immediate transition to the blink or dark program this checking must be done the first in the function 
		if ((node.getProgAnf().getProg().getNummer() == SpecialProgram.Flash.getProgramNumber()) &&
			(node.getAktProg().getNummer() != SpecialProgram.Flash.getProgramNumber())) 
		{
			return true;
		} 
		
		if ((node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) &&
			(node.getAktProg().getNummer() != SpecialProgram.Dark.getProgramNumber())) 
		{
			return true;
		}
		
		if (node.isInMaintenance()) {
			if (node.isMaintenanceProgramRequest()) {
				if (node.MainPhase.isAktiv() && Default()) {
					return true;
				}
				return false;
			} else {
				return node.MainPhase.isStageGreenBuilt() && Default();
			}
		}

        //main phase
//		System.out.println("Umschalt: " + node.getProgZeit() + "(" + node.getAktProg().getNummer() + ")");
		if (node.isMainPhase()) {
//			System.out.println("1. main phase");
		    if  (node.isRegularProgram() || (node.getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber())) {
//		    	System.out.println("2. regular prog");
				if (Default())  {
//					System.out.println("3. syncA/B");
					node.CycleCount = 0;
					return true;
				}
				return false;
			}
		    node.CycleCount = 0;
			return true;
		} 
		return false;
	}
}