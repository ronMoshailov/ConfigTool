package core.parameters;

import core.Node;

/**
 * This class handles the basic definition of a controller's parameters interface 
 * @author Ilia Butvinnik
 * @version 2.0.0
 * @since 01/07/2025
 */
public abstract class iParameters {

    private final int parametersUpdateInterval = 4;
    private       int parametersUpdateCounter  = 0;
    
    protected boolean isParametersIncludePhLen = false; 
    
    public abstract void initializeParameters();
    
    public abstract int getParameterValue(int index);
    public abstract int getParameterValue(ParametersIndex index);
    
    public abstract int getMinBySP(int nodeId, int sp);
    public abstract int getMaxBySP(int nodeId, int sp);
    public abstract int getMinTypeBySP(int nodeId, int sp);
    public abstract int getMaxTypeBySP(int nodeId, int sp);
    public abstract int getMinType(int value);
    public abstract int getMaxType(int value);
    public abstract int getCycle();
    public abstract int getCycle(int nodeId);
    public abstract int getStructure();
    public abstract int getStructure(int nodeId);
    public abstract int getOffset();
    public abstract int getOffset(int nodeId);
    public abstract boolean isGWEnable(int programNumber);
    public abstract boolean isGWEnable(int nodeId, int programNumber);
    public abstract boolean isMaster(int programNumber);
    public abstract boolean isMaster(int nodeId, int programNumber);
    public abstract boolean isSlave(int programNumber);
    public abstract boolean isSlave(int nodeId, int programNumber);
    public abstract boolean isClockSync(int programNumber);
    public abstract boolean isClockSync(int nodeId, int programNumber);
    public abstract boolean isSubmaster(int programNumber);
    public abstract boolean isSubmaster(int nodeId, int programNumber);
    public abstract boolean isLocalSubmaster(int programNumber);
    public abstract boolean isLocalSubmaster(int nodeId, int programNumber);
    /**
     * Returns the index of the Dx Enabled/Active parameter if exists and Var.NONE otherwise
     */
    public abstract int getDxActive();

    public abstract boolean isSetDemandDetector(int nodeId, int parametersId);
    public abstract boolean isSetExtensionDetector(int nodeId, int parametersId);
    public abstract boolean isDisabledDetector(int nodeId, int parametersId);
    
    public abstract boolean isDuration  (int type);
    public abstract boolean isAbsolute  (int type);
    public abstract boolean isComplement(int type);
    public abstract int isByMove(int type);
    
    public void updateParameters(Node node) {
        parametersUpdateCounter++;
        if (parametersUpdateCounter >= parametersUpdateInterval)
        {
            vtvar.VtVar.update(node);
            parametersUpdateCounter = 0;
        }
    }
    
    public boolean isIncludePhLen() {
        return this.isParametersIncludePhLen;
    }
    
    public void setIncludesPhLen(boolean value) {
        this.isParametersIncludePhLen = value;
    }
}
