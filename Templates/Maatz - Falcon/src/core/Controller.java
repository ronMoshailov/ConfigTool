package core;

import m0547.Var;
import itc.ITCProtocol;
import java.util.ArrayList;
import parameters.*;
import uhr.Zeit;
import core.central.*;
import core.detectors.Input;
import core.detectors.Output;
import core.parameters.Parameters;
import modbus.JLRTModBus;
import hw.AnlagenFehler;
import hw.AnlagenZustand;

public class Controller {
	
	public static final int DEFAULT_GAP_UNIT          = 10; // default GAP if detector is outside the allowed limit of detectors
	public static final int DEFAULT_JAM_UNIT          = 10; // default JAM if detector is outside the allowed limit of detectors
	
	private static final int LAMP_RED_INDEX   = 1;
    private static final int LAMP_AMBER_INDEX = 2;
    private static final int LAMP_GREEN_INDEX = 3;
    
    protected static int stagesAndInterstagesCounter = 0;
	
	public Node[] nodes;
    protected AppType appType;
	
    public ArrayList<Move> moves = new ArrayList<Move>();
    protected AnlagenFehler anlagenFehler = new AnlagenFehler();
	public iCentral central;
	public Parameters parameters;
	public JLRTModBus sit;
	public PoliceKeyboard policePanel;
	public UPS ups;
	public Signs signs;
	public AutoReset autoReset;
	public Log log;
	public boolean isUseMapActiveInput = false;
    public Input isMapActive;
    public Input isDetCardError;
    public Input isNetworkCabinetDoorClosed;
//    public Output reset;
    public Output cycleStartPulse;
    public int[] policeStopPoints;
    
    public int totalDetectorsCount = 0;
    
    public ITCProtocol itcProtocol;
	
	private boolean isPreemption = false;      // whether the controller supports preemption
	private boolean isUseSIT = false;          // whether the controller uses a SIT PLC
	private boolean isMaintenance = false;     // whether the controller supports maintenance ("map only" mode)
	private boolean isSynopticMap = false;     // whether the controller supports a synoptic map
	private boolean isClockSync = false;       // whether the controller supports green-wave by clock synchronization
	
	public Controller(Node node1) {
		nodes = new Node[] { node1 };
	}
	
	public Controller(Node node1, Node node2) {
		nodes = new Node[] { node1, node2 };
	}
	
	public Controller(Node node1, Node node2, Node node3) {
		nodes = new Node[] { node1, node2, node3 };
	}
	
	public Controller setAppType(AppType appType) {
	    return setAppType(appType, false);
	}

	public Controller setAppType(AppType appType, boolean isNoPT) {
		this.appType = appType;
		if (this.appType.equals(AppType.Haifa)) {
			central = new Dvi35Haifa();
			parameters = isNoPT ? new HaifaNoPTParameters() : new HaifaPTParameters();
			policePanel = new PoliceKeyboard();
			setSynopticMap();
            autoReset = new AutoReset(30, 10, 1);
		} else if (this.appType.equals(AppType.Jerusalem)) {
			central = new FDPPJerusalem();
            parameters = new JerusalemParameters();
		} else if (this.appType.equals(AppType.NetiveiAyalon)) {
		    central = new Dvi35Haifa();
		    parameters = new NetiveiAyalonParameters();
            policePanel = new PoliceKeyboard();
            setSynopticMap();
            autoReset = new AutoReset(30, 10, 1);
		} else if (this.appType.equals(AppType.NetiveiIsrael)) {
		    central = new FDPPNetiveiIsrael();
		    parameters = new NetiveiIsraelParameters();
            policePanel = new PoliceKeyboard();
            setSynopticMap();
            autoReset = new AutoReset(30, 10, 1);
		} else if (this.appType.equals(AppType.TelAviv)) {
		    central = new Dvi35TelAviv();
		    parameters = new TelAvivParameters();
            policePanel = new PoliceKeyboard();
            setSynopticMap();
            autoReset = new AutoReset(30, 10, 1);
		} else { // default is Haifa protocol
		    central = new Dvi35Haifa();
            parameters = isNoPT ? new HaifaNoPTParameters() : new HaifaPTParameters();
            policePanel = new PoliceKeyboard();
            setSynopticMap();
		}
		
		ups = new UPS();
		signs = new Signs();
		log = new Log();
		
		for (int i = 0; i < nodes.length; i++) {
		    nodes[i].scheduler = new Scheduler(nodes[i]);
		}
		
		return this;
	}
	
	public Controller setPreemption(boolean isUseSIT) {
		this.isPreemption = true;
		this.isUseSIT = isUseSIT;
		if (this.isUseSIT) {
		    sit = new JLRTModBus();
		}
		return this;
	}
	
	public Controller setMaintenance() {
		isMaintenance = true;
		return this;
	}
	
	public Controller setSynopticMap() {
		isSynopticMap = true;
		return this;
	}
	
	public Controller setClockSync() {
		isClockSync = true;
		return this;
	}
    
    public void initializeDet() {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].greenwaves.size(); j++) {
                nodes[i].greenwaves.get(j).initialisiereDet();
            }
        }
        
        if (!Var.isProduction && isPreemption()) {
            cycleStartPulse            = new Output(nodes[0], "StartPulse"  ,           252, false);
        }
        
        if (isAppNetiveiIsrael()) {
            isDetCardError             = new Input (nodes[0], "Det_Error"   , 0, 10, 0, 220);
            isNetworkCabinetDoorClosed = new Input (nodes[0], "Network_Door", 0, 10, 0, 254);
        }
        
        if (isSynopticMap() && isMapActive == null) {
            isMapActive                = new Input (nodes[0], "Map_Active"  , 0, 10, 0, 255);
        }
        
//        if (!Var.controller.isAppHaifa()) {
//            reset                      = new Output(nodes[0], "Reset"       ,           255, false);
//        }
    }
    
    public void initializeParameters() {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].greenwaves.size(); j++) {
                nodes[i].greenwaves.get(j).initialisiereParameter();
            }
        }
        if (autoReset != null) {
            autoReset.initializeParameters();
        }
    }
    
    public boolean isDoorOpen() {
        return (AnlagenZustand.getTuerZustand() == AnlagenZustand.TUER_GEOEFFNET);
    }
    
    public boolean isNetworkCabinetDoorClosed() {
        return isNetworkCabinetDoorClosed.belegt();
    }
	
    /*
     * public Controller SetNonFixedPoliceStructure(int structure) { policeStruct = structure;
     * return this; }
     */
	
	public AppType getAppType() {
		return appType;
	}
	
	/**
	 * Gets a node at a requested index (starting from 1).
	 * If the nodes are not initialized, returns null.
	 * If the index invalid, returns the first node.
	 * @param node - the index of a node (starting from 1)
	 * @return Returns the node at the requested index (or null if not initialized or first node if invalid node requested)
	 */
	public Node getNode(int node) {
		if (nodes == null || nodes.length == 0) {
			return null;
		}
		
		if (node >= 1 && node <= nodes.length) {
			return nodes[node - 1];
		}
		return nodes[0];
	}
	
	public boolean isPreemption() {
		return isPreemption;
	}
	
	public boolean isUseSIT() {
	    return isPreemption() && this.isUseSIT;
	}
	
	public boolean isMaintenance() {
		return isMaintenance;
	}
	
	public boolean isSynopticMap() {
		return isSynopticMap;
	}
	
	public boolean isPoliceDoor() {
		return Var.controller.policePanel != null;
	}
	
	public boolean isErrorInSK24() {
	    return false;
	}
	
	public boolean isErrorInIO24() {
	    return false;
	}
	
	public boolean isErrorInIO64() {
	    return false;
	}
	
	public boolean isErrorInDetectorsCard() {
	    return (isDetCardError != null) && isDetCardError.belegt();
	}
	
	public boolean isClockSync() {
		return isClockSync;
	}
	
	public boolean isAppHaifa() {
		return appType == AppType.Haifa;
	}
    
    public boolean isAppNetiveiAyalon() {
        return appType == AppType.NetiveiAyalon;
    }
    
    public boolean isAppNetiveiIsrael() {
        return appType == AppType.NetiveiIsrael;
    }
	
	public boolean isAppJerusalem() {
		return appType == AppType.Jerusalem;
	}
	
	public boolean isAppTelAviv() {
		return appType == AppType.TelAviv;
	}
	
	public long getSecondsFromMidnight() {
	    return (Zeit.getStunde() * 60 + Zeit.getMinute()) * 60 + Zeit.getSekunde();
	}
	
	public long getSecondsFrom1970() {
	    return System.currentTimeMillis() / 1000;
	}
	
	public long getSecondsFrom1980() {
	    return (System.currentTimeMillis() - 315532800) / 1000;
	}
	
	public int getMaxProgramsNumber() {
		if (appType.equals(AppType.Haifa) || appType.equals(AppType.NetiveiAyalon)) {
			return 19;
		} else if (appType.equals(AppType.Jerusalem) || appType.equals(AppType.TelAviv) || appType.equals(AppType.NetiveiIsrael)) {
			return 32;
		} else {
			return 19;
		}
	}
    
    private Move move = null; 
	public void updateMovesErrorStates() {
        boolean hasNextError = AnlagenZustand.getFirstHfUga(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        while (hasNextError) {
            if (anlagenFehler.getSgNummer() > 0 && anlagenFehler.getSgNummer() <= moves.size()) {
                move = moves.get(anlagenFehler.getSgNummer() - 1);
                switch (anlagenFehler.getLmpTypFarbe()) {
                    case LAMP_RED_INDEX  : move.isUGARed   = true; break;
                    case LAMP_AMBER_INDEX: move.isUGAAmber = true; break;
                    case LAMP_GREEN_INDEX: move.isUGAGreen = true; break;
                }
            }
            hasNextError = AnlagenZustand.getNextUgaUge(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        }
        hasNextError = AnlagenZustand.getFirstNfUga(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        while (hasNextError) {
            if (anlagenFehler.getSgNummer() > 0 && anlagenFehler.getSgNummer() <= moves.size()) {
                move = moves.get(anlagenFehler.getSgNummer() - 1);
                switch (anlagenFehler.getLmpTypFarbe()) {
                    case LAMP_RED_INDEX  : move.isUGARed   = true; break;
                    case LAMP_AMBER_INDEX: move.isUGAAmber = true; break;
                    case LAMP_GREEN_INDEX: move.isUGAGreen = true; break;
                }
            }
            hasNextError = AnlagenZustand.getNextUgaUge(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        }
        hasNextError = AnlagenZustand.getFirstHfUge(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        while (hasNextError) {
            if (anlagenFehler.getSgNummer() > 0 && anlagenFehler.getSgNummer() <= moves.size()) {
                move = moves.get(anlagenFehler.getSgNummer() - 1);
                switch (anlagenFehler.getLmpTypFarbe()) {
                    case LAMP_RED_INDEX  : move.isUGERed   = true; break;
                    case LAMP_AMBER_INDEX: move.isUGEAmber = true; break;
                    case LAMP_GREEN_INDEX: move.isUGEGreen = true; break;
                }
            }
            hasNextError = AnlagenZustand.getNextUgaUge(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        }
        hasNextError = AnlagenZustand.getFirstNfUge(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        while (hasNextError) {
            if (anlagenFehler.getSgNummer() > 0 && anlagenFehler.getSgNummer() <= moves.size()) {
                move = moves.get(anlagenFehler.getSgNummer() - 1);
                switch (anlagenFehler.getLmpTypFarbe()) {
                    case LAMP_RED_INDEX  : move.isUGERed   = true; break;
                    case LAMP_AMBER_INDEX: move.isUGEAmber = true; break;
                    case LAMP_GREEN_INDEX: move.isUGEGreen = true; break;
                }
            }
            hasNextError = AnlagenZustand.getNextUgaUge(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        }
        hasNextError = AnlagenZustand.getFirstSgKonflikt(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        while (hasNextError) {
            if (anlagenFehler.getSgNummer() > 0 && anlagenFehler.getSgNummer() <= moves.size()) {
                move = moves.get(anlagenFehler.getSgNummer() - 1);
                switch (anlagenFehler.getLmpTypFarbe()) {
                    case LAMP_RED_INDEX  : move.isUGERed   = true; break;
                    case LAMP_AMBER_INDEX: move.isUGEAmber = true; break;
                    case LAMP_GREEN_INDEX: move.isUGEGreen = true; break;
                }
            }
            hasNextError = AnlagenZustand.getNextUgaUge(AnlagenZustand.MODE_NODELETE, anlagenFehler);
        }
	}
	
	public void resetMovesErrorStates() {
	    for (int i = 0; i < moves.size(); i++) {
	        move = moves.get(0);
            move.isUGARed   = false;
            move.isUGAAmber = false;
            move.isUGAGreen = false;
            move.isUGERed   = false;
            move.isUGEAmber = false;
            move.isUGEGreen = false;
	    }
	}
}
