package ta118;

import hw.tk.HwTeilknoten;
import special.InterStage;
import special.MainStage;
import special.Move;
import special.Node;
import special.Stage;
import tk.Preemptiontk1;
import tk.Var;

public class Tk1 extends Node {
	public int structure;
	public Preemptiontk1 preemption;
	
	//SIGNAL GROUPS
	// write moves here

	//INPUTS
	//traffic detectors
//	public DDetector d2, d6;
//	public EDetector e1, e2, e5, e6;
//	public DEDetector de1;
	
	//pedestrian detectors
//	public TPDetector tp_a, tp_a_b, tp_b;
//	public Input de;

	//OUTPUTS
//	public Output PB;
	
	//STAGES
	//structure 1
	public MainStage PhA;
	public Stage PhEQA, PhB, PhC;
	//structure 2
//	public Stage PhB1, PhC1, PhD1, PhE1;
	
	//INTERSTAGES
	//structure 1
	public InterStage PhueEQA_B, PhueB_C, PhueC_A;
	//structure 2
//	public InterStage PhueEQA_C1, PhueC1_B1, PhueEQA_D1, PhueD1_B1, PhueEQA_E1, PhueE1_B1, PhueEQA_B1, PhueB1_A;

	public boolean isExitConditionsDone() {
		
		isCheckExitConditionsRequired = false;
		
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
	public void checkDetectors() {
		super.checkDetectors();
		
		// Note: special functionality regarding detectors or inputs should be implemented here
	}


	/**
	 * This method determines the start and end of green time of signal groups in Fixed Time Police program
	 */
	public void setFixedPoliceSG() {
//                     sg  Start  Stop - time (in sec) of the green for all phases

//		pj.freiSperr (  72,   84 );
//		
//		k1.freiSperr (  28,   61 );
//		k2.freiSperr (  12,   46 );
//		k3.freiSperr (  84,    7 );
//		k4.freiSperr (  68,   79 );
//		k5.freiSperr (  53,   61 );
//		k6.freiSperr (  12,   20 );
//
//		pa.freiSperr (  69,   19 );
//		pc.freiSperr (  57,   78 );
//		pd.freiSperr (  54,    3 );
//		pf.freiSperr (  88,   22 );
//		pg.freiSperr (  12,   77 );
//		pi.freiSperr (  31,   65 );
//		pi.freiSperr (  87,    8 );
//		pj.freiSperr (  15,   52 );
//		pk.freiSperr (  84,   62 );

		/* blink mutne */
//		Bc.setSgDirekt(Zustand.GELBBLINKEN,    68, 1, 1);
//		Bc.setSgDirekt(Zustand.AUS,            82, 1, 1);
//
//		Bf.setSgDirekt(Zustand.GELBBLINKEN,    84, 1, 1);
//		Bf.setSgDirekt(Zustand.AUS,            10, 1, 1);
//
//		Bi.setSgDirekt(Zustand.GELBBLINKEN,    28, 1, 1);
//		Bi.setSgDirekt(Zustand.AUS,            67, 1, 1);
//
//		Bj.setSgDirekt(Zustand.GELBBLINKEN,    12, 1, 1);
//		Bj.setSgDirekt(Zustand.AUS,            52, 1, 1);
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
		//structure = ((ParametersJerusalem)Var.controller.dvi35Parameters).getStructure();
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
