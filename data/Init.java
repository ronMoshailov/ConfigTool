package m0075;

import det.*;
import hw.*;
import hw.hwlmp.*;
import hw.nkk.*;
import hw.tk.*;
import sbh.vtbib.dvi35.DVI35Modul;
import sg.*;
import vt.*;
import vtvar.*;
import special.Func;
import special.InitTk;
import special.Signs;
import special.PoliceKeyboard;
import special.SpecialInOuts;

public class Init implements enp.Initialisierung {

	public static String anlagenName = "Maatz Junc. 0075";
	public static String tk1Name     = "Yavor - Road 70 - Road 805";
	public static String version     = "V1.2.20 G5tf.7";
	public static String[] versions = {
		"22/05/2025 - Shachar G.",
		"08/07/2025 - Ron M.",
	};

	//Intergreentime
	public static void main(String[] args) {
        System.out.println(anlagenName + " " + tk1Name + " " + version + " " + versions[versions.length - 1]);

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

	protected void initialisiereTk() {
		System.out.println();
		System.out.println("Initialisiere Hardware-Knoten...");

		//Hardware-Knoten initialisieren
		Var.hwTk1 = new HwTeilknoten230V("HWKnoten1");
// 		Var.hwTk2 = new HwTeilknoten230V("HWKnoten2");

		System.out.println("Initialisiere Software-Teilknoten...");
		System.out.println("...vor tk");

		//Software-Teilknoten initialisieren
		//				  name   | hardware | dx?  | 1st cycle
		//                       |          |      | extension
		Var.tk1 = new Tk1(tk1Name, Var.hwTk1, false,    11    );  //
// 		Var.tk2 = new Tk2(tk2Name, Var.hwTk2, true, 8 );

		Var.initarray = new InitTk[]{new InitTk1(Var.tk1)/*,new InitTk2(Var.tk2)*/};

		System.out.println("...nach tk");
	}

	protected void initialisiereDet() {
		int[] i = {1,101,1,101};
		for (int j = 0; j < Var.initarray.length; j++) {
			i = Var.initarray[j].initialisiereDet(i);
		}

		//output for Signs
		Signs.initSigns();

		//inputs for Police Keyboards
		PoliceKeyboard.initPoliceKeyboard();

		//inputs for green wave                                                                 															
		GreenWave.initGreenWaveSlave(Var.tk1,
				GreenWave.inactive,				//whether green wave is used
				GreenWave.REGULAR,	            //green wave type: REGULAR or BREATHING
				GreenWave.ULin_inactive,		//whether ULin is used. if used: ULin_active. otherwise: ULin_inactive
				GreenWave.ULout_inactive,		//whether ULout is used. if used: ULout_active. otherwise: ULout_inactive
				1,								//number of the first green wave program (for Maatz only)
				GreenWave.GWdelta_inactive, 0);	//whether delta is used. if used: GWdelta_active. otherwise: GWdelta_inactive. The number is the delta.

		//outputs for green wave master
		GreenWave.initGreenWaveMaster(Var.tk1,
				GreenWave.inactive,			//whether green wave is used                                             
				GreenWave.REGULAR,			//green wave type: REGULAR or BREATHING                                  
				GreenWave.ULin_inactive,	//whether ULin is used. if used: ULin_active. otherwise: ULin_inactive   
				GreenWave.ULout_inactive,	//whether ULout is used. if used: ULout_active. otherwise: ULout_inactive
				9, 3,						//pulseSec, pulseLen
				1, 9);						//number of the first green wave program. quantity of green wave programs (for Maatz only)

		//input for checking ups power in Maatz applications
		SpecialInOuts.initIsMapActive();
		SpecialInOuts.initUpsInput(Var.tk1, SpecialInOuts.active);

		//pulse for checking preemption application
		//                                               pulse      sec  len
		SpecialInOuts.initCheckPuls(Var.tk1, SpecialInOuts.inactive, 7 ,  1 );

		//end of input/output initialization
		Det.endInit();
		System.out.println("input/output initialized");
	}

	protected void initialisiereHW() {
		int cardsIndex = 1;
		
		//initialize device
		Var.device = new Anlage(Var.tk1/*, Var.tk2*/);
		Var.device.setName(anlagenName);

		//set internal cycle to 500 ms
		Var.device.setZyklZeitHalbeSek();

		//changes behavior of safety to needed behavior according to Israel
		Anlage.setPruefungen(Anlage.MIN_PRUEF_ABSCH | Anlage.ZWZ_PRUEF_ABSCH);

		//hardware lamp types 		/* 9w leds */
		HwLmpTyp hwRed200   = new LmpMin230V(Hw.ROT);
		HwLmpTyp hwAmber200 = new LmpMin230V(Hw.GELB);
		HwLmpTyp hwGreen200 = new LmpMin230V(Hw.GRUEN);

		//NKK
//      new Nkk230V50Hz(); - old Backplane
		new NkkFusion230V50Hz(); // for new Alpha Backplane without NKK Card
		
		//switch cards
		SK24 sk1 = new SK24(cardsIndex++, (HwTeilknoten)Var.hwTk1);
		SK24 sk2 = new SK24(cardsIndex++, (HwTeilknoten)Var.hwTk1);

		//input/output-cards
		IO24 io1 = new IO24(cardsIndex++);
		IO24 io2 = new IO24(cardsIndex++);
		IO24 io3 = new IO24(cardsIndex++);
		IO24 io4 = new IO24(cardsIndex++);

//		IO64 io64 = new IO64(cardsIndex++);
		System.out.println("cards initialised");

		//switch channels
//		new SchaltKanal(Var.tk1.k1   , Var.lred,        hwRed200  , Hw.HF, sk1, 1, Hw.SK);
//		new SchaltKanal(Var.tk1.k1   , Var.lamber,      hwAmber200, Hw.HF, sk1, 2, Hw.SK);
//		new SchaltKanal(Var.tk1.k1   , Var.lgreen,      hwGreen200, Hw.HF, sk1, 3, Hw.SK);
//		new SchaltKanal(Var.tk1.k2   , Var.lred,        hwRed200  , Hw.HF, sk1, 4, Hw.SK);
//		new SchaltKanal(Var.tk1.k2   , Var.lamber,      hwAmber200, Hw.HF, sk1, 5, Hw.SK);
//		new SchaltKanal(Var.tk1.k2   , Var.lgreen,      hwGreen200, Hw.HF, sk1, 6, Hw.SK);
		new SchaltKanal(Var.tk1.k3   , Var.lred,        hwRed200  , Hw.HF, sk1, 7, Hw.SK);
		new SchaltKanal(Var.tk1.k3   , Var.lamber,      hwAmber200, Hw.HF, sk1, 8, Hw.SK);
		new SchaltKanal(Var.tk1.k3   , Var.lgreen,      hwGreen200, Hw.HF, sk1, 9, Hw.SK);
		new SchaltKanal(Var.tk1.k4   , Var.lred,        hwRed200  , Hw.HF, sk1,10, Hw.SK);
		new SchaltKanal(Var.tk1.k4   , Var.lamber,      hwAmber200, Hw.HF, sk1,11, Hw.SK);
		new SchaltKanal(Var.tk1.k4   , Var.lgreen,      hwGreen200, Hw.HF, sk1,12, Hw.SK);
                                                        
		new SchaltKanal(Var.tk1.pa   , Var.lred,        hwRed200  , Hw.HF, sk1,13, Hw.SK);
		new SchaltKanal(Var.tk1.pa   , Var.lgreen,      hwGreen200, Hw.HF, sk1,14, Hw.SK);
		new SchaltKanal(Var.tk1.pb   , Var.lred,        hwRed200  , Hw.HF, sk1,15, Hw.SK);
		new SchaltKanal(Var.tk1.pb   , Var.lgreen,      hwGreen200, Hw.HF, sk1,16, Hw.SK);
		new SchaltKanal(Var.tk1.pc   , Var.lred,        hwRed200  , Hw.HF, sk1,17, Hw.SK);
		new SchaltKanal(Var.tk1.pc   , Var.lgreen,      hwGreen200, Hw.HF, sk1,18, Hw.SK);
		new SchaltKanal(Var.tk1.pd   , Var.lred,        hwRed200  , Hw.HF, sk1,19, Hw.SK);
		new SchaltKanal(Var.tk1.pd   , Var.lgreen,      hwGreen200, Hw.HF, sk1,20, Hw.SK);
				
		new SchaltKanal(Var.tk1.pe   , Var.lred,        hwRed200  , Hw.HF, sk1,21, Hw.SK);
		new SchaltKanal(Var.tk1.pe   , Var.lgreen,      hwGreen200, Hw.HF, sk1,22, Hw.SK);
		new SchaltKanal(Var.tk1.pf   , Var.lred,        hwRed200  , Hw.HF, sk1,23, Hw.SK);
		new SchaltKanal(Var.tk1.pf   , Var.lgreen,      hwGreen200, Hw.HF, sk1,24, Hw.SK);

		new SchaltKanal(Var.tk1.Ba   , Var.lamber_nuge, hwAmber200, Hw.HF, sk2, 1, Hw.KK);
		new SchaltKanal(Var.tk1.Bb   , Var.lamber_nuge, hwAmber200, Hw.HF, sk2, 2, Hw.KK);

		//all blinkers, including conditioned (mutne), have to be excluded  from current monitoring
		//therefore the last parameter must be Hw.KK

		//Can be Compact only if:
		// 1. A Maatz application. In Haifa ONLY ALPHA!
		// 2. Has only 1 SK24 card, and only 1 IO24 card.
		// 3. There are maximum 8 inputs and 4 outputs on the IO24 card.
		// 4. Lamps in the junction are LEDS 
		//if Compact: Input channels: 3, 4, 7, 8, 13 - 16. Output channels: 21 - 24

		//I/O24 - Input channels 1-16
		new IoKanal (Var.tk1.e_1      , io1,  1);	
		new IoKanal (Var.tk1.e_2      , io1,  2);
		new IoKanal (Var.tk1.de_3     , io1,  3);
		new IoKanal (Var.tk1.e_3      , io1,  4);
		new IoKanal (Var.tk1.de_4     , io1,  5);
		new IoKanal (Var.tk1.e_4      , io1,  6);
		
		// 
		new IoKanal (Var.tk1.tp_a     , io1,  7);
		new IoKanal (Var.tk1.tp_a_b   , io1,  8);
		new IoKanal (Var.tk1.tp_b     , io1,  9);
		new IoKanal (Var.tk1.tp_b_a   , io1, 10);
		new IoKanal (Var.tk1.tp_e     , io1, 11);
		new IoKanal (Var.tk1.tp_e_f   , io1, 12);
		new IoKanal (Var.tk1.tp_f     , io1, 13);		
		new IoKanal (Var.tk1.tp_f_e   , io1, 14);
		
		//		 I/O24 - Output channels 17-24
		new IoKanal (Var.tk1.skpa     , io1, 17);
		new IoKanal (Var.tk1.skpa_b   , io1, 18);
		new IoKanal (Var.tk1.skpb     , io1, 19);
		new IoKanal (Var.tk1.skpb_a   , io1, 20);
		new IoKanal (Var.tk1.skpe     , io1, 21);
		new IoKanal (Var.tk1.skpe_f   , io1, 22);
		new IoKanal (Var.tk1.skpf     , io1, 23);
		new IoKanal (Var.tk1.skpf_e   , io1, 24);
		
		new IoKanal (Var.tk1.RM_Gre             , io2, 17);
		new IoKanal (Var.tk1.RM_Grf             , io2, 18);
		
		new IoKanal (Var.tk1.DSYe_1             , io3,  1);
		new IoKanal (Var.tk1.DSYe_2             , io3,  2);
		new IoKanal (Var.tk1.DSYde_3            , io3,  3);
		new IoKanal (Var.tk1.DSYe_3             , io3,  4);
		new IoKanal (Var.tk1.DSYde_4            , io3,  5);
		new IoKanal (Var.tk1.DSYe_4             , io3,  6);
		new IoKanal (Var.tk1.DSYtp_a            , io3,  7);
		new IoKanal (Var.tk1.DSYtp_a_b          , io3,  8);
		new IoKanal (Var.tk1.DSYtp_b            , io3,  9);
		new IoKanal (Var.tk1.DSYtp_b_a          , io3, 10);
		new IoKanal (Var.tk1.DSYtp_e            , io3, 11);
		new IoKanal (Var.tk1.DSYtp_e_f          , io3, 12);
		new IoKanal (Var.tk1.DSYtp_f            , io3, 13);
		new IoKanal (Var.tk1.DSYtp_f_e          , io3, 14);
		
		new IoKanal (Var.tk1.RM_Gr1             , io3, 17);
		new IoKanal (Var.tk1.RM_Gr2             , io3, 18);
		new IoKanal (Var.tk1.RM_Gr3             , io3, 19);
		new IoKanal (Var.tk1.RM_Gr4             , io3, 20);
		new IoKanal (Var.tk1.RM_Gra             , io3, 21);
		new IoKanal (Var.tk1.RM_Grb             , io3, 22);
		new IoKanal (Var.tk1.RM_Grc             , io3, 23);
		new IoKanal (Var.tk1.RM_Grd             , io3, 24);


		//		 I/O24 - Input channels 1-16
		new IoKanal (PoliceKeyboard.Flash       , io4,  1); // BLINK button
		new IoKanal (PoliceKeyboard.Dark        , io4,  2); // DARK button
		new IoKanal (PoliceKeyboard.Automat     , io4,  3); // AUTO button
		new IoKanal (PoliceKeyboard.Hand        , io4,  4); // POLICE button
		new IoKanal (PoliceKeyboard.FS          , io4,  5); // ADVANCE button
		new IoKanal (SpecialInOuts.isMapActive  , io4,  6);
		new IoKanal (PoliceKeyboard.Door_N_O    , io4,  7);
		new IoKanal (SpecialInOuts.ups_power_off, io4, 16);
		//		 I/O24 - Output channels 17-24
		new IoKanal (PoliceKeyboard.Led_Flash   , io4, 17); // BLINK led
		new IoKanal (PoliceKeyboard.Led_Dark    , io4, 18); // DARK led
		new IoKanal (PoliceKeyboard.Led_Automat , io4, 19); // AUTO led
		new IoKanal (PoliceKeyboard.Led_Hand    , io4, 20); // POLICE led
		new IoKanal (PoliceKeyboard.FS_ready    , io4, 21); // ADVANCE led
		new IoKanal (GreenWave.Led_GWfail       , io4, 22); // green wave error led (SY or gw_prog)
		new IoKanal (Signs.signs_on             , io4, 24);

		//Initialization complete
		System.out.println("java initialization of the device complete!");

		Var.device.startAnlage();
	}

	protected void initialisiereSg() {
		int i = 1;
		for (int j = 0; j < Var.initarray.length; j++) {
			i = Var.initarray[j].initialisiereSg(i);
		}
		Sg.endInit();
		System.out.println("signal groups are initialized");
	}

	protected void initialisiereZwz() {
		for (int j = 0; j < Var.initarray.length; j++) {
			Var.initarray[j].initialisiereZwz();
		}
		//end of intergreen initialization
		System.out.println("intergreen times are initialized");
	}

	protected void initialisiereProgs() {
		for (int j = 0; j < Var.initarray.length; j++) {
			Var.initarray[j].initialisiereProgs();
		}
		Vt.endInit();
		//end of program initialization
		System.out.println("programs are initialized");
	}

	protected void initialisierePhasen() {
		for (int j = 0; j < Var.initarray.length; j++) {
			Var.initarray[j].initialisierePhasen();
		}
		System.out.println("phases/interstages are initialized");
	}

	protected void initialisiereZentrale() {
		System.out.print("Initialize control center .....");
		//Zentrale initialisieren
		if(Func.IsHaifaApp()) {
			int central = 0;
			central = DVI35Modul.initDvi35();
			if (central == 0) { System.out.println("DVI35 initialized!!"); }
			System.out.println("ready!");
		}
//		else {
//			Zentrale.initOcit();
//		}

		System.out.println("ready!");
	}

	protected void initialisiereOEV() {
		//Public transport
		System.out.println("pt-modul initialized");
	}

	protected void initialisiereParameter()  {
		//all the following variables can be seen with actros.access
		DebugVar.registerVarClassName(Preemptiontk1.class.getName());
		DebugVar.setTeilKnoten(Var.tk1);
		//traffic plan variables
		DebugVar.endInit();
		System.out.println("Debug Var are initialized");

		/******************************** Global Parameters *********************************/
		ParSets.signsOffsetInOn  = VarInt.init(Var.tk1, "Signs offset in ON" , 30 , true, true, true);
		ParSets.signsOffsetInOff = VarInt.init(Var.tk1, "Signs offset in OFF", 30 , true, true, true);
		
		/********************** Parameters referring to the programs ************************/
		if(Func.IsHaifaApp()) {
			InitParametersHaifa.SetParameters();
		}
		else {
			InitParametersMaatz.SetParameters();
		}

		/****************** For the Police Program ***********************/
		if (Var.STOP_PUNKTE.length != 0) {
			ParSets.r = VtVarStrukt.init(Var.tk1, "Police_Stop_Points", Var.STOP_PUNKTE, true, true, true);
		}
		/*****************************************************************/

		VtVar.endInit();
		System.out.println("variables are initialized");
	}

	protected void initialisiereUhr() {
		for (int j = 0; j < Var.initarray.length; j++) {
			Var.initarray[j].initialisiereUhr();
		}
		System.out.println("time table is initialised");
	}

	public String getVersion() {
    	return (anlagenName + ", version: " + version + ", of: " + versions[versions.length - 1] + ".");
	}
}