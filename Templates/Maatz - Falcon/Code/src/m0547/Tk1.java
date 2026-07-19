package m0547;

import core.InterStage;
import core.MainStage;
import core.Move;
import core.Node;
import core.Stage;
import core.detectors.*;
import hw.tk.HwTeilknoten;

/**
 * @author ilia
 * 
 */
public class Tk1 extends Node {
    
    public int structure;
    
	// Moves (signal groups)
    // Traffic moves: vehicles, LRTs, BRTs, bicycles
	public Move k1, k2, k3, k4;
	// Pedestrians
//	public Move pa, pb, pc, pd, pe, pf, pg, ph, pi;
	// Blinkers (regular and conditional) (FFTs should not be defined in here)
//	public Move Be, Bg, Bi;

	// Detectors & inputs
	// Demand Detectors
	public DDetector d_3, d_4;
	// Demand & Extension Detectors (as a single detector)
//	public DEDetector d_3, d_4;
	// Extension Detectors
	public EDetector e_1, e_2, e_3, e_4;
	// Queue Detectors
//	public QDetector q_5;
	// Pedestrian Push-Buttons
//	public TPDetector tp_a, tp_ab, tp_c, tp_e, tp_g;
	// Inputs & Pulses
	public Input ps2;
	// Outputs
	// Timed Outputs (outputs that turn off x seconds after they've been turned on)
//	public TimedOutput to1, to2;
	public Output ps1;
	
	// Main Stage(s)
	public MainStage PhA;
    // Stages
	public Stage PhB, PhC, PhEQA, PhEQA1;
//	public Stage PhBtmp;

	// Interstages
	public InterStage PhueEQA_B, PhueEQA1_C, PhueB_C, PhueB_A, PhueC_A;

	                  
//	public Preemptiontk1 preemption;

	public boolean isExitConditionsDone() {

		isCheckExitConditionsRequired = false;

		if (isCheckExitConditionsRequired) {
			if (k1.greencheck(0) && 
			    k2.greencheck(0)) 
			{
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * This function checks whether a Dx logic is completed.
	 * Common cases are:
	 *   - if working in green-wave (to prevent desynchronization
	 *   - if at least one demand or push-button is active
	 *   - if Dx functionality is disabled by a parameter
	 * Of course, custom logic can be added according to the requiremets of the traffic engineer  
	 * @return true if Dx is completed and the node can continue its logic or false if the node has to stay at the Dx point
	 */
    public boolean isDx() {
        return false;
    }

	/**
	 * This method updates detectors flags according to vehicle presence and/or
	 * parameters and/or control center demand
	 */
	public void checkDetectors() {
	    super.checkDetectors();
		// Note: special functionality regarding detectors or inputs should be
		// implemented here
	}

	/**
	 * This method determines the start and end of green time of signal groups
	 * in Fixed Time Police program
	 */
	public void setFixedPoliceSG() {
		// sg Start Stop - time (in sec) of the green for all phases
		 k1.turnOnAndOff( 41, 14);
		 k2.turnOnAndOff( 41, 25);
		 k3.turnOnAndOff( 30, 36);
		 k4.turnOnAndOff( 19, 25);

		/* blink mutne */
//         Be.turnOnAndOff( 29, 45);
	}
	
    /**
	 * This method determines what the OperationMain function should execute
	 */
	public void operationMainFunction() {
		// Note: special functionality (not related to preemption) should be implemented here
	    
	    if (!isRegularProgram()) {
	        ps1 .turnOff();
	    }
	    
		if (Var.controller.isPreemption()) {
		    // TODO:
//			preemption.runSecond();

			// Note: special functionality (related to this node and to
			// preemption) should be implemented here
		}
	}

	// TODO: delete after NATI test
	public void postMainFunction() {
	    if (Var.controller.isPreemption()) {
	        // TODO:
//            preemption.PostEverySecond();
        }    
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
		nodeInitializer = new InitTk1(this); // TODO: Update this field
												// according to Tk number
												// (InitTk1/2/3)
	}
	
	public void startNode(){
		super.startNode();
		// TODO:
//		preemption.processMovesChanges();	
	}
}
