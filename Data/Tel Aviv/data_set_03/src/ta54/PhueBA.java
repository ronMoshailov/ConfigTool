package ta54;

import vt.*;
import special.InterStage;

public class PhueBA extends InterStage {
	private static Tk1 _tk;

	public PhueBA(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.pa.TurnOff (1);
		_tk.pb.TurnOff (8);
		_tk.pc.TurnOff (0);
		_tk.pd.TurnOff (9);
		_tk.pe.TurnOff (0);
		
		_tk.k1.TurnOn (14);
		_tk.k2.TurnOn (14);
		_tk.k3.TurnOn (13);
		_tk.k4.TurnOn (14);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}