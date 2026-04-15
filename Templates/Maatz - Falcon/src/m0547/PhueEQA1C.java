package m0547;

import core.InterStage;
import vt.*;

public class PhueEQA1C extends InterStage {
    protected Tk1 node;

    public PhueEQA1C(Tk1 node, String name, int laenge, Phase quelle, Phase ziel) {
	    super(node, name, laenge, quelle, ziel);
        this.node = node;
    }

	public Phase phasenFunktion() {
	    
	    node.k1.turnOff(  3);
		node.k2.turnOff(  3);

		node.k3.turnOn (  8);

	    if (isTargetStageBuilt()) {
	        deactivated();
		    this.entfernen();
	    }

	    return KEINE_UMSCHALTUNG;
    }
}