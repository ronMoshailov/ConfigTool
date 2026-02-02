/**
 * 
 */
package special;

import det.Det;
import det.Detektor;
import enums.HaifaParametersIndexes;
import sbh.vtbib.dvi35.DVI35Modul;
import special.Node.SpecialProgram;
import ta172.GreenWave;
import ta172.Var;
import vt.StgEbene;
import vt.TeilKnoten;
import vt.Vt;

/**
 * @author ilia
 *
 */
public class Dvi35Haifa {
	public static final int MAX_SET_DETECTORS = 22;
	
	public static final int dvi35_prog_ind   = 0;
	public static final int dvi35_sy_ind     = 1;
	public static final int dvi35_status_ind = 2;
	public static final int dvi35_demand_ind = 3;
	public static final int clockmode = 0;
	public static final int prccmode  = 1;
	public static final int syccmode  = 2;
	public static final int on  = 1;
    public static final int off = 2;

	public static final Node node_1 = Var.tk1, node_2 = (TeilKnoten.getAnzahl() > 1 ? (Node)TeilKnoten.getTeilKnoten(2) : null);
	
	public static boolean [] wait_flag_tk = { false, false, false };
    
    public static int j, tkanzahl;
    
//    With the flag switchnodeseperate we are able to switch on/off each node separately.
//    If this flag is set to false, the controller will switch on/off the nodes with the bit 0
//    If this flag is set to true, the controller will switch the nodes on when the bit 2 /bit 3 are set, and off if the bits are not set, or bit 0 is not set. 
//    Both nodes work with the same program number
//    If there is no connection to the cc, the controller witch switch to clock
    public static final boolean switchnodeseperate = true;

    //Operation mode from control center
    public static int  opmodecc, useCentral;
    //Monitoring dvi35
    public static int  dvi35_prog, dvi35_sy, dvi35_status, dvi35_demandDV, dvi35_det1_8, dvi35_det9_16, dvi35_det17_22, dvi35_hand;
    //flagProgCC=true if the program from the control center is OK (exists and not 0)
    public static boolean  flagProgCC = false;
    //flagPrccGW=true if the controller works in GW from the control center
    public static boolean  flagPrccGW = false;
    //flag for switch controller/node on-off
    public static int controller;
    public static int [] swNode = new int[3];
    //arrays of 23 boolean flags for detector function via control center (array nummber 0 not used!)
    public static boolean[] setDet            = new boolean[23]; 
    public static boolean[] Detbusy           = new boolean[23]; 
    public static boolean[] Detnotbusy        = new boolean[23]; 
    public static boolean[] Detjitter         = new boolean[23]; 
    public static boolean[] Detector          = new boolean[23]; 
    public static int    [] DetectorFaultMode = new int    [23];
    
    public static boolean[] Detbusy23_30           = new boolean[9]; 
    public static boolean[] Detnotbusy23_30        = new boolean[9]; 
    public static boolean[] Detjitter23_30         = new boolean[9]; 
    public static boolean[] Detector23_30          = new boolean[9];
    public static boolean[] StatusDetector23_30    = new boolean[9]; 
    public static int    [] DetectorFaultMode23_30 = new int    [9];
    //flags for hand programm
	public static boolean hand_mode_auto, hand_advance;
	
    public static void monitoringDvi35Telegram()     {
    	if (!Var.controller.isAppHaifa()) {
    		return;
    	}
    	
        GreenWave.dvi35GW = false;

        if  (DVI35Modul.isZentAktiv()) {
            //monitoring dvi35 telegram
            dvi35_prog     = DVI35Modul.getId1(dvi35_prog_ind);      //ID1 Byte 11
            dvi35_sy       = DVI35Modul.getId1(dvi35_sy_ind);        //ID1 Byte 12
            dvi35_status   = DVI35Modul.getId1(dvi35_status_ind);    //ID1 Byte 13
            dvi35_demandDV = DVI35Modul.getId1(dvi35_demand_ind);    //ID1 Byte 14
            dvi35_det1_8   = DVI35Modul.getId1(4);                   //ID1 Byte 15
            dvi35_det9_16  = DVI35Modul.getId1(5);                   //ID1 Byte 16
            dvi35_det17_22 = DVI35Modul.getId1(6);                   //ID1 Byte 17
            dvi35_hand     = DVI35Modul.getId1(12);                  //ID1 Byte 23

            hand_mode_auto = false;
            hand_advance = false;
            //read byte 23 (hand programm)
            if  ((dvi35_hand & 0x01) == 0x01) {
                hand_mode_auto = true;
                dvi35_prog = SpecialProgram.Police.getProgramNumber();
                dvi35_status = dvi35_prog | 0x10;
            }
            if  ((dvi35_hand & 0x02) == 0x02) {
                hand_advance = true;
            }

            //node on/off from control center (bit 0 = controller, bit 2 = node 1, bit 3 = node 2 from Byte 13)
            controller = off;
            swNode[0] = off;
            swNode[1] = off;
            if  ((dvi35_status & 0x01) == 0x01) {
                controller = on;
            }
            if  ((dvi35_status & 0x04) == 0x04) {
                swNode[0] = on;
            }
            if  ((dvi35_status & 0x08) == 0x08) {
                swNode[1] = on;
            }

            checkProgCC();

            useCentral = 1;
            //operation mode from control center (bit 4 & 5 from Byte 13)
            if  (((dvi35_status & 0x30) == 0x00) || (flagProgCC == false)) {
                opmodecc = clockmode;
            }
            else
               {
               if  ((dvi35_status & 0x30) == 0x10) {
                   opmodecc = prccmode;
                   GreenWave.dvi35GW = true;
               }
               if  ((dvi35_status & 0x30) == 0x20) {
                   opmodecc = syccmode;
               }
            }
            
            //clear array detector 1-22  
            for (j = 0; j < setDet.length; j++) {
                setDet[j] = false;
            }
            //read byte 15-17 for setting detector 1-22 from contorl center (only when dvi35 is activ) 
            
            for (j = 1; j < 9; j++) {
                if  ((dvi35_det1_8 & (1<<(j-1))) == (0xff & (1<<(j-1)))) { setDet[j] = true; }
            }
            for (j = 1; j < 9; j++) {
                if  ((dvi35_det9_16 & (1<<(j-1))) == (0xff & (1<<(j-1)))) { setDet[j+8] = true; }
            }
            for (j = 1; j < 7; j++) {
                if  ((dvi35_det17_22 & (1<<(j-1))) == (0xff & (1<<(j-1)))) { setDet[j+16] = true; }
            }

        } else {
             useCentral = 0;
             opmodecc = clockmode;
             hand_mode_auto = false;
             hand_advance = false;
             for (j = 0; j < setDet.length; j++) {
                 setDet[j] = false;
             }
       }
    }

    public static void checkProgCC()  {
		vt.Programm progId;

		flagProgCC = false;
		progId = Vt.findProgByNum(dvi35_prog);
		if ((dvi35_prog != 0) && (progId != null))
		{
			flagProgCC = true;
		}
    }
    
    private static void SetGWactiveFlag()  {
        if (GreenWave.flag_GWactive == 2)
        {
	        if (opmodecc != prccmode)
	        	GreenWave.flag_GWactive = 0;
	        else
	        {
	      	  if (flagPrccGW == true)
	      		GreenWave.flag_GWactive = 1;
	      	  else
	      		GreenWave.flag_GWactive = 0;
	        }
        }
    }
    
    private static void SetCCProg()  {
    	tkanzahl = vt.TeilKnoten.getAnzahl();
    	
    	if  (controller == on)
    	{
    		if  (switchnodeseperate == true)
    		{
				for (j = 1; j <= tkanzahl; j++)
				{
	    			if  (swNode[j-1] == on)
	    			{
	    				TeilKnoten.getTeilKnoten(j).setProgWunsch(Vt.findProgByNum(dvi35_prog, TeilKnoten.getTeilKnoten(j).getNummer()), StgEbene.STG_ZENTRALE);
	    			}
	    			else
	    			{
	    				TeilKnoten.getTeilKnoten(j).setProgWunsch( TeilKnoten.getTeilKnoten(j).getBlinkProgramm() , StgEbene.STG_ZENTRALE);
	    			}
				}
    		}
    		else
    		{
    			for (j = 1; j <= tkanzahl; j++)
				{
					TeilKnoten.getTeilKnoten(j).setProgWunsch(Vt.findProgByNum(dvi35_prog, TeilKnoten.getTeilKnoten(j).getNummer()), StgEbene.STG_ZENTRALE);
				}
    		}
    	}
    	else
    	{
			for (j = 1; j <= tkanzahl; j++)
			{
				TeilKnoten.getTeilKnoten(j).setProgWunsch( TeilKnoten.getTeilKnoten(j).getBlinkProgramm() , StgEbene.STG_ZENTRALE);
			}
    	}
    }
    
    public static void manipulateCentralMode()
    {
    	if (!Var.controller.isAppHaifa()) {
    		return;
    	}
    	
    	tkanzahl = vt.TeilKnoten.getAnzahl();
    	
    	//Manipulate central mode according to operation mode from control centre in byte 13 bit 4 & 5
    	if  (opmodecc == clockmode) {
   			for (j = 1; j <= tkanzahl; j++)
   			{
   				TeilKnoten.getTeilKnoten(j).setProgWunsch(null,StgEbene.STG_ZENTRALE);
   			}
    	}
    	else if  (opmodecc == prccmode)  {
    	    SetCCProg();  //choose new programm
            checkPrccGW();
        }
        else if  (opmodecc == syccmode) {
            SetCCProg();  //choose new programm
        }
    	
    	SetGWactiveFlag();
    }
    
    public static void statusToCenter() {
    	int [] mask_Tk = {0,0,0};
    	//                      1  2  3  4  5  6  7  8  9 10 11 12
        int [] id110_special = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        //Manipulating byte 14 bit 0 (Police door open) of the remote frame to control center
        if (!PoliceKeyboard.Door_N_O.eingangGesetzt()) {
            mask_Tk[2] = mask_Tk[2] | 0x04;
        }
        //Manipulating byte 13 and 14 bit 2 (Hand mode) of the remote frame to control center
        if (node_1.getAktProg().getNummer() ==  SpecialProgram.Police.getProgramNumber()) {
            mask_Tk[1] = mask_Tk[1] | 0x04;
        }
        
        //Manipulating byte 13 and 14 bit 3 (Coordination position) of the remote frame to control center
        if  (!node_1.isOffline() && node_1.isRegularProgram() &&
             (Func.ifClockCoordination(node_1, node_1.getAktProg()) && (node_1.getProgSek() == node_1.getAktProg().getGwpB()) || wait_flag_tk[1])) {
            mask_Tk[1] = mask_Tk[1] | 0x08;
        }
        if (node_2 != null)
        {
        	if  (!node_2.isOffline() && node_2.isRegularProgram() &&
        			(Func.ifClockCoordination(node_2, node_2.getAktProg()) && (node_2.getProgSek() == node_2.getAktProg().getGwpB()) || wait_flag_tk[2])) {
        		mask_Tk[2] = mask_Tk[2] | 0x08;
        	}
        }
        //Manipulating byte 13 and 14 bit 4 (Controller ON/OFF) of the remote frame to control center
        if (!node_1.isOffline()) {
            mask_Tk[1] = mask_Tk[1] | 0x10;
        }
        if (node_2 != null)
        {
        	if (!node_2.isOffline()) {
        		mask_Tk[2] = mask_Tk[2] | 0x10;
        	}
        }
        //Manipulating byte 13 bit 5 (Green Wave ON/OFF) of the remote frame to control center
        if  (GreenWave.flag_GWactive > 0) {
            mask_Tk[1] = mask_Tk[1] | 0x20;
        }

        //Manipulating byte 14 bit 5 (UPS ON/OFF (on = power from ups, off = power from power lines)) of the remote frame to control center
        if (SpecialInOuts.useUpsInput) {
        	if (SpecialInOuts.ups_power_off.belegt()) {
        		mask_Tk[2] = mask_Tk[2] | 0x20;
        	}
        	if (SpecialInOuts.ups_failure.belegt()) {
        		mask_Tk[2] = mask_Tk[2] | 0x08;
        	}
        }
        //Manipulating byte 13 and 14 bit 6&7 (Mode of Operation) of the remote frame to control center
        if  (opmodecc == clockmode) {
//            mask_Tk1 = mask_Tk1 | 0x00;
//            mask_Tk2 = mask_Tk2 | 0x00;
        }
        if  (opmodecc == prccmode) {
            mask_Tk[1] = mask_Tk[1] | 0x40;
            mask_Tk[2] = mask_Tk[2] | 0x40;
        }
        if  (opmodecc == syccmode) {
            mask_Tk[1] = mask_Tk[1] | 0x80;
            mask_Tk[2] = mask_Tk[2] | 0x80;
        }
//        //Manipulating special bytes 24-38  !example with byte 24, 25 and 38!
//        if  (det7.belegt()) {
//            id110_byte24 = id110_byte24 | 0x01;
//        }
        
        for (j = 0; j < id110_special.length; j++) {
        	if (Det.getDetektorByNum(41 + (j*6)) != null) {
        		if (Det.getDetektorByNum(41 + (j*6)    ).belegt()) {
        			id110_special[j] = id110_special[j] | 0x01;
        		}
        		if (Det.getDetektorByNum(41 + (j*6) + 1).belegt()) {
        			id110_special[j] = id110_special[j] | 0x02;
        		}
        		if (Det.getDetektorByNum(41 + (j*6) + 2).belegt()) {
        			id110_special[j] = id110_special[j] | 0x04;
        		}
        		if (Det.getDetektorByNum(41 + (j*6) + 3).belegt()) {
        			id110_special[j] = id110_special[j] | 0x08;
        		}
        		if (Det.getDetektorByNum(41 + (j*6) + 4).belegt()) {
        			id110_special[j] = id110_special[j] | 0x10;
        		}
        		if (Det.getDetektorByNum(41 + (j*6) + 5).belegt()) {
        			id110_special[j] = id110_special[j] | 0x20;
        		}
        	} else {
        		break;
        	}
        }
    
        DVI35Modul.setResIDx0(3, 0xFC, mask_Tk[1]);
        DVI35Modul.setResIDx0(4, 0xFD, mask_Tk[2]);
        
        DVI35Modul.setResIDx0(14, 0xFF, id110_special[ 0]);
        DVI35Modul.setResIDx0(15, 0xFF, id110_special[ 1]);
        DVI35Modul.setResIDx0(16, 0xFF, id110_special[ 2]);
        DVI35Modul.setResIDx0(17, 0xFF, id110_special[ 3]);
        DVI35Modul.setResIDx0(18, 0xFF, id110_special[ 4]);
        DVI35Modul.setResIDx0(19, 0xFF, id110_special[ 5]);
        DVI35Modul.setResIDx0(20, 0xFF, id110_special[ 6]);
        DVI35Modul.setResIDx0(21, 0xFF, id110_special[ 7]);
        DVI35Modul.setResIDx0(22, 0xFF, id110_special[ 8]);
        DVI35Modul.setResIDx0(23, 0xFF, id110_special[ 9]);
        DVI35Modul.setResIDx0(24, 0xFF, id110_special[10]);
        DVI35Modul.setResIDx0(25, 0xFF, id110_special[11]);
        
        //journal user entry 23-32 example with entry 23!
//        if  (Var.test5.belegt()) {
//            DVI35Modul.setUserBtb(23,Var.tk1.getAktProg().getNummer(), Var.tk1.getProgSek(), 1, 2, 3, 4);
//        }

    }
    
    private static int errorModeByDetectorType(Detektor det) {
    	if (det instanceof TPDetector)
    		return 5;
    	return 7;
    }
    
    private static boolean combineErrors(int mode, boolean busy, boolean notbusy, boolean jitter) {
    	switch (mode) {
        	case 0: return false;
        	case 1: return busy;
        	case 2: return notbusy;
        	case 3: return busy || notbusy;
        	case 4: return jitter;
        	case 5: return busy || jitter;
        	case 6: return notbusy || jitter;
        	case 7: return busy || notbusy || jitter;
    	}
    	return false;
    }
    
    public static void detFaulltToCenter() {
    	int fault_mode;
    	int det_amount = 0;
    	int index = 0;
    	for (j = 1; j <= vt.TeilKnoten.getAnzahl(); j++) {
    		det_amount += ((Node)TeilKnoten.getTeilKnoten(j)).detQuantity;
    	}

        //make sure det_amount is not greater than Detector array length
        if  (det_amount > Detector.length-1) { det_amount = Detector.length-1;  }
        
        //erase all errors
        for (j = 0; j < Detbusy.length; j++) {
            Detbusy[j] = false;
        }
        for (j = 0; j < Detbusy23_30.length; j++) {
        	Detbusy23_30[j] = false;
        }
        for (j = 0; j < Detnotbusy.length; j++) {
            Detnotbusy[j] = false;
        }
        for (j = 0; j < Detnotbusy23_30.length; j++) {
            Detnotbusy23_30[j] = false;
        }
        for (j = 0; j < Detjitter.length; j++) {
            Detjitter[j] = false;
        }
        for (j = 0; j < Detjitter23_30.length; j++) {
            Detjitter23_30[j] = false;
        }
        for (j = 0; j < StatusDetector23_30.length; j++) {
        	StatusDetector23_30[j] = false;
        }

        if  (det_amount > 0) {
    	//status of detectors 23-30
        	for (j = 1; j < StatusDetector23_30.length - 1; j++) {
            	index = 22 + j;
                if  (Det.getDetektorByNum(index) != null) {
                	DetectorFaultMode23_30[j] = errorModeByDetectorType(Det.getDetektorByNum(index));
                    if  (Det.getDetektorByNum(index).belegt()) {
                    	StatusDetector23_30[j] = true;
                	}
                }
            }
        	if (GreenWave.SY != null) {
            	DetectorFaultMode23_30[DetectorFaultMode23_30.length - 1] = errorModeByDetectorType(GreenWave.SY);
            	if  (GreenWave.SY.belegt()) {  
            		StatusDetector23_30[StatusDetector23_30.length - 1] = true;
            	}
            } else if (GreenWave.SY2 != null) {
            	DetectorFaultMode23_30[DetectorFaultMode23_30.length - 1] = errorModeByDetectorType(GreenWave.SY2);
            	if  (GreenWave.SY2.belegt()) {  
            		StatusDetector23_30[StatusDetector23_30.length - 1] = true;
            	}
            } 
        //get errors
            for (j = 1; j < Detbusy.length; j++) {
                if  (j <= det_amount) { 
                	DetectorFaultMode[j] = errorModeByDetectorType(Det.getDetektorByNum(j));
                    if  ((Det.getDetektorByNum(j).getBelSek() >= 900) && Det.getDetektorByNum(j).belegt()) {  
                    	Detbusy[j] = true;
                	}
                }
            }
            for (j = 1; j < Detnotbusy.length; j++) {
                if  (j <= det_amount) { 
                    if  ((Det.getDetektorByNum(j).getNichtBelSek() >= 86400) && !Det.getDetektorByNum(j).belegt()) {
                    	Detnotbusy[j] = true;
                	}
                }
            }
            for (j = 1; j < Detjitter.length; j++) {
                if  (j <= det_amount) { 
                    if  (Det.getDetektorByNum(j).getError() == Detektor.FLATTERN) {  Detjitter[j] = true; }
                }
                Detjitter[j] = false;
            }

            for (j = 1; j < Detbusy23_30.length - 1; j++) {
            	index = 22 + j;
            	if  (Det.getDetektorByNum(index) != null) {
                    if  ((Det.getDetektorByNum(index).getBelSek() >= 900) && Det.getDetektorByNum(index).belegt()) {  
                    	Detbusy23_30[j] = true;
                	}
                }
            }
            if (GreenWave.SY != null) {
            	if  ((GreenWave.SY.getBelSek() >= 900) && GreenWave.SY.belegt()) {  
                	Detbusy23_30[Detbusy23_30.length - 1] = true;
            	}
            } else if (GreenWave.SY2 != null) {
            	if  ((GreenWave.SY2.getBelSek() >= 900) && GreenWave.SY2.belegt()) {  
                	Detbusy23_30[Detbusy23_30.length - 1] = true;
            	}
            } 
            
            for (j = 1; j < Detnotbusy23_30.length - 1; j++) {
            	index = 22 + j;
            	if  (Det.getDetektorByNum(index) != null) { 
                    if  ((Det.getDetektorByNum(index).getNichtBelSek() >= 86400) && !Det.getDetektorByNum(index).belegt()) {
                    	Detnotbusy23_30[j] = true;
                	}
                }
            }
            if (GreenWave.SY != null) {
            	if  ((GreenWave.SY.getNichtBelSek() >= 86400) && !GreenWave.SY.belegt()) {  
            		Detnotbusy23_30[Detnotbusy23_30.length - 1] = true;
            	}
            } else if (GreenWave.SY2 != null) {
            	if  ((GreenWave.SY2.getNichtBelSek() >= 86400) && !GreenWave.SY2.belegt()) {  
            		Detnotbusy23_30[Detnotbusy23_30.length - 1] = true;
            	}
            }
            
            for (j = 1; j < Detjitter23_30.length - 1; j++) {
            	index = 22 + j;
            	if  (Det.getDetektorByNum(index) != null) {
                    if  (Det.getDetektorByNum(index).getError() == Detektor.FLATTERN) { 
                    	Detjitter23_30[j] = true;
                	}
                }
                Detjitter23_30[j] = false;
            }

        //combine error messages depending of fault operating mode
            for (j = 1; j < Detector.length; j++) {
            	Detector[j] = combineErrors(DetectorFaultMode[j], Detbusy[j], Detnotbusy[j], Detjitter[j]);
            }
            for (j = 1; j < Detector23_30.length; j++) {
            	Detector23_30[j] = combineErrors(DetectorFaultMode23_30[j], Detbusy23_30[j], Detnotbusy23_30[j], Detjitter23_30[j]);
            }
        }
        
        int mask=0;
        for (j=1; j<9; j++) {
            if  (Detector[j]) { mask = mask | (1<<(j-1)); } 
        }
        DVI35Modul.setResIDx0(55, 0xFF, mask);
        mask=0;
        for (j=1; j<9; j++) {
            if  (Detector[j+8]) { mask = mask | (1<<(j-1)); } 
        }
        DVI35Modul.setResIDx0(56, 0xFF, mask);
        mask=0;
        for (j=1; j<7; j++) {
            if  (Detector[j+16]) { mask = mask | (1<<(j-1)); } 
        }
        DVI35Modul.setResIDx0(57, 0xFF, mask);
        mask=0;
        for (j=1; j<9; j++) {
            if  (StatusDetector23_30[j]) { mask = mask | (1<<(j-1)); } 
        }
        DVI35Modul.setResIDx0(27, 0xFF, mask);
        mask=0;
        for (j=1; j<9; j++) {
            if  (Detector23_30[j]) { mask = mask | (1<<(j-1)); } 
        }
        DVI35Modul.setResIDx0(28, 0xFF, mask);

    }

    public static int operateSigns()
    {
		Signs.signsOnFromCenter = false;
    	dvi35_status = DVI35Modul.getId1(dvi35_status_ind);
    	if ((dvi35_status & 0x40) == 0x40)
    		Signs.signsOnFromCenter = true;
    	
    	return useCentral;
    }

    public static void checkPrccGW()
    {
		for (j = 1; j <= tkanzahl; j++)
		{
			if (!((Node)TeilKnoten.getTeilKnoten(j)).isRegularProgram())
			{
				flagPrccGW = false;
				return;
			}
		}
		
		flagPrccGW = false;
		if ((flagProgCC == true) && (GreenWave.sync_fault == false)
				&& (Func.readParam(Var.tk1.getAktProg(), HaifaParametersIndexes.indIsCrossMaster) > 0)
				&& (Var.tk1.getAktProg().getNummer() == dvi35_prog))
		{
			flagPrccGW = true;
		}
    }

    /******************************************************************************/
    /******************************************************************************/
    
    public static boolean checkExitFromA_Central()
    {
    	//reading SY from the dvi35 telegram (position of SY defined in DVI35.INI)
    	//SY - the first bit
        return ((DVI35Modul.getId1(dvi35_sy_ind) & 0x01) == 0x01) || (useCentral == 0);
    }
    
    public static boolean checkExitFromA() {

  		if ((Var.tk1.getAktStgEbene() != StgEbene.STG_NUM_ZENTRALE)
  			&& (Var.tk1.getAktStgEbene() != StgEbene.STG_NUM_UHR))
  		{
  			return true;
  		}
  		
  		switch (opmodecc)
  		{
  		case clockmode: // no central conection or mode in clock
     			if ((Var.tk1.getAktStgEbene() == StgEbene.STG_NUM_UHR)
  				&& GreenWave.checkExitFromA_GW()) {
  				return true;
  			}
  			return false;
  			
  		case prccmode: // central mode is prcc: program number from control
  			// center, sy from master or permanent when no
  			// master
  			if (((Var.tk1.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE)
  				|| (Var.tk1.getAktStgEbene() == StgEbene.STG_NUM_UHR))
  				&& GreenWave.checkExitFromA_Prcc()) {
  				return true;
  			}
  			return false;
  			
  		case syccmode: // full control from the control center
  			if ((Var.tk1.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE)
  				&& checkExitFromA_Central()) {
  				return true;
  			}
  			return false;
  			
  		default:
  			return true;
  		}
  	}
    
    public static boolean ifInSYCC() {
    	
  		if (Var.tk1.getAktStgEbene() != StgEbene.STG_NUM_ZENTRALE) {return false;}
  		if (opmodecc != syccmode) {return false;}
  		return true;
    }
}
