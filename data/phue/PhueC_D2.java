package phue;

import m0075.Tk1;
import vt.*;
import sg.*;
import special.ExtendedPhue;

public class PhueC_D2 extends ExtendedPhue {
	private static Tk1 _tk;

	public PhueC_D2(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
 		_tk.k3.setSg (Zustand.ROT,    0);
 		_tk.pc.setSg (Zustand.ROT,    1);
 		_tk.pf.setSg (Zustand.ROT,    0);

		_tk.k4.setSg (Zustand.GRUEN,  6);
		_tk.pa.setSg (Zustand.GRUEN,  9);
		_tk.pd.setSg (Zustand.GRUEN,  8);
	
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}