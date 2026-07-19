package core.programs;
import core.Node;
import sg.*;
import vt.*;

public class AusProgramm extends AusProg{
	private Node node;

	public AusProgramm(Node node, String name, int nr, int tu) {
		super(node, name, nr, tu);
		this.node = node;
	}

	public void programmFunktion()
	{
		//"turning off" all traffic and pedestrian sg
		node.setTrafficSG        (Zustand.DUNKEL, 0);
		node.allPedestrianSGDunkel(0);
		//"turning off" all blink mutne
		node.setConditionedBlinks(Zustand.AUS, 0);

		if  (node.getProgAnf().getProg()== node.getDunkelProgramm())
		{
			//"turning off" all blinks
			node.setRegularBlinks(Zustand.AUS, 0);
		}
	}
}