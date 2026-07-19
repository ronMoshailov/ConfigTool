package core.programs;

import core.Node;
import core.Node.SpecialProgram;
import vt.*;

public class Umschalt extends UmschaltFkt {
	protected Node node;
	
	public Umschalt(Node node) {
		super(node.getId());
		this.node = node;
	}

	private boolean Default() {
		int t41tk1 = node.getProgSek();
		int gwpatk1= node.getAktProg().getGwpA();
		int gwpbtk1= node.getAktProg().getGwpB();

		if (gwpbtk1 > gwpatk1)
		{
			if ((t41tk1 >= gwpatk1) && (t41tk1 <= gwpbtk1)) { return true; }
			return false; 
		}
		else if (gwpatk1 > gwpbtk1)
		{
			if ((t41tk1 >= gwpatk1) || (t41tk1 <= gwpbtk1)) { return true; }
			return false;
		}
		else
		{
			if (t41tk1 == gwpatk1) { return true; }
			return false;
		}
	}

	public boolean umschFkt() {
		//for immediate transition to the blink or dark program this checking must be done the first in the function 
		if ((node.getProgAnf().getProg().getNummer() == SpecialProgram.Flash.getProgramNumber()) &&
			(node.getAktProg()          .getNummer() != SpecialProgram.Flash.getProgramNumber())) 
		{
			return true;
		} 
		
		if ((node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) &&
			(node.getAktProg()          .getNummer() != SpecialProgram.Dark.getProgramNumber())) 
		{
			return true;
		}
		
		if (node.isMapOnlyProgram()) {
			if (node.isMaintenanceProgramRequest()) {
				if (node.MainPhase[0].isAktiv() && Default())
					return true;
				return false;
			} else {
				if (node.MainPhase[0].isStageBuilt() && Default())
					return true;
				return false;
			}
		}

        //main phase
		if (node.isMainPhase())
		{
		    if  (node.isRegularProgram() || (node.getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber()))
			{
				if (Default()) 
				{
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