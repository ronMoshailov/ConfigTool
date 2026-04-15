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
public class NetiveiAyalonParameters extends Parameters {
    
    public static final int TYPE_DURATION   = 0;
    public static final int TYPE_ABSOLUTE   = 1;
    public static final int TYPE_COMPLEMENT = 2;
    
    public static final int GW_INDEPENDENT  = 0;
    public static final int GW_MASTER       = 1;
    public static final int GW_SLAVE        = 2;

    public NetiveiAyalonParameters() {
        super(200);
        
        setIncludesPhLen(false);
    }
    
    public ParametersIndex PARAM_OFFSET         = new ParametersIndex(  1);
    public ParametersIndex PARAM_PEDMAXRED      = new ParametersIndex(  2);
    public ParametersIndex PARAM_CYCLE          = new ParametersIndex(  3);
    public ParametersIndex PARAM_STRUCTURE      = new ParametersIndex(  4);
    public ParametersIndex PARAM_ISCROSSMASTER  = new ParametersIndex(  5);
    public ParametersIndex PARAM_GAP_SET        = new ParametersIndex( 99);
    public ParametersIndex PARAM_MODE_SET       = new ParametersIndex(100);
                                           
    public ParametersIndex PARAM_OFFSET2        = new ParametersIndex(101);
    public ParametersIndex PARAM_PEDMAXRED2     = new ParametersIndex(102);
    public ParametersIndex PARAM_CYCLE2         = new ParametersIndex(103);
    public ParametersIndex PARAM_STRUCTURE2     = new ParametersIndex(104);
    public ParametersIndex PARAM_ISCROSSMASTER2 = new ParametersIndex(105);
    public ParametersIndex PARAM_GAP_SET2       = new ParametersIndex(199);
    public ParametersIndex PARAM_MODE_SET2      = new ParametersIndex(200);
    
	static final int[] parameters_indexes_stucture_1 = {  1,   2,   3,   4,   5,   6,   7,   8,   9,  10,  11,  12,  21,  22,  23,  24,  25,  26,  27,  99, 100};
	static int[]       DVI35_P09                     = {  0,   0, 160,   1,   1,   2,   0,   1,   3,   1,   1, 160,   2,  30,  19,  13,  13,  15, 160,   1,   1};
	static int[]       DVI35_P10                     = {  0,   0, 130,   1,   1,   2,   0,   1,   3,   1,   1, 130,   2,  10,  10,  13,  16,  11, 130,   1,   1};
	static int[]       DVI35_P11                     = {  0,   0, 180,   1,   0,   2,   0,   1,   3,   1,   1, 180,   2,  30,  17,  27,  28,   8, 180,   1,   1};
	static int[]       DVI35_P12                     = {  0,   0, 150,   1,   0,   2,   0,   1,   3,   1,   1, 150,   2,  10,   7,  27,  28,   8, 150,   1,   1};
	static int[]       DVI35_P13                     = {  0,   0, 150,   1,   0,   2,   0,   1,   3,   1,   1, 150,   2,  34,  19,   8,   9,  10, 150,   1,   1};

	static final int[] parameters_indexes_stucture_2 = {  1,   2,   3,   4,   5,   6,   7,   8,   9,  10,  11,  12,  13,  14,  15,  16,  21,  22,  23,  24,  25,  26,  27,  28,  29,  30,  31,  66,  67,  68,  69,  70,  71,  72,  73,  74,  75,  81,  82,  84,  85,  87,  88,  89,  90,  93,  94,  99, 100};
	static int[]       DVI35_P01                     = {  0, 120, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,   0,  62,   2,   4,   1,   3,  78,   3,   1,  80, 254,  10,  15, 254,  10,   1,   2,   3,   1,   2,   0,   0,   2,  16,  67,  29,  72,  22,   1,   0,   1,   1};
	static int[]       DVI35_P02                     = {  0, 150, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,   4,  62,   8,   7,   1,   5,  87,   8,   5, 100, 254,  22,  21, 254,  14,   1,   2,   3,   1,   2,   0,   0,   2,  18,  73,  34,  75,  22,   1,   0,   1,   1};
	static int[]       DVI35_P03                     = {  0, 195, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  17,  62,  10,  10,   2,  16,  87,  10,   7, 130, 254,  20,  27, 254,  20,   1,   2,   3,   1,   2,   0,   0,   2,  34,  75,  35,  73,  22,   1,   0,   1,   1};
	static int[]       DVI35_P04                     = {  0, 195, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  16,  62,  22,   8,   1,   7,  87,   9,   6, 130, 254,  42,  24, 254,  20,   1,   2,   3,   1,   2,   0,   0,   2,  26,  75,  34,  22,  22,   1,   1,   1,   1};
	static int[]       DVI35_P05                     = {  0, 195, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  16,  62,  22,   8,   1,   7,  87,   9,   6, 130, 254,  42,  24, 254,  20,   1,   2,   3,   1,   2,   0,   0,   2,  26,  75,  34,  75,  22,   1,   0,   1,   1};
	static int[]       DVI35_P06                     = {  0, 180, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,   0,  62,  11,  12,   4,   7,  87,  22,  19, 120, 254,  26,  36, 254,  42,   1,   2,   3,   1,   2,   0,   0,   2,  24,  73,  34,  75,  22,   1,   0,   1,   1};	
	static int[]       DVI35_P07                     = {  0, 180, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,   0,  62,  21,  12,   4,   8,  87,  11,   8, 120, 254,  42,  36, 254,  24,   1,   2,   3,   1,   2,   0,   0,   2,  26,  73,  34,  75,  22,   1,   0,   1,   1};
	static int[]       DVI35_P08                     = {  0, 180, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,   0,  62,  23,  11,   3,   6,  87,  12,   9, 120, 254,  46,  33, 254,  26,   1,   2,   3,   1,   2,   0,   0,   2,  22,  73,  34,  75,  22,   1,   0,   1,   1};
	static int[]       DVI35_P14                     = {  0, 150, 130,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,   0,  62,   6,  12,   4,   7,  87,   7,   4, 100, 254,  16,  36, 254,  24,   1,   2,   3,   1,   2,   0,   0,   2,  16,  73,  34,  75,  22,   1,   0,   1,   1};
	static int[]       DVI35_P15                     = {  0, 210, 140,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  16,  72,  32,   8,   1,   7,  97,   9,   6, 140, 254,  37,  24, 254,  20,   1,   1,   3,   1,   2,   0,   0,   2,  26,  75,  35,  75,  22,   1,   0,   1,   1};	                                                                                                                                                                                                                                                                                                                                          	                                                                                                                                                                                                                                                                                                                                          
	static int[]       DVI35_P16                     = {  0, 210, 140,   2,   0,   6,   9,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  30,  72,  18,   8,   1,   7,  97,   9,   6, 140, 254,  36,  24, 254,  20,   1,   2,   3,   1,   2,   0,   0,   2,  26,  75,  35,  75,  22,   1,   0,   1,   1};
	static int[]       DVI35_P17                     = {  0, 210, 140,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  16,  72,  22,  18,  11,   7,  97,   9,   6, 140, 254,  42,  21, 254,  20,   1,   2,   1,   1,   2,   0,   0,   2,  26,  75,  35,  75,  22,   1,   0,   1,   1};
	static int[]       DVI35_P18                     = {  0, 210, 140,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  17,  72,  10,  10,   2,  16,  97,  20,  17, 140, 254,  20,  27, 254,  20,   1,   2,   3,   1,   1,   0,   0,   2,  34,  75,  35,  73,  22,   1,   0,   1,   1};
	static int[]       DVI35_P19                     = {  0, 210, 140,   2,   0,   6,   0,   1,   1,   3,   1,   3,   1,   2,   1,   1,   6,  17,  72,  10,  10,   2,  26,  97,  10,   7, 140, 254,  20,  27, 254,  20,   1,   2,   3,   1,   2,   0,   0,   1,  34,  75,  35,  75,  22,   1,   0,   1,   1};

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
        if (nodeId == 2) {
            return parameters[PARAM_CYCLE2.getIndex() - 1];
        } else {
            return parameters[PARAM_CYCLE .getIndex() - 1];
        }
    }
    
    public int getStructure() {
        return parameters[PARAM_STRUCTURE.getIndex() - 1];
    }
    
    public int getStructure(int nodeId) {
        if (nodeId == 2) {
            return parameters[PARAM_STRUCTURE2.getIndex() - 1];
        } else {
            return parameters[PARAM_STRUCTURE .getIndex() - 1];
        }
    }
    
    public int getOffset() {
        return parameters[PARAM_OFFSET.getIndex() - 1];
    }
    
    public int getOffset(int nodeId) {
        if (nodeId == 2) {
            return parameters[PARAM_OFFSET2.getIndex() - 1];
        } else {
            return parameters[PARAM_OFFSET .getIndex() - 1];
        }
    }
    
    public boolean isGWEnable(int programNumber) {
        return isGWEnable(1, programNumber);
    }
    
    public boolean isGWEnable(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 2) {
                    return parameters[PARAM_ISCROSSMASTER2.getIndex() - 1] != GW_INDEPENDENT;
                } else {
                    return parameters[PARAM_ISCROSSMASTER .getIndex() - 1] != GW_INDEPENDENT;
                }
            } else {
                if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_ISCROSSMASTER2.getIndex() - 1] != GW_INDEPENDENT;
                } else {
                    return getParameters(programNumber)[PARAM_ISCROSSMASTER .getIndex() - 1] != GW_INDEPENDENT;
                }
            }
        }
        return false;
    }
    
    public boolean isMaster(int programNumber) {
        return isMaster(1, programNumber);
    }
    
    public boolean isMaster(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 2) {
                    return parameters[PARAM_ISCROSSMASTER2.getIndex() - 1] == GW_MASTER;
                } else {
                    return parameters[PARAM_ISCROSSMASTER .getIndex() - 1] == GW_MASTER;
                }
            } else {
                if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_ISCROSSMASTER2.getIndex() - 1] == GW_MASTER;
                } else {
                    return getParameters(programNumber)[PARAM_ISCROSSMASTER .getIndex() - 1] == GW_MASTER;
                }
            }
        }
        return false;
    }
    
    public boolean isSlave(int programNumber) {
        return isSlave(1, programNumber);
    }
    
    public boolean isSlave(int nodeId, int programNumber) {
        if (nodeId <= Var.controller.nodes.length) {
            if (Var.controller.nodes[nodeId - 1].getAktProg().getNummer() == programNumber) {
                if (nodeId == 2) {
                    return parameters[PARAM_ISCROSSMASTER2.getIndex() - 1] == GW_SLAVE;
                } else {
                    return parameters[PARAM_ISCROSSMASTER .getIndex() - 1] == GW_SLAVE;
                }
            } else {
                if (nodeId == 2) {
                    return getParameters(programNumber)[PARAM_ISCROSSMASTER2.getIndex() - 1] == GW_SLAVE;
                } else {
                    return getParameters(programNumber)[PARAM_ISCROSSMASTER .getIndex() - 1] == GW_SLAVE;
                }
            }
        }
        return false;
    }
    
    public boolean isClockSync(int programNumber) {
        return isClockSync(1, programNumber);
    }
    
    public boolean isClockSync(int nodeId, int programNumber) {
        return false;
    }
    
    public boolean isSubmaster(int programNumber) {
        return isSubmaster(1, programNumber);
    }
    
    public boolean isSubmaster(int nodeId, int programNumber) {
        return false;
    }
    
    public boolean isLocalSubmaster(int programNumber) {
        return isLocalSubmaster(1, programNumber);
    }
    
    public boolean isLocalSubmaster(int nodeId, int programNumber) {
        return false;
    }

    public boolean isSetDemandDetector(int nodeId, int parametersId) {
        int modeIndex;
        if (nodeId == 2) {
            modeIndex = parameters[PARAM_MODE_SET2.getIndex() - 1];
        } else {
            modeIndex = parameters[PARAM_MODE_SET .getIndex() - 1];
        }
        
        if (modeIndex <= 0 || modeIndex > 4) {
            modeIndex = 1;
        }
        modeIndex--;
        
        return parametersDetectors[26 + modeIndex * 50 + parametersId - 1] > 0;
    }
    
    public boolean isSetExtensionDetector(int nodeId, int parametersId) {
        int modeIndex;
        if (nodeId == 2) {
            modeIndex = parameters[PARAM_MODE_SET2.getIndex() - 1];
        } else {
            modeIndex = parameters[PARAM_MODE_SET .getIndex() - 1];
        }
        
        if (modeIndex <= 0 || modeIndex > 4) {
            modeIndex = 1;
        }
        modeIndex--;
        
        return parametersDetectors[26 + modeIndex * 50 + parametersId - 1] > 0;
    }
    
    public boolean isDisabledDetector(int nodeId, int parametersId) {
        return false;
    }
	
	public void initializeParameters()
	{
		pars_P01 = VtVarStrukt.init(Var.tk1, "DVI35_P01", initializeParameters(DVI35_P01, parameters_indexes_stucture_2, new int[] {  1 }), true, true, true);
		pars_P02 = VtVarStrukt.init(Var.tk1, "DVI35_P02", initializeParameters(DVI35_P02, parameters_indexes_stucture_2, new int[] {  2 }), true, true, true);
		pars_P03 = VtVarStrukt.init(Var.tk1, "DVI35_P03", initializeParameters(DVI35_P03, parameters_indexes_stucture_2, new int[] {  3 }), true, true, true);
		pars_P04 = VtVarStrukt.init(Var.tk1, "DVI35_P04", initializeParameters(DVI35_P04, parameters_indexes_stucture_2, new int[] {  4 }), true, true, true);
		pars_P05 = VtVarStrukt.init(Var.tk1, "DVI35_P05", initializeParameters(DVI35_P05, parameters_indexes_stucture_2, new int[] {  5 }), true, true, true);
		pars_P06 = VtVarStrukt.init(Var.tk1, "DVI35_P06", initializeParameters(DVI35_P06, parameters_indexes_stucture_2, new int[] {  6 }), true, true, true);	    
		pars_P07 = VtVarStrukt.init(Var.tk1, "DVI35_P07", initializeParameters(DVI35_P07, parameters_indexes_stucture_2, new int[] {  7 }), true, true, true);
		pars_P08 = VtVarStrukt.init(Var.tk1, "DVI35_P08", initializeParameters(DVI35_P08, parameters_indexes_stucture_2, new int[] {  8 }), true, true, true);       	    	    
		pars_P09 = VtVarStrukt.init(Var.tk1, "DVI35_P09", initializeParameters(DVI35_P09, parameters_indexes_stucture_1, new int[] {  9 }), true, true, true);
		pars_P10 = VtVarStrukt.init(Var.tk1, "DVI35_P10", initializeParameters(DVI35_P10, parameters_indexes_stucture_1, new int[] { 10 }), true, true, true);	    
		pars_P11 = VtVarStrukt.init(Var.tk1, "DVI35_P11", initializeParameters(DVI35_P11, parameters_indexes_stucture_1, new int[] { 11 }), true, true, true);
		pars_P12 = VtVarStrukt.init(Var.tk1, "DVI35_P12", initializeParameters(DVI35_P12, parameters_indexes_stucture_1, new int[] { 12 }), true, true, true);
		pars_P13 = VtVarStrukt.init(Var.tk1, "DVI35_P13", initializeParameters(DVI35_P13, parameters_indexes_stucture_1, new int[] { 13 }), true, true, true);
		pars_P14 = VtVarStrukt.init(Var.tk1, "DVI35_P14", initializeParameters(DVI35_P14, parameters_indexes_stucture_2, new int[] { 14 }), true, true, true);
		pars_P15 = VtVarStrukt.init(Var.tk1, "DVI35_P15", initializeParameters(DVI35_P15, parameters_indexes_stucture_2, new int[] { 15 }), true, true, true);	    
		pars_P16 = VtVarStrukt.init(Var.tk1, "DVI35_P16", initializeParameters(DVI35_P16, parameters_indexes_stucture_2, new int[] { 16 }), true, true, true);
		pars_P17 = VtVarStrukt.init(Var.tk1, "DVI35_P17", initializeParameters(DVI35_P17, parameters_indexes_stucture_2, new int[] { 17 }), true, true, true);
		pars_P18 = VtVarStrukt.init(Var.tk1, "DVI35_P18", initializeParameters(DVI35_P18, parameters_indexes_stucture_2, new int[] { 18 }), true, true, true);
		pars_P19 = VtVarStrukt.init(Var.tk1, "DVI35_P19", initializeParameters(DVI35_P19, parameters_indexes_stucture_2, new int[] { 19 }), true, true, true);    		     

		pars_DET = VtVarStrukt.init(Var.tk1, "DVI35_DET", initializeParameters(DVI35_DET, parameters_indexes_det, new int[] { 20 }), true, true, true);
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
