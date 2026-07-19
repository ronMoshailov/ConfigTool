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
//import parameters.ParamSetJerusalem.ParametersIndex;
//import core.Move;
//import core.Node;
//import core.Stage;
//import core.detectors.LRTDetector;
//import m0.Var;
//import sg.Sg;
//
//abstract public class PreemptionJBRT extends PreemptionJ
//{
//	public PreemptionJBRT(Node node, int brtMoves, int brtsPerMove, int compMoves) {
//		super(node, brtMoves, brtsPerMove, compMoves);
//		
//		moveMap				= new int[BrtMoves ]            ;
//		compMap				= new int[CompMoves]            ;
//		detectors			= new LRTDetector[BrtMoves][10] ;
//		                                                       
//		tLx					= new int[BrtMoves][BrtsPerMove];
//		tP1x				= new int[BrtMoves][BrtsPerMove];
//		tP2x				= new int[BrtMoves][BrtsPerMove];
//		tSx					= new int[BrtMoves][BrtsPerMove];
//		aTx					= new int[BrtMoves][BrtsPerMove];
//		                                                       
//		lastDlActive		= new int[BrtMoves]             ;
//		lastDp1Active		= new int[BrtMoves]             ;
//		lastDp2Active		= new int[BrtMoves]             ;
//		lastDsActive		= new int[BrtMoves]             ;
//		lastDqActive		= new int[BrtMoves]             ;
//		memBRT				= new int[BrtMoves]             ;
//		priorityLevel		= new int[BrtMoves]             ;
//		memInsTempAT		= new int[BrtMoves]             ;
//		memInsTempCtr		= new int[BrtMoves]             ;
//		memInsTempPL		= new int[BrtMoves]             ;
//		memIns				= new int[BrtMoves]             ;
//		                                                       
//		comp				= new int[CompMoves]            ;
//		cpMin				= new int[CompMoves]            ;
//		memExDet			= new boolean[CompMoves]        ;
//		                                                       
//		memTsOnBrtMoveOpen	= new boolean[BrtMoves]         ;
//		memInsTempDQ		= new boolean[BrtMoves]         ;
//	}
//	
//	// In Haifa preemption:
//	//Starting the timers when there is RISING of a demand on the specific input, set an departure
//    //  PT_xx_1    PT_xx_2    PT_xx_3      PT_xx_4
//    //   _ _ _      _ _ _      _ _ _   ||   _ _ _   
//    //  |     |    |     |    |     |  ||  |     |   
//    //  |_ _ _|    |_ _ _|    |_ _ _|  ||  |_ _ _|   
//    //     1          2          3     ||     4
//	//
//	
//	public static final int UNDEFINED	=   -1;
//	public static final int ResetDec	= -999;
//    
//	private Node node;
//	
//	public int BrtMoves		;	// maximum number of busses moves (signal groups)
//	public int BrtsPerMove	;	// maximum number of busses per signal group
//    public int CompMoves	;	// maximum number of compensation required moves
//
//    protected static final int ValidPrDL	= 3 * Var.ONE_SEC;
//    protected static final int ValidPrDP1	= 3 * Var.ONE_SEC;
//    protected static final int ValidPrDP2	= 3 * Var.ONE_SEC;
//    protected static final int ValidPrDS	= 3 * Var.ONE_SEC;
//    protected static final int ValidPrDQ	= 3 * Var.ONE_SEC;
//    protected static final int MaxBRT		= 8; // maximum BRT's per cycle - further BRT's will not get priority level
//    
//    protected int[]           moveMap			; // map between index and public transport sg, direction priority parameter
//    protected int[]           compMap			; // map between index and compensation sg
//    protected LRTDetector[][] detectors			; //
//
//    protected int[][]	      tLx				;
//    protected int[][]	      tP1x				;
//    protected int[][]	      tP2x				;
//    protected int[][]	      tSx				;
//    protected int[][]	      aTx				;
//                              
//    protected int[]           lastDlActive		;
//    protected int[]           lastDp1Active		;
//    protected int[]           lastDp2Active		;
//    protected int[]           lastDsActive		;
//    protected int[]           lastDqActive		;
//    protected int[]           memBRT			;
//    protected int[]           priorityLevel		;
//    protected int[]           memInsTempAT		;
//    protected int[]           memInsTempCtr		;
//    protected int[]           memInsTempPL		;
//    protected int[]           memIns			;
//                              
//    protected int[]           comp				;
//    protected int[]           cpMin				;
//    protected boolean[]       memExDet			;
//                              
//    protected boolean[]       memTsOnBrtMoveOpen;
//    protected boolean[]       memInsTempDQ		;
//    protected int             arrivalTime			= ResetDec; // Arrival Time of the nearest bus (after taking into count direction priority)
//    
//    public int calcNumOfBRTMoves() {
//        return BrtMoves;
//    }
//
//    public int calcNumOfCompMoves() {
//        return CompMoves;
//    }
//
//    public Move getBRTMove( int index ) {
//    	if (index >= BrtMoves)
//    		return null;
//
//    	return (Move)Sg.getSgByNum(moveMap[index]);
//    }
//    
//    public Move getCompMove( int index ) {
//    	if (index >= CompMoves)
//    		return null;
//    	
//    	return (Move)Sg.getSgByNum(compMap[index]);
//    }
//    
//    public int getBRTIndex( Move move ) {
//    	for (int i = 0; i < BrtMoves; i++)
//    		if (moveMap[i] == move.getNr())
//    			return i;
//    	
//    	return UNDEFINED;
//    }
//    
//    public int getCompIndex( Move move ) {
//    	for (int i = 0; i < compMap.length; i++) {
//    		if (compMap[i] == move.getNr()) {
//    			return i;
//    		}
//    	}
//    	return UNDEFINED;
//    }
//	
//    public LRTDetector getBRTDetector( PreemptionDetectorType detType, int brtIndex) {
//    	if (brtIndex < 0 || brtIndex >= BrtMoves) {
//    		return null;
//    	}
//    	
//    	return detectors[brtIndex][detType.getDetectorType()];
//    }
//    
//    public abstract int getTime( int timeConst, Sg ptMove  );
//
//	public void init() {
//		for (int i = 0; i < BrtMoves; i++ )
//	    {
//	        lastDlActive[i]  = 1000 * Var.ONE_SEC;
//	        lastDp1Active[i] = 1000 * Var.ONE_SEC;
//	        lastDp2Active[i] = 1000 * Var.ONE_SEC;
//	        lastDsActive[i]  = 1000 * Var.ONE_SEC;
//	        lastDqActive[i]  = 1000 * Var.ONE_SEC;
//	        memBRT[i]        = 0;
//	        memIns[i]        = 0;
//	        priorityLevel[i] = 0;
//	        memInsTempAT[i]  = ResetDec;
//	        memInsTempCtr[i] = -1;
//	        memInsTempDQ[i]  = false;
//	        memInsTempPL[i]  = 0;
//	        memTsOnBrtMoveOpen[i] = false;
//	        
//	        for ( int j = 0; j < BrtsPerMove; j++ )
//	        {
//	            tLx[i][j]  = ResetDec;
//	            tP1x[i][j] = ResetDec;
//	            tP2x[i][j] = ResetDec;
//	            tSx[i][j]  = ResetDec;
//	            aTx[i][j]  = ResetDec;
//	        }
//	        arrivalTime  = ResetDec;
//	    }
//	    for ( int i = 0; i < CompMoves; i++ )
//	    {
//	        comp[i] = 0;
//	        cpMin[i] = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_CP_MIN, i));
//	        memExDet[i] = false;
//	    }
//	}
//
//	protected void startCounter( PreemptionDetectorType detType, int brtIndex )
//	{
//	    boolean found = false;
//
//        if (detType.equals(PreemptionDetectorType.TYPE_DL)) {
//            for ( int j = 0; j < BrtsPerMove && !found; j++ )
//                if ( tLx[brtIndex][j] == ResetDec && tP1x[brtIndex][j] == ResetDec && tP2x[brtIndex][j] == ResetDec && tSx[brtIndex][j] == ResetDec )
//                {
//                	tLx[brtIndex][j] = 0;
//                    found = true;
//                }
//        } else if (detType.equals(PreemptionDetectorType.TYPE_DP1)) {
//            for ( int j = 0; j < BrtsPerMove && !found; j++ )
//            {
//                if ( tLx[brtIndex][j] != ResetDec && tP1x[brtIndex][j] == ResetDec && tP2x[brtIndex][j] == ResetDec && tSx[brtIndex][j] == ResetDec )
//                {
//                	tP1x[brtIndex][j] = 0;
//                    found = true;
//                }
//            }
//            for ( int j = 0; j < BrtsPerMove && !found; j++ )
//            {
//                if ( tLx[brtIndex][j] == ResetDec && tP1x[brtIndex][j] == ResetDec && tP2x[brtIndex][j] == ResetDec && tSx[brtIndex][j] == ResetDec )
//                {
//                	tP1x[brtIndex][j] = 0;
//                    found = true;
//                }
//            }
//        } else if (detType.equals(PreemptionDetectorType.TYPE_DP2)) {
//            for ( int j = 0; j < BrtsPerMove && ! found; j++ )
//            {
//                if ( tP1x[brtIndex][j] != ResetDec && tP2x[brtIndex][j] == ResetDec && tSx[brtIndex][j] == ResetDec )
//                {
//                	tP2x[brtIndex][j] = 0;
//                    found = true;
//                }
//            }
//            for ( int j = 0; j < BrtsPerMove && !found; j++ )
//            {
//                if ( tP1x[brtIndex][j] == ResetDec && tP2x[brtIndex][j] == ResetDec && tSx[brtIndex][j] == ResetDec )
//                {
//                	tP2x[brtIndex][j] = 0;
//                    found = true;
//                }
//            }
//        } else if (detType.equals(PreemptionDetectorType.TYPE_DS)) {
//            for ( int j = 0; j < BrtsPerMove && !found; j++ )
//            {
//                if ( tP2x[brtIndex][j] != ResetDec && tSx[brtIndex][j] == ResetDec )
//                {
//                	tSx[brtIndex][j] = 0;
//                    found = true;
//                }
//            }
//            for ( int j = 0; j < BrtsPerMove && !found; j++ )
//            {
//                if ( tP2x[brtIndex][j] == ResetDec && tSx[brtIndex][j] == ResetDec )
//                {
//                	tSx[brtIndex][j] = 0;
//                    found = true;
//                }
//            }
//        }
//	}
//
//	protected void removeBrt( int brtIndex )
//	{
//	    shiftBrtArrayUp( brtIndex, 0);
//	}
//
//	protected void shiftBrtArrayUp(int brtIndex, int fromIndex)
//	{
//	    for ( int j = fromIndex; j < BrtsPerMove - 1; j++ )
//	    {
//	    	tLx [brtIndex][j] = tLx [brtIndex][j + 1];
//	    	tP1x[brtIndex][j] = tP1x[brtIndex][j + 1];
//	    	tP2x[brtIndex][j] = tP2x[brtIndex][j + 1];
//	    	tSx [brtIndex][j] = tSx [brtIndex][j + 1];
//	    }
//	    tLx [brtIndex][BrtsPerMove - 1] = ResetDec;
//	    tP1x[brtIndex][BrtsPerMove - 1] = ResetDec;
//	    tP2x[brtIndex][BrtsPerMove - 1] = ResetDec;
//	    tSx [brtIndex][BrtsPerMove - 1] = ResetDec;
//	}
//
//	//protected abstract ParametersIndex getParamNumber(PreemptionDetectorParameterType paramType, int index);
//	
//	public int getParameterValue(ParametersIndex index) {
//		return Var.controller.dvi35Parameters.parameters[index.getIndex() - 1] * index.getFactor();
//	}
//	
//	public abstract boolean isInserted(Stage stage);
//	public abstract boolean isCompatible(Stage stage);
//		
//	protected void checkDetectors()
//	{
//	    for ( int i = 0; i < BrtMoves; i++)
//	    {
//	        if (getBRTMove( i) == null)
//	            continue;
//	        
//	        int tStQ = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TS, i)) + getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TQ, i));
//	        boolean forceDq = getBRTDetector( PreemptionDetectorType.TYPE_OOODQ, i ).isActive() && ( memTsOnBrtMoveOpen[i] && getBRTMove( i ).getStateTime() >= tStQ || !memTsOnBrtMoveOpen[i] && tSx[i][0] >= tStQ );
//	        
//	        if (getBRTDetector( PreemptionDetectorType.TYPE_DQ, i ).isActivated() || forceDq )
//	        {
//	            if (lastDqActive[i] > ValidPrDQ)
//	                removeBrt( i );
//	            lastDqActive[i] = 0;
//	        }
//
//	        if ( getBRTDetector( PreemptionDetectorType.TYPE_DQ, i ).IsActivated())
//	        {
//	            boolean insertedStage = !node.isInInterStage() && isInserted(node.getCurrStage()) 
//	                    || node.isInInterStage() && isInserted(node.getPrevStage());
//
//	            boolean compatibleStage = !node.isInInterStage() && isCompatible(node.getCurrStage()) 
//	                    || node.isInInterStage() && isCompatible(node.getPrevStage());
//
//	            if ( insertedStage || compatibleStage )
//	                memBRT[i] += node.getZyklDauer();
//	        }
//	        if ( getBRTDetector( PreemptionDetectorType.TYPE_DL, i).IsActivated())
//	        {
//	            if (lastDlActive[i] > ValidPrDL)
//	                startCounter( PreemptionDetectorType.TYPE_DL, i);
//	            lastDlActive[i] = 0;
//	        }
//	        if (getBRTDetector( PreemptionDetectorType.TYPE_DP1, i).IsActivated())
//	        {
//	            if (lastDp1Active[i] > ValidPrDP1)
//	                startCounter( PreemptionDetectorType.TYPE_DP1, i);
//	            lastDp1Active[i] = 0;
//	        }
//	        if (getBRTDetector( PreemptionDetectorType.TYPE_DP2, i).IsActivated())
//	        {
//	            if (lastDp2Active[i] > ValidPrDP2)
//	                startCounter( PreemptionDetectorType.TYPE_DP2, i);
//	            lastDp2Active[i] = 0;
//	        }
//	        if (getBRTDetector( PreemptionDetectorType.TYPE_DS, i).IsActivated())
//	        {
//	            if (lastDlActive[i] > ValidPrDS)
//	                startCounter( PreemptionDetectorType.TYPE_DS, i);
//	            lastDsActive[i] = 0;
//	        }
//	    }
//	}
//
//	protected void forwardCounters( )
//	{
//	    for ( int i = 0; i < BrtMoves; i++)
//	    {
//	        int tS = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TS, i));
//
//	        if (lastDlActive[i] < 999 * Var.ONE_SEC)
//	        	lastDlActive[i]		+= node.getZyklDauer();
//	        if (lastDp1Active[i] < 999 * Var.ONE_SEC)
//		        lastDp1Active[i]	+= node.getZyklDauer();
//	        if (lastDp2Active[i] < 999 * Var.ONE_SEC)
//		        lastDp2Active[i]	+= node.getZyklDauer();
//	        if (lastDsActive[i] < 999 * Var.ONE_SEC)
//		        lastDsActive[i]		+= node.getZyklDauer();
//	        if (lastDqActive[i] < 999 * Var.ONE_SEC)
//		        lastDqActive[i]		+= node.getZyklDauer();
//	        
//	        for ( int j = 0; j < BrtsPerMove; j++ )
//	        {
//	            if ( tLx[i][j]  != ResetDec )
//	            	tLx[i][j] += node.getZyklDauer();
//	            if ( tP1x[i][j] != ResetDec )
//	            	tP1x[i][j] += node.getZyklDauer();
//	            if ( tP2x[i][j] != ResetDec )
//	            	tP2x[i][j] += node.getZyklDauer();
//	            if ( tSx[i][j]  != ResetDec && ( tSx[i][j] < tS || tSx[i][j] >= tS && getBRTMove( i ).isActive()))
//	            	tSx[i][j] += node.getZyklDauer();
//	        }
//	    }
//	    
//	}
//
//	protected void timeoutCounters( )
//	{
//	    for ( int i = 0; i < BrtMoves; i++)
//	    {
//	        int tLmax  = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TL_MAX , i));
//	        int tP1max = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP1_MAX, i));
//	        int tP2max = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP2_MAX, i));
//	        int tSmax  = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TS_MAX , i));
//	        
//	        for ( int j = 0; j < BrtsPerMove; j++ )
//	        {
//	            if ( tLx[i][j] != ResetDec && tLx[i][j] >= tLmax + Var.ONE_SEC && tP1x[i][j] == ResetDec && tP2x[i][j] == ResetDec && tSx[i][j] == ResetDec )
//	                shiftBrtArrayUp(i,j);
//	            if ( tP1x[i][j] != ResetDec && tP1x[i][j] >= tP1max + Var.ONE_SEC && tP2x[i][j] == ResetDec && tSx[i][j] == ResetDec )
//	                shiftBrtArrayUp(i,j);
//	            if ( tP2x[i][j] != ResetDec && tP2x[i][j] >= tP2max + Var.ONE_SEC && tSx[i][j] == ResetDec )
//	                shiftBrtArrayUp(i,j);
//	            if ( tSx[i][j] != ResetDec && tSx[i][j] >= tSmax + Var.ONE_SEC )
//	                shiftBrtArrayUp(i,j);
//	        }
//	    }
//	}
//
//	protected void calcAt( )
//	{
//	    arrivalTime  = 1000 * Var.ONE_SEC;
//	    for ( int i = 0; i < BrtMoves; i++ )
//	    {
//	        int tL  = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TL , i));
//	        int tP1 = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP1, i));
//	        int tP2 = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TP2, i));
//	        int tS  = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_TS , i));
//
//	        boolean oooDP1 = getBRTDetector( PreemptionDetectorType.TYPE_OOODP1, i).IsActive(); 
//	        boolean oooDP2 = getBRTDetector( PreemptionDetectorType.TYPE_OOODP2, i).IsActive(); 
//	        boolean oooDS  = getBRTDetector( PreemptionDetectorType.TYPE_OOODS , i).IsActive(); 
//
//	        for ( int j = 0; j < BrtsPerMove ; j++ )
//	        {
//	            if ( tSx[i][j] != ResetDec )
//	            {
//	            	aTx[i][j] = Math.max( 0, tS - tSx[i][j] );
//	            }
//	            else if ( tP2x[i][j] != ResetDec )
//	            {
//	                int minVal = oooDS ? 0 : tS + 1 * Var.ONE_SEC;
//	                aTx[i][j] = Math.max( minVal, tP2 - tP2x[i][j] );
//	            }
//	            else if ( tP1x[i][j] != ResetDec )
//	            {
//	                int minVal = oooDP2 ? ( oooDS ? 0 : tS + 1 * Var.ONE_SEC) : tP2 + 1 * Var.ONE_SEC;
//	                aTx[i][j] = Math.max( minVal, tP1 - tP1x[i][j] );
//	            }
//	            else if ( tLx[i][j] != ResetDec )
//	            {
//	                int minVal = oooDP1 ? ( oooDP2 ? ( oooDS ? 0 : tS + 1 * Var.ONE_SEC) : tP2 + 1 * Var.ONE_SEC) : tP1 + 1 * Var.ONE_SEC;
//	                aTx[i][j] = Math.max( minVal, tL - tLx[i][j] );
//	            }
//	            else
//	            	aTx[i][j] = ResetDec;
//	            
//	            if (aTx[i][j] != ResetDec)
//	                arrivalTime = Math.min( arrivalTime , aTx[i][j]);
//	        }
//	    }
//	    if ( arrivalTime == 1000 * Var.ONE_SEC )
//	        arrivalTime = ResetDec;
//	}
//
//	private void cycleStart()
//	{
//	    for ( int i = 0; i < BrtMoves; i++ )
//	    {
//	        memBRT[i]       = 0;
//	        memIns[i]       = 0;
//	    }
//
//	    for ( int i = 0; i < CompMoves; i++ )
//	    {
//	        if ( memExDet[i] ) {
//	            comp[i] = Math.max( 0, getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_CP_NEED, i ) ) - getCompMove( i ).getCumulativeCycleGT( 2 ) );
//	        } else {
//	            comp[i] = 0;
//	        }
//	        cpMin[i] = Math.min( getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_CP_MAX, i ) ), getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_CP_MIN, i ) ) + comp[i] );
//	    }
//	}
//
//	private void calcPriorityLevel()
//	{
//	    for ( int i = 0; i < BrtMoves; i++ )
//	    {
//	        int maxPLj = getParameterValue( getParamNumber(PreemptionDetectorParameterType.TYPE_PL_MAX, i));
//	        if ( getArrivalTime( i, 1 ) == ResetDec || memBRT[i] >= MaxBRT )
//	            priorityLevel[i] = Math.min( 0, maxPLj );
//	        else if ( memIns[i] >= getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_INS_MAX, i ) ) )
//	            priorityLevel[i] = Math.min( 1, maxPLj );
//	        else
//	            priorityLevel[i] = Math.min( 2, maxPLj );
//	    }
//	}
//
//	private void calcMemInsj()
//	{
//	    boolean insertedStage = !node.isInInterStage() && isInserted(node.getCurrStage()) 
//	            || node.isInInterStage() && isInserted(node.getCurrStage());
//
//	    boolean startIsgToInsertedStage = node.isInInterStage() 
//	                                        && isInserted(node.getCurrStage())   
//	                                        && node.getCurrStageDuration() == 1 * Var.ONE_SEC;
//	    
//	    boolean doneInsertedStage =  node.isInInterStage() && isInserted(node.getPrevStage()) 
//	                                    && node.getCurrStage().GT() == 1 * Var.ONE_SEC;
//	    
//	    for ( int i = 0; i < BrtMoves; i++ )
//	    {
//	        //start of interstage to inserted stage
//	        if (startIsgToInsertedStage)
//	        {
//	            memInsTempAT[i]  = aTx[i][0];
//	            if ( memInsTempAT[i] != ResetDec )
//	                memInsTempCtr[i] = 0;
//	            else
//	                memInsTempCtr[i] = -1;
//	            memInsTempPL[i]  = priorityLevel[i];
//	        }
//	        //during interstage to inserted stage and inserted stage
//	        if (insertedStage)
//	        {
//	            memInsTempDQ[i]  = getBRTDetector( PreemptionDetectorType.TYPE_DQ, i ).IsActivated();
//	            if (memInsTempAT[i] == ResetDec && aTx[i][0] != ResetDec)
//	            {
//	                memInsTempAT[i] =  aTx[i][0];  
//	                memInsTempCtr[i] = 0;
//	            }                
//	        }
//	        if (doneInsertedStage)
//	        {
//	            if ( memInsTempPL[i] == 2 && ( memInsTempDQ[i] || memInsTempAT[i] != ResetDec && memInsTempCtr[i] >= memInsTempAT[i] + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_CRSFAC, i ) ) ) )
//	                memIns[i]++;
//	            memInsTempCtr[i] = -1;
//	            memInsTempPL[i]  = 0;
//	            memInsTempAT[i]  = ResetDec;
//	        }
//	        if ( memInsTempCtr[i] >= 0 )
//	            memInsTempCtr[i] += node.getZyklDauer();
//
//	    }
//
//	}
//
//	private void calcComp()
//	{
//	    if ( node.isInInterStage() && node.getCurrStageDuration() == 1 * Var.ONE_SEC - Stage.MAX_INTERSTAGE_DURATION)
//	    {
//	        for ( int i = 0; i < CompMoves; i++ )
//	        {
//	        	
//	        	if ((i == 0 && node.getProgZeit() == 68000) || (i == 1 && node.getProgZeit() == 86000)) {
//
//	        	}
//	            if ( getCompMove( i ).RT() <= 1 * Var.ONE_SEC && node.getMoveExtensionDetector( getCompMove( i ) ) != null )
//	            {
//	                memExDet[i] = node.getMoveExtensionDetector( getCompMove( i ) ).RT() <= 1 * Var.ONE_SEC;
//	            }
//	        }
//	    }
//	}
//	
//	private void Print() {
////		Func.DebugPrintArray("moveMap", moveMap				);
////		Func.DebugPrintArray("compMap", compMap				);
////		Func.DebugPrintArray("detectors", detectors			);
////		Func.DebugPrintArray("tLx", tLx					);
////		Func.DebugPrintArray("tP1x", tP1x				    );
////		Func.DebugPrintArray("tP2x", tP2x				    );
////		Func.DebugPrintArray("tSx", tSx					);
////		Func.DebugPrintArray("aTx", aTx					);
////		Func.DebugPrintArray("lastDlActive", lastDlActive		    );
////		Func.DebugPrintArray("lastDp1Active", lastDp1Active		);
////		Func.DebugPrintArray("lastDp2Active", lastDp2Active		);
////		Func.DebugPrintArray("lastDsActive", lastDsActive		    );
////		Func.DebugPrintArray("lastDqActive", lastDqActive		    );
//		/*
//		Func.DebugPrintArray("memBRT", memBRT				);
//		Func.DebugPrintArray("priorityLevel", priorityLevel		);
//		Func.DebugPrintArray("memInsTempAT", memInsTempAT		    );
//		Func.DebugPrintArray("memInsTempCtr", memInsTempCtr		);
//		Func.DebugPrintArray("memInsTempPL", memInsTempPL		    );
//		Func.DebugPrintArray("memIns", memIns				);
//		Func.DebugPrintArray("comp", comp				    );
//		Func.DebugPrintArray("cpMin", cpMin				);
//		*/
//		//Func.DebugPrint("");
//		//Func.DebugPrint("" + node.getProgZeit());
//		//Func.DebugPrintArray("memExDet", memExDet			    );
//		//Func.DebugPrint("");
//		/*
//		Func.DebugPrintArray("memTsOnBrtMoveOpen", memTsOnBrtMoveOpen	);
//		Func.DebugPrintArray("memInsTempDQ", memInsTempDQ		    );
//		*/
////		Func.DebugPrintArray("Comp 2", ((Tk1)node).k2.greenTimes);
////		Func.DebugPrintArray("Comp 5", ((Tk1)node).k5.greenTimes);
//	}
//	
//	public void runSecond()
//	{
//		if (!Var.controller.isPreemption() || (!Var.controller.isAppHaifa() && !Var.controller.isAppJerusalem())) {
//			return;
//		}
//		
//		if (node.isOffline() || (!node.isRegularProgram() && !node.isInMaintenance())) {
//			return;
//		}
//
//	    if (node.getCurrCycleSec() == 1 * Var.ONE_SEC) {
//	        cycleStart();
//	    }
//		/************************* This part is constant *************************/
//	    calcMemTsOnBrtMoveOpen();
//	    forwardCounters();       
//	    checkDetectors();        
//	    timeoutCounters();       
//	    calcAt();                
//	    calcPriorityLevel();     
//	    calcMemInsj();           
//	    calcComp();              
//        /*************************************************************************/
//		//for Actros Access view
//		updateActrosAccessVariables();
///*
//		if (Func.TK_InLogicProg(node))
//			if ( node.lenPhue == ExtendedPhase.MaxPhueLenght && Phase.getAktivePhase(node).getPhasenZeit() == 1 * Var.ONE_SEC )
//				updateDetMem();
//*/	    
//		if (node.isRegularProgram()) {
//			EverySecond();
//		}
//		
//		Print();
//	}
//
//	private void calcMemTsOnBrtMoveOpen()
//	{
//	    for ( int i = 0; i < BrtMoves; i++ )
//	    {
//	        if (getBRTMove( i ).GT() == 1 * Var.ONE_SEC)
//	        {
//	            memTsOnBrtMoveOpen[i] = tSx[i][0] >= 0;
//        	}
//	    }
//	}
//
//	public int getArrivalTime( int brtIndex, int index  )
//	{
//	    if ( index < 1 || index > BrtsPerMove )
//	        return UNDEFINED;
//	    return aTx[brtIndex][index - 1];
//	}
//
//	public int getTlj( int brtIndex, int index  )
//	{
//	    if ( index < 1 || index > BrtsPerMove )
//	        return UNDEFINED;
//	    return tLx[brtIndex][index - 1];
//	}
//
//	public int getTp1j( int brtIndex, int index  )
//	{
//	    if ( index < 1 || index > BrtsPerMove )
//	        return UNDEFINED;
//	    return tP1x[brtIndex][index - 1];
//	}
//
//	public int getTp2j( int brtIndex, int index  )
//	{
//	    if ( index < 1 || index > BrtsPerMove )
//	        return UNDEFINED;
//	    return tP2x[brtIndex][index - 1];
//	}
//
//	public int getTsj( int brtIndex, int index  )
//	{
//	    if ( index < 1 || index > BrtsPerMove )
//	        return UNDEFINED;
//	    return tSx[brtIndex][index - 1];
//	}
//
//	public int getGtCpMin(int compMoveIndex)
//	{
//	    if ( compMoveIndex < 0 || compMoveIndex >= CompMoves )
//	        return UNDEFINED;
//	    return cpMin[compMoveIndex];
//	}
//
//	public int getComp(int compMoveIndex)
//	{
//	    if ( compMoveIndex < 0 || compMoveIndex >= CompMoves )
//	        return UNDEFINED;
//	    return comp[compMoveIndex];
//	}
//
//	public int API_ArrivalTime()
//	{
//		return arrivalTime;
//	}
//
//	public int API_AT()
//	{
//		return arrivalTime;
//	}
//
//	public int API_ATj(Move move, int busIndex)
//	{
//	    return getArrivalTime( getBRTIndex( move ) , busIndex );
//	}
//
//	public int API_Compi(Move move)
//	{
//	    return getComp(getCompIndex(move));
//	}
//
//	protected int API_CumulativeCycleGT( Move move, int cycles)
//	{
//		return move.getCumulativeCycleGT( cycles );
//	}
///*
//	protected int API_CumulativeGT( int _ARG1_, int _ARG2_ )
//	{
//		return api.getCumulativeGT( _ARG1_, _ARG2_ );
//	}
//
//	protected int API_CycleGT( int _ARG1_ )
//	{
//		return api.getElementGlobalGreenTime( _ARG1_ );
//	}
//
//	protected int API_GetProgOffset( )
//	{
//		int value = api.getProgOffset( );
//	    
//		return value;
//	}
//*/
//	public int API_GT_CpMin_i( Move move )
//	{
//	    return getGtCpMin( getCompIndex( move ));
//	}
//
//	protected boolean API_IsProgActive( int progNumber )
//	{
//		return ( node.getAktProg().getNummer() == progNumber);
//	}
//
//	protected int API_MemBRTj(Move move)
//	{
//	    return memBRT[getBRTIndex(move)];
//	}
//
//	protected int API_MemInsj(Move move)
//	{
//	    return memIns[getBRTIndex(move)];
//	}
///*
//	protected int API_OccupancyPercent( int _ARG1_, int _ARG2_ )
//	{
//		int value = api.getCumulativeGTPercent( _ARG1_, _ARG2_ );
//	    
//		return value;
//	}
//
//	protected int API_OccupancyTime( int _ARG1_, int _ARG2_ )
//	{
//		int value = api.getCumulativeGT( _ARG1_, _ARG2_ );
//	    
//		return value;
//	}
//*/
//	protected int API_PLj(Move move)
//	{
//	    return priorityLevel[getBRTIndex(move)];
//	}
//
//	protected Stage API_getPrevStage()
//	{
//	 return node.getPrevStage();
//	}
///*
//	protected int API_Time(int _ARG1_)
//	{
//	    int value = getTime( _ARG1_ , -1 );
//	    
//		return value;
//	}
//*/
//	protected int API_tLj(Move move , int busIndex)
//	{
//	    return getTlj( getBRTIndex(move) , busIndex);
//	}
//
//	protected int API_tP1j(Move move , int busIndex)
//	{
//	    return getTp1j( getBRTIndex(move) , busIndex);
//	}
//
//	protected int API_tP2j(Move move , int busIndex)
//	{
//	    return getTp2j( getBRTIndex(move) , busIndex);
//	}
//
//	protected int API_tSj(Move move , int busIndex)
//	{
//	    return getTsj( getBRTIndex(move) , busIndex);
//	}
//	
//	abstract public void InitializePreemption();
//	abstract public void updateParameters();
//	abstract public void FirstSecondActions();
//	abstract public void EverySecond();
//	abstract public void PostEverySecond();
//	abstract public void updateActrosAccessVariables();
//	
//}