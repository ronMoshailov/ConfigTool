package core;

import m0547.Var;
import core.detectors.Output;
import uhr.*;
import vtvar.VarInt;

public class Signs {
    //signsOffsetInOn  - offset (in minutes) for turning the signs ON early
    //signsOffsetInOff - offset (in minutes) for turning the signs OFF later
    public static VarInt signsOffsetInOn;
    public static VarInt signsOffsetInOff;

    public Output signs_on;

    public void initializeDet() {
		signs_on   = new Output(Var.tk1, "Signs_On", 251, false);
    }
    
    public void initializeParameters() {
        signsOffsetInOn  = VarInt.init(Var.tk1, "Signs offset in ON" , 30 , true, true, true);
        signsOffsetInOff = VarInt.init(Var.tk1, "Signs offset in OFF", 30 , true, true, true);
    }
    
    public boolean isError() {
        return false;
    }
    
	public void operateSigns() {
		int	minute = Zeit.getMinute();
		int	hour = Zeit.getStunde();
		int	day = Zeit.getTagDesMonats();
		int	month = Zeit.getMonat();
        int V10, V11, V12, V13;
        int V20, V21, V22, V23;
        int minus, minusH, minusM, plus, plusH, plusM, tmp_min, tmp_h;
		
		if  ((Var.tk1.getAktProg() == Var.tk1.getDunkelProgramm()) || (Var.tk1.getAktProg() == Var.tk1.all_dark))
		{
			signs_on.resetAusgang();
			return;
		}

        switch (month) {
	    case 1:         //January
	        V10 = 45; V11 = 16; V12 = 55; V13 = 16;
	        V20 = 40; V21 =  6; V22 = 40; V23 =  6;
	        break;
        case 2:         //February  
	        V10 = 15; V11 = 17; V12 = 25; V13 = 17;
	        V20 = 30; V21 =  6; V22 = 20; V23 =  6;
            break;
        case 3:         //March 
	        V10 = 35; V11 = 17; V12 = 45; V13 = 17;
	        V20 =  5; V21 =  6; V22 = 30; V23 =  6;
            break;
        case 4:         //April
	        V10 =  0; V11 = 19; V12 = 10; V13 = 19;
	        V20 = 30; V21 =  6; V22 = 10; V23 =  6;
            break;
        case 5:         //May
	        V10 = 20; V11 = 19; V12 = 30; V13 = 19;
	        V20 = 55; V21 =  5; V22 = 40; V23 =  5;
            break;
        case 6:         //June
	        V10 = 40; V11 = 19; V12 = 45; V13 = 19;
	        V20 = 35; V21 =  5; V22 = 40; V23 =  5;
            break;
        case 7:         //July
	        V10 = 45; V11 = 19; V12 = 35; V13 = 19;
	        V20 = 45; V21 =  5; V22 = 55; V23 =  5;
            break;
        case 8:         //August
	        V10 = 25; V11 = 19; V12 =  5; V13 = 19;
	        V20 =  5; V21 =  6; V22 = 15; V23 =  6;
            break;
        case 9:         //September
	        V10 = 45; V11 = 18; V12 = 25; V13 = 18;
	        V20 = 25; V21 =  6; V22 = 35; V23 =  6;
            break;
        case 10:        //October
	        V10 =  5; V11 = 17; V12 = 50; V13 = 16;
	        V20 = 35; V21 =  6; V22 = 55; V23 =  5;
            break;
        case 11:        //November
	        V10 = 40; V11 = 16; V12 = 35; V13 = 16;
	        V20 = 10; V21 =  6; V22 = 20; V23 =  6;
            break;
        case 12:        //December
	        V10 = 35; V11 = 16; V12 = 35; V13 = 16;
	        V20 = 30; V21 =  6; V22 = 40; V23 =  6;
            break;
        default : 
	        V10 = 45; V11 = 16; V12 = 55; V13 = 16;
	        V20 = 40; V21 =  6; V22 = 40; V23 =  6;
            break;
	}
		
		if (day >= 15) {
			V10 = V12; V11 = V13;
			V20 = V22; V21 = V23;
		}
		
		//offset in off
		plus = signsOffsetInOff.get();
		if (plus > 240)
		{
			plus = 240;
			signsOffsetInOff.set(plus);
		}
		plusH = plus / 60;
		plusM = plus % 60;
		tmp_min = V20 + plusM;
		if (tmp_min >= 60)
		{
			plusH++;
			tmp_min = tmp_min - 60;
		}
		tmp_h = V21 + plusH;
		if (tmp_h < 24)
		{
			V21 = tmp_h;
			V20 = tmp_min;
		}
		
		//offset in on
		minus = signsOffsetInOn.get();
		if (minus > 240)
		{
			minus = 240;
			signsOffsetInOn.set(minus);
		}
		minusH = minus / 60;
		minusM = minus % 60;
		tmp_min = V10 - minusM;
		if (tmp_min < 0)
		{
			minusH++;
			tmp_min = tmp_min + 60;
		}
		tmp_h = V11 - minusH;
		if (tmp_h >= 0)
		{
			V11 = tmp_h;
			V10 = tmp_min;
		}
		boolean time = ((hour > V11) || (hour < V21)) || ((hour == V11) && (minute >= V10)) || ((hour == V21) && (minute <= V20));
		
		operateSigns(time);
	}

	private void operateSigns(boolean isOn) {
	    if (Var.controller.central == null || !Var.controller.central.isConnected() || !Var.controller.central.isOperateSigns()) {
	        if (isOn) {
	            signs_on.setAusgang();
	        } else {
	            signs_on.resetAusgang();
	        }
	    }
	}
}