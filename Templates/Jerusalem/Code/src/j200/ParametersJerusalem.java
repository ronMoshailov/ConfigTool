package j200;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 239                                                                     	*
 *  Junction Name  : Varburg / Rains / HaTkuma Yahiam - Maavar Hazaya							*
 *  Equipmentno.   : h239                                                                    	*
 *                                                                                              *
 ************************************************************************************************/

import special.Dvi35ParametersBase;
import special.Node;
import vtvar.VtVarStrukt;

/**
 * @author Ilia Butvinnik
 * @version 0.0.1
 * @since 01/06/2019
 */
public class ParametersJerusalem extends Dvi35ParametersBase {
	
//	private static int SP_MIN_NUMBER	=  0;
//	private static int SP_MAX_NUMBER	= 20;
	
	public ParametersJerusalem() {
		super(200);
	}

	static final int[] parameters_indexes_stucture_1 = {  1,  2,   3,   4,  5,  6,  7,  8,  9};
	//													dver ver cyc   Gw str   B   C   B   C	                                  
	static int[]       DVI35_P01                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};// 1 - ACTIVE
	static int[]       DVI35_P02                     = { 15,  1, 120,  11,  1,  1,  1, 36,  6};// 2 - ACTIVE
	static int[]       DVI35_P03                     = { 15,  1, 120,   5,  1,  1,  1, 27, 16};// 3 - ACTIVE
	static int[]       DVI35_P04                     = { 15,  1,  80,  18,  1,  1,  1, 17,  6};// 4 - ACTIVE
	static int[]       DVI35_P05                     = { 15,  1, 120,   5,  1,  1,  1, 10,  8};// 5 - ACTIVE
	static int[]       DVI35_P06                     = { 15,  1, 120,   5,  1,  1,  1, 27, 29};// 6 - ACTIVE 
	static int[]       DVI35_P07                     = { 15,  1, 120,   5,  1,  1,  1, 50,  8};// 7 - ACTIVE
	
	static int[]       DVI35_P08                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};// 8 - P01
	static int[]       DVI35_P09                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};// 9 - P01
	static int[]       DVI35_P10                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//10 - P01
	static int[]       DVI35_P11                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//11 - P01
	static int[]       DVI35_P12                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//12 - P01
	static int[]       DVI35_P13                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//13 - P01
	static int[]       DVI35_P14                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//14 - P01
	static int[]       DVI35_P15                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//15 - P01                                                                                                                                                                                                                                                           	                                                                                                                                                                                                                                                                                                                                          
	static int[]       DVI35_P16                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//16 - P01
	static int[]       DVI35_P17                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//17 - P01
	static int[]       DVI35_P18                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//18 - P01
	static int[]       DVI35_P19                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//19 - P01                                                                                                                                                                                                                                   	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               	                                                                                                                                                                                                                                                                                                                                          
	static int[]       DVI35_P20                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//20 - P01
	static int[]       DVI35_P21                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//21 - P01
	static int[]       DVI35_P22                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//22 - P01
	static int[]       DVI35_P23                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//23 - P01
	static int[]       DVI35_P24                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//24 - P01
	static int[]       DVI35_P25                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//25 - P01
	static int[]       DVI35_P26                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//26 - P01
	static int[]       DVI35_P27                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//27 - P01
	static int[]       DVI35_P28                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//28 - P01
	static int[]       DVI35_P29                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//29 - P01
	static int[]       DVI35_P30                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//30 - P01
	static int[]       DVI35_P31                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//31 - P01
	static int[]       DVI35_P32                     = { 15,  1, 120,  15,  1,  1,  1, 32, 18};//32 - P01
	
	static int[]       DVI35_PLAN01                  = new int[257];

	// Indexes of constant parameters
	public static final int DesignVer           = 0;
	public static final int VersionIndex        = 1;
	public static final int CycleTimeIndex      = 2;
	public static final int GreenWaveOffseIndex = 3;
	public static final int StructIndex         = 4;
	// TODO: update according to application
	public static final int StartOfDetectorsIndex = 9;
	
	// Parameters for stages' extensions
//	public static int GTmin_A_Duration;
	public static int GTmin_B_Duration;
	public static int GTmin_C_Duration;

	public static int GTmax_B_Duration;
	public static int GTmax_C_Duration;
	
	public static void SetParametersValues() {
		GTmin_B_Duration  = Var.controller.dvi35Parameters.parameters[5] * Var.ONE_SEC;
		GTmin_C_Duration  = Var.controller.dvi35Parameters.parameters[6] * Var.ONE_SEC;
		
		GTmax_B_Duration  = Var.controller.dvi35Parameters.parameters[7] * Var.ONE_SEC;
		GTmax_C_Duration  = Var.controller.dvi35Parameters.parameters[8] * Var.ONE_SEC;
	}
	
	
	public void setGaps() {
//		Var.tk1.e_6.gapUnit	= VarInt.init(Var.tk1, "GAP E-6" , 1000, true, true, true);
	}

	static int[] ulfail    = new int[] {  20 };

	public void SetParameters()
	{
		setGaps();
		
		ParSets.pars_P01 = VtVarStrukt.init(Var.tk1, "DVI35_P01", initializeParameters(DVI35_P01, parameters_indexes_stucture_1, new int[] {  1, 51 }), true, true, true);
		ParSets.pars_P02 = VtVarStrukt.init(Var.tk1, "DVI35_P02", initializeParameters(DVI35_P02, parameters_indexes_stucture_1, new int[] {  2, 52 }), true, true, true);
		ParSets.pars_P03 = VtVarStrukt.init(Var.tk1, "DVI35_P03", initializeParameters(DVI35_P03, parameters_indexes_stucture_1, new int[] {  3, 53 }), true, true, true);
		ParSets.pars_P04 = VtVarStrukt.init(Var.tk1, "DVI35_P04", initializeParameters(DVI35_P04, parameters_indexes_stucture_1, new int[] {  4, 54 }), true, true, true);
		ParSets.pars_P05 = VtVarStrukt.init(Var.tk1, "DVI35_P05", initializeParameters(DVI35_P05, parameters_indexes_stucture_1, new int[] {  5, 55 }), true, true, true);
		ParSets.pars_P06 = VtVarStrukt.init(Var.tk1, "DVI35_P06", initializeParameters(DVI35_P06, parameters_indexes_stucture_1, new int[] {  6, 56 }), true, true, true);	    
		ParSets.pars_P07 = VtVarStrukt.init(Var.tk1, "DVI35_P07", initializeParameters(DVI35_P07, parameters_indexes_stucture_1, new int[] {  7, 57 }), true, true, true);
		ParSets.pars_P08 = VtVarStrukt.init(Var.tk1, "DVI35_P08", initializeParameters(DVI35_P08, parameters_indexes_stucture_1, new int[] {  8, 58 }), true, true, true);       	    	    
		ParSets.pars_P09 = VtVarStrukt.init(Var.tk1, "DVI35_P09", initializeParameters(DVI35_P09, parameters_indexes_stucture_1, new int[] {  9, 59 }), true, true, true);
		ParSets.pars_P10 = VtVarStrukt.init(Var.tk1, "DVI35_P10", initializeParameters(DVI35_P10, parameters_indexes_stucture_1, new int[] { 10, 60 }), true, true, true);	    
		ParSets.pars_P11 = VtVarStrukt.init(Var.tk1, "DVI35_P11", initializeParameters(DVI35_P11, parameters_indexes_stucture_1, new int[] { 11, 61 }), true, true, true);
		ParSets.pars_P12 = VtVarStrukt.init(Var.tk1, "DVI35_P12", initializeParameters(DVI35_P12, parameters_indexes_stucture_1, new int[] { 12, 62 }), true, true, true);
		ParSets.pars_P13 = VtVarStrukt.init(Var.tk1, "DVI35_P13", initializeParameters(DVI35_P13, parameters_indexes_stucture_1, new int[] { 13, 63 }), true, true, true);
		ParSets.pars_P14 = VtVarStrukt.init(Var.tk1, "DVI35_P14", initializeParameters(DVI35_P14, parameters_indexes_stucture_1, new int[] { 14, 64 }), true, true, true);
		ParSets.pars_P15 = VtVarStrukt.init(Var.tk1, "DVI35_P15", initializeParameters(DVI35_P15, parameters_indexes_stucture_1, new int[] { 15, 65 }), true, true, true);	    
		ParSets.pars_P16 = VtVarStrukt.init(Var.tk1, "DVI35_P16", initializeParameters(DVI35_P16, parameters_indexes_stucture_1, new int[] { 16, 66 }), true, true, true);	    
		ParSets.pars_P17 = VtVarStrukt.init(Var.tk1, "DVI35_P17", initializeParameters(DVI35_P17, parameters_indexes_stucture_1, new int[] { 17, 67 }), true, true, true);	    
		ParSets.pars_P18 = VtVarStrukt.init(Var.tk1, "DVI35_P18", initializeParameters(DVI35_P18, parameters_indexes_stucture_1, new int[] { 18, 68 }), true, true, true);	    
		ParSets.pars_P19 = VtVarStrukt.init(Var.tk1, "DVI35_P19", initializeParameters(DVI35_P19, parameters_indexes_stucture_1, new int[] { 19, 69 }), true, true, true);	    
		ParSets.pars_P20 = VtVarStrukt.init(Var.tk1, "DVI35_P20", initializeParameters(DVI35_P20, parameters_indexes_stucture_1, new int[] { 20, 70 }), true, true, true);	    
		ParSets.pars_P21 = VtVarStrukt.init(Var.tk1, "DVI35_P21", initializeParameters(DVI35_P21, parameters_indexes_stucture_1, new int[] { 21, 71 }), true, true, true);	    
		ParSets.pars_P22 = VtVarStrukt.init(Var.tk1, "DVI35_P22", initializeParameters(DVI35_P22, parameters_indexes_stucture_1, new int[] { 22, 72 }), true, true, true);	    
		ParSets.pars_P23 = VtVarStrukt.init(Var.tk1, "DVI35_P23", initializeParameters(DVI35_P23, parameters_indexes_stucture_1, new int[] { 23, 73 }), true, true, true);	    
		ParSets.pars_P24 = VtVarStrukt.init(Var.tk1, "DVI35_P24", initializeParameters(DVI35_P24, parameters_indexes_stucture_1, new int[] { 24, 74 }), true, true, true);	    
		ParSets.pars_P25 = VtVarStrukt.init(Var.tk1, "DVI35_P25", initializeParameters(DVI35_P25, parameters_indexes_stucture_1, new int[] { 25, 75 }), true, true, true);	    
		ParSets.pars_P26 = VtVarStrukt.init(Var.tk1, "DVI35_P26", initializeParameters(DVI35_P26, parameters_indexes_stucture_1, new int[] { 26, 76 }), true, true, true);	    
		ParSets.pars_P27 = VtVarStrukt.init(Var.tk1, "DVI35_P27", initializeParameters(DVI35_P27, parameters_indexes_stucture_1, new int[] { 27, 77 }), true, true, true);	    
		ParSets.pars_P28 = VtVarStrukt.init(Var.tk1, "DVI35_P28", initializeParameters(DVI35_P28, parameters_indexes_stucture_1, new int[] { 28, 78 }), true, true, true);	    
		ParSets.pars_P29 = VtVarStrukt.init(Var.tk1, "DVI35_P29", initializeParameters(DVI35_P29, parameters_indexes_stucture_1, new int[] { 29, 79 }), true, true, true);	    
		ParSets.pars_P30 = VtVarStrukt.init(Var.tk1, "DVI35_P30", initializeParameters(DVI35_P30, parameters_indexes_stucture_1, new int[] { 30, 80 }), true, true, true);	    
		ParSets.pars_P31 = VtVarStrukt.init(Var.tk1, "DVI35_P31", initializeParameters(DVI35_P31, parameters_indexes_stucture_1, new int[] { 31, 81 }), true, true, true);	    
		ParSets.pars_P32 = VtVarStrukt.init(Var.tk1, "DVI35_P32", initializeParameters(DVI35_P32, parameters_indexes_stucture_1, new int[] { 32, 82 }), true, true, true);

		ParSets.scheduler = VtVarStrukt.init(Var.tk1, "DVI35_PLAN01", DVI35_PLAN01, true, true, true);
		ParSets.ULFailParam = VtVarStrukt.init(Var.tk1, "ULFail", ulfail, true, true, true);
	}
	
	public int getVersion() {
		return Var.controller.dvi35Parameters.parameters[VersionIndex];
	}
	
	public int getStructure() {
		return Var.controller.dvi35Parameters.parameters[StructIndex];
	}
	
	public int getCycle() {
		return Var.controller.dvi35Parameters.parameters[CycleTimeIndex];
	}
	
	public int getOffset() {
		return Var.controller.dvi35Parameters.parameters[GreenWaveOffseIndex];
	}
	
	public int getDetectors(Node node, int detectorIndex) {
		if (node.isOffline() || !node.isRegularProgram())
			return 0;
		return getDetectors(detectorIndex);
	}
	
	private int getDetectors(int detectorIndex) {
		return Var.controller.dvi35Parameters.parameters[StartOfDetectorsIndex + detectorIndex];
	}
}
