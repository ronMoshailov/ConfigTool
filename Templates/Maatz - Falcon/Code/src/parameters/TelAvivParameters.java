package parameters;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 239                                                                     	*
 *  Junction Name  : Varburg / Rains / HaTkuma Yahiam - Maavar Hazaya							*
 *  Equipmentno.   : h239                                                                    	*
 *                                                                                              *
 ************************************************************************************************/
import core.parameters.Parameters;
import core.parameters.ParametersIndex;
import m0547.Var;
import vtvar.VtVarStrukt;

/**
 * @author Ilia Butvinnik
 * @version 0.0.2
 * @since 01/04/2015
 */
public class TelAvivParameters extends Parameters {
    
    public static final int TYPE_DURATION   = 0;
    public static final int TYPE_ABSOLUTE   = 1;

    public TelAvivParameters() {
        super(200);
        
        setIncludesPhLen(false);
    }

    public ParametersIndex PARAM_MIN_START  = new ParametersIndex( 51);
    public ParametersIndex PARAM_MAX_START  = new ParametersIndex( 66);
    public ParametersIndex PARAM_TYPE_START = new ParametersIndex( 81); 
    public ParametersIndex PARAM_STRUCTURE  = new ParametersIndex( 96);
    public ParametersIndex PARAM_CYCLE      = new ParametersIndex( 97);
    public ParametersIndex PARAM_RES1       = new ParametersIndex( 98);
    public ParametersIndex PARAM_RES2       = new ParametersIndex( 99);
    public ParametersIndex PARAM_SET3       = new ParametersIndex(100);
    
    public ParametersIndex PARAM_OFFSET     = PARAM_MAX_START;

    private int min = PARAM_MIN_START .getIndex();
    private int max = PARAM_MAX_START .getIndex();
    private int typ = PARAM_TYPE_START.getIndex();
    
    //                                           51    52    53      66    67    68      81    82    83      96    97
    private final int[] parameters_indexes = {min++,min++,min++,  max++,max++,max++,  typ++,typ++,typ++,     96,   97 };
    //                                          MIN                 MAX                TYPE                 STR CYCLE
    //                                            A   EQA     B       A   EQA     B       A   EQA     B         
    //                                           SY   FO1   FO2      SY   FO1   FO2      SY   FO1   FO2         
    static int[]        DVI35_P01          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; // 1 - ACTIVE   
    static int[]        DVI35_P02          = {    0,    0,    0,      6,   10,    0,      1,    1,    0,     21,   80 }; // 2 - ACTIVE   
    static int[]        DVI35_P03          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; // 3 - P01   
    static int[]        DVI35_P04          = {    0,    0,    0,      6,   10,    2,      1,    1,    0,     21,   80 }; // 4 - ACTIVE   
    static int[]        DVI35_P05          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; // 5 - P01   
    static int[]        DVI35_P06          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; // 6 - P01   
    static int[]        DVI35_P07          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; // 7 - P01   
    static int[]        DVI35_P08          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,   90 }; // 8 - ACTIVE    
    static int[]        DVI35_P09          = {    0,    0,    0,      6,   10,    2,      1,    1,    0,     21,   90 }; // 9 - ACTIVE   
    static int[]        DVI35_P10          = {    0,    0,    0,      6,   10,    0,      1,    1,    0,     21,   90 }; //10 - ACTIVE   
    static int[]        DVI35_P11          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //11 - P01   
    static int[]        DVI35_P12          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //12 - P01
    static int[]        DVI35_P13          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //13 - P01
    static int[]        DVI35_P14          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //14 - P01
    static int[]        DVI35_P15          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //15 - P01
    static int[]        DVI35_P16          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //16 - P01
    static int[]        DVI35_P17          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //17 - P01
    static int[]        DVI35_P18          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //18 - P01
    static int[]        DVI35_P19          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //19 - P01                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    static int[]        DVI35_P20          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //20 - P01
    static int[]        DVI35_P21          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //21 - P01
    static int[]        DVI35_P22          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //22 - P01
    static int[]        DVI35_P23          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //23 - P01
    static int[]        DVI35_P24          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //24 - P01
    static int[]        DVI35_P25          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //25 - P01
    static int[]        DVI35_P26          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //26 - P01
    static int[]        DVI35_P27          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //27 - P01
    static int[]        DVI35_P28          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //28 - P01
    static int[]        DVI35_P29          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //29 - P01
    static int[]        DVI35_P30          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //30 - P01
    static int[]        DVI35_P31          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //31 - P01
    static int[]        DVI35_P32          = {    0,    0,    0,      6,   10,    4,      1,    1,    0,     21,  100 }; //32 - P01

	static int[] parameters_indexes_det              = {   1,   2,   3,   4,   5,   6,   7,   8};
	static int[] DVI35_DET                           = {  28,   0,  23,  23,  28,   0,  23,  23};

    public int getMinType(int value) {
        return value;
    }
    
    public int getMaxType(int value) {
        return value;
    }
    
	public int getCycle() {
        return parameters[PARAM_CYCLE.getIndex() - 1];
	}
	
    public int getCycle(int nodeId) {
        return getCycle();
    }
    
    public int getStructure() {
        return parameters[PARAM_STRUCTURE.getIndex() - 1];
    }
    
    public int getStructure(int nodeId) {
        return getStructure();
    }
    
    public int getOffset() {
        return parameters[PARAM_OFFSET.getIndex() - 1];
    }
    
    public int getOffset(int nodeId) {
        return getOffset();
    }

    public boolean isGWEnable(int programNumber) {
        return true;
    }

    public boolean isGWEnable(int nodeId, int programNumber) {
        return true;
    }
    
    public boolean isMaster(int programNumber) {
        return false;
    }
    
    public boolean isMaster(int nodeId, int programNumber) {
        return false;
    }
    
    public boolean isSlave(int programNumber) {
        return false;
    }
    
    public boolean isSlave(int nodeId, int programNumber) {
        return false;
    }
    
    public boolean isClockSync(int programNumber) {
        return true;
    }
    
    public boolean isClockSync(int nodeId, int programNumber) {
        return true;
    }
    
    public boolean isSubmaster(int programNumber) {
        return false;
    }
    
    public boolean isSubmaster(int nodeId, int programNumber) {
        return false;
    }
    
    public boolean isLocalSubmaster(int programNumber) {
        return false;
    }
    
    public boolean isLocalSubmaster(int nodeId, int programNumber) {
        return false;
    }

    public boolean isSetDemandDetector(int nodeId, int parametersId) {
        return false;
    }
    
    public boolean isSetExtensionDetector(int nodeId, int parametersId) {
        return false;
    }
    
    public boolean isDisabledDetector(int nodeId, int parametersId) {
        return false;
    }
	
	public void initializeParameters()
	{
        pars_P01 = VtVarStrukt.init(Var.tk1, "DVI35_P01", initializeParameters(DVI35_P01, parameters_indexes, new int[] {  1, 51 }), true, true, true);
        pars_P02 = VtVarStrukt.init(Var.tk1, "DVI35_P02", initializeParameters(DVI35_P02, parameters_indexes, new int[] {  2, 52 }), true, true, true);
        pars_P03 = VtVarStrukt.init(Var.tk1, "DVI35_P03", initializeParameters(DVI35_P03, parameters_indexes, new int[] {  3, 53 }), true, true, true);
        pars_P04 = VtVarStrukt.init(Var.tk1, "DVI35_P04", initializeParameters(DVI35_P04, parameters_indexes, new int[] {  4, 54 }), true, true, true);
        pars_P05 = VtVarStrukt.init(Var.tk1, "DVI35_P05", initializeParameters(DVI35_P05, parameters_indexes, new int[] {  5, 55 }), true, true, true);
        pars_P06 = VtVarStrukt.init(Var.tk1, "DVI35_P06", initializeParameters(DVI35_P06, parameters_indexes, new int[] {  6, 56 }), true, true, true);      
        pars_P07 = VtVarStrukt.init(Var.tk1, "DVI35_P07", initializeParameters(DVI35_P07, parameters_indexes, new int[] {  7, 57 }), true, true, true);
        pars_P08 = VtVarStrukt.init(Var.tk1, "DVI35_P08", initializeParameters(DVI35_P08, parameters_indexes, new int[] {  8, 58 }), true, true, true);                      
        pars_P09 = VtVarStrukt.init(Var.tk1, "DVI35_P09", initializeParameters(DVI35_P09, parameters_indexes, new int[] {  9, 59 }), true, true, true);
        pars_P10 = VtVarStrukt.init(Var.tk1, "DVI35_P10", initializeParameters(DVI35_P10, parameters_indexes, new int[] { 10, 60 }), true, true, true);      
        pars_P11 = VtVarStrukt.init(Var.tk1, "DVI35_P11", initializeParameters(DVI35_P11, parameters_indexes, new int[] { 11, 61 }), true, true, true);
        pars_P12 = VtVarStrukt.init(Var.tk1, "DVI35_P12", initializeParameters(DVI35_P12, parameters_indexes, new int[] { 12, 62 }), true, true, true);
        pars_P13 = VtVarStrukt.init(Var.tk1, "DVI35_P13", initializeParameters(DVI35_P13, parameters_indexes, new int[] { 13, 63 }), true, true, true);
        pars_P14 = VtVarStrukt.init(Var.tk1, "DVI35_P14", initializeParameters(DVI35_P14, parameters_indexes, new int[] { 14, 64 }), true, true, true);
        pars_P15 = VtVarStrukt.init(Var.tk1, "DVI35_P15", initializeParameters(DVI35_P15, parameters_indexes, new int[] { 15, 65 }), true, true, true);      
        pars_P16 = VtVarStrukt.init(Var.tk1, "DVI35_P16", initializeParameters(DVI35_P16, parameters_indexes, new int[] { 16, 66 }), true, true, true);      
        pars_P17 = VtVarStrukt.init(Var.tk1, "DVI35_P17", initializeParameters(DVI35_P17, parameters_indexes, new int[] { 17, 67 }), true, true, true);      
        pars_P18 = VtVarStrukt.init(Var.tk1, "DVI35_P18", initializeParameters(DVI35_P18, parameters_indexes, new int[] { 18, 68 }), true, true, true);      
        pars_P19 = VtVarStrukt.init(Var.tk1, "DVI35_P19", initializeParameters(DVI35_P19, parameters_indexes, new int[] { 19, 69 }), true, true, true);      
        pars_P20 = VtVarStrukt.init(Var.tk1, "DVI35_P20", initializeParameters(DVI35_P20, parameters_indexes, new int[] { 20, 70 }), true, true, true);      
        pars_P21 = VtVarStrukt.init(Var.tk1, "DVI35_P21", initializeParameters(DVI35_P21, parameters_indexes, new int[] { 21, 71 }), true, true, true);      
        pars_P22 = VtVarStrukt.init(Var.tk1, "DVI35_P22", initializeParameters(DVI35_P22, parameters_indexes, new int[] { 22, 72 }), true, true, true);      
        pars_P23 = VtVarStrukt.init(Var.tk1, "DVI35_P23", initializeParameters(DVI35_P23, parameters_indexes, new int[] { 23, 73 }), true, true, true);      
        pars_P24 = VtVarStrukt.init(Var.tk1, "DVI35_P24", initializeParameters(DVI35_P24, parameters_indexes, new int[] { 24, 74 }), true, true, true);      
        pars_P25 = VtVarStrukt.init(Var.tk1, "DVI35_P25", initializeParameters(DVI35_P25, parameters_indexes, new int[] { 25, 75 }), true, true, true);      
        pars_P26 = VtVarStrukt.init(Var.tk1, "DVI35_P26", initializeParameters(DVI35_P26, parameters_indexes, new int[] { 26, 76 }), true, true, true);      
        pars_P27 = VtVarStrukt.init(Var.tk1, "DVI35_P27", initializeParameters(DVI35_P27, parameters_indexes, new int[] { 27, 77 }), true, true, true);      
        pars_P28 = VtVarStrukt.init(Var.tk1, "DVI35_P28", initializeParameters(DVI35_P28, parameters_indexes, new int[] { 28, 78 }), true, true, true);      
        pars_P29 = VtVarStrukt.init(Var.tk1, "DVI35_P29", initializeParameters(DVI35_P29, parameters_indexes, new int[] { 29, 79 }), true, true, true);      
        pars_P30 = VtVarStrukt.init(Var.tk1, "DVI35_P30", initializeParameters(DVI35_P30, parameters_indexes, new int[] { 30, 80 }), true, true, true);      
        pars_P31 = VtVarStrukt.init(Var.tk1, "DVI35_P31", initializeParameters(DVI35_P31, parameters_indexes, new int[] { 31, 81 }), true, true, true);      
        pars_P32 = VtVarStrukt.init(Var.tk1, "DVI35_P32", initializeParameters(DVI35_P32, parameters_indexes, new int[] { 32, 82 }), true, true, true);
	}

    public boolean isDuration(int type) {
        return TYPE_DURATION == type;
    }

    public boolean isAbsolute(int type) {
        return TYPE_ABSOLUTE == type;
    }

    public boolean isComplement(int type) {
        return false;
    }
    
    public int isByMove(int type) {
        return 0;
    }

    /**
     * Returns the index of the Dx Enabled/Active parameter if exists and Var.NONE otherwise
     */
    public int getDxActive() {
        // TODO: update index if Dx is required
        return Var.NONE;
    }

    public int getMaxBySP(int nodeId, int sp) {
        return 0;
    }

    public int getMinBySP(int nodeId, int sp) {
        return 0;
    }

    public int getMinTypeBySP(int nodeId, int sp) {
        return 0;
    }

    public int getMaxTypeBySP(int nodeId, int sp) {
        return 0;
    }
}
