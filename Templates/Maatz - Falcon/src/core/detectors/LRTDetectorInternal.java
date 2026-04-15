///**
// * 
// */
//package core.detectors;
//
//import core.Move;
//import core.Node;
//import core.preemption.PreemptionJ;
//import core.preemption.PreemptionJ.PreemptionDetectorType;
//import m0.Var;
//import vtvar.VtVarStrukt;
//import det.Detektor;
//import det.DetektorIntern;
//
///**
// * @author Ilia Butvinnik
// *
// */
//public class LRTDetectorInternal extends DetektorIntern implements LRTInterface {
//	
//	public LRTDetectorInternal outOfOrderInput;
//	public PreemptionDetectorType detectorType;
//	public boolean isActualBRT = false;
//	public VtVarStrukt detSetParam;
//	public Node node;
//	public Move move;
//	public boolean isActive, isActivated, isDeactivated;
//	
//	protected boolean currentState, previousState;
//	protected int stateTime;
//	private int i;
//	
//	protected LRTDetectorInternal[] mirroredDetectors = null;
//	
//	public LRTDetectorInternal(String name, Move move, boolean hasFaultChannel, boolean isActualDetector, PreemptionDetectorType detectorType) {
//	    super(move.node, name, 0, 10, 0, Input.getCurrentId(), move);
//        Input.skipId();
//        this.node = move.node;
//        this.move = move;
//		this.isActualBRT = isActualDetector;
//		this.detectorType = detectorType;
//        
//        detSetParam = VtVarStrukt.init(Var.tk1, "SIT_SET_" + name, new int[] { 2 }, true, true, true);
//		
//		if (hasFaultChannel) {
//		    PreemptionDetectorType oooType;
//		    if (this.detectorType == PreemptionDetectorType.TYPE_DM) {
//		        oooType = PreemptionDetectorType.TYPE_OOODM;
//		    } else if (this.detectorType == PreemptionDetectorType.TYPE_DQ) {
//		        oooType = PreemptionDetectorType.TYPE_OOODQ;
//		    } else {
//		        oooType = PreemptionDetectorType.TYPE_OOODP;
//		    }
//			outOfOrderInput = new LRTDetectorInternal("OOO" + name, move, false, this.isActualBRT, oooType);
//		}
//	}
//	
//	public void initializeMirroredDetectors(LRTDetectorInternal[] mirroredDetectors) {
//	    if (mirroredDetectors == null)
//	        return;
//	        
//	    this.mirroredDetectors = new LRTDetectorInternal[mirroredDetectors.length];
//	    for (i = 0; i < mirroredDetectors.length; i++) {
//	        this.mirroredDetectors[i] = mirroredDetectors[i];
//	    }
//	}
//	
//	public boolean isActive() {
//	    return isActive;
//	}
//	
//	public boolean isInactive() {
//	    return !isActive;
//	}
//	
//	public boolean isActivated() {
//	    return isActivated;
//	}
//	
//	public boolean isDeactivated() {
//	    return isDeactivated;
//	}
//
//    public LRTInterface getOutOfOrderInput() {
//        return outOfOrderInput;
//    }
//	
//	public void updateState() {
//	    boolean isSetOnJLRT = detSetParam != null && detSetParam.get(0) == 1;
//        boolean isSetOffJLRT = detSetParam != null && detSetParam.get(0) == 0;
//        
//        if (Var.controller.sit.isConnected()) {
//            if (Var.controller.sit.getDetector(move.getLRTIndex(), detectorType)) {
//                set();
//            } else {
//                reset();
//            }
//        } else {
//            reset();
//        }
//        
//        currentState = (belegt() || isSetOnJLRT) && !isSetOffJLRT;
//        if (this.mirroredDetectors != null && Var.controller.isUseSIT()) {
//            for (i = 0; i < this.mirroredDetectors.length; i++) {
//                currentState |= this.mirroredDetectors[i].belegt() || Var.controller.sit.getDetector(this.mirroredDetectors[i].move.getLRTIndex(), this.mirroredDetectors[i].detectorType);
//            }
//        }
//        
//        isActive = currentState;
//        isActivated = isActive && !previousState;
//        isDeactivated = !isActive && previousState;
//
//        if (currentState == previousState) {
//            if (stateTime < Var.MAX_COUNTER_VALUE) {
//                stateTime += node.getZyklDauer(); 
//            }
//        } else {
//            stateTime = 0;
//        }
//        
//        previousState = currentState;
//	}
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
