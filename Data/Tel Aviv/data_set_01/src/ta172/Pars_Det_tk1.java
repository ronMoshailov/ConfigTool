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
import vtvar.*;

public class Pars_Det_tk1 extends ParameterSatz
{
	// satzgebundene Parameter:
	public ParaInt Det_01, Det_02, Det_03, Det_04, Det_05, Det_06, Det_07, Det_08, Det_09, Det_10,
				   Det_11, Det_12, Det_13, Det_14, Det_15, Det_16, Det_17, Det_18, Det_19, Det_20;

	public Pars_Det_tk1(String name, ParaRegistryChooser chooser) {
		super (name, true, true, true);
		setChooser(chooser);
	}
	
	public boolean isDemandSet(int index) {
		ParaInt param = getParameterByIndex(index);
		if (param == null) {
			return false;
		}
		return (param.get() == 1 || param.get() == 2);
	}
	
	public boolean isExtensionSet(int index) {
		ParaInt param = getParameterByIndex(index);
		if (param == null) {
			return false;
		}
		return (param.get() == 1 || param.get() == 3);
	}
	
	public ParaInt getParameterByIndex(int index) {
		switch (index) {
		case 1:
			return Det_01;
		case 2:
			return Det_02;
		case 3:
			return Det_03;
		case 4:
			return Det_04;
		case 5:
			return Det_05;
		case 6:
			return Det_06;
		case 7:
			return Det_07;
		case 8:
			return Det_08;
		case 9:
			return Det_09;
		case 10:
			return Det_10;
		case 11:
			return Det_11;
		case 12:
			return Det_12;
		case 13:
			return Det_13;
		case 14:
			return Det_14;
		case 15:
			return Det_15;
		case 16:
			return Det_16;
		case 17:
			return Det_17;
		case 18:
			return Det_18;
		case 19:
			return Det_19;
		case 20:
			return Det_20;
		default:
			return null;
		}
	}
}
