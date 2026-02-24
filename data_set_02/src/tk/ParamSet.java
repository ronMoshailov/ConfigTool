package tk;
import enums.HaifaParametersIndexes;
import special.Node;

public class ParamSet {
	/*******************************************************************************************************************
	 * Haifa Parameters                                                                                                *
	 *******************************************************************************************************************/
	//********************************************** Constant Parameters **********************************************//
	public static int
	pOffset_ProgAtt , pPedMaxRed_ProgAtt , pFixedCycleTime_ProgAtt , pStructureNumber_ProgAtt , pIsCrossMaster_ProgAtt ,
	pOffset_ProgAtt2, pPedMaxRed_ProgAtt2, pFixedCycleTime_ProgAtt2, pStructureNumber_ProgAtt2, pIsCrossMaster_ProgAtt2,
	structure,
	pGapSet , pModeSet ,
	pGapSet2, pModeSet2,
	pStartTime_A, pEndTime_A;

	//******************************************** Application Parameters *********************************************//
	public static int pMinDuration_EQA, pMaxDuration_EQA;
	public static int pMinDuration_B  , pMaxDuration_B  ;
	public static int pMinDuration_C  , pMaxDuration_C  ;
	
	/*******************************************************************************************************************
	 * Jerusalem Parameters                                                                                                *
	 *******************************************************************************************************************/
	//********************************************** Constant Parameters **********************************************//
	public static int
	Ver_No, Struct_No, CyMax, Sync_Offset;
	public static int[] Dm_Ex_Det = new int[20];
	
	private static void struct1(Node tk) {
		if (tk.getNummer() == 1) {
//			pStartTime_A          = Var.ONE_SEC * (Var.controller.dvi35Parameters.parameters[20]);
//			pMinDuration_EQA      = Var.ONE_SEC * (Var.controller.dvi35Parameters.parameters[ 6]);			
			                                                        
//			pEndTime_A            = Var.ONE_SEC*(Var.controller.dvi35Parameters.parameters[20]);
//			pMaxDuration_EQA      = Var.ONE_SEC*(Var.controller.dvi35Parameters.parameters[21]);
		} else if (tk.getNummer() == 2) {
			
		}
	}
	
	private static void struct2(Node tk) {
		struct1(tk);
	}
	
	private static void struct3(Node tk) {
		struct1(tk);
	}
	
	private static void struct4(Node tk) {
		struct1(tk);
	}
	
	private static void specialParametersHaifa(Node tk) {
		//all values relate to cycle, phase or signals must be in ms
		// All duration parameters not include skeleton. So skeleton times added
		if (tk.getNummer() == 1) {
			pOffset_ProgAtt          = Var.ONE_SEC*Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indGreenWaveOffset.getIndex()];
			pPedMaxRed_ProgAtt       = Var.ONE_SEC*Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indPedMaxRed.getIndex()];
			pFixedCycleTime_ProgAtt  = Var.ONE_SEC*Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indCycleTime.getIndex()];
			pStructureNumber_ProgAtt = Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indStructureNumber.getIndex()];
			pIsCrossMaster_ProgAtt   = Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indIsCrossMaster.getIndex()];
			structure = pStructureNumber_ProgAtt;
		} else {
			pPedMaxRed_ProgAtt2       = Var.ONE_SEC*Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indPedMaxRed2.getIndex()];
			pFixedCycleTime_ProgAtt2  = Var.ONE_SEC*Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indCycleTime2.getIndex()];
			pStructureNumber_ProgAtt2 = Var.controller.dvi35Parameters.parameters[HaifaParametersIndexes.indStructureNumber2.getIndex()];
			structure = pStructureNumber_ProgAtt2;
		}
	}
	
	private static void specialParametersJerusalemPreemption() {
	}

	private static void specialParametersJerusalem() {
		Ver_No					= Var.controller.dvi35Parameters.parameters[  0];
		structure = Struct_No	= Var.controller.dvi35Parameters.parameters[  1];
		CyMax					= Var.controller.dvi35Parameters.parameters[  2] * Var.ONE_SEC;
		Sync_Offset				= Var.controller.dvi35Parameters.parameters[  6];
		Dm_Ex_Det[ 0]			= Var.controller.dvi35Parameters.parameters[ 67];
		Dm_Ex_Det[ 1]			= Var.controller.dvi35Parameters.parameters[ 68];
		Dm_Ex_Det[ 2]			= Var.controller.dvi35Parameters.parameters[ 69];
		Dm_Ex_Det[ 3]			= Var.controller.dvi35Parameters.parameters[ 70];
		Dm_Ex_Det[ 4]			= Var.controller.dvi35Parameters.parameters[ 71];
		Dm_Ex_Det[ 5]			= Var.controller.dvi35Parameters.parameters[ 72];
		Dm_Ex_Det[ 6]			= Var.controller.dvi35Parameters.parameters[ 73];
		Dm_Ex_Det[ 7]			= Var.controller.dvi35Parameters.parameters[ 74];
		Dm_Ex_Det[ 8]			= Var.controller.dvi35Parameters.parameters[ 75];
		Dm_Ex_Det[ 9]			= Var.controller.dvi35Parameters.parameters[ 76];
		Dm_Ex_Det[10]			= Var.controller.dvi35Parameters.parameters[ 77];
		Dm_Ex_Det[11]			= Var.controller.dvi35Parameters.parameters[ 78];
		Dm_Ex_Det[12]			= Var.controller.dvi35Parameters.parameters[ 79];
		Dm_Ex_Det[13]			= Var.controller.dvi35Parameters.parameters[ 80];
		Dm_Ex_Det[14]			= Var.controller.dvi35Parameters.parameters[ 81];
		Dm_Ex_Det[15]			= Var.controller.dvi35Parameters.parameters[ 82];
		Dm_Ex_Det[16]			= Var.controller.dvi35Parameters.parameters[ 83];
		Dm_Ex_Det[17]			= Var.controller.dvi35Parameters.parameters[ 84];
		Dm_Ex_Det[18]			= Var.controller.dvi35Parameters.parameters[ 85];
		Dm_Ex_Det[19]			= Var.controller.dvi35Parameters.parameters[ 86];
	}

	public static void setParams(Node tk) {
		if (!tk.isRegularProgram()) {
			return;
		} else if (Var.controller.isAppHaifa()) {
			ParSets.Det_Param = ParSets.pars_DET.get();
			specialParametersHaifa(tk);
		} else if (Var.controller.isAppJerusalem()) {
			if (Var.controller.isPreemption()) {
				specialParametersJerusalemPreemption();
			} else {
				specialParametersJerusalem();
			}
		} else if (Var.controller.isAppTelAviv()) {
			
		} else {
			return;
		}

		switch (structure) {
		case Var.StructureNumber1:
			struct1(tk);
			break;
		case Var.StructureNumber2:
			struct2(tk);
			break;
		case Var.StructureNumber3:
			struct3(tk);      
			break;
		case Var.StructureNumber4:
			struct4(tk);      
			break;
		default:
			struct1(tk);
			break;
		}
	}
}