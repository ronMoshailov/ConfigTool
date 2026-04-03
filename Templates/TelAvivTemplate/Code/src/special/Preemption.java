package special;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 239                                                                     	*
 *  Junction Name  : Varburg / Rains / HaTkuma Yahiam - Maavar Hazaya							*
 *  Equipmentno.   : h239                                                                    	*
 *                                                                                              *
 ************************************************************************************************/
import det.Det;
import det.Detektor;
import sg.Sg;
import sg.Zustand;
import ta172.ParamSet;
import ta172.Var;
import vt.Phase;

abstract public class Preemption
{
	private Node _tk;
	//Starting the timers when there is RISING of a demand on the specific input, set an departure
    //  PT_xx_1    PT_xx_2    PT_xx_3      PT_xx_4
    //   _ _ _      _ _ _      _ _ _   ||   _ _ _   
    //  |     |    |     |    |     |  ||  |     |   
    //  |_ _ _|    |_ _ _|    |_ _ _|  ||  |_ _ _|   
    //     1          2          3     ||     4
	//
    public static final int Yes       =    1;
    public static final int No        =    0;
    public static final int UNDEFINED =   -1;
    public static final int NONE      =   -1;
    public static final int CT_NONE   =  999*Var.ONE_SEC; // Current Time None (currently, no bus detected)
    public int BrtMoves;
    public int CompMoves;  
    public static final int DptNum   =    4; // maximum number of detectors per each public transport signalgroup
    public static final int MaxCumPeriods =   10; //maximum possible number of cycles to count cumulative green (see definition of movegreentime and phasegreentime in FuncPreemption)
    private int sum = 0;

    public static final int     DPT_DET_FAR         = 1;
    public static final int     DPT_DET_NEAR        = 2;
    public static final int     DPT_DET_SL          = 3;
    public static final int     DPT_DET_CANCEL      = 4;
    
	protected int DPT_QUANTITY = 0;
	protected int NONE_LOGICAL_DPT = 0;
	// update according to Axilion code OR according to traffic plan
    protected int     BrtsPerMove; // maximum number of busses per signal group
    protected int     Dpt4TimeOut; // maximum time till deletion of a bus after estimated arrival time
    protected boolean ResetTimeOutOnRed; // if a bus is to be deleted after being late even if signal group is in red
    //public static int MAX_STAGES; // maximum number of stages
    protected int     MaxRedTimePhase; // maximum number of pedestrian signal groups for which to count red time
    
    // constant variables and arrays
    protected int[][] atCounters; // arrival time of each bus on each public transport signal group
    protected int[][] atTypes; // array to tell if counting DPT1 or DPT2 or DPT3
    protected int[][] moveMap; // map between index and public transport sg, direction priority parameter
    protected int[] compMap; // map between index and compensation sg
    protected boolean[] compDet;
    protected int DETECTORS; // maximum total number of DPTs
    protected int[][] dptData; // number of sg, DPT type (1-4), Logical Index of DPT, assumedTimeFromDetectionToTarget
    protected int[][] stageComp; // A,15,45,2,isReset - stage, move, duration, cycles, isResetComp - Yes/No
    protected int[][] redTimePhase; // pedestrian move number, push-buton demand status, counter of red time
    public DPTDetector[] DPT_Detectors; // the DPT detectors array
    private boolean[] memBrt; // memory a BRT passed in current cycle;
    protected int[] moveArrivalTime; // move arrival time array
    protected int[] normMoveAT; // norm move AT array
    protected int[] compMoveDetMem; // count cycles with non demand for move on close
    public int[][] dpt2Timeout; // array to count time out to reach DPT2 after DPT1 


    public int arrivalTime; // Arrival Time of the nearest bus (after taking into count direction priority)
    public static int normAT;
    
    protected int sgsOffset = 0; // number of signal groups of all nodes before current node
	protected boolean phasecumulative_once = false;
	protected int[] sgcumulative_once;
    protected int[][][] movegreentime;
    protected int[][] phasegreentime;

    static public boolean IsActive(boolean det,String type){		
		String ty= type.toUpperCase();
		if(ty.startsWith("D") || ty.startsWith("P") || ty.startsWith("S") )
			return det;
		return !det;
	}
	
    static public boolean IsInActive(boolean det,String type){
		String ty= type.toUpperCase();
		if(ty.startsWith("D") || ty.startsWith("P") || ty.startsWith("S"))
			return !det;
		return det;
	}
    
    public int calcNumOfBRTMoves() {
        return BrtMoves;
    }

    public int calcNumOfCompMoves() {
        return CompMoves;
    }
    
    public int getBRTIndex( int move ) {
    	for (int i = 0; i < BrtMoves; i++)
    		if (moveMap[i][0] == move)
    			return i;
    	
    	return NONE;
    }
    
//    public int getCompIndex( int move ) {
//    	for (int i = 0; i < CompMoves; i++)
//    		if (compMap[i] == move)
//    			return i;
//    	
//    	return NONE;
//    }
//    
    

	

    // TODO update when code exists... that's how it was in Bar Yehuda / Downtown
    public abstract int getCompMoveCumPeriod(int move);
    
    public int getCompIndex( int move ) {
    	for (int i = 0; i < compMap.length; i++) {
    		if (compMap[i] == move) {
    			return i;
    		}
    	}
    	return NONE;
    }
    
    public boolean getMoveExtensionDetector(int move ) {
    	return compDet[getCompIndex(move)];
    }
    
	public Preemption(Node tk, int actualDptQuantity, int extraDptQuantity, int brt_num, int brts_per_move, int dpt4_timoue, boolean isReset, int red_stages, int compMoves) {
		_tk = tk;
		sgsOffset = _tk.getNumberOfPreviousSGs();
		DPT_QUANTITY = actualDptQuantity;
		NONE_LOGICAL_DPT = extraDptQuantity;
		BrtMoves = brt_num;
		BrtsPerMove = brts_per_move;
		Dpt4TimeOut = dpt4_timoue;
		ResetTimeOutOnRed = isReset;
		MaxRedTimePhase = red_stages;
		atCounters        = new int[BrtMoves][BrtsPerMove];
		atTypes           = new int[BrtMoves][BrtsPerMove];
		moveMap           = new int[BrtMoves][2];
		DETECTORS         = BrtMoves * DptNum;
		dptData           = new int[DETECTORS][4];
		redTimePhase      = new int[MaxRedTimePhase][3];
		DPT_Detectors     = new DPTDetector[DPT_QUANTITY + NONE_LOGICAL_DPT];
		sgcumulative_once = new int[_tk.sgQuantity + 1];
	    movegreentime     = new int[MaxCumPeriods + 2][_tk.sgQuantity + 1][2];
	    stageComp         = new int[_tk.MAX_STAGES][5];
	    phasegreentime    = new int[MaxCumPeriods + 2][_tk.MAX_STAGES];
	    CompMoves         = compMoves;
	    compMap           = new int[CompMoves];
	    compDet           = new boolean[CompMoves];
	    compMoveDetMem    = new int[CompMoves];
	    normMoveAT        = new int[BrtMoves];
	    moveArrivalTime   = new int[BrtMoves];
	    memBrt            = new boolean[BrtMoves];
	    dpt2Timeout       = new int[BrtMoves][BrtsPerMove];
	}
	
	public void runSecond()
	{
		if (!Var.controller.isPreemption() || !Var.controller.isAppHaifa()) {
			return;
		}

	    if (_tk.getCurrCycleSec == 1 * Var.ONE_SEC)
	        cycleStart();
		
		/************************* This part is constant *************************/
		updateParameter(); // update parameters from time table
		updateRedTimePhaseDemand(); // update status of demand from push-buttons
		
        forwardTimers(); // advance arrival time counters
        checkDetectors(); // add or remove or advance public transport bus according to demands from detectors
        calcAllArrivalTime(); // update arrival time of the nearest prioritized public transport bus
        updateRedTimePhase(); // update counters of red phases
        /*************************************************************************/

		//for Actros Access view
		updateActrosAccessVariables();
/*
		if (Func.TK_InLogicProg(_tk))
			if ( _tk.lenPhue == ExtendedPhase.MaxPhueLenght && Phase.getAktivePhase(_tk).getPhasenZeit() == 1 * Var.ONE_SEC )
				updateDetMem();
*/	    
		if (_tk.isRegularProgram()) {
			EverySecond();
		}
	}
    
    public void ResetCumulatives() {
    	int i, j;
    	for (i = 0; i < MaxCumPeriods + 2; i++) {
    		for (j = 0; j < (_tk.sgQuantity + 1); j++) {
    			movegreentime[ i ][ j ][ 0 ] = 0;
    			movegreentime[ i ][ j ][ 1 ] = 0;
    			sgcumulative_once[ j ] = 0;
    		}
    	}

    	for (i = 0; i < MaxCumPeriods + 2; i++) {
    		for (j = 0; j < _tk.MAX_STAGES; j++) {
    			phasegreentime[ i ][ j ] = 0;
    		}
    	}
    }
    
    public void ResetRedTimePhases() {
    	int i;
    	for (i = 0; i < MaxRedTimePhase; i++) {
    		redTimePhase[i][2] = CT_NONE;
    	}
    }

    protected int getCumulativeCycleGTreadOnly(Sg sg, int periods) {
    	int move = sg.getNr() - sgsOffset ,j;
    	sum = 0;
    	for ( j = 1; j <= periods; j++ )
        {
        	sum += movegreentime[ j ][ move ][ 0 ];
        }
        return sum;
    }
    
    /**
     * Method counts the green time for a chosen signal group over the chosen periods of cycles 
     * @param move : signal group number
     * @param periods : quantity of cycles to calculate accumulated green
     * @return : amount of accumulated green time in the chosen cycles of the signal group in milliseconds
     */
    protected int getCumulativeCycleGT( Move sg, int periods) {
        int j, move = sg.getNr() - sgsOffset;
//        Sg sg = Sg.getSgByNum( move );
        movegreentime[ 0 ][ move ][ 0 ] = 0; // contain the accumulated green value (in case of 3 cycles sum of cells 1,2,3)
        
        if (( sg.getZustand() == Zustand.GRUEN && sg.getZustZeit() > 0) || sg.getZustand() == Zustand.GRUENBLINKUEB)
        {
        	movegreentime[ 1 ][ move ][ 0 ] += _tk.getZyklDauer();
        }
        
        if(sg.getTyp()==Move.car && sg.getZustand() == Zustand.GELB && sg.getZustZeit()<_tk.getZyklDauer())
        {
        	movegreentime[ 1 ][ move ][ 0 ] += _tk.getZyklDauer();
        }
            
        if ( sgcumulative_once[ move ] != _tk.CycleCount) // if just changed from green 
        {
        	for ( j = MaxCumPeriods + 1; j > 1; j-- )
        	{
        		movegreentime[ j ][ move ][ 0 ] = movegreentime[ j - 1 ][ move ][ 0 ];
        	}
        	movegreentime[ 1 ][ move ][ 0 ] = 0;
        	
        	if ( _tk.isMainSg( sg ) )
        	{
            	movegreentime[ 1 ][ move ][ 0 ] += _tk.getZyklDauer();
            	movegreentime[ 2 ][ move ][ 0 ] -= _tk.getZyklDauer();
        	}
        }
        
    	for ( j = 1; j <= periods; j++ )
        {
        	movegreentime[ 0 ][ move ][ 0 ] += movegreentime[ j ][ move ][ 0 ];
        }
        
    	sgcumulative_once[ move ] = _tk.CycleCount;
        return movegreentime[ 0 ][ move ][ 0 ];
    }

    protected int GetPhaseCumulativeGreen(int periods, Phase name, int iInterstageDuration) {
        int k, value = 0, lenPhue = iInterstageDuration;
        int num = name.getNummer();
        
        for (num = 0; num < _tk.MAX_STAGES; num++)
        {
        	if (stageComp[ num ][ 0 ] == name.getNummer())
        	{
                phasegreentime[ 0 ][ num ] = 0; // contain the accumulated green value (in case of 3 cycles sum of cells 1,2,3) 
                int zwspgreen = 0;
                
                if (name.isAktiv() && name.getPhasenZeit() >= iInterstageDuration) {  // only if active
                	phasecumulative_once = true;
                    phasegreentime[ periods + 1 ][ num ] = name.getPhasenZeit() - lenPhue; // load cell 4 with current cycle green time
                    zwspgreen = name.getPhasenZeit() - lenPhue; // load variable zwspgreen with current cycle green time
                }
                
                if (!name.isAktiv() && Phase.getAktivePhase(name.getTeilKnoten()).getPhasenZeit() < 1000 && phasecumulative_once) { // during yellow time

                	phasecumulative_once = false;
                    phasegreentime[ periods ][ num ] = phasegreentime[periods+1][num];
                    phasegreentime[ periods + 1 ][ num ] = 0;
                    for (k = 1; k < periods; k++) {

                        phasegreentime[ k ][ num ] = phasegreentime[ k + 1 ][ num ];
                    }

                    phasegreentime[ periods ][ num ] = 0;
                }

                for (k = 1; k <= periods; k++)
                {
                    phasegreentime[ 0 ][ num ] = phasegreentime[ 0 ][ num ]+ phasegreentime[ k ][ num ];
                }

                phasegreentime[ 0 ][ num ] = phasegreentime[ 0 ][ num ]+ zwspgreen;
                value = phasegreentime[ 0 ][ num ];
        		return value;
        	}
        }
        return -1;
    }

    public int getMoveIndex( int move ) {
        for (int i = 0; i < BrtMoves; i++) {
            if (moveMap[i][0] == NONE) {
                return NONE;
            }
            if (moveMap[i][0] == move) {
                return i;
            }
        }
        return NONE;
    }

    public Sg getBRTMove( int index ) {
    	if (index >= BrtMoves)
    		return null;

    	return Sg.getSgByNum(moveMap[index][0]);
    }
    
    public Sg getCompMove( int index ) {
    	if (index >= CompMoves)
    		return null;
    	
    	return Sg.getSgByNum(compMap[index]);
    }

    public int getStageCompIndex( Stage stage ) {
        for ( int i = 0; i < _tk.MAX_STAGES; i++ ) {
            if ( stageComp[ i ][ 0 ] == NONE ) {
                return NONE;
            }
            if ( stageComp[ i ][ 0 ] == stage.getNummer()) {
                return i;
            }
        }
        return NONE;
    }
    
    public int DPTdecode(Detektor dpt_in) {
    	return dpt_in.belegt() ? 1 : 0;
    }
    
    public void resetMemBRT( int moveIndex ) {
        for ( int i = 0; i < BrtMoves; i++) {
            memBrt[i] = ( i == moveIndex );
        }
    }
	
	public void updateRedTimePhase() {
        for (int i = 0; i < MaxRedTimePhase; i++) {
            if (redTimePhase[i][0] == NONE) {
                break;
            }
            //the specified signal group is found and is red and there is a demand on detector and it's the first demand checked since last reset
			if ((Sg.getSgByNum( redTimePhase[i][0]).getZustand() == Zustand.ROT) &&	//is red
				(Det.getDetektorByNum(redTimePhase[i][1]).getAnforderung()) && //demand exists
				(redTimePhase[i][2] == CT_NONE)) { //first demand since last reset
				redTimePhase[i][2] = -_tk.getZyklDauer();//_tk.getZyklDauer(); //starting timer
			}
			if ((Sg.getSgByNum(redTimePhase[i][0]).getZustand() == Zustand.GRUEN)) { //if signal group is green
				redTimePhase[i][2] = CT_NONE; //reset
			}
			if (redTimePhase[i][2] != CT_NONE) { //if not reset
				redTimePhase[i][2] += _tk.getZyklDauer(); // advance counter
			}
        }
    }
	
	/**
	 * this methor returns the direction priority
	 * @param sg - the signal group in question
	 * @return the priority of the direction
	 */
	public int getBrtPriority( int move ) {
		if (getMoveIndex( move ) == NONE) {
			return NONE;
		}
		return moveMap[getMoveIndex(move)][ 1 ];
	}   

	/**
	 * Check if exists BRT from other direction with higher priority
	 */    
	public boolean existsBRTwithHigherPriority( int moveIndex ) {
	    int movePriority = getBrtPriority( getBRTMove( moveIndex).getNr() );
	    for ( int i = 0; i < BrtMoves; i++ ) {
	        if ( getBrtPriority( getBRTMove( i).getNr() ) > movePriority && moveArrivalTime[ i ] != CT_NONE )
	            return true;
	    }

	    return false;
	}

    /**
     * number of BRT's with arrival time less than or equal time (seconds)
     */
    public int NumBrtCloserThan( int time ) {
        int count = 0;
        for ( int i = 0; i < BrtMoves; i++ ) {
            for ( int j = 0; j < BrtsPerMove; j++ ) {
                if ( atCounters[ i ][ j ] != CT_NONE && atCounters[ i ][ j ] <= time ) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public abstract int getTime( int timeConst, Sg ptMove  );

    /**
     * return allBrtCloserThan
     */
    public boolean allBrtCloserThan(int timeStringJConst, int timeAdd) {
        boolean res = true;
        for ( int moveIndex = 0; moveIndex < BrtMoves; moveIndex++) {
            int time = getTime( timeStringJConst, getBRTMove( moveIndex ) );
            
            if ( normMoveAT[moveIndex] != CT_NONE && normMoveAT[moveIndex] >  time + timeAdd ) {
                res =  false;
                break;
            }
        }
        return res;
    }

    /**
     * return allBrtFarThan
     */
    public boolean allBrtFarThan(int timeStringJConst, int timeAdd) {
        boolean res = true;
        for ( int moveIndex = 0; moveIndex < BrtMoves; moveIndex++) {
            int time = getTime( timeStringJConst, getBRTMove( moveIndex ) );
            
            if ( normMoveAT[moveIndex] <=  time + timeAdd ) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * return anyBrtCloserThan
     */
    public boolean anyBrtCloserThan(int timeStringJConst, int timeAdd) {
        boolean res = false;
        for ( int moveIndex = 0; moveIndex < BrtMoves; moveIndex++) {
            int time = getTime( timeStringJConst, getBRTMove( moveIndex ) );
            
            if ( normMoveAT[moveIndex] <=  time + timeAdd ) {
                res =  true;
                break;
            }
        }
        return res;
    }

    /**
     * return anyBrtCloserThan
     */
    public boolean closestBrtAtPoint(int timeStringJConst, int timeAdd) {
        boolean res = false;
        for ( int moveIndex = 0; moveIndex < BrtMoves; moveIndex++) {
            int time = getTime( timeStringJConst, getBRTMove( moveIndex ) );
            
            if ( normMoveAT[moveIndex] == normAT && normMoveAT[moveIndex] ==  time + timeAdd  ) {
                res =  true;
                break;
            }
        }
        return res;
    }

    /**
     * number of BRT's with arrival time more than time (seconds)
     */
    public int NumBrtFarThan( int time ) {
        int count = 0;
        for ( int i = 0; i < BrtMoves; i++ ) {
            for ( int j = 0; j < BrtsPerMove; j++ ) {
                if ( atCounters[ i ][ j ] != CT_NONE && atCounters[ i ][ j ] > time ) {
                    count++;
                }
            }
        }

        return count;
    }
    
    /**
     * return the red time counter for a move and detector
     */
    public int GetRedTimePhase( int move, int detector)
    {
        for ( int i = 0; i < MaxRedTimePhase; i++)
        {
            if ( redTimePhase[ i][ 0] == NONE)
                break;
            if ( redTimePhase[ i][ 0] == move && redTimePhase[ i][ 1] == detector)
            {
                int res = redTimePhase[ i][ 2];
                if ( res == 0)
                    res = CT_NONE;
                return res;
            }
        }
        return -1;
    }
	
	/**
	 * for pedestrian signal groups. returns (in ms) the time of red state since the first demand (push-button) for this "red cycle"
	 * @param sg - the signal group in question
	 * @param det - state of demand for this sg
	 * @return the time of red (in ms) since first demand during red till current time. if 
	 */
	public int RedTimePhase( int move ) {
		int i;
		for (i = 0; i < MaxRedTimePhase; i++) {
			if (redTimePhase[ i ][ 0 ] == move) {
				return redTimePhase[ i ][ 2 ];
			}
		}
		return CT_NONE;
	}
	
//	public void ResetInactiveStagesCompensation() {
//		int i = 0, j = 0;
//		activeStages = ExtendedPhase.getActiveStages();
//		while (stageComp[i][0] != NONE) {
//			while (activeStages[j] != NONE) {
//			}
//		}
//		
//	}
	
	public void ResetCompensationOfInactiveStages() {
		int i = 0;
		while (stageComp[i][0] != NONE) {
			if (!_tk.wasStageActive((Stage)Phase.findPhaseByNum(stageComp[i][0]))) {
				stageComp[i][4] = Yes;
			}
			i++;
		}
	}
	
	public void ResetCompensation( int ph, int PI_cancel )
	{
		int i;
		for (i = 0; i < _tk.MAX_STAGES; i++) {
			if (stageComp[i][0] == NONE) {
				return;
			}
			if (stageComp[i][0] == ph) {
				stageComp[i][4] = PI_cancel;
			}
		}
	}
	
	public boolean isResetCompensation(Phase ph)
	{
		int i;
		for (i = 0; i < _tk.MAX_STAGES; i++)
		{
			if (stageComp[i][0] == ph.getNummer())
			{
				if (stageComp[i][4] == Yes)
				{
					return true;
				}
			}
		}
		return false;
	}

	protected int Time3( int type, Stage stage1, Stage stage2, Stage stage3 )
    {
        int res = InterStage.IsgDuration(stage1, stage2) + getStageDuration(type, stage2) + InterStage.IsgDuration(stage2, stage3);

        return res;
    }

	protected int Time4( int type, Stage stage1, Stage stage2, Stage stage3, Stage stage4 )
    {
        int res = InterStage.IsgDuration(stage1, stage2) + getStageDuration(type, stage2) + InterStage.IsgDuration(stage2, stage3) + getStageDuration(type, stage3) + InterStage.IsgDuration(stage3, stage4);
        return res;
    }

	protected int Time5( int type, Stage stage1, Stage stage2, Stage stage3, Stage stage4, Stage stage5 )
    {
        int res = InterStage.IsgDuration(stage1, stage2) + getStageDuration(type, stage2) + InterStage.IsgDuration(stage2, stage3) + getStageDuration(type, stage3) + InterStage.IsgDuration(stage3, stage4) + getStageDuration(type, stage4) + InterStage.IsgDuration(stage4, stage5);

        return res;
    }

    /**
     * return current stage minimum duration + compensation
      */
    public int getStageMinimumComp( int lenPhue )
    {
        Stage stage = (Stage)Phase.getAktivePhase(_tk);
        int stageMinDuration = getStageDuration( Var.STAGE_DURATION_MIN, stage );
        int moveCompGreen = getMoveComp( stage );

        int index = getStageCompIndex( stage );
        int move = stageComp[ index ][ 1 ];
        int diff = ((Move)Sg.getSgByNum( move )).GT() - stage.GT() - ( 1*Var.ONE_SEC );

        int newMin = moveCompGreen - diff;
        
        if ( stageMinDuration > newMin || _tk.CycleCount == 0 )
        {
            newMin = stageMinDuration;
        }
        
        return newMin;
    }
	
    /**
     * return the move compensation
     */
    public int getMoveComp( Stage stage )
    {
        int index = getStageCompIndex( stage );
        if ( index > NONE )
        {
            int move = stageComp[ index ][ 1 ];
            int requestedComp = stageComp[ index ][ 2 ];
            int cycles = stageComp[ index ][ 3 ];

            int res = requestedComp - getCumulativeCycleGTreadOnly( Sg.getSgByNum( move ), cycles ) +  ((Move)Sg.getSgByNum( move )).GT() - (1*Var.ONE_SEC);/*Func.gt( Sg.getSgByNum( move ) ) - (1*Var.ONE_SEC)*/;

            if ( res < 0 )
            {
                res = 0;
            }
            return res;
        }

        return 0;
    }
	
    /**
     * init stage compensation
     */
    public void initStageComp( int stage, int move, int comp, int cycles )
    {
        for ( int i = 0; i < _tk.MAX_STAGES; i++ )
        {
            if ( stageComp[ i ][ 0 ] == NONE )
            {
                stageComp[ i ][ 0 ] = stage;
                stageComp[ i ][ 1 ] = move;
                stageComp[ i ][ 2 ] = comp;
                stageComp[ i ][ 3 ] = cycles;
                return;
            }
            else if ( ( stageComp [ i ][ 0 ] == stage ) && ( stageComp[ i ][ 1 ] == move) )
            {
                stageComp[ i ][ 2 ] = comp;
                stageComp[ i ][ 3 ] = cycles;
                return;
            }
        }
    }
	
    /**
     * init red times
     */
    public void initRedTimePhase(int move )
    {
        for (int i = 0; i < MaxRedTimePhase; i++) {
            if (redTimePhase[i][0] == NONE) {
                redTimePhase[i][0] = move;
//                redTimePhase[i][1] = detector;
                redTimePhase[i][2] = CT_NONE;
                return;
            }
        }
    }

	public void init() {
        for ( int i = 0; i < DETECTORS; i++ ) {
            for ( int j = 0; j < 4; j++ ) {
            	dptData[ i ][ j ] = NONE;
            }
        }
        
        for ( int i = 0; i < BrtMoves; i++ ) {
        	moveMap[i][0] = NONE;
        	moveMap[i][1] = NONE;
        	
            for ( int j = 0; j < BrtsPerMove; j++ )
            {
            	atCounters[ i ][ j ] = CT_NONE;
            	atTypes[ i ][ j ] = 0;
            }
        }

        for ( int i = 0; i < _tk.MAX_STAGES; i++ ) {
        	stageComp[ i ][ 0 ] = NONE;
        	stageComp[ i ][ 1 ] = NONE;
        	stageComp[ i ][ 2 ] = NONE;
        	stageComp[ i ][ 3 ] = NONE;
        	stageComp[ i ][ 4 ] = Yes;
        }
        
        for ( int i = 0; i < MaxRedTimePhase; i++ ) {
            redTimePhase[ i ][ 0 ] = NONE;
            redTimePhase[ i ][ 1 ] = 0;
            redTimePhase[ i ][ 2 ] = CT_NONE;
        }

        arrivalTime = CT_NONE;
        normAT      = CT_NONE;
        
        for ( int i = 0; i < CompMoves; i++)
            compMoveDetMem[i] = 0;
        
        ResetCumulatives();
	}
	
	public boolean isActive( Sg sg )
	{
		return (sg.getZustand() == Zustand.GRUEN) || (sg.getZustand() == Zustand.GRUENBLINKUEB);
	}
	
	public int getBrtDetectorTime( int detectorNumber )
	{
		return dptData[detectorNumber][3];
	}
	
	public DPTDetector getBRTDetector( int detType, int brtMove) {
		for (int i = 0; i < DPT_Detectors.length; i++) {
			if (DPT_Detectors[i].getMoveNumber() == brtMove && DPT_Detectors[i].getDPTType() == detType)
				return DPT_Detectors[i];
		}
		return null;
	}
	
	public int getTimeToStopLineForDetector(DPTDetector det) {
		return det.getTimeToTarge();
	}
	
	public int getDpt2TimeOut(int index) {
		return NONE;
	}

    /**
     * forward timers
     */
    public void forwardTimers() {
        for ( int i = 0; i < BrtMoves; i++ ) {
        	int dpt2TimeToTarget = getTimeToStopLineForDetector( getBRTDetector( DPT_DET_NEAR, getBRTMove( i ).getNr() ) );
        	int dpt1TimeToTarget = getTimeToStopLineForDetector( getBRTDetector( DPT_DET_FAR, getBRTMove( i ).getNr() ) );
            int dpt2TimeoutVal = getDpt2TimeOut( i );
            if ( dpt2TimeoutVal == NONE )
                dpt2TimeoutVal = Math.max( 1 * Var.ONE_SEC, ( dpt1TimeToTarget - dpt2TimeToTarget ) );
            
            for ( int j = 0; j < BrtsPerMove; j++ ) {
                // if reached timeout DPT4 or DPT2 - reset AT
                if ( atCounters[ i ][ j ] < -Dpt4TimeOut || atTypes[ i ][ j ] == DPT_DET_FAR && dpt2Timeout[i][j] == dpt2TimeoutVal) {
        			atCounters[ i ][ j ] = CT_NONE;
        			atTypes[ i ][ j ] = NONE;
        			dpt2Timeout[ i ][ j ] = 0;
        			resetMemBRT( i );
                }

                if ( atCounters[ i ][ j ] != CT_NONE ) { // if there is BRT
                    // need to count when:
                    // before stopline
                    // or
                    // reached stopline and green - activate the timeout
                    if ( atCounters[ i ][ j ] > 0 && atTypes[ i][ j] == DPT_DET_FAR && atCounters[ i][ j] ==  dpt2TimeToTarget + _tk.getZyklDauer() && getBRTDetector( DPT_DET_NEAR, getBRTMove( i ).getNr() ).isOK()) {
                        dpt2Timeout[i][j] += _tk.getZyklDauer();
                    } else if (atCounters[i][j] > 0) {
                    	atCounters[i][j] -= _tk.getZyklDauer();
                    } else if ( atCounters[ i ][ j ] == 0 && isActive( Sg.getSgByNum( moveMap[i][0] ) ) ) { // reached stop line and green {
                        atCounters[ i ][ j ] -= _tk.getZyklDauer();
                    } else {
                        // if in timeout and the move becomes red - keep BRT at stopline else keep counting
                        if ( ResetTimeOutOnRed && !isActive( Sg.getSgByNum( moveMap[i][0] ) ) ) {
                            atCounters[ i ][ j ] = 0;
                        } else {
                            atCounters[ i ][ j ] -= _tk.getZyklDauer();
                        }
                    }
                }
            }
        }
    }

    /**
     * checks the detectors counters
     */
    public void checkDetectors() {
        for ( int i = 0; i < DPT_QUANTITY + NONE_LOGICAL_DPT; i++ ) {
        	DPT_Detectors[i].checkDetector();
        }
    }

    /**
     * gets the arrival time of the closest BRT for a move
     */
    protected int MoveArrivalTime( int move )
    {
        int moveIndex = getMoveIndex( move );
        int minAT = CT_NONE;
        for ( int i = 0; i < BrtsPerMove; i++ ) {
            if ( atCounters[ moveIndex ][ i ] != CT_NONE && atCounters[ moveIndex ][ i ] < minAT ) {
                minAT = atCounters[ moveIndex ][ i ];
            }
        }

        if ( minAT < 0 ) {
            minAT = 0;
        }
        return minAT;
    }

    /**
     * Check if exists BRT from other direction
     */    
    private boolean existsBRTfromOtherDirection( int moveIndex ) {
        for ( int i = 0; i < BrtMoves; i++ ) {
            if ( i == moveIndex )
                continue;
            if ( moveArrivalTime[i] != CT_NONE )
                return true;
        }

        return false;
    }

    /**
     * calculates the closest arrival time and sets AT
     */
    public void calcAllArrivalTime() {
    	arrivalTime = CT_NONE;
        normAT      = CT_NONE;

        //calc move arrival time
        for ( int moveIndex = 0; moveIndex < BrtMoves; moveIndex++) {
            int minAT = CT_NONE;
            for ( int j = 0; j < BrtsPerMove; j++) {
                if ( atCounters[ moveIndex][ j] != CT_NONE && atCounters[ moveIndex][ j] < minAT)
                    minAT = atCounters[ moveIndex][ j];
            }

            moveArrivalTime[ moveIndex ] = Math.max( 0, minAT );
            arrivalTime = Math.min( arrivalTime, moveArrivalTime[ moveIndex] );
        }

        // calc all the rest
        for ( int moveIndex = 0; moveIndex < BrtMoves; moveIndex++) {
            normMoveAT[ moveIndex] = moveArrivalTime[ moveIndex ];
            if ( existsBRTwithHigherPriority( moveIndex ) || memBrt[ moveIndex ] && existsBRTfromOtherDirection( moveIndex ))
                normMoveAT[ moveIndex] =  CT_NONE;
            
            normAT = Math.min( normAT, normMoveAT[ moveIndex] );
            
            
          //  System.out.println("ProgZeit : "+ _tk.getProgZeit() +" , moveIndex : "+moveIndex + ", normMoveAT[ moveIndex] " +normMoveAT[ moveIndex] + " , normAT : "+ normAT);
            
            
        }
    }

    /**
     * gets the arrival time of the closest BRT
     */
    public int arrivalTime() {
        return arrivalTime;
    }

  
    
    /**
     * gets the arrival time of the closest BRT for a move and for a detector
     */
    public int dxArrivalTime( int move, DPTDetector detector ) {
        int detectorNumber = NONE;
        int minAT = CT_NONE;
        
        if ( getBRTDetector( DPT_DET_FAR, move ) == detector )
            detectorNumber = DPT_DET_FAR;
        else
        if ( getBRTDetector( DPT_DET_NEAR, move ) == detector )
            detectorNumber = DPT_DET_NEAR;
        else
        if ( getBRTDetector( DPT_DET_SL, move ) == detector )
            detectorNumber = DPT_DET_SL;
        
        if (detectorNumber == NONE)
            return minAT;

        int moveIndex = getBRTIndex( move);
        for ( int j = 0; j < BrtsPerMove; j++)
        {
            if ( atTypes[ moveIndex][ j] == detectorNumber && atCounters[ moveIndex][ j] != CT_NONE && atCounters[ moveIndex][ j] < minAT)
                minAT = atCounters[ moveIndex][ j];
        }
        return Math.max( 0, minAT );
    }
    
    private int getStageDuration(int type, Stage stage) {
    	return (type == Var.STAGE_DURATION_MIN ? stage.getMinExt() : stage.getMaxExt());
    }
	
	/**
	 * updates the parameters need from preemption logic from parameters table
	 * note: if not in logic program, updates to 0
	 */
	public void updateParameter()
	{
		int i;
		if (_tk.isRegularProgram()) {
			updateParameters();
		} else {
			for (i = 0; i < DPT_QUANTITY; i++) {
				dptData[i][3] = 0;
			}
			for (i = 0; i < BrtMoves; i++) {
				moveMap[i][1] = 0;
			}
			for (i = 0; i < _tk.MAX_STAGES; i++) {
					stageComp[i][2] = 0;
					stageComp[i][3] = 0;
			}
		}
	}
	
	public boolean IsProgActive(int prog) {
		if(_tk.getAktProg().getNummer() == prog)
			return true;
		return false;
	}
	
	public int getParameterValue(int param)
	{
		return param;
	}
	

	/**
	 * return CycleTimeOK
	 */
	public boolean cycleTimeOK(int timeStringConst, int timeAdd) {
	    int time = getTime( timeStringConst, null );
	    int cycle = ParamSet.pFixedCycleTime_ProgAtt;
	    if (_tk.getNummer() == 2)
	    	cycle = ParamSet.pFixedCycleTime_ProgAtt2;
	    
	    return ( _tk.getCurrCycleSec + time + timeAdd < cycle );
	}

	/**
	 * updateDetMem
	 */
	// TODO update later...
	public void updateDetMem() {
	    for ( int i = 0; i < CompMoves ; i++) {
	        // move was active in prev stage
	    	Sg sg = getCompMove( i );
	    	
	    	//int time = sg.getZustZeit();
	    	if (sg.getZustand() == Zustand.GRUEN || sg.getZustand() == Zustand.GRUENBLINKUEB || sg.getZustand() == Zustand.GELB) {
	        
	            // move related extension detector is active
	            if ( !getMoveExtensionDetector(sg.getNr()) ) {
	            	{
	            		
	                    compMoveDetMem[i] = 0;
	            	}
	                
	            }
	        }
	    }
	    
	}

	/**
	 * cycleStart
	 */
	public void cycleStart() {
	    resetMemBRT( NONE );
	    for ( int i = 0; i < CompMoves ; i++)
	    {
//	    	
	    	if ( getCumulativeCycleGTreadOnly( getCompMove( i ), 2 ) > 0 ) {
	            compMoveDetMem[i]++;
	           
		    	
	    	} else {
	             compMoveDetMem[i] = 0;
	    	}
	    	
	 //        compMoveDetMem[i]++;
	    }
	}

	/**
	 * return getNormCumCycleGT
	 */
	// TODO update when next preemption exists.
	public int getNormCumCycleGT(Sg sg, int cycles) {
		int move = sg.getNr();
	    int greenTime = 1000 *Var.ONE_SEC, index  = -1;
	    
	    if ((index = getCompIndex(move)) == NONE) {
	        return greenTime;
	    }
	    
	    if ( _tk.CycleCount + 1 >= cycles && _tk.CycleCount + 1 >= getCompMoveCumPeriod( move ) &&
	    		compMoveDetMem[index] >= cycles && getMoveExtensionDetector( move ) )
	        greenTime = getCumulativeCycleGTreadOnly( sg, cycles );


	    return greenTime;
	}
	
	

	
	abstract public void InitializeDPT();
	abstract public void InitializeStages();
	abstract public void updateRedTimePhaseDemand();
	abstract public void updateParameters();
	abstract public void FirstSecondActions();
	abstract public void EverySecond();
	abstract public void PostEverySecond();
	abstract public void updateActrosAccessVariables();
	
}