package ta118;

import enums.ExtensionType;
import special.MainStage;
import special.Move;
import tk.Var;
import vt.Phase;

public class PhaseA extends MainStage {
	private Tk1 node;
	/**
	 * Constructor for Haifa applications or for Jerusalem preemption applications
	 * @param node - the node to which the stage refers
	 * @param name - the name of the stage
	 * @param number - the number of the stage
	 * @param startLengths - array of length of this stage in the beginning of the program cycle (array size = number of structs)
	 * @param endLengths - array of length of this stage in the end of the program cycle (array size = number of structs)
	 * @param isStopInPolice - whether a non-fixed police program should stop in this stage
	 * @param sgs - list of moves that must open in this stage
	 */
	public PhaseA(Tk1 node, String name, int number, int[] startLengths, int[] endLengths, boolean isStopInPolice, Move[] sgs) {
		super(node, name, number, startLengths, endLengths, isStopInPolice, sgs);
		this.node = node;
		this.spNum = 0;
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
		return ((ParametersTelAviv)Var.controller.dvi35Parameters).getMinimum(spNum);
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
		maxType = ((ParametersTelAviv)Var.controller.dvi35Parameters).getType(spNum) > 0 ? ExtensionType.ABSOLUTE : ExtensionType.DURATION;
		return ((ParametersTelAviv)Var.controller.dvi35Parameters).getMaximum(spNum);
	}

	/**
	 * This method is called when the stage is terminated
	 */
	protected void deactivated() {
	}

	/**
	 * called once per program cycle at the beginning of the cycle
	 */
	protected void startOfCycleEvent() {
	}

	/**
	 * called once per program cycle at the end of the cycle
	 */
	protected void endOfCycleEvent() {
	}

	/**
	 * This method is called every scan-cycle while the stage is active (in the beginning of the cycle)
	 */
	public Phase phasenFunktionCycleStart() {			
		if (isStartStageDoneTA())
		{
			return startNextPhase(node.PhEQA);
		}
		return KEINE_UMSCHALTUNG;
	}

	/**
	 * This method is called every scan-cycle while the stage is active (in the end of the cycle)
	 */
	public Phase phasenFunktionCycleEnd() {
		
		if (isEndStageDone())
		{
			return startNextPhase(node.PhA);
		}
		return KEINE_UMSCHALTUNG;
	}
}