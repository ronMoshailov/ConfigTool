package ta118;

import vt.*;
import special.InterStage;

public class PhueC_A extends InterStage {
	private static Tk1 _tk;

	public PhueC_A(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k2.TurnOff (0);
		_tk.k5.TurnOff (2);
		_tk.pa.TurnOff (1);

		_tk.k1.TurnOn  (8);
		_tk.pc.TurnOn  (7);
		_tk.Bf.TurnOn  (5);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}
		return KEINE_UMSCHALTUNG;
	}
}