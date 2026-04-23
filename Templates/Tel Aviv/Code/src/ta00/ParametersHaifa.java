package ta172;
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
import vtvar.VtVarStrukt;

/**
 * @author Ilia Butvinnik
 * @version 0.0.2
 * @since 01/04/2015
 */
public class ParametersHaifa extends Dvi35ParametersBase {
	
	static final int[] parameters_indexes_stucture_1 = {  1,   2,   3,   4,   5,   6, 7,   8,     9,  10,  11,  12,  21,  22,   23,  24,  25 ,26 , 27,  99, 100};
	static int[]       DVI35_P09                 = {   0,  0, 160,     1,   1,  2,  0,   1,   3,   1,   1,  160,  2,  30 , 19,   13,  13, 15,  160,   1,   1};
	static int[]       DVI35_P10                 = {   0,  0, 130,     1,   1,  2,  0,   1,   3,   1,   1,  130,  2,  10 , 10,   13,  16, 11,  130,   1,   1};
	static int[]       DVI35_P11                 = {   0 , 0, 180,     1,   0,  2,  0,   1,   3,   1,   1,  180,  2,  30,  17,   27,  28,  8 , 180,   1,   1};
	static int[]       DVI35_P12                 = {   0 , 0, 150,     1,   0,  2,  0,   1,   3,   1,   1,  150,  2,  10,   7,   27,  28,  8 , 150,   1,   1};
	static int[]       DVI35_P13                 = {   0 , 0, 150,     1,   0,  2,  0,   1,   3,   1,   1,  150,  2,  34,  19,    8,   9, 10 , 150,   1,   1};

	static final int[] parameters_indexes_stucture_2 = {1,   2,   3,    4,   5,  6,  7,   8,   9,  10,  11,  12, 13,  14,  15,  16,     21,  22,23,  24,  25, 26,  27, 28,  29, 30,  31, 66, 67,68, 69 ,70, 71, 72,73, 74, 75,81, 82, 84,85, 87, 88, 89,  90, 93, 94,  99, 100};
	static int[]       DVI35_P01                     = {0 , 120, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,   0,62,   2,   4,  1,   3, 78,   3,  1,  80,254, 10,15,254 ,10,  1,  2, 3,  1,  2, 0,  0,  2,16, 67, 29, 72,  22,  1,  0,   1,   1};
	static int[]       DVI35_P02                     = {0 , 150, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,   4,62,   8,   7,  1,   5, 87,   8,  5, 100,254, 22,21,254 ,14,  1,  2, 3,  1,  2, 0,  0,  2,18, 73, 34, 75,  22,  1,  0,   1,   1};
	static int[]       DVI35_P03                     = {0 , 195, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  17,62,  10,  10,  2,  16, 87,  10,  7, 130,254, 20,27,254 ,20,  1,  2, 3,  1,  2, 0,  0,  2,34, 75, 35, 73,  22,  1,  0,   1,   1};
	static int[]       DVI35_P04                     = {0 , 195, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  16,62,  22,   8,  1,   7, 87,   9,  6, 130,254, 42,24,254 ,20,  1,  2, 3,  1,  2, 0,  0,  2,26, 75, 34, 22,  22,  1,  1,   1,   1};
	static int[]       DVI35_P05                     = {0 , 195, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  16,62,  22,   8,  1,   7, 87,   9,  6, 130,254, 42,24,254 ,20,  1,  2, 3,  1,  2, 0,  0,  2,26, 75, 34, 75,  22,  1,  0,   1,   1};
	static int[]       DVI35_P06                     = {0 , 180, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,   0,62,  11,  12,  4,   7, 87,  22, 19, 120,254, 26,36,254 ,42,  1,  2, 3,  1,  2, 0,  0,  2,24, 73, 34, 75,  22,  1,  0,   1,   1};	
	static int[]       DVI35_P07                     = {0 , 180, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,   0,62,  21,  12,  4,   8, 87,  11,  8, 120,254, 42,36,254 ,24,  1,  2, 3,  1,  2, 0,  0,  2,26, 73, 34, 75,  22,  1,  0,   1,   1};
	static int[]       DVI35_P08                     = {0 , 180, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,   0,62,  23,  11,  3,   6, 87,  12,  9, 120,254, 46,33,254 ,26,  1,  2, 3,  1,  2, 0,  0,  2,22, 73, 34, 75,  22,  1,  0,   1,   1};
	static int[]       DVI35_P14                     = {0 , 150, 130,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,   0,62,   6,  12,  4,   7, 87,   7,  4, 100,254, 16,36,254 ,24,  1,  2, 3,  1,  2, 0,  0,  2,16, 73, 34, 75,  22,  1,  0,   1,   1};
	static int[]       DVI35_P15                     = {0 , 210, 140,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  16,72,  32,   8,  1,   7, 97,   9,  6, 140,254, 37,24,254 ,20,  1,  1, 3,  1,  2, 0,  0,  2,26, 75, 35, 75,  22,  1,  0,   1,   1};	                                                                                                                                                                                                                                                                                                                                          	                                                                                                                                                                                                                                                                                                                                          
	static int[]       DVI35_P16                     = {0 , 210, 140,   2,   0,  6,  9,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  30,72,  18,   8,  1,   7, 97,   9,  6, 140,254, 36,24,254 ,20,  1,  2, 3,  1,  2, 0,  0,  2,26, 75, 35, 75,  22,  1,  0,   1,   1};
	static int[]       DVI35_P17                     = {0 , 210, 140,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  16,72,  22,  18, 11,   7, 97,   9,  6, 140,254, 42,21,254 ,20,  1,  2, 1,  1,  2, 0,  0,  2,26, 75, 35, 75,  22,  1,  0,   1,   1};
	static int[]       DVI35_P18                     = {0 , 210, 140,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  17,72,  10,  10,  2,  16, 97,  20, 17, 140,254, 20,27,254 ,20,  1,  2, 3,  1,  1, 0,  0,  2,34, 75, 35, 73,  22,  1,  0,   1,   1};
	static int[]       DVI35_P19                     = {0 , 210, 140,   2,   0,  6,  0,   1,   1,   3,   1,   3,  1,   2,   1,   1,      6,  17,72,  10,  10,  2,  26, 97,  10,  7, 140,254, 20,27,254 ,20,  1,  2, 3,  1,  2, 0,  0,  1,34, 75, 35, 75,  22,  1,  0,   1,   1};

	static int[] parameters_indexes_det = {  1,    2  ,   3   ,   4  ,  5 ,  6  ,  7  , 8   };
	static int[] DVI35_DET              = { 28,    0  ,  23   ,  23  ,  28 , 0  ,  23 , 23  };
	//   static int[] DVI35_DET            = {  0,    0  ,   0   ,   0  ,  0 ,  0  ,  0  , 0   };

	//							     1   2      3   4    5    6    7     8     9    10   11     12    13    14     15     16     17      18     19 
	static int[] checkPulseTime = {  61, 61,   61,  61, 61,  61,  61,   61,  159, 129 , 179 , 149 , 149 ,  61 ,   61 , 61 ,  61  ,  61,   61};

	static int[] ulfail    = new int[] {  20 };

	public ParametersHaifa() {
		super(200);
	}
	
	public void SetParameters()
	{
		ParSets.pars_P01 = VtVarStrukt.init(Var.tk1, "DVI35_P01", initializeParameters(DVI35_P01, parameters_indexes_stucture_2, new int[] {  1 }), true, true, true);
		ParSets.pars_P02 = VtVarStrukt.init(Var.tk1, "DVI35_P02", initializeParameters(DVI35_P02, parameters_indexes_stucture_2, new int[] {  2 }), true, true, true);
		ParSets.pars_P03 = VtVarStrukt.init(Var.tk1, "DVI35_P03", initializeParameters(DVI35_P03, parameters_indexes_stucture_2, new int[] {  3 }), true, true, true);
		ParSets.pars_P04 = VtVarStrukt.init(Var.tk1, "DVI35_P04", initializeParameters(DVI35_P04, parameters_indexes_stucture_2, new int[] {  4 }), true, true, true);
		ParSets.pars_P05 = VtVarStrukt.init(Var.tk1, "DVI35_P05", initializeParameters(DVI35_P05, parameters_indexes_stucture_2, new int[] {  5 }), true, true, true);
		ParSets.pars_P06 = VtVarStrukt.init(Var.tk1, "DVI35_P06", initializeParameters(DVI35_P06, parameters_indexes_stucture_2, new int[] {  6 }), true, true, true);	    
		ParSets.pars_P07 = VtVarStrukt.init(Var.tk1, "DVI35_P07", initializeParameters(DVI35_P07, parameters_indexes_stucture_2, new int[] {  7 }), true, true, true);
		ParSets.pars_P08 = VtVarStrukt.init(Var.tk1, "DVI35_P08", initializeParameters(DVI35_P08, parameters_indexes_stucture_2, new int[] {  8 }), true, true, true);       	    	    
		ParSets.pars_P09 = VtVarStrukt.init(Var.tk1, "DVI35_P09", initializeParameters(DVI35_P09, parameters_indexes_stucture_1, new int[] {  9 }), true, true, true);
		ParSets.pars_P10 = VtVarStrukt.init(Var.tk1, "DVI35_P10", initializeParameters(DVI35_P10, parameters_indexes_stucture_1, new int[] { 10 }), true, true, true);	    
		ParSets.pars_P11 = VtVarStrukt.init(Var.tk1, "DVI35_P11", initializeParameters(DVI35_P11, parameters_indexes_stucture_1, new int[] { 11 }), true, true, true);
		ParSets.pars_P12 = VtVarStrukt.init(Var.tk1, "DVI35_P12", initializeParameters(DVI35_P12, parameters_indexes_stucture_1, new int[] { 12 }), true, true, true);
		ParSets.pars_P13 = VtVarStrukt.init(Var.tk1, "DVI35_P13", initializeParameters(DVI35_P13, parameters_indexes_stucture_1, new int[] { 13 }), true, true, true);
		ParSets.pars_P14 = VtVarStrukt.init(Var.tk1, "DVI35_P14", initializeParameters(DVI35_P14, parameters_indexes_stucture_2, new int[] { 14 }), true, true, true);
		ParSets.pars_P15 = VtVarStrukt.init(Var.tk1, "DVI35_P15", initializeParameters(DVI35_P15, parameters_indexes_stucture_2, new int[] { 15 }), true, true, true);	    
		ParSets.pars_P16 = VtVarStrukt.init(Var.tk1, "DVI35_P16", initializeParameters(DVI35_P16, parameters_indexes_stucture_2, new int[] { 16 }), true, true, true);
		ParSets.pars_P17 = VtVarStrukt.init(Var.tk1, "DVI35_P17", initializeParameters(DVI35_P17, parameters_indexes_stucture_2, new int[] { 17 }), true, true, true);
		ParSets.pars_P18 = VtVarStrukt.init(Var.tk1, "DVI35_P18", initializeParameters(DVI35_P18, parameters_indexes_stucture_2, new int[] { 18 }), true, true, true);
		ParSets.pars_P19 = VtVarStrukt.init(Var.tk1, "DVI35_P19", initializeParameters(DVI35_P19, parameters_indexes_stucture_2, new int[] { 19 }), true, true, true);    		     

		ParSets.pars_DET = VtVarStrukt.init(Var.tk1, "DVI35_DET", initializeParameters(DVI35_DET, parameters_indexes_det, new int[] { 20 }), true, true, true);
		ParSets.checkPulse = VtVarStrukt.init(Var.tk1, "CheckPulse", checkPulseTime, true, true, true);
		ParSets.ULFailParam = VtVarStrukt.init(Var.tk1, "ULFail", ulfail, true, true, true);
	}
}
