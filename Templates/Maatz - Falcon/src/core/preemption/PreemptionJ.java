//package core.preemption;
///************************************************************************************************
// *                                                                                              *
// *  Contractor     : M E N O R A                                                                *
// *  City/Authority : Haifa																		*
// *  Junction No.   : 239                                                                     	*
// *  Junction Name  : Varburg / Rains / HaTkuma Yahiam - Maavar Hazaya							*
// *  Equipmentno.   : h239                                                                    	*
// *                                                                                              *
// ************************************************************************************************/
//import core.Move;
//import core.Node;
//import parameters.ParamSetJerusalem.ParametersIndex;
//import m0.Var;
//
//abstract public class PreemptionJ
//{
//	public PreemptionJ(Node node, int brtMoves, int brtsPerMove, int compMoves) {
//		this.node = node;
//		this.BrtMoves = brtMoves;
//		this.BrtsPerMove = brtsPerMove;
//		this.CompMoves = compMoves;
//	}
//
//	public static final int NONE        =  999;
//	public static final int CT_NONE     =   -1;
//	public static final int UNDEFINED	=   -1;
//	public static final int ResetDec	= -999;
//    
//	public static class PreemptionDetectorType {
//		public static final PreemptionDetectorType TYPE_DL     = new PreemptionDetectorType( 0);
//		public static final PreemptionDetectorType TYPE_DP1    = new PreemptionDetectorType( 1);
//		public static final PreemptionDetectorType TYPE_DP2    = new PreemptionDetectorType( 2);
//		public static final PreemptionDetectorType TYPE_DS     = new PreemptionDetectorType( 3);
//		public static final PreemptionDetectorType TYPE_DQ     = new PreemptionDetectorType( 4);
//		public static final PreemptionDetectorType TYPE_OOODL  = new PreemptionDetectorType( 5);
//		public static final PreemptionDetectorType TYPE_OOODP1 = new PreemptionDetectorType( 6);
//		public static final PreemptionDetectorType TYPE_OOODP2 = new PreemptionDetectorType( 7);
//		public static final PreemptionDetectorType TYPE_OOODS  = new PreemptionDetectorType( 8);
//		public static final PreemptionDetectorType TYPE_OOODQ  = new PreemptionDetectorType( 9);
//		public static final PreemptionDetectorType TYPE_DP     = new PreemptionDetectorType(10);
//		public static final PreemptionDetectorType TYPE_DM     = new PreemptionDetectorType(11);
//		public static final PreemptionDetectorType TYPE_OOODP  = new PreemptionDetectorType(12);
//		public static final PreemptionDetectorType TYPE_OOODM  = new PreemptionDetectorType(13);
//		public static final PreemptionDetectorType TYPE_DF     = new PreemptionDetectorType(14);
//		public static final PreemptionDetectorType TYPE_OOODF  = new PreemptionDetectorType(15); 
//                                            
//		private final int detType;
//
//		public PreemptionDetectorType(int detType) {
//			this.detType = detType;
//		}
//
//		public int getDetectorType() {
//			return this.detType;
//		}
//	}
//    
//	public static class PreemptionDetectorParameterType {
//		public static final PreemptionDetectorParameterType TYPE_TL			    = new PreemptionDetectorParameterType( 0);
//		public static final PreemptionDetectorParameterType TYPE_TP1		    = new PreemptionDetectorParameterType( 1);
//		public static final PreemptionDetectorParameterType TYPE_TP2		    = new PreemptionDetectorParameterType( 2);
//		public static final PreemptionDetectorParameterType TYPE_TS			    = new PreemptionDetectorParameterType( 3);
//		public static final PreemptionDetectorParameterType TYPE_TQ			    = new PreemptionDetectorParameterType( 4);
//		public static final PreemptionDetectorParameterType TYPE_TL_MAX		    = new PreemptionDetectorParameterType( 5);
//		public static final PreemptionDetectorParameterType TYPE_TP1_MAX	    = new PreemptionDetectorParameterType( 6);
//		public static final PreemptionDetectorParameterType TYPE_TP2_MAX	    = new PreemptionDetectorParameterType( 7);
//		public static final PreemptionDetectorParameterType TYPE_TS_MAX		    = new PreemptionDetectorParameterType( 8);
//		public static final PreemptionDetectorParameterType TYPE_PL_MAX		    = new PreemptionDetectorParameterType( 9);
//		public static final PreemptionDetectorParameterType TYPE_INS_MAX	    = new PreemptionDetectorParameterType(10);
//		public static final PreemptionDetectorParameterType TYPE_CRSFAC		    = new PreemptionDetectorParameterType(11);
//		public static final PreemptionDetectorParameterType TYPE_CP_MIN		    = new PreemptionDetectorParameterType(12);
//		public static final PreemptionDetectorParameterType TYPE_CP_MAX		    = new PreemptionDetectorParameterType(13);
//		public static final PreemptionDetectorParameterType TYPE_CP_NEED	    = new PreemptionDetectorParameterType(14);
//		public static final PreemptionDetectorParameterType TYPE_DL      	    = new PreemptionDetectorParameterType(15);
//		public static final PreemptionDetectorParameterType TYPE_DP      	    = new PreemptionDetectorParameterType(16);
//		public static final PreemptionDetectorParameterType TYPE_DM      	    = new PreemptionDetectorParameterType(17);
//		public static final PreemptionDetectorParameterType TYPE_DS      	    = new PreemptionDetectorParameterType(18);
//		public static final PreemptionDetectorParameterType TYPE_DQ      	    = new PreemptionDetectorParameterType(19);
//		public static final PreemptionDetectorParameterType TYPE_DF      	    = new PreemptionDetectorParameterType(20);
//		public static final PreemptionDetectorParameterType TYPE_OOODP   	    = new PreemptionDetectorParameterType(21);
//		public static final PreemptionDetectorParameterType TYPE_OOODM   	    = new PreemptionDetectorParameterType(22);
//		public static final PreemptionDetectorParameterType TYPE_OOODQ   	    = new PreemptionDetectorParameterType(23);
//		public static final PreemptionDetectorParameterType TYPE_TP             = new PreemptionDetectorParameterType(24);
//		public static final PreemptionDetectorParameterType TYPE_TP_MAX         = new PreemptionDetectorParameterType(25);
//		public static final PreemptionDetectorParameterType TYPE_TM_MAX         = new PreemptionDetectorParameterType(26);
//		public static final PreemptionDetectorParameterType TYPE_TG_MAX         = new PreemptionDetectorParameterType(27);
//		public static final PreemptionDetectorParameterType TYPE_TG_MIN         = new PreemptionDetectorParameterType(28);
//                                            
//		private final int parameterType;
//
//		public PreemptionDetectorParameterType(int detType) {
//			this.parameterType = detType;
//		}
//
//		public int getParameterType() {
//			return this.parameterType;
//		}
//	}
//
//	protected Node node;
//	public int BrtMoves		;	// maximum number of busses moves (signal groups)
//	public int BrtsPerMove	;	// maximum number of busses per signal group
//    public int CompMoves	;	// maximum number of compensation required moves
//	
//	protected abstract void Init();
//	public abstract ParametersIndex getParamNumber(PreemptionDetectorParameterType paramType, int index);
//	//public abstract int getParamNumber(ParametersIndex paramType);
//	//public abstract int getParamNumber(ParametersIndex paramType, int moveIndex);
//	protected abstract Move getLRTPreemptionTriangle(int lrtIndex);
//	protected abstract void forwardTimers();
//	protected abstract void startCounter(int[] ct, int lrtIndex);
//	protected abstract void resetCounter(int[] ct, int lrtIndex);
//	protected abstract void checkDetectors();
//	protected abstract void updateActiveLRT();
//	protected abstract void calcArrivalTime();
//	public abstract void InitializePreemption();
//	public abstract void updateParameters();
//	public abstract void FirstSecondActions();
//	public abstract void EverySecond();
//	public abstract void PostEverySecond();
//	public abstract void updateActrosAccessVariables();
//	public abstract void runSecond();
//	protected abstract void calcCompatible();
//	protected abstract void setPreemptionTriangle();
//	protected abstract void moveActivated(Move move);
//	protected abstract void moveDeactivated(Move move);
//	
//	public int getParameterValue(ParametersIndex param) {
//		if (!node.isRegularProgram() && !node.isInMaintenance())
//			return 0;
//		
//		if (param == null)
//		    return UNDEFINED;
//		
//		if (param.getIsInner())
//			return Var.controller.dvi35Parameters.parametersInner[param.getIndex() - 1] * param.getFactor();
//		else
//			return Var.controller.dvi35Parameters.parameters[param.getIndex() - 1] * param.getFactor();
//	}
//	
//	public int getParameterValue(int param) {
//		if (!node.isRegularProgram() && !node.isInMaintenance())
//			return 0;
//		return param;
//	}
//}