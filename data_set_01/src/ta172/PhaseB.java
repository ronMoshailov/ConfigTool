package ta172;


import vt.*;
import special.Move;
import special.Stage;

public class PhaseB extends Stage {
	private Tk1 node;

	/**
	 * Constructor for Haifa applications or for Jerusalem preemption applications
	 * @param node - the node to which the stage refers
	 * @param name - the name of the stage
	 * @param number - the number of the stage
	 * @param length - the minimal (skeleton) length of the stage
	 * @param isStopInPolice - whether a non-fixed police program should stop in this stage
	 * @param sgs - list of moves that must open in this stage
	 */
	public PhaseB(Tk1 node, String name, int number, int length, boolean isStopInPolice, Move[] sgs) {
		super(node, name, number, length, isStopInPolice, sgs);
		this.node = node;
	}

	/**
	 * Constructor for applications with parameters based on stop-points
	 * @param node - the node to which the stage refers
	 * @param name - the name of the stage
	 * @param number - the number of the stage
	 * @param length - the minimal (skeleton) length of the stage
	 * @param sp - this stage's stop-point number
	 * @param isStopInPolice - whether a non-fixed police program should stop in this stage
	 * @param sgs - list of moves that must open in this stage
	 */
	public PhaseB(Tk1 node, String name, int numberr, int length, int sp, boolean isStopInPolice, Move[] sgs) {
		super(node, name, numberr, length, sp, isStopInPolice, sgs);
		this.node = node;
	}

	/**
	 * This methods does two things:<br>
	 * <ol>
	 * 	<li>
	 * 		Sets the type of Minimum extension:
	 * 		<ul>
	 * 			<li>duration</li>
	 * 			<li>absolute</li>
	 * 			<li>complement</li>
	 * 		</ul>
	 * 	</li>
	 * 	<li>Returns the required value of the Minimum extension</li>
	 * </ol>
	 * @return returns the Minimum extension value
	 */
	//	public int getMinExt() {
	//		minType = ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;
	//		return ((ParametersTelAviv)Var.controller.dvi35Parameters).getMinimum(spNum);
	//	}

	/**
	 * This methods does two things:<br>
	 * <ol>
	 * 	<li>
	 * 		Sets the type of Maximum extension:
	 * 		<ul>
	 * 			<li>duration</li>
	 * 			<li>absolute</li>
	 * 			<li>complement</li>
	 * 		</ul>
	 * 	</li>
	 * 	<li>Returns the required value of the Maximum extension</li>
	 * </ol>
	 * @return returns the Maximum extension value
	 */
	//	public int getMaxExt() {
	//		maxType = ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;
	//		return ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(spNum);
	//	}

	/**
	 * This method is called when the stage is terminated
	 */
	protected void deactivated()
	{
	} 

	/**
	 * This method is called every scan-cycle while the stage is active
	 */
	public Phase phasenFunktion() {
		stopInPolice();

		if (getPhasenZeit() >= node.lenPhue + getPhLenMS())
		{
			gaps = true;

			isStageDoneFlag = isStageDone(gaps);

			if (isStageDoneFlag) 
			{
				if(node.d_3.IsActive() || Var.hand_active)
					return startNextPhase(node.PhueB_C);				
				return startNextPhase(node.PhueB_D);
			}
		}

		return KEINE_UMSCHALTUNG;
	}
}
