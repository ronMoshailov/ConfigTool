package special;

import enums.AppType;
import sg.Sg;
import ta172.ParSets;
import ta172.Var;

public class Controller {
	
	public static final int DEFAULT_GAP_UNIT = 1000; // default GAP if detector is outside the allowed limit of detectors
	public static final int DEFAULT_JAM_UNIT = 1000; // default JAM if detector is outside the allowed limit of detectors
	public static final int MAXIMAL_CUMULATIVE_CYCLES = 10; // maximal cumulative cycles both for moves and for stages
	
	public AutoReset autoReset;
	public Log log;
	
	private AppType appType;
	public Node[] nodes;
	
	public Sg[] sgs;
	public Dvi35Base dvi35;
	public Dvi35ParametersBase dvi35Parameters;
	
	private int policeStruct = 0;			// the value in which non-fixed police program should work. if 0 - last active structure
	private boolean isPreemption = false;	// whether the controller supports preemption
	private boolean isMaintenance = false;	// whether the controller supports maintenance ("map only" mode)
	private boolean isSynopticMap = false;	// whether the controller supports a synoptic map
	private boolean isPoliceDoor = false;	// whether the controller supports a police door
	private boolean isClockSync = false;	// whether the controller supports green-wave by clock synchronization
	
	public Controller(Node node1) {
		nodes = new Node[] { node1 };
	}
	
	public Controller(Node node1, Node node2) {
		nodes = new Node[] { node1, node2 };
	}
	
	public Controller(Node node1, Node node2, Node node3) {
		nodes = new Node[] { node1, node2, node3 };
	}

	public Controller SetAppType(AppType appType) {
		this.appType = appType;
		this.log = new Log();
		if (this.appType.equals(AppType.Haifa)) {
			// TODO: initialize Haifa Dvi35
		} else if (this.appType.equals(AppType.Jerusalem)) {
			dvi35 = new Dvi35Jerusalem();
		} else if (this.appType.equals(AppType.TelAviv)) {
			dvi35 = new Dvi35TelAviv();
			autoReset = new AutoReset(30,10, 1);
		}
		return this;
	}
	
	public Controller SetPreemption() {
		isPreemption = true;
		return this;
	}
	
	public Controller SetMaintenance() {
		isMaintenance = true;
		return this;
	}
	
	public Controller SetSynopticMap() {
		isSynopticMap = true;
		return this;
	}
	
	public Controller SetPoliceDoor() {
		isPoliceDoor = true;
		return this;
	}
	
	public Controller SetClockSync() {
		isClockSync = true;
		return this;
	}
	
	public Controller SetNonFixedPoliceStructure(int structure) {
		policeStruct = structure;
		return this;
	}
	
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
	
	public int getPoliceStructure() {
		return policeStruct;
	}
	
	public boolean isPreemption() {
		return isPreemption;
	}
	
	public boolean isMaintenance() {
		return isMaintenance;
	}
	
	public boolean isSynopticMap() {
		return isSynopticMap;
	}
	
	public boolean isPoliceDoor() {
		return isPoliceDoor;
	}
	
	public boolean isClockSync() {
		return isClockSync;
	}
	
	public boolean isAppHaifa() {
		return appType == AppType.Haifa;
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
	
	public boolean isAppOther() {
		return appType == AppType.Other;
	}
	
	public int getMaxProgramsNumber() {
		if (appType.equals(AppType.Haifa) || appType.equals(AppType.NetiveiIsrael) || appType.equals(AppType.Other)) {
			return 19;
		} else if (appType.equals(AppType.Jerusalem) || appType.equals(AppType.TelAviv)) {
			return 32;
		} else {
			return 19;
		}
	}
	
	public int getMaximalDetectorId() {
		if (appType == AppType.Haifa) {
			return 25;
		} else {
			return 20;
		}
	}
	
	public int getUlFailParam() {
		return ParSets.ULFailParam.get()[0] * Var.ONE_SEC;
	}
/*	
	public void UpdateMovesArrays() {
		int[] mainTraffic = new int[nodes.length], secondaryTraffic = new int[nodes.length];
		int[] mainPedestrian = new int[nodes.length], secondaryPedestrian = new int[nodes.length];
		int[] regularBlinkers = new int[nodes.length];
		int[] conditionalMainBlinkers = new int[nodes.length], conditionalSecondaryBlinkers = new int[nodes.length];
		Move move = null;
		
		sgs = Sg.getSgArray();
		
		for (sgsIterator = 0; sgsIterator < sgs.length; sgsIterator++) {
			move = (Move)sgs[sgsIterator];
			if (move.isMainTraffic()) {
				mainTraffic[move.node.getNummer() - 1]++;
			} else if (move.isSecondaryTraffic()) {
				secondaryTraffic[move.node.getNummer() - 1]++;
			} else if (move.isMainPedestrian()) {
				mainPedestrian[move.node.getNummer() - 1]++;
			} else if (move.isSecondaryPedestrian()) {
				secondaryPedestrian[move.node.getNummer() - 1]++;
			} else if (move.isRegularBlinker()) {
				regularBlinkers[move.node.getNummer() - 1]++;
			} else if (move.isMainConditionalBlinker()) {
				conditionalMainBlinkers[move.node.getNummer() - 1]++;
			} else if (move.isSecondaryConditionalBlinker()) {
				conditionalSecondaryBlinkers[move.node.getNummer() - 1]++;
			}
		}
	}
*/
}
