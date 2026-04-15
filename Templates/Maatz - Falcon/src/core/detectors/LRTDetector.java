///**
// * 
// */
//package core.detectors;
//
//import core.Move;
//
///**
// * @author Ilia Butvinnik
// *
// */
//public class LRTDetector extends Input implements LRTInterface {
//	
//	public LRTDetector outOfOrderInput;
//	public boolean isActualBRT = false;
//	
//	/**
//	 * Constructor of an BRT detector
//	 * @param name - name of the detector
//	 * @param move - the move to which the BRT detector refers
//	 * @param hasFaultChannel - whether there is an additional channel for "Out of Order" detection
//	 */
//	public LRTDetector(String name, Move move, boolean hasFaultChannel) {
//		super(name, 0, 10, 0, move, 4000);
//		isActualBRT = true;
//		isSettableFromCC = false;
//		
//		if (hasFaultChannel) {
//			outOfOrderInput = new LRTDetector("OOO" + name, move, false);
//		}
//	}
//	
//	public LRTDetector(String name, Move move, boolean hasFaultChannel, boolean isActualDetector) {
//		super(name, 0, 10, 0, move, 4000);
//		isActualBRT = isActualDetector;
//		isSettableFromCC = false;
//		
//		if (hasFaultChannel) {
//			outOfOrderInput = new LRTDetector("OOO" + name, move, false);
//		}
//	}
//	
//	public void updateState() {
//		super.updateState();
//		if (outOfOrderInput != null) {
//			outOfOrderInput.updateState();
//		}
//	}
//
//    public boolean isActivated() {
//        return isActivated;
//    }
//
//    public boolean isActive() {
//        return isActive;
//    }
//
//    public boolean isDeactivated() {
//        return isDeactivated;
//    }
//
//    public boolean isInactive() {
//        return !isActive;
//    }
//
//    public LRTInterface getOutOfOrderInput() {
//        return outOfOrderInput;
//    }
//
//    public void resetStateTime() {
//        stateTime = 0;
//    }
//    
//    public boolean isActualBRT() {
//        return isActualBRT;
//    }
//    
//    public boolean isLocalDetector() {
//        return true;
//    }
//}
