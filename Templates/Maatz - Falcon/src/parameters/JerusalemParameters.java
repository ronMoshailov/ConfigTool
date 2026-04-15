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
public class JerusalemParameters extends Parameters {
    
    public static final int TYPE_DURATION   = 0;
    public static final int TYPE_ABSOLUTE   = 1;
    public static final int TYPE_COMPLEMENT = 2;

    public static final int DETECTOR_DISABLED = 0;
    public static final int DETECTOR_SET      = 1;
    public static final int DETECTOR_AUTO     = 2;
    
    public static final int DETECTORS_MAX_QUANTITY = 64;
    
    public JerusalemParameters() {
        super(200);
        parametersDetectors = new int[DETECTORS_MAX_QUANTITY];
        setIncludesPhLen(true);
    }

    public ParametersIndex PARAM_DESIGNVER        = new ParametersIndex(  1);
    public ParametersIndex PARAM_PARAMVER         = new ParametersIndex(  2);
    public ParametersIndex PARAM_CYCLETIME        = new ParametersIndex(  3);
    public ParametersIndex PARAM_GREENWAVE_OFFSET = new ParametersIndex(  4);
    public ParametersIndex PARAM_STRUCT           = new ParametersIndex(  5);
    
    public ParametersIndex PARAM_FIRST_DETECTOR   = new ParametersIndex( 15);
                                           
	static final int[] parameters_indexes_stucture_1 = {  1,   2,   3,   4,   5,   6,   7,   8,   9,  10,  11,  12,  21,  22,  23,  24,  25,  26,  27,  99, 100};
    static int[]       DVI35_P01                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P02                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P03                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P04                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P05                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P06                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P07                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P08                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P09                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P10                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P11                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P12                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P13                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P14                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P15                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P16                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P17                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P18                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P19                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P20                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P21                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P22                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P23                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P24                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P25                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P26                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P27                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P28                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P29                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P30                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P31                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
    static int[]       DVI35_P32                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};

    static int[]       INNER_P01                     = {  0};
    static int[]       INNER_P02                     = {  0};
    static int[]       INNER_P03                     = {  0};
    static int[]       INNER_P04                     = {  0};
    static int[]       INNER_P05                     = {  0};
    static int[]       INNER_P06                     = {  0};
    static int[]       INNER_P07                     = {  0};
    static int[]       INNER_P08                     = {  0};
    static int[]       INNER_P09                     = {  0};
    static int[]       INNER_P10                     = {  0};
    static int[]       INNER_P11                     = {  0};
    static int[]       INNER_P12                     = {  0};
    static int[]       INNER_P13                     = {  0};
    static int[]       INNER_P14                     = {  0};
    static int[]       INNER_P15                     = {  0};
    static int[]       INNER_P16                     = {  0};
    static int[]       INNER_P17                     = {  0};
    static int[]       INNER_P18                     = {  0};
    static int[]       INNER_P19                     = {  0};
    static int[]       INNER_P20                     = {  0};
    static int[]       INNER_P21                     = {  0};
    static int[]       INNER_P22                     = {  0};
    static int[]       INNER_P23                     = {  0};
    static int[]       INNER_P24                     = {  0};
    static int[]       INNER_P25                     = {  0};
    static int[]       INNER_P26                     = {  0};
    static int[]       INNER_P27                     = {  0};
    static int[]       INNER_P28                     = {  0};
    static int[]       INNER_P29                     = {  0};
    static int[]       INNER_P30                     = {  0};
    static int[]       INNER_P31                     = {  0};
    static int[]       INNER_P32                     = {  0};
    
    // Gap / Jam units. Counted in tenth of a second (e.g. a gap of 2.3s would be set as 23)
    static int[]      DVI35_GAP01  /*E1 */ = {   28 };
    static int[]      DVI35_GAP02  /*E2 */ = {   23 };
    static int[]      DVI35_GAP03  /*D3 */ = {    0 };
    static int[]      DVI35_GAP04  /*E3 */ = {   18 };
    static int[]      DVI35_GAP05  /*D4 */ = {    0 };
    static int[]      DVI35_GAP06  /*E4 */ = {   15 };
    static int[]      DVI35_GAP07  /*Pa */ = {    0 };
    static int[]      DVI35_GAP08  /*Pb */ = {    0 };
    static int[]      DVI35_GAP09  /*Pc */ = {    0 };
    static int[]      DVI35_GAP10  /*Pcd*/ = {    0 };
    static int[]      DVI35_GAP11  /*Pd */ = {    0 };
    static int[]      DVI35_GAP12  /*Pdc*/ = {    0 };
    static int[]      DVI35_GAP13  /*   */ = {    0 };
    static int[]      DVI35_GAP14  /*   */ = {    0 };
    static int[]      DVI35_GAP15  /*   */ = {    0 };
    static int[]      DVI35_GAP16  /*   */ = {    0 };
    static int[]      DVI35_GAP17  /*   */ = {    0 };
    static int[]      DVI35_GAP18  /*   */ = {    0 };
    static int[]      DVI35_GAP19  /*   */ = {    0 };
    static int[]      DVI35_GAP20  /*   */ = {    0 };
    static int[]      DVI35_GAP21  /*   */ = {    0 };
    static int[]      DVI35_GAP22  /*   */ = {    0 };
    static int[]      DVI35_GAP23  /*   */ = {    0 };
    static int[]      DVI35_GAP24  /*   */ = {    0 };
    static int[]      DVI35_GAP25  /*   */ = {    0 };
    static int[]      DVI35_GAP26  /*   */ = {    0 };
    static int[]      DVI35_GAP27  /*   */ = {    0 };
    static int[]      DVI35_GAP28  /*   */ = {    0 };
    static int[]      DVI35_GAP29  /*   */ = {    0 };
    static int[]      DVI35_GAP30  /*   */ = {    0 };
    static int[]      DVI35_GAP31  /*   */ = {    0 };
    static int[]      DVI35_GAP32  /*   */ = {    0 };
    static int[]      DVI35_GAP33  /*   */ = {    0 };
    static int[]      DVI35_GAP34  /*   */ = {    0 };
    static int[]      DVI35_GAP35  /*   */ = {    0 };
    static int[]      DVI35_GAP36  /*   */ = {    0 };
    static int[]      DVI35_GAP37  /*   */ = {    0 };
    static int[]      DVI35_GAP38  /*   */ = {    0 };
    static int[]      DVI35_GAP39  /*   */ = {    0 };
    static int[]      DVI35_GAP40  /*   */ = {    0 };
    static int[]      DVI35_GAP41  /*   */ = {    0 };
    static int[]      DVI35_GAP42  /*   */ = {    0 };
    static int[]      DVI35_GAP43  /*   */ = {    0 };
    static int[]      DVI35_GAP44  /*   */ = {    0 };
    static int[]      DVI35_GAP45  /*   */ = {    0 };
    static int[]      DVI35_GAP46  /*   */ = {    0 };
    static int[]      DVI35_GAP47  /*   */ = {    0 };
    static int[]      DVI35_GAP48  /*   */ = {    0 };
    static int[]      DVI35_GAP49  /*   */ = {    0 };
    static int[]      DVI35_GAP50  /*   */ = {    0 };
    static int[]      DVI35_GAP51  /*   */ = {    0 };
    static int[]      DVI35_GAP52  /*   */ = {    0 };
    static int[]      DVI35_GAP53  /*   */ = {    0 };
    static int[]      DVI35_GAP54  /*   */ = {    0 };
    static int[]      DVI35_GAP55  /*   */ = {    0 };
    static int[]      DVI35_GAP56  /*   */ = {    0 };
    static int[]      DVI35_GAP57  /*   */ = {    0 };
    static int[]      DVI35_GAP58  /*   */ = {    0 };
    static int[]      DVI35_GAP59  /*   */ = {    0 };
    static int[]      DVI35_GAP60  /*   */ = {    0 };
    static int[]      DVI35_GAP61  /*   */ = {    0 };
    static int[]      DVI35_GAP62  /*   */ = {    0 };
    static int[]      DVI35_GAP63  /*   */ = {    0 };
    static int[]      DVI35_GAP64  /*   */ = {    0 };

    public int getMinType(int value) {
        return value;
    }
    public int getMaxType(int value) {
        return value;
    }
    
	public int getCycle() {
        return parameters[PARAM_CYCLETIME.getIndex() - 1];
	}
	
    public int getCycle(int nodeId) {
        return getCycle();
    }
    
    public int getStructure() {
        return parameters[PARAM_STRUCT.getIndex() - 1];
    }
    
    public int getStructure(int nodeId) {
        return getStructure();
    }
    
    public int getOffset() {
        return parameters[PARAM_GREENWAVE_OFFSET.getIndex() - 1];
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
        return parameters[PARAM_FIRST_DETECTOR.getIndex() + parametersId - 1] == DETECTOR_SET;
    }
    
    public boolean isSetExtensionDetector(int nodeId, int parametersId) {
        return parameters[PARAM_FIRST_DETECTOR.getIndex() + parametersId - 1] == DETECTOR_SET;
    }
    
    public boolean isDisabledDetector(int nodeId, int parametersId) {
        return parameters[PARAM_FIRST_DETECTOR.getIndex() + parametersId - 1] == DETECTOR_DISABLED;
    }
	
	public void initializeParameters()
	{
        pars_P01 = VtVarStrukt.init(Var.tk1, "DVI35_P01", initializeParameters(DVI35_P01, parameters_indexes_stucture_1, new int[] {  1, 51 }), true, true, true);
        pars_P02 = VtVarStrukt.init(Var.tk1, "DVI35_P02", initializeParameters(DVI35_P02, parameters_indexes_stucture_1, new int[] {  2, 52 }), true, true, true);
        pars_P03 = VtVarStrukt.init(Var.tk1, "DVI35_P03", initializeParameters(DVI35_P03, parameters_indexes_stucture_1, new int[] {  3, 53 }), true, true, true);
        pars_P04 = VtVarStrukt.init(Var.tk1, "DVI35_P04", initializeParameters(DVI35_P04, parameters_indexes_stucture_1, new int[] {  4, 54 }), true, true, true);
        pars_P05 = VtVarStrukt.init(Var.tk1, "DVI35_P05", initializeParameters(DVI35_P05, parameters_indexes_stucture_1, new int[] {  5, 55 }), true, true, true);
        pars_P06 = VtVarStrukt.init(Var.tk1, "DVI35_P06", initializeParameters(DVI35_P06, parameters_indexes_stucture_1, new int[] {  6, 56 }), true, true, true);      
        pars_P07 = VtVarStrukt.init(Var.tk1, "DVI35_P07", initializeParameters(DVI35_P07, parameters_indexes_stucture_1, new int[] {  7, 57 }), true, true, true);
        pars_P08 = VtVarStrukt.init(Var.tk1, "DVI35_P08", initializeParameters(DVI35_P08, parameters_indexes_stucture_1, new int[] {  8, 58 }), true, true, true);                      
        pars_P09 = VtVarStrukt.init(Var.tk1, "DVI35_P09", initializeParameters(DVI35_P09, parameters_indexes_stucture_1, new int[] {  9, 59 }), true, true, true);
        pars_P10 = VtVarStrukt.init(Var.tk1, "DVI35_P10", initializeParameters(DVI35_P10, parameters_indexes_stucture_1, new int[] { 10, 60 }), true, true, true);      
        pars_P11 = VtVarStrukt.init(Var.tk1, "DVI35_P11", initializeParameters(DVI35_P11, parameters_indexes_stucture_1, new int[] { 11, 61 }), true, true, true);
        pars_P12 = VtVarStrukt.init(Var.tk1, "DVI35_P12", initializeParameters(DVI35_P12, parameters_indexes_stucture_1, new int[] { 12, 62 }), true, true, true);
        pars_P13 = VtVarStrukt.init(Var.tk1, "DVI35_P13", initializeParameters(DVI35_P13, parameters_indexes_stucture_1, new int[] { 13, 63 }), true, true, true);
        pars_P14 = VtVarStrukt.init(Var.tk1, "DVI35_P14", initializeParameters(DVI35_P14, parameters_indexes_stucture_1, new int[] { 14, 64 }), true, true, true);
        pars_P15 = VtVarStrukt.init(Var.tk1, "DVI35_P15", initializeParameters(DVI35_P15, parameters_indexes_stucture_1, new int[] { 15, 65 }), true, true, true);      
        pars_P16 = VtVarStrukt.init(Var.tk1, "DVI35_P16", initializeParameters(DVI35_P16, parameters_indexes_stucture_1, new int[] { 16, 66 }), true, true, true);      
        pars_P17 = VtVarStrukt.init(Var.tk1, "DVI35_P17", initializeParameters(DVI35_P17, parameters_indexes_stucture_1, new int[] { 17, 67 }), true, true, true);      
        pars_P18 = VtVarStrukt.init(Var.tk1, "DVI35_P18", initializeParameters(DVI35_P18, parameters_indexes_stucture_1, new int[] { 18, 68 }), true, true, true);      
        pars_P19 = VtVarStrukt.init(Var.tk1, "DVI35_P19", initializeParameters(DVI35_P19, parameters_indexes_stucture_1, new int[] { 19, 69 }), true, true, true);      
        pars_P20 = VtVarStrukt.init(Var.tk1, "DVI35_P20", initializeParameters(DVI35_P20, parameters_indexes_stucture_1, new int[] { 20, 70 }), true, true, true);      
        pars_P21 = VtVarStrukt.init(Var.tk1, "DVI35_P21", initializeParameters(DVI35_P21, parameters_indexes_stucture_1, new int[] { 21, 71 }), true, true, true);      
        pars_P22 = VtVarStrukt.init(Var.tk1, "DVI35_P22", initializeParameters(DVI35_P22, parameters_indexes_stucture_1, new int[] { 22, 72 }), true, true, true);      
        pars_P23 = VtVarStrukt.init(Var.tk1, "DVI35_P23", initializeParameters(DVI35_P23, parameters_indexes_stucture_1, new int[] { 23, 73 }), true, true, true);      
        pars_P24 = VtVarStrukt.init(Var.tk1, "DVI35_P24", initializeParameters(DVI35_P24, parameters_indexes_stucture_1, new int[] { 24, 74 }), true, true, true);      
        pars_P25 = VtVarStrukt.init(Var.tk1, "DVI35_P25", initializeParameters(DVI35_P25, parameters_indexes_stucture_1, new int[] { 25, 75 }), true, true, true);      
        pars_P26 = VtVarStrukt.init(Var.tk1, "DVI35_P26", initializeParameters(DVI35_P26, parameters_indexes_stucture_1, new int[] { 26, 76 }), true, true, true);      
        pars_P27 = VtVarStrukt.init(Var.tk1, "DVI35_P27", initializeParameters(DVI35_P27, parameters_indexes_stucture_1, new int[] { 27, 77 }), true, true, true);      
        pars_P28 = VtVarStrukt.init(Var.tk1, "DVI35_P28", initializeParameters(DVI35_P28, parameters_indexes_stucture_1, new int[] { 28, 78 }), true, true, true);      
        pars_P29 = VtVarStrukt.init(Var.tk1, "DVI35_P29", initializeParameters(DVI35_P29, parameters_indexes_stucture_1, new int[] { 29, 79 }), true, true, true);      
        pars_P30 = VtVarStrukt.init(Var.tk1, "DVI35_P30", initializeParameters(DVI35_P30, parameters_indexes_stucture_1, new int[] { 30, 80 }), true, true, true);      
        pars_P31 = VtVarStrukt.init(Var.tk1, "DVI35_P31", initializeParameters(DVI35_P31, parameters_indexes_stucture_1, new int[] { 31, 81 }), true, true, true);      
        pars_P32 = VtVarStrukt.init(Var.tk1, "DVI35_P32", initializeParameters(DVI35_P32, parameters_indexes_stucture_1, new int[] { 32, 82 }), true, true, true);
        
        inner_P01 = VtVarStrukt.init(Var.tk1, "INNER_P01", INNER_P01, true, true, true);
        inner_P02 = VtVarStrukt.init(Var.tk1, "INNER_P02", INNER_P02, true, true, true);
        inner_P03 = VtVarStrukt.init(Var.tk1, "INNER_P03", INNER_P03, true, true, true);
        inner_P04 = VtVarStrukt.init(Var.tk1, "INNER_P04", INNER_P04, true, true, true);
        inner_P05 = VtVarStrukt.init(Var.tk1, "INNER_P05", INNER_P05, true, true, true);
        inner_P06 = VtVarStrukt.init(Var.tk1, "INNER_P06", INNER_P06, true, true, true);        
        inner_P07 = VtVarStrukt.init(Var.tk1, "INNER_P07", INNER_P07, true, true, true);
        inner_P08 = VtVarStrukt.init(Var.tk1, "INNER_P08", INNER_P08, true, true, true);                    
        inner_P09 = VtVarStrukt.init(Var.tk1, "INNER_P09", INNER_P09, true, true, true);
        inner_P10 = VtVarStrukt.init(Var.tk1, "INNER_P10", INNER_P10, true, true, true);        
        inner_P11 = VtVarStrukt.init(Var.tk1, "INNER_P11", INNER_P11, true, true, true);
        inner_P12 = VtVarStrukt.init(Var.tk1, "INNER_P12", INNER_P12, true, true, true);
        inner_P13 = VtVarStrukt.init(Var.tk1, "INNER_P13", INNER_P13, true, true, true);
        inner_P14 = VtVarStrukt.init(Var.tk1, "INNER_P14", INNER_P14, true, true, true);
        inner_P15 = VtVarStrukt.init(Var.tk1, "INNER_P15", INNER_P15, true, true, true);        
        inner_P16 = VtVarStrukt.init(Var.tk1, "INNER_P16", INNER_P16, true, true, true);        
        inner_P17 = VtVarStrukt.init(Var.tk1, "INNER_P17", INNER_P17, true, true, true);        
        inner_P18 = VtVarStrukt.init(Var.tk1, "INNER_P18", INNER_P18, true, true, true);        
        inner_P19 = VtVarStrukt.init(Var.tk1, "INNER_P19", INNER_P19, true, true, true);        
        inner_P20 = VtVarStrukt.init(Var.tk1, "INNER_P20", INNER_P20, true, true, true);        
        inner_P21 = VtVarStrukt.init(Var.tk1, "INNER_P21", INNER_P21, true, true, true);        
        inner_P22 = VtVarStrukt.init(Var.tk1, "INNER_P22", INNER_P22, true, true, true);        
        inner_P23 = VtVarStrukt.init(Var.tk1, "INNER_P23", INNER_P23, true, true, true);        
        inner_P24 = VtVarStrukt.init(Var.tk1, "INNER_P24", INNER_P24, true, true, true);        
        inner_P25 = VtVarStrukt.init(Var.tk1, "INNER_P25", INNER_P25, true, true, true);        
        inner_P26 = VtVarStrukt.init(Var.tk1, "INNER_P26", INNER_P26, true, true, true);        
        inner_P27 = VtVarStrukt.init(Var.tk1, "INNER_P27", INNER_P27, true, true, true);        
        inner_P28 = VtVarStrukt.init(Var.tk1, "INNER_P28", INNER_P28, true, true, true);        
        inner_P29 = VtVarStrukt.init(Var.tk1, "INNER_P29", INNER_P29, true, true, true);        
        inner_P30 = VtVarStrukt.init(Var.tk1, "INNER_P30", INNER_P30, true, true, true);        
        inner_P31 = VtVarStrukt.init(Var.tk1, "INNER_P31", INNER_P31, true, true, true);        
        inner_P32 = VtVarStrukt.init(Var.tk1, "INNER_P32", INNER_P32, true, true, true);

        gap_Det01 = VtVarStrukt.init(Var.tk1, "DVI35_GAP01", initializeDetectorsParameters(DVI35_GAP01), true, true, true);
        gap_Det02 = VtVarStrukt.init(Var.tk1, "DVI35_GAP02", initializeDetectorsParameters(DVI35_GAP02), true, true, true);
        gap_Det03 = VtVarStrukt.init(Var.tk1, "DVI35_GAP03", initializeDetectorsParameters(DVI35_GAP03), true, true, true);
        gap_Det04 = VtVarStrukt.init(Var.tk1, "DVI35_GAP04", initializeDetectorsParameters(DVI35_GAP04), true, true, true);
        gap_Det05 = VtVarStrukt.init(Var.tk1, "DVI35_GAP05", initializeDetectorsParameters(DVI35_GAP05), true, true, true);
        gap_Det06 = VtVarStrukt.init(Var.tk1, "DVI35_GAP06", initializeDetectorsParameters(DVI35_GAP06), true, true, true);
        gap_Det07 = VtVarStrukt.init(Var.tk1, "DVI35_GAP07", initializeDetectorsParameters(DVI35_GAP07), true, true, true);
        gap_Det08 = VtVarStrukt.init(Var.tk1, "DVI35_GAP08", initializeDetectorsParameters(DVI35_GAP08), true, true, true);
        gap_Det09 = VtVarStrukt.init(Var.tk1, "DVI35_GAP09", initializeDetectorsParameters(DVI35_GAP09), true, true, true);
        gap_Det10 = VtVarStrukt.init(Var.tk1, "DVI35_GAP10", initializeDetectorsParameters(DVI35_GAP10), true, true, true);
        gap_Det11 = VtVarStrukt.init(Var.tk1, "DVI35_GAP11", initializeDetectorsParameters(DVI35_GAP11), true, true, true);
        gap_Det12 = VtVarStrukt.init(Var.tk1, "DVI35_GAP12", initializeDetectorsParameters(DVI35_GAP12), true, true, true);
        gap_Det13 = VtVarStrukt.init(Var.tk1, "DVI35_GAP13", initializeDetectorsParameters(DVI35_GAP13), true, true, true);
        gap_Det14 = VtVarStrukt.init(Var.tk1, "DVI35_GAP14", initializeDetectorsParameters(DVI35_GAP14), true, true, true);
        gap_Det15 = VtVarStrukt.init(Var.tk1, "DVI35_GAP15", initializeDetectorsParameters(DVI35_GAP15), true, true, true);
        gap_Det16 = VtVarStrukt.init(Var.tk1, "DVI35_GAP16", initializeDetectorsParameters(DVI35_GAP16), true, true, true);
        gap_Det17 = VtVarStrukt.init(Var.tk1, "DVI35_GAP17", initializeDetectorsParameters(DVI35_GAP17), true, true, true);
        gap_Det18 = VtVarStrukt.init(Var.tk1, "DVI35_GAP18", initializeDetectorsParameters(DVI35_GAP18), true, true, true);
        gap_Det19 = VtVarStrukt.init(Var.tk1, "DVI35_GAP19", initializeDetectorsParameters(DVI35_GAP19), true, true, true);
        gap_Det20 = VtVarStrukt.init(Var.tk1, "DVI35_GAP20", initializeDetectorsParameters(DVI35_GAP20), true, true, true);
        gap_Det21 = VtVarStrukt.init(Var.tk1, "DVI35_GAP21", initializeDetectorsParameters(DVI35_GAP21), true, true, true);
        gap_Det22 = VtVarStrukt.init(Var.tk1, "DVI35_GAP22", initializeDetectorsParameters(DVI35_GAP22), true, true, true);
        gap_Det23 = VtVarStrukt.init(Var.tk1, "DVI35_GAP23", initializeDetectorsParameters(DVI35_GAP23), true, true, true);
        gap_Det24 = VtVarStrukt.init(Var.tk1, "DVI35_GAP24", initializeDetectorsParameters(DVI35_GAP24), true, true, true);
        gap_Det25 = VtVarStrukt.init(Var.tk1, "DVI35_GAP25", initializeDetectorsParameters(DVI35_GAP25), true, true, true);
        gap_Det26 = VtVarStrukt.init(Var.tk1, "DVI35_GAP26", initializeDetectorsParameters(DVI35_GAP26), true, true, true);
        gap_Det27 = VtVarStrukt.init(Var.tk1, "DVI35_GAP27", initializeDetectorsParameters(DVI35_GAP27), true, true, true);
        gap_Det28 = VtVarStrukt.init(Var.tk1, "DVI35_GAP28", initializeDetectorsParameters(DVI35_GAP28), true, true, true);
        gap_Det29 = VtVarStrukt.init(Var.tk1, "DVI35_GAP29", initializeDetectorsParameters(DVI35_GAP29), true, true, true);
        gap_Det30 = VtVarStrukt.init(Var.tk1, "DVI35_GAP30", initializeDetectorsParameters(DVI35_GAP30), true, true, true);
        gap_Det31 = VtVarStrukt.init(Var.tk1, "DVI35_GAP31", initializeDetectorsParameters(DVI35_GAP31), true, true, true);
        gap_Det32 = VtVarStrukt.init(Var.tk1, "DVI35_GAP32", initializeDetectorsParameters(DVI35_GAP32), true, true, true);
        gap_Det33 = VtVarStrukt.init(Var.tk1, "DVI35_GAP33", initializeDetectorsParameters(DVI35_GAP33), true, true, true);
        gap_Det34 = VtVarStrukt.init(Var.tk1, "DVI35_GAP34", initializeDetectorsParameters(DVI35_GAP34), true, true, true);
        gap_Det35 = VtVarStrukt.init(Var.tk1, "DVI35_GAP35", initializeDetectorsParameters(DVI35_GAP35), true, true, true);
        gap_Det36 = VtVarStrukt.init(Var.tk1, "DVI35_GAP36", initializeDetectorsParameters(DVI35_GAP36), true, true, true);
        gap_Det37 = VtVarStrukt.init(Var.tk1, "DVI35_GAP37", initializeDetectorsParameters(DVI35_GAP37), true, true, true);
        gap_Det38 = VtVarStrukt.init(Var.tk1, "DVI35_GAP38", initializeDetectorsParameters(DVI35_GAP38), true, true, true);
        gap_Det39 = VtVarStrukt.init(Var.tk1, "DVI35_GAP39", initializeDetectorsParameters(DVI35_GAP39), true, true, true);
        gap_Det40 = VtVarStrukt.init(Var.tk1, "DVI35_GAP40", initializeDetectorsParameters(DVI35_GAP40), true, true, true);
        gap_Det41 = VtVarStrukt.init(Var.tk1, "DVI35_GAP41", initializeDetectorsParameters(DVI35_GAP41), true, true, true);
        gap_Det42 = VtVarStrukt.init(Var.tk1, "DVI35_GAP42", initializeDetectorsParameters(DVI35_GAP42), true, true, true);
        gap_Det43 = VtVarStrukt.init(Var.tk1, "DVI35_GAP43", initializeDetectorsParameters(DVI35_GAP43), true, true, true);
        gap_Det44 = VtVarStrukt.init(Var.tk1, "DVI35_GAP44", initializeDetectorsParameters(DVI35_GAP44), true, true, true);
        gap_Det45 = VtVarStrukt.init(Var.tk1, "DVI35_GAP45", initializeDetectorsParameters(DVI35_GAP45), true, true, true);
        gap_Det46 = VtVarStrukt.init(Var.tk1, "DVI35_GAP46", initializeDetectorsParameters(DVI35_GAP46), true, true, true);
        gap_Det47 = VtVarStrukt.init(Var.tk1, "DVI35_GAP47", initializeDetectorsParameters(DVI35_GAP47), true, true, true);
        gap_Det48 = VtVarStrukt.init(Var.tk1, "DVI35_GAP48", initializeDetectorsParameters(DVI35_GAP48), true, true, true);
        gap_Det49 = VtVarStrukt.init(Var.tk1, "DVI35_GAP49", initializeDetectorsParameters(DVI35_GAP49), true, true, true);
        gap_Det50 = VtVarStrukt.init(Var.tk1, "DVI35_GAP50", initializeDetectorsParameters(DVI35_GAP50), true, true, true);
        gap_Det51 = VtVarStrukt.init(Var.tk1, "DVI35_GAP51", initializeDetectorsParameters(DVI35_GAP51), true, true, true);
        gap_Det52 = VtVarStrukt.init(Var.tk1, "DVI35_GAP52", initializeDetectorsParameters(DVI35_GAP52), true, true, true);
        gap_Det53 = VtVarStrukt.init(Var.tk1, "DVI35_GAP53", initializeDetectorsParameters(DVI35_GAP53), true, true, true);
        gap_Det54 = VtVarStrukt.init(Var.tk1, "DVI35_GAP54", initializeDetectorsParameters(DVI35_GAP54), true, true, true);
        gap_Det55 = VtVarStrukt.init(Var.tk1, "DVI35_GAP55", initializeDetectorsParameters(DVI35_GAP55), true, true, true);
        gap_Det56 = VtVarStrukt.init(Var.tk1, "DVI35_GAP56", initializeDetectorsParameters(DVI35_GAP56), true, true, true);
        gap_Det57 = VtVarStrukt.init(Var.tk1, "DVI35_GAP57", initializeDetectorsParameters(DVI35_GAP57), true, true, true);
        gap_Det58 = VtVarStrukt.init(Var.tk1, "DVI35_GAP58", initializeDetectorsParameters(DVI35_GAP58), true, true, true);
        gap_Det59 = VtVarStrukt.init(Var.tk1, "DVI35_GAP59", initializeDetectorsParameters(DVI35_GAP59), true, true, true);
        gap_Det60 = VtVarStrukt.init(Var.tk1, "DVI35_GAP60", initializeDetectorsParameters(DVI35_GAP60), true, true, true);
        gap_Det61 = VtVarStrukt.init(Var.tk1, "DVI35_GAP61", initializeDetectorsParameters(DVI35_GAP61), true, true, true);
        gap_Det62 = VtVarStrukt.init(Var.tk1, "DVI35_GAP62", initializeDetectorsParameters(DVI35_GAP62), true, true, true);
        gap_Det63 = VtVarStrukt.init(Var.tk1, "DVI35_GAP63", initializeDetectorsParameters(DVI35_GAP63), true, true, true);
        gap_Det64 = VtVarStrukt.init(Var.tk1, "DVI35_GAP64", initializeDetectorsParameters(DVI35_GAP64), true, true, true);
	}

    public boolean isDuration(int type) {
        return TYPE_DURATION == type;
    }

    public boolean isAbsolute(int type) {
        return TYPE_ABSOLUTE == type;
    }

    public boolean isComplement(int type) {
        return TYPE_COMPLEMENT == type;
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
