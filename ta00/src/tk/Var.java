package tk;

import special.*;
import ta118.Tk1;
import hw.*;
import hw.tk.HwTeilknoten;

public class Var {
	public static final int MAX_COUNTER_VALUE = 999000;
	
	//***** Constant for all applications. Don't Change!!!
	//Haifa parameters table
    public static final int STAGE_DURATION_MIN   = 1; // index of minimum duration time parameters 
    public static final int STAGE_DURATION_MAX   = 2; // index of maximum duration time parameters
    public static final int TYPE_DURATION        = 3; // extension type: regular type (type 0)
    public static final int TYPE_ABSOLUTE        = 4; // extension type: absolute type (type 1)
    public static final int TYPE_COMPLEMENT      = 5; // extension type: complement type (type 2)
    public static final int INPUT_FIXED			 = 1; // input type: always on
    public static final int INPUT_NORMAL		 = 2; // input type: standard (depends on current input state)
    public static final int INPUT_DISABLED		 = 3; // input type: always off
	public static final int ONE_SEC   = 1000;
	//Maatz parameters table
	public static final int indGWenableParam     =   0;
	public static final int indVersionParam      =   0;
	public static final int indStructureParam    =   0;
	public static final int indCycleParam        =   0;
	public static final int indOffsetParam       =   0;
	//Structures
	public static final int StructureNumber1     =  21;
	public static final int StructureNumber2     =  22;
	public static final int StructureNumber3     =  23;
	public static final int StructureNumber4     =  24;

	//General
	public static final int indGWULFailParam     =   0;
	public static final int VtVarUpdateCycle     =   2;
	//*************************************************
	
	public static boolean isPrintDebug = true;
	public static boolean isPrintWarnings = true;
	
	public static final int PROGRAM_NONE_FIXED_CYCLE = 900;
	
	public static Controller controller;
	
	//hardware node
	public static HwTeilknoten hwTk1;
//	public static HwTeilknoten hwTk2;
	//software node
	public static Tk1 tk1;
//	public static Tk2 tk2;

    // stop points for the Fixed Time Police Program in msec
//	public static final int STOP_PUNKTE [] = {32000, 57000, 74000, 88000, 15000, 999000};
//	public static final int FREE_PUNKTE [] = {46000, 61000, 77000,  3000, 19000, 999000};
 
	//For the None Fixed Time Police Program this array will be empty
	public static final int STOP_PUNKTE [] = {};
	public static final int FREE_PUNKTE [] = {};
	/**************************************** Constant For All Applications *****************************************/
	
    //number of logic programs:
    //	if more than 1 intersection: p01_tk1 and p01_tk2 and p01_tk3 all count as only 1 program

    //other global variables
    public static Anlage device = null;

    // Police mode flags
    public static boolean adv_must_be_active; // for stopping in Not Fixed Time Police Program
    public static boolean hand_active;
 
    //Debuging the flow chart implementation
    public static boolean debugvt;
}