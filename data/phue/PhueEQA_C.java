package phue;

import m0075.Tk1;
import vt.*;
import sg.*;
import special.ExtendedPhue;

public class PhueEQA_C extends ExtendedPhue {
	private static Tk1 _tk;

	public PhueEQA_C(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k1.setSg (Zustand.ROT,    0);
		_tk.k2.setSg (Zustand.ROT,    0);
		_tk.pd.setSg (Zustand.ROT,    4);
	
		_tk.k3.setSg (Zustand.GRUEN,  7);
		_tk.pb.setSg (Zustand.GRUEN, 10);
		_tk.pe.setSg (Zustand.GRUEN,  9);
		_tk.pf.setSg (Zustand.GRUEN,  9);
	
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}