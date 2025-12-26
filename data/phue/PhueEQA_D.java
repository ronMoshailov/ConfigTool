package phue;

import m0075.Tk1;
import vt.*;
import sg.*;
import special.ExtendedPhue;

public class PhueEQA_D extends ExtendedPhue {
	private static Tk1 _tk;

	public PhueEQA_D(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k1.setSg (Zustand.ROT,    0);
		_tk.k2.setSg (Zustand.ROT,    1);
		_tk.pc.setSg (Zustand.ROT,    2);
	
		_tk.k4.setSg (Zustand.GRUEN,  7);
		_tk.pa.setSg (Zustand.GRUEN, 11);
		_tk.pb.setSg (Zustand.GRUEN, 10);
		_tk.pe.setSg (Zustand.GRUEN,  9);
	
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}