package special;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A H                                                              *
 *  City/Authority : Tel Aviv														            *
 *  Class Revision : 1                                                                      	*
 *  Edited By      : Ilia Butvinnik            													*
 *                                                                                              *
 ************************************************************************************************/
import sbh.vtbib.dvi35.DVI35Modul;
import special.Node.SpecialProgram;
import tk.GreenWave;
import tk.Var;
import vt.Phase;
import vt.StgEbene;
import vt.TeilKnoten;
import vt.Vt;

class Dvi35Id1TA {
	public int program;                         // Byte 11
	
	public int impulse  ;                       // Byte 12
	public boolean syTk1;                       // Bit 0
	public boolean foTk1;                       // Bit 1
	public boolean syTk2;                       // Bit 2
	public boolean foTk2;                       // Bit 3
	public boolean fs   ;                       // Bit 4

	public int status;                          // Byte 13
	public boolean tlcOn;                       // Bit 0
	public boolean holOn;                       // Bit 1
	public boolean flashOn;                     // Bit 2
	public boolean signsOn;                     // Bit 3
	public boolean policeProgram;               // Bit 4
	public boolean statusBit5;                  // Bit 5
	public boolean statusBit6;                  // Bit 6
	public boolean resetController;             // Bit 7

	public int request;                         // Byte 14
	public boolean uploadInternalLog;           // Bit 0
	public boolean uploadTLCParameters;         // Bit 1
	public boolean requestBit2;                 // Bit 2
	public boolean requestBit3;                 // Bit 3
	public boolean uploadGapTimes;              // Bit 4
	public boolean uploadJournalOnline;         // Bit 5
	public boolean requestBit6;                 // Bit 6
	public boolean uploadSystemDetectorsCounts; // Bit 7

	public int status2;         // Byte 15
	public boolean status2Bit0; // Bit 0
	public boolean status2Bit1; // Bit 1
	public boolean status2Bit2; // Bit 2
	public boolean status2Bit3; // Bit 3
	public boolean status2Bit4; // Bit 4
	public boolean status2Bit5; // Bit 5
	public boolean status2Bit6; // Bit 6
	public boolean status2Bit7; // Bit 7

	public int status3;         // Byte 16
	public boolean status3Bit0; // Bit 0
	public boolean status3Bit1; // Bit 1
	public boolean status3Bit2; // Bit 2
	public boolean status3Bit3; // Bit 3
	public boolean status3Bit4; // Bit 4
	public boolean status3Bit5; // Bit 5
	public boolean status3Bit6; // Bit 6
	public boolean status3Bit7; // Bit 7

	public void setProgram(int program) {
		if (program == 0) {
			program = Var.controller.nodes[0].all_dark.getNummer();
		}
		this.program = program;
	}

	public void setImpulse(int impulse) {
		this.impulse = impulse;
		syTk1 = ((this.impulse >> 0) & 1) != 0 ? true : false;
		foTk1 = ((this.impulse >> 1) & 1) != 0 ? true : false;
		syTk2 = ((this.impulse >> 2) & 1) != 0 ? true : false;
		foTk2 = ((this.impulse >> 3) & 1) != 0 ? true : false;
		fs    = ((this.impulse >> 4) & 1) != 0 ? true : false;
	}

	public void setStatus(int status) {
		this.status = status;
		tlcOn           = ((this.status >> 0) & 1) != 0 ? true : false;
		holOn           = ((this.status >> 1) & 1) != 0 ? true : false;
		flashOn         = ((this.status >> 2) & 1) != 0 ? true : false;
		signsOn         = ((this.status >> 3) & 1) != 0 ? true : false;
		policeProgram   = ((this.status >> 4) & 1) != 0 ? true : false;
		statusBit5      = ((this.status >> 5) & 1) != 0 ? true : false;
		statusBit6      = ((this.status >> 6) & 1) != 0 ? true : false;
		resetController = ((this.status >> 7) & 1) != 0 ? true : false;
	}

	public void setRequest(int request) {
		this.request = request;
		uploadInternalLog           = ((this.request >> 0) & 1) != 0 ? true : false;
		uploadTLCParameters         = ((this.request >> 1) & 1) != 0 ? true : false;
		requestBit2                 = ((this.request >> 2) & 1) != 0 ? true : false;
		requestBit3                 = ((this.request >> 3) & 1) != 0 ? true : false;
		uploadGapTimes              = ((this.request >> 4) & 1) != 0 ? true : false;
		uploadJournalOnline         = ((this.request >> 5) & 1) != 0 ? true : false;
		requestBit6                 = ((this.request >> 6) & 1) != 0 ? true : false;
		uploadSystemDetectorsCounts = ((this.request >> 7) & 1) != 0 ? true : false;
	}

	public void setStatus2(int status) {
		this.status2 = status;
		status2Bit0 = ((this.status2 >> 0) & 1) != 0 ? true : false;
		status2Bit1 = ((this.status2 >> 1) & 1) != 0 ? true : false;
		status2Bit2 = ((this.status2 >> 2) & 1) != 0 ? true : false;
		status2Bit3 = ((this.status2 >> 3) & 1) != 0 ? true : false;
		status2Bit4 = ((this.status2 >> 4) & 1) != 0 ? true : false;
		status2Bit5 = ((this.status2 >> 5) & 1) != 0 ? true : false;
		status2Bit6 = ((this.status2 >> 6) & 1) != 0 ? true : false;
		status2Bit7 = ((this.status2 >> 7) & 1) != 0 ? true : false;
	}

	public void setStatus3(int status) {
		this.status3 = status;
		status3Bit0 = ((this.status3 >> 0) & 1) != 0 ? true : false;
		status3Bit1 = ((this.status3 >> 1) & 1) != 0 ? true : false;
		status3Bit2 = ((this.status3 >> 2) & 1) != 0 ? true : false;
		status3Bit3 = ((this.status3 >> 3) & 1) != 0 ? true : false;
		status3Bit4 = ((this.status3 >> 4) & 1) != 0 ? true : false;
		status3Bit5 = ((this.status3 >> 5) & 1) != 0 ? true : false;
		status3Bit6 = ((this.status3 >> 6) & 1) != 0 ? true : false;
		status3Bit7 = ((this.status3 >> 7) & 1) != 0 ? true : false;
	}
}

class Dvi35Id10TA {
	public int program; // Byte 11

	public int status;              // Byte 12
	public boolean generalFailure;  // Bit 0
	public boolean lampFailure;     // Bit 1
	public boolean manualOperation; // Bit 2 (police mode)
	public boolean waitingForSY;    // Bit 3
	public boolean tlcOn;           // Bit 4 (or flashing)
	public boolean centralOn;       // Bit 5
	public boolean maintenanceOn;   // Bit 6
	public boolean programs17_32;   // Bit 7

	public int cycleSecond; // Byte 13

	public int controlSignals; // Byte 59
	public boolean sy1;        // Bit 0
	public boolean sy2;        // Bit 1
	public boolean sy3;        // Bit 2
	public boolean sy4;        // Bit 3
	public boolean gridlock1;  // Bit 4
	public boolean gridlock2;  // Bit 5
	public boolean gridlock3;  // Bit 6
	public boolean gridlock4;  // Bit 7

	public void setProgram(int program) {
		this.program = program;
	}

	public void setStatus(boolean generalFailure, boolean lampFailure,
			boolean manualOperation, boolean waitingForSY,
			boolean tlcOn, boolean centralOn,
			boolean maintenanceOn, boolean waitingForFO) {
		status = 0;
		status |= (generalFailure  ? 0x01 : 0x00);
		status |= (lampFailure     ? 0x02 : 0x00);
		status |= (manualOperation ? 0x04 : 0x00);
		status |= (waitingForSY    ? 0x08 : 0x00);
		status |= (tlcOn           ? 0x10 : 0x00);
		status |= (centralOn       ? 0x20 : 0x00);
		status |= (maintenanceOn   ? 0x40 : 0x00);
		status |= (waitingForFO    ? 0x80 : 0x00);
	}

	public void setControlSignals(boolean sy1, boolean sy2, boolean sy3, boolean sy4,
			boolean gridlock1, boolean gridlock2,
			boolean gridlock3, boolean gridlock4) {
		controlSignals = 0;
		controlSignals |= (sy1       ? 0x01 : 0x00);
		controlSignals |= (sy2       ? 0x02 : 0x00);
		controlSignals |= (sy3       ? 0x04 : 0x00);
		controlSignals |= (sy4       ? 0x08 : 0x00);
		controlSignals |= (gridlock1 ? 0x10 : 0x00);
		controlSignals |= (gridlock2 ? 0x20 : 0x00);
		controlSignals |= (gridlock3 ? 0x40 : 0x00);
		controlSignals |= (gridlock4 ? 0x80 : 0x00);
	}

	public void setCycleSecond(int cycleSecond) {
		this.cycleSecond = cycleSecond;
	}
}

public class Dvi35TelAviv implements Dvi35Base {
	public static final int DVI35_PROG_IND   = 0;
	public static final int DVI35_IMPULSE_IND = 1;
	public static final int DVI35_STATUS_IND = 2;
	public static final int DVI35_REQUEST_IND = 3;
	public static final int DVI35_STATUS2_IND = 4;
	public static final int DVI35_STATUS3_IND = 5;

	public final Node node_1 = Var.tk1;
	public final Node node_2 = (TeilKnoten.getAnzahl() > 1 ? (Node)TeilKnoten.getTeilKnoten(2) : null);

	private int nodesCount = 1;
	private int nodeIterator;
	private Node currentNode;

	protected Dvi35Id1TA id1 = new Dvi35Id1TA(); 
	protected Dvi35Id10TA id10 = new Dvi35Id10TA();

	// indication (as received from the control center)
	public boolean isOperateInCentralDark = false;
	public boolean isOperateInCentral = false;
	public boolean isOperateInCentralProgram = false;
	public boolean isOperateInCentralFlash = false;
	public boolean isTurnOnSigns = false;
	public boolean isCallAll = false;
	public boolean isResetController = false;
	public boolean isSYfromCentralTk1 = false;
	public boolean isFOfromCentralTk1 = false;
	public boolean isSYfromCentralTk2 = false;
	public boolean isFOfromCentralTk2 = false;
	public boolean isGoToPoliceProgram = false;
	public boolean isPoliceAdvance = false;

	//    With the flag switchnodeseperate we are able to switch on/off each node separately.
	//    If this flag is set to false, the controller will switch on/off the nodes with the bit 0
	//    If this flag is set to true, the controller will switch the nodes on when the bit 2 /bit 3 are set, and off if the bits are not set, or bit 0 is not set. 
	//    Both nodes work with the same program number
	//    If there is no connection to the cc, the controller witch switch to clock
	public static final boolean switchnodeseperate = true;

	private Phase activePhase;

	public Dvi35TelAviv() {
		nodesCount = TeilKnoten.getAnzahl();
	}

	public void getId1() {
		if (!DVI35Modul.isZentAktiv()) {
			return;
		}

		GreenWave.dvi35GW = false;

		// read ID 1 Bytes & Bits
		id1.setProgram(DVI35Modul.getId1(DVI35_PROG_IND   )); // ID1 Byte 11
		id1.setImpulse(DVI35Modul.getId1(DVI35_IMPULSE_IND)); // ID1 Byte 12
		id1.setStatus (DVI35Modul.getId1(DVI35_STATUS_IND )); // ID1 Byte 13
		id1.setRequest(DVI35Modul.getId1(DVI35_REQUEST_IND)); // ID1 Byte 14
		id1.setStatus2(DVI35Modul.getId1(DVI35_STATUS2_IND)); // ID1 Byte 15
		id1.setStatus3(DVI35Modul.getId1(DVI35_STATUS3_IND)); // ID1 Byte 16

		// decipher ID 1
		isOperateInCentralDark = !id1.tlcOn && id1.holOn;
		isOperateInCentral = id1.tlcOn && id1.holOn;
		isOperateInCentralProgram = isOperateInCentral && !id1.flashOn;
		isOperateInCentralFlash = isOperateInCentral && id1.flashOn; // TODO: make sure that this is the correct condition
		isTurnOnSigns = isOperateInCentral && id1.signsOn;
		isResetController = id1.resetController;
		isGoToPoliceProgram = isOperateInCentral && id1.policeProgram;
		
		if (isResetController) {
			SpecialInOuts.reset.setAusgang();
		}
		
		if (SpecialInOuts.reset.isActive && SpecialInOuts.reset.stateTime >= Var.ONE_SEC) {
			SpecialInOuts.reset.resetAusgang();
		}

		isSYfromCentralTk1 = id1.syTk1;
		isFOfromCentralTk1 = id1.foTk1;
		isSYfromCentralTk2 = id1.syTk2;
		isFOfromCentralTk2 = id1.foTk2;
		isPoliceAdvance    = id1.fs   ;

		for (nodeIterator = 1; nodeIterator <= nodesCount; nodeIterator++) {
			currentNode = (Node)TeilKnoten.getTeilKnoten(nodeIterator);
			if (isOperateInCentralDark) {
				currentNode.setProgWunsch(currentNode.all_dark, StgEbene.STG_ZENTRALE);
			} else if (isGoToPoliceProgram) {
				currentNode.setProgWunsch(Vt.findProgByNum(SpecialProgram.Police.getProgramNumber()), StgEbene.STG_ZENTRALE);
			} else if (isOperateInCentralProgram) { // go to central (requested program)
				// if program request (central mode) doesn't exist or doesn't match the required program
				if (currentNode.isInMaintenance() || currentNode.isMaintenanceProgramRequest()) {
					currentNode.setProgWunsch(Vt.findProgByNum(id1.program + SpecialProgram.MaintenanceFirst.getProgramNumber() - 1, currentNode.getNummer()), StgEbene.STG_MANUELL);
				} else {
					currentNode.setProgWunsch(Vt.findProgByNum(id1.program, currentNode.getNummer()), StgEbene.STG_ZENTRALE);
				}
			} else { // return to clock
				// if flash (via central) is NOT required, switch to local schedule
				if (!isOperateInCentralFlash) {
					currentNode.setProgWunsch(null, StgEbene.STG_ZENTRALE);
				} else { // if flash (via central) is required, switch to it
					currentNode.setProgWunsch(currentNode.blinkprog, StgEbene.STG_ZENTRALE);
				}
			}
		}

		if (isTurnOnSigns) {
			Signs.signsOnFromCenter = true;
		} else {
			Signs.signsOnFromCenter = false;
		}
	}

	public void setStatus() {
		int statusByteMask = 0;
		int specialStatusByteMask = 0;

		// Manipulating byte 12 bit 0 (Controller is on (not dark)) of the remote frame to control center
//		if (node_1.getAktProg().getNummer() == node_1.all_dark.getNummer()) {
//			statusByteMask |= 0x01;
//		}
		
		// Manipulating byte 12 bit 2 (Hand mode / Manual mode) of the remote frame to control center
		if ((node_1.getAktStgEbene() == StgEbene.STG_NUM_MANUELL) &&
			(node_1.getAktProg().getNummer() ==  SpecialProgram.Police.getProgramNumber() ||
			 node_1.getAktProg().getNummer() ==  SpecialProgram.Dark  .getProgramNumber() ||
			 node_1.getAktProg().getNummer() ==  SpecialProgram.Flash .getProgramNumber())) {
			statusByteMask |= 0x04;
		}
		
		// Manipulating byte 12 bit 3 (Coordination position) of the remote frame to control center
		if (node_1.isWaitingForSy && node_1.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE) {
			statusByteMask |= 0x08;
		}

		// Manipulating byte 12 bit 4 (Controller ON/OFF) of the remote frame to control center
		if (!node_1.isOffline()) {
			statusByteMask |= 0x10;
		}

		// Manipulating byte 12 bit 5 (Controller in Central) of the remote frame to control center
		if (node_1.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE || (node_1.isInMaintenance() && isOperateInCentral)) {
			statusByteMask |= 0x20;
		}

		// Manipulating byte 12 bit 6 (Maintenance / Map Only Mode) of the remote frame to control center
		if (node_1.isInMaintenance()) {
			statusByteMask |= 0x40;
		}

		// Manipulating byte 12 bit 7 (2nd synchronization) of the remote frame to control center
		if ((node_1.getAktProg().getNummer() >= 17 && node_1.getAktProg().getNummer() <= 32) || 
			(node_1.isInMaintenance() && (node_1.getAktProg().getNummer() - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1 >= 17 && node_1.getAktProg().getNummer() - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1 <= 32))) {
			statusByteMask |= 0x80;
		}

		if (node_1.getAktProg().getNummer() == node_1.all_dark.getNummer()) {
			DVI35Modul.setResIDx0(1, 0xFF, 0);
		} else if (node_1.isInMaintenance()) {
			if (node_1.isInMaintenance() && (node_1.getAktProg().getNummer() - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1 >= 17 && node_1.getAktProg().getNummer() - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1 <= 32)) {
				DVI35Modul.setResIDx0(1, 0xFF, node_1.getAktProg().getNummer() - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1 - 16);
			} else {
				DVI35Modul.setResIDx0(1, 0xFF, node_1.getAktProg().getNummer() - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1);
			}
		} else {
			if (node_1.getAktProg().getNummer() >= 17 && node_1.getAktProg().getNummer() <= 32) {
				DVI35Modul.setResIDx0(1, 0xFF, node_1.getAktProg().getNummer() - 16);
			} else {
				DVI35Modul.setResIDx0(1, 0xFF, node_1.getAktProg().getNummer());
			}
		}
		DVI35Modul.setResIDx0(2, 0xFC, statusByteMask);

		if (SpecialInOuts.ups_power_off != null && SpecialInOuts.ups_power_off.belegt())
			specialStatusByteMask |= 0x01;
		if (SpecialInOuts.ups_failure != null && SpecialInOuts.ups_failure.belegt())
			specialStatusByteMask |= 0x02;
		if (SpecialInOuts.ups_door_open != null && SpecialInOuts.ups_door_open.belegt())
			specialStatusByteMask |= 0x04;
		if (node_1.isDoorOpen())
			specialStatusByteMask |= 0x08;
		if (!PoliceKeyboard.Door_N_O.eingangGesetzt())
			specialStatusByteMask |= 0x10;
		DVI35Modul.setResIDx0(48, 0xFF, specialStatusByteMask);
		
		if ((activePhase = Phase.getAktivePhase(node_1)) == null)
			DVI35Modul.setResIDx0(49, 0xFF, 0);
		else
			DVI35Modul.setResIDx0(49, 0xFF, activePhase.getNummer());
	}

	public void setReplyToCenter() {
	}

	public boolean operateSigns() {
		Signs.signsOnFromCenter = isTurnOnSigns;
		return !isOperateInCentral;
	}

	/******************************************************************************/
	/******************************************************************************/

	public boolean isSy(Node node) {
		switch (node.getNummer()) {
		case 1: return id1.syTk1;
		case 2: return id1.syTk2;
			default:
				return false;
		}
	}

	public boolean checkExitFromA(Node node) {
		node_1.isWaitingForSy = true;
		
		if (Var.tk1.getAktStgEbene() != StgEbene.STG_NUM_ZENTRALE ||
			!id1.tlcOn ||
			!id1.holOn ||
			(node.getNummer() == 1 && id1.syTk1) ||
			(node.getNummer() == 2 && id1.syTk2)) {
			node_1.isWaitingForSy = false;
			return true;
		}
		return false;
	}
}