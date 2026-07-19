package core;

import core.Node.SpecialProgram;
import core.detectors.Input;
import core.detectors.MonitoredInput;
import core.detectors.Output;
import m0547.Var;
import vt.Programm;
import vt.StgEbene;
import vt.TeilKnoten;
import vt.Vt;
import vtvar.VtVarStrukt;

public class PoliceKeyboard
{
	protected int Button_Hand, Button_Automat, Button_Flash, Button_Dark;  
    //inputs
    public Input FS, Hand, Automat, Flash, Dark;
    public MonitoredInput Door_Closed;
    //outputs
    public Output FS_ready, Led_Hand, Led_Automat, Led_Flash, Led_Dark; // police keyboard leds
    
	private int i;
	private int tkanzahl = vt.TeilKnoten.getAnzahl();
	private boolean inSpecialProg;
	public boolean inPolice;
	// tkNum - number of the first intersection, which is not fault
	// tkNum=0 if all intersections are fault
	public int tkNum;
	
	public PoliceKeyboard()
	{
		Button_Hand = 0;
		Button_Automat = 0;
		Button_Flash = 0;
		Button_Dark = 0;
	}

	public void ResetPoliceKeyboard()
	{
		Button_Hand = 0;
		Button_Automat = 0;
		Button_Flash = 0;
		Button_Dark = 0;
	}
	
	public void initializeDet()
	{
		FS          = new Input         (Var.tk1, "Police_Adv"     , 221);
		Hand        = new Input         (Var.tk1, "Police_Hand"    , 222);
		Automat     = new Input         (Var.tk1, "Police_Auto"    , 223);
		Flash       = new Input         (Var.tk1, "Police_Flash"   , 224);
		Dark        = new Input         (Var.tk1, "Police_Dark"    , 225);
		Door_Closed    = new MonitoredInput(Var.tk1, "Police_Door" , 226, "Police door closed", "Police door open");

		FS_ready    = new Output        (Var.tk1, "Led_Advance"    , 221, false);
		Led_Hand    = new Output        (Var.tk1, "Led_Hand"       , 222, false);
		Led_Automat = new Output        (Var.tk1, "Led_Automat"    , 223, false);
		Led_Flash   = new Output        (Var.tk1, "Led_Flash"      , 224, false);
		Led_Dark    = new Output        (Var.tk1, "Led_Dark"       , 225, false);
	}
	
	public void initializeParameters() {
	    if (Var.controller.policeStopPoints != null && Var.controller.policeStopPoints.length != 0)
	    {
	        Var.controller.parameters.r = VtVarStrukt.init(Var.tk1, "Police_Stop_Points", Var.controller.policeStopPoints, true, true, true);
	    }
	}
	
	private void CheckSpecialCase()
	{
		inSpecialProg = false;
		inPolice = false;
		tkNum = 0;
		for (i = tkanzahl; i > 0; i--)
		{
			if (!hw.AnlagenZustand.isAbschaltgrad1(TeilKnoten.getTeilKnoten(i)) &&
				!hw.AnlagenZustand.isAbschaltgrad2(TeilKnoten.getTeilKnoten(i)))
			{
				tkNum = i;
			}
			
			if (TeilKnoten.getTeilKnoten(i).getAktProg() == TeilKnoten.getTeilKnoten(i).getDunkelProgramm() ||
				TeilKnoten.getTeilKnoten(i).getAktProg() == TeilKnoten.getTeilKnoten(i).getBlinkProgramm() ||
				TeilKnoten.getTeilKnoten(i).getAktProg() == Vt.findProgByNum(SpecialProgram.Dark.getProgramNumber(), TeilKnoten.getTeilKnoten(i).getNummer()))
			{
				inSpecialProg = true;
			}
			else if (TeilKnoten.getTeilKnoten(i).getAktProg() == Vt.findProgByNum(SpecialProgram.Police.getProgramNumber(), TeilKnoten.getTeilKnoten(i).getNummer()))
			{
				inPolice = true;
			}
		}
	}
	
	public void ScanPoliceButtons()
	{
		CheckSpecialCase();
		
		if (Hand.getPosFlanken() > 0)
		{
			if (Button_Hand == 0)
			{
				if (!inSpecialProg)
				{
					Button_Hand = 1;
					Button_Automat = 0;
		    		Button_Flash = 0;
		    		Button_Dark = 0;
				}
			}
		}
		else if (Automat.getPosFlanken() > 0)
		{
			if (Button_Automat == 0)
			{
				Button_Automat = 1;
				Button_Hand = 0;
	    		Button_Flash = 0;
	    		Button_Dark = 0;
			}
		}
		else if (Flash.getPosFlanken() > 0)
		{
			if (Button_Flash == 0)
			{
				if (!inPolice)
				{
					Button_Flash = 1;
					Button_Hand = 0;
		    		Button_Automat = 0;
		    		Button_Dark = 0;
				}
			}
		}
		else if (Dark.getPosFlanken() > 0)
		{
			if (Button_Dark == 0)
			{
				if (!inPolice)
				{
					Button_Dark = 1;
					Button_Hand = 0;
		    		Button_Automat = 0;
		    		Button_Flash = 0;
				}
			}
		}
	}

	public void SetLeds()
	{
		if (tkNum == 0)
		{
			Led_Automat.resetAusgang();
			Led_Flash.resetAusgang();
			Led_Dark.resetAusgang();
			Led_Hand.resetAusgang();
			return;
		}

		if (TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() == SpecialProgram.Dark.getProgramNumber())
		{
			Led_Automat.resetAusgang();
			Led_Flash.resetAusgang();
			Led_Dark.setAusgang();
		}
		else if (TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() == SpecialProgram.Flash.getProgramNumber())
		{
			Led_Automat.resetAusgang();
			Led_Flash.setAusgang();
			Led_Dark.resetAusgang();
		}
		else
		{
			Led_Flash.resetAusgang();
			Led_Dark.resetAusgang();
		}
		
		if (TeilKnoten.getTeilKnoten(tkNum).getAktStgEbene() == StgEbene.STG_NUM_UHR)
		{
			Led_Automat.setAusgang();
			Led_Flash.resetAusgang();
			Led_Dark.resetAusgang();
		}
		else
			Led_Automat.resetAusgang();

    	if (Button_Hand == 0)
    		//button is not pressed
    	{
    		Led_Hand.resetAusgang();
    	}
    	else
    		//button is pressed
    	{
			if (TeilKnoten.getTeilKnoten(tkNum).isProgHalbeSek())
				Led_Hand.resetAusgang();
			else
				Led_Hand.setAusgang();
			
			if (TeilKnoten.getTeilKnoten(tkNum).getAktStgEbene() == StgEbene.STG_NUM_MANUELL)
			{
				Led_Automat.resetAusgang();
				if ((TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() != SpecialProgram.Dark.getProgramNumber()) &&
					(TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() != SpecialProgram.Flash.getProgramNumber()))
				{
					Led_Hand.setAusgang();
				}
			}
    	}
	}
    //For the Police Program
    private static int j;
    private static int [] stop_Punkte_New;

    public void checkEvent () {
        if (!Var.controller.isPoliceDoor()) {
            return;
        }
        
        ScanPoliceButtons();
            
        External_programm_decision();
        SetLeds();

        if (inPolice)
        {
            // if the exist program is police program, and the desired program is none police program
            // the flag "Var.adv_must_be_active" should be false for not stopping in the stop points.
            if (TeilKnoten.getTeilKnoten(tkNum).getProgAnf().getProg().getNummer() != SpecialProgram.Police.getProgramNumber())
                Var.adv_must_be_active = false;
            else
                Var.adv_must_be_active = true;
                
            Var.hand_active = true;
            if (TeilKnoten.getTeilKnoten(tkNum).isProgHalbeSek())
                FS_ready.resetAusgang();
            else
                FS_ready.setAusgang();
            
            if (TeilKnoten.getTeilKnoten(tkNum).getAktProg().getProgTyp() == Programm.FEST_PROG)
            {
                Var.adv_must_be_active = false;
                if (Var.controller.parameters.getPoliceParameters().length > 0)
                    setAdvanced();
            }
        }
        else
        {
            Var.adv_must_be_active = false;
            Var.hand_active = false;
            FS_ready.resetAusgang();
        }
        return;
    }

    
    private void External_programm_decision() {
        int taste = 0;
        
        if (Button_Dark > 0) {taste = 1;}
        if (Button_Flash > 0) {taste = 2;}
        if (Button_Automat > 0) {taste = 3;}
        if (Button_Hand > 0)
        {
            if (Door_Closed.getPosFlanken()>0)
            {
                ResetPoliceKeyboard();
                taste = 5;
            }
            else
                taste = 4;
        }
        
        if  (taste==1)
        {                           //Dark
            for (j = 1; j <= tkanzahl; j++)
            {
                TeilKnoten.getTeilKnoten(j).setProgWunsch(Vt.findProgByNum(SpecialProgram.Dark.getProgramNumber(), TeilKnoten.getTeilKnoten(j).getNummer()), StgEbene.STG_MANUELL);
            }
            ResetPoliceKeyboard();
        }
        
        if  (taste==2)
        {                           //Flash
            for (j = 1; j <= tkanzahl; j++)
            {
                TeilKnoten.getTeilKnoten(j).setProgWunsch(TeilKnoten.getTeilKnoten(j).getBlinkProgramm(), StgEbene.STG_MANUELL);
            }
            ResetPoliceKeyboard();
        }
        
        if  (taste==3)
        {                           //Aut
            ResetPoliceKeyboard();
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
                taste=5;                        //Clock
            }                   //Clock
        }
        
        if  (taste==4)
        {                           //HAND only if not in a normal Programm
            for  (j = 1; j <= tkanzahl; j++)
            {
                 TeilKnoten.getTeilKnoten(j).setProgWunsch(Vt.findProgByNum(SpecialProgram.Police.getProgramNumber(), TeilKnoten.getTeilKnoten(j).getNummer()), StgEbene.STG_MANUELL);
            }
        }

        if  (taste==5)
        {                           //CLOCK
            for  (j = 1; j <= tkanzahl; j++)
            {
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_MANUELL);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_MANUELL_EXTERN);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_VT_ANWENDER);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(null, StgEbene.STG_ZENTRALE);
                TeilKnoten.getTeilKnoten(j).setProgWunsch(TeilKnoten.getTeilKnoten(j).getProgWunsch(TeilKnoten.getTeilKnoten(j),StgEbene.STG_UHR), StgEbene.STG_UHR);
            }
        }

        if  (taste==0){                         //nothing chosen
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
    private void setAdvanced() {
        stop_Punkte_New = Var.controller.parameters.getPoliceParameters();
        for (int i=0; i < stop_Punkte_New.length; i++) 
        {
            if (TeilKnoten.getTeilKnoten(tkNum).getProgZeit() == stop_Punkte_New[i])
            {
                FS_ready.setAusgang();
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
    public void setAdvanced(Node node, Stage phase, int length) {
        if (Var.adv_must_be_active) {
            if (Var.hand_active && (phase.getPhasenSek() == length)) {
                if ((FS.getPosFlanken() == 0)) {
                    node.freezeTime();
                    phase.freezeTime();
                    FS_ready.setAusgang();
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
    public void setAdvanced(Node node, int length) {
        if (Var.adv_must_be_active)
        {
            if (Var.hand_active && (node.getProgZeit() == length)) {
                if ((FS.getPosFlanken() == 0)) {
                    node.freezeTime();
                    FS_ready.setAusgang();
                }
            }
        }
    }
}
