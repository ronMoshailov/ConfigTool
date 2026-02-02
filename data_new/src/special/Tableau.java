package special;
import special.Node.SpecialProgram;
import ta172.ParSets;
import ta172.Var;
import vt.*;

public class Tableau {

	private static int j;
	private static int [] stop_Punkte_New;
	private static int	tkanzahl = vt.TeilKnoten.getAnzahl();

	public static void checkEvent () {
		if (!Var.controller.isPoliceDoor()) {
    		return;
    	}
    	
		PoliceKeyboard.ScanPoliceButtons(); // Added by Menorah
			
		External_programm_decision();
		PoliceKeyboard.SetLeds();
		PoliceKeyboard.sendPoliceDoorstate();

		if (PoliceKeyboard.inPolice)
		{
			// if the exist program is police program, and the desired program is none police program
			// the flag "Var.adv_must_be_active" should be false for not stopping in the stop points.
			if (TeilKnoten.getTeilKnoten(PoliceKeyboard.tkNum).getProgAnf().getProg().getNummer() != SpecialProgram.Police.getProgramNumber() &&
				!((Dvi35TelAviv)Var.controller.dvi35).isGoToPoliceProgram)
				Var.adv_must_be_active = false;
			else
				Var.adv_must_be_active = true;
				
			Var.hand_active = true;
			if (TeilKnoten.getTeilKnoten(PoliceKeyboard.tkNum).isProgHalbeSek())
				PoliceKeyboard.FS_ready.resetAusgang();
			else
				PoliceKeyboard.FS_ready.setAusgang();
			
            if (TeilKnoten.getTeilKnoten(PoliceKeyboard.tkNum).getAktProg().getProgTyp() == Programm.FEST_PROG)
            {
				Var.adv_must_be_active = false;
		        if (ParSets.r1.getSize() > 0)
		        	setAdvanced();
            }
		}
		else
		{
			Var.adv_must_be_active = false;
			Var.hand_active = false;
			PoliceKeyboard.FS_ready.resetAusgang();
		}
		return;
	}

	
    private static void External_programm_decision() {
    	int	taste = 0;
    	
    	if (PoliceKeyboard.Button_Dark > 0) {taste = 1;}
    	if (PoliceKeyboard.Button_Flash > 0) {taste = 2;}
    	if (PoliceKeyboard.Button_Automat > 0) {taste = 3;}
    	if (PoliceKeyboard.Button_Hand > 0)
    	{
    		if (PoliceKeyboard.Door_N_O.getPosFlanken()>0)
    		{
       			PoliceKeyboard.ResetPoliceKeyboard();
    			taste = 5;
    		}
    		else
    			taste = 4;
    	}
    	
   		if	(taste==1)
   		{							//Dark
   			for (j = 1; j <= tkanzahl; j++)
   			{
   				TeilKnoten.getTeilKnoten(j).setProgWunsch(Vt.findProgByNum(SpecialProgram.Dark.getProgramNumber(), TeilKnoten.getTeilKnoten(j).getNummer()), StgEbene.STG_MANUELL);
   			}
   			PoliceKeyboard.ResetPoliceKeyboard();
   		}
   		
   		if	(taste==2)
   		{							//Flash
   			for (j = 1; j <= tkanzahl; j++)
   			{
   				TeilKnoten.getTeilKnoten(j).setProgWunsch(TeilKnoten.getTeilKnoten(j).getBlinkProgramm(), StgEbene.STG_MANUELL);
   			}
   			PoliceKeyboard.ResetPoliceKeyboard();
    	}
   		
   		if	(taste==3)
   		{							//Aut
   			PoliceKeyboard.ResetPoliceKeyboard();
   			for (j = 1; j <= tkanzahl; j++)
   			{
   				TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_MANUELL);
   				TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_MANUELL_EXTERN);
   				TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_VT_ANWENDER);
   			}
            if  (TeilKnoten.getTeilKnoten(1).getProgWunsch(TeilKnoten.getTeilKnoten(1),StgEbene.STG_ZENTRALE)!=null)
            {
                for  (j = 1; j <= tkanzahl; j++)
                {
                     TeilKnoten.getTeilKnoten(j).setProgWunsch(TeilKnoten.getTeilKnoten(j).getProgWunsch(TeilKnoten.getTeilKnoten(j), StgEbene.STG_ZENTRALE),StgEbene.STG_ZENTRALE);
                }
            }
            else
            {
            	taste=5;						//Clock
            }					//Clock
   		}
   		
		if	(taste==4)
		{							//HAND only if not in a normal Programm
            for  (j = 1; j <= tkanzahl; j++)
            {
            	 TeilKnoten.getTeilKnoten(j).setProgWunsch(Vt.findProgByNum(SpecialProgram.Police.getProgramNumber(), TeilKnoten.getTeilKnoten(j).getNummer()), StgEbene.STG_MANUELL);
            }
		}

   		if	(taste==5)
   		{							//CLOCK
            for  (j = 1; j <= tkanzahl; j++)
            {
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_MANUELL);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_MANUELL_EXTERN);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_VT_ANWENDER);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_ZENTRALE);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(TeilKnoten.getTeilKnoten(j).getProgWunsch(TeilKnoten.getTeilKnoten(j),StgEbene.STG_UHR), StgEbene.STG_UHR);
            }
   		}

   		if	(taste==0){							//nothing chosen
            for  (j = 1; j <= tkanzahl; j++)
            {
                 TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_MANUELL_EXTERN);
                 TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_VT_ANWENDER);
            }
   		}
	}
    
    /**
     * for the Fixed Time Police Program:
     * set Led of the ADVance button
     */
    private static void setAdvanced() {
		stop_Punkte_New = Dvi35ParametersBase.readPoliceParams();
        for (int i=0; i < stop_Punkte_New.length; i++) 
        {
            if (TeilKnoten.getTeilKnoten(PoliceKeyboard.tkNum).getProgZeit() == stop_Punkte_New[i])
            {
            	PoliceKeyboard.FS_ready.setAusgang();
                break;
            }
        }
    }

	/**
	 * for the None Fixed Time Police Program:
	 * stops the phase time and
	 * set Led of the ADVance button
     * @param tk: the junction
     * @param phase: the phase
     * @param length: the length of the phase, include "phue"
	 */
	public static void setAdvanced(TeilKnoten node, Phase phase, int length) {
		if (Var.adv_must_be_active)
		{
			if (Var.hand_active && (phase.getPhasenZeit() == length)) {
				if ((PoliceKeyboard.FS.getPosFlanken() == 0) &&
					!((Dvi35TelAviv)Var.controller.dvi35).isPoliceAdvance) {
					phase.setPhasenZeit(phase.getPhasenZeit() - node.getZyklDauer());
					PoliceKeyboard.FS_ready.setAusgang();
				}
			}
		}
	}

	/**
	 * for the None Fixed Time Police Program:
	 * stops the program time and
	 * set Led of the ADVance button.
	 * May be used only in the first phase of the cycle
     * @param tk: the junction
     * @param length: the length of the current phase
	 */
	public static void setAdvanced(Node node, int length) {
		if (Var.adv_must_be_active)
		{
			if (Var.hand_active && (node.getProgZeit() == length)) {
				if ((PoliceKeyboard.FS.getPosFlanken() == 0)) {
					node.FreezeProgramCycle();
					PoliceKeyboard.FS_ready.setAusgang();
				}
			}
		}
	}
}
