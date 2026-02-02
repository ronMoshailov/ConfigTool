package ta172;

import vt.*;
import special.InterStage;

public class PhueEQAB extends InterStage {
	private static Tk1 _tk;

	public PhueEQAB(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k5.TurnOff ( 6);
		_tk.pb.TurnOff ( 0);
		_tk.Bb.TurnOff (11);

		_tk.k8.TurnOn  (11);
		_tk.pc.TurnOn  (11);
		_tk.ph.TurnOn  (13);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}