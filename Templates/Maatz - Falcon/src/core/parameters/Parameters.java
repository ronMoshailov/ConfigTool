package core.parameters;

import core.Node.SpecialProgram;
import m0547.Var;
import vtvar.VtVarStrukt;
import fehlerhandler.FehlerHandler;

public abstract class Parameters extends iParameters {

    protected final int PARAMETERS_BLOCK_SIZE;
    
    public VtVarStrukt pars_DET;
    public VtVarStrukt pars_P01 , pars_P02 , pars_P03 , pars_P04 , pars_P05 , pars_P06 , pars_P07 , pars_P08 ;
    public VtVarStrukt pars_P09 , pars_P10 , pars_P11 , pars_P12 , pars_P13 , pars_P14 , pars_P15 , pars_P16 ;
    public VtVarStrukt pars_P17 , pars_P18 , pars_P19 , pars_P20 , pars_P21 , pars_P22 , pars_P23 , pars_P24 ;
    public VtVarStrukt pars_P25 , pars_P26 , pars_P27 , pars_P28 , pars_P29 , pars_P30 , pars_P31 , pars_P32 ;
    public VtVarStrukt inner_P01, inner_P02, inner_P03, inner_P04, inner_P05, inner_P06, inner_P07, inner_P08;
    public VtVarStrukt inner_P09, inner_P10, inner_P11, inner_P12, inner_P13, inner_P14, inner_P15, inner_P16;
    public VtVarStrukt inner_P17, inner_P18, inner_P19, inner_P20, inner_P21, inner_P22, inner_P23, inner_P24;
    public VtVarStrukt inner_P25, inner_P26, inner_P27, inner_P28, inner_P29, inner_P30, inner_P31, inner_P32;
    public VtVarStrukt pars_DET_FAULT, pars_SKIP_PREEMP;
    public VtVarStrukt r;
    public VtVarStrukt gap_Det01, gap_Det02, gap_Det03, gap_Det04, gap_Det05, gap_Det06, gap_Det07, gap_Det08;
    public VtVarStrukt gap_Det09, gap_Det10, gap_Det11, gap_Det12, gap_Det13, gap_Det14, gap_Det15, gap_Det16;
    public VtVarStrukt gap_Det17, gap_Det18, gap_Det19, gap_Det20, gap_Det21, gap_Det22, gap_Det23, gap_Det24;
    public VtVarStrukt gap_Det25, gap_Det26, gap_Det27, gap_Det28, gap_Det29, gap_Det30, gap_Det31, gap_Det32;
    public VtVarStrukt gap_Det33, gap_Det34, gap_Det35, gap_Det36, gap_Det37, gap_Det38, gap_Det39, gap_Det40;
    public VtVarStrukt gap_Det41, gap_Det42, gap_Det43, gap_Det44, gap_Det45, gap_Det46, gap_Det47, gap_Det48;
    public VtVarStrukt gap_Det49, gap_Det50, gap_Det51, gap_Det52, gap_Det53, gap_Det54, gap_Det55, gap_Det56;
    public VtVarStrukt gap_Det57, gap_Det58, gap_Det59, gap_Det60, gap_Det61, gap_Det62, gap_Det63, gap_Det64; 
    
    public int[] parameters;
    public int[] parametersInner;
    public int[] parametersDetectors;
    private static int[] tmp;
    
    protected Parameters(int parametersSize) {
        PARAMETERS_BLOCK_SIZE = parametersSize;
    }
    
    public int getParametersCount() {
        return PARAMETERS_BLOCK_SIZE;
    }
    
    /**
     * 
     * This method validates that the number of indexes matches the number of initialized parameters
     * @param values    array of the parameters' values
     * @param indexes   array of the parameters' indexes
     * @param progs     array of programs numbers to which these parameters apply
     * @return          fully initialized parameters array if no error occurred, otherwise null
     */
    protected int[] initializeParameters(int[] values, int[] indexes, int[] progs) {
        int[] array = new int[PARAMETERS_BLOCK_SIZE];
        
        if (values.length == indexes.length)
        {
            for (int i = 0; i < values.length; i++) {
                array[indexes[i]-1] = values[i];
            }
            return array;
        }
        
        for (int i = 0; i < progs.length; i++) {
            System.out.println("Error in assigning parameters of program " + progs[i]);
            FehlerHandler.writeMsg(FehlerHandler.errFatal, true, Var.tk1.getNummer(), "Assignment of Parameters in prog " + progs[i]);
        }
        return null;
    }
    
    public int[] initializeDetectorsParameters(int[] values) {
        int[] array = new int[Var.controller.getMaxProgramsNumber()];
        
        for (int i = 0; i < array.length; i++) {
            if (values == null || values.length == 0) {
                array[i] = 10;
            } else if (i < values.length) {
                array[i] = values[i];
            } else {
                array[i] = values[0];
            }
        }
        
        return array;
    }
    
    public int getParameterValue(int index) {
        if (index < 0 || index >= parameters.length) {
            return 0;
        }
        return parameters[index];
    }
    
    public int getParameterValue(ParametersIndex index) {
        if (index == null || index.getIndex() <= 0 || index.getIndex() > parameters.length) {
            return 0;
        }
        return parameters[index.getIndex() - 1];
    }
    
    public int[] getPoliceParameters() {
        return r.get();
    }
    
    public int[] getParameters(vt.ProgBase prog) {
        return getParameters(prog.getNummer());
    }
    
    public int[] getParameters(int programNumber) {
        switch (programNumber) {
            case  1:
                tmp = pars_P01.get();
                break;
            case  2: 
                tmp = pars_P02.get();
                break;
            case  3:
                tmp = pars_P03.get();
                break;
            case  4:
                tmp = pars_P04.get();
                break;
            case  5:
                tmp = pars_P05.get();
                break;
            case  6:
                tmp = pars_P06.get();
                break;
            case  7:
                tmp = pars_P07.get();
                break;
            case  8:
                tmp = pars_P08.get();
                break;
            case  9:
                tmp = pars_P09.get();
                break;
            case 10:
                tmp = pars_P10.get();
                break;
            case 11:
                tmp = pars_P11.get();
                break;
            case 12:
                tmp = pars_P12.get();
                break;
            case 13:
                tmp = pars_P13.get();
                break;
            case 14:
                tmp = pars_P14.get();
                break;
            case 15:
                tmp = pars_P15.get();
                break;
            case 16:
                tmp = pars_P16.get();
                break;
            case 17:
                tmp = pars_P17.get();
                break;
            case 18:
                tmp = pars_P18.get();
                break;
            case 19:
                tmp = pars_P19.get();
                break;
            case 20:
                tmp = pars_P20.get();
                break;
            case 21:
                tmp = pars_P21.get();
                break;
            case 22:
                tmp = pars_P22.get();
                break;
            case 23:
                tmp = pars_P23.get();
                break;
            case 24:
                tmp = pars_P24.get();
                break;
            case 25:
                tmp = pars_P25.get();
                break;
            case 26:
                tmp = pars_P26.get();
                break;
            case 27:
                tmp = pars_P27.get();
                break;
            case 28:
                tmp = pars_P28.get();
                break;
            case 29:
                tmp = pars_P29.get();
                break;
            case 30:
                tmp = pars_P30.get();
                break;
            case 31:
                tmp = pars_P31.get();
                break;
            case 32:
                tmp = pars_P32.get();
                break;
            case 51:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P01.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 52:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P02.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 53:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P03.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 54:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P04.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 55:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P05.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 56:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P06.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 57:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P07.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 58:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P08.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 59:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P09.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 60:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P10.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 61:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P11.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 62:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P12.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 63:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P13.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 64:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P14.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 65:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P15.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 66:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P16.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 67:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P17.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 68:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P18.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 69:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P19.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 70:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P20.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 71:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P21.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 72:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P22.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 73:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P23.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 74:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P24.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 75:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P25.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 76:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P26.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 77:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P27.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 78:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P28.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 79:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P29.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 80:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P30.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 81:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P31.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 82:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P32.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 95:
                tmp = r.get();
                break;
            default:
                tmp = pars_P01.get();
        }
        return tmp;
    }
    
    public int[] getGapsParameters(int progNumber) {
        if (progNumber >= 1
                && progNumber <= Var.controller.getMaxProgramsNumber()) {
            parametersDetectors[ 0] = gap_Det01.get(progNumber - 1);
            parametersDetectors[ 1] = gap_Det02.get(progNumber - 1);
            parametersDetectors[ 2] = gap_Det03.get(progNumber - 1);
            parametersDetectors[ 3] = gap_Det04.get(progNumber - 1);
            parametersDetectors[ 4] = gap_Det05.get(progNumber - 1);
            parametersDetectors[ 5] = gap_Det06.get(progNumber - 1);
            parametersDetectors[ 6] = gap_Det07.get(progNumber - 1);
            parametersDetectors[ 7] = gap_Det08.get(progNumber - 1);
            parametersDetectors[ 8] = gap_Det09.get(progNumber - 1);
            parametersDetectors[ 9] = gap_Det10.get(progNumber - 1);
            parametersDetectors[10] = gap_Det11.get(progNumber - 1);
            parametersDetectors[11] = gap_Det12.get(progNumber - 1);
            parametersDetectors[12] = gap_Det13.get(progNumber - 1);
            parametersDetectors[13] = gap_Det14.get(progNumber - 1);
            parametersDetectors[14] = gap_Det15.get(progNumber - 1);
            parametersDetectors[15] = gap_Det16.get(progNumber - 1);
            parametersDetectors[16] = gap_Det17.get(progNumber - 1);
            parametersDetectors[17] = gap_Det18.get(progNumber - 1);
            parametersDetectors[18] = gap_Det19.get(progNumber - 1);
            parametersDetectors[19] = gap_Det20.get(progNumber - 1);
            parametersDetectors[20] = gap_Det21.get(progNumber - 1);
            parametersDetectors[21] = gap_Det22.get(progNumber - 1);
            parametersDetectors[22] = gap_Det23.get(progNumber - 1);
            parametersDetectors[23] = gap_Det24.get(progNumber - 1);
            parametersDetectors[24] = gap_Det25.get(progNumber - 1);
            parametersDetectors[25] = gap_Det26.get(progNumber - 1);
            parametersDetectors[26] = gap_Det27.get(progNumber - 1);
            parametersDetectors[27] = gap_Det28.get(progNumber - 1);
            parametersDetectors[28] = gap_Det29.get(progNumber - 1);
            parametersDetectors[29] = gap_Det30.get(progNumber - 1);
            parametersDetectors[30] = gap_Det31.get(progNumber - 1);
            parametersDetectors[31] = gap_Det32.get(progNumber - 1);
            parametersDetectors[32] = gap_Det33.get(progNumber - 1);
            parametersDetectors[33] = gap_Det34.get(progNumber - 1);
            parametersDetectors[34] = gap_Det35.get(progNumber - 1);
            parametersDetectors[35] = gap_Det36.get(progNumber - 1);
            parametersDetectors[36] = gap_Det37.get(progNumber - 1);
            parametersDetectors[37] = gap_Det38.get(progNumber - 1);
            parametersDetectors[38] = gap_Det39.get(progNumber - 1);
            parametersDetectors[39] = gap_Det30.get(progNumber - 1);
            parametersDetectors[40] = gap_Det41.get(progNumber - 1);
            parametersDetectors[41] = gap_Det42.get(progNumber - 1);
            parametersDetectors[42] = gap_Det43.get(progNumber - 1);
            parametersDetectors[43] = gap_Det44.get(progNumber - 1);
            parametersDetectors[44] = gap_Det45.get(progNumber - 1);
            parametersDetectors[45] = gap_Det46.get(progNumber - 1);
            parametersDetectors[46] = gap_Det47.get(progNumber - 1);
            parametersDetectors[47] = gap_Det48.get(progNumber - 1);
            parametersDetectors[48] = gap_Det49.get(progNumber - 1);
            parametersDetectors[49] = gap_Det40.get(progNumber - 1);
            parametersDetectors[50] = gap_Det51.get(progNumber - 1);
            parametersDetectors[51] = gap_Det52.get(progNumber - 1);
            parametersDetectors[52] = gap_Det53.get(progNumber - 1);
            parametersDetectors[53] = gap_Det54.get(progNumber - 1);
            parametersDetectors[54] = gap_Det55.get(progNumber - 1);
            parametersDetectors[55] = gap_Det56.get(progNumber - 1);
            parametersDetectors[56] = gap_Det57.get(progNumber - 1);
            parametersDetectors[57] = gap_Det58.get(progNumber - 1);
            parametersDetectors[58] = gap_Det59.get(progNumber - 1);
            parametersDetectors[59] = gap_Det50.get(progNumber - 1);
            parametersDetectors[60] = gap_Det61.get(progNumber - 1);
            parametersDetectors[61] = gap_Det62.get(progNumber - 1);
            parametersDetectors[62] = gap_Det63.get(progNumber - 1);
            parametersDetectors[63] = gap_Det64.get(progNumber - 1);
        } else if (progNumber >= SpecialProgram.MaintenanceFirst.getProgramNumber()
                && progNumber <= SpecialProgram.MaintenanceFirst.getProgramNumber() + progNumber - 1) {
            return getGapsParameters(progNumber - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1);
        }
        return parametersDetectors;
    }
    
    public int getParameterAt(vt.ProgBase prog, int index) {
        switch (prog.getNummer()) {
            case  1:
                tmp = pars_P01.get();
                break;
            case  2: 
                tmp = pars_P02.get();
                break;
            case  3:
                tmp = pars_P03.get();
                break;
            case  4:
                tmp = pars_P04.get();
                break;
            case  5:
                tmp = pars_P05.get();
                break;
            case  6:
                tmp = pars_P06.get();
                break;
            case  7:
                tmp = pars_P07.get();
                break;
            case  8:
                tmp = pars_P08.get();
                break;
            case  9:
                tmp = pars_P09.get();
                break;
            case 10:
                tmp = pars_P10.get();
                break;
            case 11:
                tmp = pars_P11.get();
                break;
            case 12:
                tmp = pars_P12.get();
                break;
            case 13:
                tmp = pars_P13.get();
                break;
            case 14:
                tmp = pars_P14.get();
                break;
            case 15:
                tmp = pars_P15.get();
                break;
            case 16:
                tmp = pars_P16.get();
                break;
            case 17:
                tmp = pars_P17.get();
                break;
            case 18:
                tmp = pars_P18.get();
                break;
            case 19:
                tmp = pars_P19.get();
                break;
            case 20:
                tmp = pars_P20.get();
                break;
            case 21:
                tmp = pars_P21.get();
                break;
            case 22:
                tmp = pars_P22.get();
                break;
            case 23:
                tmp = pars_P23.get();
                break;
            case 24:
                tmp = pars_P24.get();
                break;
            case 25:
                tmp = pars_P25.get();
                break;
            case 26:
                tmp = pars_P26.get();
                break;
            case 27:
                tmp = pars_P27.get();
                break;
            case 28:
                tmp = pars_P28.get();
                break;
            case 29:
                tmp = pars_P29.get();
                break;
            case 30:
                tmp = pars_P30.get();
                break;
            case 31:
                tmp = pars_P31.get();
                break;
            case 32:
                tmp = pars_P32.get();
                break;
            case 51:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P01.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 52:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P02.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 53:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P03.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 54:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P04.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 55:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P05.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 56:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P06.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 57:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P07.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 58:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P08.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 59:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P09.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 60:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P10.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 61:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P11.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 62:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P12.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 63:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P13.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 64:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P14.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 65:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P15.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 66:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P16.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 67:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P17.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 68:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P18.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 69:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P19.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 70:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P20.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 71:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P21.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 72:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P22.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 73:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P23.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 74:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P24.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 75:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P25.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 76:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P26.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 77:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P27.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 78:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P28.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 79:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P29.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 80:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P30.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 81:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P31.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 82:
                if (Var.controller.isMaintenance()) {
                    tmp = pars_P32.get();
                } else {
                    tmp = pars_P01.get();
                }
                break;
            case 95:
                tmp = r.get();
                break;
            default:
                tmp = pars_P01.get();
        }
        if (index < 0 || index >= tmp.length) {
            return 0;
        }
        return tmp[index];
    }
    
    public int[] getInnerParameters (vt.ProgBase prog) {

        switch (prog.getNummer()) {
            case  1:
                tmp = inner_P01.get();
                break;
            case  2: 
                tmp = inner_P02.get();
                break;
            case  3:
                tmp = inner_P03.get();
                break;
            case  4:
                tmp = inner_P04.get();
                break;
            case  5:
                tmp = inner_P05.get();
                break;
            case  6:
                tmp = inner_P06.get();
                break;
            case  7:
                tmp = inner_P07.get();
                break;
            case  8:
                tmp = inner_P08.get();
                break;
            case  9:
                tmp = inner_P09.get();
                break;
            case 10:
                tmp = inner_P10.get();
                break;
            case 11:
                tmp = inner_P11.get();
                break;
            case 12:
                tmp = inner_P12.get();
                break;
            case 13:
                tmp = inner_P13.get();
                break;
            case 14:
                tmp = inner_P14.get();
                break;
            case 15:
                tmp = inner_P15.get();
                break;
            case 16:
                tmp = inner_P16.get();
                break;
            case 17:
                tmp = inner_P17.get();
                break;
            case 18:
                tmp = inner_P18.get();
                break;
            case 19:
                tmp = inner_P19.get();
                break;
            case 51:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P01.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 52:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P02.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 53:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P03.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 54:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P04.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 55:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P05.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 56:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P06.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 57:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P07.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 58:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P08.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 59:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P09.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 60:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P10.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 61:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P11.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 62:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P12.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 63:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P13.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 64:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P14.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 65:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P15.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 66:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P16.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 67:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P17.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 68:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P18.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 69:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P19.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 95:
                tmp = r.get();
                break;
            default:
                tmp = inner_P01.get();
        }
        return tmp;
    }
    
    public int getInnerParametersAt(vt.ProgBase prog, int index) {

        switch (prog.getNummer()) {
            case  1:
                tmp = inner_P01.get();
                break;
            case  2: 
                tmp = inner_P02.get();
                break;
            case  3:
                tmp = inner_P03.get();
                break;
            case  4:
                tmp = inner_P04.get();
                break;
            case  5:
                tmp = inner_P05.get();
                break;
            case  6:
                tmp = inner_P06.get();
                break;
            case  7:
                tmp = inner_P07.get();
                break;
            case  8:
                tmp = inner_P08.get();
                break;
            case  9:
                tmp = inner_P09.get();
                break;
            case 10:
                tmp = inner_P10.get();
                break;
            case 11:
                tmp = inner_P11.get();
                break;
            case 12:
                tmp = inner_P12.get();
                break;
            case 13:
                tmp = inner_P13.get();
                break;
            case 14:
                tmp = inner_P14.get();
                break;
            case 15:
                tmp = inner_P15.get();
                break;
            case 16:
                tmp = inner_P16.get();
                break;
            case 17:
                tmp = inner_P17.get();
                break;
            case 18:
                tmp = inner_P18.get();
                break;
            case 19:
                tmp = inner_P19.get();
                break;
            case 51:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P01.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 52:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P02.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 53:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P03.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 54:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P04.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 55:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P05.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 56:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P06.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 57:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P07.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 58:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P08.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 59:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P09.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 60:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P10.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 61:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P11.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 62:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P12.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 63:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P13.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 64:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P14.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 65:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P15.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 66:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P16.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 67:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P17.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 68:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P18.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 69:
                if (Var.controller.isMaintenance()) {
                    tmp = inner_P19.get();
                } else {
                    tmp = inner_P01.get();
                }
                break;
            case 95:
                tmp = r.get();
                break;
            default:
                tmp = inner_P01.get();
        }
        if (index < 0 || index >= tmp.length) {
            return 0;
        }
        return tmp[index];
    }
}
