package core.central;

import hw.AnlagenZustand;
import m0547.Var;
import core.AutoReset;
import core.Move;
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
import sbh.vtbib.dvi35.DVI35Modul;
import vt.StgEbene;

public class FDPPNetiveiIsrael extends iCentral {
    private static final int BITS_IN_BYTE       = 8;
    
    class FDPPId1 {
        private static final int FORCE_DETS_IN_BYTE = 2;

        public int program;
        public int mode1;
        // Mode 1:          bit0  bit1     bit2            bit3       bit4       bit5     bit6      bit7
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
        public boolean[] forceDetectorsHistogram;

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
        
        public void setFire(int fire) {
            this.fire = fire;
        }

        public int readDetectors(int index) {
            this.detectorsBytesCount = DVI35Modul.getId1(index++);
            
            if (detectors == null || detectors.length != this.detectorsBytesCount * BITS_IN_BYTE) {
                detectors = new boolean[this.detectorsBytesCount * BITS_IN_BYTE];
            }
            
            for (int i = 0; i < this.detectorsBytesCount; i++) {
                for (int j = 0; j < BITS_IN_BYTE; j++) {
                    if (i * BITS_IN_BYTE + j < detectors.length) {
                        detectors[i * BITS_IN_BYTE + j] = ((DVI35Modul.getId1(index) >> j) & 0x01) != 0 ? true : false;
                    }
                }
                index++;
            }
            return index;
        }

        public int readPulses(int index) {
            this.pulsesBytesCount = DVI35Modul.getId1(index++);
            
            if (pulses == null || pulses.length != this.pulsesBytesCount * BITS_IN_BYTE) {
                pulses = new boolean[this.pulsesBytesCount * BITS_IN_BYTE];
            }
            
            for (int i = 0; i < this.pulsesBytesCount; i++) {
                for (int j = 0; j < BITS_IN_BYTE; j++) {
                    if (i * BITS_IN_BYTE + j < pulses.length) {
                        pulses[i * BITS_IN_BYTE + j] = ((DVI35Modul.getId1(index) >> j) & 0x01) != 0 ? true : false;
                    }
                }
                index++;
            }
            return index;
        }

        public void executePulses() {
            if (pulses != null) {
                int remotePulsesIndex = 0;
                for (int i = 0; i < Var.tk1.inputs.size(); i++) {
                    if (Var.tk1.inputs.get(i).isPulseFromControlCenter()) {
                        if (remotePulsesIndex < pulses.length) {
                            if (pulses[remotePulsesIndex]) {
                                Var.tk1.inputs.get(i).setManualDemand(true);
//                                Var.tk1.inputs.get(i).setSimulation( 1);
                            } else {
//                                Var.tk1.inputs.get(i).setManualDemand(false);
                                Var.tk1.inputs.get(i).resetManualDemand();
//                                Var.tk1.inputs.get(i).setSimulation( 0);
//                                Var.tk1.inputs.get(i).setSimulation(-1);
                            }
                        }
                        remotePulsesIndex++;
                    }
                }
            }
        }

        public int setPreemption(int index) {
            this.preemptionDetectorsCount   = DVI35Modul.getId1(index++);
            this.bytesPerPreemptionDetector = DVI35Modul.getId1(index++);
            
            if (preemptionDetectors == null || preemptionDetectors.length != this.preemptionDetectorsCount) {
                preemptionDetectors = new int[this.preemptionDetectorsCount];
            }
            
            for (int i = 0; i < this.preemptionDetectorsCount; i++) {
                preemptionDetectors[i] = 0;
                for (int j = 0; j < this.bytesPerPreemptionDetector; j++) {
                    preemptionDetectors[i] += DVI35Modul.getId1(index++) * Math.pow(255, j);
                }
            }
            return index;
        }

        private Detektor det; 
        private int demVal, extVal;
        
        public int readForceDetectors(int index) {
            this.forceDetectorsBytesCount = DVI35Modul.getId1(index++);
            
            if (forceDetectors == null || forceDetectors.length != this.forceDetectorsBytesCount * FORCE_DETS_IN_BYTE) {
                forceDetectors          = new int[this.forceDetectorsBytesCount * FORCE_DETS_IN_BYTE];
                forceDetectorsHistogram = new boolean[this.forceDetectorsBytesCount * FORCE_DETS_IN_BYTE];
            }
            
            for (int i = 0; i < this.forceDetectorsBytesCount; i++) {
                for (int j = 0; j < FORCE_DETS_IN_BYTE; j++) {
                    if (i * FORCE_DETS_IN_BYTE + j < forceDetectors.length) {
                        forceDetectors[i * FORCE_DETS_IN_BYTE + j] = ((DVI35Modul.getId1(index) >> (j * 4)) & 0x0F);
                    }
                }
                index++;
            }
            return index;
        }
        
        public void executeForceDets() {
            if (forceDetectors != null) {
                for (int i = 0; i < forceDetectors.length; i++) {
                    det = Det.getDetektorByNum(i + 1);
                    if (det != null) {
                        demVal =  forceDetectors[i]       & 0x03;
                        extVal = (forceDetectors[i] >> 2) & 0x03;

                        if ((det instanceof DDetector) || (det instanceof DEDetector) || (det instanceof TPDetector)) {
                            if (demVal == 2) {
//                                det.setSimulation(1);
                                ((Input)det).setManualDemand(true);
                            } else if (demVal == 3) {
//                                det.setSimulation(0);
                                ((Input)det).setManualDemand(false);
                            } else if (demVal == 0) {
                                if (forceDetectorsHistogram[i]) {
//                                    det.setSimulation( 0);
//                                    det.setSimulation(-1);
//                                    ((Input)det).setManualDemand(false); // TODO: maybe return ILIA
                                    ((Input)det).resetManualDemand();
                                }
                            }
                        }

                        if ((det instanceof DEDetector) || (det instanceof EDetector) || (det instanceof QDetector)) {
                            if (extVal == 2) {
//                                det.setSimulation(1);
                                ((Input)det).setManualDemand(true);
                            } else if (extVal == 3) {
//                                det.setSimulation(0);
                                ((Input)det).setManualDemand(false);
                            } else if (extVal == 0) {
                                if (forceDetectorsHistogram[i]) {
//                                    det.setSimulation( 0);
//                                    det.setSimulation(-1);
//                                    ((Input)det).setManualDemand(false); // TODO: maybe return ILIA
                                    ((Input)det).resetManualDemand();
                                }
                            }
                        }
                    }
                    forceDetectorsHistogram[i] = forceDetectors[i] != 0;
                }
            }
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
        
        public int setPulsesToCenter(int indexOfPulsesCountByte) {
            int offset = 0;
            int lastWrittenByteIndex = indexOfPulsesCountByte;
            for (int nodeIndex = 0; nodeIndex < Var.controller.nodes.length; nodeIndex++) {
                offset += Var.controller.nodes[nodeIndex].demands.size()
                        + Var.controller.nodes[nodeIndex].demandExtensions.size()
                        + Var.controller.nodes[nodeIndex].extensions.size()
                        + Var.controller.nodes[nodeIndex].queues.size()
                        + Var.controller.nodes[nodeIndex].pushButtons.size();
            }
            
            int pulsesToCenterCounter = 0, mask = 0;;
            for (int nodeIndex = 0; nodeIndex < Var.controller.nodes.length; nodeIndex++) {
                for (int i = 0; i < Var.controller.nodes[nodeIndex].outputsRemote.size(); i++) {
                    if (Var.controller.nodes[nodeIndex].outputsRemote.get(i).isPulseToControlCenter()) {
                        if (Var.controller.nodes[nodeIndex].outputsRemote.get(i).isActive()) {
                            mask |= 0x01 << (pulsesToCenterCounter % BITS_IN_BYTE);
                        }
                        pulsesToCenterCounter++;
                    }
                    if ((pulsesToCenterCounter > 0 && (pulsesToCenterCounter % BITS_IN_BYTE) == 0)
                            || (nodeIndex == Var.controller.nodes.length - 1 && i == Var.controller.nodes[nodeIndex].outputsRemote.size() - 1)) {
                        lastWrittenByteIndex = indexOfPulsesCountByte + offset + ((pulsesToCenterCounter - 1) / BITS_IN_BYTE);
                        DVI35Modul.setResIDx0(lastWrittenByteIndex, 0xFF, mask);
                        mask = 0;
                    }
                }
            }
            return lastWrittenByteIndex + 1;
        }
        
        public int setMovesErrorCodes(int indexOfMovesCountByte) {
            int offset = 1;
            int lastWrittenByteIndex = indexOfMovesCountByte;
            Move move;
            byte mask;
            
            for (int i = 0; i < Var.controller.moves.size(); i++) {
                mask = 0x00;
                move = Var.controller.moves.get(i);
                if (move.isUGAGreen) {
                    mask |= 0x01;
                } else if (move.isUGEGreen) {
                    mask |= 0x02;
                }

                if (move.isUGAAmber) {
                    mask |= 0x04;
                } else if (move.isUGEAmber) {
                    mask |= 0x08;
                }

                if (move.isUGARed) {
                    mask |= 0x10;
                } else if (move.isUGERed) {
                    mask |= 0x20;
                }
                lastWrittenByteIndex = indexOfMovesCountByte + offset + (i * 2) + 1;
                DVI35Modul.setResIDx0(lastWrittenByteIndex, 0xFF, mask);
            }
            
            return lastWrittenByteIndex + 1;
        }
    }

    protected int keepAlivePreviousValue   = Var.NONE;
    protected int keepAliveCurrentValue    =        0;
    protected int keepAliveIntervalCounter =        0;
    protected int connectionTimeout        =      120 * Var.ONE_SEC_MS;
    protected boolean isConnectionLost     =     true;
    protected boolean isDoneResetAfterCL   =    false;

    protected FDPPId1  id1  = new FDPPId1 (); 
    protected FDPPId10 id10 = new FDPPId10();
    protected int id1ByteIndex;
    protected boolean wasCallAllSet = false;
    
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

    public FDPPNetiveiIsrael() {
        super("FDPP [Netivei-Israel]", "7.0");
    }

    private void resetCentralControl() {
        if (!isDoneResetAfterCL) {
            for (int i = 0; i < Var.controller.nodes.length; i++) {
                if (Var.controller.nodes[i].centralProgram > Var.NONE) {
                    Var.controller.nodes[i].centralProgram = Var.NONE;
                }
            }
            if (id1 != null) {
                id1.setMode1(0x02);
                id1.setMode2(0x00);
                id1.setMode3(0x00);
                id1.setFire(0);
            }
            resetIncomingDetectors();
            resetIncomingPulses();
            resetForceDetectors();
            setCallAll(false);
            
            isDoneResetAfterCL = true;
        }
    }

    private void resetIncomingDetectors() {
        if (id1.detectors != null) {
            for (int detIndex = 0; detIndex < id1.detectors.length; detIndex++) {
                id1.detectors[detIndex] = false;
            }
        }
    }

    private void resetIncomingPulses() {
        if (id1.pulses != null) {
            for (int detIndex = 0; detIndex < id1.pulses.length; detIndex++) {
                id1.pulses[detIndex] = false;
            }
        }
        
        for (int nodeIndex = 0; nodeIndex < Var.controller.nodes.length; nodeIndex++) {
            for (int detIndex = 0; detIndex < Var.controller.nodes[nodeIndex].inputs.size(); detIndex++) {
                if (Var.controller.nodes[nodeIndex].inputs.get(detIndex).isPulseFromControlCenter()) {
//                    Var.controller.nodes[nodeIndex].inputs.get(detIndex).setSimulation( 0);
//                    Var.controller.nodes[nodeIndex].inputs.get(detIndex).setSimulation(-1);
//                    Var.controller.nodes[nodeIndex].inputs.get(detIndex).setManualDemand(false); // TODO: redo if needed
                    Var.controller.nodes[nodeIndex].inputs.get(detIndex).resetManualDemand();
                }
            }
        }
    }
    
    private void setCallAll(boolean isSet) {
        if (!wasCallAllSet && !isSet) {
            return;
        }

        wasCallAllSet = isSet;
        // TODO: maybe return ILIA
        for (int i = 0; i < Var.controller.nodes.length; i++) {
            for (int j = 0; j < Var.controller.nodes[i].demands.size(); j++) {
                if (isSet) {
                    Var.controller.nodes[i].demands.get(j).setManualDemand(isSet);
                } else {
                    Var.controller.nodes[i].demands.get(j).resetManualDemand();
                }
            }
            for (int j = 0; j < Var.controller.nodes[i].demandExtensions.size(); j++) {
                if (isSet) {
                    Var.controller.nodes[i].demandExtensions.get(j).setManualDemand(isSet);
                } else {
                    Var.controller.nodes[i].demandExtensions.get(j).resetManualDemand();
                }
            }
            for (int j = 0; j < Var.controller.nodes[i].extensions.size(); j++) {
                if (isSet) {
                    Var.controller.nodes[i].extensions.get(j).setManualDemand(isSet);
                } else {
                    Var.controller.nodes[i].extensions.get(j).resetManualDemand();
                }
            }
            for (int j = 0; j < Var.controller.nodes[i].queues.size(); j++) {
                if (isSet) {
                    Var.controller.nodes[i].queues.get(j).setManualDemand(isSet);
                } else {
                    Var.controller.nodes[i].queues.get(j).resetManualDemand();
                }
            }
            for (int j = 0; j < Var.controller.nodes[i].pushButtons.size(); j++) {
                if (isSet) {
                    Var.controller.nodes[i].pushButtons.get(j).setManualDemand(isSet);
                } else {
                    Var.controller.nodes[i].pushButtons.get(j).resetManualDemand();
                }
            }
        }
    }

    private void resetForceDetectors() {
        if (id1.forceDetectors != null) {
            for (int detIndex = 0; detIndex < id1.forceDetectors.length; detIndex++) {
                id1.forceDetectors[detIndex] = 0;
                if (id1.forceDetectorsHistogram[detIndex]) {
                    Input det = (Input)Det.getDetektorByNum(detIndex + 1);
                    if (det != null) {
//                        det.setSimulation( 0);
//                        det.setSimulation(-1);
//                        det.setManualDemand(false); // TODO: maybe return ILIA
                        det.resetManualDemand();
                    }
                }
                id1.forceDetectorsHistogram[detIndex] = false;
            }
        }
    }

    int bitIndex = 0;
    int byteValue = 0;
    int counter = 0;
    
    public void checkConnection() {
        keepAliveCurrentValue = sbh.vtbib.dvi35.DVI35Modul.getId1(1000);
        connectionTimeout     = sbh.vtbib.dvi35.DVI35Modul.getId1(1003) * Var.ONE_SEC_MS;

        if (keepAliveCurrentValue != keepAlivePreviousValue) {
            keepAlivePreviousValue = keepAliveCurrentValue;
            keepAliveIntervalCounter = 0;
            isConnectionLost = false;
        } else {
            if (keepAliveIntervalCounter < Var.MAX_COUNTER_VALUE) {
                keepAliveIntervalCounter += Var.tk1.getZyklDauer();
            }
            if (keepAliveIntervalCounter >= connectionTimeout) {
                isConnectionLost = true;
            } else {
                isConnectionLost = false;
            }
        }
        
        if (!DVI35Modul.isZentAktiv()) {
            isConnectionLost = true;
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
    
    private void setCentralProgram(int progNumber) {
        for (int i = 0; i < Var.controller.nodes.length; i++) {
            Var.controller.nodes[i].centralProgram = progNumber;
        }
    }
    
    private void readID1() {
        id1ByteIndex = 0;
        id1.setProgram(DVI35Modul.getId1     (id1ByteIndex++));
        id1.setMode1  (DVI35Modul.getId1     (id1ByteIndex++));
        id1.setMode2  (DVI35Modul.getId1     (id1ByteIndex++));
        id1.setMode3  (DVI35Modul.getId1     (id1ByteIndex++));
        id1.setFire   (DVI35Modul.getId1     (id1ByteIndex++));
        id1ByteIndex = id1.readDetectors     (id1ByteIndex);
        id1ByteIndex = id1.readPulses        (id1ByteIndex);
        id1ByteIndex = id1.setPreemption     (id1ByteIndex);
        id1ByteIndex = id1.readForceDetectors(id1ByteIndex);
    }
    private static boolean isCallOnce = true;
    private void executeID1() {
        if (id1.isControl) {
            if (!id1.isOn) {
                setCentralProgram(SpecialProgram.Dark.getProgramNumber());
            } else if (id1.isFlash) {
                setCentralProgram(SpecialProgram.Flash.getProgramNumber());
            } else {
                setCentralProgram(id1.program);
            }
            
            if (id1.fire > 0) {
                if (Var.controller.nodes[0].activeStageNumber == id1.fire) {
                    Var.controller.nodes[0].isSwitchingToFire = false;
                    Var.controller.nodes[0].isInFire = true;
                } else {
                    Var.controller.nodes[0].isSwitchingToFire = true;
                    Var.controller.nodes[0].isInFire = false;
                }
            } else {
                Var.controller.nodes[0].isSwitchingToFire = false;
                Var.controller.nodes[0].isInFire = false;
            }
            
            if (id1.isReset) {
//                Var.controller.reset.setAusgang();
                if (isCallOnce) {
                    AutoReset.reset("Reset command from Control Center");
                    isCallOnce = false;
                }
            }

//            if (Var.controller.reset.isActive() && Var.controller.reset.getStateTime() >= Var.ONE_SEC_MS) {
//                Var.controller.reset.resetAusgang();
//            }
        } else {
            setCentralProgram(Var.NONE);
            Var.controller.nodes[0].isSwitchingToFire = false;
            Var.controller.nodes[0].isInFire = false;
        }
        
        id1.executePulses();
        id1.executeForceDets();
        setCallAll(id1.isCallAll);
    }

    public void monitorTelegram() {
        checkConnection();

        if (isConnectionLost()) {
            resetCentralControl();
            return;
        }
        isDoneResetAfterCL = false;

        readID1();
        executeID1();

        // reading the Daylight Saving Time (DST) bit from ID5 (if set)
        if ((DVI35Modul.getId1(5000) & 0x80) > 0) {
            //          Var.tk1.isDST.setAusgang();
        } else {
            //          Var.tk1.isDST.resetAusgang();
        }
        //      id1.printId1();
    }
    
    private int getInputStatus1() {
        int result = 0;
        if (Var.controller.ups != null) {
            if (Var.controller.ups.upsActive     .IsActive()) { result |= 0x01; }
            if (Var.controller.ups.upsWeak       .IsActive()) { result |= 0x02; }
            if (Var.controller.ups.upsWarning    .IsActive()) { result |= 0x04; }
            if (Var.controller.ups.upsFailure    .IsActive()) { result |= 0x08; }
            if (Var.controller.ups.upsInBypass   .IsActive()) { result |= 0x10; }
            if (Var.controller.ups.upsNoGridPower.IsActive()) { result |= 0x20; }
            if (Var.controller.ups.upsDoorClosed .IsActive()) { result |= 0x40; } 
        }
        return result;
    }
    
    private int getStatus1() {
        int result = 0;
        boolean isCentralControl = true, isClockControl = true, isManualControl = false, isPoliceProgram = false, isBlinkProgram = false;
        for (int i = 0; i < Var.controller.nodes.length; i++) {
            if (Var.controller.nodes[i].getAktStgEbene() != StgEbene.STG_NUM_ZENTRALE) {
                isCentralControl = false;
            } 
            if (Var.controller.nodes[i].getAktStgEbene() != StgEbene.STG_NUM_UHR) {
                isClockControl = false;
            }
            if (Var.controller.nodes[i].getAktStgEbene() == StgEbene.STG_NUM_MANUELL) {
                isManualControl = true;
            }
            if (Var.controller.nodes[i].getAktProg().getNummer() == SpecialProgram.Flash.getProgramNumber()) {
                isBlinkProgram = true;
            }
            if (Var.controller.nodes[i].getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber()) {
                isPoliceProgram = true;
            }
        }
        
        if (isCentralControl)                                      { result |= 0x01; }
        if (id1.isControlSigns)                                    { result |= 0x02; }
        if (Var.controller.signs.signs_on.getAusgang())            { result |= 0x04; }
        if (Var.controller.isPoliceDoor()) {                      
            if (isCentralControl || isClockControl)                { result |= 0x08; }
            if (isManualControl && isBlinkProgram)                 { result |= 0x10; }
            if (isManualControl && isPoliceProgram)                { result |= 0x20; }
            if (Var.controller.policePanel.Door_Closed.IsActive()) { result |= 0x40; }
        }
        if (!Var.controller.isDoorOpen())                          { result |= 0x80; }
        
        return result;
    }
    
    private int getStatus2() {
        int result = 0;
        boolean isDark = false, isFlash = false, isToFire = false, isInFire = false, isWaitForSY = false, isMapOnly = false, isPolice = false;
        for (int i = 0; i < Var.controller.nodes.length; i++) {
            if (Var.controller.nodes[i].isDarkProgram() || hw.AnlagenZustand.isAbschaltgrad2(Var.controller.nodes[i])) {
                isDark = true;
            }
            if (Var.controller.nodes[i].getAktProg().getNummer() == SpecialProgram.Flash.getProgramNumber() || hw.AnlagenZustand.isAbschaltgrad1(Var.controller.nodes[i])) {
                isFlash = true;
            }
            if (Var.controller.nodes[i].isSwitchingToFire) {
                isToFire = true;
            }
            if (Var.controller.nodes[i].isInFire) {
                isInFire = true;
            }
            if (Var.controller.nodes[i].isWaitingForSy) {
                isWaitForSY = true;
            }
            if (Var.controller.nodes[i].isInMapOnly()) {
                isMapOnly = true;
            }
            if (Var.controller.nodes[i].getAktProg().getNummer() == SpecialProgram.Police.getProgramNumber()) {
                isPolice = true;
            }
        }
        
        if (isDark)        { result |= 0x01; }
        if (isFlash)       { result |= 0x02; }
        if (isToFire)      { result |= 0x04; }
        if (isInFire)      { result |= 0x08; }
        if (isWaitForSY)   { result |= 0x10; }
        if (isMapOnly)     { result |= 0x20; }
        if (isPolice)      { result |= 0x40; }
        if (id1.isCallAll) { result |= 0x80; }
        
        return result;
    }
    
    private boolean isSingleLampError() {
        for (int i = 0; i < Var.controller.moves.size(); i++) {
            if (Var.controller.moves.get(i).isUGARed
                    || Var.controller.moves.get(i).isUGAAmber
                    || Var.controller.moves.get(i).isUGAGreen
                    || Var.controller.moves.get(i).isUGERed
                    || Var.controller.moves.get(i).isUGEAmber
                    || Var.controller.moves.get(i).isUGEGreen)
            {
                return true;
            }
        }
        return false;
    }
    
    private int getStatus3() {
        int result = 0;

        if (AnlagenZustand.isStoerung(Var.tk1))
            result |= 0x01;
        if (AnlagenZustand.isUga(Var.tk1) || AnlagenZustand.isUge(Var.tk1) || isSingleLampError())
            result |= 0x02;
        for (int i = 0; i < Var.controller.nodes.length; i++) {
            if (Var.controller.nodes[i].isDetectorsError() || (Var.controller.isDetCardError != null && Var.controller.isDetCardError.belegt())) {
                result |= 0x04;
            }
            if (hw.AnlagenZustand.isAbschaltgrad2(Var.controller.nodes[i])) {
                result |= 0x08;
            }
            if (hw.AnlagenZustand.isAbschaltgrad1(Var.controller.nodes[i])
                    && !hw.AnlagenZustand.isAbschaltgrad2(Var.controller.nodes[i])) {
                result |= 0x10;
            }
        }
        if (Var.controller.signs.isError())              { result |= 0x20; }
        if (Var.controller.isNetworkCabinetDoorClosed()) { result |= 0x40; }

        return result;
    }
    
    private int getStatus4() {
        int result = 0;

        if (Var.controller.isErrorInSK24())                                   { result |= 0x01; }
        if (Var.controller.isErrorInIO24() || Var.controller.isErrorInIO64()) { result |= 0x02; }
        if (Var.controller.isErrorInDetectorsCard())                          { result |= 0x04; }
        for (int i = 0; i < Var.controller.nodes.length; i++) {
            if (Var.controller.nodes[i].greenwaveIsSyErrorHW()) {
                result |= 0x08; 
            }
            if (Var.controller.nodes[i].greenwaveIsSyErrorClock()) {
                result |= 0x10; 
            }
        }
        
        return result;
    }
    
    public void setReplyToCenter() {
        int address;
        DVI35Modul.setResIDx0( 1, 0xFF, Var.controller.nodes[0].getAktProg().getNummer());
        DVI35Modul.setResIDx0( 2, 0xFF, Var.controller.nodes[0].currStructure);
        DVI35Modul.setResIDx0( 3, 0xFF, getInputStatus1());
//      DVI35Modul.setResIDx0( 4, 0xFF, dayOfMonth);
//      DVI35Modul.setResIDx0( 5, 0xFF, month);
//      DVI35Modul.setResIDx0( 6, 0xFF, year);
//      DVI35Modul.setResIDx0( 7, 0xFF, hours);
//      DVI35Modul.setResIDx0( 8, 0xFF, minute);
//      DVI35Modul.setResIDx0( 9, 0xFF, second);
        DVI35Modul.setResIDx0(10, 0xFF, Var.controller.nodes[0].getProgSek());
        DVI35Modul.setResIDx0(11, 0xFF, Var.controller.nodes.length > 1 ? Var.controller.nodes[1].getProgSek() : 0);
        DVI35Modul.setResIDx0(12, 0xFF, Var.controller.nodes[0].activeStageNumber);
        DVI35Modul.setResIDx0(13, 0xFF, Var.controller.nodes.length > 1 ? Var.controller.nodes[1].activeStageNumber : 0);
//      DVI35Modul.setResIDx0(14, 0xFF, statuses);
        DVI35Modul.setResIDx0(15, 0xFF, getStatus1());
        DVI35Modul.setResIDx0(16, 0xFF, getStatus2());
        DVI35Modul.setResIDx0(17, 0xFF, getStatus3());
        DVI35Modul.setResIDx0(18, 0xFF, getStatus4());
//        DVI35Modul.setResIDx0(19, number of detectors);
//        DVI35Modul.setResIDx0(19 + number of detectors + 1, number of pulses);
        address = id10.setPulsesToCenter(20 + Var.controller.totalDetectorsCount); // address hold the next index after the last pulses byte
        id10.setMovesErrorCodes(address + 2); // TODO: update after handling CAPSYS detectors
    }
    
    public boolean isConnected() {
        return !isConnectionLost();
    }
    
    public boolean isOperateSigns() {
        if (isConnected() && id1.isControlSigns) {
            if (id1.isSignsOn) {
                Var.controller.signs.signs_on.setAusgang();
            } else {
                Var.controller.signs.signs_on.resetAusgang();
            }
        }
        return isConnected() && id1.isControlSigns;
    }
    
    public boolean isDetectorSet(Input detector) {
        if (detector == null
                || !detector.isRemoteDetector()
                || id1 == null
                || id1.detectors == null
                || detector.getRemoteDetectorIndex() < 0
                || detector.getRemoteDetectorIndex() >= id1.detectors.length) {
            return false;
        }
        
        return id1.detectors[detector.getRemoteDetectorIndex()];
    }
    
    public boolean isOperateController() {
        return isConnected() && id1.isControl;
    }
    
    public boolean isOperateInCentralFlash() {
        return isConnected() && id1.isControl && id1.isFlash;
    }
    
    public boolean isOperateInCentralDark() {
        return isConnected() && id1.isControl && !id1.isOn;
    }

    public int getFire() {
        return id1.fire;
    }

    public boolean isFire() {
        return isConnected() && id1.isControl && (id1.fire > 0);
    }

    public boolean isGreenWaveSY() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isOperateGreenWaveSY() {
        return false;
    }
}


