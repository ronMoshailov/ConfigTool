package core;

import m0547.Var;
import java.util.ArrayList;
import vt.*;

abstract public class InterStage extends PhasenUebergang {
    
    public static final int PHUE_MAX_LENGTH = 99;
    
	private static ArrayList<InterStage> interstages = new ArrayList<InterStage>();
	private static int interstageIndex;
    
    public static int IsgDuration(Stage out, Stage in) {
        for (interstageIndex = 0; interstageIndex < interstages.size(); interstageIndex++) {
            if (interstages.get(interstageIndex).getQuellPhase().getNummer() == out.getNummer() &&
                interstages.get(interstageIndex).getZielPhase() .getNummer() == in .getNummer()) {
                return interstages.get(interstageIndex).getPhueLen();
            }
        }
        return 0;
    }
	
	protected Node node;
	protected int length;
	public final int localNumber;
	
	public InterStage(Node tk, String name, int length, Phase quelle, Phase ziel) {
		super(name, 99, quelle, ziel);
		this.node = tk;
		this.length = length;
		this.localNumber = ++Controller.stagesAndInterstagesCounter;
		interstages.add(this);
	}
	
	public int getPhueLen() {
		return this.length;
	}
    
    public int getPhueLenMS() {
        return this.length * Var.ONE_SEC_MS;
    }
    
    public int isgDuration() {
        return getPhueLen();
    }
    
    public int isgDurationMS() {
        return getPhueLenMS();
    }
	
	public boolean isTargetStageBuilt()
	{
		if (getPhueLen() == 99) {
			return ((Stage)getZielPhase()).isStageBuilt();
		}
		return ((Stage)getZielPhase()).isStageBuilt() && getPhasenSek() >= getPhueLen();
	}
	
	protected void deactivated() {
		node.isInInterstage = false;
		if (!((Stage)getZielPhase()).isVirtual) {
		    node.lenPhue = getPhasenSek();
		}
	}
}