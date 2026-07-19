package j200;

import vt.*;
import special.InterStage;

public class PhueBA extends InterStage {
	private static Tk1 _tk;

	public PhueBA(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k4.TurnOff	(3);
		_tk.k5.TurnOff	(1);
		_tk.pa.TurnOff 	(1);
		_tk.pe.TurnOff 	(0);
		_tk.pg.TurnOff 	(2);
		_tk.Bg.TurnOff  (8);
		
		_tk.k1.TurnOn  (12);
		_tk.k10.TurnOn  (7);
		_tk.k3.TurnOn  (12);
		_tk.pd.TurnOn   (8);
		_tk.pf.TurnOn   (6);
		_tk.ph.TurnOn   (9);
		_tk.Bd.TurnOn 	(9);
		_tk.Bh.TurnOn 	(9);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}