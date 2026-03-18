package special;
/**
 * 
 * Extends the Ausgang class
 * 
 * revision: 0.0.1
 * last modified: 15/05/2019
 * @author Ilia Butvinnik
 *
 */
import det.Ausgang;

public class Output extends Ausgang {
	
	private static int outputId = 1;
	
	public Node node;

	protected int stateTime;
	protected boolean currentState;
	protected boolean previousState;
	protected boolean isActive;
	protected boolean isActivated;
	protected boolean isDeactivated;
	
	public Output(Node node, String name) {
		super(node, name, outputId++);
		this.node = node;
	}
	
	public Output(Node node, String name, int index) {
		super(node, name, index);
		this.node = node;
	}
	
	public void UpdateState() {
		currentState = getAusgang();
		
		isActive = getAusgang();
		isActivated = isActive && !previousState;
		isDeactivated = !isActive && previousState;
		
		if (currentState == previousState) {
			if (stateTime < 999000) {
				stateTime += node.getZyklDauer(); 
			}
		} else {
			stateTime = 0;
		}
		
		previousState = currentState;
	}
	
	public int GT() {
		return isActive ? stateTime : 0;
	}
	
	public int RT() {
		return (!isActive) ? stateTime : 0;
	}
	
	public boolean IsActive() {
		return isActive;
	}
	
	public boolean IsInactive() {
		return !isActive;
	}
	
	public boolean IsActivated() {
		return isActivated;
	}
	
	public boolean IsDeactivated() {
		return isDeactivated;
	}
}
