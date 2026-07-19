package core.detectors;

import core.Move;
import core.parameters.ParametersIndex;

/**
 * @author Ilia Butvinnik
 *
 */
public class DDetector extends Input {

    /**
     * 
     * @param name
     * @param move
     */
    public DDetector(String name, Move move, ParametersIndex param) {
        super(move.node, name, move, 4000);
        hasMapInput = true;
        node.demands.add(this);
        parametersIndex = inputId - 1;
    }
    
    /**
     * 
     * @param name
     * @param move
     * @param id
     */
    public DDetector(String name, Move move, ParametersIndex param, int id) {
        super(move.node, name, move, 4000, id);
        hasMapInput = true;
        node.demands.add(this);
        parametersIndex = id;
    }
    
    public DDetector setRemoteDetector() {
        return (DDetector)super.setRemoteDetector();
    }
}
