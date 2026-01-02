package tk;

import det.Detektor;
import sg.Sg;
import special.Preemption;
import ta118.Tk1;

public class Preemptiontk1 extends Preemption {
	private Tk1 _tk;

	public Preemptiontk1(Tk1 tk, int actualDptQuantity, int extraDptQuantity,
			int brt_num, int brts_per_move, int dpt4_timoue, boolean isReset,
			int red_stages, int compMoves) {
		super(tk, actualDptQuantity, extraDptQuantity, brt_num, brts_per_move,
				dpt4_timoue * Var.ONE_SEC, isReset, red_stages, compMoves);

		_tk = tk;
		_tk.preemp = this;
	}

	// Starting the timers when there is RISING of a demand on the specific
	// input, set an departure
	// PT_xx_1 PT_xx_2 PT_xx_3 PT_xx_4
	//  ___   ___   ___  ||  ___
	// |   | |   | |   | || |   |
	// |___| |___| |___| || |___|
	//   1     2     3   ||   4
	//

	// Update according to traffic plan
	// indexes of DPT detectors (starting from 0)
	public static final int DPT_7_1 = 0;
	public static final int DPT_7_2 = 1;
	public static final int DPT_7_4 = 2;
	public static final int DPT_8_1 = 3;
	public static final int DPT_8_2 = 4;
	public static final int DPT_8_4 = 5;
	public static final int DPT_7_3 = 6;
	public static final int DPT_8_3 = 7;

	public static int CumulativeGreen1, CumulativeGreen2, CumulativeGreen3,
	CumulativeGreen4, CumulativeGreen5, CumulativeGreen6;

	public static int IsgMoveSecond_AFA_B1_7 = 0 * Var.ONE_SEC;

	// TimeString constants
	protected static final int TIME_AFA_B1min_C1min_D1min_E1min_BFAmin_A = 118;

	// TimeStringJ constants
	protected static final int TIMEJ_AFA_B1max_C1max_fD1 = 128;

	public static int comp6;
	public static int comp5;
	public static int NormEndTime_AFA;
	public static int comp3;
	public static int comp2;
	public static int tail;
	public static int addToBFA;
	public static boolean addToBFA_first = false;
	public static int CumulativeCycleGT_IA1_1;
	public static boolean Pulse_isOn=false;
	public static boolean RT_IA1;

	public static int nearest, nearest7_1, nearest7_2, nearest7_3, nearest7_4;
	public static int nearest8_1, nearest8_2, nearest8_3, nearest8_4;

	public void InitializeDPT() {
		if (!Var.controller.isPreemption()) {
			return;
		}

		init();
		// | tk | | channels_#|DPT_Logic_Index| Logical | public |sg name | DPT
		// type (1-4) | Corresponding
		// | number | | 1 or 6| 0 - as many as| Channel Num |transport | | 1 -
		// far, 2 - Near| Output to another
		// | | | | there are DPTs| (1-ch. only)| sg | | 3 - Stop Line, |
		// Controller
		// | | | | | | | 4 - Cancellation |
		//		DPT_Detectors[DPT_7_1] = new DPTDetector(_tk, _tk.preemption, 1, DPT_7_1, index++, _tk.k7, new String[] { "7" }, 1, new int[] {	1, 2, 3 }, 0);

		return;
	}

	public void InitializeStages() {
		// stageMap[i] = number of i-th stage (ExtendedPhase)
		// stageMap[0] = _tk.PhA .getNummer();
		// stageMap[1] = _tk.PhEQA.getNummer();
		// stageMap[2] = _tk.PhAFA.getNummer();
		// stageMap[3] = _tk.PhAP .getNummer();
		// stageMap[4] = _tk.PhB .getNummer();
		// stageMap[5] = _tk.PhAFB.getNummer();
		// stageMap[6] = _tk.PhC .getNummer();
		// stageMap[7] = _tk.PhD .getNummer();
		// move[i][0] = signal group number of i-th Public Transport signal
		// group
		//		moveMap[0][0] = _tk.k7.getNr();

		//		compMap[0] = _tk.k2.getNr();

		// redTimePhase[i][0] = number of the i-th pedestrian for which red time
		// count is needed
		// redTimePhase[0][0] = _tk.pa.getNr();
		// redTimePhase[1][0] = _tk.pb.getNr();
		// redTimePhase[2][0] = _tk.pc.getNr();
		// redTimePhase[3][0] = _tk.pd.getNr();
		// redTimePhase[4][0] = _tk.pe.getNr();
		// redTimePhase[5][0] = _tk.pf.getNr();
		// redTimePhase[6][0] = _tk.pg.getNr();
		// redTimePhase[7][0] = _tk.ph.getNr();

		//
		// redTimePhase[0][1] = _tk.tp_ab.getNummer();
		// redTimePhase[1][1] = _tk.tp_ba.getNummer();
		// redTimePhase[2][1] = _tk.tp_cd.getNummer();
		// redTimePhase[3][1] = _tk.tp_dc.getNummer();
		// redTimePhase[4][1] = _tk.tp_hi.getNummer();
		// redTimePhase[5][1] = _tk.tp_ih.getNummer();
	}

	public void updateRedTimePhaseDemand() {
		// redTimePhase[i][1] = value of push-button demand status relating to
		// i-th red phase
		// redTimePhase[0][1] = _tk.dem_Pa ? 1 : 0;
		// redTimePhase[1][1] = _tk.dem_Pb ? 1 : 0;
		// redTimePhase[2][1] = _tk.dem_Pc ? 1 : 0;
		// redTimePhase[3][1] = _tk.dem_Pd ? 1 : 0;

	}

	public void updateParameters() {
		// Update all AssumedTimeFromDetectionToTarget parameter of all public
		// transport detectors
//		dptData[DPT_7_1][3] = ParamSet.pAssumedTimeFromDetectionToTarget_7_1;
		//		
		// //Update all PT_DirectionPriority parameter of all public transport
		// signal groups
//		moveMap[0][1] = ParamSet.pPT_DirectionPriority_7; // should be 0 if no

		// Update Compensation parameters: RequestedDuration (column index 2)
		// and Cycle (column index 3)
		// FuncPreemptiontk1.initStageComp(_tk.PhB.getNummer() , _tk.k4.getNr(),
		// ParamSet.pReqCumDuration_4, ParamSet.pCumPeriod_4);
	}

	public void FirstSecondActions() {
		if (_tk.isRegularProgram()) {
			ResetCumulatives();
			ResetRedTimePhases();
		}
	}

	public void EverySecond() {
		// must update according to the order of "compMap"
		//		compDet[0] = !_tk.gap_e2;

		if (ParamSet.structure == Var.StructureNumber2) {
//			getCumulativeCycleGT(_tk.k2, ParamSet.pCumPeriod_2);

//			CumulativeGreen1 = getCumulativeCycleGT(_tk.k1,	ParamSet.pCumPeriod_1);
//			CumulativeGreen2 = getNormCumCycleGT(_tk.k2, ParamSet.pCumPeriod_2);
		}
		emulationEventOccured();
	}

	private void emulationEventOccured() {
	}

	public int getCompMoveCumPeriod(int move) {
		switch (move) {
//		case 1: // _tk.k1.getNr() 
//			return ParamSet.pCumPeriod_1;
		}
		return UNDEFINED;
	}

	public void updateActrosAccessVariables() {
		// counters of closest arrival time
		if (Var.tk1.isRegularProgram()) {
			nearest = normAT;
//			nearest7_1 = dxArrivalTime(_tk.k7.getNr(), DPT_Detectors[DPT_7_1]);
			// nearest7_3 = dxArrivalTime(_tk.k7.getNr(),
			// DPT_Detectors[DPT_7_3]);
		}

	}

	public int getTimeJ(int timeConst) {
		return getTime(timeConst, null);
	}

	public int getTime(int timeConst, Sg ptMove) {
		int x = 0;
		switch (timeConst) {
/*		case TIME_AFA_B1min_C1min_D1min_E1min_BFAmin_A:
			return _tk.PhueAFA_B1.isgDuration() + ParamSet.pMinDuration_B1
			+ _tk.PhueB1_C1.isgDuration() + ParamSet.pMinDuration_C1
			+ _tk.PhueC1_D1.isgDuration() + ParamSet.pMinDuration_D1
			+ _tk.PhueD1_E1.isgDuration() + ParamSet.pMinDuration_E1
			+ _tk.PhueE1_BFA.isgDuration() + ParamSet.pMinDuration_BFA
			+ 0;*/
		}
		return x;
	}

	public int GetRedTimePhase(Sg _sg, Detektor _det) {
		int time = GetRedTimePhase(_sg.getNr(), _det.getNummer());
		return time;
	}

	public int GetarrivalTime() {
		return arrivalTime;
	}

	public int GetnormAT() {
		return normAT;
	}

	public int GetNormMoveAT(Sg move) {
		return normMoveAT[getMoveIndex(move.getNr())];
	}

	// @Override
	public void PostEverySecond() {
		// TODO Auto-generated method stub
	}
}