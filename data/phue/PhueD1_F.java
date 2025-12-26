package phue;

import m0075.Tk1;
import vt.*;
import sg.*;
import special.ExtendedPhue;

public class PhueD1_F extends ExtendedPhue {
	private static Tk1 _tk;

	public PhueD1_F(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k4.setSg (Zustand.ROT,    0);
		_tk.pb.setSg (Zustand.ROT,    2);
		_tk.pe.setSg (Zustand.ROT,    1);
	
		_tk.k1.setSg (Zustand.GRUEN,  6);
		_tk.pc.setSg (Zustand.GRUEN,  8);
		_tk.pf.setSg (Zustand.GRUEN,  9);
	
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}