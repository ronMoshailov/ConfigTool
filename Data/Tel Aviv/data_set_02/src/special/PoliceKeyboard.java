package special;

import special.Node.SpecialProgram;
import tk.Var;
import vt.StgEbene;
import vt.TeilKnoten;
import vt.Vt;
import det.Ausgang;
import det.Detektor;
import fehlerhandler.FehlerHandler;

// Added by Menorah
public class PoliceKeyboard
{
	public static int Button_Hand, Button_Automat, Button_Flash, Button_Dark;  
    //inputs
    public static Detektor FS, Hand, Automat, Flash, Dark, Door_N_O, Door_N_C;
    //outputs
    public static Ausgang FS_ready; // led for advanced button
    public static Ausgang Led_Hand, Led_Automat, Led_Flash, Led_Dark; // police keyboard leds
    
	private static int i;
	private static int tkanzahl = vt.TeilKnoten.getAnzahl();
	private static boolean inSpecialProg;
	public static boolean inPolice;
	// tkNum - number of the first intersection, which is not fault
	// tkNum=0 if all intersections are fault
	public static int tkNum;
	
    //for message door open (Maatz)
    private static boolean gesendet, gesendet1;
	
	public PoliceKeyboard() {
		Button_Hand = 0;
		Button_Automat = 0;
		Button_Flash = 0;
		Button_Dark = 0;
	}

	public static void ResetPoliceKeyboard() {
		Button_Hand = 0;
		Button_Automat = 0;
		Button_Flash = 0;
		Button_Dark = 0;
	}
	
	public static void initPoliceKeyboard() {
		//constant inputs for all applications
		FS          = new Detektor( Var.tk1 , "FS"         ,    0 ,    0   ,   0  ,  221);
		Hand        = new Detektor( Var.tk1 , "Hand"       ,    0 ,    0   ,   0  ,  222);
		Automat     = new Detektor( Var.tk1 , "Automat"    ,    0 ,    0   ,   0  ,  223);
		Flash       = new Detektor( Var.tk1 , "Flash"      ,    0 ,    0   ,   0  ,  224);
		Dark        = new Detektor( Var.tk1 , "Dark"       ,    0 ,    0   ,   0  ,  225);
		
		Door_N_O    = new Detektor( Var.tk1 , "Door_N_O"   ,    0 ,    0   ,   0  ,  231);
		Door_N_C    = new Detektor( Var.tk1 , "Door_N_C"   ,    0 ,    0   ,   0  ,  232);

		//constant outputs for all applications
		FS_ready    = new Ausgang( Var.tk1  , "FS_ready"   ,  221);
		Led_Hand    = new Ausgang( Var.tk1  , "Led_Hand"   ,  222);
		Led_Automat = new Ausgang( Var.tk1  , "Led_Automat",  223);
		Led_Flash   = new Ausgang( Var.tk1  , "Led_Flash"  ,  224);
		Led_Dark    = new Ausgang( Var.tk1  , "Led_Dark"   ,  225);
	}
	
	private static void CheckSpecialCase() {
		inSpecialProg = false;
		inPolice = false;
		tkNum = 0;
		for (i = tkanzahl; i > 0; i--) {
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
			} else if (TeilKnoten.getTeilKnoten(i).getAktProg() == Vt.findProgByNum(SpecialProgram.Police.getProgramNumber(), TeilKnoten.getTeilKnoten(i).getNummer()))
			{
				inPolice = true;
			}
		}
	}
	
	public static void ScanPoliceButtons() {
		CheckSpecialCase();
		
		if (Hand.getPosFlanken() > 0) {
			if (Button_Hand == 0) {
				if (!inSpecialProg) {
					Button_Hand = 1;
					Button_Automat = 0;
		    		Button_Flash = 0;
		    		Button_Dark = 0;
				}
			}
		}
		else if (Automat.getPosFlanken() > 0) {
			if (Button_Automat == 0) {
				Button_Automat = 1;
				Button_Hand = 0;
	    		Button_Flash = 0;
	    		Button_Dark = 0;
			}
		}
		else if (Flash.getPosFlanken() > 0) {
			if (Button_Flash == 0) {
				if (!inPolice) {
					Button_Flash = 1;
					Button_Hand = 0;
		    		Button_Automat = 0;
		    		Button_Dark = 0;
				}
			}
		}
		else if (Dark.getPosFlanken() > 0) {
			if (Button_Dark == 0) {
				if (!inPolice) {
					Button_Dark = 1;
					Button_Hand = 0;
		    		Button_Automat = 0;
		    		Button_Flash = 0;
				}
			}
		}
	}

	public static void SetLeds() {
		if (tkNum == 0) {
			Led_Automat.resetAusgang();
			Led_Flash.resetAusgang();
			Led_Dark.resetAusgang();
			Led_Hand.resetAusgang();
			return;
		}

		if (TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) {
			Led_Automat.resetAusgang();
			Led_Flash.resetAusgang();
			Led_Dark.setAusgang();
		} else if (TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() == SpecialProgram.Flash.getProgramNumber()) {
			Led_Automat.resetAusgang();
			Led_Flash.setAusgang();
			Led_Dark.resetAusgang();
		} else {
			Led_Flash.resetAusgang();
			Led_Dark.resetAusgang();
		}
		
		if (TeilKnoten.getTeilKnoten(tkNum).getAktStgEbene() == StgEbene.STG_NUM_UHR) {
			Led_Automat.setAusgang();
			Led_Flash.resetAusgang();
			Led_Dark.resetAusgang();
		} else
			Led_Automat.resetAusgang();

    	if (Button_Hand == 0) {
    		//button is not pressed
    		Led_Hand.resetAusgang();
    	} else {
    		//button is pressed
			if (TeilKnoten.getTeilKnoten(tkNum).isProgHalbeSek())
				Led_Hand.resetAusgang();
			else
				Led_Hand.setAusgang();
			
			if (TeilKnoten.getTeilKnoten(tkNum).getAktStgEbene() == StgEbene.STG_NUM_MANUELL) {
				Led_Automat.resetAusgang();
				if ((TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() != SpecialProgram.Dark.getProgramNumber()) &&
					(TeilKnoten.getTeilKnoten(tkNum).getAktProg().getNummer() != SpecialProgram.Flash.getProgramNumber()))
				{
					Led_Hand.setAusgang();
				}
			}
    	}
	}
	
    public static void sendPoliceDoorstate() {
        if (PoliceKeyboard.Door_N_O.eingangGesetzt()) {
            if  (!gesendet) {
                gesendet = true;
                gesendet1 = false;
                //Write message in error log file as information
                FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), "Police door closed");
            }
        } else {
            if  (!gesendet1) {
                gesendet1 = true;
                gesendet = false;
                //Write message in error log file as information
                FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), "Police door open");
            }
        }
    }
}
