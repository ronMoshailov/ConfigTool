package core.programs;

import m0547.Var;
import core.Node;
import vt.*;

public class VAProg extends LogikProg {
    protected Node node;
    
	public VAProg(Node kn, String name, int num,
			int umlZeit, int gwpa, int gwpb, int warteZeit,
			int versatzZeit) {
		super(kn, name, num, umlZeit, gwpa, gwpb, warteZeit, versatzZeit);
		node = kn;
	}

	public void programmFunktion() {
		if (node.isRegularProgram() || node.isMapOnlyProgram())
        {
			Var.controller.parameters.parameters = Var.controller.parameters.getParameters(this);
			if (Var.controller.isAppJerusalem()) {
				Var.controller.parameters.parametersInner = Var.controller.parameters.getInnerParameters(this);
                Var.controller.parameters.parametersDetectors = Var.controller.parameters.getGapsParameters(getNummer());
			} else if (Var.controller.isAppNetiveiIsrael()) {
			    Var.controller.parameters.parametersDetectors = Var.controller.parameters.getGapsParameters(getNummer());
			}
        }
	}
}