package ta172;

import vt.*;
import special.InterStage;

public class PhueBD extends InterStage {
	private static Tk1 _tk;

	public PhueBD(Tk1 tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(tk, name, laenge, quelle, ziel);
		_tk = tk;
	}

	public Phase phasenFunktion() {
		_tk.k7.TurnOff (4);
		_tk.k8.TurnOff (4);
		_tk.pe.TurnOff (0);
		_tk.ph.TurnOff (0);
		
		_tk.k1.TurnOn (10);
		_tk.pd.TurnOn (11);
		_tk.pg.TurnOn ( 9);
		
		if (isTargetStageBuilt()) {
			_tk.lenPhue = this.getPhasenZeit();
			this.entfernen();
		}

		return KEINE_UMSCHALTUNG;
	}
}