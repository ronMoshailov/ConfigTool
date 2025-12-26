package phue;

import m0075.Tk1;
import vt.*;
import sg.*;
import special.ExtendedPhue;

public class PhueD1_A extends ExtendedPhue {
	private static Tk1 _tk;

	public PhueD1_A(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k4.setSg (Zustand.ROT,    6);
		_tk.pa.setSg (Zustand.ROT,    5);
		_tk.pb.setSg (Zustand.ROT,    8);
		_tk.pe.setSg (Zustand.ROT,    7);
	
		_tk.k1.setSg (Zustand.GRUEN,  12);
		_tk.k2.setSg (Zustand.GRUEN,  13);
		_tk.pc.setSg (Zustand.GRUEN,  14);
	
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}