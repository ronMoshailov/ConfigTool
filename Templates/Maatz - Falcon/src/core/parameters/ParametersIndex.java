package core.parameters;

public class ParametersIndex {
    private int index;
    private int factor;
    private boolean isInner;
    
    public ParametersIndex(int index) {
        this.index = index;
        this.factor = 1;
    }
    
    public ParametersIndex(int index, int factor) {
        this.index = index;
        this.factor = factor;
        this.isInner = false;
    }
    
    public ParametersIndex(int index, int factor, boolean isInner) {
        this.index = index;
        this.factor = factor;
        this.isInner = isInner;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int getFactor() {
        return this.factor;
    }
    
    public boolean getIsInner() {
        return this.isInner;
    }
}
