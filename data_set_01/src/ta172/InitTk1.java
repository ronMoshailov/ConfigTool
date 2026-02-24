package ta172;

import ik.Handler;
import ik.HandlerExt;
import sg.ZwzMatrix;
import uhr.TagesPlan;
import uhr.WochenPlan;
import vt.StgEbene;
import special.*;
import special.Move.MoveType;
import special.Node.SpecialProgram;


public class InitTk1 extends InitTk {
	private static Tk1 tk;

	public InitTk1(Tk1 tk1) {
		tk = tk1;
	}

	public void initialisiereSg()
	{
		tk.initAllesGelbSek(0);

		// SIGNAL GROUPS
		// Minimum Green Rules:
		// - main traffic sg:		as is
		// - not main traffic sg:	maximum 5
		// - pedestrian sg:			as is (but no less than 6) 
		// - blinkers:				0    	 
		// - for traffic sg:			if the sg has green blink, subtract 3 seconds from the minimum green time

		//Sg-definition:		partial- | Sg-	|  Sg-						|  min.   | min.| Main 
		//						node     | name	|  type					    |  green  | red | Sg
		tk.k1   	=  new Move(     tk  , "_1"  , MoveType.Traffic			,	    5 ,   0 , false );
		tk.k3   	=  new Move(     tk  , "_3"  , MoveType.Traffic         ,		5 ,   0 , false);
		tk.k5   	=  new Move(     tk  , "_5"  , MoveType.Traffic_Flashing,		3 ,   0 , true );
		tk.k6   	=  new Move(     tk  , "_6"  , MoveType.Traffic_Flashing,		3 ,   0 , false);
		tk.k7   	=  new Move(     tk  , "_7"  , MoveType.Traffic_Flashing,		3 ,   0 , true );
		tk.k8   	=  new Move(     tk  , "_8"  , MoveType.Traffic_Flashing,		3 ,   0 , false);
                                              
		tk.pa		=  new Move(     tk  , "_a"	,  MoveType.Pedestrian		,		7 ,   0 , true ); 
		tk.pb		=  new Move(     tk  , "_b"	,  MoveType.Pedestrian		,		8 ,   0 , true );
		tk.pc		=  new Move(     tk  , "_c"	,  MoveType.Pedestrian		,	   12 ,   0 , false); 
		tk.pd		=  new Move(     tk  , "_d"	,  MoveType.Pedestrian		,		6 ,   0 , false);
		tk.pe		=  new Move(     tk  , "_e"	,  MoveType.Pedestrian		,		7 ,   0 , true ); 
		tk.pf		=  new Move(     tk  , "_f"	,  MoveType.Pedestrian		,		6 ,   0 , true );
		tk.pg		=  new Move(     tk  , "_g"	,  MoveType.Pedestrian		,		7 ,   0 , false); 
		tk.ph		=  new Move(     tk  , "_h"	,  MoveType.Pedestrian		,		8 ,   0 , false); 

		tk.Bb 		=  new Move(     tk  , "_Bb",  MoveType.Blinker_Conditional,	0 ,   0 , true );
		tk.Bi 		=  new Move(     tk  , "_Bi",  MoveType.Blinker_Conditional,	0 ,   0 , true );
		tk.Bj 		=  new Move(     tk  , "_Bj",  MoveType.Blinker_Conditional,	0 ,   0 , true );
		tk.Bk 		=  new Move(     tk  , "_Bk",  MoveType.Blinker_Conditional,	0 ,   0 , true );
		
	}

	public void initialisiereZwz()
	{
		//intergreen matrix

		//if a traffic sg has green-blink:
		//    if in "end":   add 3 seconds to its "time"
		//    if in "start": add 3 seconds to it's "opposing conflict time"  
		tk.zwz = new ZwzMatrix();
		//                 ending | starting  | ending->starting | starting->ending
        tk.zwz.setzeZwz( tk.k1    , tk.k3     ,  7  ,   7);
        tk.zwz.setzeZwz( tk.k1    , tk.k5     ,  7  ,   8);
        tk.zwz.setzeZwz( tk.k1    , tk.k6     ,  6  ,   9);
        tk.zwz.setzeZwz( tk.k1    , tk.k7     ,  5  ,   9);
        tk.zwz.setzeZwz( tk.k1    , tk.k8     ,  6  ,   8);
        tk.zwz.setzeZwz( tk.k1    , tk.pb     ,  7  ,  10);
        tk.zwz.setzeZwz( tk.k1    , tk.pe     ,  5  ,  10);
        tk.zwz.setzeZwz( tk.k1    , tk.ph     ,  8  ,  10);
        
        tk.zwz.setzeZwz( tk.k3    , tk.k5     ,  5  ,  10);
        tk.zwz.setzeZwz( tk.k3    , tk.k6     ,  6  ,   8);
        tk.zwz.setzeZwz( tk.k3    , tk.k7     ,  7  ,   8);
        tk.zwz.setzeZwz( tk.k3    , tk.k8     ,  6  ,   8);
        tk.zwz.setzeZwz( tk.k3    , tk.pa     ,  5  ,  10);
        tk.zwz.setzeZwz( tk.k3    , tk.pd     ,  8  ,   6);
        tk.zwz.setzeZwz( tk.k3    , tk.pf     ,  7  ,   7);

        tk.zwz.setzeZwz( tk.k5    , tk.k8     ,  8  ,  10);
        tk.zwz.setzeZwz( tk.k5    , tk.pc     ,  8  ,  17);
        tk.zwz.setzeZwz( tk.k5    , tk.ph     , 10  ,   9);

        tk.zwz.setzeZwz( tk.k6    , tk.k7     , 10  ,   9);
        tk.zwz.setzeZwz( tk.k6    , tk.k8     ,  9  ,   9);
        tk.zwz.setzeZwz( tk.k6    , tk.pc     ,  8  ,  17);
        tk.zwz.setzeZwz( tk.k6    , tk.pf     , 11  ,   8);
        
        tk.zwz.setzeZwz( tk.k7    , tk.pd     , 10  ,   6);
        tk.zwz.setzeZwz( tk.k7    , tk.pg     ,  8  ,  10);

        tk.zwz.setzeZwz( tk.k8    , tk.pb     , 11  ,  11);
        tk.zwz.setzeZwz( tk.k8    , tk.pg     ,  8  ,  10);
               
		tk.zwz.endInit();
	}

	public void initialisiereDet(){
		// Logic I/O numbers: 
		// Detectors (car, pedestrian and DPT):................ numbers   1-100
		// MAP inputs: Detectors (car, pedestrian and DPT):.... numbers 101-200
		// MAP outputs: Green lights of SG:.................... numbers   1-100
		// Feedbacks of pedestrian detectors and other outputs: numbers 101-200

		// Constant I/O numbers for all applications:
		// GreenWave inputs:................................... numbers 201-220
		// GreenWave outputs:.................................. numbers 201-220
		// PoliceKeyboard inputs:.............................. numbers 221-240
		// PoliceKeyboard outputs:............................. numbers 221-240
		// Signs output:....................................... number  251
		// checkPulse output:.................................. number  252
		// UPS Power input:.................................... number  253

		/*---------------------------------------------------------------------*
   	     | Order of Detectors:                                                 |
   	     | 1. Traffic Detectors                                                |
   	     | 	 a. sorted by sg number.                                           |
   	     | 	 b. inside sg: first Demands then Extensions                       |
   	     | 2. Public Transport Detectors (DPT)                                 |
   	     | 	 a. sorted by sg number.                                           |
   	     | 	 b. inside sg: by detector number                                  |
   	     | 3. Pedestrian Detectors (PushButtons)                               |
   	     | 4. Other Special Input                                              |
		 *---------------------------------------------------------------------*/

		/*---------------------------------------------------------------------*
   	     | Detector Types:                                                     |
		 | - Input - regular input (such as a pulse)                           |
		 | - DDetector - demand detector                                       |
		 | - EDetector - extension detector                                    |
		 | - DEDetector - demand & extension detector                          |
		 | - QDetector - Queue detector                                        |
		 | - TPDetector - Pushbutton for pedestrian crossing                   |
		 *---------------------------------------------------------------------*/

		// detectors
		//		                    Name   | SG    | Has DSY | is call-all | isSetFromParamsEnabled
		tk.d_3	  = new DDetector ("D-3"   , tk.k3 ,    true ,        true ,                  true );
		tk.e_3	  = new EDetector ("E-3"   , tk.k3 ,    true ,        true ,                  true );
		tk.d_6	  = new DDetector ("D-6"   , tk.k6 ,    true ,        true ,                  true );
		tk.e_6	  = new EDetector ("E-6"   , tk.k6 ,    true ,        true ,                  true );
		
//		tk.tp_c = new TPDetector ("TP-C"   , tk.pc ,    true ,        true ,                  true );
//		tk.tp_d = new TPDetector ("TP-D"   , tk.pd ,    true ,        true ,                  true );
//		tk.tp_g = new TPDetector ("TP-G"   , tk.pg ,    true ,        true ,                  true );
//		tk.tp_h = new TPDetector ("TP-H"   , tk.ph ,    true ,        true ,                  true );
		
		// inputs
		//                  Node | Name | Has DSY | is call-all | isSetFromParamsEnabled
//		tk.de   = new Input ( tk , "DE" ,  false  , false, 		  false);

		// outputs
		//                  Node | Name
//		tk.PB   = new Output( tk , "PB");

		if (Var.controller.isPreemption()) {
			tk.preemption = new Preemptiontk1(tk, // software intersection
					6, // number of logical DPT (that are referred to in the traffic plan logic)
					2, // number of DPT that are physically connected BUT are not used logically
					2, // number of public transport signal groups
					8, // maximal number of busses per public transport signal group
					10, // timeout for deletion when bus is late to cancellation detector
					true, // should reset when late even if signal group is red
					10, // number of red stages
					4 // number of comp stages
			);
		}
	}

	public void initialisiereProgs(){

		//Main- und PostMainProgram
		//tk1
		tk.opmain          = new OperationalMain(tk);
		tk.postmain        = new MyPostMainFkt(tk);
		tk.prog_transfer   = new MyUmschaltFkt(tk);

		//system programs
		//                             | ref.   |  prog      |  prog                                  | cycle
		//                             | on pn  |  name      |   No                                   | time
		//tk1
		tk.startprog 	= new MyEinProgramm  (tk, "StartProg", SpecialProgram.Ein  .getProgramNumber(),  7 );

		tk.stopprog  	= new MyAusProgramm  (tk, "StopProg" , SpecialProgram.Aus  .getProgramNumber(),  1 );
		tk.blinkprog 	= new MyBlinkProgramm(tk, "BlinkProg", SpecialProgram.Flash.getProgramNumber(),  5 );

		//                            node         | name    | num. | cycle | syncA | syncB | waitTime | offset
		tk.p01         = new VAProg(    tk         , "P01"   ,   1  ,     90,     62,     62,      999 ,   0 );// 1 - P09 
		tk.p02         = new VAProg(    tk         , "P02"   ,   2  ,     90,     24,     24,      999 ,   0 );// 2 - ACTIVE  
		tk.p03         = new VAProg(    tk         , "P03"   ,   3  ,    100,     62,     62,      999 ,   0 );// 3 - P09
		tk.p04         = new VAProg(    tk         , "P04"   ,   4  ,    100,     62,     62,      999 ,   0 );// 4 - P09
		tk.p05         = new VAProg(    tk         , "P05"   ,   5  ,     90,      1,      1,      999 ,   0 );// 5 - ACTIVE
		tk.p06         = new VAProg(    tk         , "P06"   ,   6  ,     85,     17,     17,      999 ,   0 );// 6 - ACTIVE
		tk.p07         = new VAProg(    tk         , "P07"   ,   7  ,    100,      3,      3,      999 ,   0 );// 7 - P09
		tk.p08         = new VAProg(    tk         , "P08"   ,   8  ,    100,      2,      2,      999 ,   0 );// 8 - ACTIVE
		tk.p09         = new VAProg(    tk         , "P09"   ,   9  ,    100,     62,     62,      999 ,   0 );// 9 - ACTIVE
		tk.p10         = new VAProg(    tk         , "P10"   ,  10  ,    100,      5,      5,      999 ,   0 );//10 - ACTIVE
		tk.p11         = new VAProg(    tk         , "P11"   ,  11  ,    127,     62,     62,      999 ,   0 );//11 - P09
		tk.p12         = new VAProg(    tk         , "P12"   ,  12  ,    127,     62,     62,      999 ,   0 );//12 - P09
		tk.p13         = new VAProg(    tk         , "P13"   ,  13  ,    127,     62,     62,      999 ,   0 );//13 - P09
		tk.p14         = new VAProg(    tk         , "P14"   ,  14  ,    127,     62,     62,      999 ,   0 );//14 - P09
		tk.p15         = new VAProg(    tk         , "P15"   ,  15  ,    110,     64,     64,      999 ,   0 );//15 - ACTIVE
		tk.p16         = new VAProg(    tk         , "P16"   ,  16  ,    127,     62,     62,      999 ,   0 );//16 - P09
		tk.p17         = new VAProg(    tk         , "P17"   ,  17  ,    110,     60,     60,      999 ,   0 );//17 - ACTIVE
		tk.p18         = new VAProg(    tk         , "P18"   ,  18  ,    110,     64,     64,      999 ,   0 );//18 - ACTIVE
		tk.p19         = new VAProg(    tk         , "P19"   ,  19  ,    100,     62,     62,      999 ,   0 );//19 - P09
		tk.p20         = new VAProg(    tk         , "P20"   ,  20  ,    100,     62,     62,      999 ,   0 );//20 - P09
		tk.p21         = new VAProg(    tk         , "P21"   ,  21  ,    100,     62,     62,      999 ,   0 );//21 - P09
		tk.p22         = new VAProg(    tk         , "P22"   ,  22  ,    100,     62,     62,      999 ,   0 );//22 - P09
		tk.p23         = new VAProg(    tk         , "P23"   ,  23  ,    100,     62,     62,      999 ,   0 );//23 - P09
		tk.p24         = new VAProg(    tk         , "P24"   ,  24  ,    100,     62,     62,      999 ,   0 );//24 - P09
		tk.p25         = new VAProg(    tk         , "P25"   ,  25  ,    100,     62,     62,      999 ,   0 );//25 - P09
		tk.p26         = new VAProg(    tk         , "P26"   ,  26  ,    100,     62,     62,      999 ,   0 );//26 - P09
		tk.p27         = new VAProg(    tk         , "P27"   ,  27  ,    100,     62,     62,      999 ,   0 );//27 - P09
		tk.p28         = new VAProg(    tk         , "P28"   ,  28  ,    100,     62,     62,      999 ,   0 );//28 - P09
		tk.p29         = new VAProg(    tk         , "P29"   ,  29  ,    100,     62,     62,      999 ,   0 );//29 - P09
		tk.p30         = new VAProg(    tk         , "P30"   ,  30  ,    100,     62,     62,      999 ,   0 );//30 - P09
		tk.p31         = new VAProg(    tk         , "P31"   ,  31  ,    100,     62,     62,      999 ,   0 );//31 - P09
		tk.p32         = new VAProg(    tk         , "P32"   ,  32  ,    100,     62,     62,      999 ,   0 );//32 - P09

		if (Var.controller.isMaintenance()) {
			tk.p01MO   = new VAProg(    tk         , "P01MO" ,  51  , tk.p01.getUmlaufZeit() , tk.p01.getGwpA() , tk.p01.getGwpB() , tk.p01.getWarteZeit(), tk.p01.getVersatzZeit() );
			tk.p02MO   = new VAProg(    tk         , "P02MO" ,  52  , tk.p02.getUmlaufZeit() , tk.p02.getGwpA() , tk.p02.getGwpB() , tk.p02.getWarteZeit(), tk.p02.getVersatzZeit() );
			tk.p03MO   = new VAProg(    tk         , "P03MO" ,  53  , tk.p03.getUmlaufZeit() , tk.p03.getGwpA() , tk.p03.getGwpB() , tk.p03.getWarteZeit(), tk.p03.getVersatzZeit() );
			tk.p04MO   = new VAProg(    tk         , "P04MO" ,  54  , tk.p04.getUmlaufZeit() , tk.p04.getGwpA() , tk.p04.getGwpB() , tk.p04.getWarteZeit(), tk.p04.getVersatzZeit() );
			tk.p05MO   = new VAProg(    tk         , "P05MO" ,  55  , tk.p05.getUmlaufZeit() , tk.p05.getGwpA() , tk.p05.getGwpB() , tk.p05.getWarteZeit(), tk.p05.getVersatzZeit() );
			tk.p06MO   = new VAProg(    tk         , "P06MO" ,  56  , tk.p06.getUmlaufZeit() , tk.p06.getGwpA() , tk.p06.getGwpB() , tk.p06.getWarteZeit(), tk.p06.getVersatzZeit() );
			tk.p07MO   = new VAProg(    tk         , "P07MO" ,  57  , tk.p07.getUmlaufZeit() , tk.p07.getGwpA() , tk.p07.getGwpB() , tk.p07.getWarteZeit(), tk.p07.getVersatzZeit() );
			tk.p08MO   = new VAProg(    tk         , "P08MO" ,  58  , tk.p08.getUmlaufZeit() , tk.p08.getGwpA() , tk.p08.getGwpB() , tk.p08.getWarteZeit(), tk.p08.getVersatzZeit() );
			tk.p09MO   = new VAProg(    tk         , "P09MO" ,  59  , tk.p09.getUmlaufZeit() , tk.p09.getGwpA() , tk.p09.getGwpB() , tk.p09.getWarteZeit(), tk.p09.getVersatzZeit() );
			tk.p10MO   = new VAProg(    tk         , "P10MO" ,  60  , tk.p10.getUmlaufZeit() , tk.p10.getGwpA() , tk.p10.getGwpB() , tk.p10.getWarteZeit(), tk.p10.getVersatzZeit() );
			tk.p11MO   = new VAProg(    tk         , "P11MO" ,  61  , tk.p11.getUmlaufZeit() , tk.p11.getGwpA() , tk.p11.getGwpB() , tk.p11.getWarteZeit(), tk.p11.getVersatzZeit() );
			tk.p12MO   = new VAProg(    tk         , "P12MO" ,  62  , tk.p12.getUmlaufZeit() , tk.p12.getGwpA() , tk.p12.getGwpB() , tk.p12.getWarteZeit(), tk.p12.getVersatzZeit() );
			tk.p13MO   = new VAProg(    tk         , "P13MO" ,  63  , tk.p13.getUmlaufZeit() , tk.p13.getGwpA() , tk.p13.getGwpB() , tk.p13.getWarteZeit(), tk.p13.getVersatzZeit() );
			tk.p14MO   = new VAProg(    tk         , "P14MO" ,  64  , tk.p14.getUmlaufZeit() , tk.p14.getGwpA() , tk.p14.getGwpB() , tk.p14.getWarteZeit(), tk.p14.getVersatzZeit() );
			tk.p15MO   = new VAProg(    tk         , "P15MO" ,  65  , tk.p15.getUmlaufZeit() , tk.p15.getGwpA() , tk.p15.getGwpB() , tk.p15.getWarteZeit(), tk.p15.getVersatzZeit() );
			tk.p16MO   = new VAProg(    tk         , "P16MO" ,  66  , tk.p16.getUmlaufZeit() , tk.p16.getGwpA() , tk.p16.getGwpB() , tk.p16.getWarteZeit(), tk.p16.getVersatzZeit() );
			tk.p17MO   = new VAProg(    tk         , "P17MO" ,  67  , tk.p17.getUmlaufZeit() , tk.p17.getGwpA() , tk.p17.getGwpB() , tk.p17.getWarteZeit(), tk.p17.getVersatzZeit() );
			tk.p18MO   = new VAProg(    tk         , "P18MO" ,  68  , tk.p18.getUmlaufZeit() , tk.p18.getGwpA() , tk.p18.getGwpB() , tk.p18.getWarteZeit(), tk.p18.getVersatzZeit() );
			tk.p19MO   = new VAProg(    tk         , "P19MO" ,  69  , tk.p19.getUmlaufZeit() , tk.p19.getGwpA() , tk.p19.getGwpB() , tk.p19.getWarteZeit(), tk.p19.getVersatzZeit() );
			tk.p20MO   = new VAProg(    tk         , "P20MO" ,  70  , tk.p20.getUmlaufZeit() , tk.p20.getGwpA() , tk.p20.getGwpB() , tk.p20.getWarteZeit(), tk.p20.getVersatzZeit() );
			tk.p21MO   = new VAProg(    tk         , "P21MO" ,  71  , tk.p21.getUmlaufZeit() , tk.p21.getGwpA() , tk.p21.getGwpB() , tk.p21.getWarteZeit(), tk.p21.getVersatzZeit() );
			tk.p22MO   = new VAProg(    tk         , "P22MO" ,  72  , tk.p22.getUmlaufZeit() , tk.p22.getGwpA() , tk.p22.getGwpB() , tk.p22.getWarteZeit(), tk.p22.getVersatzZeit() );
			tk.p23MO   = new VAProg(    tk         , "P23MO" ,  73  , tk.p23.getUmlaufZeit() , tk.p23.getGwpA() , tk.p23.getGwpB() , tk.p23.getWarteZeit(), tk.p23.getVersatzZeit() );
			tk.p24MO   = new VAProg(    tk         , "P24MO" ,  74  , tk.p24.getUmlaufZeit() , tk.p24.getGwpA() , tk.p24.getGwpB() , tk.p24.getWarteZeit(), tk.p24.getVersatzZeit() );
			tk.p25MO   = new VAProg(    tk         , "P25MO" ,  75  , tk.p25.getUmlaufZeit() , tk.p25.getGwpA() , tk.p25.getGwpB() , tk.p25.getWarteZeit(), tk.p25.getVersatzZeit() );
			tk.p26MO   = new VAProg(    tk         , "P26MO" ,  76  , tk.p26.getUmlaufZeit() , tk.p26.getGwpA() , tk.p26.getGwpB() , tk.p26.getWarteZeit(), tk.p26.getVersatzZeit() );
			tk.p27MO   = new VAProg(    tk         , "P27MO" ,  77  , tk.p27.getUmlaufZeit() , tk.p27.getGwpA() , tk.p27.getGwpB() , tk.p27.getWarteZeit(), tk.p27.getVersatzZeit() );
			tk.p28MO   = new VAProg(    tk         , "P28MO" ,  78  , tk.p28.getUmlaufZeit() , tk.p28.getGwpA() , tk.p28.getGwpB() , tk.p28.getWarteZeit(), tk.p28.getVersatzZeit() );
			tk.p29MO   = new VAProg(    tk         , "P29MO" ,  79  , tk.p29.getUmlaufZeit() , tk.p29.getGwpA() , tk.p29.getGwpB() , tk.p29.getWarteZeit(), tk.p29.getVersatzZeit() );
			tk.p30MO   = new VAProg(    tk         , "P30MO" ,  80  , tk.p30.getUmlaufZeit() , tk.p30.getGwpA() , tk.p30.getGwpB() , tk.p30.getWarteZeit(), tk.p30.getVersatzZeit() );
			tk.p31MO   = new VAProg(    tk         , "P31MO" ,  81  , tk.p31.getUmlaufZeit() , tk.p31.getGwpA() , tk.p31.getGwpB() , tk.p31.getWarteZeit(), tk.p31.getVersatzZeit() );
			tk.p32MO   = new VAProg(    tk         , "P32MO" ,  82  , tk.p32.getUmlaufZeit() , tk.p32.getGwpA() , tk.p32.getGwpB() , tk.p32.getWarteZeit(), tk.p32.getVersatzZeit() );
		}

		tk.all_dark = new DarkProg( tk   , "P99"   , SpecialProgram.Dark.getProgramNumber(), 900 , 0 , 0 , 999 , 0);

		if (Var.controller.isPoliceDoor()) {
			// For the Fixed Time Police Program
//			tk.police_fixed = new MyHandProgramm(tk, "Police", SpecialProgram.Police.getProgramNumber(), 70,  1,  1, 999, 0);
			// For the None Fixed Time Police Program      
			tk.police_non_fixed = new VAProg(tk, "Police", SpecialProgram.Police.getProgramNumber(), 900, 2, 2, 999, 0);
		}

		Func.ifClockCoordination(tk, tk.p01, true);
		Func.ifClockCoordination(tk, tk.p02, true);
		Func.ifClockCoordination(tk, tk.p03, true);
		Func.ifClockCoordination(tk, tk.p04, true);
		Func.ifClockCoordination(tk, tk.p05, true);
		Func.ifClockCoordination(tk, tk.p06, true);
		Func.ifClockCoordination(tk, tk.p07, true);
		Func.ifClockCoordination(tk, tk.p08, true);
		Func.ifClockCoordination(tk, tk.p09, true);
		Func.ifClockCoordination(tk, tk.p10, true);
		Func.ifClockCoordination(tk, tk.p11, true);
		Func.ifClockCoordination(tk, tk.p12, true);
		Func.ifClockCoordination(tk, tk.p13, true);
		Func.ifClockCoordination(tk, tk.p14, true);
		Func.ifClockCoordination(tk, tk.p15, true);
		Func.ifClockCoordination(tk, tk.p16, true);
		Func.ifClockCoordination(tk, tk.p17, true);
		Func.ifClockCoordination(tk, tk.p18, true);
		Func.ifClockCoordination(tk, tk.p19, true);
		Func.ifClockCoordination(tk, tk.p20, true);
		Func.ifClockCoordination(tk, tk.p21, true);
		Func.ifClockCoordination(tk, tk.p22, true);
		Func.ifClockCoordination(tk, tk.p23, true);
		Func.ifClockCoordination(tk, tk.p24, true);
		Func.ifClockCoordination(tk, tk.p25, true);
		Func.ifClockCoordination(tk, tk.p26, true);
		Func.ifClockCoordination(tk, tk.p27, true);
		Func.ifClockCoordination(tk, tk.p28, true);
		Func.ifClockCoordination(tk, tk.p29, true);
		Func.ifClockCoordination(tk, tk.p30, true);
		Func.ifClockCoordination(tk, tk.p31, true);
		Func.ifClockCoordination(tk, tk.p32, true);

		//coordination
		ik.HandlerExt.init(tk, StgEbene.STG_UHR, HandlerExt.TYP_UHR, Handler.VAR_RR_NULL_UHR);
	}

	public void initialisierePhasen(){
		// Skeleton length here is a length without length of transition to the phase

		// Stages)
		//   	                         node|     name            |number | skel length     | skel length     | is stop in | moves
		//                                   |                     |       | start of cycle  | end of cycle    | Police     | list
		tk.MainPhase = tk.PhA = new PhaseA(tk, "PhaseA"            ,    10 , new int[] { 3 } , new int[] { 0 } , false      , new Move[] {tk.k5, tk.k7, tk.pa, tk.pb, tk.pe, tk.pf});
		
//      node| name			| number | skel | sp | is stop in | moves
		//                                   |				|        | len  | sp | Police     | list
		tk.PhEQA      = new PhaseEQA      (tk, "PhaseEQA"	,     11 ,    3 ,  1 , true       , new Move[] {tk.k5, tk.k7, tk.pa, tk.pb, tk.pe, tk.pf       });
		tk.PhB        = new PhaseB        (tk, "PhaseB"		,     20 ,    8 ,  2 , true       , new Move[] {tk.k7, tk.k8, tk.pa, tk.pc, tk.pe, tk.pf, tk.ph});
		tk.PhC        = new PhaseC        (tk, "PhaseC"		,     30 ,    3 ,  3 , true       , new Move[] {tk.k3, tk.pc, tk.pe, tk.pg, tk.ph              });
		tk.PhD        = new PhaseD        (tk, "PhaseD"		,     40 ,    1 ,  4 , true       , new Move[] {tk.k1, tk.pa, tk.pc, tk.pd, tk.pf, tk.pg       });
		tk.PhE        = new PhaseE        (tk, "PhaseE" 	,     50 ,    3 ,  5 , true       , new Move[] {tk.k5, tk.k6, tk.pa, tk.pb, tk.pd, tk.pe, tk.pg});
		
		// Interstages                 
		//                             		node| name			|  len | stage out | stage in
		tk.PhueEQA_B	= new PhueEQAB		(tk, "PhueEQAB"		,   99 , tk.PhEQA  , tk.PhB 	);
		tk.PhueB_C		= new PhueBC		(tk, "PhueBC"		,   99 , tk.PhB    , tk.PhC     );
		tk.PhueB_D		= new PhueBD		(tk, "PhueBD"		,   99 , tk.PhB    , tk.PhD     );
		tk.PhueC_D		= new PhueCD		(tk, "PhueCD"		,   99 , tk.PhC    , tk.PhD     );
		tk.PhueD_A		= new PhueDA		(tk, "PhueDA"		,   99 , tk.PhD    , tk.PhA     );
		tk.PhueD_E		= new PhueDE		(tk, "PhueDE"		,   99 , tk.PhD    , tk.PhE     );
		tk.PhueE_A		= new PhueEA		(tk, "PhueDA"		,   99 , tk.PhE    , tk.PhA     );
				
		if (Var.controller.isPreemption()) {
			tk.preemption.InitializeStages();
		}
	}

	public void initialisiereUhr(){
		//time table
		
		TagesPlan sun_thur = new TagesPlan("Sunday_Thurday", tk.p06);
		sun_thur.initProgWunsch(  6, 00,  tk.p07 );
		sun_thur.initProgWunsch(  9, 00,  tk.p10 );		
		sun_thur.initProgWunsch( 12, 00,  tk.p09 );
		sun_thur.initProgWunsch( 14, 30,  tk.p15 );
		sun_thur.initProgWunsch( 20, 00,  tk.p06 );

		TagesPlan fr = new TagesPlan("Fr", tk.p06);
		fr.initProgWunsch(  6, 30,  tk.p02 );
		fr.initProgWunsch( 11, 45,  tk.p05 );
           
		TagesPlan sa = new TagesPlan("Sat", tk.p05);
		sa.initProgWunsch( 05, 00,  tk.p05 );
		
		TagesPlan kipurEve = new TagesPlan("KipurEve", tk.p05);
		kipurEve.initProgWunsch( 6 , 30,  tk.p03 );
		kipurEve.initProgWunsch( 9 , 00,  tk.p04 );
		kipurEve.initProgWunsch(10 , 00,  tk.p06 );
		kipurEve.initProgWunsch(19 , 00,  tk.p04 );
		kipurEve.initProgWunsch(23 , 45,  tk.p04 );

		TagesPlan kipur = new TagesPlan("Kipur", tk.p05);
		kipur.initProgWunsch( 8 , 30,  tk.p04 );
		kipur.initProgWunsch(23 , 45,  tk.p04 );

		TagesPlan blink = new TagesPlan("Blink", tk.blinkprog);
		blink.initProgWunsch( 0 , 01,  tk.blinkprog );

		new WochenPlan("time table", sun_thur, sun_thur, sun_thur, sun_thur, fr, sa, sun_thur);

		//                       Friday | Saturday | Kipur Eve| Kipur    | All Day | Is Kipur | Is Saturday
		//                       Sched. | Sched.   | Sched.   | Sched.   | Blink   | Blink    | Blink (Haifa)
		InitHolidays.setHolidays(    fr ,       sa , kipurEve ,    kipur ,   blink ,    false , false);
	}
}
