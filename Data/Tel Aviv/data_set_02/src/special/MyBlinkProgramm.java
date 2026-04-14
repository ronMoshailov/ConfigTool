package special;
import sg.*;
import special.Node.SpecialProgram;
import tk.Var;
import vt.*;

public class MyBlinkProgramm extends BlinkProg{
	private Node node;

    public MyBlinkProgramm(Node node, String name, int no, int tu) {
        super(node, name, no, tu);
        this.node = node;
    }

    // After switching ON the controller it is needed to skip the 5 seconds of Blinking program.
    // The 5 seconds of Blinking program is a standard in Germany and not in Israel.
    // Also from normal operation to dark (program 99) it is needed to skip the 5 seconds of Blinking program.
    // in the implementation below we recognize the second zero of the program and update it to the Blinking program 
    // duration (5 seconds in our case) in order to skip Blinking.
    // Changed by Menorah
   public void programmFunktion(){
        if  (((node.getProgAnf().getProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) ||
        	(node.getProgAnf().getProg().getNummer() == SpecialProgram.Police.getProgramNumber()) ||
        	((node.getProgAnf().getProg().getNummer() > 0) && (node.getProgAnf().getProg().getNummer() <= Var.controller.getMaxProgramsNumber())))
        	&& (node.getProgSek() == 0))
        {
            node.setProgZeit((node.blinkprog.getUmlaufZeit()*1000)-500);
            return;
        }
        
        if  ((node.getProgAnf().getProg()== node.getDunkelProgramm()) && (node.getProgSek() == 0)) {
            //"turning off" all blinks
        	node.setConditionedBlinks(Zustand.AUS, 0);
        	node.setRegularBlinks    (Zustand.AUS, 0);
            
            node.setProgZeit((node.blinkprog.getUmlaufZeit()*1000)-500);
        } else {
            //set all traffic sg to blink
        	node.setTrafficSG        (Zustand.GELBBLINKEN, 0);
            //"turning off" all pedestrian sg
        	node.allPedestrianSGDunkel(0);
            //set all blinks to blink
        	node.setConditionedBlinks(Zustand.GELBBLINKEN, 0);
        	node.setRegularBlinks    (Zustand.GELBBLINKEN, 0);
        }
    }
}