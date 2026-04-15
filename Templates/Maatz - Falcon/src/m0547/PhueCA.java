package m0547;

import core.InterStage;
import vt.*;

public class PhueCA extends InterStage {
    protected Tk1 node;

    public PhueCA(Tk1 node, String name, int laenge, Phase quelle, Phase ziel) {
	    super(node, name, laenge, quelle, ziel);
        this.node = node;
    }

	public Phase phasenFunktion() {
	    
		node.k3.turnOff(  3);

		node.k1.turnOn (  8);
		node.k2.turnOn (  8);

	    if (isTargetStageBuilt()) {
	        deactivated();
		    this.entfernen();
	    }

	    return KEINE_UMSCHALTUNG;
    }
}