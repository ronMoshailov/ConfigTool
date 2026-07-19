package j200;

import vt.*;
import special.InterStage;

public class PhueAC extends InterStage {
	private static Tk1 _tk;

	public PhueAC(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k1.TurnOff	(1);
		_tk.k10.TurnOff	(2);
		_tk.k3.TurnOff	(2);
		_tk.pc.TurnOff	(0);
		_tk.ph.TurnOff	(2);
		_tk.Bd.TurnOff	(6);
		_tk.Bh.TurnOff	(7);
		
		_tk.k6.TurnOn   (8);
		_tk.pa.TurnOn 	(7);
		_tk.pb.TurnOn 	(9);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}