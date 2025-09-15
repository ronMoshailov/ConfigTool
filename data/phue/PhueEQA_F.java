package phue;

import m0075.Tk1;
import vt.*;
import sg.*;
import special.ExtendedPhue;

public class PhueEQA_F extends ExtendedPhue {
	private static Tk1 _tk;

	public PhueEQA_F(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k2.setSg (Zustand.ROT,    0);
	
		_tk.pa.setSg (Zustand.GRUEN, 10);
		_tk.pf.setSg (Zustand.GRUEN,  9);
	
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}