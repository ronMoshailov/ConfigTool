package m0547;

import itc.ITC;
import itc.ITCProtocol;
import core.AppType;
import core.Controller;
import core.MathHelper;
import core.Move;
import core.Scheduler;
import core.central.iCentral;
import core.detectors.Input;
import core.detectors.Output;
import core.detectors.TPDetector;
import core.greenwave.GreenWaveHW;
import core.greenwave.iGreenWave;
import core.greenwave.GreenWaveHW.MasterPulseType;
import modbus.JLRTModBus;
import det.*;     
import enp.utilities.VtRam;
import hw.*;                              
import hw.hwlmp.*;                        
import hw.nkk.*;                          
import hw.tk.*;                           
import sbh.vtbib.dvi35.DVI35Modul;        
import sg.*;                               
import vt.*;                              
import vtvar.*;  

public class Init implements enp.Initialisierung {
    public static String anlagenName = " Netivei-Israel Junc.0547";
    public static String tk1Name     = " Road 721 / Atlit Interchange - East";
    public static String version     = " v2.0.0 G5tf.7";
    public static String revision    = " 28/01/2025";
    public static String date        = " 12/03/2026";
    public static String editor      = " Ilia B.";

    private HwLmpTyp hwRed200;  
    private HwLmpTyp hwAmber200;
    private HwLmpTyp hwGreen200;

    public static void main(String[] args) {
        System.out.println(anlagenName + tk1Name + version + revision + date + editor);

        Init init1 = new Init();
        enp.Actros.registerInitialisierung(init1);

        init1.initialisiereTk(); //hardware and software intersection

        init1.initialisiereSg(); //signal-groups
        init1.initialisiereZwz(); //intergreen matrix
        init1.initialisiereDet();  //detectors
        init1.initialisiereProgs(); //programs
        init1.initialisierePhasen(); //stages and interstages
        init1.initialisiereZentrale(); //central-command
        init1.initialisiereOEV(); //public transport
        init1.initialiseITC();
        if (!Scheduler.isUseCustomScheduler()) {
            init1.initialisiereParameter(); //parameters
            init1.initialisiereUhr(); //time table
        } else {
            init1.initialisiereUhr(); //time table
            init1.initialisiereParameter(); //parameters
        }
        init1.initialisiereHW(); //hardware (cards)
        init1.initialiseSIT();
    }
    
    protected void initialiseSIT() {
        System.out.println("Initialisiere Special Protocols...");
        if (Var.controller.isUseSIT()) {
            Var.controller.sit = new JLRTModBus();
        }
        
        
        System.out.println("special protocols initialized");
    }
    
    protected void initialiseITC() {
        Var.controller.itcProtocol = new ITCProtocol("ITC", "192.168.16.99", "192.168.60.99", 17000, 17000, 10, false);
        Var.tk1.itc = new ITC(Var.tk1);
//        Var.tk2.itc = new ITC(Var.tk2);
//        Var.tk3.itc = new ITC(Var.tk3);
    }

    protected void initialisiereTk(){
        System.out.println();
        System.out.println("Initialisiere Hardware-Knoten...");

        //Hardware-Knoten initialisieren
        // TODO: update according to the required number of hardwares 
        Var.hwTk1 = new HwTeilknoten230V("HWKnoten1");
        //		Var.hwTk2 = new HwTeilknoten230V("HWKnoten2");
        //		Var.hwTk3 = new HwTeilknoten230V("HWKnoten3");

        System.out.println("Initialisiere Software-Teilknoten...");
        System.out.println("...vor tk");
        //Software-Teilknoten initialisieren
        //				                                  name   | hardware | is use | 1st cycle |
        //                                                       |          | Dx     | extension |
        // TODO: update according to the number of nodes
        Var.controller = new Controller(Var.tk1 = new Tk1(tk1Name, Var.hwTk1, false  ,         0)/*,
                                        Var.tk2 = new Tk2(tk2Name, Var.hwTk2, false  ,         1),
                                        Var.tk3 = new Tk3(tk3Name, Var.hwTk3, false  ,         1)*/);
        Var.controller.setAppType(AppType.NetiveiIsrael);
                                        
        System.out.println("...nach tk");
    }

    protected void initialisiereDet() {
        // --------------------------------------------------------------------
        // Logic I/O numbers:                                                  
        // --------------------------------------------------------------------
        // Application Specific I/O numbers:                                   
        // Inputs:                                                             
        //   - Detectors, Push Buttons, Pulses & Inputs: ..............   1-100
        //   - Synoptic Map Inputs: ................................... 121-200
        // Outputs:                                                            
        //   - Synoptic Map Outputs: ..................................   1-100
        //   - Push Buttons Feedback, Pulses & Outputs: ............... 121-200
        // --------------------------------------------------------------------
        // Constant I/O numbers for all applications:                          
        // Inputs:                                  
        //   - Detectors Card(s) Error: ............................... 220
        //   - Police Keyboard: ....................................... 221-226
        //   - Green Wave: ............................................ 231-245
        //   - UPS: ................................................... 246-252
        //   - SIT: ................................................... 253
        //   - Network Cabinet Door: .................................. 254
        //   - Synoptic Map Active: ................................... 255    
        // Outputs:                                                            
        //   - Police Keyboard: ....................................... 221-225
        //   - Green Wave: ............................................ 231-245
        //   - Signs: ................................................. 251    
        //   - Cycle Start Pulse: ..................................... 252    
        //   - Reset Relay: ........................................... 255    
        // --------------------------------------------------------------------
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].nodeInitializer.initialisiereDet();
            Var.controller.totalDetectorsCount += Var.controller.nodes[j].demands.size() + Var.controller.nodes[j].demandExtensions.size() + Var.controller.nodes[j].extensions.size() + Var.controller.nodes[j].queues.size() + Var.controller.nodes[j].pushButtons.size();
        }
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].nodeInitializer.initialisierePulses();
        }
        
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].initializeDet();
            Var.controller.nodes[j].setTK();
        }
        
        // TODO: update greenwave initialization according to what's required in the current application
        // Any node can have a greenwave class attached to it
        // More than one greenwave class or type can be attached to a single node, as long as they do not contradict each other
        
//        Var.tk1.greenwaveAdd(GreenWaveHW.initializeMaster(Var.tk1, 5, MasterPulseType.ProgramSwitchPointSec, 1, new String[] { "P3" }).setBreathingGW(new String[] { "P1", "P2" })/*.setNoGWSchedule(10)*/);
        //                                                node   | PR lines | First GW Prog |        SY pulse(s) name(s) |               mSY2 pulse(s) name(s) | is ULin | is ULout
        Var.tk1.greenwaveAdd(GreenWaveHW.initializeSlave (Var.tk1,        5 ,             1 , new String[] { "P2", "P3" }).setBreathingGW(new String[] { "P1" })/*.setULin()*//*.setULin()*/);
//        Var.tk1.greenwaveAdd(new GreenWaveClock(Var.tk1, false, GreenWaveClock.SYNC_1970));

        Var.controller.initializeDet();
        if (Var.controller.isPoliceDoor()) {
            Var.controller.policePanel.initializeDet();
        }
        Var.controller.signs.initializeDet();
        Var.controller.ups.initializeDet();

        Det.endInit();
        System.out.println("input/output initialized");
    }

    /**
     * 
     */
    protected void initialisiereHW(){
        int cardNumber = 1, channelNumber = 1;
        //initialize device
        Var.device = new Anlage(Var.tk1/*, Var.tk2*/);
        Var.device.setName(anlagenName);
        //set internal cycle to 500 ms
        Var.device.setZyklZeitHalbeSek();
        
        if (Var.controller.log != null) {
            VtRam.init(60, 8404385, VtRam.DEVICE_ROM2);
        }

        //changes behavior of safety to needed behavior according to Israel
        Anlage.setPruefungen(Anlage.MIN_PRUEF_ABSCH | Anlage.ZWZ_PRUEF_ABSCH);

        //hardware lamp types 		/* 9w leds */
        hwRed200   = new LmpMin230V(Hw.ROT);
        hwAmber200 = new LmpMin230V(Hw.GELB);
        hwGreen200 = new LmpMin230V(Hw.GRUEN);

        //NKK
        //         new Nkk230V50Hz(); - old Backplane
        new NkkFusion230V50Hz(); // for new Alpha Backplane without NKK Card

        //switch cards
        SK24[] skCards = new SK24[] {
                new SK24(cardNumber++, (HwTeilknoten)Var.hwTk1)
        };

        //input/output-cards
        IO24 io1 = new IO24(cardNumber++); 
        IO24 io2 = new IO24(cardNumber++);
        IO24 io3 = new IO24(cardNumber++);
        
//        IO64 io64 = new IO64(cardNumber++);

        if (!Var.isProduction && Var.controller.isPreemption()) { // TODO: cancel after finish debug
            /*
			IO24 io3  = new IO24(cardNumber++);
			initializeIO(SpecialInOuts.checkPuls    , io3, 24);
             */
            IO64 io64_2 = new IO64(cardNumber++); 
            initializeIO(Var.controller.cycleStartPulse, io64_2, 39); // pulse for checking preemption application
        }

        //SK24 channels
        //                  move | sk24 array | IO24 card for RM_Gr | IO24 channel for RM_Gr 
        initializeSK24(Var.tk1.k1,    skCards ,                 io2 , 17 );
        initializeSK24(Var.tk1.k2,    skCards ,                 io2 , 18 );
        initializeSK24(Var.tk1.k3,    skCards ,                 io2 , 19 );
        initializeSK24(Var.tk1.k4,    skCards ,                 io2 , 20 );
//        initializeSK24(Var.tk1.Bl,     skCards);

        //Can be Compact only if:
        // 1. A Maatz application. In Haifa ONLY ALPHA!
        // 2. Has only 1 SK24 card, and only 1 IO24 card.
        // 3. There are maximum 8 inputs and 4 outputs on the IO24 card.
        // 4. Lamps in the junction are LEDS 
        //if Compact: Input channels: 3, 4, 7, 8, 13 - 16. Output channels: 21 - 24

        //           Input                    | IO24 for  | IO24    | IO24 channel
        //                                    | the Input | for DSY | for Input & DSY
        initializeIO(Var.tk1.e_1              ,       io1 ,     io2 ,  1 );
        initializeIO(Var.tk1.e_2              ,       io1 ,     io2 ,  2 );
        initializeIO(Var.tk1.d_3              ,       io1 ,     io2 ,  3 );
        initializeIO(Var.tk1.e_3              ,       io1 ,     io2 ,  4 );
        initializeIO(Var.tk1.d_4              ,       io1 ,     io2 ,  5 );
        initializeIO(Var.tk1.e_4              ,       io1 ,     io2 ,  6 );
        initializeIO(GreenWaveHW.SYpulses[0]  ,       io1 ,            7 ); // P2
        initializeIO(GreenWaveHW.SYpulses[1]  ,       io1 ,            8 ); // P3
        initializeIO(GreenWaveHW.prInputs[0]  ,       io1 ,            9 );
        initializeIO(GreenWaveHW.prInputs[1]  ,       io1 ,           10 );
        initializeIO(GreenWaveHW.prInputs[2]  ,       io1 ,           11 );
        initializeIO(GreenWaveHW.prInputs[3]  ,       io1 ,           12 );
        initializeIO(GreenWaveHW.prInputs[4]  ,       io1 ,           13 );
        initializeIO(Var.tk1.ps2              ,       io1 ,           14 ); // PS2 

        //           Output                   |     IO24  |      channel 
        initializeIO(GreenWaveHW.mSY2pulses[0],       io1 ,           17 ); // P1
        initializeIO(Var.tk1.ps1              ,       io1 ,           18 ); // PS1 

//        initializeSpecialInputsOnIO64(io2, io64);
        initializeSpecialInputsOnIO24(io3);

        System.out.println("java initialization of the device complete!");
        Var.device.startAnlage();
    }
    
    /**
     * Initializes the hardware channels for all the special inputs related to:
     *   - FDPP
     *   - Police Panel
     *   - UPS
     *   - Signs On
     * This method should only be used in applications that do not have an IO64 card
     * @param io24FDPP
     */
    private void initializeSpecialInputsOnIO24(IO24 io24FDPP) {
        Var.controller.isUseMapActiveInput = true;
        // I/O24 - Input channels 1-16                            
        if (Var.controller.isPoliceDoor()) {                      
            initializeIO(Var.controller.policePanel.Flash         , io24FDPP,  1);
            initializeIO(Var.controller.policePanel.Dark          , io24FDPP,  2);
            initializeIO(Var.controller.policePanel.Automat       , io24FDPP,  3);
            initializeIO(Var.controller.policePanel.Hand          , io24FDPP,  4);
            initializeIO(Var.controller.policePanel.FS            , io24FDPP,  5);
            initializeIO(Var.controller.policePanel.Door_Closed   , io24FDPP,  7);
        }                                                         
        if (Var.controller.isSynopticMap()) {                     
            initializeIO(Var.controller.isMapActive               , io24FDPP,  6);
        }

        if (Var.controller.isDetCardError != null && Var.controller.isAppNetiveiIsrael()) {
            initializeIO(Var.controller.isDetCardError            , io24FDPP,  8);
        }
        
        if (Var.controller.sit != null && Var.controller.sit.sitDoorOpen != null && Var.controller.isAppJerusalem()) {
            initializeIO(Var.controller.sit.sitDoorOpen           , io24FDPP,  8);
        }
        
        if (Var.controller.isAppNetiveiIsrael()) {
            initializeIO(Var.controller.isNetworkCabinetDoorClosed, io24FDPP,  9);
        }
        
        if (Var.controller.ups != null) {
            if (Var.controller.ups.upsActive != null) {
                initializeIO(Var.controller.ups.upsActive         , io24FDPP, 10);
            }                                                    
            if (Var.controller.ups.upsWeak != null) {             
                initializeIO(Var.controller.ups.upsWeak           , io24FDPP, 11);
            }                                                     
            if (Var.controller.ups.upsWarning != null ) {         
                initializeIO(Var.controller.ups.upsWarning        , io24FDPP, 12);
            }                                                     
            if (Var.controller.ups.upsFailure != null ) {         
                initializeIO(Var.controller.ups.upsFailure        , io24FDPP, 13);
            }                                                     
            if (Var.controller.ups.upsInBypass != null ) {        
                initializeIO(Var.controller.ups.upsInBypass       , io24FDPP, 14);
            }                                                     
            if (Var.controller.ups.upsNoGridPower != null ) {     
                initializeIO(Var.controller.ups.upsNoGridPower    , io24FDPP, 15);
            }                                                     
            if (Var.controller.ups.upsDoorClosed != null ) {      
                initializeIO(Var.controller.ups.upsDoorClosed     , io24FDPP, 16);
            }
        }
        
        // I/O24 - Output channels 17-24
        if (Var.controller.isPoliceDoor()) {
            initializeIO(Var.controller.policePanel.Led_Flash     , io24FDPP, 17);
            initializeIO(Var.controller.policePanel.Led_Dark      , io24FDPP, 18);
            initializeIO(Var.controller.policePanel.Led_Automat   , io24FDPP, 19);
            initializeIO(Var.controller.policePanel.Led_Hand      , io24FDPP, 20);
            initializeIO(Var.controller.policePanel.FS_ready      , io24FDPP, 21);
        }                                                         
        if (iGreenWave.Led_GwFail != null) {                      
            initializeIO(iGreenWave.Led_GwFail                    , io24FDPP, 22);
        }                                                         
        if (iGreenWave.Led_SyOffset != null) {                      
            initializeIO(iGreenWave.Led_SyOffset                  , io24FDPP, 23);
        }                                                         
//        if (Var.controller.reset != null) {                       
//            initializeIO(Var.controller.reset                     , io24FDPP, 23);
//        }                                                         
        initializeIO(Var.controller.signs.signs_on                , io24FDPP, 24);
    }
    
    /**
     * Initializes the hardware channels for all the special inputs related to:
     *   - FDPP
     *   - Police Panel
     *   - UPS
     *   - Signs On
     * This method should only be used in applications that have an IO64 card
     * @param io24ForSigns - the IO24 that has the Signs On output (usually, the first io24)
     * @param io24ForFDPP - the IO24 that has the special FDPP inputs and outputs (usually, the last io24)
     * @param io64ForPolice - the IO64 for the police panel and other special IO
     */
    private void initializeSpecialInputsOnIO64(IO24 io24ForFDPP, IO64 io64ForPolice) {
        Var.controller.isUseMapActiveInput = false;
        // I/O24 - Input channels 1-32                            
        if (Var.controller.isPoliceDoor()) {                      
            initializeIO(Var.controller.policePanel.Flash         , io64ForPolice,  1);
            initializeIO(Var.controller.policePanel.Dark          , io64ForPolice,  2);
            initializeIO(Var.controller.policePanel.Automat       , io64ForPolice,  3);
            initializeIO(Var.controller.policePanel.Hand          , io64ForPolice,  4);
            initializeIO(Var.controller.policePanel.FS            , io64ForPolice,  5);
            initializeIO(Var.controller.policePanel.Door_Closed   , io64ForPolice,  7);
        }                                                         
//        if (Var.controller.isSynopticMap()) {                     
//            initializeIO(Var.controller.isMapActive               , io64ForPolice,  8); // TODO: consult with Jacob if to keep this. Previously, channel 8 was reserved for DOOR_N_C but never actually used
//        }

        if (Var.controller.isDetCardError != null && Var.controller.isAppNetiveiIsrael()) {
            initializeIO(Var.controller.isDetCardError            , io24ForFDPP,  8);
        }
        
        if (Var.controller.sit != null && Var.controller.sit.sitDoorOpen != null && Var.controller.isAppJerusalem()) {
            initializeIO(Var.controller.sit.sitDoorOpen           , io24ForFDPP,  8);
        }
        
        if (Var.controller.isAppNetiveiIsrael()) {
            initializeIO(Var.controller.isNetworkCabinetDoorClosed, io24ForFDPP,  9);
        }
        
        if (Var.controller.ups != null) {
            if (Var.controller.ups.upsActive != null) {
                initializeIO(Var.controller.ups.upsActive         , io24ForFDPP, 10);
            }                                                    
            if (Var.controller.ups.upsWeak != null) {             
                initializeIO(Var.controller.ups.upsWeak           , io24ForFDPP, 11);
            }                                                     
            if (Var.controller.ups.upsWarning != null ) {         
                initializeIO(Var.controller.ups.upsWarning        , io24ForFDPP, 12);
            }                                                     
            if (Var.controller.ups.upsFailure != null ) {         
                initializeIO(Var.controller.ups.upsFailure        , io24ForFDPP, 13);
            }                                                     
            if (Var.controller.ups.upsInBypass != null ) {        
                initializeIO(Var.controller.ups.upsInBypass       , io24ForFDPP, 14);
            }                                                     
            if (Var.controller.ups.upsNoGridPower != null ) {     
                initializeIO(Var.controller.ups.upsNoGridPower    , io24ForFDPP, 15);
            }                                                     
            if (Var.controller.ups.upsDoorClosed != null ) {      
                initializeIO(Var.controller.ups.upsDoorClosed     , io24ForFDPP, 16);
            }
        }
        
        // I/O24 - Output channels 33-64
        if (Var.controller.isPoliceDoor()) {
            initializeIO(Var.controller.policePanel.Led_Flash     , io64ForPolice, 33);
            initializeIO(Var.controller.policePanel.Led_Dark      , io64ForPolice, 34);
            initializeIO(Var.controller.policePanel.Led_Automat   , io64ForPolice, 35);
            initializeIO(Var.controller.policePanel.Led_Hand      , io64ForPolice, 36);
            initializeIO(Var.controller.policePanel.FS_ready      , io64ForPolice, 37);
        }
        
        if (iCentral.LED_CC_isConnected != null) {
            initializeIO(iCentral.LED_CC_isConnected              , io64ForPolice, 38);
        }
        
        if (iGreenWave.Led_SyOffset != null) {                      
            initializeIO(iGreenWave.Led_SyOffset                  , io64ForPolice, 40);
        }
        if (iGreenWave.Led_GwFail != null) {                      
            initializeIO(iGreenWave.Led_GwFail                    , io64ForPolice, 41);
        }
        if (iGreenWave.Led_GwActive != null) {                      
            initializeIO(iGreenWave.Led_GwActive                  , io64ForPolice, 42);
        }                                                         
        
//        if (Var.controller.reset != null) {                       
//            initializeIO(Var.controller.reset                     , io24ForFDPP  , 23);
//        }                                                         
        initializeIO(Var.controller.signs.signs_on                , io24ForFDPP  , 24);
    }
    
    private int sk24Index = 0, sk24Channel = 1;
    
    /**
     * This method automatically allocates SK24 to the signal groups.
     * In case you need to leave "blank" channels, use the initializeSK24BlankChannels method
     * @param move - the move that needs assignment
     * @param sk24Cards - array of all the SK24 cards
     */
    private void initializeSK24(Move move, SK24[] sk24Cards) {
        if (move == null) {
            throw new IllegalArgumentException("Received an uninitialized move for SK24 index " + sk24Index + " channel " + sk24Channel);
        }
        
        if (sk24Cards == null) {
            throw new IllegalArgumentException("Received a null SK24 array for SK24 index " + sk24Index + " channel " + sk24Channel);
        }
        
        if (!MathHelper.isInRange(sk24Channel, 1, 24)) {
            throw new IllegalArgumentException("Invalid channel received for an SK24 when initializing move " + move.getName() + ". Only channels 1-24 are accepted");
        }
        
        if (move.isLRT()) {
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lred  , hwRed200  , Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lamber, hwAmber200, Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lgreen, hwGreen200, Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
        } else if (move.isTraffic()) {
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lred  , hwRed200  , Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lamber, hwAmber200, Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lgreen, hwGreen200, Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
        } else if (move.isPedestrian()) {
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lred  , hwRed200  , Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lgreen, hwGreen200, Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.SK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
        } else if (move.isBlinker() || move.isPreemptionTriangle()) {
            if (sk24Index < 0 || sk24Index >= sk24Cards.length) {
                throw new IllegalArgumentException("Received an illegal SK24 card index when initializing move " + move.getName());
            }
            new SchaltKanal(move, Move.lamber_nuge, hwAmber200, Hw.HF, sk24Cards[sk24Index], sk24Channel++, Hw.KK);
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
        }
    }
    
    private void initializeSK24(Move move, SK24[] sk24Cards, IO24 io24MapGreen, int channel) {
        initializeSK24(move, sk24Cards);
        if (move.rm_gr != null) {
            initializeIO(move.rm_gr, io24MapGreen, channel);
        }
    }
    
    private void initializeSK24(Move move, SK24[] sk24Cards, IO64 io64MapGreen, int channel) {
        initializeSK24(move, sk24Cards);
        if (move.rm_gr != null) {
            initializeIO(move.rm_gr, io64MapGreen, channel);
        }
    }
    
    /**
     * When automatically allocating SK24 channels, use this method to leave blank channels when required
     * @param blankChannelsCount - the amount of blank channels to be left
     */
    private void initializeSK24BlankChannels(int blankChannelsCount) {
        for (int i = 0; i < blankChannelsCount; i++) {
            sk24Channel++;
            if (sk24Channel > 24) {
                sk24Channel -= 24;
                sk24Index++;
            }
        }
    }
    
    /**
     * Initializes SK24 channels for a LRT move (red, yellow, green, FFT)
     * @param move - the move to which the channels belong
     * @param sk24RedChannel - SK24 for the red lamp
     * @param redChannelNumber - the channel number for the red lamp (1 - 24)
     * @param sk24YellowChannel - SK24 for the yellow lamp
     * @param yellowChannelNumber - the channel number for the yellow lamp (1 - 24)
     * @param sk24GreenChannel - SK24 for the green lamp
     * @param greenChannelNumber - the channel number for the green lamp (1 - 24)
     * @param sk24FFT - SK24 for the FFT (Forward Flashing Triangle)
     * @param fftChannelNumber - the channel number for the FFT lamp (1 - 24)
     */
    private void initializeSK24(Move move, SK24 sk24RedChannel, int redChannelNumber, SK24 sk24YellowChannel, int yellowChannelNumber, SK24 sk24GreenChannel, int greenChannelNumber, SK24 sk24FFT, int fftChannelNumber) {
        if (!move.isLRT()) {
            throw new IllegalArgumentException("Move " + move.getName() + "isn't a LRT move (3 signal-heads + 1 FFT)");
        }
        if (!MathHelper.isInRange(redChannelNumber, 1, 24)
                || !MathHelper.isInRange(yellowChannelNumber, 1, 24)
                || !MathHelper.isInRange(greenChannelNumber, 1, 24)
                || !MathHelper.isInRange(fftChannelNumber, 1, 24)) {
            throw new IllegalArgumentException("Invalid channel received for an SK24. Only channels 1-24 are accepted");
        }
        new SchaltKanal(move, Move.lred  , hwRed200  , Hw.HF, sk24RedChannel   , redChannelNumber   , Hw.SK);
        new SchaltKanal(move, Move.lamber, hwAmber200, Hw.HF, sk24YellowChannel, yellowChannelNumber, Hw.SK);
        new SchaltKanal(move, Move.lgreen, hwGreen200, Hw.HF, sk24GreenChannel , greenChannelNumber , Hw.SK);
    }
    
    /**
     * Initializes SK24 channels for a traffic move (red, yellow, green)
     * @param move - the move to which the channels belong
     * @param sk24RedChannel - SK24 for the red lamp
     * @param redChannelNumber - the channel number for the red lamp (1 - 24)
     * @param sk24YellowChannel - SK24 for the yellow lamp
     * @param yellowChannelNumber - the channel number for the yellow lamp (1 - 24)
     * @param sk24GreenChannel - SK24 for the green lamp
     * @param greenChannelNumber - the channel number for the green lamp (1 - 24)
     */
    private void initializeSK24(Move move, SK24 sk24RedChannel, int redChannelNumber, SK24 sk24YellowChannel, int yellowChannelNumber, SK24 sk24GreenChannel, int greenChannelNumber) {
        if (!move.isTraffic()) {
            throw new IllegalArgumentException("Move " + move.getName() + "isn't a traffic move (3 signal-heads)");
        }
        if (!MathHelper.isInRange(redChannelNumber, 1, 24)
                || !MathHelper.isInRange(yellowChannelNumber, 1, 24)
                || !MathHelper.isInRange(greenChannelNumber, 1, 24)) {
            throw new IllegalArgumentException("Invalid channel received for an SK24. Only channels 1-24 are accepted");
        }
        new SchaltKanal(move, Move.lred  , hwRed200  , Hw.HF, sk24RedChannel   , redChannelNumber   , Hw.SK);
        new SchaltKanal(move, Move.lamber, hwAmber200, Hw.HF, sk24YellowChannel, yellowChannelNumber, Hw.SK);
        new SchaltKanal(move, Move.lgreen, hwGreen200, Hw.HF, sk24GreenChannel , greenChannelNumber , Hw.SK);
    }
    
    /**
     * Initializes SK24 channels for a pedestrian move (red, green)
     * @param move - the move to which the channels belong
     * @param sk24RedChannel - SK24 for the red lamp
     * @param redChannelNumber - the channel number for the red lamp (1 - 24)
     * @param sk24GreenChannel - SK24 for the green lamp
     * @param greenChannelNumber - the channel number for the green lamp (1 - 24)
     */
    private void initializeSK24(Move move, SK24 sk24RedChannel, int redChannelNumber, SK24 sk24GreenChannel, int greenChannelNumber) {
        if (!move.isPedestrian()) {
            throw new IllegalArgumentException("Move " + move.getName() + "isn't a pedestrian move (2 signal-heads)");
        }
        if (!MathHelper.isInRange(redChannelNumber, 1, 24)
                || !MathHelper.isInRange(greenChannelNumber, 1, 24)) {
            throw new IllegalArgumentException("Invalid channel received for an SK24. Only channels 1-24 are accepted");
        }
        new SchaltKanal(move, Move.lred  , hwRed200  , Hw.HF, sk24RedChannel   , redChannelNumber   , Hw.SK);
        new SchaltKanal(move, Move.lgreen, hwGreen200, Hw.HF, sk24GreenChannel , greenChannelNumber , Hw.SK);
    }
    
    /**
     * Initializes SK24 channels for a blinker move or a preemption triangle move (yellow)
     * @param move - the move to which the channels belong
     * @param sk24YellowChannel - SK24 for the yellow lamp
     * @param yellowChannelNumber - the channel number for the yellow lamp (1 - 24)
     */
    private void initializeSK24(Move move, SK24 sk24YellowChannel, int yellowChannelNumber) {
        if (!move.isBlinker() && !move.isPreemptionTriangle()) {
            throw new IllegalArgumentException("Move " + move.getName() + "isn't a blinker nor a preemption triangle (1 signal-heads)");
        }
        if (!MathHelper.isInRange(yellowChannelNumber, 1, 24)) {
            throw new IllegalArgumentException("Invalid channel received for an SK24. Only channels 1-24 are accepted");
        }
        
        if (move.isPreemptionTriangle()) {
            new SchaltKanal(move, Move.lamber, hwAmber200, Hw.HF, sk24YellowChannel, yellowChannelNumber, Hw.SK);
        } else {
            //all blinkers, including conditioned (mutne),
            //have to be excluded  from current monitoring
            //therefore the last parameter must be Hw.KK
            new SchaltKanal(move, Move.lamber_nuge, hwAmber200, Hw.HF, sk24YellowChannel, yellowChannelNumber, Hw.KK);
        }
    }
    
    private void initializeIO(Input input, IO24 io24, IO24 io24Map, int channel) {
        initializeIO(input         , io24   , channel);
        initializeIO(input.mapInput, io24Map, channel);
    }
    
    private void initializeIO(Input input, IO24 io24, IO24 io24Map, int channelInput, int mapChannel) {
        initializeIO(input         , io24   , channelInput);
        initializeIO(input.mapInput, io24Map, mapChannel);
    }
    
    private void initializeIO(Input input, IO24 io24, int inputChannel, IO64 io64Map, int mapChannel) {
        initializeIO(input         , io24   , inputChannel);
        initializeIO(input.mapInput, io64Map, mapChannel  );
    }
    
    private void initializeIO(TPDetector pushbutton, IO24 io24, IO24 io24Map, int inputChannel, int outputChannel) {
        initializeIO(pushbutton         , io24   , inputChannel );
        initializeIO(pushbutton.mapInput, io24Map, inputChannel);
        initializeIO(pushbutton.feedback, io24   , outputChannel);
    }
    
    private void initializeIO(TPDetector pushbutton, IO64 io64, int inputChannel, int mapChannel, int outputChannel) {
        initializeIO(pushbutton         , io64, inputChannel );
        initializeIO(pushbutton.mapInput, io64, mapChannel   );
        initializeIO(pushbutton.feedback, io64, outputChannel);
    }
    
    private void initializeIO(Input input, IO24 io24, int channel) {
        if (!MathHelper.isInRange(channel, 1, 16)) {
            throw new IllegalArgumentException("Invalid input channel received for an IO24. Only channels 1-16 are accepted (received channel #" + channel + " when assigning " + input.getName() +")");
        }
        
        new IoKanal(input, io24, channel);
    }
    
    private void initializeIO(Input input, IO64 io64, int channel) {
        if (!MathHelper.isInRange(channel, 1, 32)) {
            throw new IllegalArgumentException("Invalid input channel received for an IO64. Only channels 1-32 are accepted (received channel #" + channel + " when assigning " + input.getName() +")");
        }
        
        new IoKanal(input, io64, channel);   
    }
    
    private void initializeIO(Output output, IO24 io24, int channel) {
        if (!MathHelper.isInRange(channel, 17, 24)) {
            throw new IllegalArgumentException("Invalid output channel received for an IO24. Only channels 17-24 are accepted (received channel #" + channel + " when assigning " + output.getName() +")");
        }
        
        new IoKanal(output, io24, channel);
    }
    
    private void initializeIO(Output output, IO64 io64, int channel) {
        if (!MathHelper.isInRange(channel, 33, 64)) {
            throw new IllegalArgumentException("Invalid output channel received for an IO64. Only channels 33-64 are accepted (received channel #" + channel + " when assigning " + output.getName() +")");
        }
        
        new IoKanal(output, io64, channel);
    }

    protected void initialisiereSg(){
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].nodeInitializer.initialisiereSg();
        }

        Sg.endInit();
        System.out.println("signal groups are initialized");
    }

    protected void initialisiereZwz(){
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].nodeInitializer.initialisiereZwz();
        }
        //end of intergreen initialization
        System.out.println("intergreen times are initialized");
    }

    protected void initialisiereProgs(){
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].nodeInitializer.initialisiereProgs();
        }
        Vt.endInit();//end of program initialization
        System.out.println("programs are initialized");

    }

    protected void initialisierePhasen(){
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].nodeInitializer.initialisierePhasen();
            Var.controller.nodes[j].prevStage = Var.controller.nodes[j].MainPhase[0];
        }

        System.out.println("phases/interstages are initialized");
    }

    protected void initialisiereZentrale(){
        int central = 0;
        central = DVI35Modul.initDvi35();
        if (central == 0) {
            if (Var.controller.central != null) {
                System.out.println(Var.controller.central.name + " Module initialized [Version + " + Var.controller.central.version + "]");
            } else {
                System.out.println("DVI35 initialized!!");
            }
        }
        System.out.println(" ready!");
    }

    protected void initialisiereOEV(){
        //Public transport
        System.out.println("pt-modul initialized");
    }

    protected void initialisiereParameter()  {

        //all the following variables can be seen with actros.access
//        DebugVar.registerVarClassName(Tk1.class.getName());
//        DebugVar.setTeilKnoten(Var.tk1);
//        DebugVar.init("TK1_Interstage");
        DebugVar.registerVarClassName(iCentral.class.getName());
        DebugVar.setTeilKnoten(Var.tk1); 
        DebugVar.init("CC_isConnected");
        DebugVar.registerVarClassName(iGreenWave.class.getName());
        DebugVar.setTeilKnoten(Var.tk1); 
        DebugVar.init("GW_Offset");
        DebugVar.init("GW_Fail"  );
        DebugVar.init("GW_Active");
        DebugVar.registerVarClassName(GreenWaveHW.class.getName());
        DebugVar.setTeilKnoten(Var.tk1);
        DebugVar.init("isProgramFault");
        DebugVar.init("isULinFault");
        DebugVar.init("isSyFault");
        DebugVar.init("syOffsetCounter");
        DebugVar.init("syDuration");    
        if (Var.controller.isUseSIT()) {
            DebugVar.registerVarClassName(Var.class.getName());
            DebugVar.setTeilKnoten(Var.tk1); 
//            DebugVar.init("SIT_SHOW_20L1");
        }
        DebugVar.endInit();
        System.out.println("DebugVar are initialized");

        Var.controller.initializeParameters();
        Var.controller.signs.initializeParameters();
        Var.controller.parameters.initializeParameters();
        if (Var.controller.isPoliceDoor()) {
            Var.controller.policePanel.initializeParameters();
        }
        if (Var.controller.itcProtocol != null) {
            Var.controller.itcProtocol.initializeParameters();
        }
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            if (Var.controller.nodes[j].scheduler != null) {
                Var.controller.nodes[j].scheduler.initializeParameters();
            }
        }

        VtVar.endInit();
        System.out.println("Parameters are initialized");
    }

    protected void initialisiereUhr(){
        for (int j = 0; j < Var.controller.nodes.length; j++) {
            Var.controller.nodes[j].nodeInitializer.initialisiereUhr();
        }

        System.out.println("time table is initialised");
    }

    public String getVersion()
    {
        return (": " + anlagenName + " (revision: " + revision + "), version:" + version + ", of:" + date + " Editor:"+ editor+ ".");
    }
}