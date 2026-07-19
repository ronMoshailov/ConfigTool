package j200;


import enums.ExtensionType;
import vt.*;
import special.Move;
import special.Stage;

public class PhaseC extends Stage {
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
	public PhaseC(Tk1 node, String name, int number, int length, boolean isStopInPolice, Move[] sgs) {
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
	public PhaseC(Tk1 node, String name, int numberr, int length, int sp, boolean isStopInPolice, Move[] sgs) {
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
	public int getMinExt() {
		minType = ExtensionType.DURATION;
		return ParametersJerusalem.GTmin_C_Duration;
	}

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
	public int getMaxExt() {
		maxType = ExtensionType.DURATION;
		return ParametersJerusalem.GTmax_C_Duration;
	}

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
				return startNextPhase(node.PhueC_B);
			}
		}

		return KEINE_UMSCHALTUNG;
	}
}
