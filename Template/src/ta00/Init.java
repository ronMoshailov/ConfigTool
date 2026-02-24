package ta172;

import det.*;     
import enp.utilities.VtRam;
import enums.AppType;
import hw.*;                              
import hw.hwlmp.*;                        
import hw.nkk.*;                          
import hw.tk.*;                           
import sbh.vtbib.dvi35.DVI35Modul;        
import sg.*;                               
import vt.*;                              
import vtvar.*;  
import special.Controller;
import special.Move;
import special.Signs;
import special.PoliceKeyboard;
import special.SpecialInOuts;
import ta172.GreenWave;
import ta172.Init;
import ta172.InitParametersMaatz;
import ta172.ParSets;
import ta172.ParametersHaifa;
import ta172.ParametersJerusalem;
import ta172.ParametersTelAviv;
import ta172.Tk1;
import ta172.Var;

public class Init implements enp.Initialisierung {

    // write settings here

	/*******************************************************************************************
	 * Versions:
	 * V1 - Basic Version - Last Update: 06/11/2007
	 *******************************************************************************************/

	//Intergreentime
	public static void main(String[] args) {
		System.out.println(anlagenName + " " + tk1Name + " " + version + " " + versions[versions.length - 1]);
//		System.out.println(anlagenName + tk1Name + version + date + editor);

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
		init1.initialisiereParameter(); //parameters
		init1.initialisiereUhr(); //time table
		init1.initialisiereHW(); //hardware (cards)

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
		//				                                  name   | hardware | dx?  | 1st cycle
		//                                                       |          |      | extension
		// TODO: update according to the number of nodes
		Var.controller = new Controller(Var.tk1 = new Tk1(tk1Name, Var.hwTk1, false,        2));
		// TODO: update according to application type
		Var.controller.SetAppType(AppType.TelAviv);   	// sets the type of application (Haifa/Netivei-Israel/Jerusalem....)
		Var.controller.SetMaintenance();				// marks that the controller has a "Map Only" option
		Var.controller.SetPoliceDoor();					// marks that the controller has a police door
		Var.controller.SetNonFixedPoliceStructure(1);	// sets the default structure to be used in non-fixed police. NOTE: values must range from 1 to maximal available structure number. if last active structure is to be used, use the value "0"!
		Var.controller.SetSynopticMap();				// marks that the controller has a synoptic map
		//		Var.controller.SetPreemption();					// marks that the controller implements preemption for public transport

		System.out.println("...nach tk");
	}

	protected void initialisiereDet(){
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisiereDet();
		}

		if (Var.controller.isPreemption()) {
			// TODO: update according to amount of nodes
			Var.tk1.preemption.InitializeDPT();
		}

		//output for Signs
		Signs.initSigns();

		//inputs for Police Keyboards
		if (Var.controller.isPoliceDoor()) {
			PoliceKeyboard.initPoliceKeyboard();
		}
		
		SpecialInOuts.initResetOutput(Var.tk1);

		//inputs for green wave
		if (Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv()) {
			Var.controller.SetClockSync();	// should be called only when greenwave by clock synchronization is required
		}
		
		GreenWave.initGreenWaveSlave(Var.tk1, GreenWave.inactive,					//whether green wave is used
				GreenWave.REGULAR,	            //green wave type: REGULAR or BREATHING
				GreenWave.ULin_inactive,			//whether ULin is used. if used: ULin_active. otherwise: ULin_inactive
				GreenWave.ULout_inactive,			//whether ULout is used. if used: ULout_active. otherwise: ULout_inactive
				1,								//number of the first green wave program (for Maatz only)
				GreenWave.GWdelta_inactive, 0);	//whether delta is used. if used: GWdelta_active. otherwise: GWdelta_inactive. The number is the delta.

		//outputs for green wave master
		GreenWave.initGreenWaveMaster(Var.tk1, GreenWave.inactive,			//whether green wave is used                                             
				GreenWave.REGULAR,			//green wave type: REGULAR or BREATHING                                  
				GreenWave.ULin_inactive,	//whether ULin is used. if used: ULin_active. otherwise: ULin_inactive   
				GreenWave.ULout_inactive,	//whether ULout is used. if used: ULout_active. otherwise: ULout_inactive
				0, 2,						//pulseSec, pulseLen
				1, 5);						//number of the first green wave program. quantity of green wave programs (for Maatz only)
		// input for checking ups power in Maatz applications
		SpecialInOuts.initUpsInput(Var.tk1, SpecialInOuts.active);

		// pulse for checking preemption application
		if (Var.controller.isSynopticMap()) {
			//                                               pulse      sec   len
			SpecialInOuts.initCheckPuls(Var.tk1, SpecialInOuts.inactive,    8 ,   1 );
		}

		//end of input/output initialization
		Det.endInit();

		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].UpdateDetectors();
			Var.controller.nodes[j].UpdatePushButtons();
			Var.controller.nodes[j].SetTK();
		}
		System.out.println("input/output initialized");
	}

	protected void initialisiereHW(){
		//initialize device
        int cardsIndex = 1;

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
		HwLmpTyp hwRed200   = new LmpMin230V(Hw.ROT);
		HwLmpTyp hwAmber200 = new LmpMin230V(Hw.GELB);
		HwLmpTyp hwGreen200 = new LmpMin230V(Hw.GRUEN);

		//NKK
		//         new Nkk230V50Hz(); - old Backplane
		new NkkFusion230V50Hz(); // for new Alpha Backplane without NKK Card

		//switch cards
        // write sk cards here

		//input/output-cards
		IO24 io1  = new IO24(cardsIndex++);
		IO64 io64 = null;
		if (Var.controller.isPoliceDoor() || Var.controller.isSynopticMap()) {
			io64 = new IO64(cardsIndex++);
		}

		//switch channels
        // write sk channels here

		
		//all blinkers, including conditioned (mutne),                        
		//have to be excluded  from current monitoring                        
		//therefore the last parameter must be Hw.KK
// 		new SchaltKanal(Var.tk1.Bb     , Move.lamber_nuge, hwAmber200, Hw.HF, sk2,11, Hw.KK);

		//Can be Compact only if:
		// 1. A Maatz application. In Haifa ONLY ALPHA!
		// 2. Has only 1 SK24 card, and only 1 IO24 card.
		// 3. There are maximum 8 inputs and 4 outputs on the IO24 card.
		// 4. Lamps in the junction are LEDS 
		//if Compact: Input channels: 3, 4, 7, 8, 13 - 16. Output channels: 21 - 24

		//		 I/O24 - Input channels 1-16
// 		new IoKanal (Var.tk1.d_3                , io1,  1);
// 		new IoKanal (Var.tk1.e_3                , io1,  2);
// 		new IoKanal (Var.tk1.d_6                , io1,  3);
// 		new IoKanal (Var.tk1.e_6                , io1,  4);

		new IoKanal (SpecialInOuts.ups_door_open, io1, 14);
		new IoKanal (SpecialInOuts.ups_failure  , io1, 15);
		new IoKanal (SpecialInOuts.ups_power_off, io1, 16);	
	

		//		 I/O24 - Output channels 17-24
		new IoKanal (Signs.signs_on             , io1, 23);
		new IoKanal	(SpecialInOuts.reset        , io1, 24);

		if (io64 != null) {
			//       I/O64 - Input channels 1-32
			//		 constant inputs for all applications
			new IoKanal (PoliceKeyboard.Flash       , io64,  1); // BLINK button
			new IoKanal (PoliceKeyboard.Dark        , io64,  2); // DARK button
			new IoKanal (PoliceKeyboard.Automat     , io64,  3); // AUTO button
			new IoKanal (PoliceKeyboard.Hand        , io64,  4); // POLICE button
			new IoKanal (PoliceKeyboard.FS          , io64,  5); // ADVANCE button
			//      new IoKanal (PoliceKeyboard.Red         , io64,  6); // RED button
			new IoKanal (PoliceKeyboard.Door_N_O    , io64,  7);
			new IoKanal (PoliceKeyboard.Door_N_C    , io64,  8);
			//      new IoKanal (Var.Spare                  , io64,  9);
			//      new IoKanal (Var.Spare                  , io64, 10);
			//		 inputs for current application
// 			new IoKanal (Var.tk1.d_3.mapInput		, io64, 11);
// 			new IoKanal (Var.tk1.e_3.mapInput		, io64, 12);
// 			new IoKanal (Var.tk1.d_6.mapInput		, io64, 13);
// 			new IoKanal (Var.tk1.e_6.mapInput		, io64, 14);
			
//			new IoKanal (Var.tk1.tp_c.mapInput      , io64, 19);
//			new IoKanal (Var.tk1.tp_d.mapInput      , io64, 20);
//			new IoKanal (Var.tk1.tp_g.mapInput      , io64, 21);
//			new IoKanal (Var.tk1.tp_h.mapInput      , io64, 22);
			

//			new IoKanal (Var.tk1.tp_h               , io64, 29);
//			new IoKanal (Var.tk1.tp_g               , io64, 30);
//			new IoKanal (Var.tk1.tp_d               , io64, 31);
//			new IoKanal (Var.tk1.tp_c               , io64, 32);
			

			//       I/O64 - Output channels 33-64
			//		 constant outputs for all applications
			new IoKanal (PoliceKeyboard.Led_Flash   , io64, 33); // BLINK led
			new IoKanal (PoliceKeyboard.Led_Dark    , io64, 34); // DARK led
			new IoKanal (PoliceKeyboard.Led_Automat , io64, 35); // AUTO led
			new IoKanal (PoliceKeyboard.Led_Hand    , io64, 36); // POLICE led
			new IoKanal (PoliceKeyboard.FS_ready    , io64, 37); // ADVANCE led
			new IoKanal (Signs.cc_connect           , io64, 38); // Control Center Connection
//			new IoKanal (SpecialInOuts.checkPuls    , io64, 39); // pulse for checking preemption application
			new IoKanal (GreenWave.Led_OffsetSY     , io64, 40); // green wave pulse SY after offset
			new IoKanal (GreenWave.Led_GWfail       , io64, 41); // green wave error led (SY or gw_prog)
			new IoKanal (GreenWave.Led_GWactive     , io64, 42); // green wave active

// 			new IoKanal (Var.tk1.k1 .rm_gr			, io64, 43);
// 			new IoKanal (Var.tk1.k3 .rm_gr			, io64, 44);
// 			new IoKanal (Var.tk1.k5 .rm_gr			, io64, 45);
// 			new IoKanal (Var.tk1.k6 .rm_gr			, io64, 46);
// 			new IoKanal (Var.tk1.k7 .rm_gr			, io64, 47);
// 			new IoKanal (Var.tk1.k8 .rm_gr			, io64, 48);
// 			new IoKanal (Var.tk1.pa .rm_gr			, io64, 49);
// 			new IoKanal (Var.tk1.pb .rm_gr			, io64, 50);
// 			new IoKanal (Var.tk1.pc .rm_gr			, io64, 51);
// 			new IoKanal (Var.tk1.pd .rm_gr			, io64, 52);
// 			new IoKanal (Var.tk1.pe .rm_gr			, io64, 53);
// 			new IoKanal (Var.tk1.pf .rm_gr			, io64, 54);
// 			new IoKanal (Var.tk1.pg .rm_gr			, io64, 55);
// 			new IoKanal (Var.tk1.ph .rm_gr			, io64, 56);


//			new IoKanal (Var.tk1.tp_h.feedback      , io64, 61);
//			new IoKanal (Var.tk1.tp_g.feedback      , io64, 62);
//			new IoKanal (Var.tk1.tp_d.feedback      , io64, 63);
//			new IoKanal (Var.tk1.tp_c.feedback      , io64, 64);
		}
			
		System.out.println("java initialization of the device complete!");

		Var.device.startAnlage();
	}

	protected void initialisiereSg(){
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisiereSg();
		}

		Sg.endInit();

		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].UpdateMovesArraysAndSafety();
		}

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
		}

		System.out.println("phases/interstages are initialized");
	}

	protected void initialisiereZentrale(){
		System.out.print("Initialize control center .....");
		//Zentrale initialisieren

		if(Var.controller.isAppHaifa() || Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv())
		{
			int central = 0;
			central = DVI35Modul.initDvi35();
			if (central == 0) {
				System.out.println("DVI35 initialized!!");
			}
			System.out.println(" ready!");
		}
		else
		{
//			Zentrale.initOcit();
		}

		System.out.println(" ready!");
	}

	protected void initialisiereOEV(){
		//Public transport
		System.out.println("pt-modul initialized");
	}

	protected void initialisiereParameter()  {

		//all the following variables can be seen with actros.access
		DebugVar.registerVarClassName(Tk1.class.getName());
		DebugVar.setTeilKnoten(Var.tk1);
//		DebugVar.init("M");
//		DebugVar.init("N");
//		DebugVar.init("X");
//		DebugVar.init("syOffsetCounter");
//		DebugVar.init("previousSy");
//		DebugVar.init("syDuration");
//		DebugVar.init("waitForSy");
//		DebugVar.init("isSYfromCentralOffset");

		// traffic plan variables
		DebugVar.endInit();
		System.out.println("Debug Var are initialized");

		/******************************** Global Parameters *********************************/

		ParSets.signsOffsetInOn  = VarInt.init(Var.tk1, "Signs offset in ON" , 30 , true, true, true);
		ParSets.signsOffsetInOff = VarInt.init(Var.tk1, "Signs offset in OFF", 30 , true, true, true);

		/********************** Parameters referring to the programs ************************/

		
		if (Var.controller.isAppHaifa()) {
			Var.controller.dvi35Parameters = new ParametersHaifa();
			Var.controller.dvi35Parameters.SetParameters();
		} else if (Var.controller.isAppJerusalem()) {
			Var.controller.dvi35Parameters = new ParametersJerusalem();
			Var.controller.dvi35Parameters.SetParameters();
		} else if (Var.controller.isAppTelAviv()) {
			Var.controller.dvi35Parameters = new ParametersTelAviv();
			Var.controller.dvi35Parameters.SetParameters();
		} else {
			InitParametersMaatz.SetParameters();
		}

		/****************** For the Police Program ***********************/
		if (Var.STOP_PUNKTE.length != 0)
		{
			ParSets.r1 = VtVarStrukt.init(Var.tk1, "Police_Stop_Points", Var.STOP_PUNKTE, true, true, true);
			ParSets.r2 = VtVarStrukt.init(Var.tk1, "Police_Free_Points", Var.FREE_PUNKTE, true, true, true);
		}
		/*****************************************************************/

		VtVar.endInit();

		System.out.println("");
	}

	protected void initialisiereUhr(){
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisiereUhr();
		}

		System.out.println("time table is initialised");
	}

	public String getVersion()
	{
		return (anlagenName + ", version: " + version + ", of: " + versions[versions.length - 1] + ".");
//		return (": " + anlagenName + ", version:" + version + ", of:" + date + " Editor:"+ editor+ ".");
	}
}