package ta172;

import pars.*;
import special.Node;
import vtvar.ParameterDaten;
import vtvar.VtVarStrukt;

public class InitParametersMaatz {

	/**
	 * Initializes the Gap units of all the extension detectors
	 */
	private static void SetGaps() 
	{
//		Var.tk1.e4.gapUnit = VarInt.init(Var.tk1, "GAP E-4"  , 1000  , true, true, true);
	}

	/**
	 * Initializes the IsGWEnabled parameter
	 */
	private static void SetGWEnable() 
	{
		int[] parsP01    = new int[] {    0 };
		int[] parsP02    = new int[] {    0 };
		int[] parsP03    = new int[] {    0 };
		int[] parsP04    = new int[] {    0 };
		int[] parsP05    = new int[] {    0 };
		int[] parsP06    = new int[] {    0 };
		int[] parsP07    = new int[] {    0 };
		int[] parsP08    = new int[] {    0 };
		int[] parsP09    = new int[] {    0 };
		int[] parsP10    = new int[] {    0 };
		int[] parsP11    = new int[] {    0 };
		int[] parsP12    = new int[] {    0 };
		int[] parsP13    = new int[] {    0 };
		int[] parsP14    = new int[] {    0 };
		int[] parsP15    = new int[] {    0 };
		int[] parsP16    = new int[] {    0 };
		int[] parsP17    = new int[] {    0 };
		int[] parsP18    = new int[] {    0 };
		int[] parsP19    = new int[] {    0 };

		ParSets.glob_P01 = VtVarStrukt.init(Var.tk1, "Z_P01", parsP01, true, true, true);
		ParSets.glob_P02 = VtVarStrukt.init(Var.tk1, "Z_P02", parsP02, true, true, true);
		ParSets.glob_P03 = VtVarStrukt.init(Var.tk1, "Z_P03", parsP03, true, true, true);
		ParSets.glob_P04 = VtVarStrukt.init(Var.tk1, "Z_P04", parsP04, true, true, true);
		ParSets.glob_P05 = VtVarStrukt.init(Var.tk1, "Z_P05", parsP05, true, true, true);
		ParSets.glob_P06 = VtVarStrukt.init(Var.tk1, "Z_P06", parsP06, true, true, true);
		ParSets.glob_P07 = VtVarStrukt.init(Var.tk1, "Z_P07", parsP07, true, true, true);
		ParSets.glob_P08 = VtVarStrukt.init(Var.tk1, "Z_P08", parsP08, true, true, true);
		ParSets.glob_P09 = VtVarStrukt.init(Var.tk1, "Z_P09", parsP09, true, true, true);
		ParSets.glob_P10 = VtVarStrukt.init(Var.tk1, "Z_P10", parsP10, true, true, true);
		ParSets.glob_P11 = VtVarStrukt.init(Var.tk1, "Z_P11", parsP11, true, true, true);
		ParSets.glob_P12 = VtVarStrukt.init(Var.tk1, "Z_P12", parsP12, true, true, true);
		ParSets.glob_P13 = VtVarStrukt.init(Var.tk1, "Z_P13", parsP13, true, true, true);
		ParSets.glob_P14 = VtVarStrukt.init(Var.tk1, "Z_P14", parsP14, true, true, true);
		ParSets.glob_P15 = VtVarStrukt.init(Var.tk1, "Z_P15", parsP15, true, true, true);
		ParSets.glob_P16 = VtVarStrukt.init(Var.tk1, "Z_P16", parsP16, true, true, true);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParSets.glob_P17 = VtVarStrukt.init(Var.tk1, "Z_P17", parsP17, true, true, true);
			ParSets.glob_P18 = VtVarStrukt.init(Var.tk1, "Z_P18", parsP18, true, true, true);
			ParSets.glob_P19 = VtVarStrukt.init(Var.tk1, "Z_P19", parsP19, true, true, true);
		}
	}

	/**
	 * Initializes the GW Offset parameter
	 */
	private static void SetOffset() 
	{
		int[] parsOffsetP01    = new int[] {    0 };
		int[] parsOffsetP02    = new int[] {    0 };
		int[] parsOffsetP03    = new int[] {    0 };
		int[] parsOffsetP04    = new int[] {    0 };
		int[] parsOffsetP05    = new int[] {    0 };
		int[] parsOffsetP06    = new int[] {    0 };
		int[] parsOffsetP07    = new int[] {    0 };
		int[] parsOffsetP08    = new int[] {    0 };
		int[] parsOffsetP09    = new int[] {    0 };
		int[] parsOffsetP10    = new int[] {    0 };
		int[] parsOffsetP11    = new int[] {    0 };
		int[] parsOffsetP12    = new int[] {    0 };
		int[] parsOffsetP13    = new int[] {    0 };
		int[] parsOffsetP14    = new int[] {    0 };
		int[] parsOffsetP15    = new int[] {    0 };
		int[] parsOffsetP16    = new int[] {    0 };
		int[] parsOffsetP17    = new int[] {    0 };
		int[] parsOffsetP18    = new int[] {    0 };
		int[] parsOffsetP19    = new int[] {    0 };

		ParSets.Offset_P01 = VtVarStrukt.init(Var.tk1, "Offset_P01", parsOffsetP01, true, true, true);
		ParSets.Offset_P02 = VtVarStrukt.init(Var.tk1, "Offset_P02", parsOffsetP02, true, true, true);
		ParSets.Offset_P03 = VtVarStrukt.init(Var.tk1, "Offset_P03", parsOffsetP03, true, true, true);
		ParSets.Offset_P04 = VtVarStrukt.init(Var.tk1, "Offset_P04", parsOffsetP04, true, true, true);
		ParSets.Offset_P05 = VtVarStrukt.init(Var.tk1, "Offset_P05", parsOffsetP05, true, true, true);
		ParSets.Offset_P06 = VtVarStrukt.init(Var.tk1, "Offset_P06", parsOffsetP06, true, true, true);
		ParSets.Offset_P07 = VtVarStrukt.init(Var.tk1, "Offset_P07", parsOffsetP07, true, true, true);
		ParSets.Offset_P08 = VtVarStrukt.init(Var.tk1, "Offset_P08", parsOffsetP08, true, true, true);
		ParSets.Offset_P09 = VtVarStrukt.init(Var.tk1, "Offset_P09", parsOffsetP09, true, true, true);
		ParSets.Offset_P10 = VtVarStrukt.init(Var.tk1, "Offset_P10", parsOffsetP10, true, true, true);
		ParSets.Offset_P11 = VtVarStrukt.init(Var.tk1, "Offset_P11", parsOffsetP11, true, true, true);
		ParSets.Offset_P12 = VtVarStrukt.init(Var.tk1, "Offset_P12", parsOffsetP12, true, true, true);
		ParSets.Offset_P13 = VtVarStrukt.init(Var.tk1, "Offset_P13", parsOffsetP13, true, true, true);
		ParSets.Offset_P14 = VtVarStrukt.init(Var.tk1, "Offset_P14", parsOffsetP14, true, true, true);
		ParSets.Offset_P15 = VtVarStrukt.init(Var.tk1, "Offset_P15", parsOffsetP15, true, true, true);
		ParSets.Offset_P16 = VtVarStrukt.init(Var.tk1, "Offset_P16", parsOffsetP16, true, true, true);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParSets.Offset_P17 = VtVarStrukt.init(Var.tk1, "Offset_P17", parsOffsetP17, true, true, true);
			ParSets.Offset_P18 = VtVarStrukt.init(Var.tk1, "Offset_P18", parsOffsetP18, true, true, true);
			ParSets.Offset_P19 = VtVarStrukt.init(Var.tk1, "Offset_P19", parsOffsetP19, true, true, true);
		}
	}

	/**
	 * Initializes program's cycle time
	 */
	private static void SetCycle() 
	{
		int[] parsCycleP01    = new int[] {    0 };
		int[] parsCycleP02    = new int[] {    0 };
		int[] parsCycleP03    = new int[] {    0 };
		int[] parsCycleP04    = new int[] {    0 };
		int[] parsCycleP05    = new int[] {    0 };
		int[] parsCycleP06    = new int[] {    0 };
		int[] parsCycleP07    = new int[] {    0 };
		int[] parsCycleP08    = new int[] {    0 };
		int[] parsCycleP09    = new int[] {    0 };
		int[] parsCycleP10    = new int[] {    0 };
		int[] parsCycleP11    = new int[] {    0 };
		int[] parsCycleP12    = new int[] {    0 };
		int[] parsCycleP13    = new int[] {    0 };
		int[] parsCycleP14    = new int[] {    0 };
		int[] parsCycleP15    = new int[] {    0 };
		int[] parsCycleP16    = new int[] {    0 };
		int[] parsCycleP17    = new int[] {    0 };
		int[] parsCycleP18    = new int[] {    0 };
		int[] parsCycleP19    = new int[] {    0 };

		ParSets.CyMax_P01 = VtVarStrukt.init(Var.tk1, "CyMax_P01", parsCycleP01, true, true, true);
		ParSets.CyMax_P02 = VtVarStrukt.init(Var.tk1, "CyMax_P02", parsCycleP02, true, true, true);
		ParSets.CyMax_P03 = VtVarStrukt.init(Var.tk1, "CyMax_P03", parsCycleP03, true, true, true);
		ParSets.CyMax_P04 = VtVarStrukt.init(Var.tk1, "CyMax_P04", parsCycleP04, true, true, true);
		ParSets.CyMax_P05 = VtVarStrukt.init(Var.tk1, "CyMax_P05", parsCycleP05, true, true, true);
		ParSets.CyMax_P06 = VtVarStrukt.init(Var.tk1, "CyMax_P06", parsCycleP06, true, true, true);
		ParSets.CyMax_P07 = VtVarStrukt.init(Var.tk1, "CyMax_P07", parsCycleP07, true, true, true);
		ParSets.CyMax_P08 = VtVarStrukt.init(Var.tk1, "CyMax_P08", parsCycleP08, true, true, true);
		ParSets.CyMax_P09 = VtVarStrukt.init(Var.tk1, "CyMax_P09", parsCycleP09, true, true, true);
		ParSets.CyMax_P10 = VtVarStrukt.init(Var.tk1, "CyMax_P10", parsCycleP10, true, true, true);
		ParSets.CyMax_P11 = VtVarStrukt.init(Var.tk1, "CyMax_P11", parsCycleP11, true, true, true);
		ParSets.CyMax_P12 = VtVarStrukt.init(Var.tk1, "CyMax_P12", parsCycleP12, true, true, true);
		ParSets.CyMax_P13 = VtVarStrukt.init(Var.tk1, "CyMax_P13", parsCycleP13, true, true, true);
		ParSets.CyMax_P14 = VtVarStrukt.init(Var.tk1, "CyMax_P14", parsCycleP14, true, true, true);
		ParSets.CyMax_P15 = VtVarStrukt.init(Var.tk1, "CyMax_P15", parsCycleP15, true, true, true);
		ParSets.CyMax_P16 = VtVarStrukt.init(Var.tk1, "CyMax_P16", parsCycleP16, true, true, true);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParSets.CyMax_P17 = VtVarStrukt.init(Var.tk1, "CyMax_P17", parsCycleP17, true, true, true);
			ParSets.CyMax_P18 = VtVarStrukt.init(Var.tk1, "CyMax_P18", parsCycleP18, true, true, true);
			ParSets.CyMax_P19 = VtVarStrukt.init(Var.tk1, "CyMax_P19", parsCycleP19, true, true, true);
		}
	}

	/**
	 * Initializes the Structure parameter
	 */
	private static void SetStruct() 
	{
		// IMPORTANT!!! structure parameter must always start from 1 (can't be 0!)
		int[] parsStructP01    = new int[] {    1 };
		int[] parsStructP02    = new int[] {    1 };
		int[] parsStructP03    = new int[] {    1 };
		int[] parsStructP04    = new int[] {    1 };
		int[] parsStructP05    = new int[] {    1 };
		int[] parsStructP06    = new int[] {    1 };
		int[] parsStructP07    = new int[] {    1 };
		int[] parsStructP08    = new int[] {    1 };
		int[] parsStructP09    = new int[] {    1 };
		int[] parsStructP10    = new int[] {    1 };
		int[] parsStructP11    = new int[] {    1 };
		int[] parsStructP12    = new int[] {    1 };
		int[] parsStructP13    = new int[] {    1 };
		int[] parsStructP14    = new int[] {    1 };
		int[] parsStructP15    = new int[] {    1 };
		int[] parsStructP16    = new int[] {    1 };
		int[] parsStructP17    = new int[] {    1 };
		int[] parsStructP18    = new int[] {    1 };
		int[] parsStructP19    = new int[] {    1 };

		ParSets.Struct_No_P01 = VtVarStrukt.init(Var.tk1, "Struct_No_P01", parsStructP01, true, true, true);
		ParSets.Struct_No_P02 = VtVarStrukt.init(Var.tk1, "Struct_No_P02", parsStructP02, true, true, true);
		ParSets.Struct_No_P03 = VtVarStrukt.init(Var.tk1, "Struct_No_P03", parsStructP03, true, true, true);
		ParSets.Struct_No_P04 = VtVarStrukt.init(Var.tk1, "Struct_No_P04", parsStructP04, true, true, true);
		ParSets.Struct_No_P05 = VtVarStrukt.init(Var.tk1, "Struct_No_P05", parsStructP05, true, true, true);
		ParSets.Struct_No_P06 = VtVarStrukt.init(Var.tk1, "Struct_No_P06", parsStructP06, true, true, true);
		ParSets.Struct_No_P07 = VtVarStrukt.init(Var.tk1, "Struct_No_P07", parsStructP07, true, true, true);
		ParSets.Struct_No_P08 = VtVarStrukt.init(Var.tk1, "Struct_No_P08", parsStructP08, true, true, true);
		ParSets.Struct_No_P09 = VtVarStrukt.init(Var.tk1, "Struct_No_P09", parsStructP09, true, true, true);
		ParSets.Struct_No_P10 = VtVarStrukt.init(Var.tk1, "Struct_No_P10", parsStructP10, true, true, true);
		ParSets.Struct_No_P11 = VtVarStrukt.init(Var.tk1, "Struct_No_P11", parsStructP11, true, true, true);
		ParSets.Struct_No_P12 = VtVarStrukt.init(Var.tk1, "Struct_No_P12", parsStructP12, true, true, true);
		ParSets.Struct_No_P13 = VtVarStrukt.init(Var.tk1, "Struct_No_P13", parsStructP13, true, true, true);
		ParSets.Struct_No_P14 = VtVarStrukt.init(Var.tk1, "Struct_No_P14", parsStructP14, true, true, true);
		ParSets.Struct_No_P15 = VtVarStrukt.init(Var.tk1, "Struct_No_P15", parsStructP15, true, true, true);
		ParSets.Struct_No_P16 = VtVarStrukt.init(Var.tk1, "Struct_No_P16", parsStructP16, true, true, true);
		ParSets.Struct_No_P17 = VtVarStrukt.init(Var.tk1, "Struct_No_P17", parsStructP17, true, true, true);
		ParSets.Struct_No_P18 = VtVarStrukt.init(Var.tk1, "Struct_No_P18", parsStructP18, true, true, true);
		ParSets.Struct_No_P19 = VtVarStrukt.init(Var.tk1, "Struct_No_P19", parsStructP19, true, true, true);
	}

	/**
	 * Initializes the Version (of the parameters table) parameter
	 */
	private static void SetVersion() 
	{
		int[] parsVerP01    = new int[] {    1 };
		int[] parsVerP02    = new int[] {    1 };
		int[] parsVerP03    = new int[] {    1 };
		int[] parsVerP04    = new int[] {    1 };
		int[] parsVerP05    = new int[] {    1 };
		int[] parsVerP06    = new int[] {    1 };
		int[] parsVerP07    = new int[] {    1 };
		int[] parsVerP08    = new int[] {    1 };
		int[] parsVerP09    = new int[] {    1 };
		int[] parsVerP10    = new int[] {    1 };
		int[] parsVerP11    = new int[] {    1 };
		int[] parsVerP12    = new int[] {    1 };
		int[] parsVerP13    = new int[] {    1 };
		int[] parsVerP14    = new int[] {    1 };
		int[] parsVerP15    = new int[] {    1 };
		int[] parsVerP16    = new int[] {    1 };
		int[] parsVerP17    = new int[] {    1 };
		int[] parsVerP18    = new int[] {    1 };
		int[] parsVerP19    = new int[] {    1 };

		ParSets.Ver_No_P01 = VtVarStrukt.init(Var.tk1, "Ver_No_P01", parsVerP01, true, true, true);
		ParSets.Ver_No_P02 = VtVarStrukt.init(Var.tk1, "Ver_No_P02", parsVerP02, true, true, true);
		ParSets.Ver_No_P03 = VtVarStrukt.init(Var.tk1, "Ver_No_P03", parsVerP03, true, true, true);
		ParSets.Ver_No_P04 = VtVarStrukt.init(Var.tk1, "Ver_No_P04", parsVerP04, true, true, true);
		ParSets.Ver_No_P05 = VtVarStrukt.init(Var.tk1, "Ver_No_P05", parsVerP05, true, true, true);
		ParSets.Ver_No_P06 = VtVarStrukt.init(Var.tk1, "Ver_No_P06", parsVerP06, true, true, true);
		ParSets.Ver_No_P07 = VtVarStrukt.init(Var.tk1, "Ver_No_P07", parsVerP07, true, true, true);
		ParSets.Ver_No_P08 = VtVarStrukt.init(Var.tk1, "Ver_No_P08", parsVerP08, true, true, true);
		ParSets.Ver_No_P09 = VtVarStrukt.init(Var.tk1, "Ver_No_P09", parsVerP09, true, true, true);
		ParSets.Ver_No_P10 = VtVarStrukt.init(Var.tk1, "Ver_No_P10", parsVerP10, true, true, true);
		ParSets.Ver_No_P11 = VtVarStrukt.init(Var.tk1, "Ver_No_P11", parsVerP11, true, true, true);
		ParSets.Ver_No_P12 = VtVarStrukt.init(Var.tk1, "Ver_No_P12", parsVerP12, true, true, true);
		ParSets.Ver_No_P13 = VtVarStrukt.init(Var.tk1, "Ver_No_P13", parsVerP13, true, true, true);
		ParSets.Ver_No_P14 = VtVarStrukt.init(Var.tk1, "Ver_No_P14", parsVerP14, true, true, true);
		ParSets.Ver_No_P15 = VtVarStrukt.init(Var.tk1, "Ver_No_P15", parsVerP15, true, true, true);
		ParSets.Ver_No_P16 = VtVarStrukt.init(Var.tk1, "Ver_No_P16", parsVerP16, true, true, true);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParSets.Ver_No_P17 = VtVarStrukt.init(Var.tk1, "Ver_No_P17", parsVerP17, true, true, true);
			ParSets.Ver_No_P18 = VtVarStrukt.init(Var.tk1, "Ver_No_P18", parsVerP18, true, true, true);
			ParSets.Ver_No_P19 = VtVarStrukt.init(Var.tk1, "Ver_No_P19", parsVerP19, true, true, true);
		}
	}

	/* Extension Type */
	/*
	 * format:  XY(X first character, Y second character)
	 * 	X relate to max value of the stop point
	 * 	Y relate to min value of the stop point
	 * 	values:
	 * 		Netivei Israel: 0 | Jerusalem: 1 - the value of stop point is relative(addition seconds to the stop point)
	 *  	Netivei Israel: 1 | Jerusalem: 2 - the value of stop point is absolute(the second of the cycle)
	 *   	Netivei Israel: 2 | Jerusalem: 3 - the value of stop point is accumulated(the rest of the previous stop point extension)
	 */
	
	/**
	 * Initializes Stop-Point #0
	 */
	private static void SetSP00()
	{
		Node tk = Var.tk1;
		//   					     max  min  type
		int[] sp00_P01 = new int[] {    0,   0,    0};
		int[] sp00_P02 = new int[] {    0,   0,    0};
		int[] sp00_P03 = new int[] {    0,   0,    0};
		int[] sp00_P04 = new int[] {    0,   0,    0};
		int[] sp00_P05 = new int[] {    0,   0,    0};
		int[] sp00_P06 = new int[] {    0,   0,    0};
		int[] sp00_P07 = new int[] {    0,   0,    0};		
		int[] sp00_P08 = new int[] {    0,   0,    0};
		int[] sp00_P09 = new int[] {    0,   0,    0};
		int[] sp00_P10 = new int[] {    0,   0,    0};		
		int[] sp00_P11 = new int[] {    0,   0,    0};
		int[] sp00_P12 = new int[] {    0,   0,    0};
		int[] sp00_P13 = new int[] {    0,   0,    0};
		int[] sp00_P14 = new int[] {    0,   0,    0};
		int[] sp00_P15 = new int[] {    0,   0,    0};
		int[] sp00_P16 = new int[] {    0,   0,    0};
		int[] sp00_P17 = new int[] {    0,   0,    0};
		int[] sp00_P18 = new int[] {    0,   0,    0};
		int[] sp00_P19 = new int[] {    0,   0,    0};

		ParSets.pars_sp00 = new Pars_SP00("SP00_Sync", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp00   , sp00_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp00   , sp00_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp00   , sp00_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp00   , sp00_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp00   , sp00_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp00   , sp00_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp00   , sp00_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp00   , sp00_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp00   , sp00_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp00   , sp00_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp00   , sp00_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp00   , sp00_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp00   , sp00_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp00   , sp00_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp00   , sp00_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp00   , sp00_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp00   , sp00_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp00   , sp00_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp00   , sp00_P19);
		}
	}

	/**
	 * Initializes Stop-Point #1
	 */
	private static void SetSP01() 
	{
		Node tk = Var.tk1;
		//   					     max  min  type 
		int[] sp01_P01 = new int[] { 33 ,   0,    0};
		int[] sp01_P02 = new int[] { 15 ,   0,    0};
		int[] sp01_P03 = new int[] { 22 ,   0,    0};
		int[] sp01_P04 = new int[] { 27 ,   0,    0};
		int[] sp01_P05 = new int[] {  3 ,   0,    0};
		int[] sp01_P06 = new int[] { 35 ,   0,    0};
		int[] sp01_P07 = new int[] { 72 ,   0,    0};
		int[] sp01_P08 = new int[] { 23 ,   0,    0};
		int[] sp01_P09 = new int[] { 44 ,   0,    0};
		int[] sp01_P10 = new int[] {  0 ,   0,    0};   		
		int[] sp01_P11 = new int[] {  0 ,   0,    0};   
		int[] sp01_P12 = new int[] {  0 ,   0,    0};    
		int[] sp01_P13 = new int[] {  0 ,   0,    0};   
		int[] sp01_P14 = new int[] {  0 ,   0,    0};   
		int[] sp01_P15 = new int[] {  0 ,   0,    0};   
		int[] sp01_P16 = new int[] {  0 ,   0,    0};   
		int[] sp01_P17 = new int[] {  0 ,   0,    0};   
		int[] sp01_P18 = new int[] {  0 ,   0,    0};   
		int[] sp01_P19 = new int[] {  0 ,   0,    0};   

		ParSets.pars_sp01 = new Pars_SP01("SP01", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp01   , sp01_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp01   , sp01_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp01   , sp01_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp01   , sp01_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp01   , sp01_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp01   , sp01_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp01   , sp01_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp01   , sp01_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp01   , sp01_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp01   , sp01_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp01   , sp01_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp01   , sp01_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp01   , sp01_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp01   , sp01_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp01   , sp01_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp01   , sp01_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp01   , sp01_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp01   , sp01_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp01   , sp01_P19);
		}
	}

	/**
	 * Initializes Stop-Point #2
	 */
	private static void SetSP02()
	{
		Node tk = Var.tk1;
		//                           max  min  type 
		int[] sp02_P01 = new int[] {   7 ,   0,   0};
		int[] sp02_P02 = new int[] {   3 ,   0,   0};
		int[] sp02_P03 = new int[] {   4 ,   0,   0};
		int[] sp02_P04 = new int[] {   7 ,   0,   0};
		int[] sp02_P05 = new int[] {   5 ,   0,   0};
		int[] sp02_P06 = new int[] {   7 ,   0,   0};
		int[] sp02_P07 = new int[] {   4 ,   0,   0};
		int[] sp02_P08 = new int[] {   4 ,   0,   0};
		int[] sp02_P09 = new int[] {   2 ,   0,   0};
		int[] sp02_P10 = new int[] {   0 ,   0,   0};   		
		int[] sp02_P11 = new int[] {   0 ,   0,   0};   
		int[] sp02_P12 = new int[] {   0 ,   0,   0};    
		int[] sp02_P13 = new int[] {   0 ,   0,   0};   
		int[] sp02_P14 = new int[] {   0 ,   0,   0};   
		int[] sp02_P15 = new int[] {   0 ,   0,   0};   
		int[] sp02_P16 = new int[] {   0 ,   0,   0};   
		int[] sp02_P17 = new int[] {   0 ,   0,   0};   
		int[] sp02_P18 = new int[] {   0 ,   0,   0};   
		int[] sp02_P19 = new int[] {   0 ,   0,   0};   

		ParSets.pars_sp02     = new Pars_SP02("SP02", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp02   , sp02_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp02   , sp02_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp02   , sp02_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp02   , sp02_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp02   , sp02_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp02   , sp02_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp02   , sp02_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp02   , sp02_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp02   , sp02_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp02   , sp02_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp02   , sp02_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp02   , sp02_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp02   , sp02_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp02   , sp02_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp02   , sp02_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp02   , sp02_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp02   , sp02_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp02   , sp02_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp02   , sp02_P19);
		}
	}

	/**
	 * Initializes Stop-Points #3
	 */
	private static void SetSP03()
	{
		Node tk = Var.tk1;
		//                           max   min  type
		int[] sp03_P01 = new int[] {  31 ,   0,    0};
		int[] sp03_P02 = new int[] {   8 ,   0,    0};
		int[] sp03_P03 = new int[] {  10 ,   0,    0};
		int[] sp03_P04 = new int[] {  37 ,   0,    0};
		int[] sp03_P05 = new int[] {   8 ,   0,    0};
		int[] sp03_P06 = new int[] {  29 ,   0,    0};
		int[] sp03_P07 = new int[] {  15 ,   0,    0};
		int[] sp03_P08 = new int[] {  64 ,   0,    0};
		int[] sp03_P09 = new int[] {  15 ,   0,    0};
		int[] sp03_P10 = new int[] {   0 ,   0,    0};
		int[] sp03_P11 = new int[] {   0 ,   0,    0};
		int[] sp03_P12 = new int[] {   0 ,   0,    0};
		int[] sp03_P13 = new int[] {   0 ,   0,    0};
		int[] sp03_P14 = new int[] {   0 ,   0,    0};
		int[] sp03_P15 = new int[] {   0 ,   0,    0};
		int[] sp03_P16 = new int[] {   0 ,   0,    0};
		int[] sp03_P17 = new int[] {   0 ,   0,    0};
		int[] sp03_P18 = new int[] {   0 ,   0,    0};
		int[] sp03_P19 = new int[] {   0 ,   0,    0};
		
		ParSets.pars_sp03 = new Pars_SP03("SP03", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp03   , sp03_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp03   , sp03_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp03   , sp03_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp03   , sp03_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp03   , sp03_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp03   , sp03_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp03   , sp03_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp03   , sp03_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp03   , sp03_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp03   , sp03_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp03   , sp03_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp03   , sp03_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp03   , sp03_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp03   , sp03_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp03   , sp03_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp03   , sp03_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp03   , sp03_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp03   , sp03_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp03   , sp03_P19);
		}
	}

	/**
	 * Initializes Stop-Point #4
	 */
	private static void SetSP04()
	{ 
		Node tk = Var.tk1;
		//    						 max  min  type 
		int[] sp04_P01 = new int[] {  22 ,   0,   0}; 
		int[] sp04_P02 = new int[] {  15 ,   0,   0}; 
		int[] sp04_P03 = new int[] {  17 ,   0,   0}; 
		int[] sp04_P04 = new int[] {  34 ,   0,   0}; 
		int[] sp04_P05 = new int[] {  15 ,   0,   0}; 
		int[] sp04_P06 = new int[] {  36 ,   0,   0}; 
		int[] sp04_P07 = new int[] {  20 ,   0,   0}; 
		int[] sp04_P08 = new int[] {  71 ,   0,   0}; 
		int[] sp04_P09 = new int[] {  22 ,   0,   0}; 
		int[] sp04_P10 = new int[] {   0 ,   0,   0}; 
		int[] sp04_P11 = new int[] {   0 ,   0,   0}; 
		int[] sp04_P12 = new int[] {   0 ,   0,   0}; 
		int[] sp04_P13 = new int[] {   0 ,   0,   0}; 
		int[] sp04_P14 = new int[] {   0 ,   0,   0}; 
		int[] sp04_P15 = new int[] {   0 ,   0,   0}; 
		int[] sp04_P16 = new int[] {   0 ,   0,   0}; 
		int[] sp04_P17 = new int[] {   0 ,   0,   0};   
		int[] sp04_P18 = new int[] {   0 ,   0,   0};   
		int[] sp04_P19 = new int[] {   0 ,   0,   0};   

		ParSets.pars_sp04 = new Pars_SP04("SP04", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp04   , sp04_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp04   , sp04_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp04   , sp04_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp04   , sp04_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp04   , sp04_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp04   , sp04_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp04   , sp04_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp04   , sp04_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp04   , sp04_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp04   , sp04_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp04   , sp04_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp04   , sp04_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp04   , sp04_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp04   , sp04_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp04   , sp04_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp04   , sp04_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp04   , sp04_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp04   , sp04_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp04   , sp04_P19);
		}

	}

	/**
	 * Initializes Stop-Point #5
	 */
	private static void SetSP05()
	{
		Node tk = Var.tk1;
		//                          max  min  type 
		int[] sp05_P01 = new int[] {  0,   0,    0};    
		int[] sp05_P02 = new int[] {  0,   0,    0};    
		int[] sp05_P03 = new int[] {  0,   0,    0};    
		int[] sp05_P04 = new int[] {  0,   0,    0};    
		int[] sp05_P05 = new int[] {  0,   0,    0};     
		int[] sp05_P06 = new int[] {  0,   0,    0};     
		int[] sp05_P07 = new int[] {  0,   0,    0};    
		int[] sp05_P08 = new int[] {  0,   0,    0};    
		int[] sp05_P09 = new int[] {  0,   0,    0};    
		int[] sp05_P10 = new int[] {  0,   0,    0};     
		int[] sp05_P11 = new int[] {  0,   0,    0};   
		int[] sp05_P12 = new int[] {  0,   0,    0};   
		int[] sp05_P13 = new int[] {  0,   0,    0};   
		int[] sp05_P14 = new int[] {  0,   0,    0};   
		int[] sp05_P15 = new int[] {  0,   0,    0};   
		int[] sp05_P16 = new int[] {  0,   0,    0};    
		int[] sp05_P17 = new int[] {  0,   0,    0};    
		int[] sp05_P18 = new int[] {  0,   0,    0};    
		int[] sp05_P19 = new int[] {  0,   0,    0};    

		ParSets.pars_sp05 = new Pars_SP05("SP05", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp05   , sp05_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp05   , sp05_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp05   , sp05_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp05   , sp05_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp05   , sp05_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp05   , sp05_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp05   , sp05_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp05   , sp05_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp05   , sp05_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp05   , sp05_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp05   , sp05_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp05   , sp05_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp05   , sp05_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp05   , sp05_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp05   , sp05_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp05   , sp05_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp05   , sp05_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp05   , sp05_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp05   , sp05_P19);
		}
	}

	/**
	 * Initializes Stop-Point 6
	 */
	private static void SetSP06()
	{
		Node tk = Var.tk1;
		//                          max  min  type 
		int[] sp06_P01 = new int[] {  0,   0,    0};
		int[] sp06_P02 = new int[] {  0,   0,    0};
		int[] sp06_P03 = new int[] {  0,   0,    0};
		int[] sp06_P04 = new int[] {  0,   0,    0};
		int[] sp06_P05 = new int[] {  0,   0,    0};
		int[] sp06_P06 = new int[] {  0,   0,    0};
		int[] sp06_P07 = new int[] {  0,   0,    0};
		int[] sp06_P08 = new int[] {  0,   0,    0};
		int[] sp06_P09 = new int[] {  0,   0,    0};
		int[] sp06_P10 = new int[] {  0,   0,    0};
		int[] sp06_P11 = new int[] {  0,   0,    0};
		int[] sp06_P12 = new int[] {  0,   0,    0};
		int[] sp06_P13 = new int[] {  0,   0,    0};
		int[] sp06_P14 = new int[] {  0,   0,    0};
		int[] sp06_P15 = new int[] {  0,   0,    0};
		int[] sp06_P16 = new int[] {  0,   0,    0};
		int[] sp06_P17 = new int[] {  0,   0,    0};
		int[] sp06_P18 = new int[] {  0,   0,    0};
		int[] sp06_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp06 = new Pars_SP06("SP06", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp06   , sp06_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp06   , sp06_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp06   , sp06_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp06   , sp06_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp06   , sp06_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp06   , sp06_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp06   , sp06_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp06   , sp06_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp06   , sp06_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp06   , sp06_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp06   , sp06_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp06   , sp06_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp06   , sp06_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp06   , sp06_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp06   , sp06_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp06   , sp06_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp06   , sp06_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp06   , sp06_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp06   , sp06_P19);
		}
	}

	/**
	 * Initializes Stop-Point #7
	 */
	private static void SetSP07()
	{ 
		Node tk = Var.tk1;
		//                          max  min  type 
		int[] sp07_P01 = new int[] {  0,   0,    0};
		int[] sp07_P02 = new int[] {  0,   0,    0};
		int[] sp07_P03 = new int[] {  0,   0,    0};
		int[] sp07_P04 = new int[] {  0,   0,    0};
		int[] sp07_P05 = new int[] {  0,   0,    0};
		int[] sp07_P06 = new int[] {  0,   0,    0};
		int[] sp07_P07 = new int[] {  0,   0,    0};
		int[] sp07_P08 = new int[] {  0,   0,    0};
		int[] sp07_P09 = new int[] {  0,   0,    0};
		int[] sp07_P10 = new int[] {  0,   0,    0};
		int[] sp07_P11 = new int[] {  0,   0,    0};
		int[] sp07_P12 = new int[] {  0,   0,    0};
		int[] sp07_P13 = new int[] {  0,   0,    0};
		int[] sp07_P14 = new int[] {  0,   0,    0};
		int[] sp07_P15 = new int[] {  0,   0,    0};
		int[] sp07_P16 = new int[] {  0,   0,    0};
		int[] sp07_P17 = new int[] {  0,   0,    0};
		int[] sp07_P18 = new int[] {  0,   0,    0};                      
		int[] sp07_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp07 = new Pars_SP07("SP07", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp07   , sp07_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp07   , sp07_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp07   , sp07_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp07   , sp07_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp07   , sp07_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp07   , sp07_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp07   , sp07_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp07   , sp07_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp07   , sp07_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp07   , sp07_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp07   , sp07_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp07   , sp07_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp07   , sp07_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp07   , sp07_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp07   , sp07_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp07   , sp07_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp07   , sp07_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp07   , sp07_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp07   , sp07_P19);
		}
	}

	/**
	 * Initializes Stop-Point #8
	 */
	private static void SetSP08()
	{
		Node tk = Var.tk1;
		//                          max  min  type 
		int[] sp08_P01 = new int[] {  0,   0,    0};
		int[] sp08_P02 = new int[] {  0,   0,    0};
		int[] sp08_P03 = new int[] {  0,   0,    0};
		int[] sp08_P04 = new int[] {  0,   0,    0};
		int[] sp08_P05 = new int[] {  0,   0,    0};
		int[] sp08_P06 = new int[] {  0,   0,    0};
		int[] sp08_P07 = new int[] {  0,   0,    0};
		int[] sp08_P08 = new int[] {  0,   0,    0};
		int[] sp08_P09 = new int[] {  0,   0,    0};
		int[] sp08_P10 = new int[] {  0,   0,    0};
		int[] sp08_P11 = new int[] {  0,   0,    0};
		int[] sp08_P12 = new int[] {  0,   0,    0};
		int[] sp08_P13 = new int[] {  0,   0,    0};
		int[] sp08_P14 = new int[] {  0,   0,    0};
		int[] sp08_P15 = new int[] {  0,   0,    0};
		int[] sp08_P16 = new int[] {  0,   0,    0};
		int[] sp08_P17 = new int[] {  0,   0,    0};
		int[] sp08_P18 = new int[] {  0,   0,    0};
		int[] sp08_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp08 = new Pars_SP08("SP08", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp08   , sp08_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp08   , sp08_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp08   , sp08_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp08   , sp08_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp08   , sp08_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp08   , sp08_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp08   , sp08_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp08   , sp08_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp08   , sp08_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp08   , sp08_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp08   , sp08_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp08   , sp08_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp08   , sp08_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp08   , sp08_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp08   , sp08_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp08   , sp08_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp08   , sp08_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp08   , sp08_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp08   , sp08_P19);
		}
	}

	/**
	 * Initializes Stop-Point #9
	 */
	private static void SetSP09()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp09_P01 = new int[] {  0,   0,    0};
		int[] sp09_P02 = new int[] {  0,   0,    0};
		int[] sp09_P03 = new int[] {  0,   0,    0};
		int[] sp09_P04 = new int[] {  0,   0,    0};
		int[] sp09_P05 = new int[] {  0,   0,    0};
		int[] sp09_P06 = new int[] {  0,   0,    0};
		int[] sp09_P07 = new int[] {  0,   0,    0};
		int[] sp09_P08 = new int[] {  0,   0,    0};
		int[] sp09_P09 = new int[] {  0,   0,    0};
		int[] sp09_P10 = new int[] {  0,   0,    0};
		int[] sp09_P11 = new int[] {  0,   0,    0};
		int[] sp09_P12 = new int[] {  0,   0,    0};
		int[] sp09_P13 = new int[] {  0,   0,    0};
		int[] sp09_P14 = new int[] {  0,   0,    0};
		int[] sp09_P15 = new int[] {  0,   0,    0};
		int[] sp09_P16 = new int[] {  0,   0,    0};
		int[] sp09_P17 = new int[] {  0,   0,    0};
		int[] sp09_P18 = new int[] {  0,   0,    0};
		int[] sp09_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp09 = new Pars_SP09("SP09", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp09   , sp09_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp09   , sp09_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp09   , sp09_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp09   , sp09_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp09   , sp09_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp09   , sp09_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp09   , sp09_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp09   , sp09_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp09   , sp09_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp09   , sp09_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp09   , sp09_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp09   , sp09_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp09   , sp09_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp09   , sp09_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp09   , sp09_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp09   , sp09_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp09   , sp09_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp09   , sp09_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp09   , sp09_P19);
		}
	}

	/**
	 * Initializes Stop-Point #10
	 */
	private static void SetSP10()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp10_P01 = new int[] {  0,   0,    0};
		int[] sp10_P02 = new int[] {  0,   0,    0};
		int[] sp10_P03 = new int[] {  0,   0,    0};
		int[] sp10_P04 = new int[] {  0,   0,    0};
		int[] sp10_P05 = new int[] {  0,   0,    0};
		int[] sp10_P06 = new int[] {  0,   0,    0};
		int[] sp10_P07 = new int[] {  0,   0,    0};
		int[] sp10_P08 = new int[] {  0,   0,    0};
		int[] sp10_P09 = new int[] {  0,   0,    0};
		int[] sp10_P10 = new int[] {  0,   0,    0};
		int[] sp10_P11 = new int[] {  0,   0,    0};
		int[] sp10_P12 = new int[] {  0,   0,    0};
		int[] sp10_P13 = new int[] {  0,   0,    0};
		int[] sp10_P14 = new int[] {  0,   0,    0};
		int[] sp10_P15 = new int[] {  0,   0,    0};
		int[] sp10_P16 = new int[] {  0,   0,    0};
		int[] sp10_P17 = new int[] {  0,   0,    0};
		int[] sp10_P18 = new int[] {  0,   0,    0};
		int[] sp10_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp10 = new Pars_SP10("SP10", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp10   , sp10_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp10   , sp10_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp10   , sp10_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp10   , sp10_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp10   , sp10_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp10   , sp10_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp10   , sp10_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp10   , sp10_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp10   , sp10_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp10   , sp10_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp10   , sp10_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp10   , sp10_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp10   , sp10_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp10   , sp10_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp10   , sp10_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp10   , sp10_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp10   , sp10_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp10   , sp10_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp10   , sp10_P19);
		}
	}

	/**
	 * Initializes Stop-Point #11
	 */
	private static void SetSP11()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp11_P01 = new int[] {  0,   0,    0};
		int[] sp11_P02 = new int[] {  0,   0,    0};
		int[] sp11_P03 = new int[] {  0,   0,    0};
		int[] sp11_P04 = new int[] {  0,   0,    0};
		int[] sp11_P05 = new int[] {  0,   0,    0};
		int[] sp11_P06 = new int[] {  0,   0,    0};
		int[] sp11_P07 = new int[] {  0,   0,    0};
		int[] sp11_P08 = new int[] {  0,   0,    0};
		int[] sp11_P09 = new int[] {  0,   0,    0};
		int[] sp11_P10 = new int[] {  0,   0,    0};
		int[] sp11_P11 = new int[] {  0,   0,    0};
		int[] sp11_P12 = new int[] {  0,   0,    0};
		int[] sp11_P13 = new int[] {  0,   0,    0};
		int[] sp11_P14 = new int[] {  0,   0,    0};
		int[] sp11_P15 = new int[] {  0,   0,    0};
		int[] sp11_P16 = new int[] {  0,   0,    0};
		int[] sp11_P17 = new int[] {  0,   0,    0};
		int[] sp11_P18 = new int[] {  0,   0,    0};
		int[] sp11_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp11 = new Pars_SP11("SP11", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp11   , sp11_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp11   , sp11_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp11   , sp11_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp11   , sp11_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp11   , sp11_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp11   , sp11_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp11   , sp11_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp11   , sp11_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp11   , sp11_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp11   , sp11_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp11   , sp11_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp11   , sp11_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp11   , sp11_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp11   , sp11_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp11   , sp11_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp11   , sp11_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp11   , sp11_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp11   , sp11_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp11   , sp11_P19);
		}
	}

	/**
	 * Initializes Stop-Point #12
	 */
	private static void SetSP12()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp12_P01 = new int[] {  0,   0,    0};
		int[] sp12_P02 = new int[] {  0,   0,    0};
		int[] sp12_P03 = new int[] {  0,   0,    0};
		int[] sp12_P04 = new int[] {  0,   0,    0};
		int[] sp12_P05 = new int[] {  0,   0,    0};
		int[] sp12_P06 = new int[] {  0,   0,    0};
		int[] sp12_P07 = new int[] {  0,   0,    0};
		int[] sp12_P08 = new int[] {  0,   0,    0};
		int[] sp12_P09 = new int[] {  0,   0,    0};
		int[] sp12_P10 = new int[] {  0,   0,    0};
		int[] sp12_P11 = new int[] {  0,   0,    0};
		int[] sp12_P12 = new int[] {  0,   0,    0};
		int[] sp12_P13 = new int[] {  0,   0,    0};
		int[] sp12_P14 = new int[] {  0,   0,    0};
		int[] sp12_P15 = new int[] {  0,   0,    0};
		int[] sp12_P16 = new int[] {  0,   0,    0};
		int[] sp12_P17 = new int[] {  0,   0,    0};
		int[] sp12_P18 = new int[] {  0,   0,    0};
		int[] sp12_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp12 = new Pars_SP12("SP12", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp12   , sp12_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp12   , sp12_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp12   , sp12_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp12   , sp12_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp12   , sp12_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp12   , sp12_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp12   , sp12_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp12   , sp12_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp12   , sp12_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp12   , sp12_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp12   , sp12_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp12   , sp12_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp12   , sp12_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp12   , sp12_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp12   , sp12_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp12   , sp12_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp12   , sp12_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp12   , sp12_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp12   , sp12_P19);
		}
	}

	/**
	 * Initializes Stop-Point #13
	 */
	private static void SetSP13()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp13_P01 = new int[] {  0,   0,    0};
		int[] sp13_P02 = new int[] {  0,   0,    0};
		int[] sp13_P03 = new int[] {  0,   0,    0};
		int[] sp13_P04 = new int[] {  0,   0,    0};
		int[] sp13_P05 = new int[] {  0,   0,    0};
		int[] sp13_P06 = new int[] {  0,   0,    0};
		int[] sp13_P07 = new int[] {  0,   0,    0};
		int[] sp13_P08 = new int[] {  0,   0,    0};
		int[] sp13_P09 = new int[] {  0,   0,    0};
		int[] sp13_P10 = new int[] {  0,   0,    0};
		int[] sp13_P11 = new int[] {  0,   0,    0};
		int[] sp13_P12 = new int[] {  0,   0,    0};
		int[] sp13_P13 = new int[] {  0,   0,    0};
		int[] sp13_P14 = new int[] {  0,   0,    0};
		int[] sp13_P15 = new int[] {  0,   0,    0};
		int[] sp13_P16 = new int[] {  0,   0,    0};
		int[] sp13_P17 = new int[] {  0,   0,    0};
		int[] sp13_P18 = new int[] {  0,   0,    0};
		int[] sp13_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp13 = new Pars_SP13("SP13", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp13   , sp13_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp13   , sp13_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp13   , sp13_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp13   , sp13_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp13   , sp13_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp13   , sp13_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp13   , sp13_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp13   , sp13_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp13   , sp13_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp13   , sp13_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp13   , sp13_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp13   , sp13_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp13   , sp13_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp13   , sp13_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp13   , sp13_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp13   , sp13_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp13   , sp13_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp13   , sp13_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp13   , sp13_P19);
		}
	}

	/**
	 * Initializes Stop-Point #14
	 */
	private static void SetSP14()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp14_P01 = new int[] {  0,   0,    0};
		int[] sp14_P02 = new int[] {  0,   0,    0};
		int[] sp14_P03 = new int[] {  0,   0,    0};
		int[] sp14_P04 = new int[] {  0,   0,    0};
		int[] sp14_P05 = new int[] {  0,   0,    0};
		int[] sp14_P06 = new int[] {  0,   0,    0};
		int[] sp14_P07 = new int[] {  0,   0,    0};
		int[] sp14_P08 = new int[] {  0,   0,    0};
		int[] sp14_P09 = new int[] {  0,   0,    0};
		int[] sp14_P10 = new int[] {  0,   0,    0};
		int[] sp14_P11 = new int[] {  0,   0,    0};
		int[] sp14_P12 = new int[] {  0,   0,    0};
		int[] sp14_P13 = new int[] {  0,   0,    0};
		int[] sp14_P14 = new int[] {  0,   0,    0};
		int[] sp14_P15 = new int[] {  0,   0,    0};
		int[] sp14_P16 = new int[] {  0,   0,    0};
		int[] sp14_P17 = new int[] {  0,   0,    0};
		int[] sp14_P18 = new int[] {  0,   0,    0};
		int[] sp14_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp14 = new Pars_SP14("SP14", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp14   , sp14_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp14   , sp14_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp14   , sp14_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp14   , sp14_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp14   , sp14_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp14   , sp14_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp14   , sp14_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp14   , sp14_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp14   , sp14_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp14   , sp14_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp14   , sp14_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp14   , sp14_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp14   , sp14_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp14   , sp14_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp14   , sp14_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp14   , sp14_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp14   , sp14_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp14   , sp14_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp14   , sp14_P19);
		}
	}

	/**
	 * Initializes Stop-Point #15
	 */
	private static void SetSP15()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp15_P01 = new int[] {  0,   0,    0};
		int[] sp15_P02 = new int[] {  0,   0,    0};
		int[] sp15_P03 = new int[] {  0,   0,    0};
		int[] sp15_P04 = new int[] {  0,   0,    0};
		int[] sp15_P05 = new int[] {  0,   0,    0};
		int[] sp15_P06 = new int[] {  0,   0,    0};
		int[] sp15_P07 = new int[] {  0,   0,    0};
		int[] sp15_P08 = new int[] {  0,   0,    0};
		int[] sp15_P09 = new int[] {  0,   0,    0};
		int[] sp15_P10 = new int[] {  0,   0,    0};
		int[] sp15_P11 = new int[] {  0,   0,    0};
		int[] sp15_P12 = new int[] {  0,   0,    0};
		int[] sp15_P13 = new int[] {  0,   0,    0};
		int[] sp15_P14 = new int[] {  0,   0,    0};
		int[] sp15_P15 = new int[] {  0,   0,    0};
		int[] sp15_P16 = new int[] {  0,   0,    0};
		int[] sp15_P17 = new int[] {  0,   0,    0};
		int[] sp15_P18 = new int[] {  0,   0,    0};
		int[] sp15_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp15 = new Pars_SP15("SP15", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp15   , sp15_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp15   , sp15_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp15   , sp15_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp15   , sp15_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp15   , sp15_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp15   , sp15_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp15   , sp15_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp15   , sp15_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp15   , sp15_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp15   , sp15_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp15   , sp15_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp15   , sp15_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp15   , sp15_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp15   , sp15_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp15   , sp15_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp15   , sp15_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp15   , sp15_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp15   , sp15_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp15   , sp15_P19);
		}
	}

	/**
	 * Initializes Stop-Point #16
	 */
	private static void SetSP16()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp16_P01 = new int[] {  0,   0,    0};
		int[] sp16_P02 = new int[] {  0,   0,    0};
		int[] sp16_P03 = new int[] {  0,   0,    0};
		int[] sp16_P04 = new int[] {  0,   0,    0};
		int[] sp16_P05 = new int[] {  0,   0,    0};
		int[] sp16_P06 = new int[] {  0,   0,    0};
		int[] sp16_P07 = new int[] {  0,   0,    0};
		int[] sp16_P08 = new int[] {  0,   0,    0};
		int[] sp16_P09 = new int[] {  0,   0,    0};
		int[] sp16_P10 = new int[] {  0,   0,    0};
		int[] sp16_P11 = new int[] {  0,   0,    0};
		int[] sp16_P12 = new int[] {  0,   0,    0};
		int[] sp16_P13 = new int[] {  0,   0,    0};
		int[] sp16_P14 = new int[] {  0,   0,    0};
		int[] sp16_P15 = new int[] {  0,   0,    0};
		int[] sp16_P16 = new int[] {  0,   0,    0};
		int[] sp16_P17 = new int[] {  0,   0,    0};
		int[] sp16_P18 = new int[] {  0,   0,    0};
		int[] sp16_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp16 = new Pars_SP16("SP16", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp16   , sp16_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp16   , sp16_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp16   , sp16_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp16   , sp16_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp16   , sp16_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp16   , sp16_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp16   , sp16_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp16   , sp16_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp16   , sp16_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp16   , sp16_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp16   , sp16_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp16   , sp16_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp16   , sp16_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp16   , sp16_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp16   , sp16_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp16   , sp16_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp16   , sp16_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp16   , sp16_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp16   , sp16_P19);
		}
	}

	/**
	 * Initializes Stop-Point #17
	 */
	private static void SetSP17()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp17_P01 = new int[] {  0,   0,    0};
		int[] sp17_P02 = new int[] {  0,   0,    0};
		int[] sp17_P03 = new int[] {  0,   0,    0};
		int[] sp17_P04 = new int[] {  0,   0,    0};
		int[] sp17_P05 = new int[] {  0,   0,    0};
		int[] sp17_P06 = new int[] {  0,   0,    0};
		int[] sp17_P07 = new int[] {  0,   0,    0};
		int[] sp17_P08 = new int[] {  0,   0,    0};
		int[] sp17_P09 = new int[] {  0,   0,    0};
		int[] sp17_P10 = new int[] {  0,   0,    0};
		int[] sp17_P11 = new int[] {  0,   0,    0};
		int[] sp17_P12 = new int[] {  0,   0,    0};
		int[] sp17_P13 = new int[] {  0,   0,    0};
		int[] sp17_P14 = new int[] {  0,   0,    0};
		int[] sp17_P15 = new int[] {  0,   0,    0};
		int[] sp17_P16 = new int[] {  0,   0,    0};
		int[] sp17_P17 = new int[] {  0,   0,    0};
		int[] sp17_P18 = new int[] {  0,   0,    0};
		int[] sp17_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp17 = new Pars_SP17("SP17", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp17   , sp17_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp17   , sp17_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp17   , sp17_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp17   , sp17_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp17   , sp17_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp17   , sp17_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp17   , sp17_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp17   , sp17_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp17   , sp17_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp17   , sp17_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp17   , sp17_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp17   , sp17_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp17   , sp17_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp17   , sp17_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp17   , sp17_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp17   , sp17_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp17   , sp17_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp17   , sp17_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp17   , sp17_P19);
		}
	}

	/**
	 * Initializes Stop-Point #18
	 */
	private static void SetSP18()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp18_P01 = new int[] {  0,   0,    0};
		int[] sp18_P02 = new int[] {  0,   0,    0};
		int[] sp18_P03 = new int[] {  0,   0,    0};
		int[] sp18_P04 = new int[] {  0,   0,    0};
		int[] sp18_P05 = new int[] {  0,   0,    0};
		int[] sp18_P06 = new int[] {  0,   0,    0};
		int[] sp18_P07 = new int[] {  0,   0,    0};
		int[] sp18_P08 = new int[] {  0,   0,    0};
		int[] sp18_P09 = new int[] {  0,   0,    0};
		int[] sp18_P10 = new int[] {  0,   0,    0};
		int[] sp18_P11 = new int[] {  0,   0,    0};
		int[] sp18_P12 = new int[] {  0,   0,    0};
		int[] sp18_P13 = new int[] {  0,   0,    0};
		int[] sp18_P14 = new int[] {  0,   0,    0};
		int[] sp18_P15 = new int[] {  0,   0,    0};
		int[] sp18_P16 = new int[] {  0,   0,    0};
		int[] sp18_P17 = new int[] {  0,   0,    0};
		int[] sp18_P18 = new int[] {  0,   0,    0};
		int[] sp18_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp18 = new Pars_SP18("SP18", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp18   , sp18_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp18   , sp18_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp18   , sp18_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp18   , sp18_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp18   , sp18_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp18   , sp18_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp18   , sp18_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp18   , sp18_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp18   , sp18_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp18   , sp18_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp18   , sp18_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp18   , sp18_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp18   , sp18_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp18   , sp18_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp18   , sp18_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp18   , sp18_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp18   , sp18_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp18   , sp18_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp18   , sp18_P19);
		}
	}

	/**
	 * Initializes Stop-Point #19
	 */
	private static void SetSP19()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp19_P01 = new int[] {  0,   0,    0};
		int[] sp19_P02 = new int[] {  0,   0,    0};
		int[] sp19_P03 = new int[] {  0,   0,    0};
		int[] sp19_P04 = new int[] {  0,   0,    0};
		int[] sp19_P05 = new int[] {  0,   0,    0};
		int[] sp19_P06 = new int[] {  0,   0,    0};
		int[] sp19_P07 = new int[] {  0,   0,    0};
		int[] sp19_P08 = new int[] {  0,   0,    0};
		int[] sp19_P09 = new int[] {  0,   0,    0};
		int[] sp19_P10 = new int[] {  0,   0,    0};
		int[] sp19_P11 = new int[] {  0,   0,    0};
		int[] sp19_P12 = new int[] {  0,   0,    0};
		int[] sp19_P13 = new int[] {  0,   0,    0};
		int[] sp19_P14 = new int[] {  0,   0,    0};
		int[] sp19_P15 = new int[] {  0,   0,    0};
		int[] sp19_P16 = new int[] {  0,   0,    0};
		int[] sp19_P17 = new int[] {  0,   0,    0};
		int[] sp19_P18 = new int[] {  0,   0,    0};
		int[] sp19_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp19 = new Pars_SP19("SP19", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp19   , sp19_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp19   , sp19_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp19   , sp19_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp19   , sp19_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp19   , sp19_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp19   , sp19_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp19   , sp19_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp19   , sp19_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp19   , sp19_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp19   , sp19_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp19   , sp19_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp19   , sp19_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp19   , sp19_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp19   , sp19_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp19   , sp19_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp19   , sp19_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp19   , sp19_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp19   , sp19_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp19   , sp19_P19);
		}
	}

	/**
	 * Initializes Stop-Point #20
	 */
	private static void SetSP20()
	{
		Node tk = Var.tk1;
		//                          min  max  type
		int[] sp20_P01 = new int[] {  0,   0,    0};
		int[] sp20_P02 = new int[] {  0,   0,    0};
		int[] sp20_P03 = new int[] {  0,   0,    0};
		int[] sp20_P04 = new int[] {  0,   0,    0};
		int[] sp20_P05 = new int[] {  0,   0,    0};
		int[] sp20_P06 = new int[] {  0,   0,    0};
		int[] sp20_P07 = new int[] {  0,   0,    0};
		int[] sp20_P08 = new int[] {  0,   0,    0};
		int[] sp20_P09 = new int[] {  0,   0,    0};
		int[] sp20_P10 = new int[] {  0,   0,    0};
		int[] sp20_P11 = new int[] {  0,   0,    0};
		int[] sp20_P12 = new int[] {  0,   0,    0};
		int[] sp20_P13 = new int[] {  0,   0,    0};
		int[] sp20_P14 = new int[] {  0,   0,    0};
		int[] sp20_P15 = new int[] {  0,   0,    0};
		int[] sp20_P16 = new int[] {  0,   0,    0};
		int[] sp20_P17 = new int[] {  0,   0,    0};
		int[] sp20_P18 = new int[] {  0,   0,    0};
		int[] sp20_P19 = new int[] {  0,   0,    0};

		ParSets.pars_sp20 = new Pars_SP20("SP20", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_sp20   , sp20_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_sp20   , sp20_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_sp20   , sp20_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_sp20   , sp20_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_sp20   , sp20_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_sp20   , sp20_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_sp20   , sp20_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_sp20   , sp20_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_sp20   , sp20_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_sp20   , sp20_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_sp20   , sp20_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_sp20   , sp20_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_sp20   , sp20_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_sp20   , sp20_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_sp20   , sp20_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_sp20   , sp20_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_sp20   , sp20_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_sp20   , sp20_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_sp20   , sp20_P19);
		}
	}

	/**
	 * Initializes the Detectors parameters:
	 * <ol>
	 * 		<li>0 - Automatic</li>
	 *  	<li>1 - Set both as Demand and as Extension</li>
	 *  	<li>2 - Set only as Demand</li>
	 *  	<li>3 - Set only as Extension</li>
	 *  </ol>
	 *  In total, there are 20 detectors. If there is more than 1 node, they should be divided
	 *  into three arrays (per program) according to the following assignment:
	 *  <ul>
	 *  	<li>Node 1: according to how many detectors exist</li>
	 *  	<li>Node 2: according to how many detectors exist (no more than 20 in total)</li>
	 *  	<li>Node 3: 20's complement</li>
	 *  </ul>
	 */
	private static void SetDet() 
	{
		Node tk = Var.tk1;
		//    					   1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20
		int[] det_P01 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P02 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P03 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P04 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P05 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P06 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P07 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P08 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P09 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P10 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P11 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P12 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P13 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P14 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P15 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P16 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P17 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P18 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] det_P19 = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		ParSets.pars_det1    = new Pars_Det_tk1("Dm_Ex", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_det1  , det_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_det1  , det_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_det1  , det_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_det1  , det_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_det1  , det_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_det1  , det_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_det1  , det_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_det1  , det_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_det1  , det_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_det1  , det_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_det1  , det_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_det1  , det_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_det1  , det_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_det1  , det_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_det1  , det_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_det1  , det_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_det1  , det_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_det1  , det_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_det1  , det_P19);
		}
	}

	/**
	 * Initializes the reserved parameters (13 in total per program)
	 */
	private static void SetRes() 
	{
		Node tk = Var.tk1;
		//    						 1    2    3    4    5    6    7    8    9   10   11   12   13
		int[] res_P01 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P02 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P03 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P04 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P05 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P06 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P07 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P08 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P09 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P10 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P11 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P12 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P13 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P14 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P15 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P16 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P17 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P18 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
		int[] res_P19 = new int[] {  1,   2,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};

		ParSets.pars_res     = new Pars_Res("Res", tk);
		ParameterDaten.assign(tk.p01 , ParSets.pars_res   , res_P01);
		ParameterDaten.assign(tk.p02 , ParSets.pars_res   , res_P02);
		ParameterDaten.assign(tk.p03 , ParSets.pars_res   , res_P03);
		ParameterDaten.assign(tk.p04 , ParSets.pars_res   , res_P04);
		ParameterDaten.assign(tk.p05 , ParSets.pars_res   , res_P05);
		ParameterDaten.assign(tk.p06 , ParSets.pars_res   , res_P06);
		ParameterDaten.assign(tk.p07 , ParSets.pars_res   , res_P07);
		ParameterDaten.assign(tk.p08 , ParSets.pars_res   , res_P08);
		ParameterDaten.assign(tk.p09 , ParSets.pars_res   , res_P09);
		ParameterDaten.assign(tk.p10 , ParSets.pars_res   , res_P10);
		ParameterDaten.assign(tk.p11 , ParSets.pars_res   , res_P11);
		ParameterDaten.assign(tk.p12 , ParSets.pars_res   , res_P12);
		ParameterDaten.assign(tk.p13 , ParSets.pars_res   , res_P13);
		ParameterDaten.assign(tk.p14 , ParSets.pars_res   , res_P14);
		ParameterDaten.assign(tk.p15 , ParSets.pars_res   , res_P15);
		ParameterDaten.assign(tk.p16 , ParSets.pars_res   , res_P16);
		if (Var.controller.getMaxProgramsNumber() > 16)
		{
			ParameterDaten.assign(tk.p17 , ParSets.pars_res   , res_P17);
			ParameterDaten.assign(tk.p18 , ParSets.pars_res   , res_P18);
			ParameterDaten.assign(tk.p19 , ParSets.pars_res   , res_P19);
		}
	}

	/**
	 * Initializes the UL-Fail parameter which determines in GW application with a ULin input
	 * what the threshold for determining error when ULin is not received
	 */
	private static void SetULFail()
	{
		//                             UL 
		//                         fail/timeout                            
		int[] ulfail    = new int[] {  20 };

		ParSets.ULFailParam = VtVarStrukt.init(Var.tk1, "ULFail", ulfail, true, true, true);
	}

	/**
	 * Initializes all the parameters
	 */
	public static void SetParameters()
	{
		SetGWEnable();
		SetGaps();
		SetVersion();
		SetStruct();
		SetCycle();
		SetOffset();
		SetSP00();
		SetSP01();
		SetSP02();
		SetSP03();
		SetSP04();
		SetSP05();
		SetSP06();
		SetSP07();
		SetSP08();
		SetSP09();
		SetSP10();
		SetSP11();
		SetSP12();
		SetSP13();
		SetSP14();
		SetSP15();
		SetSP16();
		SetSP17();
		SetSP18();
		SetSP19();
		SetSP20();
		SetDet();
		SetRes();
		SetULFail();
	}
}
