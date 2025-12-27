package ta69;

import extra.Preemptiontk1;
import hw.tk.HwTeilknoten;
import special.DDetector;
import special.EDetector;
import special.InterStage;
import special.MainStage;
import special.Move;
import special.Node;
import special.Stage;
import special.TPDetector;
import special.Var;

public class Tk1 extends Node {
	public int structure;
	public Preemptiontk1 preemption;
	
	//SIGNAL GROUPS

	public Move k1, k2;  // traffic
	public Move pa, pb; // pedestrians
	public Move Ba, Bb;	// blinkers

	//INPUTS
	//traffic detectors
	public DEDetector de_4, de_5;
	public DDetector d_1;
	public EDetector e_3, e_4;
    public QDetector q_1, q_2, q_55;

	//pedestrian detectors
	public TPDetector tp_a, tp_b, tp_cc, tp_cd, tp_dc, tp_dd;
//	public Input de, dp;

	//OUTPUTS
//	public Output outPulse;
	
	//STAGES
	//structure 1
	public MainStage PhA;
	public Stage PhEQA, PhB, PhC;
	//structure 2
//	public Stage PhD;
	
	//INTERSTAGES
	//structure 1
	public InterStage PhueEQA_B, PhueB_C, PhueC_A;
	//structure 2
//	public InterStage PhueB1_C, PhueC_A;

	public boolean isExitConditionsDone() {
		isCheckExitConditionsRequired = false;
		
		if (isCheckExitConditionsRequired) {
//			if (pe.greencheck(5000)) {
				return true;
//			}
			
//			if (exitConditionsCounter < Var.MAX_COUNTER_VALUE) {
//				exitConditionsCounter += Var.tk1.getZyklDauer(); 
//			}
//			return false;
		}
		return true;
	}

	/**
	 * This method updates detectors flags according to vehicle presence and/or parameters and/or control center demand
	 */
	public void checkDetectors() {
		super.checkDetectors();
		// Note: special functionality regarding detectors or inputs should be implemented here
	}


	/**
	 * This method determines the start and end of green time of signal groups in Fixed Time Police program
	 */
	public void setFixedPoliceSG() {
		//      sg            Start  Stop - time (in sec) of the green for all phases
		//		k1.freiSperr (  67,   14 );
		//		k2.freiSperr (   6,   14 );
		//
		//		pa.freiSperr (  18,   55 );
		//		pb.freiSperr (  10,   23 );

		/* blink mutne */
		//		Ba.setSgDirekt(Zustand.GELBBLINKEN,    11, 1, 1);
		//		Bb.setSgDirekt(Zustand.AUS,            20, 1, 1);
	}

	/**
	 * This method determines what the OperationMain function should execute 
	 */
	public void operationMainFunction() {
		// Note: special functionality (not related to preemption) should be implemented here
		if (!this.isRegularProgram() || this.isOffline()) {
		}
		
		// Note: special functionality (related to this node and to preemption) should be implemented here
		if (Var.controller.isPreemption()) {
			preemption.runSecond();
		}
	}

	public void postMainFunction() {
	}

	/**
	 * Constructor for Tk1
	 * @param name - name of the node
	 * @param hwTk - hardware of the node
	 * @param dx - flag that is true only if Dx should be used in this node
	 * @param firstExt - length (in seconds) of extension of main stage in first cycle
	 */
	public Tk1(String name, HwTeilknoten hwTk, boolean dx, int firstExt) {
		super(name, hwTk, dx, firstExt);
		nodeInitializer = new InitTk1(this); // TODO: Update this field according to Tk number (InitTk1/2/3)
	}
}
