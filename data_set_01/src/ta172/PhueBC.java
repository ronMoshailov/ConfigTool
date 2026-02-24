package ta172;

import vt.*;
import special.InterStage;

public class PhueBC extends InterStage {
	private static Tk1 _tk;

	public PhueBC(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k7.TurnOff (5);
		_tk.k8.TurnOff (5);
		_tk.pa.TurnOff (0);
		_tk.pf.TurnOff (3);
		
		_tk.k3.TurnOn (10);
		_tk.pg.TurnOn (10);
		
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}