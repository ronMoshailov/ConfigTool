package core.detectors;

public interface LRTInterface {
    public boolean isActive();
    public boolean isInactive();
    public boolean isActivated();
    public boolean isDeactivated();
    public LRTInterface getOutOfOrderInput();
    public void resetStateTime();
    public void updateState();
    public boolean isActualBRT();
    public boolean isLocalDetector();
}
