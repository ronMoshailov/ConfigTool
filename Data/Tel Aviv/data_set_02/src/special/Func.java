package special;

import enums.HaifaParametersIndexes;
import special.Node.SpecialProgram;
import ta118.ParametersTelAviv;
import tk.ParSets;
import tk.ParametersJerusalem;
import tk.Var;
import uhr.Zeit;
import vt.*;

public class Func {
	private static boolean [][] flagClockCoordination = new boolean[vt.TeilKnoten.getAnzahl()][32 + 1];
    private static int[] tmp = {};
    private static int tmp_int1, tmp_int2, tmp_int_new, i;

    public static void DebugPrint(String str) {
    	if (Var.isPrintDebug) {
    		System.out.println();
    		System.out.print(Zeit.getStunde() + ":" + Zeit.getMinute() + ":" + Zeit.getSekunde() + ":");
    		System.out.println("\tPr=" + Var.tk1.getAktProg().getNummer() + "\tTx=" + Var.tk1.getProgZeit() + "(Ebene=" + Var.tk1.getAktStgEbene() + ")");
    		System.out.println(str);
    		System.out.println();
    	}
    }
    
	/**
     * Method reads the array combinded to a program
     * @param prog
     * @return
     */
    public static int[] read_intarry (vt.ProgBase prog) {
        switch (prog.getNummer()) {
        	case  1:  tmp = ParSets.glob_P01.get();
        		break;
            case  2:  tmp = ParSets.glob_P02.get();
            	break;
            case  3:  tmp = ParSets.glob_P03.get();
            	break;
            case  4:  tmp = ParSets.glob_P04.get();
            	break;
            case  5:  tmp = ParSets.glob_P05.get();
        		break;
            case  6:  tmp = ParSets.glob_P06.get();
        		break;
            case  7:  tmp = ParSets.glob_P07.get();
        		break;
            case  8:  tmp = ParSets.glob_P08.get();
        		break;
            case  9:  tmp = ParSets.glob_P09.get();
        		break;
            case 10:  tmp = ParSets.glob_P10.get();
        		break;
            case 11:  tmp = ParSets.glob_P11.get();
        		break;
            case 12:  tmp = ParSets.glob_P12.get();
        		break;
            case 13:  tmp = ParSets.glob_P13.get();
        		break;
            case 14:  tmp = ParSets.glob_P14.get();
        		break;
            case 15:  tmp = ParSets.glob_P15.get();
        		break;
            case 16:  tmp = ParSets.glob_P16.get();
            	break;
            case 17:  tmp = ParSets.glob_P17.get();
        		break;
            case 18:  tmp = ParSets.glob_P18.get();
        		break;
            case 19:  tmp = ParSets.glob_P19.get();
        		break;
            default:  tmp = ParSets.glob_P01.get();
        }
        return tmp;
    }
    
    public static int read_prog_param (int progNum, int paramInd) {
    	switch (progNum) {
		    case  1:  tmp = ParSets.glob_P01.get();
				break;
		    case  2:  tmp = ParSets.glob_P02.get();
		    	break;
		    case  3:  tmp = ParSets.glob_P03.get();
		    	break;
		    case  4:  tmp = ParSets.glob_P04.get();
		    	break;
		    case  5:  tmp = ParSets.glob_P05.get();
				break;                  
		    case  6:  tmp = ParSets.glob_P06.get();
				break;                  
		    case  7:  tmp = ParSets.glob_P07.get();
				break;                  
		    case  8:  tmp = ParSets.glob_P08.get();
				break;                  
		    case  9:  tmp = ParSets.glob_P09.get();
				break;                  
		    case 10:  tmp = ParSets.glob_P10.get();
				break;                  
		    case 11:  tmp = ParSets.glob_P11.get();
				break;                  
		    case 12:  tmp = ParSets.glob_P12.get();
				break;                  
		    case 13:  tmp = ParSets.glob_P13.get();
				break;                  
		    case 14:  tmp = ParSets.glob_P14.get();
				break;                  
		    case 15:  tmp = ParSets.glob_P15.get();
				break;                  
		    case 16:  tmp = ParSets.glob_P16.get();
		    	break;                  
		    case 17:  tmp = ParSets.glob_P17.get();
				break;                  
		    case 18:  tmp = ParSets.glob_P18.get();
				break;                  
		    case 19:  tmp = ParSets.glob_P19.get();
				break;                  
		    default:  tmp = ParSets.glob_P01.get();
        }
        return tmp[paramInd];
    }
    
    public static int read_version_param (int progNum, int paramInd) {
    	switch (progNum) {
		    case  1:  tmp = ParSets.Ver_No_P01.get();
				break;
		    case  2:  tmp = ParSets.Ver_No_P02.get();
		    	break;
		    case  3:  tmp = ParSets.Ver_No_P03.get();
		    	break;
		    case  4:  tmp = ParSets.Ver_No_P04.get();
		    	break;
		    case  5:  tmp = ParSets.Ver_No_P05.get();
				break;                  
		    case  6:  tmp = ParSets.Ver_No_P06.get();
				break;                  
		    case  7:  tmp = ParSets.Ver_No_P07.get();
				break;                  
		    case  8:  tmp = ParSets.Ver_No_P08.get();
				break;                  
		    case  9:  tmp = ParSets.Ver_No_P09.get();
				break;                  
		    case 10:  tmp = ParSets.Ver_No_P10.get();
				break;                  
		    case 11:  tmp = ParSets.Ver_No_P11.get();
				break;                  
		    case 12:  tmp = ParSets.Ver_No_P12.get();
				break;                  
		    case 13:  tmp = ParSets.Ver_No_P13.get();
				break;                  
		    case 14:  tmp = ParSets.Ver_No_P14.get();
				break;                  
		    case 15:  tmp = ParSets.Ver_No_P15.get();
				break;                  
		    case 16:  tmp = ParSets.Ver_No_P16.get();
		    	break;                  
		    case 17:  tmp = ParSets.Ver_No_P17.get();
				break;                  
		    case 18:  tmp = ParSets.Ver_No_P18.get();
				break;                  
		    case 19:  tmp = ParSets.Ver_No_P19.get();
				break;                  
		    default:  tmp = ParSets.Ver_No_P01.get();
        }
        return tmp[paramInd];
    }
    
    public static int getStructureParam (int progNum) {
    	switch (progNum) {
		    case  1:  tmp = ParSets.Struct_No_P01.get();
				break;
		    case  2:  tmp = ParSets.Struct_No_P02.get();
		    	break;
		    case  3:  tmp = ParSets.Struct_No_P03.get();
		    	break;
		    case  4:  tmp = ParSets.Struct_No_P04.get();
		    	break;
		    case  5:  tmp = ParSets.Struct_No_P05.get();
				break;                  
		    case  6:  tmp = ParSets.Struct_No_P06.get();
				break;                  
		    case  7:  tmp = ParSets.Struct_No_P07.get();
				break;                  
		    case  8:  tmp = ParSets.Struct_No_P08.get();
				break;                  
		    case  9:  tmp = ParSets.Struct_No_P09.get();
				break;                  
		    case 10:  tmp = ParSets.Struct_No_P10.get();
				break;                  
		    case 11:  tmp = ParSets.Struct_No_P11.get();
				break;                  
		    case 12:  tmp = ParSets.Struct_No_P12.get();
				break;                  
		    case 13:  tmp = ParSets.Struct_No_P13.get();
				break;                  
		    case 14:  tmp = ParSets.Struct_No_P14.get();
				break;                  
		    case 15:  tmp = ParSets.Struct_No_P15.get();
				break;                  
		    case 16:  tmp = ParSets.Struct_No_P16.get();
		    	break;                  
		    case 17:  tmp = ParSets.Struct_No_P17.get();
				break;                  
		    case 18:  tmp = ParSets.Struct_No_P18.get();
				break;                  
		    case 19:  tmp = ParSets.Struct_No_P19.get();
				break;                  
		    default:  tmp = ParSets.Struct_No_P01.get();
        }
        return tmp[0];
    }
    
    public static int getCycleParam(int progNum) {
    	switch (progNum) {
		    case  1:  tmp = ParSets.CyMax_P01.get();
				break;
		    case  2:  tmp = ParSets.CyMax_P02.get();
		    	break;
		    case  3:  tmp = ParSets.CyMax_P03.get();
		    	break;
		    case  4:  tmp = ParSets.CyMax_P04.get();
		    	break;
		    case  5:  tmp = ParSets.CyMax_P05.get();
				break;                  
		    case  6:  tmp = ParSets.CyMax_P06.get();
				break;                  
		    case  7:  tmp = ParSets.CyMax_P07.get();
				break;                  
		    case  8:  tmp = ParSets.CyMax_P08.get();
				break;                  
		    case  9:  tmp = ParSets.CyMax_P09.get();
				break;                  
		    case 10:  tmp = ParSets.CyMax_P10.get();
				break;                  
		    case 11:  tmp = ParSets.CyMax_P11.get();
				break;                  
		    case 12:  tmp = ParSets.CyMax_P12.get();
				break;                  
		    case 13:  tmp = ParSets.CyMax_P13.get();
				break;                  
		    case 14:  tmp = ParSets.CyMax_P14.get();
				break;                  
		    case 15:  tmp = ParSets.CyMax_P15.get();
				break;                  
		    case 16:  tmp = ParSets.CyMax_P16.get();
		    	break;                  
		    case 17:  tmp = ParSets.CyMax_P17.get();
				break;                  
		    case 18:  tmp = ParSets.CyMax_P18.get();
				break;                  
		    case 19:  tmp = ParSets.CyMax_P19.get();
				break;                  
		    default:  tmp = ParSets.CyMax_P01.get();
        }
        return tmp[0];
    }
    
    public static int getOffsetParam (int progNum) {
    	switch (progNum) {
		    case  1:  tmp = ParSets.Offset_P01.get();
				break;
		    case  2:  tmp = ParSets.Offset_P02.get();
		    	break;
		    case  3:  tmp = ParSets.Offset_P03.get();
		    	break;
		    case  4:  tmp = ParSets.Offset_P04.get();
		    	break;
		    case  5:  tmp = ParSets.Offset_P05.get();
				break;                  
		    case  6:  tmp = ParSets.Offset_P06.get();
				break;                  
		    case  7:  tmp = ParSets.Offset_P07.get();
				break;                  
		    case  8:  tmp = ParSets.Offset_P08.get();
				break;                  
		    case  9:  tmp = ParSets.Offset_P09.get();
				break;                  
		    case 10:  tmp = ParSets.Offset_P10.get();
				break;                  
		    case 11:  tmp = ParSets.Offset_P11.get();
				break;                  
		    case 12:  tmp = ParSets.Offset_P12.get();
				break;                  
		    case 13:  tmp = ParSets.Offset_P13.get();
				break;                  
		    case 14:  tmp = ParSets.Offset_P14.get();
				break;                  
		    case 15:  tmp = ParSets.Offset_P15.get();
				break;                  
		    case 16:  tmp = ParSets.Offset_P16.get();
		    	break;                  
		    case 17:  tmp = ParSets.Offset_P17.get();
				break;                  
		    case 18:  tmp = ParSets.Offset_P18.get();
				break;                  
		    case 19:  tmp = ParSets.Offset_P19.get();
				break;                  
		    default:  tmp = ParSets.Offset_P01.get();
        }
        return tmp[Var.indOffsetParam];
    }

    public static int readParam (vt.ProgBase prog, HaifaParametersIndexes paramInd) {
        switch (prog.getNummer()) {
            case  1:  tmp = ParSets.pars_P01.get();
            break;
            case  2:  tmp = ParSets.pars_P02.get();
            break;
            case  3:  tmp = ParSets.pars_P03.get();
            break;
            case  4:  tmp = ParSets.pars_P04.get();
            break;
            case  5:  tmp = ParSets.pars_P05.get();
            break;
            case  6:  tmp = ParSets.pars_P06.get();
            break;
            case  7:  tmp = ParSets.pars_P07.get();
            break;
            case  8:  tmp = ParSets.pars_P08.get();
            break;
            case  9:  tmp = ParSets.pars_P09.get();
            break;
            case 10:  tmp = ParSets.pars_P10.get();
            break;
            case 11:  tmp = ParSets.pars_P11.get();
            break;
            case 12:  tmp = ParSets.pars_P12.get();
            break;
            case 13:  tmp = ParSets.pars_P13.get();
            break;
            case 14:  tmp = ParSets.pars_P14.get();
            break;
            case 15:  tmp = ParSets.pars_P15.get();
            break;
            case 16:  tmp = ParSets.pars_P16.get();
            break;
            case 17:  tmp = ParSets.pars_P17.get();
    		break;
            case 18:  tmp = ParSets.pars_P18.get();
            break;
            case 19:  tmp = ParSets.pars_P19.get();
            break;
            case 95:  tmp = ParSets.r1.get();
            break;
            default:  tmp = ParSets.pars_P01.get();
        }
        return tmp[paramInd.getIndex()];
    }

    public static int readParamPolice(int index, boolean isFreePoint) {
    	if (!isFreePoint)
    		return ParSets.r1.get()[index];
    	else
    		return ParSets.r2.get()[index];
    }

    public static int readParam (int progNum, int paramInd) {
        switch (progNum) {
            case  1:  tmp = ParSets.pars_P01.get();
            break;
            case  2:  tmp = ParSets.pars_P02.get();
            break;
            case  3:  tmp = ParSets.pars_P03.get();
            break;
            case  4:  tmp = ParSets.pars_P04.get();
            break;
            case  5:  tmp = ParSets.pars_P05.get();
            break;
            case  6:  tmp = ParSets.pars_P06.get();
            break;
            case  7:  tmp = ParSets.pars_P07.get();
            break;
            case  8:  tmp = ParSets.pars_P08.get();
            break;
            case  9:  tmp = ParSets.pars_P09.get();
            break;
            case 10:  tmp = ParSets.pars_P10.get();
            break;
            case 11:  tmp = ParSets.pars_P11.get();
            break;
            case 12:  tmp = ParSets.pars_P12.get();
            break;
            case 13:  tmp = ParSets.pars_P13.get();
            break;
            case 14:  tmp = ParSets.pars_P14.get();
            break;
            case 15:  tmp = ParSets.pars_P15.get();
            break;
            case 16:  tmp = ParSets.pars_P16.get();
            break;
            case 17:  tmp = ParSets.pars_P17.get();
    		break;
            case 18:  tmp = ParSets.pars_P18.get();
            break;
            case 19:  tmp = ParSets.pars_P19.get();
            break;
            case 95:  tmp = ParSets.r1.get();
            break;
            default:  tmp = ParSets.pars_P01.get();
        }
        return tmp[paramInd];
    }

	/**
	 * Method for setting the flagClockCoordination
	 * @param prog
	 */
    public static boolean ifClockCoordination(TeilKnoten node, vt.Programm prog, boolean check) {
    	tmp_int_new = prog.getUmlaufZeit();
    	tmp_int1 = prog.getWarteZeit();

    	if (tmp_int1 < 999) {
    		if (tmp_int_new < 900) {
        		flagClockCoordination[node.getNummer()-1][prog.getNummer()] = true;
	    		/*tmp_int_new = tmp_int_new / 3;
	        	if (tmp_int1 != tmp_int_new) {prog.setWarteZeit(tmp_int_new);}*/
	        	return true;
    		}
    		prog.setWarteZeit(0);
        }
    	
    	if (Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv()) {
    		flagClockCoordination[node.getNummer()-1][prog.getNummer()] = check;
    	} else {
    		flagClockCoordination[node.getNummer()-1][prog.getNummer()] = false;
    	}
    	return false;
    }

	/**
	 * Method for checking if the cycle is fixed
	 * @param prog
	 */
    public static boolean ifClockCoordination(Node node, vt.Programm prog) {
    	if (Var.controller.isMaintenance()) {
    		if (node.isInMaintenance()) {
    			return flagClockCoordination[node.getNummer()-1][prog.getNummer() - SpecialProgram.MaintenanceFirst.getProgramNumber() + 1];
    		}
    	}
    	return flagClockCoordination[node.getNummer()-1][prog.getNummer()];
    }

	/**
	 * Method for changing the values of the program (syncA, syncB, waiting time, offset)
	 */
    public static void GlobalParam_update(Node tk) {
    	if (Var.controller.isAppJerusalem()) {
    		GlobalUpdateJ(tk);
    		return;
    	}
    	
    	if (Var.controller.isAppTelAviv()) {
    		GlobalUpdateTA(tk);
    		return;
    	}
    	
    	if (!Var.controller.isAppNetiveiIsrael()) {
    		return;
    	}
    	
    	if (ParSets.CyMax_P01 != null) { //Maatz applications
    		for (i=1; i<=Var.controller.getMaxProgramsNumber(); i++) {
    			GlobalUpdateM(Vt.findProgByNum(i, tk.getNummer()));
    		}
    	}
    	else { //Haifa applications
    		for (i=1; i<=Var.controller.getMaxProgramsNumber(); i++) {
    			GlobalUpdateH(Vt.findProgByNum(i, tk.getNummer()));
    		}
    	}
    }
    
    public static boolean isProgramFixedCycle(vt.Programm prog) {
    	return (prog.getUmlaufZeit() != 900);
    }
    
    private static int paramSyncAB;
    private static int paramCycle;
//    private static int paramWait;
    private static vt.Programm paramProg;
    private static vt.Programm paramProgMapOnly = null;
    
    public static void GlobalUpdateJ(Node node) {
    	if (node.isOffline() || !node.isRegularProgram()) {
    		return;
    	}
    	
    	paramProg = node.getAktProg();
		if (Var.controller.isMaintenance()) {
			paramProgMapOnly = Vt.findProgByNum(SpecialProgram.MaintenanceFirst.getProgramNumber(), node.getNummer());
		}
    	
		if (node.IsNoGwFixed() || node.IsGwFixed()) {
			paramSyncAB = ((ParametersJerusalem)Var.controller.dvi35Parameters).getOffset();
		} else {
			paramSyncAB = 0;
		}
    	paramCycle  = ((ParametersJerusalem)Var.controller.dvi35Parameters).getCycle();
//    	paramWait   = ((ParametersJerusalem)Var.controller.dvi35Parameters).getSYWaitTime();
		
		if (paramCycle > 0) {
			if (paramSyncAB == paramCycle) {
				paramSyncAB = 0;
			} else if (paramSyncAB > paramCycle) {
				paramSyncAB = paramSyncAB % paramCycle;
			}
		}

		if (paramSyncAB != paramProg.getGwpA()) {
    		paramProg.setGwpA(paramSyncAB);
    		if (paramProgMapOnly != null) {
    			paramProgMapOnly.setGwpA(paramSyncAB);
    		}
    	}
		
    	if (paramSyncAB != paramProg.getGwpB()) {
    		paramProg.setGwpB(paramSyncAB);
    		if (paramProgMapOnly != null) {
    			paramProgMapOnly.setGwpB(paramSyncAB);
    		}
    	}
    	
    	if (node.IsNoGwFixed() || node.IsGwFixed()) {
    		if (paramCycle != paramProg.getUmlaufZeit()) {
	    		paramProg.setUmlaufZeit(paramCycle);
	    		if (paramProgMapOnly != null) {
	    			paramProgMapOnly.setUmlaufZeit(paramCycle);
	    		}
	    	}
    	}
    }
    
    public static void GlobalUpdateTA(Node node) {
    	if (node.isOffline() || !node.isRegularProgram()) {
    		return;
    	}
    	
    	paramProg = node.getAktProg();
		if (Var.controller.isMaintenance()) {
			paramProgMapOnly = Vt.findProgByNum(SpecialProgram.MaintenanceFirst.getProgramNumber(), node.getNummer());
		}
    	
		if (Var.controller.isClockSync()) {
			paramSyncAB = ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(0) / Var.ONE_SEC;
		} else {
			paramSyncAB = 0;
		}
		
    	paramCycle  = ((ParametersTelAviv)Var.controller.dvi35Parameters).getCycle();
//    	paramWait   = ((ParametersTelAviv)Var.controller.dvi35Parameters).getSYWaitTime();
		
		if (paramCycle > 0) {
			if (paramSyncAB == paramCycle) {
				paramSyncAB = 0;
			} else if (paramSyncAB > paramCycle) {
				paramSyncAB = paramSyncAB % paramCycle;
			}
		}

		if (paramSyncAB != paramProg.getGwpA()) {
			if (((Node)paramProg.getTeilKnoten()).MainPhase.isAktiv() &&
				((Node)paramProg.getTeilKnoten()).MainPhase.getPhasenZeit() < Var.ONE_SEC) {
				
				if (paramProg.getTeilKnoten().getProgZeit() == paramProg.getGwpA() * Var.ONE_SEC)
					paramProg.getTeilKnoten().setProgZeit(paramSyncAB * Var.ONE_SEC);
				else if (paramProg.getTeilKnoten().getProgZeit() == paramProg.getGwpA() * Var.ONE_SEC - paramProg.getTeilKnoten().getZyklDauer())
					paramProg.getTeilKnoten().setProgZeit(paramSyncAB * Var.ONE_SEC - paramProg.getTeilKnoten().getZyklDauer());
			}
    		paramProg.setGwpA(paramSyncAB);
    		if (paramProgMapOnly != null) {
    			paramProgMapOnly.setGwpA(paramSyncAB);
    		}
    	}
		
    	if (paramSyncAB != paramProg.getGwpB()) {
    		paramProg.setGwpB(paramSyncAB);
    		if (paramProgMapOnly != null) {
    			paramProgMapOnly.setGwpB(paramSyncAB);
    		}
    	}
    	
		if (paramCycle != paramProg.getUmlaufZeit()) {
			if (paramCycle != 0 || paramProg.getUmlaufZeit() != 900) {
				paramProg.setUmlaufZeit(paramCycle != 0 ? paramCycle : 900);
				if (paramProgMapOnly != null) {
					paramProgMapOnly.setUmlaufZeit(paramCycle != 0 ? paramCycle : 900);
				}
			}
    	}
    }

	/**
	 * Method for changing the values of the program cycle time - for Haifa)
	 */
    public static void GlobalUpdateH(vt.Programm prog) {
    	tmp_int1 = prog.getUmlaufZeit();  //cycle time
    	tmp_int2 = prog.getVersatzZeit(); //offset time
    	
    	switch (prog.getNummer()) {
		    case  1:
		    	tmp = ParSets.pars_P01.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P01.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  2:
		    	tmp = ParSets.pars_P02.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P02.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  3:
		    	tmp = ParSets.pars_P03.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P03.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  4:
		    	tmp = ParSets.pars_P04.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P04.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  5:
		    	tmp = ParSets.pars_P05.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P05.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  6:
		    	tmp = ParSets.pars_P06.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P06.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  7:
		    	tmp = ParSets.pars_P07.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P07.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  8:
		    	tmp = ParSets.pars_P08.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P08.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case  9:
		    	tmp = ParSets.pars_P09.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P09.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 10:
		    	tmp = ParSets.pars_P10.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P10.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 11:
		    	tmp = ParSets.pars_P11.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P11.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 12:
		    	tmp = ParSets.pars_P12.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P12.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 13:
		    	tmp = ParSets.pars_P13.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P13.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 14:
		    	tmp = ParSets.pars_P14.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P14.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 15:
		    	tmp = ParSets.pars_P15.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P15.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 16:
		    	tmp = ParSets.pars_P16.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P16.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 17:
		    	tmp = ParSets.pars_P17.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P17.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 18:
		    	tmp = ParSets.pars_P18.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P18.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
		    
		    case 19:
		    	tmp = ParSets.pars_P19.get();
		    	tmp_int_new = tmp[HaifaParametersIndexes.indCycleTime.getIndex()];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.pars_P19.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
	
		    	tmp_int_new = tmp[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
    	}
    }

	/**
	 * Method for changing the values of the program cycle time - for Maatz)
	 */
    public static void GlobalUpdateM(vt.Programm prog) {
    	tmp_int1 = prog.getUmlaufZeit();  //cycle time
    	tmp_int2 = prog.getVersatzZeit(); //offset time
    	
    	switch (prog.getNummer()) {
		    case  1:
		    	tmp_int_new = ParSets.CyMax_P01.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P01.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P01.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  2:
		    	tmp_int_new = ParSets.CyMax_P02.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P02.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P02.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  3:
		    	tmp_int_new = ParSets.CyMax_P03.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P03.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P03.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  4:
		    	tmp_int_new = ParSets.CyMax_P04.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P04.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P04.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  5:
		    	tmp_int_new = ParSets.CyMax_P05.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P05.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P05.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  6:
		    	tmp_int_new = ParSets.CyMax_P06.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P06.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P06.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  7:
		    	tmp_int_new = ParSets.CyMax_P07.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P07.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P07.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  8:
		    	tmp_int_new = ParSets.CyMax_P08.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P08.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P08.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case  9:
		    	tmp_int_new = ParSets.CyMax_P09.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P09.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P09.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 10:
		    	tmp_int_new = ParSets.CyMax_P10.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P10.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P10.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 11:
		    	tmp_int_new = ParSets.CyMax_P11.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P11.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P11.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 12:
		    	tmp_int_new = ParSets.CyMax_P12.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P12.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P12.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 13:
		    	tmp_int_new = ParSets.CyMax_P13.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P13.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P13.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 14:
		    	tmp_int_new = ParSets.CyMax_P14.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P14.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P14.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 15:
		    	tmp_int_new = ParSets.CyMax_P15.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P15.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P15.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 16:
		    	tmp_int_new = ParSets.CyMax_P16.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P16.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P16.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 17:
		    	tmp_int_new = ParSets.CyMax_P17.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P17.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P17.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 18:
		    	tmp_int_new = ParSets.CyMax_P18.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P18.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P18.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
	    
		    case 19:
		    	tmp_int_new = ParSets.CyMax_P19.get()[Var.indCycleParam];
		    	
		    	if (tmp_int_new != 0) {
		    		if (tmp_int1 != tmp_int_new) {
		    			if (tmp_int1 < 900) {
			    			if (tmp_int_new >= 900){ParSets.CyMax_P19.set(Var.indCycleParam, tmp_int1);}
			    			else {
			    				prog.setUmlaufZeit(tmp_int_new);
			    				prog.setWarteZeit(tmp_int_new/3);
			    			}
		    			}
		    		}
		    	}
		    	
		    	tmp_int_new = ParSets.Offset_P19.get()[Var.indOffsetParam];
	    		if (tmp_int2 != tmp_int_new){prog.setVersatzZeit(tmp_int_new);}
		    	break;
    	}
    }
    
	/**
	 * Method for changing the values of the program cycle time - for Haifa)
	 */
    public static void GlobalUpdateHaifa(Node tk, vt.Programm prog) {
    	if (!tk.isRegularProgram() || !Var.controller.isAppHaifa()) {
    		return;
    	}
		GlobalUpdateHaifaTK(Var.controller.dvi35Parameters.parameters, prog);
    }
    
    public static void GlobalUpdateHaifaTK(int[] CurrentParam_tk, vt.Programm prog) {
    	tmp_int1 = prog.getUmlaufZeit();  //cycle time

    	if (CurrentParam_tk[HaifaParametersIndexes.indCycleTime.getIndex()] != 0) {
    		if (tmp_int1 != CurrentParam_tk[HaifaParametersIndexes.indCycleTime.getIndex()]) {
    			if (tmp_int1 < 900) {
	    			if (CurrentParam_tk[HaifaParametersIndexes.indCycleTime.getIndex()] >= 900)
	    			{
	    		    	switch (prog.getNummer()) {
		    			    case  1:
		    			    	ParSets.pars_P01.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  2:
		    			    	ParSets.pars_P02.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  3:
		    			    	ParSets.pars_P03.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  4:
		    			    	ParSets.pars_P04.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  5:
		    			    	ParSets.pars_P05.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  6:
		    			    	ParSets.pars_P06.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  7:
		    			    	ParSets.pars_P07.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  8:
		    			    	ParSets.pars_P08.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case  9:
		    			    	ParSets.pars_P09.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 10:
		    			    	ParSets.pars_P10.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 11:
		    			    	ParSets.pars_P11.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 12:
		    			    	ParSets.pars_P12.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 13:
		    			    	ParSets.pars_P13.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 14:
		    			    	ParSets.pars_P14.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 15:
		    			    	ParSets.pars_P15.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 16:
		    			    	ParSets.pars_P16.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 17:
		    			    	ParSets.pars_P17.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 18:
		    			    	ParSets.pars_P18.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
		    			    	
		    			    case 19:
		    			    	ParSets.pars_P19.set(HaifaParametersIndexes.indCycleTime.getIndex(), tmp_int1);
		    			    	break;
	    		    	}
	    			}
	    			else {
	    				prog.setUmlaufZeit(CurrentParam_tk[HaifaParametersIndexes.indCycleTime.getIndex()]);
	    				prog.setWarteZeit(CurrentParam_tk[HaifaParametersIndexes.indCycleTime.getIndex()]/3);
	    			}
    			}
    		}
    	}

		if (prog.getVersatzZeit() != CurrentParam_tk[HaifaParametersIndexes.indGreenWaveOffset.getIndex()]) {
			prog.setVersatzZeit(CurrentParam_tk[HaifaParametersIndexes.indGreenWaveOffset.getIndex()]);
		}
    }
}