package core.central;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A H                                                              *
 *  City/Authority : Jerusalem														            *
 *  Class Revision : 1                                                                      	*
 *  Edited By      : Ilia Butvinnik            													*
 *                                                                                              *
 ************************************************************************************************/

import m0547.Var;
import core.AutoReset;
import core.Node;
import core.Node.SpecialProgram;
import core.detectors.DDetector;
import core.detectors.DEDetector;
import core.detectors.EDetector;
import core.detectors.Input;
import core.detectors.Output;
import core.detectors.QDetector;
import core.detectors.TPDetector;
import det.Det;
import det.Detektor;
import hw.AnlagenZustand;
import sbh.vtbib.dvi35.DVI35Modul;
import sg.Sg;
import vt.Phase;
import vt.StgEbene;
import vt.TeilKnoten;

public class FDPPJerusalem extends iCentral {
    class FDPPId1 {
        private static final int BITS_IN_BYTE = 8;

        // Byte 11 - Program Number: 1-32
        public int program;
        // Byte 12 - Mode1 
        public int mode1;
        //                  bit0  bit1     bit2            bit3       bit4       bit5     bit6      bit7
        public boolean isControl, isOn, isFlash, isControlSigns, isSignsOn, isCallAll, isReset, isSynced;
        // Byte 13 - Mode2
        public int mode2; // not in use yet
        // Byte 14 - Mode3
        public int mode3; // not in use yet
        // Byte 15 - Fire
        public int fire;
        // Remote Detectors
        public int detectorsBytesCount;
        public boolean[] detectors;
        // Pulses
        public int pulsesBytesCount;
        public boolean[] pulses;
        // Remote Preemption Detectors
        public int preemptionDetectorsCount;
        public int bytesPerPreemptionDetector;
        public int[] preemptionDetectors;
        // Force Detectors
        public int forceDetectorsBytesCount;
        public int[] forceDetectors;
        public boolean[] backupForceDetectors;

        public void setProgram(int program) {
            if (program == 0) {
                program = Var.controller.nodes[0].all_dark.getNummer();
            }
            this.program = program;
        }

        public void setMode1(int mode1) {
            this.mode1 = mode1;
            isControl      = ((this.mode1 >> 0) & 1) != 0 ? true : false; // bit 0
            isOn           = ((this.mode1 >> 1) & 1) != 0 ? true : false; // bit 1
            isFlash        = ((this.mode1 >> 2) & 1) != 0 ? true : false; // bit 2
            isControlSigns = ((this.mode1 >> 3) & 1) != 0 ? true : false; // bit 3
            isSignsOn      = ((this.mode1 >> 4) & 1) != 0 ? true : false; // bit 4
            isCallAll      = ((this.mode1 >> 5) & 1) != 0 ? true : false; // bit 5
            isReset        = ((this.mode1 >> 6) & 1) != 0 ? true : false; // bit 6
            isSynced       = ((this.mode1 >> 7) & 1) != 0 ? true : false; // bit 7
        }

        public void setMode2(int mode2) {
            this.mode2 = mode2;
        }

        public void setMode3(int mode3) {
            this.mode3 = mode3;
        }

        public void setDetectors(int detectorsBytesCount, int index) {
            this.detectorsBytesCount = detectorsBytesCount;
            if (detectors == null || detectors.length != this.detectorsBytesCount * BITS_IN_BYTE) {
                detectors = new boolean[this.detectorsBytesCount * BITS_IN_BYTE];
            }
            for (int i = 0; i < this.detectorsBytesCount; i++) {
                for (int j = 0; j < BITS_IN_BYTE; j++) {
                    detectors[i * BITS_IN_BYTE + j] = ((DVI35Modul.getId1(index + i) >> j) & 1) != 0 ? true : false;
                }
            }
        }

        private int nodeIterator = 0, detIterator = 0;
        private Node currentNode;
        public void setPulses(int pulsesBytesCount, int index) {
            this.pulsesBytesCount = pulsesBytesCount;
            if (pulses == null || pulses.length != this.pulsesBytesCount * BITS_IN_BYTE) {
                pulses = new boolean[this.pulsesBytesCount * BITS_IN_BYTE];
            }
            for (int i = 0; i < this.pulsesBytesCount; i++) {
                for (int j = 0; j < BITS_IN_BYTE; j++) {
                    pulses[i * BITS_IN_BYTE + j] = ((DVI35Modul.getId1(index + i) >> j) & 1) != 0 ? true : false;
                }
            }
            System.out.println("ID1: pulses.length = " + pulses.length);

            int counter = 0;
            for (nodeIterator = 1; nodeIterator <= TeilKnoten.getAnzahl(); nodeIterator++) {
                currentNode = (Node)TeilKnoten.getTeilKnoten(nodeIterator);
                for (detIterator = 0; detIterator < currentNode.inputs.size(); detIterator++) {
                    if (currentNode.inputs.get(detIterator).isPulseFromControlCenter()) {
                        currentNode.inputs.get(detIterator).setSimulation(pulses[counter] ? 1 : 0);
                        counter++;
                    }
                }
            }
            System.out.println("ID1: counter = " + counter);
        }

        public void setPreemption(int preemptionDetectorsCount, int index) {
            this.preemptionDetectorsCount = preemptionDetectorsCount;
            this.bytesPerPreemptionDetector = DVI35Modul.getId1(index);

            if (preemptionDetectors == null || preemptionDetectors.length != this.preemptionDetectorsCount * this.bytesPerPreemptionDetector) {
                preemptionDetectors = new int[this.preemptionDetectorsCount * this.bytesPerPreemptionDetector];
            }
            for (int i = 0; i < this.preemptionDetectorsCount; i++) {
                for (int j = 0; j < this.bytesPerPreemptionDetector; j++) {
                    //pulses[i * BITS_IN_BYTE + j] = ((DVI35Modul.getId1(index + i) >> j) & 1) != 0 ? true : false;
                }
            }
        }

        private Detektor det; 
        private int demVal, extVal;
        public void setForceDets(int forceDetectorsBytesCount, int index) {
            this.forceDetectorsBytesCount = forceDetectorsBytesCount;
            if (forceDetectors == null || forceDetectors.length != this.forceDetectorsBytesCount * 2) {
                forceDetectors = new int[this.forceDetectorsBytesCount * 2];
                backupForceDetectors = new boolean[this.forceDetectorsBytesCount * 2];
            }
            for (int i = 0; i < this.forceDetectorsBytesCount; i++) {
                for (int j = 0; j < 2; j++) {
                    forceDetectors[i * 2 + j] = (DVI35Modul.getId1(index + i) >> j * 4) & 0x0F;
                }
            }

            if (forceDetectors != null) {
                for (int i = 0; i < forceDetectors.length; i++) {
                    det = Det.getDetektorByNum(i + 1);
                    if (det != null) {
                        demVal = forceDetectors[i] & 0x03;
                        extVal = (forceDetectors[i] >> 2) & 0x03;

                        if ((det instanceof DDetector) || (det instanceof DEDetector) || (det instanceof TPDetector)) {
                            if (demVal == 2) {
                                det.setSimulation(1);
                                this.backupForceDetectors[i] = true;
                            } else if (demVal == 3) {
                                det.setSimulation(0);
                                this.backupForceDetectors[i] = true;
                            } else if (demVal == 0 && backupForceDetectors[i]) {
                                det.setSimulation(-1);
                                backupForceDetectors[i] = false;
                            }
                        }

                        if ((det instanceof DEDetector) || (det instanceof EDetector) || (det instanceof QDetector)) {
                            if (extVal == 2) {
                                det.setSimulation(1);
                                this.backupForceDetectors[i] = true;
                            } else if (extVal == 3) {
                                det.setSimulation(0);
                                this.backupForceDetectors[i] = true;
                            } else if (extVal == 0 && backupForceDetectors[i]) {
                                det.setSimulation(-1);
                                backupForceDetectors[i] = false;
                            }
                        }
                    }
                }
            }
        }

        public void printId1() {
            System.out.println("------------------------------------------- ID-1  -------------------------------------------");
            //        System.out.println("Timeout: " + DVI35Modul.getId1(-1));
            System.out.println("Program: " + program);
            System.out.println("Mode1: " + mode1 + " | Mode2: " + mode2 + " | Mode3: " + mode3);
            System.out.println("Fire: " + fire);
            System.out.println("Detectors Bytes Count: " + detectorsBytesCount);
            if (detectors != null) {
                for (int i = 0; i < detectors.length; i++)
                    System.out.print((detectors[i] ? "V" : "X") + " ");
                System.out.println();
            }
            System.out.println("Pulses Bytes Count: " + pulsesBytesCount);
            if (pulses != null) {
                for (int i = 0; i < pulses.length; i++)
                    System.out.print((pulses[i] ? "V" : "X") + " ");
                System.out.println();
            }
            System.out.println("Preemption Detectors Count: " + preemptionDetectorsCount + " | Bytes per Preemption Detectors: " + bytesPerPreemptionDetector);
            if (preemptionDetectors != null) {
                for (int i = 0; i < preemptionDetectors.length; i++)
                    System.out.print(preemptionDetectors[i] + " ");
                System.out.println();
            }
            System.out.println("Detectors Force Bytes Count: " + forceDetectorsBytesCount);
            if (forceDetectors != null) {
                for (int i = 0; i < forceDetectors.length; i++)
                    System.out.print(forceDetectors[i] + " ");
                System.out.println();
            }
            System.out.println("Timeout = " + DVI35Modul.getId1(-1));
            //        System.out.println("9000 = " + DVI35Modul.getId1(9000));
            System.out.println("9001 = " + DVI35Modul.getId1(9001));
            System.out.println("---------------------------------------------------------------------------------------------");
        }
    }

    class FDPPId10 {
        // Byte 11
        public int program; 
        // Byte 12
        public int structure;
        // Byte 13
        public int status1;
        // Byte 14
        public int status2;
        // Byte 15
        public int status3;
        // Byte 16
        public int inputStatus;
        // Byte 17
        public int dayOfMonth;
        // Byte 18
        public int month;
        // Byte 19 
        public int year;
        // Byte 20 
        public int hours;
        // Byte 21 
        public int minute;
        // Byte 22
        public int second;
        // Byte 23
        public int cycleSecond;
        // Byte 24
        public int cycleSecond2;
        // Byte 25
        public int stage;
        // Byte 26
        public int stage2;
        // Detectors
        public int detectorsCount;
        public int[] occupancies;
        // Pulses
        public int pulsesCount;
        public boolean[] pulses;
        // Preemption Detectors
        public int preemptionDetectorsCount;
        public int bytesPerPreemptionDetector = 1;
        public int[] preemptionDetectors;
        // Moves
        public int movesCount;
        public int[] moves;

        public void setProgram(int program) {
            this.program = program;
        }
        /*
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
         */
    }

    public static final int DVI35_IND_PROG   = 0;
    public static final int DVI35_IND_MODE1  = 1;
    public static final int DVI35_IND_MODE2  = 2;
    public static final int DVI35_IND_MODE3  = 3;
    public static final int DVI35_IND_FIRE   = 4;
    public static final int DVI35_IND_DETS   = 5;

    public final Node node_1 = Var.tk1;
    public final Node node_2 = (TeilKnoten.getAnzahl() > 1 ? (Node)TeilKnoten.getTeilKnoten(2) : null);

    protected int keepAlivePreviousValue = -1;
    protected int keepAliveCurrentValue = 0;
    protected int keepAliveIntervalCounter = 0;
    protected int connectionTimeout = 120 * Var.ONE_SEC_MS;
    protected boolean isConnectionLost = true;


    private int nodesCount = 1;
    private int nodeIterator;
    private Node currentNode;

    protected FDPPId1 id1 = new FDPPId1(); 
    protected FDPPId10 id10 = new FDPPId10();
    private int byteIndex;
    public boolean isOperateInCentralFlash, isOperateInCentralDark;

    private Output[] outputs = null;

    public void setOutputs(Output[] outputs) {
        if (outputs != null) {
            this.outputs = new Output[outputs.length];
        } else {
            this.outputs = new Output[0];
        }

        if (this.outputs != null && this.outputs.length > 0) {
            for (int i = 0; i < this.outputs.length; i++) {
                this.outputs[i] = outputs[i];
            }
        }
    }

    public FDPPJerusalem() {
        super("FDPP [Jerusalem]", "1.2.01");
    }

    public boolean isPrcc() {
        return id1.isControl && DVI35Modul.isZentAktiv() && !isConnectionLost();
    }

    private void resetCentralControl() {
        nodesCount = TeilKnoten.getAnzahl();
        for (nodeIterator = 1; nodeIterator <= nodesCount; nodeIterator++) {
            currentNode = (Node)TeilKnoten.getTeilKnoten(nodeIterator);
            if (currentNode.centralProgram >= 0) {
                currentNode.centralProgram = -1;
            }
        }
        resetIncomingPulses();
        resetForceDetectors();
    }

    private void resetIncomingPulses() {
        if (id1.pulses != null) {
            for (detIndex = 0; detIndex < id1.pulses.length; detIndex++) {
                id1.pulses[detIndex] = false;
            }
        }
        for (nodeIterator = 1; nodeIterator <= nodesCount; nodeIterator++) {
            currentNode = (Node)TeilKnoten.getTeilKnoten(nodeIterator);
            for (detIndex = 0; detIndex < currentNode.inputs.size(); detIndex++) {
                if (currentNode.inputs.get(detIndex).isPulseFromControlCenter()) {
                    currentNode.inputs.get(detIndex).setSimulation(-1);
                }
            }
        }
    }

    private void resetForceDetectors() {
        if (id1.forceDetectors != null) {
            for (detIndex = 0; detIndex < id1.forceDetectors.length; detIndex++) {
                id1.forceDetectors[detIndex] = 0;
                Detektor det = Det.getDetektorByNum(detIndex + 1);
                if (det != null) {
                    det.setSimulation(-1);
                }
            }
        }
    }

    public void checkConnection() {
        keepAliveCurrentValue = sbh.vtbib.dvi35.DVI35Modul.getId1(1000);
        connectionTimeout     = sbh.vtbib.dvi35.DVI35Modul.getId1(1003) * Var.ONE_SEC_MS;

        if (keepAliveCurrentValue != keepAlivePreviousValue) {
            keepAlivePreviousValue = keepAliveCurrentValue;
            keepAliveIntervalCounter = 0;
            isConnectionLost = false;
        } else {
            keepAliveIntervalCounter += Var.tk1.getZyklDauer();
            if (keepAliveIntervalCounter >= connectionTimeout) {
                isConnectionLost = true;
            } else {
                isConnectionLost = false;
            }
        }
        
        CC_isConnected = !isConnectionLost;
        if (LED_CC_isConnected != null) {
            if (CC_isConnected) {
                LED_CC_isConnected.setAusgang();
            } else {
                LED_CC_isConnected.resetAusgang();
            }
        }
    }

    public boolean isConnectionLost() {
        return isConnectionLost;
    }

    public void getId1()
    {
        checkConnection();

        if (!DVI35Modul.isZentAktiv() || isConnectionLost()) {
            resetCentralControl();
            return;
        }

        // read ID 1 Bytes & Bits
        id1.setProgram   (DVI35Modul.getId1(DVI35_IND_PROG   )                                ); 
        id1.setMode1     (DVI35Modul.getId1(DVI35_IND_MODE1  )                                );
        id1.setMode2     (DVI35Modul.getId1(DVI35_IND_MODE2  )                                );
        id1.setMode3     (DVI35Modul.getId1(DVI35_IND_MODE3  )                                );
        id1.fire  =       DVI35Modul.getId1(DVI35_IND_FIRE   )                                 ;
        id1.setDetectors (DVI35Modul.getId1(DVI35_IND_DETS   ), byteIndex = DVI35_IND_DETS + 1);
        byteIndex += id1.detectorsBytesCount;
        id1.setPulses    (DVI35Modul.getId1(byteIndex        ), ++byteIndex                   );
        byteIndex += id1.pulsesBytesCount;
        id1.setPreemption(DVI35Modul.getId1(byteIndex        ), ++byteIndex                   );
        byteIndex += 1 + id1.bytesPerPreemptionDetector * id1.preemptionDetectorsCount;
        id1.setForceDets (DVI35Modul.getId1(byteIndex        ), ++byteIndex                   );

        // reading the Daylight Saving Time (DST) bit from ID5 (if set)
        if ((DVI35Modul.getId1(5000) & 0x80) > 0) {
            //		    Var.tk1.isDST.setAusgang();
        } else {
            //		    Var.tk1.isDST.resetAusgang();
        }
        //		id1.printId1();
    }

    public void setStatus() {
        if (!DVI35Modul.isZentAktiv() || isConnectionLost()) {
            return;
        }

        if (id1.isControl) {
            if (!id1.isOn) {
                setCentralProgram(SpecialProgram.Dark.getProgramNumber());
                isOperateInCentralDark = true;
                isOperateInCentralFlash = false;
            } else if (id1.isFlash) {
                setCentralProgram(SpecialProgram.Flash.getProgramNumber());
                isOperateInCentralDark = false;
                isOperateInCentralFlash = true;
            } else {
                // TODO
//                if (!Var.controller.isPreemption() || !(Var.tk1.preemption instanceof PreemptionJLRT) || !((PreemptionJLRT)Var.tk1.preemption).goToCata) {
//                    setCentralProgram(id1.program);
//                }
                isOperateInCentralDark = false;
                isOperateInCentralFlash = false;
            }
        } else {
            setCentralProgram(-1);
            isOperateInCentralDark = false;
            isOperateInCentralFlash = false;
        }

        if (id1.isReset) {
            AutoReset.reset("Reset command from Control Center");
        }

        for (int nodeIndex = 1; nodeIndex <= TeilKnoten.getAnzahl(); nodeIndex++) {
            Node node = (Node)TeilKnoten.getTeilKnoten(nodeIndex);
            if (id1.fire > 0) {
                node.isInFire = node.activeStageNumber == id1.fire;
                node.isSwitchingToFire = node.activeStageNumber != id1.fire;
                //                Var.tk1.fireReq.setAusgang();
                if (node.isSwitchingToFire) {
                    //                    Var.tk1.fireTrans.setAusgang();
                } else {
                    //                    Var.tk1.fireTrans.resetAusgang();
                }
                if (node.isInFire) {
                    //                    Var.tk1.fireState.setAusgang();
                } else {
                    //                    Var.tk1.fireState.resetAusgang();
                }
            } else {
                node.isSwitchingToFire = false;
                node.isInFire = false;
                //                Var.tk1.fireReq.resetAusgang();
                //                Var.tk1.fireTrans.resetAusgang();
                //                Var.tk1.fireState.resetAusgang();
            }
        }
    }

    private int nodeIndex, detIndex;

    private void setCentralProgram(int progNumber) {
        for (nodeIndex = 1; nodeIndex <= TeilKnoten.getAnzahl(); nodeIndex++) {
            ((Node)TeilKnoten.getTeilKnoten(nodeIndex)).centralProgram = progNumber;
        }   
    }

    private int getStatus1() {
        int mask = 0x00;

        if (node_1.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE)
            mask |= 0x01;
        if (id1.isControlSigns)
            mask |= 0x02;
        if (Var.controller.signs.signs_on.getAusgang())
            mask |= 0x04;
//        if (false) // police panel auto
//            mask |= 0x08;
//        if (false) // police panel flash
//            mask |= 0x10;
//        if (false) // police panel police prog
//            mask |= 0x20;
        if (true) // police panel door closed
            mask |= 0x40;
        if (AnlagenZustand.getTuerZustand() != AnlagenZustand.TUER_GEOEFFNET)
            mask |= 0x80;

        return mask;
    }

    private int getStatus2() {
        int mask = 0x00;

        if (node_1.getAktProg().getNummer() == node_1.all_dark.getNummer() || node_1.getAktProg().getNummer() == node_1.getDunkelProgramm().getNummer() || AnlagenZustand.isAbschaltgrad2(node_1) ||
                (node_2 != null && (node_2.getAktProg().getNummer() == node_2.all_dark.getNummer() || node_2.getAktProg().getNummer() == node_2.getDunkelProgramm().getNummer() || AnlagenZustand.isAbschaltgrad2(node_2))))
            mask |= 0x01;
        if (node_1.getAktProg().getNummer() == node_1.getBlinkProgramm().getNummer() || AnlagenZustand.isAbschaltgrad1(node_1) ||
                (node_2 != null && (node_2.getAktProg().getNummer() == node_2.getBlinkProgramm().getNummer() || AnlagenZustand.isAbschaltgrad1(node_2))))
            mask |= 0x02;
        if (node_1.isSwitchingToFire || (node_2 != null && node_2.isSwitchingToFire))
            mask |= 0x04;
        if (node_1.isInFire || (node_2 != null && node_2.isInFire))
            mask |= 0x08;
        if (node_1.isWaitingForSy || (node_2 != null && node_2.isWaitingForSy))
            mask |= 0x10;
        if (node_1.isInMapOnly() || (node_2 != null && node_2.isInMapOnly()))
            mask |= 0x20;
        if (node_1.getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber()) // in police program
            mask |= 0x40;
        if (id1.isCallAll) {
            mask |= 0x80;
            System.out.println("set id10 callall");
        }

        return mask;
    }

    private int getStatus3() {
        int mask = 0x00;

        if (AnlagenZustand.isStoerung(Var.tk1))
            mask |= 0x01;
        if (AnlagenZustand.isUga(Var.tk1) || AnlagenZustand.isUge(Var.tk1))
            mask |= 0x02;
        if (node_1.isDetectorsError() || (node_2 != null && node_2.isDetectorsError())/* || Var.detsError.belegt()*/)
            mask |= 0x04;
        if (AnlagenZustand.isAbschaltgrad2(node_1) || (node_2 != null && AnlagenZustand.isAbschaltgrad2(node_2)))
            mask |= 0x08;
        if (AnlagenZustand.isAbschaltgrad1(node_1) || (node_2 != null && AnlagenZustand.isAbschaltgrad1(node_2)))
            mask |= 0x10;
//        if (false) // is Signs Error
//            mask |= 0x20;
        //        if (notInUse)
        //            mask |= 0x40;
        //        if (notInUse)
        //            mask |= 0x80;

        return mask;
    }

    private int getInputStatus() {
        int mask = 0x00;

        if (Var.controller.ups.upsActive.belegt())
            mask |= 0x01;
        if (Var.controller.ups.upsWeak.belegt())
            mask |= 0x02;
        if (Var.controller.ups.upsWarning.belegt())
            mask |= 0x04;
        if (Var.controller.ups.upsFailure.belegt())
            mask |= 0x08;
        if (Var.controller.ups.upsInBypass.belegt())
            mask |= 0x10;
        if (Var.controller.ups.upsNoGridPower.belegt())
            mask |= 0x20;
        if (Var.controller.ups.upsDoorClosed.belegt())
            mask |= 0x40;
        if (Var.controller.isUseSIT() && Var.controller.sit.sitDoorOpen.belegt())
            mask |= 0x80;

        return mask;
    }

    public boolean operateSigns()
    {
        if (id1.isControlSigns) {
            if (id1.isSignsOn) {
                Var.controller.signs.signs_on.setAusgang();
            } else {
                Var.controller.signs.signs_on.resetAusgang();
            }
            return true;
        }
        return false;
    }

    public void setReplyToCenter() {
        int byteIndex = 17;
        int structure = 1;
        int node2ProgSec = node_2 != null ? node_2.getProgSek() : 0;
        Phase node1Phase = Phase.getAktivePhase(node_1);
        Phase node2Phase = node_2 != null ? Phase.getAktivePhase(node_2) : null;
        int node1PhaseNum = node1Phase != null ? node1Phase.getNummer() : 0;
        int node2PhaseNum = node2Phase != null ? node2Phase.getNummer() : 0;

        DVI35Modul.setResIDx0( 1, 0xFF, node_1.getAktProg().getNummer()); // Byte 11
        DVI35Modul.setResIDx0( 2, 0xFF, structure);                       // Byte 12
        DVI35Modul.setResIDx0( 3, 0xFF, getStatus1());                    // Byte 13
        DVI35Modul.setResIDx0( 4, 0xFF, getStatus2());                    // Byte 14
        DVI35Modul.setResIDx0( 5, 0xFF, getStatus3());                    // Byte 15
        DVI35Modul.setResIDx0( 6, 0xFF, getInputStatus());                // Byte 16
        //      DVI35Modul.setResIDx0( 7, 0xFF, dayOfMonth);                      // Byte 17
        //      DVI35Modul.setResIDx0( 8, 0xFF, month);                           // Byte 18
        //      DVI35Modul.setResIDx0( 9, 0xFF, year);                            // Byte 19
        //      DVI35Modul.setResIDx0(10, 0xFF, hours);                           // Byte 20
        //      DVI35Modul.setResIDx0(11, 0xFF, minute);                          // Byte 21
        //      DVI35Modul.setResIDx0(12, 0xFF, second);                          // Byte 22
        DVI35Modul.setResIDx0(13, 0xFF, node_1.getProgSek());             // Byte 23
        DVI35Modul.setResIDx0(14, 0xFF, node2ProgSec);                    // Byte 24
        DVI35Modul.setResIDx0(15, 0xFF, node1PhaseNum);                   // Byte 25
        DVI35Modul.setResIDx0(16, 0xFF, node2PhaseNum);                   // Byte 26
        //        System.out.println("Starting Dets @" + byteIndex);
        byteIndex = setLocalDetectors(byteIndex);
        //        System.out.println("Starting Pulses @" + byteIndex);
        byteIndex = setLocalPulses(byteIndex);
        //        System.out.println("Starting Pre Dets @" + byteIndex);
        byteIndex = setLocalPreemptionDetectors(byteIndex);
        //        System.out.println("Starting Moves @" + byteIndex);
        byteIndex = setSignalgroups(byteIndex);
        DVI35Modul.setResIDx0(byteIndex++, 0xFF, connectionTimeout / Var.ONE_SEC_MS);
    }

    private int setLocalDetectors(int byteIndex) {
        int localDetsCount = 0;
        for (nodeIndex = 0; nodeIndex < Var.controller.nodes.length; nodeIndex++) {
            Node node = Var.controller.nodes[nodeIndex];
            //            System.out.println("D   : " + node.demands.length);
            //            System.out.println("DE  : " + node.demandExtensions.length);
            //            System.out.println("E   : " + node.extensions.length);
            //            System.out.println("Q   : " + node.queues.length);
            //            System.out.println("TP  : " + node.pushButtons.length);
            //            System.out.println("BRTS: " + node.brts.length);
            //            System.out.println();

            for (detIndex = 0; detIndex < node.demands.size(); detIndex++) {
                if (!node.demands.get(detIndex).isPulseFromControlCenter()) {
                    localDetsCount++;
                }
            }

            for (detIndex = 0; detIndex < node.demandExtensions.size(); detIndex++) {
                if (!node.demandExtensions.get(detIndex).isPulseFromControlCenter()) {
                    localDetsCount++;
                }
            }

            for (detIndex = 0; detIndex < node.extensions.size(); detIndex++) {
                if (!node.extensions.get(detIndex).isPulseFromControlCenter()) {
                    localDetsCount++;
                }
            }

            for (detIndex = 0; detIndex < node.queues.size(); detIndex++) {
                if (!node.queues.get(detIndex).isPulseFromControlCenter()) {
                    localDetsCount++;
                }
            }

            for (detIndex = 0; detIndex < node.pushButtons.size(); detIndex++) {
                if (!node.pushButtons.get(detIndex).isPulseFromControlCenter()) {
                    localDetsCount++;
                }
            }

            if (node.internals != null) {
                localDetsCount += node.internals.size();
            }

            for (detIndex = 0; detIndex < node.brts.size(); detIndex++) {
                if (node.brts.get(detIndex).isLocalDetector()) {
                    localDetsCount++;
                }
            }
        }

        DVI35Modul.setResIDx0(byteIndex++, 0xFF, localDetsCount); // Byte 26

        return byteIndex + localDetsCount;
    }

    int bitIndex = 0;
    int byteValue = 0;
    int counter = 0;
    private int setLocalPulses(int byteIndex) {
        int localDetsCount = 0;

        for (nodeIndex = 0; nodeIndex < Var.controller.nodes.length; nodeIndex++) {
            Node node = Var.controller.nodes[nodeIndex];
            for (detIndex = 0; detIndex < node.outputsRemote.size(); detIndex++) {
                if (node.outputsRemote.get(detIndex).isPulseToControlCenter()) {
                    localDetsCount++;
                }
            }
        }

        if (this.outputs != null)
            localDetsCount = this.outputs.length;

        DVI35Modul.setResIDx0(byteIndex++, 0xFF, localDetsCount); // Byte 26

        bitIndex = 0;
        byteValue = 0;
        counter = 0;

        for (nodeIndex = 0; nodeIndex < Var.controller.nodes.length; nodeIndex++) {
            Node node = Var.controller.nodes[nodeIndex];
            for (detIndex = 0; detIndex < node.outputsRemote.size(); detIndex++) {
                if (node.outputsRemote.get(detIndex).isPulseToControlCenter()) {
                    if (node.outputsRemote.get(detIndex).isActive()) {
                        byteValue |= (0x01 << bitIndex);
                    }
                    counter++;
                    bitIndex++;
                    if (bitIndex == 8 || counter == localDetsCount) {
                        DVI35Modul.setResIDx0(byteIndex++, 0xFF, byteValue);
                        bitIndex = 0;
                        byteValue = 0;
                    }
                }
            }
        }
        return byteIndex;
    }

    private int setLocalPreemptionDetectors(int byteIndex) {
        int localDetsCount = 0;
        /*
        for (nodeIndex = 0; nodeIndex < Var.controller.nodes.length; nodeIndex++) {
            Node node = Var.controller.nodes[nodeIndex];
            for (detIndex = 0; detIndex < node.brts.length; detIndex++) {
                if (node.brts[detIndex].isLocalDetector()) {
                    localDetsCount++;
                }
            }
        }
         */
        // TODO: update appropriately

        DVI35Modul.setResIDx0(byteIndex++, 0xFF, localDetsCount);
        byteIndex++;

        for (int i = 0; i < localDetsCount; i++) {
            if (Var.isTest) {
                DVI35Modul.setResIDx0(byteIndex++, 0xFF, 0x20);
            } else {
                // TODO set data
                DVI35Modul.setResIDx0(byteIndex++, 0xFF, 0x00);
            }
        }

        //return byteIndex + localDetsCount * id10.bytesPerPreemptionDetector;
        return byteIndex;
    }

    private int setSignalgroups(int byteIndex) {
        int sgsCount = Sg.getSgArray().length;

        DVI35Modul.setResIDx0(byteIndex++, 0xFF, sgsCount);

        return byteIndex + (int)Math.ceil(sgsCount / 2.0);
    }

    /******************************************************************************/
    /******************************************************************************/
    /*
	public static boolean checkExitFromA_Central()
	{
		//reading SY from the dvi35 telegram (position of SY defined in DVI35.INI)
		//SY - the first bit
		return ((DVI35Modul.getId1(DVI35_IMPULSE_IND) & 0x01) == 0x01) || (useCentral == 0);
	}
     */
    public boolean checkExitFromA() {

        if ((Var.tk1.getAktStgEbene() != StgEbene.STG_NUM_ZENTRALE)
                && (Var.tk1.getAktStgEbene() != StgEbene.STG_NUM_UHR))
        {
            return true;
        }
        return true;
    }

    public boolean isOperateInCentralDark() {
        if (!DVI35Modul.isZentAktiv() || isConnectionLost()) {
            return false;
        }
        return isOperateInCentralDark;
    }

    public boolean isOperateInCentralFlash() {
        if (!DVI35Modul.isZentAktiv() || isConnectionLost()) {
            return false;
        }
        return isOperateInCentralFlash;
    }

    @Override
    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDetectorSet(Input detector) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isOperateSigns() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void monitorTelegram() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isOperateController() {
        // TODO Auto-generated method stub
        return false;
    }

    public int getFire() {
        return id1.fire;
    }

    public boolean isFire() {
        return !isConnectionLost() && id1.fire > 0;
    }

    public boolean isGreenWaveSY() {
        return false;
    }

    public boolean isOperateGreenWaveSY() {
        return false;
    }
}


