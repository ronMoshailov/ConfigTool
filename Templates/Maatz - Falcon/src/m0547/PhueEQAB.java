package m0547;

import core.InterStage;
import vt.*;

public class PhueEQAB extends InterStage {
    protected Tk1 node;

    public PhueEQAB(Tk1 node, String name, int laenge, Phase quelle, Phase ziel) {
	    super(node, name, laenge, quelle, ziel);
        this.node = node;
    }

	public Phase phasenFunktion() {
	    
		node.k1.turnOff(  3);

		node.k4.turnOn (  8);

	    if (isTargetStageBuilt()) {
	        deactivated();
		    this.entfernen();
	    }

	    return KEINE_UMSCHALTUNG;
    }
}