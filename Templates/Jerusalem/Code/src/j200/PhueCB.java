package j200;

import vt.*;
import special.InterStage;

public class PhueCB extends InterStage {
	private static Tk1 _tk;

	public PhueCB(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k6.TurnOff	(1);
		_tk.pb.TurnOff	(5);
		_tk.pd.TurnOff	(2);
		_tk.pf.TurnOff	(0);
		
		_tk.k4.TurnOn  (12);
		_tk.k5.TurnOn   (8);
		_tk.pc.TurnOn 	(6);
		_tk.pe.TurnOn   (9);
		_tk.pg.TurnOn   (9);
		_tk.Bg.TurnOn 	(9);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}