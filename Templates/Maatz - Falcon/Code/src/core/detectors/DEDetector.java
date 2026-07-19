package core.detectors;
/**
 * 
 */
import m0547.Var;
import core.Controller;
import core.Move;
import core.parameters.ParametersIndex;
import vtvar.VarInt;

/**
 * @author Ilia Butvinnik
 *
 */
public class DEDetector extends Input {
    public VarInt gapUnit;
    public int gapIndex;

    /**
     * 
     * @param name
     * @param move
     */
    public DEDetector(String name, Move move, ParametersIndex param) {
        super(move.node, name, move, 4000);
        hasMapInput = true;
        node.demandExtensions.add(this);
        parametersIndex = inputId - 1;
    }
    
    /**
     * 
     * @param node
     * @param name
     * @param move
     * @param id
     */
    public DEDetector(String name, Move move, ParametersIndex param, int id) {
        super(move.node, name, move, 4000, id);
        hasMapInput = true;
        node.demandExtensions.add(this);
        parametersIndex = id;
    }

    public DEDetector setRemoteDetector() {
        return (DEDetector)super.setRemoteDetector();
    }
    
    public void setGapAndModeIndex(int gapIndex) {
        
    }
    
    public void setGap(int value) {
        this.gapUnit = VarInt.init(node, "GAP_" + getName(), value, true, true, true);
    }
    
    /**
     * Depending on the application type, returns the gap unit of this extension detector
     * @return the gap unit (in MS) of this extension detector
     */
    protected int getGap() {
        if (Var.controller.isAppHaifa() || Var.controller.isAppNetiveiAyalon()) {
            if (node.gapSet + getNummer() - 1 < Var.controller.parameters.parametersDetectors.length) {
                return Var.controller.parameters.parametersDetectors[node.gapSet + getNummer() - 1];
            }
        } else if (Var.controller.isAppNetiveiIsrael() || Var.controller.isAppJerusalem()) {
            return Var.controller.parameters.parametersDetectors[getNummer() - 1] * 100;
        } else {
            if (gapUnit != null) {
                return gapUnit.get();
            }
        }
        return Controller.DEFAULT_GAP_UNIT;
    }
    
    /**
     * 
     */
    public void updateState() {
        updateState(!dynLuecke(getGap()));
    }
}
