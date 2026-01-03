package ta118;

import det.*;
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
import tk.GreenWave;
import tk.ParametersMaatz;
import tk.ParSets;
import tk.ParametersHaifa;
import tk.ParametersJerusalem;
import tk.Var;

public class Init implements enp.Initialisierung {

	public static String anlagenName = "Tel-Aviv Junc. 118";
	public static String tk1Name     = "Yitzhak Sadeh - Tversky";
	public static String version     = "V1.4.1e G5tf.7";
//	public static String date        = " 04/11/2024";
//	public static String editor      = " Sergei V.";
	public static String[] versions = {
		"26/11/2023 - Basic - Kmahya",
		"04/11/2024 - UPDATE - Sergei V.",
		"05/01/2025 - Hesder 250 - Kmahya",
		"11/06/2025 - Hesder 282 - Shachar G.",
		"13/08/2025 - Hesder 278 - Ron M."
	};

	public static void main(String[] args) {
		System.out.println(anlagenName + " " + tk1Name + " " + version + " " + versions[versions.length - 1]);
//		System.out.println(anlagenName + tk1Name + version + date + editor);

		Init init1 = new Init();
		enp.Actros.registerInitialisierung(init1);
		init1.initialisiereTk(); 		// hardware and software intersection
		init1.initialisiereSg(); 		// signal-groups
		init1.initialisiereZwz(); 		// intergreen matrix
		init1.initialisiereDet();  		// detectors
		init1.initialisiereProgs(); 	// programs
		init1.initialisierePhasen(); 	// stages and interstages
		init1.initialisiereZentrale(); 	// central-command
		init1.initialisiereOEV(); 		// public transport
		init1.initialisiereParameter(); // parameters
		init1.initialisiereUhr(); 		// time table
		init1.initialisiereHW(); 		// hardware (cards)
	}

	protected void initialisiereTk() {
		System.out.println();
		System.out.println("Initialisiere Hardware-Knoten...");
		//Hardware-Knoten initialisieren | EN: Initialize Nodes Hardware
		//TODO: update according to the required number of hardwares 
		Var.hwTk1 = new HwTeilknoten230V("HWKnoten1");
//		Var.hwTk2 = new HwTeilknoten230V("HWKnoten2");
//		Var.hwTk3 = new HwTeilknoten230V("HWKnoten3");
		
		System.out.println("Initialisiere Software-Teilknoten...");
		System.out.println("...vor tk");
		
		//Software-Teilknoten initialisieren | EN: Initialize Subnodes Software
		//TODO: update according to the number of nodes
		//				                                  name   | hardware  |  dx?  | 1st cycle
		//                                                       |           |       | extension
		Var.controller = new Controller(Var.tk1 = new Tk1(tk1Name, Var.hwTk1 , false ,        2 ));
		
		//TODO: update according to application type
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
			Var.controller.SetClockSync();		// should be called only when greenwave by clock synchronization is required
		}
		
		GreenWave.initGreenWaveSlave(Var.tk1,
			GreenWave.inactive,				  // whether green wave is used
			GreenWave.REGULAR,	              // green wave type: REGULAR or BREATHING
			GreenWave.ULin_inactive,		  // whether ULin is used. if used: ULin_active. otherwise: ULin_inactive
			GreenWave.ULout_inactive,		  // whether ULout is used. if used: ULout_active. otherwise: ULout_inactive
			1,								  // number of the first green wave program (for Maatz only)
			GreenWave.GWdelta_inactive, 0);	  // whether delta is used. if used: GWdelta_active. otherwise: GWdelta_inactive. The number is the delta.

		//outputs for green wave master
		GreenWave.initGreenWaveMaster(Var.tk1, 
			GreenWave.inactive,				  // whether green wave is used                                             
			GreenWave.REGULAR,				  // green wave type: REGULAR or BREATHING                                  
			GreenWave.ULin_inactive,		  // whether ULin is used. if used: ULin_active. otherwise: ULin_inactive   
			GreenWave.ULout_inactive,		  // whether ULout is used. if used: ULout_active. otherwise: ULout_inactive
			0, 2,							  // pulseSec, pulseLen
			1, 5);							  // number of the first green wave program. quantity of green wave programs (for Maatz only)
		
		//input for checking ups power in Maatz applications
		SpecialInOuts.initUpsInput(Var.tk1, SpecialInOuts.active);

		//pulse for checking preemption application
		if (Var.controller.isSynopticMap()) {
			//                                                  pulse  | sec | len
			SpecialInOuts.initCheckPuls(Var.tk1, SpecialInOuts.inactive,  8  ,  1 );
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
	
	protected void initialisiereHW() {
		int cardsIndex = 1;
		
		//initialize device
		//TODO: update according to amount of nodes
		Var.device = new Anlage(Var.tk1/*, Var.tk2*/);
		Var.device.setName(anlagenName);
		Var.device.setZyklZeitHalbeSek(); //set internal cycle to 500 ms

		//changes behavior of safety to needed behavior according to Israel
		Anlage.setPruefungen(Anlage.MIN_PRUEF_ABSCH | Anlage.ZWZ_PRUEF_ABSCH);

		//hardware lamp types 	 /* 9w leds */
		HwLmpTyp hwRed200   = new LmpMin230V(Hw.ROT);
		HwLmpTyp hwAmber200 = new LmpMin230V(Hw.GELB);
		HwLmpTyp hwGreen200 = new LmpMin230V(Hw.GRUEN);

		//NKK
//      new Nkk230V50Hz(); - old Backplane
		new NkkFusion230V50Hz(); // for new Alpha Backplane without NKK Card

		//switch cards
		SK24 sk1 = new SK24(cardsIndex++, (HwTeilknoten)Var.hwTk1);
		SK24 sk2 = new SK24(cardsIndex++, (HwTeilknoten)Var.hwTk1);
        // write sk cards here

		//input/output cards
		IO24 io1  = new IO24(cardsIndex++);
//		IO24 io2  = new IO24(cardsIndex++);
		
		IO64 io64 = null;
		if (Var.controller.isPoliceDoor() || Var.controller.isSynopticMap()) {
			io64 = new IO64(cardsIndex++);
		}

		//switch channels
		//TODO: update according to amount of moves and switch cards
		new SchaltKanal(Var.tk1.k1     , Move.lred       , hwRed200  , Hw.HF, sk1, 1, Hw.SK);
		new SchaltKanal(Var.tk1.k1     , Move.lamber     , hwAmber200, Hw.HF, sk1, 2, Hw.SK);
		new SchaltKanal(Var.tk1.k1     , Move.lgreen     , hwGreen200, Hw.HF, sk1, 3, Hw.SK);
		new SchaltKanal(Var.tk1.k2     , Move.lred       , hwRed200  , Hw.HF, sk1, 4, Hw.SK);
		new SchaltKanal(Var.tk1.k2     , Move.lamber     , hwAmber200, Hw.HF, sk1, 5, Hw.SK);
		new SchaltKanal(Var.tk1.k2     , Move.lgreen     , hwGreen200, Hw.HF, sk1, 6, Hw.SK);
		new SchaltKanal(Var.tk1.k3     , Move.lred       , hwRed200  , Hw.HF, sk1, 7, Hw.SK);
		new SchaltKanal(Var.tk1.k3     , Move.lamber     , hwAmber200, Hw.HF, sk1, 8, Hw.SK);
		new SchaltKanal(Var.tk1.k3     , Move.lgreen     , hwGreen200, Hw.HF, sk1, 9, Hw.SK);
		new SchaltKanal(Var.tk1.k4     , Move.lred       , hwRed200  , Hw.HF, sk1,10, Hw.SK);
		new SchaltKanal(Var.tk1.k4     , Move.lamber     , hwAmber200, Hw.HF, sk1,11, Hw.SK);
		new SchaltKanal(Var.tk1.k4     , Move.lgreen     , hwGreen200, Hw.HF, sk1,12, Hw.SK);
		new SchaltKanal(Var.tk1.k5     , Move.lred       , hwRed200  , Hw.HF, sk1,13, Hw.SK);
		new SchaltKanal(Var.tk1.k5     , Move.lamber     , hwAmber200, Hw.HF, sk1,14, Hw.SK);
		new SchaltKanal(Var.tk1.k5     , Move.lgreen     , hwGreen200, Hw.HF, sk1,15, Hw.SK);
//		new SchaltKanal(Var.tk1.k6     , Move.lred       , hwRed200  , Hw.HF, sk1,16, Hw.SK);
//		new SchaltKanal(Var.tk1.k6     , Move.lamber     , hwAmber200, Hw.HF, sk1,17, Hw.SK);
//		new SchaltKanal(Var.tk1.k6     , Move.lgreen     , hwGreen200, Hw.HF, sk1,18, Hw.SK);
		new SchaltKanal(Var.tk1.pa     , Move.lred       , hwRed200  , Hw.HF, sk1,19, Hw.SK);
		new SchaltKanal(Var.tk1.pa     , Move.lgreen     , hwGreen200, Hw.HF, sk1,20, Hw.SK);
//		new SchaltKanal(Var.tk1.pb     , Move.lred       , hwRed200  , Hw.HF, sk1,21, Hw.SK);
//		new SchaltKanal(Var.tk1.pb     , Move.lgreen     , hwGreen200, Hw.HF, sk1,22, Hw.SK);
		new SchaltKanal(Var.tk1.pc     , Move.lred       , hwRed200  , Hw.HF, sk1,23, Hw.SK);
		new SchaltKanal(Var.tk1.pc     , Move.lgreen     , hwGreen200, Hw.HF, sk1,24, Hw.SK);
//		new SchaltKanal(Var.tk1.pd     , Move.lred       , hwRed200  , Hw.HF, sk2, 1, Hw.SK);
//		new SchaltKanal(Var.tk1.pd     , Move.lgreen     , hwGreen200, Hw.HF, sk2, 2, Hw.SK);
//		new SchaltKanal(Var.tk1.pe     , Move.lred       , hwRed200  , Hw.HF, sk2, 3, Hw.SK);
//		new SchaltKanal(Var.tk1.pe     , Move.lgreen     , hwGreen200, Hw.HF, sk2, 4, Hw.SK);
		new SchaltKanal(Var.tk1.pf     , Move.lred       , hwRed200  , Hw.HF, sk2, 5, Hw.SK);
		new SchaltKanal(Var.tk1.pf     , Move.lgreen     , hwGreen200, Hw.HF, sk2, 6, Hw.SK);
//		new SchaltKanal(Var.tk1.pg     , Move.lred       , hwRed200  , Hw.HF, sk2, 7, Hw.SK);
//		new SchaltKanal(Var.tk1.pg     , Move.lgreen     , hwGreen200, Hw.HF, sk2, 8, Hw.SK);
//		new SchaltKanal(Var.tk1.Bb     , Move.lamber     , hwAmber200, Hw.HF, sk2, 9, Hw.SK);
//		new SchaltKanal(Var.tk1.Bc     , Move.lamber     , hwAmber200, Hw.HF, sk2,10, Hw.SK);
//		new SchaltKanal(Var.tk1.Be     , Move.lamber     , hwAmber200, Hw.HF, sk2,11, Hw.SK);
//		new SchaltKanal(Var.tk1.Bg     , Move.lamber     , hwAmber200, Hw.HF, sk2,12, Hw.SK);
		new SchaltKanal(Var.tk1.Bf     , Move.lamber     , hwAmber200, Hw.HF, sk2,13, Hw.SK);

		//all blinkers, including conditioned (mutne), have to be excluded from current monitoring
		//therefore the last parameter must be Hw.KK
		
		//Can be Compact only if:
		// 1. A Maatz application. In Haifa ONLY ALPHA!
		// 2. Has only 1 SK24 card, and only 1 IO24 card.
		// 3. There are maximum 8 inputs and 4 outputs on the IO24 card.
		// 4. Lamps in the junction are LEDS 
		//if Compact: Input channels: 3, 4, 7, 8, 13 - 16. Output channels: 21 - 24

		//I/O24 - Input channels 1-16
		//TODO: 
//		new IoKanal (Var.tk1.e1                 , io1,  1);
//		new IoKanal (Var.tk1.d2                 , io1,  2);
//		new IoKanal (Var.tk1.e2                 , io1,  3);
//		new IoKanal (Var.tk1.e5                 , io1,  4);
//		new IoKanal (Var.tk1.d6                 , io1,  5);
//		new IoKanal (Var.tk1.e6                 , io1,  6);

		new IoKanal (SpecialInOuts.ups_door_open, io1, 14);
		new IoKanal (SpecialInOuts.ups_failure  , io1, 15);
		new IoKanal (SpecialInOuts.ups_power_off, io1, 16);

		//I/O24 - Output channels 17-24
		new IoKanal (Signs.signs_on             , io1, 23);
		new IoKanal	(SpecialInOuts.reset        , io1, 24);

		if (io64 != null) {
			//I/O64 - Input channels 1-32
			//constant inputs for all applications
			new IoKanal (PoliceKeyboard.Flash       ,io64,  1); // BLINK button
			new IoKanal (PoliceKeyboard.Dark        ,io64,  2); // DARK button
			new IoKanal (PoliceKeyboard.Automat     ,io64,  3); // AUTO button
			new IoKanal (PoliceKeyboard.Hand        ,io64,  4); // POLICE button
			new IoKanal (PoliceKeyboard.FS          ,io64,  5); // ADVANCE button
//			new IoKanal (PoliceKeyboard.Red         ,io64,  6); // RED button
			new IoKanal (PoliceKeyboard.Door_N_O    ,io64,  7);
			new IoKanal (PoliceKeyboard.Door_N_C    ,io64,  8);
//			new IoKanal (Var.Spare                  ,io64,  9);
//			new IoKanal (Var.Spare                  ,io64, 10);
			
			//inputs for current application
			//TODO: update according to amount of Detectors
//			new IoKanal (Var.tk1.e1  .mapInput      ,io64, 11);
//			new IoKanal (Var.tk1.d2  .mapInput      ,io64, 12);
//			new IoKanal (Var.tk1.e2  .mapInput      ,io64, 13);
//			new IoKanal (Var.tk1.e5  .mapInput      ,io64, 14);
//			new IoKanal (Var.tk1.d6  .mapInput      ,io64, 15);
//			new IoKanal (Var.tk1.e6  .mapInput      ,io64, 16);
			
//			new IoKanal (Var.tk1.tp_a   .mapInput   ,io64, 13);
//			new IoKanal (Var.tk1.tp_a_b .mapInput   ,io64, 14);

//			new IoKanal (Var.tk1.tp_a_b			    ,io64, 31);			
//			new IoKanal (Var.tk1.tp_a			    ,io64, 32);

			//I/O64 - Output channels 33-64
			//constant outputs for all applications
			new IoKanal (PoliceKeyboard.Led_Flash   ,io64, 33); // BLINK led
			new IoKanal (PoliceKeyboard.Led_Dark    ,io64, 34); // DARK led
			new IoKanal (PoliceKeyboard.Led_Automat ,io64, 35); // AUTO led
			new IoKanal (PoliceKeyboard.Led_Hand    ,io64, 36); // POLICE led
			new IoKanal (PoliceKeyboard.FS_ready    ,io64, 37); // ADVANCE led
			new IoKanal (Signs.cc_connect           ,io64, 38); // Control Center Connection
//			new IoKanal (SpecialInOuts.checkPuls    ,io64, 39); // pulse for checking preemption application
			new IoKanal (GreenWave.Led_OffsetSY     ,io64, 40); // green wave pulse SY after offset
			new IoKanal (GreenWave.Led_GWfail       ,io64, 41); // green wave error led (SY or gw_prog)
			new IoKanal (GreenWave.Led_GWactive     ,io64, 42); // green wave active
			
			//outputs for current application
			//TODO: update according to amount of Moves
			new IoKanal (Var.tk1.k1 .rm_gr			,io64, 43);
			new IoKanal (Var.tk1.k2 .rm_gr			,io64, 44);
			new IoKanal (Var.tk1.k3 .rm_gr			,io64, 45);
			new IoKanal (Var.tk1.k4 .rm_gr			,io64, 46);
			new IoKanal (Var.tk1.k5 .rm_gr			,io64, 47);
//			new IoKanal (Var.tk1.k6 .rm_gr			,io64, 48);
			
			new IoKanal (Var.tk1.pa .rm_gr			,io64, 49);
//			new IoKanal (Var.tk1.pb .rm_gr			,io64, 50);
			new IoKanal (Var.tk1.pc .rm_gr			,io64, 51);
//			new IoKanal (Var.tk1.pd .rm_gr			,io64, 52);
//			new IoKanal (Var.tk1.pe .rm_gr			,io64, 53);
			new IoKanal (Var.tk1.pf .rm_gr			,io64, 54);
//			new IoKanal (Var.tk1.pg .rm_gr			,io64, 55);
			
//			new IoKanal (Var.tk1.tp_b	.feedback	,io64, 63);			
//			new IoKanal (Var.tk1.tp_a_b	.feedback	,io64, 63);			
//			new IoKanal (Var.tk1.tp_a	.feedback	,io64, 64);
		}

		System.out.println("java initialization of the device complete!");

		Var.device.startAnlage();
	}

	protected void initialisiereSg() {
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisiereSg();
		}

		Sg.endInit();

		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].UpdateMovesArraysAndSafety();
		}
		System.out.println("signal groups are initialized");
	}

	protected void initialisiereZwz() {
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisiereZwz();
		}
		//end of intergreen initialization
		System.out.println("intergreen times are initialized");
	}

	protected void initialisiereProgs() {
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisiereProgs();
		}
		Vt.endInit();
		//end of program initialization
		System.out.println("programs are initialized");
	}

	protected void initialisierePhasen() {
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisierePhasen();
		}
		System.out.println("phases/interstages are initialized");
	}

	protected void initialisiereZentrale() {
		System.out.print("Initialize control center .....");
		//Zentrale initialisieren

		if(Var.controller.isAppHaifa() || Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv()) {
			int central = 0;
			central = DVI35Modul.initDvi35();
			if (central == 0) {
				System.out.println("DVI35 initialized!!");
			}
			System.out.println(" ready!");
		} else {
//			Zentrale.initOcit();
		}
		System.out.println(" ready!");
	}

	protected void initialisiereOEV() {
		//Public transport
		System.out.println("pt-modul initialized");
	}

	protected void initialisiereParameter() {
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

		//traffic plan variables
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
			ParametersMaatz.SetParameters();
		}

		/****************** For the Police Program ***********************/
		if (Var.STOP_PUNKTE.length != 0) {
			ParSets.r1 = VtVarStrukt.init(Var.tk1, "Police_Stop_Points", Var.STOP_PUNKTE, true, true, true);
			ParSets.r2 = VtVarStrukt.init(Var.tk1, "Police_Free_Points", Var.FREE_PUNKTE, true, true, true);
		}
		/*****************************************************************/

		VtVar.endInit();

		System.out.println("");
	}

	protected void initialisiereUhr() {
		for (int j = 0; j < Var.controller.nodes.length; j++) {
			Var.controller.nodes[j].nodeInitializer.initialisiereUhr();
		}
		System.out.println("time table is initialised");
	}

	public String getVersion() {
		return (anlagenName + ", version: " + version + ", of: " + versions[versions.length - 1] + ".");
	}
//	public String getVersion() {
//		return (": " + anlagenName + ", version: " + version + ", of: " + date + ", Editor: "+ editor+ ".");
//	}
}