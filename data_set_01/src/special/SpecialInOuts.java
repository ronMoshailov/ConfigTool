package special;

import det.*;
import fehlerhandler.FehlerHandler;
import special.Node.SpecialProgram;
import ta172.ParSets;
import ta172.Var;
import vt.*;

public class SpecialInOuts
{
    public static final boolean active = true;
    public static final boolean inactive = false;
    
    //for Check Application Pulse (preemption applications)
    public static Ausgang checkPuls; //one second pulse in the cycle beginning for checking the application
    public static boolean usecheckPuls;
    private static int pulseSec;
    private static int pulseLen;
    private static boolean flag_setPuls = false;
    private static int counter_setPuls, tmp;

    //for UPS Power Input (Maatz)
	public static Detektor ups_power_off;
	public static Detektor ups_failure;
	public static Detektor ups_door_open;
	public static boolean useUpsInput;
    private static boolean gesendet, gesendet1;
    private static boolean gesendet2, gesendet3;
    private static boolean gesendet4, gesendet5;
    
    public static Output reset;
    
    public static void initResetOutput(Node node) {
    	if (!Var.controller.isAppJerusalem() && !Var.controller.isAppTelAviv()) {
    		return;
    	}
    	
    	reset = new Output(node,"Reset", 255);
    }
    
    //********** for Check Application Pulse (preemption applications) **********
    public static void initCheckPuls(TeilKnoten node, boolean flag, int sec, int len) {
        if  (flag == active)
        {
        	usecheckPuls = true;
        	checkPuls = new Ausgang(node,   "checkPuls"     ,   252 );
            pulseSec = sec;
            pulseLen = len;
        }
        else
        {
        	usecheckPuls = false;
        }
    }

    public static void setCheckPulse()
    {
    	if (usecheckPuls == true)
    	{
    		if (Var.controller.isAppHaifa() && Var.tk1.isRegularProgram()) {
    			if (Var.tk1.getProgSek() == ParSets.checkPulse.get(Var.tk1.getAktProg().getNummer()-1)) {
    	        	checkPuls.setAusgang();
    				flag_setPuls = true;
    				counter_setPuls = 0;
    			}
    		} else if (Var.tk1.getAktProg().getNummer() == SpecialProgram.Dark.getProgramNumber()) {
    			if (Var.tk1.getProgZeit() == 10500) {
    				checkPuls.setAusgang();
    				flag_setPuls = true;
    				counter_setPuls = 0;
    			}
    		} else if (Var.tk1.getProgSek() == pulseSec) {
	        	checkPuls.setAusgang();
				flag_setPuls = true;
				counter_setPuls = 0;
	        }
	        
	    	if (flag_setPuls == true)
	    	{
	    		counter_setPuls++;
	        	if (Var.tk1.getZyklDauer() == 500)
	        		tmp = pulseLen * 2;
	        	else
	        		tmp = pulseLen;

	    		if (counter_setPuls > tmp)
	    		{
	    			flag_setPuls = false;
	    			checkPuls.resetAusgang();
	    		}
	    	}
	    	else
	    		checkPuls.resetAusgang();
    	}
    }
    //********************************************************
    
    //********** for UPS Power Input (Maatz) **********
	public static void initUpsInput(TeilKnoten node, boolean flag) {
//		input on the IO24/Chanel 13 name is ups_power_off.
//		If this input is set to high level, we will get an information in the error log file named: FEHLER: VT Fehler: Failure in the UPS
//		If the input is set to low level, we will get also an information in the error log file named: FEHLER: VT Fehler: UPS is OK

		if  (flag == active) { 
	    	useUpsInput = true;
	    	ups_power_off = new Detektor( Var.tk1, "UPSPower"  ,    0 ,   10   ,   0  ,  253);
	    	ups_failure   = new Detektor( Var.tk1, "UPSFailure",    0 ,   10   ,   0  ,  254);
	    	
	    	ups_power_off.addStau();
	    	ups_failure.addStau();
	    	
	    	if (Var.controller.isAppTelAviv()) {
	    		ups_door_open = new Detektor( Var.tk1, "UPSDoor"   ,    0 ,   10   ,   0  ,  255);
	    		ups_door_open.addStau();
	    	}
	    } else {
	    	useUpsInput = false;
	    }
	}

    public static void checkUpsPower() {
        if (!useUpsInput)
            return ;
    	
		if (ups_power_off.eingangGesetzt() && ups_power_off.stau(2000)) {
		    if  (!gesendet) {
		    	gesendet = true;
		    	gesendet1 = false;
		    	//Write error in error log file as error
		    	if (Var.controller.isAppHaifa() || Var.controller.isAppTelAviv()) {
		    		FehlerHandler.writeMsg(FehlerHandler.errError, true, Var.tk1.getNummer(), "UPS is active (NOT OK)");
		    	} else {
		    		FehlerHandler.writeMsg(FehlerHandler.errError, true, Var.tk1.getNummer(), "Failure in the UPS");
		    	}
		    }
		} else {
		    if  (!gesendet1) {
		    	gesendet1 = true;
		    	gesendet = false;
		    	//Write ok message in error log file as information
		    	if (Var.controller.isAppHaifa() || Var.controller.isAppTelAviv()) {
		    		FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), "UPS is NOT active (OK)");
		    	} else {
		    		FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), "UPS is OK");
		    	}
		    }
		}
		
		if (Var.controller.isAppHaifa() || Var.controller.isAppTelAviv()) {
			if (ups_failure.eingangGesetzt() && ups_failure.stau(2000)) {
			    if  (!gesendet2) {
			    	gesendet2 = true;
			    	gesendet3 = false;
			    	//Write error in error log file as error
		    		FehlerHandler.writeMsg(FehlerHandler.errError, true, Var.tk1.getNummer(), "UPS failure (NOT OK)");
			    }
			} else {
			    if  (!gesendet3) {
			    	gesendet3 = true;
			    	gesendet2 = false;
			    	//Write ok message in error log file as information
		    		FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), "UPS is OK");
			    }
			}
		}
		
		if (Var.controller.isAppTelAviv()) {
			if (ups_door_open.eingangGesetzt() && ups_failure.stau(2000)) {
			    if  (!gesendet4) {
			    	gesendet4 = true;
			    	gesendet5 = false;
		    		FehlerHandler.writeMsg(FehlerHandler.errError, true, Var.tk1.getNummer(), "UPS door open (NOT OK)");
			    }
			} else {
			    if  (!gesendet5) {
			    	gesendet5 = true;
			    	gesendet4 = false;
		    		FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), "UPS door closed (OK)");
			    }
			}
		}
    }
    //********************************************************
}