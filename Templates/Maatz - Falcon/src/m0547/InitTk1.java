package m0547;

import parameters.NetiveiIsraelParameters;
import core.*;
import core.Move.MoveType;
import core.Node.SpecialProgram;
import core.detectors.*;
import core.programs.*;
import sg.ZwzMatrix;
import uhr.TagesPlan;
import uhr.WochenPlan;

public class InitTk1 extends InitTk {
	private static Tk1 tk;

	public InitTk1(Tk1 tk1) {
		tk = tk1;
	}

	/**
	 * Initializes the moves (signal-groups) of this node
	 * Note: in this version, the minimum green time is handled automatically!
	 */
	public void initialisiereSg()
	{
		tk.initAllesGelbSek(0);

		//Sg-definition:     | partial- | Sg-     |  Sg-                         | min.  | min.| Main  | 200   |
		//                   | node     | name    |  type                        | green | red | Sg    | Check |
		tk.k1     =  new Move(      tk  , "_1"    ,  MoveType.Traffic_Flashing   ,    15 ,   0 , true  , false );
		tk.k2     =  new Move(      tk  , "_2"    ,  MoveType.Traffic_Flashing   ,    15 ,   0 , true  , false );
		tk.k3     =  new Move(      tk  , "_3"    ,  MoveType.Traffic_Flashing   ,     6 ,   0 , false , false );
		tk.k4     =  new Move(      tk  , "_4"    ,  MoveType.Traffic_Flashing   ,     6 ,   0 , false , false );
		                                                                                
//		tk.pa     =  new Move(      tk  , "_a"    ,  MoveType.Pedestrian         ,     6 ,   0 , false , false ); 

//		tk.Be     =  new Move(      tk  , "_Be"   ,  MoveType.Blinker_Conditional,     0 ,   0 , false , false );
	}

	/**
	 * Initializes the inter-green matrix (conflicts matrix) of this node
	 * Note: in this version, the flashing-green time is handled automatically!
	 */
	public void initialisiereZwz()
	{
		//                node |    sg1 |      sg2 | sg1->sg2 | sg2->sg1
        setMatrixConflict( tk  , tk.k1  ,   tk.k3  ,        5 ,       5 );
        setMatrixConflict( tk  , tk.k1  ,   tk.k4  ,        5 ,       5 );

        setMatrixConflict( tk  , tk.k2  ,   tk.k3  ,        5 ,       5 );

        setMatrixConflict( tk  , tk.k3  ,   tk.k4  ,        5 ,       5 );

		tk.zwz.endInit();
	}

	/**
	 * Initializes the detectors of this node
	 * The detectors should be initialized in the following order:
	 *   - first, they should be ordered by the Move to which they relate
	 *   - secondly, they should ordered by road order: D -> DE -> E -> Q
	 */
	public void initialisiereDet(){
		//		                     Name   | Move  | Set from Parameters
        tk.e_1   = new EDetector   ( "E1"   , tk.k1 , NetiveiIsraelParameters.PARAM_DM_EX_DET_01);
        tk.e_2   = new EDetector   ( "E2"   , tk.k2 , NetiveiIsraelParameters.PARAM_DM_EX_DET_02);
        tk.d_3   = new DDetector   ( "D3"   , tk.k3 , NetiveiIsraelParameters.PARAM_DM_EX_DET_03);
        tk.e_3   = new EDetector   ( "E3"   , tk.k3 , NetiveiIsraelParameters.PARAM_DM_EX_DET_04);
        tk.d_4   = new DDetector   ( "D4"   , tk.k4 , NetiveiIsraelParameters.PARAM_DM_EX_DET_05);
        tk.e_4   = new EDetector   ( "E4"   , tk.k4 , NetiveiIsraelParameters.PARAM_DM_EX_DET_06);

//        tk.tp_a  = new TPDetector  ( "TPa"  , tk.pa , NetiveiIsraelParameters.PARAM_DM_EX_DET_10);
        
//		if (Var.controller.isUseSIT()) {
//		    SITModBus.InitializeSITOK();
//		} else {
//	        tk.SITOK = new LRTDetector("SITOK", tk.k20, false);
//		}
//                                   DL   | DP   | DM   | DS   | DQ   | DF
//        tk.k20.InitializeLRTChannels(false, true , true , true , true , true );
//        tk.k21.InitializeLRTChannels(true , true , true , true , true , true );  
//        
//        tk.k20.InitializeVirtualLRTChannels();
//        tk.k21.InitializeVirtualLRTChannels();
//        
//		if (Var.controller.isPreemption()) {
//			tk.preemption = new Preemptiontk1(tk, // software intersection
//					2, // number of public transport signal groups
//					8, // maximal number of busses per public transport signal group
//					0  // number of comp stages
//			);
//		}

		// TODO:
//		tk.preemption.SwitchProg = new Input(tk, "ProgSwitch", false, false, true);
	}
	
	/**
	 * Initializes the inputs (pulses) and outputs for this node
	 *  - Incoming pulses that are received via a control center (and not through I/O)
	 *    should be marked by the setPulseFromControlCenter() method
	 *  - Outgoing pulses that are sent via a control center (and not through I/O)
	 *    should be marked by the setPulseToControlCenter() method
	 */
    public void initialisierePulses(){
        tk.ps2    = new Input       ( tk, "PS2"  );//.setPulseFromControlCenter();
        
        tk.ps1    = new Output      ( tk, "PS1"  );//.setPulseToControlCenter();
    }

	public void initialisiereProgs(){
		//Main- und PostMainProgram
		//tk1
        tk.opmain          = new OperationalMain(tk);
		tk.postmain        = new PostMain       (tk);
		tk.prog_transfer   = new Umschalt       (tk);

		//system programs
		//                                 node |  prog      | Prog                                   | cycle
		//                                      |  name      | No                                     | time
		tk.startprog 	= new EinProgramm  (tk, "StartProg", SpecialProgram.Ein  .getProgramNumber(),  7 );//tk.longestIntergreenTime + 1 );
		tk.stopprog  	= new AusProgramm  (tk, "StopProg" , SpecialProgram.Aus  .getProgramNumber(),  1                        );
		tk.blinkprog 	= new BlinkProgramm(tk, "BlinkProg", SpecialProgram.Flash.getProgramNumber(),  5                        );

		//                            node         | name    | num. | cycle | syncA | syncB | waitTime | offset
		tk.p01         = new VAProg(    tk         , "P01"   ,   1  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p02         = new VAProg(    tk         , "P02"   ,   2  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p03         = new VAProg(    tk         , "P03"   ,   3  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p04         = new VAProg(    tk         , "P04"   ,   4  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p05         = new VAProg(    tk         , "P05"   ,   5  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p06         = new VAProg(    tk         , "P06"   ,   6  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p07         = new VAProg(    tk         , "P07"   ,   7  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p08         = new VAProg(    tk         , "P08"   ,   8  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p09         = new VAProg(    tk         , "P09"   ,   9  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p10         = new VAProg(    tk         , "P10"   ,  10  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p11         = new VAProg(    tk         , "P11"   ,  11  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p12         = new VAProg(    tk         , "P12"   ,  12  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p13         = new VAProg(    tk         , "P13"   ,  13  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p14         = new VAProg(    tk         , "P14"   ,  14  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p15         = new VAProg(    tk         , "P15"   ,  15  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p16         = new VAProg(    tk         , "P16"   ,  16  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p17         = new VAProg(    tk         , "P17"   ,  17  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p18         = new VAProg(    tk         , "P18"   ,  18  ,   900 ,     0 ,     0 ,      999 ,   0 );
		tk.p19         = new VAProg(    tk         , "P19"   ,  19  ,   900 ,     0 ,     0 ,      999 ,   0 );
		if (Var.controller.getMaxProgramsNumber() > 19) {
    		tk.p20     = new VAProg(    tk         , "P20"   ,  20  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p21     = new VAProg(    tk         , "P21"   ,  21  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p22     = new VAProg(    tk         , "P22"   ,  22  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p23     = new VAProg(    tk         , "P23"   ,  23  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p24     = new VAProg(    tk         , "P24"   ,  24  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p25     = new VAProg(    tk         , "P25"   ,  25  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p26     = new VAProg(    tk         , "P26"   ,  26  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p27     = new VAProg(    tk         , "P27"   ,  27  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p28     = new VAProg(    tk         , "P28"   ,  28  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p29     = new VAProg(    tk         , "P29"   ,  29  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p30     = new VAProg(    tk         , "P30"   ,  30  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p31     = new VAProg(    tk         , "P31"   ,  31  ,   900 ,     0 ,     0 ,      999 ,   0 );
    		tk.p32     = new VAProg(    tk         , "P32"   ,  32  ,   900 ,     0 ,     0 ,      999 ,   0 );
		}

		if (Var.controller.isMaintenance()) {
		    initializeMapOnlyPrograms(tk);
		}

	    tk.all_dark    = new DarkProg(  tk         , "P99"   , SpecialProgram.Dark.getProgramNumber(), 900 , 0 , 0 , 999 , 0);
		    
	    if (Var.controller.isPoliceDoor()) {
	        //                      node | fixed-time | cycle | stop points
	        initializePoliceProgram(  tk ,      true  ,    42 , new int[] {11, 22, 33});
	    }
	}

	public void initialisierePhasen(){
		// Skeleton length here is a length without length of transition to the phase

		// Stages)
		//   	                                  node| name                | number | skel length     | skel length     | is stop in | moves
		//                                            |                     |        | start of cycle  | end of cycle    | Police     | list
	    tk.MainPhase    = new MainStage[1];
        tk.MainPhase[0] = tk.PhA   = new PhaseA   (tk, "PhaseA"             ,      1 , new int[] { 11 } , new int[] { 1 } , true      , new Move[] { tk.k1, tk.k2 }).setSpNumber(0);                          
        
        //                                        node| name                | number | skel   | is stop                          | moves
        //                                            |                     |        | length | in police                        | list
        tk.PhEQA                   = new PhaseEQA  (tk, "PhaseEQA"          ,      2 ,      0 , true                             , new Move[] { tk.k1, tk.k2 }).setSpNumber(1);
        tk.PhEQA1                  = new PhaseEQA1 (tk, "PhaseEQA1"         ,      3 ,      0 , true                             , new Move[] { tk.k1, tk.k2 }).setSpNumber(2);
        tk.PhB                     = new PhaseB    (tk, "PhaseB"            ,      4 ,      1 , true                             , new Move[] { tk.k2, tk.k4 }).setSpNumber(2);
        tk.PhC                     = new PhaseC    (tk, "PhaseC"            ,      5 ,      1 , true                             , new Move[] { tk.k3        }).setSpNumber(3);

		// Interstages                   
		//                              node| name         |  len | stage out | stage in
        tk.PhueEQA_B    = new PhueEQAB   (tk, "PhueEQAB"   ,   10 , tk.PhEQA  , tk.PhB   );
        tk.PhueEQA1_C   = new PhueEQA1C  (tk, "PhueEQA1C"  ,   10 , tk.PhEQA1 , tk.PhC   );
        tk.PhueB_C      = new PhueBC     (tk, "PhueBC"     ,   10 , tk.PhB    , tk.PhC   );
        tk.PhueB_A      = new PhueBA     (tk, "PhueBA"     ,    8 , tk.PhB    , tk.PhA   );
        tk.PhueC_A      = new PhueCA     (tk, "PhueCA"     ,    8 , tk.PhC    , tk.PhA   );
	}
	
	public void initialisiereUhr(){
		//time table
        //                                  HH | MM | prog    MON    TUE    WED    THU    FRI    SAT    SUN
	    tk.scheduler.addProgramToSchedule(   0 , 00 ,    4,  true,  true,  true,  true, false, false,  true );
	    tk.scheduler.addProgramToSchedule(   6 , 00 ,    1,  true,  true,  true,  true, false, false,  true );
	    tk.scheduler.addProgramToSchedule(   9 , 00 ,    2,  true,  true,  true,  true, false, false,  true );
	    tk.scheduler.addProgramToSchedule(  15 , 00 ,    3,  true,  true,  true,  true, false, false,  true );
                                                         
        tk.scheduler.addProgramToSchedule(   0 , 00 ,    4, false, false, false, false,  true, false, false );
        tk.scheduler.addProgramToSchedule(   7 , 00 ,    1, false, false, false, false,  true, false, false );
        tk.scheduler.addProgramToSchedule(  10 , 00 ,    3, false, false, false, false,  true, false, false );  
        tk.scheduler.addProgramToSchedule(  17 , 00 ,    4, false, false, false, false,  true, false, false );  
                                                         
        tk.scheduler.addProgramToSchedule(   0 , 00 ,    4, false, false, false, false, false,  true, false );
        tk.scheduler.addProgramToSchedule(  15 , 00 ,    3, false, false, false, false, false,  true, false );

        //                                     HH | MM | prog | DD | MM | YY 
//        tk.scheduler.addSpecialDateToSchedule(  8 , 30 ,    7 , 18 ,  1 , 25 );
        
        TagesPlan su_thur = new TagesPlan("Su_Thur", tk.p01);

        TagesPlan fr = new TagesPlan("Fr", tk.p01);

        TagesPlan sa = new TagesPlan("Sat", tk.p01);

		TagesPlan kipurEve = new TagesPlan("KipurEve", tk.p06);

		TagesPlan kipur = new TagesPlan("Kipur",  tk.p06);

		TagesPlan blink = new TagesPlan("Blink", tk.blinkprog);
		
		new WochenPlan("time table", su_thur, su_thur, su_thur, su_thur, fr, sa, su_thur);

		//                       Friday | Saturday | Kipur Eve| Kipur    | All Day | Is Kipur | Is Saturday
		//                       Sched. | Sched.   | Sched.   | Sched.   | Blink   | Blink    | Blink (Haifa)
		InitHolidays.setHolidays(    fr ,       sa , kipurEve ,    kipur ,   blink ,    false , false);
	}
}
