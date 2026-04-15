package core;

import sg.ZwzMatrix;
import core.Move.MoveType;
import core.Node.SpecialProgram;
import core.programs.HandProgramm;
import core.programs.VAProg;
import m0547.Var;

public abstract class InitTk {
	public abstract void initialisiereSg();
	public abstract void initialisiereZwz();
	public abstract void initialisiereDet();
	public abstract void initialisierePulses();
	public abstract void initialisiereProgs();
	public abstract void initialisierePhasen();
	public abstract void initialisiereUhr();
    
    protected void setMatrixConflict(Node node, Move sg1, Move sg2, int conflictTime_sg1_to_sg2, int conflictTime_sg2_to_sg1) {
        if (sg1.moveType == MoveType.Traffic_Flashing) {
            conflictTime_sg1_to_sg2 += 3;
        }
        if (sg2.moveType == MoveType.Traffic_Flashing) {
            conflictTime_sg2_to_sg1 += 3;
        }
        
        if (conflictTime_sg1_to_sg2 > node.longestIntergreenTime) {
            node.longestIntergreenTime = conflictTime_sg1_to_sg2; 
        }
        
        if (conflictTime_sg2_to_sg1 > node.longestIntergreenTime) {
            node.longestIntergreenTime = conflictTime_sg2_to_sg1;
        }
        
        if (node.zwz == null) {
            node.zwz = new ZwzMatrix();
        }
        
        node.zwz.setzeZwz(sg1, sg2, conflictTime_sg1_to_sg2, conflictTime_sg2_to_sg1);
    }
    
    protected void initializeMapOnlyPrograms(Node node) {
        if (!Var.controller.isMaintenance()) {
            return;
        }
        
        node.p01MO     = new VAProg(    node         , "P01MO" ,  51  , node.p01.getUmlaufZeit() , node.p01.getGwpA() , node.p01.getGwpB() , node.p01.getWarteZeit(), node.p01.getVersatzZeit() );
        node.p02MO     = new VAProg(    node         , "P02MO" ,  52  , node.p02.getUmlaufZeit() , node.p02.getGwpA() , node.p02.getGwpB() , node.p02.getWarteZeit(), node.p02.getVersatzZeit() );
        node.p03MO     = new VAProg(    node         , "P03MO" ,  53  , node.p03.getUmlaufZeit() , node.p03.getGwpA() , node.p03.getGwpB() , node.p03.getWarteZeit(), node.p03.getVersatzZeit() );
        node.p04MO     = new VAProg(    node         , "P04MO" ,  54  , node.p04.getUmlaufZeit() , node.p04.getGwpA() , node.p04.getGwpB() , node.p04.getWarteZeit(), node.p04.getVersatzZeit() );
        node.p05MO     = new VAProg(    node         , "P05MO" ,  55  , node.p05.getUmlaufZeit() , node.p05.getGwpA() , node.p05.getGwpB() , node.p05.getWarteZeit(), node.p05.getVersatzZeit() );
        node.p06MO     = new VAProg(    node         , "P06MO" ,  56  , node.p06.getUmlaufZeit() , node.p06.getGwpA() , node.p06.getGwpB() , node.p06.getWarteZeit(), node.p06.getVersatzZeit() );
        node.p07MO     = new VAProg(    node         , "P07MO" ,  57  , node.p07.getUmlaufZeit() , node.p07.getGwpA() , node.p07.getGwpB() , node.p07.getWarteZeit(), node.p07.getVersatzZeit() );
        node.p08MO     = new VAProg(    node         , "P08MO" ,  58  , node.p08.getUmlaufZeit() , node.p08.getGwpA() , node.p08.getGwpB() , node.p08.getWarteZeit(), node.p08.getVersatzZeit() );
        node.p09MO     = new VAProg(    node         , "P09MO" ,  59  , node.p09.getUmlaufZeit() , node.p09.getGwpA() , node.p09.getGwpB() , node.p09.getWarteZeit(), node.p09.getVersatzZeit() );
        node.p10MO     = new VAProg(    node         , "P10MO" ,  60  , node.p10.getUmlaufZeit() , node.p10.getGwpA() , node.p10.getGwpB() , node.p10.getWarteZeit(), node.p10.getVersatzZeit() );
        node.p11MO     = new VAProg(    node         , "P11MO" ,  61  , node.p11.getUmlaufZeit() , node.p11.getGwpA() , node.p11.getGwpB() , node.p11.getWarteZeit(), node.p11.getVersatzZeit() );
        node.p12MO     = new VAProg(    node         , "P12MO" ,  62  , node.p12.getUmlaufZeit() , node.p12.getGwpA() , node.p12.getGwpB() , node.p12.getWarteZeit(), node.p12.getVersatzZeit() );
        node.p13MO     = new VAProg(    node         , "P13MO" ,  63  , node.p13.getUmlaufZeit() , node.p13.getGwpA() , node.p13.getGwpB() , node.p13.getWarteZeit(), node.p13.getVersatzZeit() );
        node.p14MO     = new VAProg(    node         , "P14MO" ,  64  , node.p14.getUmlaufZeit() , node.p14.getGwpA() , node.p14.getGwpB() , node.p14.getWarteZeit(), node.p14.getVersatzZeit() );
        node.p15MO     = new VAProg(    node         , "P15MO" ,  65  , node.p15.getUmlaufZeit() , node.p15.getGwpA() , node.p15.getGwpB() , node.p15.getWarteZeit(), node.p15.getVersatzZeit() );
        node.p16MO     = new VAProg(    node         , "P16MO" ,  66  , node.p16.getUmlaufZeit() , node.p16.getGwpA() , node.p16.getGwpB() , node.p16.getWarteZeit(), node.p16.getVersatzZeit() );
        node.p17MO     = new VAProg(    node         , "P17MO" ,  67  , node.p17.getUmlaufZeit() , node.p17.getGwpA() , node.p17.getGwpB() , node.p17.getWarteZeit(), node.p17.getVersatzZeit() );
        node.p18MO     = new VAProg(    node         , "P18MO" ,  68  , node.p18.getUmlaufZeit() , node.p18.getGwpA() , node.p18.getGwpB() , node.p18.getWarteZeit(), node.p18.getVersatzZeit() );
        node.p19MO     = new VAProg(    node         , "P19MO" ,  69  , node.p19.getUmlaufZeit() , node.p19.getGwpA() , node.p19.getGwpB() , node.p19.getWarteZeit(), node.p19.getVersatzZeit() );
        if (Var.controller.getMaxProgramsNumber() > 19) {
            node.p20MO = new VAProg(    node         , "P20MO" ,  70  , node.p20.getUmlaufZeit() , node.p20.getGwpA() , node.p20.getGwpB() , node.p20.getWarteZeit(), node.p20.getVersatzZeit() );
            node.p21MO = new VAProg(    node         , "P21MO" ,  71  , node.p21.getUmlaufZeit() , node.p21.getGwpA() , node.p21.getGwpB() , node.p21.getWarteZeit(), node.p21.getVersatzZeit() );
            node.p22MO = new VAProg(    node         , "P22MO" ,  72  , node.p22.getUmlaufZeit() , node.p22.getGwpA() , node.p22.getGwpB() , node.p22.getWarteZeit(), node.p22.getVersatzZeit() );
            node.p23MO = new VAProg(    node         , "P23MO" ,  73  , node.p23.getUmlaufZeit() , node.p23.getGwpA() , node.p23.getGwpB() , node.p23.getWarteZeit(), node.p23.getVersatzZeit() );
            node.p24MO = new VAProg(    node         , "P24MO" ,  74  , node.p24.getUmlaufZeit() , node.p24.getGwpA() , node.p24.getGwpB() , node.p24.getWarteZeit(), node.p24.getVersatzZeit() );
            node.p25MO = new VAProg(    node         , "P25MO" ,  75  , node.p25.getUmlaufZeit() , node.p25.getGwpA() , node.p25.getGwpB() , node.p25.getWarteZeit(), node.p25.getVersatzZeit() );
            node.p26MO = new VAProg(    node         , "P26MO" ,  76  , node.p26.getUmlaufZeit() , node.p26.getGwpA() , node.p26.getGwpB() , node.p26.getWarteZeit(), node.p26.getVersatzZeit() );
            node.p27MO = new VAProg(    node         , "P27MO" ,  77  , node.p27.getUmlaufZeit() , node.p27.getGwpA() , node.p27.getGwpB() , node.p27.getWarteZeit(), node.p27.getVersatzZeit() );
            node.p28MO = new VAProg(    node         , "P28MO" ,  78  , node.p28.getUmlaufZeit() , node.p28.getGwpA() , node.p28.getGwpB() , node.p28.getWarteZeit(), node.p28.getVersatzZeit() );
            node.p29MO = new VAProg(    node         , "P29MO" ,  79  , node.p29.getUmlaufZeit() , node.p29.getGwpA() , node.p29.getGwpB() , node.p29.getWarteZeit(), node.p29.getVersatzZeit() );
            node.p30MO = new VAProg(    node         , "P30MO" ,  80  , node.p30.getUmlaufZeit() , node.p30.getGwpA() , node.p30.getGwpB() , node.p30.getWarteZeit(), node.p30.getVersatzZeit() );
            node.p31MO = new VAProg(    node         , "P31MO" ,  81  , node.p31.getUmlaufZeit() , node.p31.getGwpA() , node.p31.getGwpB() , node.p31.getWarteZeit(), node.p31.getVersatzZeit() );
            node.p32MO = new VAProg(    node         , "P32MO" ,  82  , node.p32.getUmlaufZeit() , node.p32.getGwpA() , node.p32.getGwpB() , node.p32.getWarteZeit(), node.p32.getVersatzZeit() );
        }
    }
    
    /**
     * This method initializes the police program
     * @param node - the node to which the program belongs to
     * @param isFixedCycle - whether it's a fixed cycle police program or not (true = fixed, false = non-fixed)
     * @param cycleTime - the cycle time (only in case of a fixed-time police program)
     * @param stopPoints - stop points (only in case of the fixed-time police program)
     */
    protected void initializePoliceProgram(Node node, boolean isFixedCycle, int cycleTime, int[] stopPoints) {
        if (isFixedCycle && cycleTime > 0 && cycleTime < 900) {
            Var.controller.policeStopPoints = stopPoints;
            node.police_fixed     = new HandProgramm(node, "Police", SpecialProgram.Police.getProgramNumber(), cycleTime, 0, 0, 999, 0);
        } else {
            Var.controller.policeStopPoints = new int[] {};
            node.police_non_fixed = new VAProg      (node, "Police", SpecialProgram.Police.getProgramNumber(),       900, 0, 0, 999, 0);
        }
        // For the Fixed Time Police Program
//      tk.police_fixed = new MyHandProgramm(tk, "Police", SpecialProgram.Police.getProgramNumber(), 77, 0, 0, 999, 0);
        // For the None Fixed Time Police Program      
        //          tk.police_non_fixed = new VAProg(tk, "Police", Node.PRG_NUM_POLICE, 900, 0, 0, 999, 0);
    }
}
