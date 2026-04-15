package m0547;
//package m0;
//
//import core.Move;
//import core.Stage;
//import core.detectors.LRTInterface;
//import core.preemption.PreemptionJLRT;
//import vt.Phase;
//
//public class Preemptiontk1 extends PreemptionJLRT {
//    private Tk1 node;
//
//    public Preemptiontk1(Tk1 tk, int brtMoves, int brtsPerMove, int compMoves) {
//        super(tk, brtMoves, brtsPerMove, compMoves);
//        node = tk;
//        node.preempJ = this;
//    }
//
//    protected static final int    LRT20          = 0;
//    protected static final int    LRT21          = 1;
//
//    protected static final int GGT_B = 1;
//    protected static final int GGT_groupA = 0;
//
//    protected static final int EG_A0 = 3;
//    protected static final int EG_B = 0;
//    protected static final int EG_BFA0 = 2;
//    protected static final int EG_C = 1;
//
//    protected static final int WTG_A0_Bcpmin_Cmin_A0 = 0;
//    protected static final int WTG_A0_Bcpmin_Cmin_L32_DQ_A0 = 1;
//    protected static final int WTG_A0_Bcpmin_L31_DQ_A0 = 2;
//    protected static final int WTG_A0_Cmin_A0 = 4;
//    protected static final int WTG_A0_Cmin_L32_DQ_A0 = 5;
//    protected static final int WTG_A0_L30_DQ_Bcpmin_Cmin_A0 = 3;
//    protected static final int WTG_A0_L31_DQ_A0 = 6;
//    protected static final int WTG_A0_L31_DQ_BFA0min_A0 = 25;
//    protected static final int WTG_A31_A0 = 23;
//    protected static final int WTG_B_Cmin_A0 = 7;
//    protected static final int WTG_B_Cmin_L32_DQ_A0 = 8;
//    protected static final int WTG_B_L31_DQ_A0 = 9;
//    protected static final int WTG_BFA0_A0 = 11;
//    protected static final int WTG_BFA0_L32_DQ_A0 = 12;
//    protected static final int WTG_C_A0 = 13;
//    protected static final int WTG_C_BFA0_A0 = 10;
//    protected static final int WTG_C_BFA0_L32_DQ_A0 = 29;
//    protected static final int WTG_C_L32_DQ_A0 = 14;
//    protected static final int WTG_L30_DQ_A30saf_Bcpmin_Cmin_A0 = 18;
//    protected static final int WTG_L30_DQ_A30saf_Cmin_A0 = 20;
//    protected static final int WTG_L30_DQ_Bcpmin_Cmin_A0 = 16;
//    protected static final int WTG_L30_DQ_Cmin_A0 = 19;
//    protected static final int WTG_L31_DQ_A0 = 17;
//    protected static final int WTG_L31_DQ_A31saf_A0 = 21;
//    protected static final int WTG_L31_L32_DQ_A0 = 24;
//    protected static final int WTG_L32_DQ_A0 = 15;
//    protected static final int WTG_L32_DQ_A32saf_A0 = 22;
//
//    protected static final int TIME_A0_20L30 = 37;
//    protected static final int TIME_A0_20L31 = 61;
//    protected static final int TIME_A0_21L30 = 38;
//    protected static final int TIME_A0_21L31 = 62;
//    protected static final int TIME_A0_B_20L31 = 39;
//    protected static final int TIME_A0_B_21L31 = 40;
//    protected static final int TIME_A0_Bcpmin_20L31 = 4;
//    protected static final int TIME_A0_Bcpmin_21L31 = 5;
//    protected static final int TIME_A0_Bcpmin_Cmin_20L32 = 2;
//    protected static final int TIME_A0_Bcpmin_Cmin_21L32 = 3;
//    protected static final int TIME_A0_Bcpmin_Cmin_A0cpmin_20L30 = 0;
//    protected static final int TIME_A0_Bcpmin_Cmin_A0cpmin_21L30 = 1;
//    protected static final int TIME_A0_Bcpmin_Cmin_A0max_20L30 = 6;
//    protected static final int TIME_A0_Bcpmin_Cmin_A0max_21L30 = 7;
//    protected static final int TIME_A0_Cmin_20L32 = 10;
//    protected static final int TIME_A0_Cmin_21L32 = 11;
//    protected static final int TIME_A0_Cmin_A0cpmin_20L30 = 8;
//    protected static final int TIME_A0_Cmin_A0cpmin_21L30 = 9;
//    protected static final int TIME_A0_Cmin_A0min_20L30 = 12;
//    protected static final int TIME_A0_Cmin_A0min_21L30 = 13;
//    protected static final int TIME_A0_L30_Bcpmin_Cmin_A0 = 85;
//    protected static final int TIME_B_20L31 = 41;
//    protected static final int TIME_B_21L31 = 42;
//    protected static final int TIME_B_C = 68;
//    protected static final int TIME_B_C_A0cpmin_20L30 = 77;
//    protected static final int TIME_B_Cmin_20L32 = 18;
//    protected static final int TIME_B_Cmin_21L32 = 19;
//    protected static final int TIME_B_Cmin_A0_20L30 = 20;
//    protected static final int TIME_B_Cmin_A0_21L30 = 21;
//    protected static final int TIME_B_Cmin_A0cpmin_20L30 = 16;
//    protected static final int TIME_B_Cmin_A0cpmin_21L30 = 17;
//    protected static final int TIME_B_Cmin_A0cpmin_Bcpmin_20L31 = 14;
//    protected static final int TIME_B_Cmin_A0cpmin_Bcpmin_21L31 = 15;
//    protected static final int TIME_B_L31_A0 = 86;
//    protected static final int TIME_BFA0_20L32 = 49;
//    protected static final int TIME_BFA0_21L32 = 50;
//    protected static final int TIME_BFA0_A0_20L30 = 51;
//    protected static final int TIME_BFA0_A0_21L30 = 52;
//    protected static final int TIME_BFA0_A0cpmin_20L30 = 26;
//    protected static final int TIME_BFA0_A0cpmin_21L30 = 27;
//    protected static final int TIME_BFA0_A0cpmin_Bcpmin_20L31 = 24;
//    protected static final int TIME_BFA0_A0cpmin_Bcpmin_21L31 = 25;
//    protected static final int TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32 = 22;
//    protected static final int TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32 = 23;
//    protected static final int TIME_C_20L32 = 43;
//    protected static final int TIME_C_21L32 = 44;
//    protected static final int TIME_C_A0 = 69;
//    protected static final int TIME_C_A0cpmin_20L30 = 32;
//    protected static final int TIME_C_A0cpmin_21L30 = 33;
//    protected static final int TIME_C_A0cpmin_Bcpmin_20L31 = 30;
//    protected static final int TIME_C_A0cpmin_Bcpmin_21L31 = 31;
//    protected static final int TIME_C_A0cpmin_Bcpmin_Cmin_20L32 = 28;
//    protected static final int TIME_C_A0cpmin_Bcpmin_Cmin_21L32 = 29;
//    protected static final int TIME_C_L32_A0 = 87;
//    protected static final int TIME_L30_A30_Bcpmin_C_A0 = 80;
//    protected static final int TIME_L30_A30_Cmin_20L32 = 79;
//    protected static final int TIME_L30_A30saf_Bcpmin_20L31 = 53;
//    protected static final int TIME_L30_A30saf_Bcpmin_21L31 = 54;
//    protected static final int TIME_L30_A30saf_Cmin_20L32 = 55;
//    protected static final int TIME_L30_A30saf_Cmin_21L32 = 56;
//    protected static final int TIME_L30_B = 67;
//    protected static final int TIME_L30_Bcpmin_20L31 = 45;
//    protected static final int TIME_L30_Bcpmin_21L31 = 46;
//    protected static final int TIME_L30_Bcpmin_C_A0 = 78;
//    protected static final int TIME_L30_Bcpmin_C_A0_20L30 = 65;
//    protected static final int TIME_L30_C = 72;
//    protected static final int TIME_L30_C_20L32 = 71;
//    protected static final int TIME_L30_C_A0_20L30 = 70;
//    protected static final int TIME_L30_Cmin_20L32 = 63;
//    protected static final int TIME_L30_Cmin_21L32 = 64;
//    protected static final int TIME_L31_A0 = 66;
//    protected static final int TIME_L31_A0cpmin_20L30 = 47;
//    protected static final int TIME_L31_A0cpmin_21L30 = 48;
//    protected static final int TIME_L31_A0cpmin_Bcpmin_20L31 = 73;
//    protected static final int TIME_L31_A31_A0 = 84;
//    protected static final int TIME_L31_A31_A0cpmin_20L30 = 83;
//    protected static final int TIME_L31_A31saf_A0cpmin_20L30 = 57;
//    protected static final int TIME_L31_A31saf_A0cpmin_21L30 = 58;
//    protected static final int TIME_L32_A0 = 34;
//    protected static final int TIME_L32_A0_Bcpmin_20L31 = 76;
//    protected static final int TIME_L32_A0cpmin_20L30 = 35;
//    protected static final int TIME_L32_A0cpmin_21L30 = 36;
//    protected static final int TIME_L32_A0cpmin_Bcpmin_20L31 = 75;
//    protected static final int TIME_L32_A0cpmin_Bcpmin_C_20L32 = 74;
//    protected static final int TIME_L32_A32_A0 = 82;
//    protected static final int TIME_L32_A32_A0cpmin_20L30 = 81;
//    protected static final int TIME_L32_A32saf_A0cpmin_20L30 = 59;
//    protected static final int TIME_L32_A32saf_A0cpmin_21L30 = 60;
//
//    protected static final int STAGE_A0 = 8;
//    protected static final int STAGE_A30 = 13;
//    protected static final int STAGE_A31 = 16;
//    protected static final int STAGE_A32 = 19;
//    protected static final int STAGE_B = 21;
//    protected static final int STAGE_BFA0 = 22;
//    protected static final int STAGE_C = 27;
//    protected static final int STAGE_L30 = 85;
//    protected static final int STAGE_L31 = 89;
//    protected static final int STAGE_L32 = 94;
//    
//    protected int GTcpminB;
//    protected int GTcpmaxB;
//    protected int GTcpminA0;
//    protected int GTcpmaxA0;
//    protected int Comp1B;
//    protected int Comp2B;
//    protected boolean memEgB;
//    protected int Comp1A0;
//    protected int Comp2A0;
//    protected boolean memEgA0;
//    protected int GGTGroupgroupA;
//
//    protected boolean Flag_L30_last_cycle;
//    protected boolean Flag_C;
//    protected int RT_HH101_End_4;
//    protected boolean iscounting_RT_HH101_End_4;
//    protected boolean Flag_mem_L30;
//    protected int compatible;
//    protected int Counter;
//    protected boolean iscounting_Counter;
//    protected boolean GoToCata;
//    protected int HH10_End_5;
//    protected boolean iscounting_HH10_End_5;
//    protected boolean RT_HH101_sync2;
//    protected boolean HH10_OpenLRT21;
//    protected int HH10_OpenLRT20;
//    protected boolean iscounting_HH10_OpenLRT20;
//    protected boolean Active_Q5;
//    protected boolean HH11_DF20;
//    protected boolean HH_P_C_L32;
//    protected boolean Flag_L32_A0;
//    protected boolean HH10_Sync1;
//    protected boolean Flag_L31;
//    protected boolean Q5_A0L30_A0L31_BFA0L32;
//    protected boolean Flag_L30;
//    protected boolean Flag_L32;
//    protected boolean HHDillema_Zone;
//    protected boolean HH10_D8;
//    protected boolean Flag_L32_21_delay;
//    protected boolean HH10_D7;
//    protected int L32_A0;
//    protected boolean iscounting_L32_A0;
//
//    boolean callOnce = true;
//    protected void nodeOffline() {
//        if(callOnce && node.startmark){
//
//            callOnce = false;
//        }
//    }
//
//    protected void Init() {
//        super.Init();
//        memL = 0;
//        memF = false;
//        memS = false;
//        LIG1 = false;
//        LIG2 = false;
//        LIG  = false;
//        DF = false;
//        inserted = false;
//        Ghost = false;
//        GTcpminB = 0;
//        GTcpmaxB = 0;
//        GTcpminA0 = 0;
//        GTcpmaxA0 = 0;
//        Comp1B = 0;
//        Comp2B = 0;
//        Comp1A0 = 0;
//        Comp2A0 = 0;
//        GGTGroupgroupA = 0;
//
//        Flag_L30_last_cycle = false;
//        Flag_C = false;
//        RT_HH101_End_4 = 0;
//        iscounting_RT_HH101_End_4 = false;
//        Flag_mem_L30 = false;
//        compatible = 0;
//        Counter = 0;
//        iscounting_Counter = false;
//        GoToCata = false;
//        HH10_End_5 = 0;
//        iscounting_HH10_End_5 = false;
//        RT_HH101_sync2 = false;
//        HH10_OpenLRT21 = false;
//        HH10_OpenLRT20 = 0;
//        iscounting_HH10_OpenLRT20 = false;
//        Active_Q5 = false;
//        HH11_DF20 = false;
//        HH_P_C_L32 = false;
//        Flag_L32_A0 = false;
//        HH10_Sync1 = false;
//        Flag_L31 = false;
//        Q5_A0L30_A0L31_BFA0L32 = false;
//        Flag_L30 = false;
//        Flag_L32 = false;
//        HHDillema_Zone = false;
//        HH10_D8 = false;
//        Flag_L32_21_delay = false;
//        HH10_D7 = false;
//        L32_A0 = 0;
//        iscounting_L32_A0 = false;
//        
//
//        // Logic Group: 1, Order: 1
//        HH10_End_5 = -1;
//        // Logic Group: 1, Order: 2
//        setElementNotActive( node.HH10_OpenLRT21_O );
//        // Logic Group: 1, Order: 3
//        Flag_L30 = false;
//        // Logic Group: 1, Order: 4
//        Flag_L32_21_delay = false;
//        // Logic Group: 1, Order: 5
//        Flag_mem_L30 = false;
//        // Logic Group: 1, Order: 6
//        Flag_L31 = false;
//        // Logic Group: 1, Order: 7
//        Flag_L32 = false;
//        // Logic Group: 1, Order: 8
//        Flag_C = false;
//        // Logic Group: 1, Order: 9
//        Flag_L30_last_cycle = false;
//        // Logic Group: 1, Order: 10
//        L32_A0 = -1;
//    }
//
//    public void FirstSecondActions() {
//        if (node.isRegularProgram()) {
//            node.resetCumulativeCycles();
//        }
//    }
//
//    public boolean isPreemptionProgram() {
//        if (!node.isRegularProgram() && !node.isInMaintenance())
//            return false;
//        return getParameterValue(ParametersIndex.PARAM_OPERMODE) == 2;
//    }
//
//    // TODO Modify in case Catastrophe program required special condition except GoToCata 
//    // Defualt return should be FALSE
//    protected boolean goToCataSpecialCondition() {
//        //	return !node.INFO_HH02_IN.IsActive();
//        return false;
//    }
//
//    public void EverySecond() {
//        if (!node.isFullSecond())
//            return;
//        // must update according to the order of "compMap"
//        // compDet[0] = !_tk.gap_e2;
//        
//
//        processPulseAndDetectorChanges();
//        preactionsEverySecond();
//        processMovesAndStageChanges();
//        emulationEventOccured();
//        updateActrosAccessVariables();		
//    }
//    
//    private void processPulseAndDetectorChanges() {
//        if (iscounting_HH10_End_5) {
//            HH10_End_5++;
//        }
//        if (iscounting_RT_HH101_End_4) {
//            RT_HH101_End_4++;
//        }
//        if (iscounting_Counter) {
//            Counter++;
//        }
//        
//        // Logic Group: 2, Order: 1
//        if ( isElementActivated( node.HH10_End_5_O ) )
//        {
//            iscounting_HH10_End_5 = true;
//            HH10_End_5 = 1;
//        }
//        // Logic Group: 2, Order: 2
//        if ( isElementDeactivated( node.HH10_End_5_O ) )
//        {
//            iscounting_HH10_End_5 = false;
//            HH10_End_5 = -1;
//        }
//        // Logic Group: 2, Order: 3
//        if ( isElementDeactivated( node.HH101_End_4_I ) )
//        {
//            iscounting_RT_HH101_End_4 = true;
//            RT_HH101_End_4 = 1;
//        }
//        // Logic Group: 2, Order: 4
//        if ( isElementActivated( node.HH101_End_4_I ) )
//        {
//            iscounting_RT_HH101_End_4 = false;
//            RT_HH101_End_4 = -1;
//        }
//        // Logic Group: 2, Order: 5
//        if ( isElementActivated( node.HH101_Sync2_I ) )
//        {
//            RT_HH101_sync2 = true;
//        }
//        // Logic Group: 2, Order: 6
//        if ( isElementActivated( node.P_C_L32 ) )
//        {
//            HH_P_C_L32 = true;
//        }
//        // Logic Group: 2, Order: 7
//        if ( isElementActivated( node.d_7 ) )
//        {
//            HH10_D7 = true;
//        }
//        // Logic Group: 2, Order: 8
//        if ( isElementActivated( node.d_8 ) )
//        {
//            HH10_D8 = true;
//        }
//        // Logic Group: 2, Order: 9
//        Active_Q5 = isElementActive( node.q_5 );
//        // Logic Group: 2, Order: 10
//        HHDillema_Zone = isElementActive( node.HH11_Dillema_Zone_I );
//    }
//
//    private void preactionsEverySecond() {
//        // Logic Group: 3, Order: 1
//        if ( ( ( ( ( Flag_L30 == true ) && ( userDefined_Condition_L30() == false ) ) || ( ( Flag_L31 == true ) && ( userDefined_Condition_L31() == false ) ) ) || ( ( Flag_L32 == true ) && ( userDefined_Condition_L32() == false ) ) ) )
//        {
//            setElementActive( node.HH10_DelayLRT20_O );
//        }
//        // Logic Group: 3, Order: 2
//        if ( ( ( ( ( userDefined_train_not_Delay20() || ( isElementActive( node.PhL30 ) && ( userDefined_WTGL30() == false ) ) ) || ( isElementActive( node.PhL31 ) && ( userDefined_WTG_L31() == false ) ) ) || ( isElementActive( node.PhL32 ) && ( userDefined_WTGL32_2() == false ) ) ) || ( isElementActive( node.PhA0 ) && ( userDefined_WTGL30_2() == false ) ) ) )
//        {
//            setElementNotActive( node.HH10_DelayLRT20_O );
//        }
//        // Logic Group: 3, Order: 3
//        if ( ( ( Active_Q5 == true ) && ( getElementElapsedTime( node.q_5 ) >= getParameterValue( ParametersIndex.PARAM_X4 ) ) ) )
//        {
//            Q5_A0L30_A0L31_BFA0L32 = true;
//        }
//        // Logic Group: 3, Order: 4
//        if ( ( ( Active_Q5 == false ) && ( getElementElapsedRedTime( node.q_5 ) >= getParameterValue( ParametersIndex.PARAM_X6 ) ) ) )
//        {
//            Q5_A0L30_A0L31_BFA0L32 = false;
//        }
//        // Logic Group: 3, Order: 5
//        setElementActive( node.Info_HH10_to_HH101_O );
//        // Logic Group: 3, Order: 6
//        if ( ( GoToCata == false ) )
//        {
//            setElementActive( node.Info_HH10_to_HH11_O );
//        }
//        // Logic Group: 3, Order: 7
//        if ( userDefined_Dillema_Zone_HH10() )
//        {
//            setElementActive( node.HH10_Dillema_Zone_O );
//        }
//        // Logic Group: 3, Order: 8
//        if ( ( ( Counter <= getInterStageDuration( STAGE_L30, STAGE_B ) ) && userDefined_Delay_B() ) )
//        {
//            setElementActive( node.HH10_DelayLRT20_O );
//        }
//        // Logic Group: 3, Order: 9
//        if ( userDefined_WTGL32_21() )
//        {
//            setElementActive( node.HH10_DelayLRT21_O );
//        }
//        // Logic Group: 3, Order: 10
//        if ( isElementActive( node.PhA0 ) )
//        {
//            setElementNotActive( node.HH10_DelayLRT21_O );
//        }
//        // Logic Group: 3, Order: 11
//        if ( ( ( ( ( isElementActive( node.k20.dm.outOfOrderInput) || isElementActive( node.k21.dm.outOfOrderInput ) ) || isElementActive( node.k20.dq.outOfOrderInput ) ) || isElementActive( node.k21.dq.outOfOrderInput ) ) || ( ! isElementActive( node.SITOK ) ) ) )
//        {
//            GoToCata = true;
//        }
//    }
//
//    private void processMovesAndStageChanges() {
//        // Logic Group: 5, Order: 2
//        if ( isCompatibleStage( node.getPrevStage() ) && isElementDeactivated( node.getPrevStage() ) )
//        {
//            compatible = getGlobalGreenTime( GGT_groupA );
//        }
//    }
//
//    private void emulationEventOccured() {
//    }
//
//    public void processMovesChanges() {
//        if (!node.isFullSecond())
//            return;
//    }
//
//    protected void moveActivated(Move move) {
//
//    }
//
//    protected void moveDeactivated(Move move) {
//
//    }
//
//    public void CycleStarted() {
//        // Logic Group: 5, Order: 13
//        setElementNotActive( node.HH10_Sync1_O );
//        // Logic Group: 5, Order: 14
//        RT_HH101_sync2 = false;
//    }
//
//    public void PostEverySecond() {
//        // Logic Group: 5, Order: 1
//        if ( ! isElementActive( node.k5 ) )
//            setElementActive( node.HH10_End_5_O );
//        else
//            setElementNotActive( node.HH10_End_5_O );
//        // Logic Group: 5, Order: 3
//        if ( ! isElementActive( node.k6 ) )
//            setElementActive( node.HH10_End_6_O );
//        else
//            setElementNotActive( node.HH10_End_6_O );
//
//        // Logic Group: 5, Order: 20
//        if ( isElementDeactivated( node.k20 ) )
//        {
//            setElementNotActive( node.HH10_OpenLRT20_v2_O );
//        }
//        // Logic Group: 5, Order: 31
//        if ( isElementActivated( node.pa ) )
//        {
//            Flag_mem_L30 = false;
//        }
//        // Logic Group: 5, Order: 34
//        if ( isElementActivated( node.pd ) )
//        {
//            Flag_L32_21_delay = false;
//        }
//        // Logic Group: 5, Order: 62
//        if ( isElementActivated( node.pd ) )
//        {
//            if ( Flag_mem_L30 )
//            {
//                Flag_L30_last_cycle = true;
//            }
//        }
//        // Logic Group: 5, Order: 52
//        if ( isElementDeactivated( node.k20 ) )
//        {
//            HH11_DF20 = false;
//        }
//        // Logic Group: 5, Order: 53
//        if ( isElementDeactivated( node.k20 ) )
//        {
//            setElementNotActive( node.HH10_Dillema_Zone_O );
//        }
//        // Logic Group: 5, Order: 59
//        if ( isElementDeactivated( node.k20 ) )
//        {
//            setElementNotActive( node.HH10_OpenLRT21_O );
//        }
//        // Logic Group: 5, Order: 63
//        if ( isElementActivated( node.k20 ) )
//        {
//            Flag_L30_last_cycle = false;
//        }
//    }
//
//    public Move getLRTMove(int lrtIndex) {
//        switch (lrtIndex % LRT_NUM_MOVES) {
//            case LRT20:
//                return node.k20;
//            case LRT21:
//                return node.k21;
////            case LRT22:
////                return node.k22;
////            case LRT23:
////                return node.k23;
////            case LRT25:
////                return node.k25;
//            default:
//                return null;
//        }
//    }
//
//    protected void calcCompatible() {
//    }
//
//    protected boolean isGridK(Stage stage) {
//        return false;
//    }
//
//    protected void setPreemptionTriangle() {
//    }
//
//    public void updateParameters() {
//    }
//
//    public int getCompMoveCumPeriod(int move) {
//        switch (move) {
//            // case 1: // _tk.k1.getNr()
//            // return ParamSet.pCumPeriod_1;
//        }
//        return UNDEFINED;
//    }
//
//    public void updateActrosAccessVariables() {
//        // counters of closest arrival time
//        //        tLxLRT20 = tLx[LRT20];
//        //        tLxLRT21 = tLx[LRT21];
//        //        tPxLRT20 = tPx[LRT20];
//        //        tPxLRT21 = tPx[LRT21];
//        //        tMxLRT20 = tMx[LRT20];
//        //        tMxLRT21 = tMx[LRT21];
//        //        tSxLRT20 = tSx[LRT20];
//        //        tSxLRT21 = tSx[LRT21];
//        //        ctQjLRT20 = ctQj[LRT20];
//        //        ctQjLRT21 = ctQj[LRT21];
//        //        ATjLRT20 = ATj[LRT20];
//        //        ATjLRT21 = ATj[LRT21];
//        //        PLjLRT20 = PLj[LRT20];
//        //        PLjLRT21 = PLj[LRT21];
//        //        tLjLRT20 = tLj[LRT20];
//        //        tLjLRT21 = tLj[LRT21];
//        //        tPjLRT20 = tPj[LRT20];
//        //        tPjLRT21 = tPj[LRT21];
//        //        tMjLRT20 = tMj[LRT20];
//        //        tMjLRT21 = tMj[LRT21];
//        //        tGjLRT20 = tGj[LRT20];
//        //        tGjLRT21 = tGj[LRT21];
//        //        tSjLRT20 = tSj[LRT20];
//        //        tSjLRT21 = tSj[LRT21];
//        //        SjLRT20 = Sj[LRT20];
//        //        SjLRT21 = Sj[LRT21];
//        //        FjLRT20 = Fj[LRT20];
//        //        FjLRT21 = Fj[LRT21];
//        //        FTjLRT20 = FTj[LRT20];
//        //        FTjLRT21 = FTj[LRT21];
//        //        memLRTMoveOpenLRT20 = memLRTMoveOpen[LRT20];
//        //        memLRTMoveOpenLRT21 = memLRTMoveOpen[LRT21];
//
//        if (Var.tk1.isRegularProgram()) {
//            // CycleLength = getParameterValue(ParametersIndex.PARAM_LCMAX);
//        }
//    }
//
//    public int getTimeJ(int timeConst) {
//        return getTime(timeConst, null);
//    }
//
//    public int getTime(int timeConst, Move ptMove) {
//        if (ptMove.getNr() == node.k20.getNr())
//            return getTime(timeConst, LRT20);
//        else
//            return getTime(timeConst, LRT21);
//    }
//
//    public void InitializePreemption() {
//        node.prevStage = node.MainPhase[0];
//    }
//
//    public ParametersIndex getParamNumber(
//            PreemptionDetectorParameterType paramType, int lrtIndex) {
//
//        Move lrtMove = getLRTMove(lrtIndex);
//        if (lrtMove == null)
//            return null;
//
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TL
//                .getParameterType()) {
////                        if (lrtMove.equals(node.k20))
////                            return ParametersIndex.PARAM_TL20;
//                        if (lrtMove.equals(node.k21))
//                            return ParametersIndex.PARAM_TL21;
//            //            if (lrtMove.equals(node.k22))
//            //                return ParametersIndex.PARAM_TL22;
//            //            if (lrtMove.equals(node.k23))
//            //                return ParametersIndex.PARAM_TL23;
//            //            if (lrtMove.equals(node.k25))
//            //                return ParametersIndex.PARAM_TL25;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TP
//                .getParameterType()) {
//                        if (lrtMove.equals(node.k20))
//                            return ParametersIndex.PARAM_TP20;
//                        if (lrtMove.equals(node.k21))
//                            return ParametersIndex.PARAM_TP21;
//            //            if (lrtMove.equals(node.k22))
//            //                return ParametersIndex.PARAM_TP22;
//            //            if (lrtMove.equals(node.k23))
//            //                return ParametersIndex.PARAM_TP23;
//            //            if (lrtMove.equals(node.k25))
//            //                return ParametersIndex.PARAM_TP25;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TQ
//                .getParameterType()) {
//            if (lrtMove.equals(node.k20))
//                return ParametersIndex.PARAM_TQ20;
//            if (lrtMove.equals(node.k21))
//                return ParametersIndex.PARAM_TQ21;
////            if (lrtMove.equals(node.k22))
////                return ParametersIndex.PARAM_TQ22;
////            if (lrtMove.equals(node.k23))
////                return ParametersIndex.PARAM_TQ23;
////            if (lrtMove.equals(node.k25))
////                return ParametersIndex.PARAM_TQ25;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TL_MAX
//                .getParameterType()) {
////                        if (lrtMove.equals(node.k20))
////                            return ParametersIndex.PARAM_TL20MAX;
//                        if (lrtMove.equals(node.k21))
//                            return ParametersIndex.PARAM_TL21MAX;
//            //            if (lrtMove.equals(node.k22))
//            //                return ParametersIndex.PARAM_TL22MAX;
//            //            if (lrtMove.equals(node.k23))
//            //                return ParametersIndex.PARAM_TL23MAX;
//            //            if (lrtMove.equals(node.k25))
//            //                return ParametersIndex.PARAM_TL25MAX;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TP_MAX
//                .getParameterType()) {
//                        if (lrtMove.equals(node.k20))
//                            return ParametersIndex.PARAM_TP20MAX;
//                        if (lrtMove.equals(node.k21))
//                            return ParametersIndex.PARAM_TP21MAX;
//            //            if (lrtMove.equals(node.k22))
//            //                return ParametersIndex.PARAM_TP22MAX;
//            //            if (lrtMove.equals(node.k23))
//            //                return ParametersIndex.PARAM_TP23MAX;
//            //            if (lrtMove.equals(node.k25))
//            //                return ParametersIndex.PARAM_TP25MAX;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TM_MAX
//                .getParameterType()) {
//                        if (lrtMove.equals(node.k20))
//                            return ParametersIndex.PARAM_TM20MAX;
//                        if (lrtMove.equals(node.k21))
//                            return ParametersIndex.PARAM_TM21MAX;
//            //            if (lrtMove.equals(node.k22))
//            //                return ParametersIndex.PARAM_TM22MAX;
//            //            if (lrtMove.equals(node.k23))
//            //                return ParametersIndex.PARAM_TM23MAX;
//            //            if (lrtMove.equals(node.k25))
//            //                return ParametersIndex.PARAM_TM25MAX;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TG_MAX
//                .getParameterType()) {
//            if (lrtMove.equals(node.k20))
//                return ParametersIndex.PARAM_TGMAX_20;
//            if (lrtMove.equals(node.k21))
//                return ParametersIndex.PARAM_TGMAX_21;
////            if (lrtMove.equals(node.k22))
////                return ParametersIndex.PARAM_TGMAX_22;
////            if (lrtMove.equals(node.k23))
////                return ParametersIndex.PARAM_TGMAX_23;
////            if (lrtMove.equals(node.k25))
////                return ParametersIndex.PARAM_TGMAX_25;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TG_MIN
//                .getParameterType()) {
//                        if (lrtMove.equals(node.k20))
//                            return ParametersIndex.PARAM_TGMIN_20;
//                        if (lrtMove.equals(node.k21))
//                            return ParametersIndex.PARAM_TGMIN_21;
//            //            if (lrtMove.equals(node.k22))
//            //                return ParametersIndex.PARAM_TG22MIN;
//            //            if (lrtMove.equals(node.k23))
//            //                return ParametersIndex.PARAM_TG23MIN;
//            //            if (lrtMove.equals(node.k25))
//            //                return ParametersIndex.PARAM_TG25MIN;
//        }
//        if (paramType.getParameterType() == PreemptionDetectorParameterType.TYPE_TS_MAX
//                .getParameterType()) {
//            if (lrtMove.equals(node.k20))
//                return ParametersIndex.PARAM_TS20MAX;
//            if (lrtMove.equals(node.k21))
//                return ParametersIndex.PARAM_TS21MAX;
////            if (lrtMove.equals(node.k22))
////                return ParametersIndex.PARAM_TS22MAX;
////            if (lrtMove.equals(node.k23))
////                return ParametersIndex.PARAM_TS23MAX;
////            if (lrtMove.equals(node.k25))
////                return ParametersIndex.PARAM_TS25MAX;
//        }
//        return null;
//    }
//
//    public LRTInterface getLRTDetector(PreemptionDetectorType detType,
//            int lrtIndex) {
//        Move lrtMove = getLRTMove(lrtIndex);
//        if (lrtMove == null)
//            return null;
//        return lrtMove.getLRTDetector(detType);
//    }
//
//    protected Move getLRTPreemptionTriangle(int lrtIndex) {
//        Move lrtMove = getLRTMove(lrtIndex);
//        if (lrtMove == null)
//            throw null;
//        if (lrtMove.getId() == node.k20.getId())
//            return node.k20.preemptionTriangle;
//        if (lrtMove.getId() == node.k21.getId())
//            return node.k21.preemptionTriangle;
////        if (lrtMove.getId() == node.k22.getId())
////            return node.k22.preemptionTriangle;
////        if (lrtMove.getId() == node.k23.getId())
////            return node.k23.preemptionTriangle;
////        if (lrtMove.getId() == node.k25.getId())
////            return node.k25.preemptionTriangle;
//        return null;
//    }
//
//    protected boolean isResetGGTGroups() {
//        return node.getCurrCycleSec() == 1;
//    }
//    
//    protected void calcCompK( Stage stage, Stage toStage )
//    {
//        if ( stage.getNummer() == node.PhB.getNummer() )
//        {
//            Comp2B = Comp1B;
//            Comp1B = 0;
//            if ( !extensionGood( EG_B ) && stage.getCurrStageDuration( ) < getParameterValue( ParametersIndex.PARAM_GTNEED_B ) )
//                Comp1B = getParameterValue( ParametersIndex.PARAM_GTNEED_B ) - stage.getCurrStageDuration( );
//        }
//        
//        if ( stage.getNummer() == node.PhA0.getNummer() )
//        {
//            Comp2A0 = Comp1A0;
//            Comp1A0 = 0;
//            if ( !extensionGood( EG_A0 ) && GGTGroupgroupA < getParameterValue( ParametersIndex.PARAM_GTNEED_A0 ) )
//                Comp1A0 = getParameterValue( ParametersIndex.PARAM_GTNEED_A0 ) - GGTGroupgroupA;
//        }
//        
//        if (toStage.isMainStage)
//        {
//        }
//    }
//
//
//    protected void calcGTcp()
//    {
//        GTcpmaxB  = getParameterValue( ParametersIndex.PARAM_GTMAX_B ) + Comp1B;
//        GTcpmaxA0 = getParameterValue( ParametersIndex.PARAM_GTMAX_A0 ) + Comp1A0;
//
//        if ( isGridK( node.PhB ) )
//            GTcpminB = getParameterValue( ParametersIndex.PARAM_GTNEED_B );
//        else if ( getParameterValue( ParametersIndex.PARAM_PEAK ) == 1 )
//            GTcpminB = getParameterValue( ParametersIndex.PARAM_GTMIN_B ) + Comp1B;
//        else if ( Comp1B > 0 && Comp2B > 0)
//            GTcpminB = getParameterValue( ParametersIndex.PARAM_GTMIN_B ) + maxFunc( Comp1B, Comp2B);
//        else
//            GTcpminB = getParameterValue( ParametersIndex.PARAM_GTMIN_B );
//
//        if ( isGridK( node.PhA0 ) )
//            GTcpminA0 = getParameterValue( ParametersIndex.PARAM_GTNEED_A0 );
//        else if ( getParameterValue( ParametersIndex.PARAM_PEAK ) == 1 )
//            GTcpminA0 = getParameterValue( ParametersIndex.PARAM_GTMIN_A0 ) + Comp1A0;
//        else if ( Comp1A0 > 0 && Comp2A0 > 0)
//            GTcpminA0 = getParameterValue( ParametersIndex.PARAM_GTMIN_A0 ) + maxFunc( Comp1A0, Comp2A0);
//        else
//            GTcpminA0 = getParameterValue( ParametersIndex.PARAM_GTMIN_A0 );
//    }
//        
//    public boolean extensionGood( int extensionGood )
//    {
//        boolean result = true;
//
//        if ( extensionGood == EG_A0 ) {
//            result = ( ( ( ( ( isElementActive( node.e_5 )==false )&&( isElementActive( node.e_6 )==false ) )&&( getElementElapsedTime( node.PhA0 )>=getParameterValue( ParametersIndex.PARAM_GTCOMF_A0 ) ) )||( getGlobalGreenTime( GGT_groupA )>=getGTcpmax( node.PhA0 ) ) ) );
//        } else if ( extensionGood == EG_B ) {
//            result = ( ( ( ( ( isElementActive( node.e_6 )==false )&&( isElementActive( node.e_7 )==false ) )&&( getGlobalGreenTime( GGT_B )>=getParameterValue( ParametersIndex.PARAM_GTCOMF_B ) ) )||( getGlobalGreenTime( GGT_B )>=getGTcpmax( node.PhB ) ) ) );
//        } else if ( extensionGood == EG_BFA0 ) {
//            result = ( ( ( isElementActive( node.Info_HH101_to_H10_I )&&RT_HH101_sync2 )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) );
//        } else if ( extensionGood == EG_C ) {
//            result = ( ( getElementElapsedTime( node.PhC )>=getParameterValue( ParametersIndex.PARAM_GTCOMF_C ) ) );
//        } else {
//            return false;
//        }
//
//        return result;
//    }
//
//    protected void forwardGGTGroups()
//    {
//        if ( isResetGGTGroups() )
//        {
//            GGTGroupgroupA = 0;
//        }
//        
//        if ( node.isInInterStage() )
//        {
//            if ( node.getPrevStage().getNummer() == node.PhBFA0.getNummer() && node.getCurrStage().getNummer() == node.PhA0.getNummer() )
//                GGTGroupgroupA++;
//        }
//        else
//        {
//            if ( node.getCurrStage().getNummer() == node.PhBFA0.getNummer() )
//                GGTGroupgroupA++;
//            else if ( node.getCurrStage().getNummer() == node.PhA0.getNummer() )
//                GGTGroupgroupA++;
//        }
//    }
//
//    protected int getGTcpmax( Stage element )
//    {
//        if ( element.getNummer() == node.PhB.getNummer() )
//            return GTcpmaxB;
//
//        if ( element.getNummer() == node.PhA0.getNummer() )
//            return GTcpmaxA0;
//            
//        return 0;
//    }
//    
//    protected int getGTcpmin( Stage element )
//    {
//        if ( element.getNummer() == node.PhB.getNummer() )
//            return GTcpminB;
//
//        if ( element.getNummer() == node.PhA0.getNummer() )
//            return GTcpminA0;
//            
//        return 0;
//    }
//    
//    protected int getGlobalGreenTime( int path )
//    {
//        if ( path == GGT_B )
//        {
//            return node.PhB.getCurrStageDuration();
//        }
//
//        if ( path == GGT_groupA )
//        {
//            return GGTGroupgroupA;
//        }
//        
//        return UNDEFINED;
//    }
//    
//    protected int getTime( int path, int lrtMove )
//    {
//        int time = 0;
//        if ( path == TIME_A0_20L30 )
//        {
//            time = time
//            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//        }
//        else
//            if ( path == TIME_A0_20L31 )
//            {
//                time = time
//                + getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( LRT20 ) );
//            }
//            else
//                if ( path == TIME_A0_21L30 )
//                {
//                    time = time
//                    + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                }
//                else
//                    if ( path == TIME_A0_21L31 )
//                    {
//                        time = time
//                        + getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( LRT21 ) );
//                    }
//                    else
//                        if ( path == TIME_A0_B_20L31 )
//                        {
//                            time = time
//                            + getInterStageDuration( STAGE_A0, STAGE_B ) + getMinStageDuration( STAGE_B )
//                            + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                        }
//                        else
//                            if ( path == TIME_A0_B_21L31 )
//                            {
//                                time = time
//                                + getInterStageDuration( STAGE_A0, STAGE_B ) + getMinStageDuration( STAGE_B )
//                                + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                            }
//                            else
//                                if ( path == TIME_A0_Bcpmin_20L31 )
//                                {
//                                    time = time
//                                    + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                    + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                }
//                                else
//                                    if ( path == TIME_A0_Bcpmin_21L31 )
//                                    {
//                                        time = time
//                                        + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                        + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                                    }
//                                    else
//                                        if ( path == TIME_A0_Bcpmin_Cmin_20L32 )
//                                        {
//                                            time = time
//                                            + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                            + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                            + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                        }
//                                        else
//                                            if ( path == TIME_A0_Bcpmin_Cmin_21L32 )
//                                            {
//                                                time = time
//                                                + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                            }
//                                            else
//                                                if ( path == TIME_A0_Bcpmin_Cmin_A0cpmin_20L30 )
//                                                {
//                                                    time = time
//                                                    + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                    + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                    + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                }
//                                                else
//                                                    if ( path == TIME_A0_Bcpmin_Cmin_A0cpmin_21L30 )
//                                                    {
//                                                        time = time
//                                                        + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                        + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                        + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                    }
//                                                    else
//                                                        if ( path == TIME_A0_Bcpmin_Cmin_A0max_20L30 )
//                                                        {
//                                                            time = time
//                                                            + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                            + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                            + getInterStageDuration( STAGE_C, STAGE_A0 ) + getParameterValue( ParametersIndex.PARAM_GTMAX_A0 )
//                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                        }
//                                                        else
//                                                            if ( path == TIME_A0_Bcpmin_Cmin_A0max_21L30 )
//                                                            {
//                                                                time = time
//                                                                + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                + getInterStageDuration( STAGE_C, STAGE_A0 ) + getParameterValue( ParametersIndex.PARAM_GTMAX_A0 )
//                                                                + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                            }
//                                                            else
//                                                                if ( path == TIME_A0_Cmin_20L32 )
//                                                                {
//                                                                    time = time
//                                                                    + getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                    + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                }
//                                                                else
//                                                                    if ( path == TIME_A0_Cmin_21L32 )
//                                                                    {
//                                                                        time = time
//                                                                        + getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                        + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                    }
//                                                                    else
//                                                                        if ( path == TIME_A0_Cmin_A0cpmin_20L30 )
//                                                                        {
//                                                                            time = time
//                                                                            + getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                            + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                        }
//                                                                        else
//                                                                            if ( path == TIME_A0_Cmin_A0cpmin_21L30 )
//                                                                            {
//                                                                                time = time
//                                                                                + getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                            }
//                                                                            else
//                                                                                if ( path == TIME_A0_Cmin_A0min_20L30 )
//                                                                                {
//                                                                                    time = time
//                                                                                    + getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + getParameterValue( ParametersIndex.PARAM_GTMIN_A0 )
//                                                                                    + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                }
//                                                                                else
//                                                                                    if ( path == TIME_A0_Cmin_A0min_21L30 )
//                                                                                    {
//                                                                                        time = time
//                                                                                        + getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                        + getInterStageDuration( STAGE_C, STAGE_A0 ) + getParameterValue( ParametersIndex.PARAM_GTMIN_A0 )
//                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                    }
//                                                                                    else
//                                                                                        if ( path == TIME_A0_L30_Bcpmin_Cmin_A0 )
//                                                                                        {
//                                                                                            time = time
//                                                                                            + getInterStageDuration( STAGE_A0, STAGE_L30 ) + getMinStageDuration( STAGE_L30 )
//                                                                                            + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                                                                                            + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                            + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                        }
//                                                                                        else
//                                                                                            if ( path == TIME_BFA0_20L32 )
//                                                                                            {
//                                                                                                time = time
//                                                                                                + getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                            }
//                                                                                            else
//                                                                                                if ( path == TIME_BFA0_21L32 )
//                                                                                                {
//                                                                                                    time = time
//                                                                                                    + getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                                                }
//                                                                                                else
//                                                                                                    if ( path == TIME_BFA0_A0_20L30 )
//                                                                                                    {
//                                                                                                        time = time
//                                                                                                        + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + getMinStageDuration( STAGE_A0 )
//                                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                    }
//                                                                                                    else
//                                                                                                        if ( path == TIME_BFA0_A0_21L30 )
//                                                                                                        {
//                                                                                                            time = time
//                                                                                                            + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + getMinStageDuration( STAGE_A0 )
//                                                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                        }
//                                                                                                        else
//                                                                                                            if ( path == TIME_BFA0_A0cpmin_20L30 )
//                                                                                                            {
//                                                                                                                time = time
//                                                                                                                + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + GTcpminA0
//                                                                                                                + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                            }
//                                                                                                            else
//                                                                                                                if ( path == TIME_BFA0_A0cpmin_21L30 )
//                                                                                                                {
//                                                                                                                    time = time
//                                                                                                                    + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + GTcpminA0
//                                                                                                                    + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                }
//                                                                                                                else
//                                                                                                                    if ( path == TIME_BFA0_A0cpmin_Bcpmin_20L31 )
//                                                                                                                    {
//                                                                                                                        time = time
//                                                                                                                        + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + GTcpminA0
//                                                                                                                        + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                        + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                    }
//                                                                                                                    else
//                                                                                                                        if ( path == TIME_BFA0_A0cpmin_Bcpmin_21L31 )
//                                                                                                                        {
//                                                                                                                            time = time
//                                                                                                                            + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + GTcpminA0
//                                                                                                                            + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                            + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                                                                                                                        }
//                                                                                                                        else
//                                                                                                                            if ( path == TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32 )
//                                                                                                                            {
//                                                                                                                                time = time
//                                                                                                                                + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + GTcpminA0
//                                                                                                                                + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                            }
//                                                                                                                            else
//                                                                                                                                if ( path == TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32 )
//                                                                                                                                {
//                                                                                                                                    time = time
//                                                                                                                                    + getInterStageDuration( STAGE_BFA0, STAGE_A0 ) + GTcpminA0
//                                                                                                                                    + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                    + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                    + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                                                                                }
//                                                                                                                                else
//                                                                                                                                    if ( path == TIME_B_20L31 )
//                                                                                                                                    {
//                                                                                                                                        time = time
//                                                                                                                                        + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                    }
//                                                                                                                                    else
//                                                                                                                                        if ( path == TIME_B_21L31 )
//                                                                                                                                        {
//                                                                                                                                            time = time
//                                                                                                                                            + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                                                                                                                                        }
//                                                                                                                                        else
//                                                                                                                                            if ( path == TIME_B_C )
//                                                                                                                                            {
//                                                                                                                                                time = time
//                                                                                                                                                + getInterStageDuration( STAGE_B, STAGE_C ) + getMinStageDuration( STAGE_C );
//                                                                                                                                            }
//                                                                                                                                            else
//                                                                                                                                                if ( path == TIME_B_C_A0cpmin_20L30 )
//                                                                                                                                                {
//                                                                                                                                                    time = time
//                                                                                                                                                    + getInterStageDuration( STAGE_B, STAGE_C ) + getMinStageDuration( STAGE_C )
//                                                                                                                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                    + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                }
//                                                                                                                                                else
//                                                                                                                                                    if ( path == TIME_B_Cmin_20L32 )
//                                                                                                                                                    {
//                                                                                                                                                        time = time
//                                                                                                                                                        + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                        + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                    }
//                                                                                                                                                    else
//                                                                                                                                                        if ( path == TIME_B_Cmin_21L32 )
//                                                                                                                                                        {
//                                                                                                                                                            time = time
//                                                                                                                                                            + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                            + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                                                                                                        }
//                                                                                                                                                        else
//                                                                                                                                                            if ( path == TIME_B_Cmin_A0_20L30 )
//                                                                                                                                                            {
//                                                                                                                                                                time = time
//                                                                                                                                                                + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 )
//                                                                                                                                                                + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                            }
//                                                                                                                                                            else
//                                                                                                                                                                if ( path == TIME_B_Cmin_A0_21L30 )
//                                                                                                                                                                {
//                                                                                                                                                                    time = time
//                                                                                                                                                                    + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 )
//                                                                                                                                                                    + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                                                                }
//                                                                                                                                                                else
//                                                                                                                                                                    if ( path == TIME_B_Cmin_A0cpmin_20L30 )
//                                                                                                                                                                    {
//                                                                                                                                                                        time = time
//                                                                                                                                                                        + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                        + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                    }
//                                                                                                                                                                    else
//                                                                                                                                                                        if ( path == TIME_B_Cmin_A0cpmin_21L30 )
//                                                                                                                                                                        {
//                                                                                                                                                                            time = time
//                                                                                                                                                                            + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                            + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                                                                        }
//                                                                                                                                                                        else
//                                                                                                                                                                            if ( path == TIME_B_Cmin_A0cpmin_Bcpmin_20L31 )
//                                                                                                                                                                            {
//                                                                                                                                                                                time = time
//                                                                                                                                                                                + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                                                            }
//                                                                                                                                                                            else
//                                                                                                                                                                                if ( path == TIME_B_Cmin_A0cpmin_Bcpmin_21L31 )
//                                                                                                                                                                                {
//                                                                                                                                                                                    time = time
//                                                                                                                                                                                    + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                    + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                    + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                                                                                                                                                                                }
//                                                                                                                                                                                else
//                                                                                                                                                                                    if ( path == TIME_B_L31_A0 )
//                                                                                                                                                                                    {
//                                                                                                                                                                                        time = time
//                                                                                                                                                                                        + getInterStageDuration( STAGE_B, STAGE_L31 ) + getMinStageDuration( STAGE_L31 )
//                                                                                                                                                                                        + getInterStageDuration( STAGE_L31, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                    }
//                                                                                                                                                                                    else
//                                                                                                                                                                                        if ( path == TIME_C_20L32 )
//                                                                                                                                                                                        {
//                                                                                                                                                                                            time = time
//                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                                                        }
//                                                                                                                                                                                        else
//                                                                                                                                                                                            if ( path == TIME_C_21L32 )
//                                                                                                                                                                                            {
//                                                                                                                                                                                                time = time
//                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                                                                                                                                            }
//                                                                                                                                                                                            else
//                                                                                                                                                                                                if ( path == TIME_C_A0 )
//                                                                                                                                                                                                {
//                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                }
//                                                                                                                                                                                                else
//                                                                                                                                                                                                    if ( path == TIME_C_A0cpmin_20L30 )
//                                                                                                                                                                                                    {
//                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                        + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                    }
//                                                                                                                                                                                                    else
//                                                                                                                                                                                                        if ( path == TIME_C_A0cpmin_21L30 )
//                                                                                                                                                                                                        {
//                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                            + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                        }
//                                                                                                                                                                                                        else
//                                                                                                                                                                                                            if ( path == TIME_C_A0cpmin_Bcpmin_20L31 )
//                                                                                                                                                                                                            {
//                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                            }
//                                                                                                                                                                                                            else
//                                                                                                                                                                                                                if ( path == TIME_C_A0cpmin_Bcpmin_21L31 )
//                                                                                                                                                                                                                {
//                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                    + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                    + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                }
//                                                                                                                                                                                                                else
//                                                                                                                                                                                                                    if ( path == TIME_C_A0cpmin_Bcpmin_Cmin_20L32 )
//                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                        + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                        + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                        + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                        if ( path == TIME_C_A0cpmin_Bcpmin_Cmin_21L32 )
//                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                            + getInterStageDuration( STAGE_C, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                            + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                            + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                            if ( path == TIME_C_L32_A0 )
//                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                + getInterStageDuration( STAGE_C, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L32, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                if ( path == TIME_L30_A30_Bcpmin_C_A0 )
//                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_A30, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_B, STAGE_C ) + getMinStageDuration( STAGE_C )
//                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                    if ( path == TIME_L30_A30_Cmin_20L32 )
//                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_A30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                        if ( path == TIME_L30_A30saf_Bcpmin_20L31 )
//                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_A30, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                            if ( path == TIME_L30_A30saf_Bcpmin_21L31 )
//                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_A30, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                if ( path == TIME_L30_A30saf_Cmin_20L32 )
//                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_A30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                                                                                    + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                    if ( path == TIME_L30_A30saf_Cmin_21L32 )
//                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_A30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                        if ( path == TIME_L30_B )
//                                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L30, STAGE_B ) + getMinStageDuration( STAGE_B );
//                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                                            if ( path == TIME_L30_Bcpmin_20L31 )
//                                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                                if ( path == TIME_L30_Bcpmin_21L31 )
//                                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                    + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                                    if ( path == TIME_L30_Bcpmin_C_A0 )
//                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_B, STAGE_C ) + getMinStageDuration( STAGE_C )
//                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                                        if ( path == TIME_L30_Bcpmin_C_A0_20L30 )
//                                                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_B, STAGE_C ) + getMinStageDuration( STAGE_C )
//                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 )
//                                                                                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                                                            if ( path == TIME_L30_C )
//                                                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L30, STAGE_C ) + getMinStageDuration( STAGE_C );
//                                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                                                if ( path == TIME_L30_C_20L32 )
//                                                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L30, STAGE_C ) + getMinStageDuration( STAGE_C )
//                                                                                                                                                                                                                                                                                    + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                                                    if ( path == TIME_L30_C_A0_20L30 )
//                                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L30, STAGE_C ) + getMinStageDuration( STAGE_C )
//                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_C, STAGE_A0 ) + getMinStageDuration( STAGE_A0 )
//                                                                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                                                        if ( path == TIME_L30_Cmin_20L32 )
//                                                                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                                                                            if ( path == TIME_L30_Cmin_21L32 )
//                                                                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                                                                if ( path == TIME_L31_A0 )
//                                                                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L31, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                                                                    if ( path == TIME_L31_A0cpmin_20L30 )
//                                                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L31, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                                                                        if ( path == TIME_L31_A0cpmin_21L30 )
//                                                                                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L31, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                                                                                            if ( path == TIME_L31_A0cpmin_Bcpmin_20L31 )
//                                                                                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L31, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                                                                                if ( path == TIME_L31_A31_A0 )
//                                                                                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L31, STAGE_A31 ) + getMinStageDuration( STAGE_A31 )
//                                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_A31, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                                                                                    if ( path == TIME_L31_A31_A0cpmin_20L30 )
//                                                                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L31, STAGE_A31 ) + getMinStageDuration( STAGE_A31 )
//                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_A31, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                                                                                        if ( path == TIME_L31_A31saf_A0cpmin_20L30 )
//                                                                                                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L31, STAGE_A31 ) + getMinStageDuration( STAGE_A31 )
//                                                                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_A31, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                                                                                                            if ( path == TIME_L31_A31saf_A0cpmin_21L30 )
//                                                                                                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L31, STAGE_A31 ) + getMinStageDuration( STAGE_A31 )
//                                                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_A31, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                                                                                                if ( path == TIME_L32_A0 )
//                                                                                                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L32, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                                                                                                    if ( path == TIME_L32_A0_Bcpmin_20L31 )
//                                                                                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L32, STAGE_A0 ) + getMinStageDuration( STAGE_A0 )
//                                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                                                                                                        if ( path == TIME_L32_A0cpmin_20L30 )
//                                                                                                                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L32, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                            + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                                                                                                                            if ( path == TIME_L32_A0cpmin_21L30 )
//                                                                                                                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L32, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                                                                                                                if ( path == TIME_L32_A0cpmin_Bcpmin_20L31 )
//                                                                                                                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L32, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                                                                                                    + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                                                                                                                    if ( path == TIME_L32_A0cpmin_Bcpmin_C_20L32 )
//                                                                                                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L32, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_B, STAGE_C ) + getMinStageDuration( STAGE_C )
//                                                                                                                                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                                                                                                                        if ( path == TIME_L32_A32_A0 )
//                                                                                                                                                                                                                                                                                                                                                        {
//                                                                                                                                                                                                                                                                                                                                                            time = time
//                                                                                                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_L32, STAGE_A32 ) + getMinStageDuration( STAGE_A32 )
//                                                                                                                                                                                                                                                                                                                                                            + getInterStageDuration( STAGE_A32, STAGE_A0 ) + getMinStageDuration( STAGE_A0 );
//                                                                                                                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                                                                                                                        else
//                                                                                                                                                                                                                                                                                                                                                            if ( path == TIME_L32_A32_A0cpmin_20L30 )
//                                                                                                                                                                                                                                                                                                                                                            {
//                                                                                                                                                                                                                                                                                                                                                                time = time
//                                                                                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_L32, STAGE_A32 ) + getMinStageDuration( STAGE_A32 )
//                                                                                                                                                                                                                                                                                                                                                                + getInterStageDuration( STAGE_A32, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                                                + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                                                                                                                            else
//                                                                                                                                                                                                                                                                                                                                                                if ( path == TIME_L32_A32saf_A0cpmin_20L30 )
//                                                                                                                                                                                                                                                                                                                                                                {
//                                                                                                                                                                                                                                                                                                                                                                    time = time
//                                                                                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_L32, STAGE_A32 ) + getMinStageDuration( STAGE_A32 )
//                                                                                                                                                                                                                                                                                                                                                                    + getInterStageDuration( STAGE_A32, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                                                    + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT20 ) );
//                                                                                                                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                                                                                                                                else
//                                                                                                                                                                                                                                                                                                                                                                    if ( path == TIME_L32_A32saf_A0cpmin_21L30 )
//                                                                                                                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                                                                                                                        time = time
//                                                                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_L32, STAGE_A32 ) + getMinStageDuration( STAGE_A32 )
//                                                                                                                                                                                                                                                                                                                                                                        + getInterStageDuration( STAGE_A32, STAGE_A0 ) + GTcpminA0
//                                                                                                                                                                                                                                                                                                                                                                        + getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( LRT21 ) );
//                                                                                                                                                                                                                                                                                                                                                                    }
//                                                                                                                                                                                                                                                                                                                                                                    else
//                                                                                                                                                                                                                                                                                                                                                                    {
//                                                                                                                                                                                                                                                                                                                                                                        return -1;
//                                                                                                                                                                                                                                                                                                                                                                    }
//        return time;
//    }
//
//    protected boolean waitingTimeGood( int path )
//    {
//        int time = node.getCurrCycleSec();
//        if ( path == WTG_A0_Bcpmin_Cmin_A0 )
//        {
//            time += getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                 + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                 + getInterStageDuration( STAGE_C, STAGE_A0 );
//        }
//        else
//        if ( path == WTG_A0_Bcpmin_Cmin_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                               + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                               + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_C, STAGE_L32 ) - getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                          + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                          + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L32),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_A0_Bcpmin_L31_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_L31 ) + getMinStageDuration( STAGE_L31 )
//                      + getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                               + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_B, STAGE_L31 ) - getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageDuration( STAGE_A0, STAGE_B ) + GTcpminB
//                                                                                          + getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L31),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_A0_Cmin_A0 )
//        {
//            time += getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                 + getInterStageDuration( STAGE_C, STAGE_A0 );
//        }
//        else
//        if ( path == WTG_A0_Cmin_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                               + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_C, STAGE_L32 ) - getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageDuration( STAGE_A0, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                          + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L32),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_A0_L30_DQ_Bcpmin_Cmin_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_A0, STAGE_L30 ) + getMinStageDuration( STAGE_L30 )
//                      + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_A0, STAGE_L30 ) - getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageMoveSecond( STAGE_A0, STAGE_L30, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L30),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_A0_L31_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_A0, STAGE_L31 ) + getMinStageDuration( STAGE_L31 )
//                      + getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_A0, STAGE_L31 ) - getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L31),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_A0_L31_DQ_BFA0min_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_A0, STAGE_L31 ) + getMinStageDuration( STAGE_L31 )
//                      + getInterStageDuration( STAGE_L31, STAGE_BFA0 ) + getParameterValue( ParametersIndex.PARAM_GTMIN_BFA0 )
//                      + getInterStageDuration( STAGE_BFA0, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_A0, STAGE_L31 ) - getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageMoveSecond( STAGE_A0, STAGE_L31, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L31),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L31, STAGE_BFA0 ) + getParameterValue( ParametersIndex.PARAM_GTMIN_BFA0 )
//                      + getInterStageDuration( STAGE_BFA0, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_A31_A0 )
//        {
//            time += getInterStageDuration( STAGE_A31, STAGE_A0 );
//        }
//        else
//        if ( path == WTG_BFA0_A0 )
//        {
//            time += getInterStageDuration( STAGE_BFA0, STAGE_A0 );
//        }
//        else
//        if ( path == WTG_BFA0_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_BFA0, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_BFA0, STAGE_L32 ) - getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L32),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_B_Cmin_A0 )
//        {
//            time += getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                 + getInterStageDuration( STAGE_C, STAGE_A0 );
//        }
//        else
//        if ( path == WTG_B_Cmin_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                               + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_C, STAGE_L32 ) - getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                                                                                          + getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L32),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_B_L31_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_B, STAGE_L31 ) + getMinStageDuration( STAGE_L31 )
//                      + getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_B, STAGE_L31 ) - getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageMoveSecond( STAGE_B, STAGE_L31, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L31),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_C_A0 )
//        {
//            time += getInterStageDuration( STAGE_C, STAGE_A0 );
//        }
//        else
//        if ( path == WTG_C_BFA0_A0 )
//        {
//            time += getInterStageDuration( STAGE_C, STAGE_BFA0 ) + getMinStageDuration( STAGE_BFA0 )
//                 + getInterStageDuration( STAGE_BFA0, STAGE_A0 );
//        }
//        else
//        if ( path == WTG_C_BFA0_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_C, STAGE_BFA0 ) + getMinStageDuration( STAGE_BFA0 )
//                      + getInterStageDuration( STAGE_BFA0, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageDuration( STAGE_C, STAGE_BFA0 ) + getMinStageDuration( STAGE_BFA0 )
//                               + getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_BFA0, STAGE_L32 ) - getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageDuration( STAGE_C, STAGE_BFA0 ) + getMinStageDuration( STAGE_BFA0 )
//                                                                                          + getInterStageMoveSecond( STAGE_BFA0, STAGE_L32, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L32),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_C_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_C, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_C, STAGE_L32 ) - getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageMoveSecond( STAGE_C, STAGE_L32, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L32),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L30_DQ_A30saf_Bcpmin_Cmin_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                      + getInterStageDuration( STAGE_A30, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                      + getInterStageDuration( STAGE_A30, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L30_DQ_A30saf_Cmin_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                      + getInterStageDuration( STAGE_A30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L30, STAGE_A30 ) + getMinStageDuration( STAGE_A30 )
//                      + getInterStageDuration( STAGE_A30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L30_DQ_Bcpmin_Cmin_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L30, STAGE_B ) + GTcpminB
//                      + getInterStageDuration( STAGE_B, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L30_DQ_Cmin_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L30, STAGE_C ) + getParameterValue( ParametersIndex.PARAM_GTMIN_C )
//                      + getInterStageDuration( STAGE_C, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L31_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L31, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L31_DQ_A31saf_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L31, STAGE_A31 ) + getMinStageDuration( STAGE_A31 )
//                      + getInterStageDuration( STAGE_A31, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L31, STAGE_A31 ) + getMinStageDuration( STAGE_A31 )
//                      + getInterStageDuration( STAGE_A31, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L31_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L31, STAGE_L32 ) + getMinStageDuration( STAGE_L32 )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += maxFunc( AT + getParameterValue( ParametersIndex.PARAM_SEC ), 
//                                 getInterStageMoveSecond( STAGE_L31, STAGE_L32, getLRTMove( ATindex ) ) )
//                      + maxFunc( getInterStageDuration( STAGE_L31, STAGE_L32 ) - getInterStageMoveSecond( STAGE_L31, STAGE_L32, getLRTMove( ATindex ) )
//                                 - minFunc( getParameterValue( ParametersIndex.PARAM_GBL ),
//                                            maxFunc( 0, AT + getParameterValue( ParametersIndex.PARAM_SEC ) - ( getInterStageMoveSecond( STAGE_L31, STAGE_L32, getLRTMove( ATindex ) ) ) ) )
//                                 + getMinStageDuration( STAGE_L32),
//                                 getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L32_DQ_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A0 );
//            }
//        }
//        else
//        if ( path == WTG_L32_DQ_A32saf_A0 )
//        {
//            if ( AT == NONE )
//            {
//                time += getInterStageDuration( STAGE_L32, STAGE_A32 ) + getMinStageDuration( STAGE_A32 )
//                      + getInterStageDuration( STAGE_A32, STAGE_A0 );
//            }
//            else
//            {
//                time += AT + getParameterValue( ParametersIndex.PARAM_SEC ) + getParameterValue( getParamNumber( PreemptionDetectorParameterType.TYPE_TQ, ATindex ) )
//                      + getInterStageDuration( STAGE_L32, STAGE_A32 ) + getMinStageDuration( STAGE_A32 )
//                      + getInterStageDuration( STAGE_A32, STAGE_A0 );
//            }
//        }
//        else
//        {
//            return false;
//        }
//        return ( time < getParameterValue(ParametersIndex.PARAM_LCMAX));
//    }
//    
//    protected int getMinStageDuration(int stage )
//    {
//        switch ( stage )
//        {
//            case STAGE_A0:
//                return 1;
//            case STAGE_A30:
//                return 1;
//            case STAGE_A31:
//                return 1;
//            case STAGE_A32:
//                return 1;
//            case STAGE_B:
//                return 1;
//            case STAGE_BFA0:
//                return 1;
//            case STAGE_C:
//                return 1;
//            case STAGE_L30:
//                return 1;
//            case STAGE_L31:
//                return 1;
//            case STAGE_L32:
//                return 1;
//        }
//        return -1;
//    }
//
//    protected int getInterStageMoveSecond(int outStage, int inStage, Move move)
//    {
//         if (outStage == STAGE_A0 && inStage == STAGE_B && move.getNr() == node.k5.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_B && move.getNr() == node.k7.getNr())
//             return 5;
//         if (outStage == STAGE_A0 && inStage == STAGE_B && move.getNr() == node.k8.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_B && move.getNr() == node.pe.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_C && move.getNr() == node.k5.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_C && move.getNr() == node.k6.getNr())
//             return 3;
//         if (outStage == STAGE_A0 && inStage == STAGE_C && move.getNr() == node.k8.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_C && move.getNr() == node.pb.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_C && move.getNr() == node.pc.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_C && move.getNr() == node.pe.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_C && move.getNr() == node.pf.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.k20.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.k21.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.k5.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.pa.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.pb.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.pc.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.pd.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_L30 && move.getNr() == node.pe.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.k20.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.k21.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.k5.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.k6.getNr())
//             return 3;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.pa.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.pb.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.pc.getNr())
//             return 8;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.pd.getNr())
//             return 0;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.pe.getNr())
//             return 7;
//         if (outStage == STAGE_A0 && inStage == STAGE_L31 && move.getNr() == node.pf.getNr())
//             return 8;
//         if (outStage == STAGE_A30 && inStage == STAGE_B && move.getNr() == node.k7.getNr())
//             return 14;
//         if (outStage == STAGE_A30 && inStage == STAGE_B && move.getNr() == node.k8.getNr())
//             return 17;
//         if (outStage == STAGE_A30 && inStage == STAGE_B && move.getNr() == node.pa.getNr())
//             return 19;
//         if (outStage == STAGE_A30 && inStage == STAGE_B && move.getNr() == node.pb.getNr())
//             return 8;
//         if (outStage == STAGE_A30 && inStage == STAGE_B && move.getNr() == node.pc.getNr())
//             return 11;
//         if (outStage == STAGE_A30 && inStage == STAGE_B && move.getNr() == node.pd.getNr())
//             return 15;
//         if (outStage == STAGE_A30 && inStage == STAGE_C && move.getNr() == node.k6.getNr())
//             return 0;
//         if (outStage == STAGE_A30 && inStage == STAGE_C && move.getNr() == node.k8.getNr())
//             return 17;
//         if (outStage == STAGE_A30 && inStage == STAGE_C && move.getNr() == node.pa.getNr())
//             return 19;
//         if (outStage == STAGE_A30 && inStage == STAGE_C && move.getNr() == node.pd.getNr())
//             return 15;
//         if (outStage == STAGE_A30 && inStage == STAGE_C && move.getNr() == node.pf.getNr())
//             return 5;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.k5.getNr())
//             return 17;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.k6.getNr())
//             return 17;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.pa.getNr())
//             return 19;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.pb.getNr())
//             return 10;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.pc.getNr())
//             return 14;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.pd.getNr())
//             return 15;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.pe.getNr())
//             return 14;
//         if (outStage == STAGE_A31 && inStage == STAGE_A0 && move.getNr() == node.pf.getNr())
//             return 9;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.k5.getNr())
//             return 17;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.k6.getNr())
//             return 17;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.pa.getNr())
//             return 19;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.pb.getNr())
//             return 10;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.pc.getNr())
//             return 14;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.pd.getNr())
//             return 15;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.pe.getNr())
//             return 14;
//         if (outStage == STAGE_A31 && inStage == STAGE_BFA0 && move.getNr() == node.pf.getNr())
//             return 9;
//         if (outStage == STAGE_A32 && inStage == STAGE_A0 && move.getNr() == node.k5.getNr())
//             return 17;
//         if (outStage == STAGE_A32 && inStage == STAGE_A0 && move.getNr() == node.pa.getNr())
//             return 19;
//         if (outStage == STAGE_A32 && inStage == STAGE_A0 && move.getNr() == node.pb.getNr())
//             return 10;
//         if (outStage == STAGE_A32 && inStage == STAGE_A0 && move.getNr() == node.pc.getNr())
//             return 14;
//         if (outStage == STAGE_A32 && inStage == STAGE_A0 && move.getNr() == node.pd.getNr())
//             return 15;
//         if (outStage == STAGE_A32 && inStage == STAGE_A0 && move.getNr() == node.pe.getNr())
//             return 14;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.k20.getNr())
//             return 7;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.k21.getNr())
//             return 8;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.k5.getNr())
//             return 0;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.pa.getNr())
//             return 0;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.pb.getNr())
//             return 7;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.pc.getNr())
//             return 8;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.pd.getNr())
//             return 0;
//         if (outStage == STAGE_BFA0 && inStage == STAGE_L32 && move.getNr() == node.pe.getNr())
//             return 7;
//         if (outStage == STAGE_B && inStage == STAGE_C && move.getNr() == node.k6.getNr())
//             return 3;
//         if (outStage == STAGE_B && inStage == STAGE_C && move.getNr() == node.k7.getNr())
//             return 0;
//         if (outStage == STAGE_B && inStage == STAGE_C && move.getNr() == node.pb.getNr())
//             return 8;
//         if (outStage == STAGE_B && inStage == STAGE_C && move.getNr() == node.pc.getNr())
//             return 8;
//         if (outStage == STAGE_B && inStage == STAGE_C && move.getNr() == node.pf.getNr())
//             return 8;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.k20.getNr())
//             return 8;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.k21.getNr())
//             return 8;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.k6.getNr())
//             return 3;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.k7.getNr())
//             return 0;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.k8.getNr())
//             return 3;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.pa.getNr())
//             return 0;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.pb.getNr())
//             return 8;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.pc.getNr())
//             return 8;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.pd.getNr())
//             return 1;
//         if (outStage == STAGE_B && inStage == STAGE_L31 && move.getNr() == node.pf.getNr())
//             return 8;
//         if (outStage == STAGE_C && inStage == STAGE_A0 && move.getNr() == node.k5.getNr())
//             return 7;
//         if (outStage == STAGE_C && inStage == STAGE_A0 && move.getNr() == node.k6.getNr())
//             return 8;
//         if (outStage == STAGE_C && inStage == STAGE_A0 && move.getNr() == node.k8.getNr())
//             return 4;
//         if (outStage == STAGE_C && inStage == STAGE_A0 && move.getNr() == node.pb.getNr())
//             return 0;
//         if (outStage == STAGE_C && inStage == STAGE_A0 && move.getNr() == node.pc.getNr())
//             return 4;
//         if (outStage == STAGE_C && inStage == STAGE_A0 && move.getNr() == node.pe.getNr())
//             return 4;
//         if (outStage == STAGE_C && inStage == STAGE_A0 && move.getNr() == node.pf.getNr())
//             return 0;
//         if (outStage == STAGE_C && inStage == STAGE_BFA0 && move.getNr() == node.k5.getNr())
//             return 7;
//         if (outStage == STAGE_C && inStage == STAGE_BFA0 && move.getNr() == node.k6.getNr())
//             return 8;
//         if (outStage == STAGE_C && inStage == STAGE_BFA0 && move.getNr() == node.k8.getNr())
//             return 4;
//         if (outStage == STAGE_C && inStage == STAGE_BFA0 && move.getNr() == node.pb.getNr())
//             return 0;
//         if (outStage == STAGE_C && inStage == STAGE_BFA0 && move.getNr() == node.pc.getNr())
//             return 4;
//         if (outStage == STAGE_C && inStage == STAGE_BFA0 && move.getNr() == node.pe.getNr())
//             return 4;
//         if (outStage == STAGE_C && inStage == STAGE_BFA0 && move.getNr() == node.pf.getNr())
//             return 0;
//         if (outStage == STAGE_C && inStage == STAGE_L32 && move.getNr() == node.k20.getNr())
//             return 8;
//         if (outStage == STAGE_C && inStage == STAGE_L32 && move.getNr() == node.k21.getNr())
//             return 8;
//         if (outStage == STAGE_C && inStage == STAGE_L32 && move.getNr() == node.k6.getNr())
//             return 8;
//         if (outStage == STAGE_C && inStage == STAGE_L32 && move.getNr() == node.k8.getNr())
//             return 3;
//         if (outStage == STAGE_C && inStage == STAGE_L32 && move.getNr() == node.pa.getNr())
//             return 0;
//         if (outStage == STAGE_C && inStage == STAGE_L32 && move.getNr() == node.pd.getNr())
//             return 1;
//         if (outStage == STAGE_C && inStage == STAGE_L32 && move.getNr() == node.pf.getNr())
//             return 0;
//         if (outStage == STAGE_L30 && inStage == STAGE_A30 && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L30 && inStage == STAGE_A30 && move.getNr() == node.k21.getNr())
//             return 0;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.k21.getNr())
//             return 0;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.k7.getNr())
//             return 17;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.k8.getNr())
//             return 14;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.pa.getNr())
//             return 15;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.pb.getNr())
//             return 11;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.pc.getNr())
//             return 14;
//         if (outStage == STAGE_L30 && inStage == STAGE_B && move.getNr() == node.pd.getNr())
//             return 12;
//         if (outStage == STAGE_L30 && inStage == STAGE_C && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L30 && inStage == STAGE_C && move.getNr() == node.k21.getNr())
//             return 0;
//         if (outStage == STAGE_L30 && inStage == STAGE_C && move.getNr() == node.k6.getNr())
//             return 17;
//         if (outStage == STAGE_L30 && inStage == STAGE_C && move.getNr() == node.k8.getNr())
//             return 14;
//         if (outStage == STAGE_L30 && inStage == STAGE_C && move.getNr() == node.pa.getNr())
//             return 15;
//         if (outStage == STAGE_L30 && inStage == STAGE_C && move.getNr() == node.pd.getNr())
//             return 12;
//         if (outStage == STAGE_L30 && inStage == STAGE_C && move.getNr() == node.pf.getNr())
//             return 22;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.k21.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.k5.getNr())
//             return 20;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.k6.getNr())
//             return 8;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.pa.getNr())
//             return 15;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.pb.getNr())
//             return 13;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.pc.getNr())
//             return 17;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.pd.getNr())
//             return 12;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.pe.getNr())
//             return 17;
//         if (outStage == STAGE_L31 && inStage == STAGE_A0 && move.getNr() == node.pf.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_A31 && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_A31 && move.getNr() == node.k21.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.k21.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.k5.getNr())
//             return 20;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.k6.getNr())
//             return 8;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.pa.getNr())
//             return 15;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.pb.getNr())
//             return 13;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.pc.getNr())
//             return 17;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.pd.getNr())
//             return 12;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.pe.getNr())
//             return 17;
//         if (outStage == STAGE_L31 && inStage == STAGE_BFA0 && move.getNr() == node.pf.getNr())
//             return 0;
//         if (outStage == STAGE_L31 && inStage == STAGE_L32 && move.getNr() == node.k6.getNr())
//             return 8;
//         if (outStage == STAGE_L31 && inStage == STAGE_L32 && move.getNr() == node.pf.getNr())
//             return 0;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.k21.getNr())
//             return 0;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.k5.getNr())
//             return 20;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.pa.getNr())
//             return 15;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.pb.getNr())
//             return 13;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.pc.getNr())
//             return 17;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.pd.getNr())
//             return 12;
//         if (outStage == STAGE_L32 && inStage == STAGE_A0 && move.getNr() == node.pe.getNr())
//             return 17;
//         if (outStage == STAGE_L32 && inStage == STAGE_A32 && move.getNr() == node.k20.getNr())
//             return 0;
//         if (outStage == STAGE_L32 && inStage == STAGE_A32 && move.getNr() == node.k21.getNr())
//             return 0;
//         return -1;
//    }
//    
//    protected int getInterStageDuration(int outStage, int inStage)
//    {
//        if (outStage == STAGE_A0 && inStage == STAGE_B)
//            return 10;
//        if (outStage == STAGE_A0 && inStage == STAGE_C)
//            return 13;
//        if (outStage == STAGE_A0 && inStage == STAGE_L30)
//            return 12;
//        if (outStage == STAGE_A0 && inStage == STAGE_L31)
//            return 13;
//        if (outStage == STAGE_A30 && inStage == STAGE_B)
//            return 24;
//        if (outStage == STAGE_A30 && inStage == STAGE_C)
//            return 24;
//        if (outStage == STAGE_A31 && inStage == STAGE_A0)
//            return 24;
//        if (outStage == STAGE_A31 && inStage == STAGE_BFA0)
//            return 24;
//        if (outStage == STAGE_A32 && inStage == STAGE_A0)
//            return 24;
//        if (outStage == STAGE_BFA0 && inStage == STAGE_A0)
//            return 0;
//        if (outStage == STAGE_BFA0 && inStage == STAGE_L32)
//            return 12;
//        if (outStage == STAGE_B && inStage == STAGE_C)
//            return 13;
//        if (outStage == STAGE_B && inStage == STAGE_L31)
//            return 13;
//        if (outStage == STAGE_C && inStage == STAGE_A0)
//            return 13;
//        if (outStage == STAGE_C && inStage == STAGE_BFA0)
//            return 12;
//        if (outStage == STAGE_C && inStage == STAGE_L32)
//            return 12;
//        if (outStage == STAGE_L30 && inStage == STAGE_A30)
//            return 3;
//        if (outStage == STAGE_L30 && inStage == STAGE_B)
//            return 22;
//        if (outStage == STAGE_L30 && inStage == STAGE_C)
//            return 27;
//        if (outStage == STAGE_L31 && inStage == STAGE_A0)
//            return 25;
//        if (outStage == STAGE_L31 && inStage == STAGE_A31)
//            return 3;
//        if (outStage == STAGE_L31 && inStage == STAGE_BFA0)
//            return 25;
//        if (outStage == STAGE_L31 && inStage == STAGE_L32)
//            return 8;
//        if (outStage == STAGE_L32 && inStage == STAGE_A0)
//            return 25;
//        if (outStage == STAGE_L32 && inStage == STAGE_A32)
//            return 3;
//         return -1;
//    }
//
//    public boolean actionPlan_A0_to_B()
//    {
//        if( ( ( ( ( isElementActive( node.d_7 )==true )&&( Ghost==false ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( ( ( ( ( ( ( ( ( ( ( waitingTimeGood( WTG_A0_Bcpmin_Cmin_A0 )==false )||( ( ( PL==0 )&&( extensionGood( EG_A0 )==true ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_Bcpmin_Cmin_L32_DQ_A0 )==false ) ) )||( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<2 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<2 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( waitingTimeGood( WTG_A0_Bcpmin_Cmin_L32_DQ_A0 )==true ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_Bcpmin_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Bcpmin_Cmin_A0max_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0max_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Bcpmin_Cmin_A0max_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0max_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( ( isElementActive( node.d_7 )==true )&&( Ghost==false ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( ( ( ( ( ( ( ( ( ( ( waitingTimeGood( WTG_A0_Bcpmin_Cmin_A0 )==false )||( ( ( PL==0 )&&( extensionGood( EG_A0 )==true ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_Bcpmin_Cmin_L32_DQ_A0 )==false ) ) )||( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<2 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<2 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( waitingTimeGood( WTG_A0_Bcpmin_Cmin_L32_DQ_A0 )==true ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_Bcpmin_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Bcpmin_Cmin_A0max_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0max_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Bcpmin_Cmin_A0max_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_A0max_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A0_to_C()
//    {
//        if( ( ( ( ( isElementActive( node.d_7 )==false )&&( Ghost==false ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( ( ( ( ( ( ( ( ( ( waitingTimeGood( WTG_A0_Cmin_A0 )==false )||( ( ( PL==0 )&&( extensionGood( EG_A0 )==true ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_Cmin_L32_DQ_A0 )==false ) ) )||( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<2 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<2 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( waitingTimeGood( WTG_A0_Cmin_L32_DQ_A0 )==true ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Cmin_A0min_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_A0min_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Cmin_A0min_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_A0min_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( ( isElementActive( node.d_7 )==false )&&( Ghost==false ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( ( ( ( ( ( ( ( ( ( waitingTimeGood( WTG_A0_Cmin_A0 )==false )||( ( ( PL==0 )&&( extensionGood( EG_A0 )==true ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_A0 ) )&&( ( isElementActive( node.Info_HH101_to_H10_I )&&( RT_HH101_End_4>getParameterValue( ParametersIndex.PARAM_X1 ) ) )||( isElementActive( node.Info_HH101_to_H10_I )==false ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_Cmin_L32_DQ_A0 )==false ) ) )||( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<2 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<2 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( waitingTimeGood( WTG_A0_Cmin_L32_DQ_A0 )==true ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_A0_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Cmin_A0min_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_A0min_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Cmin_A0min_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_A0min_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_B_to_C()
//    {
//        if( ( ( getElementElapsedTime( node.PhB )>=getGTcpmin( node.PhB ) )&&( ( ( ( ( ( ( ( ( ( waitingTimeGood( WTG_B_Cmin_A0 )==false )||( ( PL==0 )&&( extensionGood( EG_B )==true ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_B ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_B ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_B ) ) )||( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_B_Cmin_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_B_Cmin_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_B_Cmin_L32_DQ_A0 )==false ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_B_Cmin_A0_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_B_Cmin_A0_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( getElementElapsedTime( node.PhB )>=getGTcpmin( node.PhB ) )&&( ( ( ( ( ( ( ( ( ( waitingTimeGood( WTG_B_Cmin_A0 )==false )||( ( PL==0 )&&( extensionGood( EG_B )==true ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_B ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_B ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_B ) ) )||( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_B_Cmin_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_B_Cmin_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_B_Cmin_L32_DQ_A0 )==false ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_B_Cmin_A0_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_A0_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_B_Cmin_A0_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_A0_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_B_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_B_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_C_to_BFA0()
//    {
//        if( ( ( ( PL==0 )&&( RT_HH101_sync2==false ) )&&( ( extensionGood( EG_C )==true )||( waitingTimeGood( WTG_C_BFA0_A0 )==false ) ) ) )
//            return true;
//        else
//        if( ( ( ( PL==0 )&&( RT_HH101_sync2==false ) )&&( ( extensionGood( EG_C )==true )||( waitingTimeGood( WTG_C_BFA0_A0 )==false ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_BFA0_to_A0()
//    {
//        if( ( ( ( ( ( ( ( ( ( ( PL==0 )&&extensionGood( EG_BFA0 ) )||( waitingTimeGood( WTG_BFA0_A0 )==false ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_BFA0 ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_BFA0 ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_BFA0 ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_BFA0_L32_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( ( ( ( ( ( ( ( PL==0 )&&extensionGood( EG_BFA0 ) )||( waitingTimeGood( WTG_BFA0_A0 )==false ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_BFA0 ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_BFA0 ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_BFA0 ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_BFA0_L32_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_BFA0_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_BFA0_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_C_to_A0()
//    {
//        if( ( ( ( ( ( PL==0 )&&( extensionGood( EG_C )==true ) )&&( RT_HH101_sync2==true ) )||( waitingTimeGood( WTG_C_A0 )==false ) )||( ( PL>0 )&&( ( ( ( ( ( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_C ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_C ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_C ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_C_L32_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_C_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_C_A0cpmin_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( ( ( userDefined_AT_21_First()==true )&&( userDefined_LRT_21_Delay()==true ) )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( ( ( userDefined_AT_21_First()==true )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( waitingTimeGood( WTG_C_A0 )==true ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==true ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L32_A0, getLRTMove( LRT20 ) ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( ( ( PL==0 )&&( extensionGood( EG_C )==true ) )&&( RT_HH101_sync2==true ) )||( waitingTimeGood( WTG_C_A0 )==false ) )||( ( PL>0 )&&( ( ( ( ( ( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_C ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_C ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&extensionGood( EG_C ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( waitingTimeGood( WTG_C_L32_DQ_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_C_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_C_A0cpmin_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]==( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<=0 )||( ATj[ LRT21 ]>=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]==( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT20 ]<=0 )||( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( ( ( userDefined_AT_21_First()==true )&&( userDefined_LRT_21_Delay()==true ) )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )||( ( ( ( ( ( userDefined_AT_21_First()==true )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( ATj[ LRT20 ]>=( getTime( TIME_C_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( waitingTimeGood( WTG_C_A0 )==true ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==true ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L32_A0, getLRTMove( LRT21 ) ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L32_to_A0()
//    {
//        if( ( ( ( closeL&&( LIG==false ) )&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L32_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L32_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( waitingTimeGood( WTG_L32_DQ_A0 )==false ) ) )&&( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )||( isElementActive( node.Info_HH11_to_HH10_I )==false ) ) ) )
//            return true;
//        else
//        if( ( ( ( closeL&&( LIG==false ) )&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L32_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L32_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( waitingTimeGood( WTG_L32_DQ_A0 )==false ) ) )&&( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )||( isElementActive( node.Info_HH11_to_HH10_I )==false ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A0_to_L30()
//    {
//        if( ( ( ( ( ( isElementActive( node.d_7 )==true )||( isElementActive( node.d_8 )==true ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( waitingTimeGood( WTG_A0_L30_DQ_Bcpmin_Cmin_A0 )==true )&&( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_B_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_B_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( isElementActive( node.q_5 )==true ) )&&Q5_A0L30_A0L31_BFA0L32 ) ) ) )||( Ghost&&extensionGood( EG_A0 ) ) ) )
//            return true;
//        else
//        if( ( ( ( ( ( isElementActive( node.d_7 )==true )||( isElementActive( node.d_8 )==true ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( waitingTimeGood( WTG_A0_L30_DQ_Bcpmin_Cmin_A0 )==true )&&( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_B_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_B_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( isElementActive( node.q_5 )==true ) )&&Q5_A0L30_A0L31_BFA0L32 ) ) ) )||( Ghost&&extensionGood( EG_A0 ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_B_to_L31()
//    {
//        if( ( ( ( getElementElapsedTime( node.PhB )>=getGTcpmin( node.PhB ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==true ) )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_B_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_B_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( getElementElapsedTime( node.PhB )>=getGTcpmin( node.PhB ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==true ) )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_B_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_B_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_C_to_L32()
//    {
//        if( ( ( waitingTimeGood( WTG_C_L32_DQ_A0 )==true )&&( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( userDefined_LRT_21_Delay()==false ) )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( waitingTimeGood( WTG_C_L32_DQ_A0 )==true )&&( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( userDefined_LRT_21_Delay()==false ) )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_C_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L30_to_B()
//    {
//        if( ( ( HH10_D7&&( closeL&&( LIG==false ) ) )&&( ( HH10_D7&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) ) )||( HH10_D7&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( HH10_D7&&( closeL&&( LIG==false ) ) )&&( ( HH10_D7&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) ) )||( HH10_D7&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L30_DQ_Bcpmin_Cmin_A0 )==false ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L31_to_BFA0()
//    {
//        if( ( ( ( closeL&&( LIG==false ) )&&( PL==0 ) )&&( RT_HH101_sync2==false ) ) )
//            return true;
//        else
//        if( ( ( ( closeL&&( LIG==false ) )&&( PL==0 ) )&&( RT_HH101_sync2==false ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L31_to_A0()
//    {
//        if( ( ( closeL&&( LIG==false ) )&&( ( ( ( PL==0 )&&( RT_HH101_sync2==true ) )||( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L31_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L31_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )||( waitingTimeGood( WTG_L31_DQ_A0 )==false ) ) ) )
//            return true;
//        else
//        if( ( ( closeL&&( LIG==false ) )&&( ( ( ( PL==0 )&&( RT_HH101_sync2==true ) )||( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L31_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L31_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )||( waitingTimeGood( WTG_L31_DQ_A0 )==false ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_BFA0_to_L32()
//    {
//        if( ( ( ( waitingTimeGood( WTG_BFA0_L32_DQ_A0 )==true )&&( Ghost==false ) )&&( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_BFA0_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_BFA0_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_BFA0_A0_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_BFA0_A0_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( isElementActive( node.q_5 )==true ) )&&Q5_A0L30_A0L31_BFA0L32 ) ) ) )
//            return true;
//        else
//        if( ( ( ( waitingTimeGood( WTG_BFA0_L32_DQ_A0 )==true )&&( Ghost==false ) )&&( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_BFA0_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_BFA0_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_BFA0_A0_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_BFA0_A0_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_BFA0_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_BFA0_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( isElementActive( node.q_5 )==true ) )&&Q5_A0L30_A0L31_BFA0L32 ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L30_to_A30()
//    {
//        if( ( ( ( closeL==true )&&LIG )&&( ( ( HH10_D7&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_A30saf_Bcpmin_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_A30saf_Bcpmin_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( waitingTimeGood( WTG_L30_DQ_A30saf_Bcpmin_Cmin_A0 )==false ) ) )||( ( HH10_D7==false )&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_A30saf_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_A30saf_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) )&&( waitingTimeGood( WTG_L30_DQ_A30saf_Cmin_A0 )==false ) ) ) ) )||( ( ( HH10_D7==false )&&HH10_D8 )&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_A30saf_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_A30saf_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) )&&( waitingTimeGood( WTG_L30_DQ_A30saf_Cmin_A0 )==false ) ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( closeL==true )&&LIG )&&( ( ( HH10_D7&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_A30saf_Bcpmin_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_A30saf_Bcpmin_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( waitingTimeGood( WTG_L30_DQ_A30saf_Bcpmin_Cmin_A0 )==false ) ) )||( ( HH10_D7==false )&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_A30saf_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_A30saf_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) )&&( waitingTimeGood( WTG_L30_DQ_A30saf_Cmin_A0 )==false ) ) ) ) )||( ( ( HH10_D7==false )&&HH10_D8 )&&( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_A30saf_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_A30saf_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) )&&( waitingTimeGood( WTG_L30_DQ_A30saf_Cmin_A0 )==false ) ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L31_to_A31()
//    {
//        if( ( ( ( closeL==true )&&LIG )&&( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L31_A31saf_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L31_A31saf_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L31_DQ_A31saf_A0 )==false ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L31_DQ_A31saf_A0 )==false ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( closeL==true )&&LIG )&&( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L31_A31saf_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L31_A31saf_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L31_DQ_A31saf_A0 )==false ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L31_DQ_A31saf_A0 )==false ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L32_to_A32()
//    {
//        if( ( ( ( closeL==true )&&LIG )&&( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L32_A32saf_A0cpmin_20L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L32_A32saf_A0cpmin_21L30, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L32_DQ_A32saf_A0 )==false ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L32_DQ_A32saf_A0 )==false ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( closeL==true )&&LIG )&&( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L32_A32saf_A0cpmin_20L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L32_A32saf_A0cpmin_21L30, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L32_DQ_A32saf_A0 )==false ) ) )||( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( waitingTimeGood( WTG_L32_DQ_A32saf_A0 )==false ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A30_to_B()
//    {
//        if( HH10_D7 )
//            return true;
//        else
//        if( HH10_D7 )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A31_to_A0()
//    {
//        if( ( ( ( RT_HH101_sync2==true )||( isElementActive( node.Info_HH101_to_H10_I )==false ) )||( waitingTimeGood( WTG_A31_A0 )==false ) ) )
//            return true;
//        else
//        if( ( ( ( RT_HH101_sync2==true )||( isElementActive( node.Info_HH101_to_H10_I )==false ) )||( waitingTimeGood( WTG_A31_A0 )==false ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A31_to_BFA0()
//    {
//        if( ( ( RT_HH101_sync2==false )&&( isElementActive( node.Info_HH101_to_H10_I )==true ) ) )
//            return true;
//        else
//        if( ( ( RT_HH101_sync2==false )&&( isElementActive( node.Info_HH101_to_H10_I )==true ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A32_to_A0()
//    {
//        return true;
//    }
//
//    public boolean actionPlan_L31_to_L32()
//    {
//        if( ( ( ( closeL==false )&&( PL>0 ) )&&( waitingTimeGood( WTG_L31_L32_DQ_A0 )==true ) ) )
//            return true;
//        else
//        if( ( ( ( closeL==false )&&( PL>0 ) )&&( waitingTimeGood( WTG_L31_L32_DQ_A0 )==true ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A0_to_L31()
//    {
//        if( ( ( ( ( isElementActive( node.d_7 )==false )&&( isElementActive( node.d_8 )==false ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( waitingTimeGood( WTG_A0_L31_DQ_BFA0min_A0 )==true )&&( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_20L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_21L31, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( isElementActive( node.q_5 )==true ) )&&Q5_A0L30_A0L31_BFA0L32 ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( ( isElementActive( node.d_7 )==false )&&( isElementActive( node.d_8 )==false ) )&&( getElementElapsedTime( node.PhA0 )>=getGTcpmin( node.PhA0 ) ) )&&( ( waitingTimeGood( WTG_A0_L31_DQ_BFA0min_A0 )==true )&&( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_A0_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )&&( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_A0_20L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_A0_21L31, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) ) )&&( isElementActive( node.q_5 )==true ) )&&Q5_A0L30_A0L31_BFA0L32 ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_L30_to_C()
//    {
//        if( ( ( ( HH10_D7==false )&&( closeL&&( LIG==false ) ) )&&( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Cmin_20L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Cmin_21L32, getLRTMove( LRT20 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( HHDillema_Zone==false ) )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) ) ) ) ) )
//            return true;
//        else
//        if( ( ( ( HH10_D7==false )&&( closeL&&( LIG==false ) ) )&&( ( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( isElementActive( node.Info_HH11_to_HH10_I )==true )&&( HHDillema_Zone==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==false ) )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) ) )||( ( ( ( PLj[ LRT20 ]<1 )||( ATj[ LRT20 ]>=( getTime( TIME_L30_Cmin_20L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( PLj[ LRT21 ]<1 )||( ATj[ LRT21 ]>=( getTime( TIME_L30_Cmin_21L32, getLRTMove( LRT21 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( isElementActive( node.Info_HH11_to_HH10_I )==false )&&( HHDillema_Zone==false ) )&&( waitingTimeGood( WTG_L30_DQ_Cmin_A0 )==false ) ) ) ) ) )
//            return true;
//        return false;
//    }
//
//    public boolean actionPlan_A30_to_C()
//    {
//        if( ( HH10_D7==false ) )
//            return true;
//        else
//        if( ( HH10_D7==false ) )
//            return true;
//        return false;
//    }
//
//    protected boolean userDefined_A0_BFA0()
//    {
//        Phase stage = Phase.getAktivePhase(node);
//        if (stage == null) 
//            return false;
//        return ( ( ( stage.getNummer() == node.PhA0.getNummer() )||( stage.getNummer() == node.PhBFA0.getNummer() ) ) );
//    }
//
//    protected boolean userDefined_AT_0()
//    {
//        return ( ( AT==0 ) );
//    }
//
//    protected boolean userDefined_AT_20_First()
//    {
//        boolean userdefined_AT_20_First_result = ( ( ( ATj[ LRT20 ]!=999 )&&( ATj[ LRT21 ]>=ATj[ LRT20 ] ) ) );
//        
//        return userdefined_AT_20_First_result;
//    }
//
//    protected boolean userDefined_AT_21_First()
//    {
//        boolean userdefined_AT_21_First_result = ( ( ( ATj[ LRT21 ]!=999 )&&( ATj[ LRT20 ]>=ATj[ LRT21 ] ) ) );
//        
//        return userdefined_AT_21_First_result;
//    }
//
//    protected boolean userDefined_Condition_L30()
//    {
//        return ( ( Flag_L30&&( ( HH10_D7&&( ( ( ( ( ( ATj[ LRT20 ]>=( ( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L30_Bcpmin_C_A0_20L30, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L30_Bcpmin_C_A0_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ATj[ LRT20 ]>=( ( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L30_Bcpmin_20L31, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L30_Bcpmin_20L31, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ATj[ LRT20 ]>=( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L30_Bcpmin_20L31, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) )||( ( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>( getTime( TIME_L30_Bcpmin_20L31, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L31_A0, UNDEFINED ) )<getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) )||( ( ( ( ( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L30_B, UNDEFINED ) )+getGTcpmin( node.PhB ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )+getTime( TIME_B_C, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GTMIN_C ) )+getTime( TIME_C_A0, UNDEFINED ) )<getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) )||( ( ( HH10_D7==false )&&HH10_D8 )&&( ( ( ( ( ATj[ LRT20 ]>=( ( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L30_C_A0_20L30, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L30_C_A0_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ATj[ LRT20 ]>=( ( ( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L30_C_20L32, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) )+getParameterValue( ParametersIndex.PARAM_X2 ) ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L30_C_20L32, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+1 )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L30_C, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GTMIN_C ) )+getTime( TIME_C_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )<getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) ) ) );
//    }
//
//    protected boolean userDefined_Condition_L31()
//    {
//        return ( ( Flag_L31&&( ( ( ( ( ATj[ LRT20 ]>=( ( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L31_A0cpmin_Bcpmin_20L31, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L31_A0cpmin_Bcpmin_20L31, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ATj[ LRT20 ]>=( ( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L31_A0cpmin_20L30, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L31_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L31_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )<=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) );
//    }
//
//    protected boolean userDefined_Condition_L32()
//    {
//        boolean userdefined_Condition_L32_result = ( ( Flag_L32&&( ( ( ( ( ( ( ATj[ LRT20 ]>=( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L32_A0cpmin_Bcpmin_C_20L32, UNDEFINED ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L32_A0cpmin_Bcpmin_C_20L32, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ATj[ LRT20 ]>=( ( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L32_A0cpmin_Bcpmin_20L31, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L32_A0_Bcpmin_20L31, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ATj[ LRT20 ]>=( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L32_A0cpmin_20L30, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )||( ( ATj[ LRT21 ]==999 )&&( ATj[ LRT20 ]>=( getTime( TIME_L32_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L32_A0, UNDEFINED ) )<=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) );
//        
//        return userdefined_Condition_L32_result;
//    }
//
//    protected boolean userDefined_Delay_A0()
//    {
//        boolean userdefined_Delay_A0_result = ( ( ( ( ( ( userDefined_AT_20_First()==false )&&( PLj[ LRT20 ]>0 ) )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_Cmin_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( waitingTimeGood( WTG_B_Cmin_L32_DQ_A0 )==false ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==false ) ) );
//        
//        return userdefined_Delay_A0_result;
//    }
//
//    protected boolean userDefined_Delay_B()
//    {
//        boolean userdefined_Delay_B_result = ( ( ( ( ( ( ( ( userDefined_AT_20_First()==false )||Flag_L30_last_cycle )&&( PLj[ LRT20 ]>0 ) )&&( getElementElapsedTime( node.PhB )>=getGTcpmin( node.PhB ) ) )&&( ATj[ LRT20 ]<=( getTime( TIME_B_C_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( waitingTimeGood( WTG_B_Cmin_L32_DQ_A0 )==false ) )&&( waitingTimeGood( WTG_B_L31_DQ_A0 )==false ) ) );
//        
//        return userdefined_Delay_B_result;
//    }
//
//    protected boolean userDefined_Delay_C()
//    {
//        boolean userdefined_Delay_C_result = ( ( ( ( ( ( userDefined_AT_20_First()==false )||Flag_L30_last_cycle )&&( PLj[ LRT20 ]>0 ) )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( waitingTimeGood( WTG_C_L32_DQ_A0 )==false )||( waitingTimeGood( WTG_C_BFA0_L32_DQ_A0 )==false ) ) ) );
//        
//        return userdefined_Delay_C_result;
//    }
//
//    protected boolean userDefined_Dillema_Zone_HH10()
//    {
//        return ( ( ( ( ATj[ LRT20 ]!=999 )&&( ATj[ LRT21 ]!=999 ) )&&( ( ATj[ LRT20 ]+getParameterValue( ParametersIndex.PARAM_TQ20 ) )>=( ATj[ LRT21 ]-getParameterValue( ParametersIndex.PARAM_TM21 ) ) ) ) );
//    }
//
//    protected boolean userDefined_HH11_DF21()
//    {
//        boolean userdefined_HH11_DF21_result = ( ( isElementActive( node.k21.df )==true ) );
//        
//        return userdefined_HH11_DF21_result;
//    }
//
//    protected boolean userDefined_LRT_20_Delay()
//    {
//        boolean userdefined_LRT_20_Delay_result = ( ( ( userDefined_AT_20_First()==false )&&( ( ( ( Flag_L30==true )&&( userDefined_Condition_L30()==false ) )||( ( Flag_L31==true )&&( userDefined_Condition_L31()==false ) ) )||( ( Flag_L32==true )&&( userDefined_Condition_L32()==false ) ) ) ) );
//        
//        return userdefined_LRT_20_Delay_result;
//    }
//
//    protected boolean userDefined_LRT_21_Delay()
//    {
//        boolean userdefined_LRT_21_Delay_result = ( ( ( ( Flag_C&&( userDefined_AT_20_First()==false ) )&&( isElementActive( node.HH11_Dillema_Zone_I )==true ) )&&( ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( ( ( ATj[ LRT21 ]+getParameterValue( ParametersIndex.PARAM_SEC ) )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L32_A0cpmin_20L30, UNDEFINED ) ) ) )&&( ATj[ LRT20 ]>=( getTime( TIME_C_20L32, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L32_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) )&&( ( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT21 ]<=( getTime( TIME_C_20L32, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT21 ] )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L32_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )<=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) ) );
//        
//        return userdefined_LRT_21_Delay_result;
//    }
//
//    protected boolean userDefined_WTGL30()
//    {
//        boolean userdefined_WTGL30_result = ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_L30_Bcpmin_20L31, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L30_Bcpmin_C_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTGL30_result;
//    }
//
//    protected boolean userDefined_WTGL30_1()
//    {
//        boolean userdefined_WTGL30_1_result = ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_L30_A30_Cmin_20L32, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L30_A30_Bcpmin_C_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTGL30_1_result;
//    }
//
//    protected boolean userDefined_WTGL30_2()
//    {
//        boolean userdefined_WTGL30_2_result = ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( ( getTime( TIME_A0_20L30, UNDEFINED )+getGTcpmin( node.PhA0 ) )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ATj[ LRT21 ]==999 ) ) );
//        
//        return userdefined_WTGL30_2_result;
//    }
//
//    protected boolean userDefined_WTGL32()
//    {
//        boolean userdefined_WTGL32_result = ( ( ( ( ( ( PLj[ LRT21 ]>0 )&&( PLj[ LRT20 ]>0 ) )&&userDefined_AT_21_First() )&&( ATj[ LRT20 ]<=( getTime( TIME_C_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L32_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTGL32_result;
//    }
//
//    protected boolean userDefined_WTGL32_2()
//    {
//        boolean userdefined_WTGL32_2_result = ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_L32_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L32_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTGL32_2_result;
//    }
//
//    protected boolean userDefined_WTGL32_21()
//    {
//        boolean userdefined_WTGL32_21_result = ( ( ( ( ( Flag_L32_21_delay==true )&&( ( PLj[ LRT21 ]>0 )&&( ATj[ LRT20 ]==999 ) ) )&&( ATj[ LRT21 ]<=( getTime( TIME_L32_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT21 ] )+getParameterValue( ParametersIndex.PARAM_TQ21 ) )+getTime( TIME_L32_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTGL32_21_result;
//    }
//
//    protected boolean userDefined_WTGL32_3()
//    {
//        boolean userdefined_WTGL32_3_result = ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_L32_A32_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L32_A32_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTGL32_3_result;
//    }
//
//    protected boolean userDefined_WTG_L31()
//    {
//        boolean userdefined_WTG_L31_result = ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_L31_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L31_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTG_L31_result;
//    }
//
//    protected boolean userDefined_WTG_L31_2()
//    {
//        boolean userdefined_WTG_L31_2_result = ( ( ( ( PLj[ LRT20 ]>0 )&&( ATj[ LRT20 ]<=( getTime( TIME_L31_A31_A0cpmin_20L30, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_L31_A31_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )>=getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) );
//        
//        return userdefined_WTG_L31_2_result;
//    }
//
//    protected boolean userDefined_train_not_Delay20()
//    {
//        Phase stage = Phase.getAktivePhase(node);
//        
//        boolean userdefined_train_not_Delay20_result = ( ( ( ( ( ( tSj[ LRT20 ]>0 )||( ( ( ( ( stage != null && stage.getNummer() == node.PhA0.getNummer() )&&( getElementElapsedTime( node.PhA0 )>( getGTcpmin( node.PhA0 )-getParameterValue( ParametersIndex.PARAM_X3 ) ) ) )&&( PLj[ LRT20 ]>0 ) )&&( ATj[ LRT20 ]<=( getTime( TIME_A0_Bcpmin_20L31, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_A0_L30_Bcpmin_Cmin_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )<getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) )||( ( ( ( ( Flag_L32_A0==true )&&( getGTcpmin( node.PhA0 )<getParameterValue( ParametersIndex.PARAM_X3 ) ) )&&( L32_A0>( ( getTime( TIME_L32_A0, UNDEFINED )-getGTcpmin( node.PhA0 ) )-getParameterValue( ParametersIndex.PARAM_X3 ) ) ) )&&( PLj[ LRT20 ]>0 ) )&&( ATj[ LRT20 ]<=( getTime( TIME_L32_A0cpmin_Bcpmin_20L31, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) ) )||( ( ( ( ( stage != null && stage.getNummer() == node.PhB.getNummer() )&&( getElementElapsedTime( node.PhB )>=getGTcpmin( node.PhB ) ) )&&( PLj[ LRT20 ]>0 ) )&&( ATj[ LRT20 ]<=( getTime( TIME_B_Cmin_20L32, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_B_L31_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )<getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) )||( ( ( ( ( stage != null && stage.getNummer() == node.PhC.getNummer() )&&( getElementElapsedTime( node.PhC )>=getParameterValue( ParametersIndex.PARAM_GTMIN_C ) ) )&&( PLj[ LRT20 ]>0 ) )&&( ATj[ LRT20 ]<=( getTime( TIME_C_20L32, UNDEFINED )+getParameterValue( ParametersIndex.PARAM_GBL ) ) ) )&&( ( ( ( ( node.getCurrCycleSec()+ATj[ LRT20 ] )+getParameterValue( ParametersIndex.PARAM_TQ20 ) )+getTime( TIME_C_L32_A0, UNDEFINED ) )+getParameterValue( ParametersIndex.PARAM_SEC ) )<getParameterValue( ParametersIndex.PARAM_LCMAX ) ) ) ) );
//        
//        return userdefined_train_not_Delay20_result;
//    }
//
//    public void resetStagesCounters() {
//        PhA0 .GGTStage = 0;
//        PhB  .GGTStage = 0;     
//        PhC  .GGTStage = 0;
//        PhL30.GGTStage = 0;
//        PhL31.GGTStage = 0;        
//    }
//}