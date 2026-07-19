package m0547;

import core.*;
import hw.*;
import hw.tk.HwTeilknoten;

public class Var
{
	public static final int MAX_COUNTER_VALUE    =  999000;
	
	//***** Constant for all applications. Don't Change!!!
    public static final int STAGE_DURATION_MIN   =    1; // index of minimum duration time parameters 
    public static final int STAGE_DURATION_MAX   =    2; // index of maximum duration time parameters
    public static final int ONE_SEC              =    1;
    public static final int ONE_SEC_MS           = 1000;
    public static final int NONE                 =   -1;
    
	//Maatz parameters table
	public static final int indGWenableParam     =   0;
	public static final int indVersionParam      =   0;
	public static final int indStructureParam    =   0;
	public static final int indCycleParam        =   0;
	public static final int indOffsetParam       =   0;
	//Structures
	public static final int StructureNumber1     =   1;
	public static final int StructureNumber2     =   2;
	public static final int StructureNumber3     =   3;
	public static final int StructureNumber4     =   4;

	//General
	public static final int indGWULFailParam     =   0;
	public static final int VtVarUpdateCycle     =   2;
	//*************************************************

	public static final boolean isTest = false;
	public static boolean isPrintDebug = true;
	public static boolean isPrintWarnings = true;
	public static boolean isProduction = true;
	
	public static final int PROGRAM_NONE_FIXED_CYCLE = 900;
    
//    public static boolean SIT_SHOW_20L1, SIT_SHOW_20L2, SIT_SHOW_20L3;
//    public static boolean SIT_SHOW_21L1, SIT_SHOW_21L2, SIT_SHOW_21L3;
//    public static boolean SIT_SHOW_22L1, SIT_SHOW_22L2, SIT_SHOW_22L3;
//    public static boolean SIT_SHOW_23L1, SIT_SHOW_23L2, SIT_SHOW_23L3;
//    public static boolean SIT_SHOW_25L1, SIT_SHOW_25L2, SIT_SHOW_25L3;
//    public static boolean SIT_SHOW_SITOK, SIT_SHOW_KEEPALIVE;
//    public static boolean SIT_SHOW_DL20, SIT_SHOW_DP20, SIT_SHOW_OOODP20, SIT_SHOW_DM20, SIT_SHOW_OOODM20, SIT_SHOW_DS20, SIT_SHOW_DQ20, SIT_SHOW_OOODQ20, SIT_SHOW_DF20;
//    public static boolean SIT_SHOW_DL21, SIT_SHOW_DP21, SIT_SHOW_OOODP21, SIT_SHOW_DM21, SIT_SHOW_OOODM21, SIT_SHOW_DS21, SIT_SHOW_DQ21, SIT_SHOW_OOODQ21, SIT_SHOW_DF21;
//    public static boolean SIT_SHOW_DL22, SIT_SHOW_DP22, SIT_SHOW_OOODP22, SIT_SHOW_DM22, SIT_SHOW_OOODM22, SIT_SHOW_DS22, SIT_SHOW_DQ22, SIT_SHOW_OOODQ22, SIT_SHOW_DF22;
//    public static boolean SIT_SHOW_DL23, SIT_SHOW_DP23, SIT_SHOW_OOODP23, SIT_SHOW_DM23, SIT_SHOW_OOODM23, SIT_SHOW_DS23, SIT_SHOW_DQ23, SIT_SHOW_OOODQ23, SIT_SHOW_DF23;
//    public static boolean SIT_SHOW_DL25, SIT_SHOW_DP25, SIT_SHOW_OOODP25, SIT_SHOW_DM25, SIT_SHOW_OOODM25, SIT_SHOW_DS25, SIT_SHOW_DQ25, SIT_SHOW_OOODQ25, SIT_SHOW_DF25;
	
	public static Controller controller;
	
	
	//hardware node
	public static HwTeilknoten hwTk1;
//	public static HwTeilknoten hwTk2;
	//software node
	public static Tk1 tk1;
//	public static Tk2 tk2; 

    //other global variables
    public static Anlage device = null;

    // Police mode flags
    public static boolean adv_must_be_active; // for stopping in Not Fixed Time Police Program
    public static boolean hand_active;
 
    //Debuging the flow chart implementation
    public static boolean debugvt;
}