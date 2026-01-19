package ta118;

import special.Dvi35ParametersBase;
import special.VAProg;
import tk.ParSets;
import tk.Var;
import vtvar.VtVarStrukt;

/**
 * @author Ilia Butvinnik
 * @version 0.0.1
 * @since 01/06/2019
 */
public class ParametersTelAviv extends Dvi35ParametersBase {
	
	public ParametersTelAviv() {
		super(200);
	}
	
	//indexes of constant parameters
	public static int min = 51;
	public static int max = 66;
	public static int typ = 81;
	public static final int StructIndex         = 95;
	public static final int CycleTimeIndex      = 96;

    // write parameters here






	//TODO:
	public void setGaps() {
//		Var.tk1.e1.gapUnit = VarInt.init(Var.tk1, "GAP E-1" , 1000, true, true, true);
//		Var.tk1.e2.gapUnit = VarInt.init(Var.tk1, "GAP E-2" , 1000, true, true, true);
	}

	static int[] ulfail = new int[] { 20 };

	public void SetParameters() {
		setGaps();
		
		ParSets.pars_P01 = VtVarStrukt.init(Var.tk1, "DVI35_P01", initializeParameters(DVI35_P01, parameters_indexes, new int[] {  1, 51 }), true, true, true);
		ParSets.pars_P02 = VtVarStrukt.init(Var.tk1, "DVI35_P02", initializeParameters(DVI35_P02, parameters_indexes, new int[] {  2, 52 }), true, true, true);
		ParSets.pars_P03 = VtVarStrukt.init(Var.tk1, "DVI35_P03", initializeParameters(DVI35_P03, parameters_indexes, new int[] {  3, 53 }), true, true, true);
		ParSets.pars_P04 = VtVarStrukt.init(Var.tk1, "DVI35_P04", initializeParameters(DVI35_P04, parameters_indexes, new int[] {  4, 54 }), true, true, true);
		ParSets.pars_P05 = VtVarStrukt.init(Var.tk1, "DVI35_P05", initializeParameters(DVI35_P05, parameters_indexes, new int[] {  5, 55 }), true, true, true);
		ParSets.pars_P06 = VtVarStrukt.init(Var.tk1, "DVI35_P06", initializeParameters(DVI35_P06, parameters_indexes, new int[] {  6, 56 }), true, true, true);	    
		ParSets.pars_P07 = VtVarStrukt.init(Var.tk1, "DVI35_P07", initializeParameters(DVI35_P07, parameters_indexes, new int[] {  7, 57 }), true, true, true);
		ParSets.pars_P08 = VtVarStrukt.init(Var.tk1, "DVI35_P08", initializeParameters(DVI35_P08, parameters_indexes, new int[] {  8, 58 }), true, true, true);       	    	    
		ParSets.pars_P09 = VtVarStrukt.init(Var.tk1, "DVI35_P09", initializeParameters(DVI35_P09, parameters_indexes, new int[] {  9, 59 }), true, true, true);
		ParSets.pars_P10 = VtVarStrukt.init(Var.tk1, "DVI35_P10", initializeParameters(DVI35_P10, parameters_indexes, new int[] { 10, 60 }), true, true, true);	    
		ParSets.pars_P11 = VtVarStrukt.init(Var.tk1, "DVI35_P11", initializeParameters(DVI35_P11, parameters_indexes, new int[] { 11, 61 }), true, true, true);
		ParSets.pars_P12 = VtVarStrukt.init(Var.tk1, "DVI35_P12", initializeParameters(DVI35_P12, parameters_indexes, new int[] { 12, 62 }), true, true, true);
		ParSets.pars_P13 = VtVarStrukt.init(Var.tk1, "DVI35_P13", initializeParameters(DVI35_P13, parameters_indexes, new int[] { 13, 63 }), true, true, true);
		ParSets.pars_P14 = VtVarStrukt.init(Var.tk1, "DVI35_P14", initializeParameters(DVI35_P14, parameters_indexes, new int[] { 14, 64 }), true, true, true);
		ParSets.pars_P15 = VtVarStrukt.init(Var.tk1, "DVI35_P15", initializeParameters(DVI35_P15, parameters_indexes, new int[] { 15, 65 }), true, true, true);	    
		ParSets.pars_P16 = VtVarStrukt.init(Var.tk1, "DVI35_P16", initializeParameters(DVI35_P16, parameters_indexes, new int[] { 16, 66 }), true, true, true);	    
		ParSets.pars_P17 = VtVarStrukt.init(Var.tk1, "DVI35_P17", initializeParameters(DVI35_P17, parameters_indexes, new int[] { 17, 67 }), true, true, true);	    
		ParSets.pars_P18 = VtVarStrukt.init(Var.tk1, "DVI35_P18", initializeParameters(DVI35_P18, parameters_indexes, new int[] { 18, 68 }), true, true, true);	    
		ParSets.pars_P19 = VtVarStrukt.init(Var.tk1, "DVI35_P19", initializeParameters(DVI35_P19, parameters_indexes, new int[] { 19, 69 }), true, true, true);	    
		ParSets.pars_P20 = VtVarStrukt.init(Var.tk1, "DVI35_P20", initializeParameters(DVI35_P20, parameters_indexes, new int[] { 20, 70 }), true, true, true);	    
		ParSets.pars_P21 = VtVarStrukt.init(Var.tk1, "DVI35_P21", initializeParameters(DVI35_P21, parameters_indexes, new int[] { 21, 71 }), true, true, true);	    
		ParSets.pars_P22 = VtVarStrukt.init(Var.tk1, "DVI35_P22", initializeParameters(DVI35_P22, parameters_indexes, new int[] { 22, 72 }), true, true, true);	    
		ParSets.pars_P23 = VtVarStrukt.init(Var.tk1, "DVI35_P23", initializeParameters(DVI35_P23, parameters_indexes, new int[] { 23, 73 }), true, true, true);	    
		ParSets.pars_P24 = VtVarStrukt.init(Var.tk1, "DVI35_P24", initializeParameters(DVI35_P24, parameters_indexes, new int[] { 24, 74 }), true, true, true);	    
		ParSets.pars_P25 = VtVarStrukt.init(Var.tk1, "DVI35_P25", initializeParameters(DVI35_P25, parameters_indexes, new int[] { 25, 75 }), true, true, true);	    
		ParSets.pars_P26 = VtVarStrukt.init(Var.tk1, "DVI35_P26", initializeParameters(DVI35_P26, parameters_indexes, new int[] { 26, 76 }), true, true, true);	    
		ParSets.pars_P27 = VtVarStrukt.init(Var.tk1, "DVI35_P27", initializeParameters(DVI35_P27, parameters_indexes, new int[] { 27, 77 }), true, true, true);	    
		ParSets.pars_P28 = VtVarStrukt.init(Var.tk1, "DVI35_P28", initializeParameters(DVI35_P28, parameters_indexes, new int[] { 28, 78 }), true, true, true);	    
		ParSets.pars_P29 = VtVarStrukt.init(Var.tk1, "DVI35_P29", initializeParameters(DVI35_P29, parameters_indexes, new int[] { 29, 79 }), true, true, true);	    
		ParSets.pars_P30 = VtVarStrukt.init(Var.tk1, "DVI35_P30", initializeParameters(DVI35_P30, parameters_indexes, new int[] { 30, 80 }), true, true, true);	    
		ParSets.pars_P31 = VtVarStrukt.init(Var.tk1, "DVI35_P31", initializeParameters(DVI35_P31, parameters_indexes, new int[] { 31, 81 }), true, true, true);	    
		ParSets.pars_P32 = VtVarStrukt.init(Var.tk1, "DVI35_P32", initializeParameters(DVI35_P32, parameters_indexes, new int[] { 32, 82 }), true, true, true);

		ParSets.ULFailParam = VtVarStrukt.init(Var.tk1, "ULFail", ulfail, true, true, true);
	}
	
	public int getMinimum(int index) {
		return Var.controller.dvi35Parameters.parameters[50 + index] * Var.ONE_SEC;
	}
	
	public int getMaximum(int index) {
		return Var.controller.dvi35Parameters.parameters[65 + index] * Var.ONE_SEC;
	}
	
	public int getType(int index) {
		return Var.controller.dvi35Parameters.parameters[80 + index];
	}
	
	public int getStructure() {
		return Var.controller.dvi35Parameters.parameters[StructIndex];
	}
	
	public int getCycle() {
		return Var.controller.dvi35Parameters.parameters[CycleTimeIndex];
	}
}
