package ta172;

import hw.tk.HwTeilknoten;
//import special.DDetector;
//import special.EDetector;
import special.DDetector;
import special.EDetector;
import special.InterStage;
import special.MainStage;
import special.Move;
import special.Node;
import special.Stage;
import ta172.Preemptiontk1;

public class Tk1 extends Node {

	//signal groups
	// write moves here

	//inputs
//	public DEDetector de_4, de_5;
	public DDetector d_3, d_6;
	public EDetector e_3, e_6;
//	public TPDetector tp_c, tp_d, tp_g,tp_h;
//	public Input de, dp;

	//outputs
//	public Output PB;
	//stages
	public MainStage PhA;
	public Stage PhEQA, PhB, PhC, PhD, PhE;
	
	//interstages
	public InterStage PhueEQA_B, PhueB_C, PhueB_D, PhueC_D, PhueD_A, PhueD_E, PhueE_A;
	
	public Preemptiontk1 preemption;

	public boolean isExitConditionsDone() {
		
		isCheckExitConditionsRequired = true;
		
		if (isCheckExitConditionsRequired)
		{
//			if (pe.greencheck(5000))
//			{
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

//		k1.freiSperr (  63,   43 );
//		k2.freiSperr (  30,   47 );
//		k3.freiSperr (  50,   26 );
//
//		pa.freiSperr (  48,   59 );
//		pb.freiSperr (  31,   43 );
//		pc.freiSperr (  53,   27 );

		/* blink mutne */
//		Bc.setSgDirekt(Zustand.GELBBLINKEN,    50, 1, 1);
//		Bc.setSgDirekt(Zustand.AUS,            29, 1, 1);

	}

	/**
	 * This method determines what the OperationMain function should execute 
	 */
	public void operationMainFunction() {
		// Note: special functionality (not related to preemption) should be implemented here
		if (!this.isRegularProgram() || this.isOffline())
		{
			
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
