package ta118;

import vt.*;
import special.InterStage;

public class PhueB_C extends InterStage {
	private static Tk1 _tk;

	public PhueB_C(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k3.TurnOff (0);
		_tk.k4.TurnOff (0);
		
		_tk.k5.TurnOn (5);
		_tk.pf.TurnOn (8);

		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}
		return KEINE_UMSCHALTUNG;
	}
}