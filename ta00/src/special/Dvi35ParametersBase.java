package special;

import fehlerhandler.FehlerHandler;
import tk.ParSets;
import tk.Var;

/**
 * @author Ilia Butvinnik
 * @version 0.0.1
 * @since 01/06/2019
 */
public abstract class Dvi35ParametersBase {

	protected final int PARAMETERS_BLOCK_SIZE;
	public int[] parameters;
	private static int[] tmp;
	
	public Dvi35ParametersBase(int parametersBlockSize) {
		this.PARAMETERS_BLOCK_SIZE = parametersBlockSize;
	}
	
	public abstract void SetParameters();

	/**
	 * This method validates that the number of indexes matches the number of initialized parameters
	 * @param values	array of the parameters' values
	 * @param indexes	array of the parameters' indexes
	 * @param progs		array of programs numbers to which these parameters apply
	 * @return			fully initialized parameters array if no error occurred, otherwise null
	 */
    protected int[] initializeParameters(int[] values, int[] indexes, int[] progs) {
    	int[] array = new int[PARAMETERS_BLOCK_SIZE];
    	
    	if (values.length == indexes.length) {
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
    
    public int[] readParamArray (vt.ProgBase prog) {
        switch (prog.getNummer()) {
            case  1:
            	tmp = ParSets.pars_P01.get();
            	break;
            case  2: 
            	tmp = ParSets.pars_P02.get();
            	break;
            case  3:
            	tmp = ParSets.pars_P03.get();
            	break;
            case  4:
            	tmp = ParSets.pars_P04.get();
            	break;
            case  5:
            	tmp = ParSets.pars_P05.get();
            	break;
            case  6:
            	tmp = ParSets.pars_P06.get();
            	break;
            case  7:
            	tmp = ParSets.pars_P07.get();
            	break;
            case  8:
            	tmp = ParSets.pars_P08.get();
            	break;
            case  9:
            	tmp = ParSets.pars_P09.get();
            	break;
            case 10:
            	tmp = ParSets.pars_P10.get();
            	break;
            case 11:
            	tmp = ParSets.pars_P11.get();
            	break;
            case 12:
            	tmp = ParSets.pars_P12.get();
            	break;
            case 13:
            	tmp = ParSets.pars_P13.get();
            	break;
            case 14:
            	tmp = ParSets.pars_P14.get();
            	break;
            case 15:
            	tmp = ParSets.pars_P15.get();
            	break;
            case 16:
            	tmp = ParSets.pars_P16.get();
            	break;
            case 17:
        		tmp = ParSets.pars_P17.get();
            	break;
            case 18:
        		tmp = ParSets.pars_P18.get();
            	break;
            case 19:
        		tmp = ParSets.pars_P19.get();
        		break;
            case 20:
        		tmp = ParSets.pars_P20.get();
        		break;	
            case 21:
            	tmp = ParSets.pars_P21.get();
            	break;
            case 22:
            	tmp = ParSets.pars_P22.get();
            	break;
            case 23:
            	tmp = ParSets.pars_P23.get();
            	break;
            case 24:
            	tmp = ParSets.pars_P24.get();
            	break;
            case 25:
            	tmp = ParSets.pars_P25.get();
            	break;
            case 26:
            	tmp = ParSets.pars_P26.get();
            	break;
            case 27:
        		tmp = ParSets.pars_P27.get();
            	break;
            case 28:
        		tmp = ParSets.pars_P28.get();
            	break;
            case 29:
        		tmp = ParSets.pars_P29.get();
        		break;
            case 30:
        		tmp = ParSets.pars_P30.get();
        		break;
            case 31:
        		tmp = ParSets.pars_P31.get();
        		break;	
            case 32:
        		tmp = ParSets.pars_P32.get();
        		break;	
            case 51:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P01.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 52:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P02.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 53:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P03.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 54:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P04.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 55:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P05.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 56:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P06.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 57:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P07.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 58:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P08.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 59:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P09.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 60:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P10.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 61:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P11.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 62:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P12.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 63:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P13.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 64:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P14.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 65:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P15.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 66:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P16.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 67:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P17.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 68:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P18.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 69:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P19.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 70:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P20.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 71:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P21.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 72:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P22.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 73:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P23.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 74:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P24.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 75:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P25.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 76:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P26.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 77:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P27.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 78:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P28.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 79:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P29.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 80:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P30.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 81:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P31.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 82:
            	if (Var.controller.isMaintenance()) {
            		tmp = ParSets.pars_P32.get();
            	} else {
            		tmp = ParSets.pars_P01.get();
            	}
        		break;
            case 95:
            	tmp = ParSets.r1.get();
            	break;
            default:
            	tmp = ParSets.pars_P01.get();
        }
        return tmp;
    }


    public int[] readParamArray (int progNum) {
        switch (progNum) {
        case  1:
        	tmp = ParSets.pars_P01.get();
        	break;
        case  2: 
        	tmp = ParSets.pars_P02.get();
        	break;
        case  3:
        	tmp = ParSets.pars_P03.get();
        	break;
        case  4:
        	tmp = ParSets.pars_P04.get();
        	break;
        case  5:
        	tmp = ParSets.pars_P05.get();
        	break;
        case  6:
        	tmp = ParSets.pars_P06.get();
        	break;
        case  7:
        	tmp = ParSets.pars_P07.get();
        	break;
        case  8:
        	tmp = ParSets.pars_P08.get();
        	break;
        case  9:
        	tmp = ParSets.pars_P09.get();
        	break;
        case 10:
        	tmp = ParSets.pars_P10.get();
        	break;
        case 11:
        	tmp = ParSets.pars_P11.get();
        	break;
        case 12:
        	tmp = ParSets.pars_P12.get();
        	break;
        case 13:
        	tmp = ParSets.pars_P13.get();
        	break;
        case 14:
        	tmp = ParSets.pars_P14.get();
        	break;
        case 15:
        	tmp = ParSets.pars_P15.get();
        	break;
        case 16:
        	tmp = ParSets.pars_P16.get();
        	break;
        case 17:
    		tmp = ParSets.pars_P17.get();
        	break;
        case 18:
    		tmp = ParSets.pars_P18.get();
        	break;
        case 19:
    		tmp = ParSets.pars_P19.get();
    		break;
        case 20:
    		tmp = ParSets.pars_P20.get();
    		break;
        case 21:
    		tmp = ParSets.pars_P21.get();
    		break;
        case 22:
    		tmp = ParSets.pars_P22.get();
    		break;
        case 23:
    		tmp = ParSets.pars_P23.get();
    		break;
        case 24:
    		tmp = ParSets.pars_P24.get();
    		break;
        case 25:
    		tmp = ParSets.pars_P25.get();
    		break;
        case 26:
    		tmp = ParSets.pars_P26.get();
    		break;
        case 27:
    		tmp = ParSets.pars_P27.get();
    		break;
        case 28:
    		tmp = ParSets.pars_P28.get();
    		break;
        case 29:
    		tmp = ParSets.pars_P29.get();
    		break;
        case 30:
    		tmp = ParSets.pars_P30.get();
    		break;
        case 31:
    		tmp = ParSets.pars_P31.get();
    		break;
        case 32:
    		tmp = ParSets.pars_P32.get();
    		break;
        case 51:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P01.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 52:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P02.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 53:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P03.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 54:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P04.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 55:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P05.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 56:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P06.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 57:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P07.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 58:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P08.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 59:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P09.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 60:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P10.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 61:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P11.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 62:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P12.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 63:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P13.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 64:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P14.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 65:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P15.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 66:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P16.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 67:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P17.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 68:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P18.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 69:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P19.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 70:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P20.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 71:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P21.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 72:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P22.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 73:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P23.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 74:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P24.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 75:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P25.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 76:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P26.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 77:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P27.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 78:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P28.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 79:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P29.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 80:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P30.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 81:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P31.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 82:
        	if (Var.controller.isMaintenance()) {
        		tmp = ParSets.pars_P32.get();
        	} else {
        		tmp = ParSets.pars_P01.get();
        	}
    		break;
        case 95:
        	tmp = ParSets.r1.get();
        	break;
        default:
        	tmp = ParSets.pars_P01.get();
        }
        return tmp;
    }
    
    public static int[] readPoliceParams() {
    	tmp = ParSets.r1.get();
    	return tmp;
    }
}