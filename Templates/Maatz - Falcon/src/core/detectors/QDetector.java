/**
 * 
 */
package core.detectors;

import core.Controller;
import core.Move;
import m0547.Var;
import vtvar.VarInt;

/**
 * @author Ilia Butvinnik
 *
 */
public class QDetector extends Input {
    public VarInt jamUnit;
    public int jamIndex;
    
    private int jamInt;

    /**
     * 
     * @param name
     * @param move
     */
    public QDetector(String name, Move move) {
        super(move.node, name, move, 4000);
        hasMapInput = true;
        node.queues.add(this);
        parametersIndex = inputId - 1;
    }
    
    /**
     * 
     * @param name
     * @param move
     * @param id
     */
    public QDetector(String name, Move move, int id) {
        super(move.node, name, move, 4000, id);
        hasMapInput = true;
        node.queues.add(this);
        parametersIndex = id;
    }
    
    public QDetector setRemoteDetector() {
        return (QDetector)super.setRemoteDetector();
    }
    
    public void setGapAndModeIndex(int gapIndex) {
        
    }
    
    public void setGap(int value) {
        this.jamUnit = VarInt.init(node, "JAM_" + getName(), value, true, true, true);
    }

    /**
     * 
     * @return
     */
    protected int getJam() {
        if (Var.controller.isAppHaifa() || Var.controller.isAppNetiveiAyalon()) {
            if (node.gapSet + getNummer() - 1 < Var.controller.parameters.parametersDetectors.length) {
                return Var.controller.parameters.parametersDetectors[node.gapSet + getNummer() - 1];
            }
        } else if (Var.controller.isAppNetiveiIsrael() || Var.controller.isAppJerusalem()) {
            return Var.controller.parameters.parametersDetectors[getNummer() - 1] * 100;
        } else {
            if (jamUnit != null) {
                return jamUnit.get();
            }
        }
        return Controller.DEFAULT_GAP_UNIT;
    }
    
	public void updateState() {
		jamInt = getJam();
		updateState(belegt() && (jamInt == 0 || (previousState && getStateTimeMS() >= jamInt)));
	}
}
