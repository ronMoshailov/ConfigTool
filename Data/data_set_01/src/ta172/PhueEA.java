package ta172;

import vt.*;
import special.InterStage;

public class PhueEA extends InterStage {
	private static Tk1 _tk;

	public PhueEA(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k6.TurnOff ( 3);
		_tk.pd.TurnOff ( 4);
		_tk.pg.TurnOff ( 0);
		
		_tk.k7.TurnOn  (10);
		_tk.pf.TurnOn  (11);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}