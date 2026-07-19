package core.preemption;

import core.Move;
import m0547.Var;

public abstract class Preemption {
    
    public static final int CT_NONE   =  999 * Var.ONE_SEC_MS; // Current Time None (currently, no bus detected)
    public static final int UNDEFINED =   -1;
    public static final int NONE      =   -1;
    
    public abstract void init();
    
    protected abstract Move getLRTMove( int lrtIndex);
    protected abstract int getParamNumber( int paramType, int lrtIndex);
    protected abstract int getLRTDetector( int detType, int lrtIndex);
    protected abstract void forwarTimers( );
    protected abstract void calcMemL( );
    protected abstract void startCounter( int[] ct, int lrtIndex);
    protected abstract void resetCounter( int[] ct, int lrtIndex);
    protected abstract void checkDetectors( );
    protected abstract void updateActiveLRT( );
    protected abstract void calcArrivalTime( );
    protected abstract void lrtStageOpen( );
    protected abstract void lrtMoveOpen( int moveIndex);
    protected abstract void mainStageOpen( );
    protected abstract void lrtStageClosed( int stage);
    protected abstract void calcFT( );
    protected abstract void calcGhost( );
    protected abstract void calcPriorityLevel( );
    protected abstract void calcLIG( );
    protected abstract void calcCloseL( );
    protected abstract void setPreemptionTriangle( );
    protected abstract void forwardGGTGroups();
    protected abstract void switchStage( int fromStage, int toStage );
    public abstract int calcNumOfMoves();
    public abstract void runSecond( );
    protected abstract int stb( );
    protected abstract boolean isInsertedStage ( int stage );
    protected abstract boolean isLRTStage(int stage);
    protected abstract boolean isGridK( int stage );
    protected abstract void calcGTcp();
    protected abstract int getGTcpmax( int element );
    protected abstract int getGTcpmin( int element );
    protected abstract boolean extensionGood( int extensionGood );
    public abstract void emulationEventOccured( int groupNumber, int dummy );
    protected abstract boolean waitingTimeGood( int path );
    protected abstract int getTime( int path, int lrtMove );
    protected abstract int getGlobalGreenTime( int path );
    protected abstract void calcCompK( int stage, int toStage );
    protected abstract boolean isResetGGTGroups();
    protected abstract void calcCompatible();
    protected abstract int getParameterValue( int parameter );
    protected abstract int getMinStageDuration(int stage );
    protected abstract int getInterStageMoveSecond(int outStage, int inStage, int move);
    protected abstract int getInterStageDuration(int outStage, int inStage);
    public abstract void resetStagesCounters();
    public abstract void startOfCycleActions();
    
    
    protected Move getLRTPreemptionTriangle( int lrtIndex) {
        Move move = getLRTMove(lrtIndex);
        
        if (move == null) {
            return null;
        }
        
        if (move.isLRT()) {
            return move.preemptionTriangle;
        }
        return null;
    }

    protected int maxFunc( int p1, int p2 )
    {
        if ( p2 > p1 )
            return p2;
        return p1;
    }

    protected int minFunc( int p1, int p2 )
    {
        if ( p2 < p1 )
            return p2;
        return p1;
    }

    protected int maxArr( int[] arr, int arrSize, int startVal, int indexStep)
    {
        int maxVal = CT_NONE;
        for ( int i = startVal; i < arrSize; i += indexStep)
            maxVal = ( maxVal > arr[ i] ? maxVal : arr[ i]);
        return maxVal;
    }

    protected int minArr( int[] arr, int arrSize, int startVal, int indexStep)
    {
        int minVal = NONE;
        for ( int i = startVal; i < arrSize; i += indexStep)
            minVal = ( minVal < arr[ i] ? minVal : arr[ i]);
        return minVal;
    }
}
