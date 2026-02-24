package ta172;
/************************************************************************************************
 *                                                                                              *
 *  Contractor     : M E N O R A                                                                *
 *  City/Authority : Haifa																		*
 *  Junction No.   : 239                                                                     	*
 *  Junction Name  : Varburg / Rains / HaTkuma Yahiam - Maavar Hazaya							*
 *  Equipmentno.   : h239                                                                    	*
 *                                                                                              *
 ************************************************************************************************/
import ik.Handler;
import det.Ausgang;
import det.Detektor;
import enums.HaifaParametersIndexes;
import uhr.Uhr;
import vt.StgEbene;
import vt.TeilKnoten;
import vt.Vt;
import special.Func;
import special.Node;

public class GreenWave
{
	public static final boolean active           = true;
	public static final boolean inactive         = false;
	public static final boolean REGULAR          = false;
	public static final boolean BREATHING        = true;
	public static final boolean ULin_inactive    = false;
	public static final boolean ULin_active      = true;
	public static final boolean ULout_inactive   = false;
	public static final boolean ULout_active     = true;
	public static final boolean GWdelta_inactive = false;
	public static final boolean GWdelta_active   = true;
	public static final int     systemHaifa      = 1;
	public static final int     systemMaatz      = 2;
	public static final int     cyclesForFault   = 2;

	private static Node tk; //intersection name
	private static int system; //haifa or maatz

	public static boolean isUseIndicateros;

	//inputs
	public  static Detektor SY, pr_1, pr_2, pr_4, pr_8; //inputs for regular green wave slave
	public  static Detektor SY2; //input for breathing green wave master
	public  static Detektor ULin; //input for Continuous Signal Integrity, a.k.a.: CSI
	//outputs
	public  static Ausgang mSY, mpr_1, mpr_2, mpr_4, mpr_8; //output for regular green wave master
	public  static Ausgang mSY2; //output for breathing green wave slave
	public  static Ausgang ULout; //output for Continuous Signal Integrity, a.k.a.: CSI
	public  static Ausgang Led_GWfail, Led_GWactive, Led_OffsetSY; //leds for green wave

	//types of green wave
	private static boolean GWtype = REGULAR; //breathing or regular
	private static boolean useGreenWave_Slave; //true if the intersection is a green wave slave
	private static boolean useGreenWave_Master; //true if the intersection is a green wave master
	private static boolean useULin; //true if requires input for CSI 
	private static boolean useULout; //true if requires output for CSI
	private static boolean useGWdelta; //true if requires green wave delta

	//fault flags
	public static boolean sync_fault; //true if no pulse
	private static boolean gw_prog_fault; //true if prog number = 0
	private static boolean ul_fault;  //true if CSI timed-out
	private static boolean gw_fault; // = sync_fault || gw_prog_fault || ul_fault
	private static boolean enable_fault; // true if desired program is disabled for green wave use (doesn't count as green wave fault!!!)

	//program number flags
	private static int gw_prog; //number of green wave program from master (used in slave)
	private static int m_prog; //master's green wave program (used in master)
	private static int slaveGWprogFirstNum; //the number of the slave's green wave program
	private static int masterGWprogFirstNum = 1; //the number of master's first green wave program
	private static int masterGWprogQuant; //number of master's green wave programs
	private static int masterMaxProg; //number of master's last green wave program number
	private static int GWDelta = 0; //the delta for difference between green wave programs and regular programs. Used only by Maatz green wave slave
	private static boolean markmaster = false; //true in green wave slave when the current program = the desired program from master

	//pulse flags    
	private static int counterSYstate; //counts how long there wasn't a changed in the input of the green wave pulse
	private static int offset_counter; //counts the offset since the last received green wave pulse
	private static int SY_duration; //counts the duration of the input of the green wave pulse    
	private static int SYpulseSec; //for regular green wave master. Used to define on which second to set the pulse
	private static int SYpulseLen; //for regular green wave master. Used to define for how long to set the pulse
	private static int counter_setSY; //for regular green wave master. Used to count how long the pulse is set;
	private static int BGWpulseResetCounter; //for breathing green wave slave/master. counts how long ago was the output pulse set.
	private static boolean pulsSY; // = the state of the input pulse
	private static boolean old_pulsSY; // = the state of the input pulse in the last scan-cycle
	private static boolean oldSYstate, currSYstate;
	private static boolean rising_sync;
	private static boolean flag_pulsSY; //true "offset" seconds after pulse was received by slave or by breathing green wave master
	private static boolean flag_setSY = false; //true while regular green wave master sends its pulse
	public static boolean flag_OffsetSY;

	//UL (Continuous Signal Integrity) flags
	private static int ul_counter; //counts CSI time-out
	private static int ul_revive_counter; //counts CSI time-out when the device return to clock mode (or exits dark/flash progs)

	private static boolean markInGW; //true when slave started changing it's program to master's green wave program. resets to false when there is a program-related fault and uhr.update() was called once.
	public static int flag_GWactive; //true if green wave is active

	//green wave revive flags
	private static int pulse_revive_counter; //for green wave master or for breathing green wave slave. counts the time since last revive started
	private static int oldMasterProg = 0; //for master revival
	private static int oldProg, currProg; //for delta - used to check whether a program was changed
	public static int currebene, oldebene; //stores the current/old ebene (ebene = control level (manual, clock, central)). used for green wave master.
	private static boolean ebeneAuto; //true for one scan-cycle when green wave master's ebene was changed either to clock or to central. used for ResetFaultCounter()
	private static boolean enableRevive; //true when there is an "enable_fault" and there was a change in the program number
	private static boolean pulseRevive; //used to revive the breathing green wave pulse

	private static int tmp;

	private static int counterCoordination=0;

	public static boolean dvi35GW;
	//parameters
	private static int GW_SYfault_param;
	private static int GW_offset_param;
	private static int GW_ULfault_param;
	private static int GW_SYrevivefault_param;
	private static int GW_ULrevivefault_param;

	public static boolean flag_masterEnable = true; //constant (for green wave master) - don't change

	/******************************* GW Master *********************************/

	/**
	 * The method initializes the green wave master for Maatz
	 * @param node - intersection name
	 * @param flag - true for active green wave
	 * @param type - true for breathing green wave. false for regular green wave
	 * @param ulin - true if there's a requirement for ulin
	 * @param ulout - true if there's a requirement for ulout
	 * @param sec - second of program cycle that master sends sync pulse.
	 * @param len - width of sync pulse in seconds
	 * @param first - number of the first green wave program according application (Maatz only)
	 * @param quant - quantity of green wave programs (Maatz only)
	 **/
	public static void initGreenWaveMaster(Node node, boolean flag, boolean type, boolean ulin, boolean ulout, int sec, int len, int first, int quant)
	{
		if (Var.controller.isAppHaifa()) {
			initGreenWaveMasterHaifa(node, flag, type, ulin, ulout, sec, len);
		} else {
			initGreenWaveMasterMaatz(node, flag, type, ulin, ulout, sec, len, first, quant);
		}
	}

	/**
	 * The method initializes the green wave master for Maatz
	 * @param node - intersection name
	 * @param flag - true for active green wave
	 * @param type - true for breathing green wave. false for regular green wave
	 * @param ulin - true if there's a requirement for ulin
	 * @param ulout - true if there's a requirement for ulout
	 * @param sec - second of program cycle that master sends sync pulse.
	 * @param len - width of sync pulse in seconds
	 * @param first - number of the first green wave program according application
	 * @param quant - quantity of green wave programs
	 **/
	public static void initGreenWaveMasterMaatz(Node node, boolean flag, boolean type, boolean ulin, boolean ulout, int sec, int len, int first, int quant)
	{
		tk = node;

		if  (flag == active)
		{
			useGreenWave_Master = true;
			mSY       = new Ausgang(node,   "mSY"      ,   201 );
			mpr_1     = new Ausgang(node,   "mPR1"    ,   202 );
			mpr_2     = new Ausgang(node,   "mPR2"    ,   203 );
			mpr_4     = new Ausgang(node,   "mPR4"    ,   204 );
			mpr_8     = new Ausgang(node,   "mPR8"    ,   205 );

			GWtype = type;
			if (GWtype == BREATHING)
			{
				SY2 = new Detektor(node, "SY2", 0, 10, 0, 206 );
			}

			SYpulseSec = sec;
			SYpulseLen = len;
			masterGWprogFirstNum = first;
			masterGWprogQuant = quant;

			useULin = ulin;
			if (useULin)
			{
				ULin = new Detektor(node, "ULin", 0, 10, 0, 207 );
			}

			useULout = ulout;
			if (useULout)
			{
				ULout = new Ausgang(node, "ULout", 207 );
			}
		}
		else
		{
			useGreenWave_Master = false;
		}

		system = systemMaatz;
		masterMaxProg = masterGWprogQuant;
	}


	/**
	 * The method initializes the green wave master for Haifa
	 * @param node - intersection name
	 * @param flag - true for active green wave
	 * @param type - true for breathing green wave. false for regular green wave
	 * @param ulin - true if there's a requirement for ulin
	 * @param ulout - true if there's a requirement for ulout
	 * @param sec - second of program cycle that master sends sync pulse.
	 * @param len - width of sync pulse in seconds
	 **/
	public static void initGreenWaveMasterHaifa(Node node, boolean flag, boolean type, boolean ulin, boolean ulout, int sec, int len)
	{
		tk = node;

		if  (flag == active)
		{
			useGreenWave_Master = true;
			mSY       = new Ausgang(node,   "mSY"     ,   201 );
			mpr_1     = new Ausgang(node,   "mPR1"    ,   202 );
			mpr_2     = new Ausgang(node,   "mPR2"    ,   203 );
			mpr_4     = new Ausgang(node,   "mPR4"    ,   204 );
			mpr_8     = new Ausgang(node,   "mPR8"    ,   205 );

			GWtype = type;
			if (GWtype == BREATHING)
			{
				SY2 = new Detektor(node, "SY2", 0, 10, 0, 206 );
			}

			SYpulseSec = sec;
			SYpulseLen = len;

			useULin = ulin;
			if (useULin)
			{
				ULin = new Detektor(node, "ULin", 0, 10, 0, 207 );
			}

			useULout = ulout;
			if (useULout)
			{
				ULout = new Ausgang(node, "ULout", 207 );
			} 
		}
		else
		{
			useGreenWave_Master = false;
		}

		system = systemHaifa;
		masterMaxProg = Var.controller.getMaxProgramsNumber();
	}

	/**
	 * The method sets the required output lines for program number.
	 **/
	//	m_prog contains the program number for sending to slave on outputs lines.
	// for example: in case first green wave programs is 7 tmp will be 1
	private static void setMasterProg()
	{
		if ((system == systemHaifa) && (m_prog > 15))
		{
			mpr_1.resetAusgang();
			mpr_2.resetAusgang();
			mpr_4.resetAusgang();
			mpr_8.resetAusgang();
			return;
		}

		if ((m_prog & 1) == 1) {
			mpr_1.setAusgang();
		} else {
			mpr_1.resetAusgang();
		}

		if ((m_prog & 2) == 2) {
			mpr_2.setAusgang();
		} else {
			mpr_2.resetAusgang();
		}

		if ((m_prog & 4) == 4) {
			mpr_4.setAusgang();
		} else {
			mpr_4.resetAusgang();
		}

		if ((m_prog & 8) == 8) {
			mpr_8.setAusgang();
		} else {
			mpr_8.resetAusgang();
		}
	}

	/**
	 * The method sets the output line for regular master's pulse
	 **/
	private static void setMasterSYpuls()
	{
		if (tk.getProgSek() == SYpulseSec)
		{
			mSY.setAusgang();
			flag_setSY = true;
			counter_setSY = 0;
		}

		if (flag_setSY == true)
		{
			counter_setSY++;
			if (tk.getZyklDauer() == 500)
				tmp = SYpulseLen * 2;
			else
				tmp = SYpulseLen;
			if (counter_setSY > tmp)
			{
				flag_setSY = false;
				mSY.resetAusgang();
			}
		}
		else
		{
			mSY.resetAusgang();
		}
	}

	/**
	 * The method resets all regular master's output lines
	 **/
	private static void resetMasterOutputs()
	{
		flag_setSY = false;
		mSY.resetAusgang();
		mpr_1.resetAusgang();
		mpr_2.resetAusgang();
		mpr_4.resetAusgang();
		mpr_8.resetAusgang();
	}

	private static void sendMasterOutputs()
	{
		if (!useGreenWave_Master) {
			return ;
		}

		m_prog = tk.getAktProg().getNummer() + 1 - masterGWprogFirstNum;
		if ((flag_masterEnable == false) || (m_prog < 1) || (m_prog > masterMaxProg))
		{
			resetMasterOutputs();
		}
		else
		{
			setMasterProg();
			if (GWtype == REGULAR) {
				setMasterSYpuls();
			}
		}

	}

	/**
	 * The method checks for green wave Master whether the program is a green wave program
	 * @param progNumber - the number of the program to be checked
	 * @return true if the program is a green wave program. otherwise, false. 
	 **/
	private static boolean ifGWProg(int progNumber)
	{
		if (system == systemMaatz)
		{
			if ((progNumber >= masterGWprogFirstNum) && (progNumber <= masterGWprogFirstNum + masterGWprogQuant - 1))
			{
				return true;
			}
			return false;
		}
		else
		{
			if ((progNumber >= 1) && (progNumber <= masterMaxProg))
			{
				return true;
			}
			return false;
		}
	}


	/**
	 * The method checks for green wave Master whether there is a ULin fault.
	 * Also sets the ULout output (if used), and Led_GWfail accordingly.
	 **/
	private static void MasterULFault()
	{
		checkULFault();
		if (useULout)
		{
			if (!ul_fault || !useULin)
			{
				if (isUseIndicateros) {
					Led_GWfail.resetAusgang();
				}

				if (ifGWProg(tk.getAktProg().getNummer()))
				{
					ULout.setAusgang();
				}
				else
				{
					ULout.resetAusgang();
				}
			}
			else // ul_fault && useULin
			{
				if (isUseIndicateros) {
					Led_GWfail.setAusgang();
				}
				ULout.resetAusgang();
			}
		}
		else // !useULout
		{
			if (useULin)
			{
				if (ul_fault)
				{
					if (isUseIndicateros) {
						Led_GWfail.setAusgang();
					}
				}
				else
				{
					if (isUseIndicateros) {
						Led_GWfail.resetAusgang();
					}
				}
			}
		}
	}

	/**
	 * The method checks for green wave Master whether the ebene (control level (clock, manual or central)) was changed to clock or to central
	 **/
	public static void CheckEbene()
	{
		if (!useGreenWave_Master)
		{
			return;
		}
		currebene = tk.getAktStgEbene();
		if ((currebene == StgEbene.STG_NUM_UHR) || (currebene == StgEbene.STG_NUM_ZENTRALE))
		{
			if (oldebene != currebene)
			{
				ebeneAuto = true;
				ResetFaultCounter();
			}
			else
			{
				ebeneAuto = false;
			}
		}
		else
		{
			ebeneAuto = false;
		}
		oldebene = currebene;
	}

	/***************************** Breathing GW Master or Slave *****************************/

	/**
	 * The method sets the output pulse for the breathing green wave master/slave
	 **/
	public static void SetBGWPulse()
	{
		if (GWtype != BREATHING) {
			return;
		}

		BGWpulseResetCounter = 0;

		if (useGreenWave_Master)
		{
			if (ifGWProg(tk.getAktProg().getNummer()))
			{
				mSY.setAusgang();
			}
		}

		if (useGreenWave_Slave)
		{
			if (((readGW_enable(tk.getAktProg()) > 0) && !gw_fault && !enable_fault) || pulseRevive)
			{
				mSY2.setAusgang();
			}
		}
	}

	/**
	 * The method resets the output pulse for the breathing green wave master/slave
	 **/
	public static void resetBGWPulse()
	{
		if (!Var.tk1.isRegularProgram())
		{
			if (useGreenWave_Master)
			{
				mSY.resetAusgang();
			}

			if (useGreenWave_Slave)
			{
				mSY2.resetAusgang();
			}
		}
		else
		{
			if (BGWpulseResetCounter >= 2000)
			{
				if (useGreenWave_Master)
				{
					mSY.resetAusgang();
				}

				if (useGreenWave_Slave)
				{
					mSY2.resetAusgang();
				}
			}
		}
	}

	/***************************** GW Master or Slave *****************************/

	/**
	 * The method counts how much time has passed since the ULin input was last reset
	 **/
	public static void CountULTimeout()
	{
		if (ul_revive_counter < 999000) { ul_revive_counter += tk.getZyklDauer(); }

		if (!useULin)
		{
			return;
		}

		if (ULin.belegt())
		{
			ul_counter = 0;
			ul_fault = false;
		}
		else
		{
			if (ul_counter < 999000) { ul_counter += tk.getZyklDauer(); }
		}
	}

	/**
	 * The method checks whether there is a ULin fault
	 * @return true when ULin isn't used or if ULin timed-out and revive of green wave failed
	 **/
	public static void checkULFault()
	{
		if (!useULin)
		{
			ul_fault = true;
			return;
		}

		GW_ULfault_param = Var.controller.getUlFailParam();

		if (reviveULFail() && (ul_fault || (ul_counter >= GW_ULfault_param)))
		{
			ul_fault = true;
		}
		else
		{
			ul_fault = false;
		}
	}

	/**
	 * This functions checks whether the greenwave can be "revived"
	 * @return true when revive FAILED!!!
	 **/
	public static boolean reviveULFail()
	{
		if (useULout || useULin)
		{
			GW_ULrevivefault_param = tk.getCycleParam() * cyclesForFault;

			if (ul_revive_counter >= GW_ULrevivefault_param)
			{
				return true;
			}
		}
		return false;
	}

	/********************************** GW Slave **********************************/


	/**
	 * The method initializes the green wave slave
	 * @param node - intersection name
	 * @param flag - true for active green wave
	 * @param type - true for breathing green wave. false for regular green wave
	 * @param ulin - true if there's a requirement for ulin
	 * @param ulout - true if there's a requirement for ulout
	 * @param first - number of the first green wave program according to the application (Maatz only)
	 * @param flagDelta - true if there's a requirement for delta
	 * @param delta - the result of the following equation: ((first greenwave program number) - (first NONE greenwave program number)) = delta (could be positive, negative or 0)
	 * 				  it's important to make sure that if delta is used, it should be the same for all the programs.
	 **/
	public static void initGreenWaveSlave(Node node, boolean flag, boolean type, boolean ulin, boolean ulout, int first, boolean flagDelta, int delta)
	{
		isUseIndicateros = Var.controller.isSynopticMap();
		if (Var.controller.isAppHaifa()) {
			initGreenWaveSlaveHaifa(node, flag, type, ulin, ulout, flagDelta, delta);
		} else {
			initGreenWaveSlaveMaatz(node, flag, type, ulin, ulout, first, flagDelta, delta);
		}
	}

	/**
	 * The method initializes the green wave slave for Maatz
	 * @param node - intersection name
	 * @param flag - true for active green wave
	 * @param type - true for breathing green wave. false for regular green wave
	 * @param ulin - true if there's a requirement for ulin
	 * @param ulout - true if there's a requirement for ulout
	 * @param first - number of the first green wave program according to the application
	 * @param flagDelta - true if there's a requirement for delta
	 * @param delta - the result of the following equation: ((first greenwave program number) - (first NONE greenwave program number)) = delta (could be positive, negative or 0)
	 * 				  it's important to make sure that if delta is used, it should be the same for all the programs.
	 **/
	public static void initGreenWaveSlaveMaatz(Node node, boolean flag, boolean type, boolean ulin, boolean ulout, int first, boolean flagDelta, int delta)
	{
		tk = node;
		// leds for green wave
		if (isUseIndicateros) {
			Led_GWfail   = new Ausgang(node, "Led_GWfail"  , 211);
			Led_GWactive = new Ausgang(node, "Led_GWactive", 212);
			Led_OffsetSY = new Ausgang(node, "Led_OffsetSY", 213);
		}
		flag_GWactive = 0;
		dvi35GW = false;

		if  (flag == active)
		{
			useGreenWave_Slave = true;
			SY       = new Detektor(node,   "SY"     ,    0   ,   10    ,   0    ,   201 );
			pr_1     = new Detektor(node,   "PR1"    ,    0   ,   10    ,   0    ,   202 );
			pr_2     = new Detektor(node,   "PR2"    ,    0   ,   10    ,   0    ,   203 );
			pr_4     = new Detektor(node,   "PR4"    ,    0   ,   10    ,   0    ,   204 );
			pr_8     = new Detektor(node,   "PR8"    ,    0   ,   10    ,   0    ,   205 );

			useGWdelta = flagDelta;
			GWtype = type;
			if (GWtype == BREATHING)
			{
				mSY2 = new Ausgang(node, "mSY2", 206 );
				if (useGWdelta)
				{
					GWDelta = delta;
				}
			}

			slaveGWprogFirstNum = first;

			useULin = ulin;
			if (useULin)
			{
				ULin = new Detektor(node, "ULin", 0, 10, 0, 207 );
			}

			useULout = ulout;
			if (useULout)
			{
				ULout = new Ausgang(node, "ULout", 207 );
				if (useGWdelta)
				{
					GWDelta = delta;
				}
			}
		}
		else
		{
			useGreenWave_Slave = false;
		}
	}


	/**
	 * The method initializes the green wave slave for Haifa
	 * @param node - intersection name
	 * @param flag - true for active green wave
	 * @param type - true for breathing green wave. false for regular green wave
	 * @param ulin - true if there's a requirement for ulin
	 * @param ulout - true if there's a requirement for ulout
	 * @param flagDelta - true if there's a requirement for delta
	 * @param delta - the result of the following equation: ((first greenwave program number) - (first NONE greenwave program number)) = delta (could be positive, negative or 0)
	 * 				  it's important to make sure that if delta is used, it should be the same for all the programs.
	 **/
	public static void initGreenWaveSlaveHaifa(Node node, boolean flag, boolean type, boolean ulin, boolean ulout, boolean flagDelta, int delta)
	{
		tk = node;
		// leds for green wave
		if (isUseIndicateros) {
			Led_GWfail   = new Ausgang(node, "Led_GWfail"  , 211);
			Led_GWactive = new Ausgang(node, "Led_GWactive", 212);
			Led_OffsetSY = new Ausgang(node, "Led_OffsetSY", 213);
		}
		flag_GWactive = 0;
		dvi35GW = false;

		if  (flag == active)
		{
			useGreenWave_Slave = true;
			SY       = new Detektor(node,   "SY"    ,    0   ,   10    ,   0    ,   201 );
			pr_1     = new Detektor(node,   "PR1"    ,    0   ,   10    ,   0    ,   202 );
			pr_2     = new Detektor(node,   "PR2"    ,    0   ,   10    ,   0    ,   203 );
			pr_4     = new Detektor(node,   "PR4"    ,    0   ,   10    ,   0    ,   204 );
			pr_8     = new Detektor(node,   "PR8"    ,    0   ,   10    ,   0    ,   205 );

			useGWdelta = flagDelta;
			GWtype = type;
			if (GWtype == BREATHING)
			{
				mSY2 = new Ausgang(node, "mSY2", 206 );
				if (useGWdelta)
				{
					GWDelta = delta;
				}
			}

			slaveGWprogFirstNum = 1;

			useULin = ulin;
			if (useULin)
			{
				ULin = new Detektor(node, "ULin", 0, 10, 0, 207 );
			}

			useULout = ulout;
			if (useULout)
			{
				ULout = new Ausgang(node, "ULout", 207 );
				if (useGWdelta)
				{
					GWDelta = delta;
				}
			}        	
		}
		else
		{
			useGreenWave_Slave = false;
		}
	}


	/**
	 * The method resets all flags and counters indicating various faults
	 **/
	public static void ResetFaultCounter()
	{
		if (useGreenWave_Slave) {
			oldSYstate = SY.belegt();
		}
		else if (GWtype == BREATHING && useGreenWave_Master) {
			oldSYstate = SY2.belegt();
		}
		else {
			oldSYstate = false;
		}

		sync_fault = false;
		counterSYstate = 0;
		offset_counter = 0;
		pulsSY = false; 
		old_pulsSY = false;
		SY_duration = 0;
		rising_sync = false;
		flag_pulsSY = false;
		oldProg = 0;
		enable_fault = false;
		enableRevive = false;
		pulseRevive = true;
		pulse_revive_counter = 0;

		if (useGreenWave_Slave || (useGreenWave_Master && (!tk.isRegularProgram() || ebeneAuto)))
		{
			ul_fault = false;
			ul_counter = 0;
			ul_revive_counter = 0;
			if (useULout)
			{
				ULout.resetAusgang();
			}
		}

		programmauswahl();
		if ((useGreenWave_Slave || useGreenWave_Master) && (!gw_prog_fault))
		{
			gw_fault = false;
			if (isUseIndicateros) {
				Led_GWfail.resetAusgang();
			}
			flag_GWactive = 0;
		}
	}


	/**
	 * The method checks, for green wave Slave, what program was received from the master
	 * If the received program's number is invalid (0 or exceeds the defined program number) then counts as fault 
	 **/
	private static void programmauswahl()
	{
		tmp = 0;
		if (!useGreenWave_Slave)
			return ;

		if  (pr_1.eingangGesetzt()) { tmp = 1;  }
		if  (pr_2.eingangGesetzt()) { tmp = tmp + 2;  }
		if  (pr_4.eingangGesetzt()) { tmp = tmp + 4;  }
		if  (pr_8.eingangGesetzt()) { tmp = tmp + 8;  }

		gw_prog = tmp + slaveGWprogFirstNum - 1;

		if ((tmp == 0) || (gw_prog < 1) || (gw_prog > Var.controller.getMaxProgramsNumber()))
		{
			gw_prog = 0;
			gw_prog_fault = true;
		}
		else
			gw_prog_fault = false;
	}


	/**
	 * The method checks for Slave or for breathing green wave master whether the input pulse is ok.
	 * If there wasn't a changed in the pulse input line for a longer period of time than define, counts as fault 
	 **/
	private static void checkSYfault()
	{
		if (useGreenWave_Slave || (useGreenWave_Master && GWtype == BREATHING))
		{
			if (useGreenWave_Slave)
			{
				currSYstate = SY.belegt();
			}
			else
			{
				currSYstate = SY2.belegt();
			}

			GW_SYfault_param = (tk.getCycleParam() / Var.ONE_SEC) * cyclesForFault;

			if (tk.getZyklDauer() == 500)
			{
				GW_SYfault_param *= 2;
			}

			if (currSYstate != oldSYstate)
			{
				counterSYstate = 0;
				oldSYstate = currSYstate;
			}
			else
			{
				if (counterSYstate < 999000)
					counterSYstate++;
			}

			if (counterSYstate >= GW_SYfault_param)
			{
				sync_fault = true;
				counterSYstate = 999000;
			}
			else
			{
				sync_fault = false;
			}
		}    	
	}

	/**
	 * The method checks for for both master and slave whether there is a green wave fault
	 * Also, the method sets the green wave leds accordingly.
	 **/
	private static void checkGWfault()
	{
		if (!useGreenWave_Master && !useGreenWave_Slave)
		{
			return;
		}

		if (useGreenWave_Master && GWtype == REGULAR)
		{
			MasterULFault();
			if (!useGreenWave_Slave) //if only master
			{
				return;
			}
		}

		//useGreenWave_Slave || (useGreenWave_Master && GWtype == BREATHING)
		programmauswahl();
		checkSYfault();
		checkULFault();

		if ((!sync_fault) && (!gw_prog_fault) && (!useULin || !ul_fault))
		{
			if (useGreenWave_Master) //master in breathing green wave
			{
				if (ifGWProg(tk.getAktProg().getNummer()))
				{
					flag_GWactive = 1;
					if (useULout)
					{
						ULout.setAusgang();
					}
				}
				else
				{
					flag_GWactive = 0;
					if (useULout)
					{
						ULout.resetAusgang();
					}
				}
			}
			else //useGreenWave_Slave
			{
				if (useULout)
				{
					if (readGW_enable(Vt.findProgByNum(gw_prog)) > 0)
					{
						ULout.setAusgang();
					}
					else
					{
						ULout.resetAusgang();
					}
				}
			}
			gw_fault = false;
			if (isUseIndicateros) {
				Led_GWfail.resetAusgang();
			}
		}
		else //gw fault
		{
			flag_GWactive = 0;
			gw_fault = true;
			if (isUseIndicateros) {
				Led_GWfail.setAusgang();
			}

			if (useULout)
			{
				if (reviveULFail()) //if UL revive is NOT possible
				{
					ULout.resetAusgang();
				}
				else
				{
					ULout.setAusgang();
				}
			}
		}
	}


	/**
	 * The method counts how much time has passed since the last change in the input pulse line.
	 * If the change hasn't occurred over the defined time, counts as fault
	 **/
	public static void checkSYpuls()
	{
		if (useGreenWave_Slave || (useGreenWave_Master && GWtype == BREATHING))
		{
			if (tk.getProgSek() == 0)
			{
				if (isUseIndicateros) {
					Led_OffsetSY.resetAusgang();
				}
				flag_pulsSY = false;
				offset_counter = 0;
				rising_sync = false;
			}

			GW_offset_param = tk.getOffsetParam();

			if (tk.getZyklDauer() == 500)
				GW_offset_param *= 2;

			if (useGreenWave_Slave) //if slave in green wave
			{
				pulsSY = SY.belegt();
			}
			else //master in breathing green wave
			{
				pulsSY = SY2.belegt();
			}

			if (pulsSY == true)
			{
				if (old_pulsSY == false)  // first time SYNC input true
				{
					old_pulsSY = true;
					SY_duration = 0;
					offset_counter = 0;
					rising_sync = true;
				}
				else
				{
					SY_duration++;
				}
			}
			else
				old_pulsSY = false;

			if (rising_sync == true)
			{
				offset_counter++;
			}

			if (offset_counter >= GW_offset_param)
			{
				if(SY_duration > 0)
				{
					if (isUseIndicateros) {
						Led_OffsetSY.setAusgang();
					}
					flag_pulsSY = true;
					SY_duration--;
				}
				else
				{
					if (isUseIndicateros) {
						Led_OffsetSY.resetAusgang();
					}
					flag_pulsSY = false;
					offset_counter = 0;
					rising_sync = false;
				}
			}
		}
	}


	/**
	 * The method returns the "green wave enable" parameter for the program
	 * @param prog - the program to be checked
	 * @return the value of the enable parameter of the program
	 **/
	private static int readGW_enable(vt.ProgBase prog)
	{
		if (system == systemMaatz)
			return Func.read_intarry(prog)[Var.indGWenableParam];
		else
			return Func.readParam(prog, HaifaParametersIndexes.indIsCrossMaster);
	}

	/**
	 * This method central must be retrieved in the operationalMain class. In this method the program
	 * number chosen form the central unit/master is transformed into a program request for the operating system
	 */
	private static void setDesiredProgram()
	{
		int tkanzahl = vt.TeilKnoten.getAnzahl();
		vt.Programm progId;

		if (!useGreenWave_Slave)
		{
			if (useGreenWave_Master && (GWtype == REGULAR))
			{
				flag_GWactive = 0;
			}
			return ;
		}

		programmauswahl();
		progId = Vt.findProgByNum(gw_prog, tk.getNummer());

		if ((progId != null) && (!gw_fault) && (readGW_enable(progId) > 0))
		{
			for (tmp = 1; tmp <= tkanzahl; tmp++)
			{
				TeilKnoten.getTeilKnoten(tmp).setProgWunsch(Vt.findProgByNum(gw_prog, TeilKnoten.getTeilKnoten(tmp).getNummer()), StgEbene.STG_UHR);
				markInGW = true;
			}
			flag_GWactive = 1;
			if (tk.getAktProg() == progId)
			{
				markmaster = true;
			}
			enable_fault = false;
			oldProg = 0;
		}
		else
		{
			if ((progId != null) && (!gw_fault) && ((GWtype == BREATHING) || useULout))
			{
				if (oldProg == 0)
				{
					oldProg = gw_prog - GWDelta;
					enable_fault = true;
				}
			}

			flag_GWactive = 2;
			markmaster = false;
			if (markInGW)
			{
				markInGW = false;
				Uhr.update();
			}

			if (useGWdelta)
			{
				checkChangedProgram();
			}
		}
	}

	/**
	 * The method checks if there was an enable_fault whether the program has changed since the fault occurred.
	 **/
	private static void checkChangedProgram()
	{
		//slave
		//progId = null || gw_fault || readGW_enable(progId) = 0
		if (!enable_fault
				|| (oldProg + GWDelta < slaveGWprogFirstNum) || (oldProg + GWDelta > Var.controller.getMaxProgramsNumber())) 
		{
			return;
		}

		if (gw_prog != 0)
		{
			currProg = gw_prog - GWDelta;
		}
		else
		{
			currProg = tk.getAktProg().getNummer();
		}

		if ((currProg + GWDelta < slaveGWprogFirstNum) || (currProg + GWDelta > Var.controller.getMaxProgramsNumber()))
		{
			return;
		}

		if (currProg != oldProg)
		{
			enableRevive = true;
			oldProg = currProg;

			if (enableRevive)
			{
				enableRevive = false;
				checkDeltaProgEnable();
			}
		}
	}

	/**
	 * The method checks whether the desired program's enable parameter is not 0.
	 * The desired program is the parallel green wave program to the current independent program.
	 **/
	private static void checkDeltaProgEnable()
	{
		vt.Programm progId;
		progId = Vt.findProgByNum((currProg+GWDelta), tk.getNummer());

		if (readGW_enable(progId) > 0)
		{
			if (useULout)
			{
				ul_revive_counter = 0;
			}

			if (GWtype == BREATHING)
			{
				pulse_revive_counter = 0;
				pulseRevive = true;
			}
		}
	}

	/******************************************************************************/

	public static void operateGW()
	{
		CheckEbene();

		if (BGWpulseResetCounter < 999000) { BGWpulseResetCounter += tk.getZyklDauer(); }

		//for ULin check (if activated)
		CountULTimeout();

		if (tk.isRegularProgram())
		{
			checkSYpuls();

			if (useGreenWave_Master && (useULin || useULout || (GWtype == BREATHING))) //revives the master if switched from none-green-wave program to green wave program
			{
				if (ifGWProg(tk.getAktProg().getNummer()))
				{
					if (!ifGWProg(oldMasterProg))
					{
						if (useULin || useULout)
						{
							ul_revive_counter = 0;
						}

						if (GWtype == BREATHING)
						{
							pulseRevive = true;
							pulse_revive_counter = 0;
						}
					}
				}
				oldMasterProg = tk.getAktProg().getNummer();
			}

			if (((tk.getAktStgEbene() == StgEbene.STG_NUM_UHR) ||
					((tk.getAktStgEbene() == StgEbene.STG_NUM_ZENTRALE) && ((dvi35GW) || (system == systemMaatz)))))
			{
				//check GWfalt and central only if actual program is one of the user programs
				checkGWfault();

				if (GWtype == BREATHING && useGreenWave_Slave)
				{
					if (pulseRevive)
					{	    				
						if (pulse_revive_counter < 999000) { pulse_revive_counter += tk.getZyklDauer(); }

						GW_SYrevivefault_param = tk.getCycleParam() * cyclesForFault;

						if (pulse_revive_counter >= GW_SYrevivefault_param + 2000)
						{
							pulseRevive = false;
						}
					}
				}

				setDesiredProgram();
			}
			else //manual or Central Haifa without DVI35 GreenWave
			{
				if (useGreenWave_Master)
				{
					checkGWfault();
				}

				ResetFaultCounter();
			}
		}
		else //special program
		{
			ResetFaultCounter();
		}

		//for green wave MASTER
		sendMasterOutputs();

		//resets the output pulses for breathing green wave
		if (GWtype == BREATHING)
		{
			resetBGWPulse();
		}
	}

	public static void SetClockCoordinationLeds(Node tn)
	{
		if (Func.ifClockCoordination(tn, tn.getAktProg()))
		{
			if (ik.MyHandler.testIk(tn, Handler.VAR_RR_NULL_UHR, tn.getAktProg()))
			{
				flag_OffsetSY = true;
			}
			else
			{
				flag_OffsetSY = false;
			}

			if (tn.getProgSek() == tn.getAktProg().getGwpB())
			{
				if (counterCoordination < 2)
				{
					if (flag_OffsetSY)
					{
						if (isUseIndicateros) {
							Led_GWfail.resetAusgang();
						}
						counterCoordination = 100;
					}
					else
					{
						counterCoordination++;
						if (isUseIndicateros) {
							Led_GWactive.setAusgang();
						}
					}
				}

				if (counterCoordination == 2)
				{
					if (isUseIndicateros) {
						Led_GWfail.setAusgang();
					}
				}
			}
			else /* program second is not GwpB */
			{
				counterCoordination = 0;
				if (isUseIndicateros) {
					Led_GWactive.resetAusgang();
				}
			}
		}
		else /* is not clock coordination */
		{
			flag_OffsetSY = false;
			if (isUseIndicateros) {
				Led_GWfail.resetAusgang();
				Led_GWactive.resetAusgang();
			}
		}
	}

	public static void SetGWactiveLed()
	{
		/******************************* Clock Coordination *******************************/

		if ((!useGreenWave_Master) && (!useGreenWave_Slave))
		{
			if (tk.isRegularProgram() && (tk.getAktStgEbene() == StgEbene.STG_NUM_UHR))
			{
				SetClockCoordinationLeds(tk);
			}
			else /* tk1 is not in logic program */
			{
				flag_OffsetSY = false;
				if (isUseIndicateros) {
					Led_GWfail.resetAusgang();
					Led_GWactive.resetAusgang();
				}
			}

			if (isUseIndicateros) {
				if (flag_OffsetSY)
					Led_OffsetSY.setAusgang();
				else
					Led_OffsetSY.resetAusgang();
			}
		}
		else //master or slave
		{
			if (isUseIndicateros) {
				if (flag_GWactive == 1)
					Led_GWactive.setAusgang();
				else
					Led_GWactive.resetAusgang();
			}
		}
	}

	public static boolean checkExitFromA_GW()
	{
		if (!useGreenWave_Slave && GWtype == REGULAR)
			return true;

		if ((tk.getAktStgEbene() != StgEbene.STG_NUM_ZENTRALE) &&
				(tk.getAktStgEbene() != StgEbene.STG_NUM_UHR))
		{
			return true;
		}

		if (!tk.isRegularProgram())
			return true;

		if (gw_fault || flag_pulsSY || (useGreenWave_Slave && ((readGW_enable(tk.getAktProg()) == 0) ||!markmaster)))
		{
			return true;
		}

		return false;
	}

	/******************************* Control Center *******************************/

	public static boolean checkExitFromA_Prcc() {
		if (!useGreenWave_Slave && GWtype == REGULAR)
		{
			return true;
		}

		if ((tk.getAktStgEbene() != StgEbene.STG_NUM_ZENTRALE) &&
				(tk.getAktStgEbene() != StgEbene.STG_NUM_UHR))
		{
			return true;
		}

		if (!tk.isRegularProgram())
			return true;

		if (sync_fault || flag_pulsSY || (useGreenWave_Slave && (readGW_enable(tk.getAktProg()) == 0)))
		{
			if (tk.getAktStgEbene() == StgEbene.STG_NUM_UHR) {markmaster = false;}
			return true;
		}
		return false;
	}
}
