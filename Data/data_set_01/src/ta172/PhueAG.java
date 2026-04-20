package ta172;

import vt.*;
import special.InterStage;

public class PhueDE extends InterStage {
	private static Tk1 _tk;

	public PhueDE(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k1.TurnOff (7);
		_tk.pc.TurnOff (0);
		_tk.pf.TurnOff (9);
		
		_tk.k5.TurnOn (17);
		_tk.k6.TurnOn (17);
		_tk.pb.TurnOn (14);
		_tk.pe.TurnOn (15);
		_tk.Bb.TurnOn (12);
		
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}