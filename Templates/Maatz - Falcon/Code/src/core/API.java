package core;

import core.detectors.Output;

public final class API {
    
    public static boolean isElementActivated(Move move) {
        if (move == null) {
            return false;
        }
        
        return move.isActivated();
    }
    
    public static boolean isElementActive(Move move) {
        if (move == null) {
            return false;
        }
        
        return move.isActive();
    }
    
    public static boolean isElementDeactivated(Move move) {
        if (move == null) {
            return false;
        }
        
        return move.isDeactivated();
    }
    
    public static boolean isElementInactive(Move move) {
        if (move == null) {
            return false;
        }
        
        return move.isInactive();
    }
    
    /**
     * This method activates an Output (if it's not null)
     * @param output - the Output to be activated
     */
    public static void setElementActive(Output output) {
        if (output != null) {
            output.setAusgang();
        }
    }
    
    /**
     * This method deactivates an Output (if it's not null)
     * @param output - the Output to be deactivated
     */
    public static void setElementNotActive(Output output) {
        if (output != null) {
            output.resetAusgang();
        }
    }
}
