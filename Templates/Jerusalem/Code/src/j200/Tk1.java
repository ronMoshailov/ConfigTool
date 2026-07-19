package j200;

import j200.Preemptiontk1;
import hw.tk.HwTeilknoten;
import special.InterStage;
import special.MainStage;
import special.Move;
import special.Node;
import special.Stage;

public class Tk1 extends Node {

	//signal groups
	public Move k1, k10, k3, k4, k5, k6; // traffic
	public Move pa, pb, pc, pd, pe, pf, pg, ph; // pedestrians
	public Move Bd, Bg, Bh, Bl;	// blinkers

	//inputs
//	public DEDetector de_2;
//	public DDetector d_3;
//	public EDetector e_6;
//	public TPDetector tp_a_a, tp_a_b, tp_b_a, tp_b_b;
//	public Input de, dp;

	//outputs
//	public Output S1;
	//stages
	public MainStage PhA;
	public Stage PhB, PhC;

	//interstages
	public InterStage PhueA_C, PhueC_B, PhueB_A;

	public Preemptiontk1 preemption;

	public boolean isExitConditionsDone() {
		
		isCheckExitConditionsRequired = false;
		
		if (isCheckExitConditionsRequired)
		{
			if (pd.greencheck(5000))
			{
				return true;
			}
			
			if (exitConditionsCounter < Var.MAX_COUNTER_VALUE) {
				exitConditionsCounter += Var.tk1.getZyklDauer(); 
			}
			return false;
		}
		return true;
	}

	/**
	 * This method updates detectors flags according to vehicle presence and/or parameters and/or control center demand
	 */
	public void checkDetectors()
	{
		super.checkDetectors();
		
		// Note: special functionality regarding detectors or inputs should be implemented here
	}


	/**
	 * This method determines the start and end of green time of signal groups in Fixed Time Police program
	 */
	public void setFixedPoliceSG() {
		//                     sg  Start  Stop - time (in sec) of the green for all phases
//		k1.freiSperr (  67,   14 );
//		k2.freiSperr (   6,   14 );
//		k3.freiSperr (  18,   28 );
//		k6.freiSperr (  51,    3 );
//
//		pa.freiSperr (  18,   55 );
//		pb.freiSperr (  10,   23 );
//		pc.freiSperr (  32,   11 );

		/* blink mutne */
//		bd.setSgDirekt(Zustand.GELBBLINKEN,    11, 1, 1);
//		bd.setSgDirekt(Zustand.AUS,            20, 1, 1);
	}

	/**
	 * This method determines what the OperationMain function should execute 
	 */
	public void operationMainFunction() {
		// Note: special functionality (not related to preemption) should be implemented here
		if (!this.isRegularProgram() || this.isOffline())
		{
//			this.PB1.resetAusgang();
		}
		
		if (Var.controller.isPreemption()) {
			preemption.runSecond();
			
			// Note: special functionality (related to this node and to preemption) should be implemented here
		}
	}

	public void postMainFunction() 
	{
		
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
