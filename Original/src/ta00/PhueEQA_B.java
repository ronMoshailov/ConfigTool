package ta118;

import vt.*;
import special.InterStage;

public class PhueEQA_B extends InterStage {
	private static Tk1 _tk;

	public PhueEQA_B(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k1.TurnOff (3);
		_tk.pc.TurnOff (0);
		_tk.pf.TurnOff (0);
		_tk.Bf.TurnOff (8);

		_tk.k2.TurnOn (13);
		_tk.k3.TurnOn (13);
		_tk.k4.TurnOn  (9);
		_tk.pa.TurnOn  (8);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}
		return KEINE_UMSCHALTUNG;
	}
}