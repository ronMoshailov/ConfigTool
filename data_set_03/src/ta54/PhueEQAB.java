package ta54;

import vt.*;
import special.InterStage;

public class PhueEQAB extends InterStage {
	private static Tk1 _tk;

	public PhueEQAB(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k1.TurnOff (0);
		_tk.k2.TurnOff (0);
		_tk.k3.TurnOff (0);
		_tk.k4.TurnOff (0);

		_tk.pa.TurnOn  (7);
		_tk.pb.TurnOn  (7);
		_tk.pc.TurnOn  (7);
		_tk.pd.TurnOn  (7);
		_tk.pe.TurnOn  (5);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}