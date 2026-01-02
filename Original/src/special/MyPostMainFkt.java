package special;

import hw.*;
import special.Node.SpecialProgram;
import tk.Var;
import vt.*;
import vtvar.*;


public class MyPostMainFkt extends PostMainFkt {
	private Node node;

	public MyPostMainFkt (Node node) {
		super(node);
		this.node = node;
	}
	
	protected void inJedemZyklus() {

		node.isAlreadyFrozen = false;
		node.lastActiveProgram = node.getAktProg().getNummer();
		
	    if (node.getNummer() == 1) {
	    	Signs.SignsOn();
	    }
	    
		DebugVar.updateAll();

	    if (node.getNummer() == 1) {
	    	SpecialInOuts.checkUpsPower();
	    }
		
		if  ((node.getAktProg() == node.getDunkelProgramm()) || (node.getAktProg() == node.getBlinkProgramm())) {
			node.skReset();
		} else {
		    if  (((node.getAktProg().getNummer() == SpecialProgram.Ein.getProgramNumber()) && (node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber())) ||
		        (node.getAktProg().getNummer() == SpecialProgram.Dark.getProgramNumber()))
		    { 
		    	node.skReset();
		    } else {
		    	node.setBlink();
			    if  (node.isRegularProgram() || (node.getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber())) {
			    	node.skSet();
			    } else {
			    	node.skReset();
			    }
			    
			    if (AnlagenZustand.isAbschaltgrad1(node) || AnlagenZustand.isAbschaltgrad2(node)) {
			    	node.skReset();
			    }
		    }
		}
	    if (node.getNummer() == 1 && (Var.controller.isAppHaifa() || Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv())) {
	    	Var.controller.dvi35.setReplyToCenter();
	    }
	}
}
