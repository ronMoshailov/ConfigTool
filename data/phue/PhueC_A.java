package phue;

import m0075.Tk1;
import vt.*;
import sg.*;
import special.ExtendedPhue;

public class PhueC_A extends ExtendedPhue {
	private static Tk1 _tk;

	public PhueC_A(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
 		_tk.k3.setSg (Zustand.ROT,    0);
 		_tk.pb.setSg (Zustand.ROT,    3);
 		_tk.pe.setSg (Zustand.ROT,    2);
 		_tk.pf.setSg (Zustand.ROT,    0);

		_tk.k1.setSg (Zustand.GRUEN,  7);
		_tk.k2.setSg (Zustand.GRUEN,  7);
		_tk.pd.setSg (Zustand.GRUEN,  8);
	
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}