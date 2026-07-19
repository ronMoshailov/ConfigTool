//package core.preemption;
//import parameters.ParamSetJerusalem.ParametersIndex;
//import core.InterStage;
//import core.Move;
//import core.Node;
//import core.Stage;
//import core.Node.SpecialProgram;
//import core.detectors.LRTDetectorInternal;
//import core.detectors.LRTInterface;
//import core.detectors.Input;
//import core.detectors.Output;
//import core.detectors.QDetector;
//import m0.Var;
//import det.DetektorIntern;
//import sg.Zustand;
//import uhr.Uhr;
//import vt.Phase;
//import vt.ProgBase;
//import vt.Programm;
//import vt.StgEbene;
//
//abstract public class PreemptionJLRT extends PreemptionJ
//{
//	public PreemptionJLRT(Node node, int brtMoves, int brtsPerMove, int compMoves) {
//		super(node, brtMoves, brtsPerMove, compMoves);
//		
//		LRT_NUM_MOVES = brtMoves;
//		LRTS_PER_MOVES = brtsPerMove;
//		
//		tLx            = new int[brtMoves * brtsPerMove];
//		tPx            = new int[brtMoves * brtsPerMove];
//		tMx            = new int[brtMoves * brtsPerMove];
////		tGx            = new int[brtMoves * brtsPerMove];
//		tSx            = new int[brtMoves * brtsPerMove];
//		ctQj           = new int[brtMoves];
//		ATj            = new int[brtMoves];
//		PLj            = new int[brtMoves];
//
//		tLj            = new int[brtMoves];
//		tPj            = new int[brtMoves];
//		tMj            = new int[brtMoves];
//		tGj            = new int[brtMoves];
//		tSj            = new int[brtMoves];
//
//		Sj             = new boolean[brtMoves];
//		Fj             = new boolean[brtMoves];
//		FTj            = new boolean[brtMoves];
//		memLRTMoveOpen = new boolean[brtMoves];
//	}
//	
//	protected int LRT_NUM_MOVES;
//	protected int LRTS_PER_MOVES;
//	
//	public static int[]            tLx            ;
//	public static int[]            tPx            ;
//	public static int[]            tMx            ;
////	public static int[]            tGx            ;
//	public static int[]            tSx            ;
//	public static int[]            ctQj           ;
//	public static int[]            ATj            ;
//	public static int[]            PLj            ;
//	public static int[]            tLj            ;
//	public static int[]            tPj            ;
//	public static int[]            tMj            ;
//	public static int[]            tGj            ;
//	public static int[]            tSj            ;
//	public static boolean[]        Sj             ;
//	public static boolean[]        Fj             ;
//	public static boolean[]        FTj            ;
//	public static boolean[]        memLRTMoveOpen ;
//	
//	public static boolean switchProgram;
//	public Input SwitchProg;
//
//	public static int          tL;
//	public static int          tP;
//	public static int          tM;
//	public static int          tS;
//	public static int          tG;
//	public static int          TL;
//	public static int          TP;
//	public static int          ctQ;
//	public static int          AT;
//	public static int          PL;
//
//	public static int          numOfLRTMoveOpen;
//	public static boolean      LIG;
//	public static boolean      LIG1;
//	public static boolean      LIG2;
//	public static boolean      closeL;
//	public static int	       memL;
//	public static boolean      memS;
//	public static boolean      memF;
//	public static boolean      DF;
//	public static boolean      inserted;
//	
//	public static boolean      goToCata;
//	public static boolean      memCat;
//	public static boolean      cata;
//
//	public static int          ATindex;
//	public static int          compatible;
//
//	public static boolean Ghost = false;
//	
//	public abstract Move getLRTMove(int lrtIndex);
//	public abstract LRTInterface getLRTDetector(PreemptionDetectorType detType, int lrtIndex);
//	protected abstract Move getLRTPreemptionTriangle(int lrtIndex);
//	protected abstract void forwardGGTGroups();
//	protected abstract boolean isGridK( Stage stage );
//	protected abstract void calcGTcp();
//	protected abstract int getGTcpmax( Stage stage );
//	protected abstract int getGTcpmin( Stage stage );
//	public abstract boolean extensionGood( int stage); 
//	protected abstract boolean waitingTimeGood( int path );
//	protected abstract int getTime( int path, int lrtMove );
//	protected abstract int getGlobalGreenTime( int path );
//	protected abstract void calcCompK( Stage stage, Stage toStage );
//	protected abstract void nodeOffline();
//	protected abstract boolean goToCataSpecialCondition();
//	
//	protected void Init()
//	{
//	    for ( int i = 0; i < LRT_NUM_MOVES * LRTS_PER_MOVES; i++)
//	    {
//	        tLx[ i] = CT_NONE;
//	        tPx[ i] = CT_NONE;
//	        tMx[ i] = CT_NONE;
////	        tGx[ i] = CT_NONE;
//	        tSx[ i] = CT_NONE;
//	    }
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	    {
//	        tLj[ i ] = CT_NONE; 
//	        tPj[ i ] = CT_NONE; 
//	        tMj[ i ] = CT_NONE; 
//	        tGj[ i ] = CT_NONE; 
//	        tSj[ i ] = CT_NONE; 
//	        Sj[ i] = false;
//	        Fj[ i] = false;
//	        ctQj[ i] = 150 + 1;
//	        ATj[ i] = NONE;
//	        FTj[ i] = false;
//	        Fj[ i] = false;
//	        memLRTMoveOpen[i] = false;
//
//	    }
//	    memL = 0;
//	    memF = false;
//	    memS = false;
//	    LIG1 = false;
//	    LIG2 = false;
//	    LIG  = false;
//	    DF = false;
//	    inserted = false;
//	}
//	
//	protected void forwardTimers() {
//		for ( int i = 0; i < LRT_NUM_MOVES * LRTS_PER_MOVES; i++)
//	    {
//	        if ( getLRTMove( i) == null)
//	            continue;
//	        
//	        if ( tLx[ i] != CT_NONE && tLx[ i]  != getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TL_MAX, i)))
//	            tLx[ i]++;
//	        
//	        else
//	            tLx[ i] = CT_NONE;
//
//	        if ( tPx[ i] != CT_NONE && tPx[ i]  != getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP_MAX, i)))
//	            tPx[ i]++;
//	        else
//	            tPx[ i] = CT_NONE;
//
//	        if ( tMx[ i] != CT_NONE && tMx[ i]  != getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TM_MAX, i)))
//	            tMx[ i]++;
//	        else
//	            tMx[ i] = CT_NONE;
//	        
//
//	        if ( ( tSx[ i] > 0 || ( tSx[ i] == 0 && getLRTMove( i).isActive()))
//	        		&& tSx[ i]  != getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TS_MAX, i)))
//	            tSx[ i]++;
//	        else
//	            tSx[ i] = CT_NONE;
//	        
//	        if ( i < LRT_NUM_MOVES )
//	        {
//                if ( tGj[i] != CT_NONE && tGj[i] != getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TG_MAX, i ) ) ) {
//	                tGj[i]++;
//	        	} else {
//	                tGj[i] = CT_NONE;
//	        	}
//	        }
//	    }
//	}
//
//	protected void calcMemL( )
//	{
//	    memL = 0;
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	    {
//	        if ( getLRTMove( i) == null)
//	            continue;
//	        if ( ++ctQj[ i] < node.getCurrCycleSec( ) && ( compatible > getParameterValue( ParametersIndex.PARAM_GT_COMPATIBLE) || inserted))
//	            memL +=1;
//	    }
//	}
//	
//	public void resetLIG1(){
//        LIG1= false;
//        memCat = false;
//    }
//
//	protected void startCounter( int[] ct, int lrtIndex)
//	{
//
//	    if ( ct[ lrtIndex] == CT_NONE)
//	        ct[ lrtIndex] = 0;
//	    else
//	        ct[ lrtIndex + LRT_NUM_MOVES] = 0;
//	}
//
//	protected void resetCounter( int[] ct, int lrtIndex)
//	{
//
//	    if ( ct[ lrtIndex] > ct[ lrtIndex + LRT_NUM_MOVES])
//	        ct[ lrtIndex] = CT_NONE;
//	    else
//	        ct[ lrtIndex + LRT_NUM_MOVES] = CT_NONE;
//
//	}
//
//	private boolean isOOO;
//	private Move lrtMove;
//	protected void checkDetectors( )
//	{
//        LRTInterface tmpDet = null;
//        isOOO = false;
//        
//        for ( int i = 0; i < LRT_NUM_MOVES; i++)
//        {
//            if ((lrtMove = getLRTMove( i)) == null)
//                continue;
//            
//            if (((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_OOODP, i)) != null && tmpDet.isActive()) ||
//                ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_OOODM, i)) != null && tmpDet.isActive()) ||
//                ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_OOODQ, i)) != null && tmpDet.isActive())) {
//                isOOO = true;
//            }
//            
//            if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DL, i)) != null && tmpDet.isActivated()) {
//                startCounter( tLx, i);
//            }
//
//            if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DP, i)) != null && tmpDet.isActivated())
//            {
//                startCounter( tPx, i);
//                resetCounter( tLx, i);
//            }
//            if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DM, i)) != null && tmpDet.isActivated())
//            {
//                startCounter( tMx, i); 
//                if ( tGj[i] == CT_NONE ) {
//                    tGj[i] = 0;
//                }
//            }
//
//            if (((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DS, i)) != null && tmpDet.isActivated()) && !Sj[ i])
//            {
//                memS = true;
//                resetCounter( tPx, i);
//                resetCounter( tMx, i);
//                if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DP, i)) != null && tmpDet.getOutOfOrderInput().isActive())
//                    resetCounter( tLx, i);
//                
//                if (getLRTMove( i).isActive() && getLRTMove( i).GT() > 0)
//                    startCounter( tSx, i);
//                else
//                    Sj[ i] = true;
//            }
//            if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DQ, i)) != null && tmpDet.isActivated())
//            {
//                ctQj[ i] = CT_NONE;
//                resetCounter( tSx, i);
//                tGj[i] = CT_NONE;
//            }
//
//            if (((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DF, i)) != null && tmpDet.isActivated()) && ! memF && ! memS )
//            {
//                memF = true;
//                // is LRT move red
//                if ( !getLRTMove( i).isActive() || getLRTMove(i).getZustSek() < 1)
//                    Fj[ i] = true;
//                else if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_DS, i)) != null && !tmpDet.isActive())
//                    startCounter( tSx, i);
//            }
//        }
//	}
//
//	protected void updateActiveLRT( )
//	{
//	    DF = false;
//	    ctQ = NONE;
//	    tL = CT_NONE;
//	    tP = CT_NONE;
//	    tM = CT_NONE;
//	    tS = CT_NONE;
//	    tG = CT_NONE;
//	    TL = NONE;
//	    TP = NONE;
//
//	    for ( int i = 0; i < LRT_NUM_MOVES * LRTS_PER_MOVES; i++)
//	    {
//	        if ( tLx[ i] > tL)
//	        {
//	            tL = tLx[ i];
//	            TL = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TL, i));
//	        }
//	        if ( tPx[ i] > tP)
//	        {
//	            tP = tPx[ i];
//	            TP = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP, i));
//	        }
//	        if ( tMx[ i] > tM)
//	        {
//	            tM = tMx[ i];
//	        }
//	        if ( tSx[ i] > tS)
//	        {
//	            tS = tSx[ i];
//	        }/*
//	        if ( tGx[ i] > tG)
//	        {
//	            tG = tGx[ i];
//	        }*/
//	    }
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++ )
//	    {
//	        tLj[ i ] = Math.max( tLx[ i ], tLx[ i + LRT_NUM_MOVES ] );
//	        tPj[ i ] = Math.max( tPx[ i ], tPx[ i + LRT_NUM_MOVES ] );
//	        tMj[ i ] = Math.max( tMx[ i ], tMx[ i + LRT_NUM_MOVES ] );
////	        tGj[ i ] = Math.max( tGx[ i ], tGx[ i + LRT_NUM_MOVES ] );
//	        tSj[ i ] = Math.max( tSx[ i ], tSx[ i + LRT_NUM_MOVES ] );
//	    }
//	}
//	
//	protected int minFunc(int first, int second)
//	{
//		return Math.min(first, second);
//	}
//	
//	protected int maxFunc(int first, int second)
//	{
//		return Math.max(first, second);
//	}
//	
//	protected boolean isCompatible(Stage stage)
//	{ 
//		return stage.isCompatible;
//	}
//	
//	protected boolean isInserted(Stage stage)
//	{
//		return stage.isInserted;
//	}
//
//	protected int maxArr( int[] arr, int arrSize, int startVal, int indexStep)
//	{
//	    int maxVal = CT_NONE;
//	    for ( int i = startVal; i < arrSize; i += indexStep)
//	        maxVal = ( maxVal > arr[ i] ? maxVal : arr[ i]);
//	    return maxVal;
//	}
//
//	protected int minArr( int[] arr, int arrSize, int startVal, int indexStep)
//	{
//	    int minVal = NONE;
//	    for ( int i = startVal; i < arrSize; i += indexStep)
//	        minVal = ( minVal < arr[ i] ? minVal : arr[ i]);
//	    return minVal;
//	}
//	
//	public Stage switchStage(Stage source, Stage target)
//	{
//		calcCompK(source, target);
//		return source.startNextPhase(target);
//	}
//	
//	public Stage switchStage(Stage source, InterStage interstage)
//	{
//		calcCompK(source, (Stage)interstage.getZielPhase());
//		return source.startNextPhase(interstage);
//	}
//
//	protected void calcArrivalTime( )
//	{
//	    int tPj_Temp, tLj_Temp, ATj_Temp;
//
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	    {
//	        ATj[ i] = NONE;
//	        tPj_Temp = maxArr( tPx, LRT_NUM_MOVES * LRTS_PER_MOVES, i, LRT_NUM_MOVES);
//	        tLj_Temp = maxArr( tLx, LRT_NUM_MOVES * LRTS_PER_MOVES, i, LRT_NUM_MOVES);
//	        if ( tPj_Temp != CT_NONE)
//	            ATj[ i] = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP, i)) - tPj_Temp;
//	        else if ( tLj_Temp != CT_NONE)
//	        {
//	            ATj_Temp = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TL, i)) - tLj_Temp;
//	            if ( !getLRTDetector(PreemptionDetectorType.TYPE_OOODP, i).isActive()
//	                    && getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP, i)) + 1 > ATj_Temp)
//	                ATj[ i] = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP, i)) + 1;
//	            else
//	                ATj[ i] = ATj_Temp;
//	        }
//	        if ( ATj[ i] < 0)
//	            ATj[ i] = 0;
//	        if ( Sj[ i] || Fj[ i])
//	            ATj[ i] = 0;
//		    
//		    Move move = getLRTMove(i);
//		    Move triangle = getLRTPreemptionTriangle(i);
//		    if (move != null && triangle != null) {
//		    	/*
//		    	if (ATj[i] != NONE) {
//		    		if (!move.isActive() && triangle.getZustand() == Zustand.AUS) {
//		    			triangle.setSg(Zustand.GELBBLINKEN, node.getProgSek());
//		    		}
//		    	} else {
//		    		if (move.getZustand() == Zustand.GELBBLINKEN) {
//		    			triangle.setSg(Zustand.AUS, node.getProgSek());
//		    		}
//		    	}
//		    	*/
//		    	if (ATj[i] != NONE && move.RT() > 0) {
//		    		if (!move.isActive() && triangle.getZustand() == Zustand.AUS) {
//		    			triangle.setSg(Zustand.GELBBLINKEN, node.getProgSek());
//		    		}
//		    	} else {
//		    		if ((ATj[i] == NONE || move.RT() == 0) && triangle.getZustand() != Zustand.EIN) {
//		    			triangle.setSg(Zustand.AUS, node.getProgSek());
//		    		}
//		    	}
//		    }
//	    }
//
//	    AT = minArr( ATj, LRT_NUM_MOVES, 0, 1);
//	    // find AT index
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	        if ( AT == ATj[ i])
//	            ATindex = i;
//	    
//	}
//
//	protected void lrtStageOpen( )
//	{
//	    if (node.getCurrStage().isInserted)
//	        inserted = true;
//	}
//
//	protected void lrtMoveOpen( int moveIndex)
//	{
//	    Fj[ moveIndex] = false;
//	    Sj[ moveIndex] = false;
//	    startCounter( tSx, moveIndex);
//
//	}
//
//	protected void mainStageOpen( )
//	{
//	    inserted = false;
//	    compatible = 0;
//	}
//
//	protected void lrtStageClosed(Stage stage)
//	{
//	    memF = false;
//	    memS = false;
//	}
//
//	protected void calcFT( )
//	{
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	    {
//	        if (getLRTMove( i) == null)
//	            continue;
//	        FTj[ i] = false;
//	        if ( ctQj[ i] + ATj[ i] + getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TQ, i)) < getParameterValue(ParametersIndex.PARAM_CTQMIN))
//	            FTj[ i] = true;
//	    }
//	}
//
//	protected void calcGhost( )
//	{
//	    Ghost = false;
//	    for ( int i = 0; i < LRT_NUM_MOVES ; i++)
//	    {
//	        if ( getLRTMove( i) == null)
//	            continue;
//
//	        Ghost |= tGj[i] > getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TG_MIN, i)) && !FTj[i];
//	    }
//	}
//
//	//(Fjx=false and Sjx=false and tPjx<0 and tLjx<0 and tGgx>tMjmax)
//	protected void calcPriorityLevel( )
//	{
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	    {
//	        PLj[ i] = 2;
//	        if ( ATj[ i] == NONE || FTj[ i])
//	            PLj[ i] = 0;
//	        else if (( memL > 2 && getParameterValue(ParametersIndex.PARAM_PEAK) == 1)) 
//	            PLj[ i] = 1;
//	    }
//	    PL = maxArr( PLj, LRT_NUM_MOVES, 0, 1);
//	}
//
//	protected void calcLIG( )
//	{
//	    boolean OOODM = false;
//	    boolean OOODQ = false;
//	    boolean tsMaxReached = false;
//	    boolean tMMaxReached = false;
//	    boolean tGpositive = false;
//
//	    for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	    {
//	        if ( getLRTMove( i) == null)
//	            continue;
//	        OOODQ |= getLRTDetector( PreemptionDetectorType.TYPE_OOODQ, i) != null && getLRTDetector( PreemptionDetectorType.TYPE_OOODQ, i).isActive();
//	        OOODM |= getLRTDetector( PreemptionDetectorType.TYPE_OOODM, i) != null && getLRTDetector( PreemptionDetectorType.TYPE_OOODM, i).isActive();
//	        tGpositive |= (  tGj[ i] > 0 );
//	    }
//	    for ( int i = 0; i < LRT_NUM_MOVES * LRTS_PER_MOVES; i++)
//	    {
//	        tsMaxReached |= (  tSx[ i] > 0 ) && ( tSx[ i] == getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TS_MAX, i)));
//	        tMMaxReached |= (  tMx[ i] > 0 ) && ( tMx[ i] == getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TM_MAX, i)));
//	    }
//	    
//	    LIG2 = tGpositive; 
//	    if ( LIG1
//	        || OOODQ
//	        || OOODM
//	        || tsMaxReached
//	        || tMMaxReached
//	        || memCat
//            || !isSITOK()
//	        //|| (node.CycleCount == 1 && node.MainPhase[0].getIsCycleStart())
//	        )
//	            LIG1 = true;
//	            
//	    LIG = LIG1 || LIG2;
//	}
//	
//	protected boolean checkLIG1(){
//	    boolean OOODM = false;
//        boolean OOODQ = false;
//        boolean tsMaxReached = false;
//        boolean tMMaxReached = false;
//        boolean tGpositive = false;
//
//        for ( int i = 0; i < LRT_NUM_MOVES; i++)
//        {
//            if ( getLRTMove( i) == null)
//                continue;
//            OOODQ |= getLRTDetector( PreemptionDetectorType.TYPE_OOODQ, i) != null && getLRTDetector( PreemptionDetectorType.TYPE_OOODQ, i).isActive();
//            OOODM |= getLRTDetector( PreemptionDetectorType.TYPE_OOODM, i) != null && getLRTDetector( PreemptionDetectorType.TYPE_OOODM, i).isActive();
//            tGpositive |= (  tGj[ i] > 0 );
//        }
//        for ( int i = 0; i < LRT_NUM_MOVES * LRTS_PER_MOVES; i++)
//        {
//            tsMaxReached |= (  tSx[ i] > 0 ) && ( tSx[ i] == getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TS_MAX, i)));
//            tMMaxReached |= (  tMx[ i] > 0 ) && ( tMx[ i] == getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TM_MAX, i)));
//        }
//	    
//	    if(OOODQ || OOODM || tsMaxReached || tMMaxReached || memCat || !isSITOK())
//	        return true;
//        return false;
//	}
//
//	protected void calcCloseL( )
//	{
//	    closeL = false;
//	    if ( tM == CT_NONE && tS == CT_NONE)
//	        closeL = true;
//	}
//	
//	private void checkSIT() {
////		for ( int i = 0; i < LRT_NUM_MOVES; i++) {
////			Move lrtMove = getLRTMove(i);
////			if (i == 0) {
////				node.SITOK            .setSimulation(Var.controller.SITinterface.getSitOk(i) ? 1 : -1);
////			}
////			lrtMove.dl                .setSimulation(Var.controller.SITinterface.getDL   (i) ? 1 : -1);
////			lrtMove.dp                .setSimulation(Var.controller.SITinterface.getDP   (i) ? 1 : -1);
////			lrtMove.dm                .setSimulation(Var.controller.SITinterface.getDM   (i) ? 1 : -1);
////			lrtMove.ds                .setSimulation(Var.controller.SITinterface.getDS   (i) ? 1 : -1);
////			lrtMove.dq                .setSimulation(Var.controller.SITinterface.getDQ   (i) ? 1 : -1);
////			lrtMove.df                .setSimulation(Var.controller.SITinterface.getDF   (i) ? 1 : -1);
////			lrtMove.dp.outOfOrderInput.setSimulation(Var.controller.SITinterface.getOOODP(i) ? 1 : -1);
////			lrtMove.dm.outOfOrderInput.setSimulation(Var.controller.SITinterface.getOOODM(i) ? 1 : -1);
////			lrtMove.dq.outOfOrderInput.setSimulation(Var.controller.SITinterface.getOOODQ(i) ? 1 : -1);
//////			permissive;
////		}
//	}
//	
//	Stage prevStage = null;
//	public void runSecond() {
//		if (node.getProgZeit() % Var.ONE_SEC == 0 || node.firstSec)
//		{
//			node.firstSec = false;
//			
//		    prevStage = node.getPrevStage();
//		    forwardTimers( );
//		    checkSIT( );
//		    checkDetectors( );
//	
//		    if (!node.isRegularProgram() && !node.isInMaintenance()) {
//		    	nodeOffline();
//		    } else {
//		    	if (node.getCurrStage().isMainStage && node.getCurrStageDuration() == 1)
//		    		mainStageOpen( );
//
//		    	if (prevStage != null)
//		    		if ( prevStage.isLRT && node.getCurrStage().getPhasenSek() == 1)
//		    			lrtStageClosed( node.getPrevStage( ));
//
//		    	if ( node.getCurrStage().isLRT && node.getCurrStageDuration( ) == 1)
//		    		lrtStageOpen( );
//
//		    	for ( int i = 0; i < LRT_NUM_MOVES; i++)
//		    		if ( getLRTMove( i).isActive() && getLRTMove(i).getZustSek() >= 1 && ( Sj[ i] || Fj[ i]))
//		    			lrtMoveOpen(i);
//
//		    	//reset of LIG
//		    	numOfLRTMoveOpen = 0;               
//		    	for ( int i = 0; i < LRT_NUM_MOVES; i++)
//		    		if ( getLRTMove( i).isActive())
//		    			numOfLRTMoveOpen++;
//
//		    	if (numOfLRTMoveOpen == 0)
//		    		for ( int i = 0; i < LRT_NUM_MOVES; i++)
//		    			if ( memLRTMoveOpen[i] && !getLRTMove( i).isActive()) 
//		    				LIG1 = false;
//
//		    	for ( int i = 0; i < LRT_NUM_MOVES; i++)
//		    		memLRTMoveOpen[i] = getLRTMove( i).isActive();
//
//		    	// update DF
//		    	DF = false;
//		    	for ( int i = 0; i < LRT_NUM_MOVES; i++)
//		    		DF = DF || Fj[ i];
//
//		    	forwardGGTGroups();
//
//		    	calcCompatible( );
//		    	updateActiveLRT( );
//		    	calcArrivalTime( );
//		    	calcMemL( );
//		    	ctQ = minArr( ctQj, LRT_NUM_MOVES, 0, 1);
//		    	calcFT( );
//		    	calcLIG( );
//		    	calcPriorityLevel( );
//		    	calcCloseL( );
//		    	calcGhost( );
//		    	setPreemptionTriangle( );
//
//		    	calcGTcp();
//
//		    	EverySecond();
//		    }
//		}
//		goToCata = checkGoToCata() || goToCataSpecialCondition();
//	    cata = node.getAktProg().getNummer() == SpecialProgram.Catastrophe.getProgramNumber();
//	    switchToCata();
//	    
//	    switchProgram = node.getAktProg().getNummer() != node.getProgAnf().getProg().getNummer() || SwitchProg!=null && SwitchProg.IsActive();
//	}
//	
//	private ProgBase tmpProg;
//	private StgEbene cataEbene = null;
//	protected void switchToCata() {
//		if (!goToCata) {
//			// no need to go to catastrophe program
//			if (cata || node.getProgAnf().getProg().getNummer() ==  SpecialProgram.Catastrophe.getProgramNumber()) {
//				resetCatastropheDemand();
//			} else if (!cata) {
//				cataEbene = null;
//			}
//		} else {
//            // go to catasprophe
//            if ((node.getProgAnf().getProg().getNummer() != SpecialProgram.Catastrophe.getProgramNumber() ||
//                 (node.clockProgram != SpecialProgram.Catastrophe.getProgramNumber()) && (node.getAktStgEbene() == StgEbene.STG_NUM_UHR)) &&
//                node.getAktStgEbene() != StgEbene.STG_NUM_MANUELL &&
//                node.getAktStgEbene() != StgEbene.STG_NUM_MANUELL_EXTERN &&
//                node.getAktStgEbene() != StgEbene.STG_NUM_SYSTEM) {
//                
//                if (node.getAktStgEbene() == StgEbene.STG_NUM_UHR && 
//                    node.getProgAnf().getStgEbene() == StgEbene.STG_ZENTRALE) {
//                    if (cataEbene == StgEbene.STG_UHR) {
////                      Uhr.update();
//                        node.isClockReset = true;
//                        node.clockProgram = -1;
//                    }
////                  node.setProgWunsch(Programm.findByNum(SpecialProgram.Catastrophe.getProgramNumber(), node.getNummer()), (cataEbene = StgEbene.STG_ZENTRALE));
//                    node.centralProgram = SpecialProgram.Catastrophe.getProgramNumber();
//                    cataEbene = StgEbene.STG_ZENTRALE;
//                } else if (node.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE && 
//                           !Var.controller.central.isPrcc()) {
//                    if (cataEbene == StgEbene.STG_ZENTRALE &&
//                        !Var.controller.central.isOperateInCentralFlash() &&
//                        !Var.controller.central.isOperateInCentralDark()) {
////                      node.setProgWunsch(null, StgEbene.STG_ZENTRALE);
//                        node.centralProgram = -1;
//                    }
//                    if (node.getProgWunsch(node, StgEbene.STG_UHR).getNummer() != SpecialProgram.Catastrophe.getProgramNumber()) {
//                        node.clockProgram = SpecialProgram.Catastrophe.getProgramNumber();
//                        cataEbene = StgEbene.STG_UHR;
////                      node.setProgWunsch(Programm.findByNum(SpecialProgram.Catastrophe.getProgramNumber(), node.getNummer()), (cataEbene = StgEbene.STG_UHR));
//                    }
//                } else {
//                    if (node.getStgEbene() != StgEbene.STG_ZENTRALE ||
//                        (!Var.controller.central.isOperateInCentralFlash() &&
//                         !Var.controller.central.isOperateInCentralDark())) {
//                        if (node.getStgEbene() == StgEbene.STG_ZENTRALE) {
//                            node.centralProgram = SpecialProgram.Catastrophe.getProgramNumber();
//                            cataEbene = StgEbene.STG_ZENTRALE;
//                        } else {
//                            node.clockProgram = SpecialProgram.Catastrophe.getProgramNumber();
////                          node.setProgWunsch(Programm.findByNum(SpecialProgram.Catastrophe.getProgramNumber(), node.getNummer()), (cataEbene = node.getStgEbene()));
//                        }
//                    }
//                }
//            }
//        }
//	}
//	
//	protected void resetCatastropheDemand() {
//		Uhr.update();
////		node.setProgWunsch(null, StgEbene.STG_ZENTRALE);
////		cataEbene = null;
//		if (node.centralProgram == SpecialProgram.Catastrophe.getProgramNumber()) {
//			node.centralProgram = -1;
//		}
//		if (node.clockProgram == SpecialProgram.Catastrophe.getProgramNumber()) {
//            node.clockProgram = -1;
//            node.isClockReset = true;
//        }
//	}
//	
//	protected boolean checkGoToCata() {
//        if (!isSITOK())
//			return true;
//		
//		LRTInterface tmpDet;
//		
//		for ( int i = 0; i < LRT_NUM_MOVES; i++)
//	    {
//			if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_OOODM, i)) != null && tmpDet.isActive()) {
//				return true;
//			}
//
//			if ((tmpDet = getLRTDetector( PreemptionDetectorType.TYPE_OOODQ, i)) != null && tmpDet.isActive()) {
//				return true;
//			}
//	    }
//		
//		return false;
//	}
//    
//    public boolean isSITOK() {
//        if (Var.controller.isUseSIT()) {
//            return Var.controller.sit.sitOK.isActive();
//        } else {
//            return node.SITOK.IsActive();
//        }
//    }
//
//	protected int stb( )
//	{
//	    return node.getCurrCycleSec( );
//	}
//    
//    protected boolean isElementDeactivated(Input input) {
//        return input.IsDeactivated();
//    }
//    
//    protected boolean isElementActivated(Output output) {
//        return output.IsActivated();
//    }
//    
//    protected boolean isElementDeactivated(Output output) {
//        return output.IsDeactivated();
//    }
//	
//	protected boolean isElementActivated(Move move){
//		move.UpdateStateCounters();;
//		return move.IsActivated();
//		//return  move.currentState == Zustand.ROT && move.stateTime < Var.ONE_SEC;
//	}
//	
//	protected boolean isElementDeactivated(Move move){
//		move.UpdateStateCounters();;
//		return move.IsDeactivated();
//		//return  move.currentState == Zustand.ROT && move.stateTime < Var.ONE_SEC;
//	}
//	
//	protected int getElementElapsedTime(QDetector queue) {
//		if (queue == null)
//			return 0;
//		return queue.GT();
//	}
//	protected int getElementElapsedRedTime(QDetector queue) {
//		if (queue == null)
//			return 0;
//		return queue.RT();
//	}
//	public void setElementActive(Output output){
//        if(output.IsActive())
//            return;
//		output.setAusgang();
//	}
//		
//	public void setElementNotActive(Output output){
//        if(!output.IsActive())
//            return;
//		output.resetAusgang();
//	}
//	
//	protected boolean isCompatibleStage( Stage stage ) {
//	    return stage != null && stage.isCompatible;
//	}
//	
//	protected boolean isElementActive(Move move) {
//	    return move.isActive();
//	}
//	
//	public boolean isElementActive(Input input){
//		return input.IsActive();
//	}
//    
//    protected boolean isElementActivated(Input input){
//        return input.IsActivated();
//    }
//    
//    protected boolean isElementActivated(LRTDetectorInternal input){
//        return input.isActivated();
//    }
//	
//	
//	protected boolean isElementNotActive(Input input){
//		return !input.IsActive();
//	}
//	
//	protected boolean isElementActive(Stage stage) {
//	    if (stage == null)
//	        return false;
//	    
//	    Phase activeStage = Phase.getAktivePhase(stage.getTeilKnoten()); 
//	    return activeStage != null && activeStage.getNummer() == stage.getNummer() && ((Node)stage.getTeilKnoten()).getLenPhue() != Stage.MAX_INTERSTAGE_DURATION; 
//	}
//	
//	protected boolean isElementDeactivated(Stage stage) {
//	    if (stage == null)
//	        return false;
//	    
//	    Node node = (Node)stage.getTeilKnoten();
//	    Phase phase = Phase.getAktivePhase(node);
//	    return node.prevStage != null && node.prevStage.getNummer() == stage.getNummer() && phase != null && phase.getPhasenSek() <= 1;
//	}
//	
//	protected int getElementElapsedTime(Stage stage) {
//		if (stage == null)
//			return 0;
//		
//		return stage.GT();
//	}
//
//	protected int getElementElapsedTime(Move move) {
//		if (move == null)
//			return 0;
//		
//		return (move.getState() == Zustand.GRUEN) ? (move.getElapsedTime()) / Var.ONE_SEC : 0; 
//	}
//}