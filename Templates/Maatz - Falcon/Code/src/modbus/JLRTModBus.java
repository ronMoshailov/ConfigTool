package modbus;

import m0547.Var;
import sg.Zustand;
import core.Move;
import core.Move.MoveType;
import core.detectors.Input;
import core.detectors.InputInternal;
import vtvar.VtVarStrukt;

public class JLRTModBus {
    
    public static boolean outKA;
    public static int outKAindex;

	private static int MAX_DIRECTIONS;
	private static final int MODBUS_SLAVE_ID = 3;
//	private static final int BYTES_PER_RECEIVED_DIRECTION =   2;
//	private static final int BYTES_PER_SENT_DIRECTION     =   2;
//	private static final int MAX_LRT_SG_COUNT = 16;
	private static final int LRT_SG_BITS = 16;
	private static final int KEEP_ALIVE_BITS = 8;
//	private static final int BITS_IN_BYTE = 8;
	private static final int COILS_START = 10001;
	private static final int INPUTS_START = 20001;
	
	private boolean[] receivedData, sentData;
	private boolean lastKA = false;
	private int countKA = 0;
	private boolean isConnected = false;

	private static ModbusSlave modbusSlave = null;

//	private int keepAliveCounter = 0;

    public Input sitDoorOpen;

	public static InputInternal sitOK;
	public static boolean readSitOK = false;
    public static VtVarStrukt SITTimeOut;
	
	public static void initializeParameter() {
        SITTimeOut  = VtVarStrukt.init(Var.tk1, "SIT_TIMEOUT"  , new int[] { 10 }, true, true, true);
	}
	
	public static void InitializeSITOK() {
	    sitOK = new InputInternal("SITOK", Var.tk1);
	}

	public JLRTModBus() {
		MAX_DIRECTIONS = Move.LRTCount;

		if (modbusSlave == null) {
			// init Modbus RTU
			modbusSlave = ModbusSlave.createRtu(3, 19200, ModbusSlave.PARITY_EVEN, 8, 1, MODBUS_SLAVE_ID);
//			modbusSlave.initMapping((MAX_DIRECTIONS * LRT_SG_BITS) + KEEP_ALIVE_BITS, (MAX_DIRECTIONS * LRT_SG_BITS) + KEEP_ALIVE_BITS, 0, 0);
			modbusSlave.initMapping(
					COILS_START, (MAX_DIRECTIONS * LRT_SG_BITS) + KEEP_ALIVE_BITS,
					INPUTS_START, (MAX_DIRECTIONS * LRT_SG_BITS) + KEEP_ALIVE_BITS,
					0, 0, 0, 0
			);
		}

		MAX_DIRECTIONS = Move.LRTCount;
		receivedData   = new boolean[1 + MAX_DIRECTIONS * LRT_SG_BITS];
		sentData       = new boolean[2 + MAX_DIRECTIONS * LRT_SG_BITS];
		
		System.out.println("SIT-modbus protocol initialized");
	}

	public void receiveMessage() {
		/*
		 * 2 Bytes Per direction:
		 * ----------------------------------------------------
		 * Bit  0 - SIT OK
		 * Bit  1 - DL
		 * Bit  2 - DP
		 * Bit  3 - DM
		 * Bit  4 - DS
		 * Bit  5 - DQ
		 * Bit  6 - DF
		 * Bit  7 - OOODP
		 * ----------------------------------------------------
		 * Bit  8 - OOODM
		 * Bit  9 - OOODQ
		 * Bit 10 - Permissive
		 * Bit 11 - 
		 * Bit 12 - 
		 * Bit 13 - 
		 * Bit 14 - 
		 * Bit 15 - Keep Alive (for last move only)
		 * ----------------------------------------------------
		 * SIT-OK is sent only for the first move. For any other move, everything is shifted by one bit.
		 */

		receivedData = modbusSlave.getBits(COILS_START, MAX_DIRECTIONS * LRT_SG_BITS + 1);
		if (getKeepAlive() == lastKA) {
			if (countKA < 999000) {
				countKA++;
			}
		} else {
			countKA = 0;
		}
		lastKA = getKeepAlive();
		isConnected = countKA < SITTimeOut.get(0);
		
		if (receivedData == null || receivedData.length < 1 + MAX_DIRECTIONS * LRT_SG_BITS) {
			receivedData = new boolean[1 + MAX_DIRECTIONS * LRT_SG_BITS];
		}
	}
	
	public boolean isConnected() {
	    return isConnected;
	}
	/*
	public boolean getDetector(int moveIndex, PreemptionDetectorType type) {
	    if (type == PreemptionDetectorType.TYPE_DL) {
	        return getDL(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_DP) {
	        return getDP(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_DM) {
	        return getDM(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_DS) {
	        return getDS(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_DQ) {
	        return getDQ(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_OOODP) {
	        return getOOODP(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_OOODM) {
	        return getOOODM(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_OOODQ) {
	        return getOOODQ(moveIndex);
	    } else if (type == PreemptionDetectorType.TYPE_DF) {
	        return getDF(moveIndex);
	    }
	    return false;
	}
	 */
	public boolean getSitOk() {
	    if (receivedData == null || !isConnected)
            return false;

        return receivedData[0];
	}
	
	public boolean getDL(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 0];
	}

	public boolean getDP(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 1];
	}

	public boolean getDM(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 2];
	}

	public boolean getDS(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 3];
	}

	public boolean getDQ(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 4];
	}

	public boolean getDF(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 5];
	}

	public boolean getOOODP(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 6];
	}

	public boolean getOOODM(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 7];
	}

	public boolean getOOODQ(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 8];
	}

	public boolean getPermissive(int moveIndex) {
		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS || receivedData == null || !isConnected)
			return false;

		return receivedData[1 + moveIndex * LRT_SG_BITS + 9];
	}
	
	public boolean getKeepAlive() {
		if (receivedData == null)
			return false;
		
		return receivedData[1 + (MAX_DIRECTIONS - 1) * LRT_SG_BITS + 15]; 
	}
	
	public void getLRTState(Move move) {
	    if (move == null || !move.moveType.equals(MoveType.LRT)) {
	        return;
	    }
	    
        setLRTData(
                move.getLRTIndex(),
                move.node.OOOTLC.getAusgang(),
                move.node.Catastrophe.getAusgang(),
                move.l1,
                move.l2,
                move.l3,
                move.preemptionTriangle != null && move.preemptionTriangle.getZustand() == Zustand.GELBBLINKEN,
                move.preemptionTriangle != null && move.preemptionTriangle.getZustand() == Zustand.EIN,
                false,
                false
        );
	}

	private int tmp = 0;
	private void setLRTData(
			int moveIndex,
			boolean oootlc,
			boolean catastrophe,
			boolean l1,
			boolean l2,
			boolean l3,
			boolean triangleBlinking,
			boolean triangleOn,
			boolean interstageStatus,
			boolean interstageTimeout)
	{
		/* 2 Bytes per direction:
		 * ----------------------------------------------------
		 * Bit  0 - OOOTLC 
		 * Bit  1 - Catastrophe Plan
		 * Bit  2 - Traffic Lights Status - L1
		 * Bit  3 - Traffic Lights Status - L2
		 * Bit  4 - Traffic Lights Status - L3
		 * Bit  5 - Triangle Status - Triangle Blinking / Fixed
		 * Bit  6 - Triangle Status - Triangle On / Off
		 * Bit  7 - Interstage Status
		 * ----------------------------------------------------
		 * Bit  8 - Interstage Timeout
		 * Bit  9 - 
		 * Bit 10 - 
		 * Bit 11 - 
		 * Bit 12 - 
		 * Bit 13 - 
		 * Bit 14 - 
		 * Bit 15 - Keep Alive (for last move only)
		 * ----------------------------------------------------
		 * OOOTLC, Catastrophe are sent only for the first move. For any other move, everything is shifted by two bits.
		 */
//		byte low = 0, high = 0, special = 0;

		if (moveIndex < 0 || moveIndex >= MAX_DIRECTIONS)
			return;

		if (moveIndex == 0) {
			sentData[0] = oootlc;
			sentData[1] = catastrophe;
		}
		sentData[2 + moveIndex * LRT_SG_BITS + 0] = l1;
		sentData[2 + moveIndex * LRT_SG_BITS + 1] = l2;
		sentData[2 + moveIndex * LRT_SG_BITS + 2] = l3;
		sentData[2 + moveIndex * LRT_SG_BITS + 3] = triangleBlinking;
		sentData[2 + moveIndex * LRT_SG_BITS + 4] = triangleOn;
		sentData[2 + moveIndex * LRT_SG_BITS + 5] = interstageStatus;
		sentData[2 + moveIndex * LRT_SG_BITS + 6] = interstageTimeout;
		
		if (moveIndex == MAX_DIRECTIONS - 1) {
			sentData[2 + moveIndex * LRT_SG_BITS + 15] = outKA = (tmp <= 1);
			outKAindex = 2 + moveIndex * LRT_SG_BITS + 15;
			// 2 + 0 * 16 + 15 = 2 +  0 + 15 = 17
			// 2 * 1 * 16 + 15 = 2 + 16 + 15 = 33
	        tmp++;
	        tmp = tmp % 4;
		}
	}

	public void writeMessage() {
		if (sentData != null)
			modbusSlave.setInputBits(INPUTS_START, sentData);
	}
}
