package special;
import ta172.Var;
import vt.*;

abstract public class InterStage extends PhasenUebergang {
	private static final int MAX_INTER_STAGES = 100;
	private static InterStage[] _phues = new InterStage[MAX_INTER_STAGES];
	private static int _phue_count = 0;
	private int _length;

	public InterStage(Node tk, String name, int laenge, Phase quelle, Phase ziel) {
		super(name, 99, quelle, ziel);
		_length = laenge;
		if (Var.controller.isPreemption()) {
			_phues[_phue_count] = this;
		}
		_phue_count++;
	}
	
	public int getPhueLen() {
		return _length;
	}
	
	public int isgDuration() {
		return _length*Var.ONE_SEC;
	}
	
	public static int IsgDuration(Stage out, Stage in) {
		
		if (!Var.controller.isPreemption()) {
			return 0;
		}
		for (int i = 0; i < _phue_count; i++) {
			if (_phues[i].getQuellPhase().getNummer() == out.getNummer() &&
				_phues[i].getZielPhase().getNummer() == in.getNummer()) {
				return _phues[i]._length*Var.ONE_SEC;
			}
		}
		return 0;
	}
	
	public  boolean isTargetStageBuilt()
	{
	//	if (((ExtendedPhase)getZielPhase()).isStageBuilt() && _tk.lenPhueSG == ExtendedPhase.MaxPhueLenght) {
	//		_tk.lenPhueSG = getPhasenZeit();
	//	}
		
		if (_length == 99) {
			return ((Stage)getZielPhase()).isStageBuilt();
		}
		return ((Stage)getZielPhase()).isStageBuilt() && getPhasenZeit()>=_length*Var.ONE_SEC;
	}
}