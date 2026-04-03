package pars;
import vtvar.*;

public class Pars_SP04 extends ParameterSatz
{
	// satzgebundene Parameter:
	public ParaInt Max, Min, MinMax_Prm;

	public Pars_SP04(String name, ParaRegistryChooser chooser) {
		super (name, true, true, true);
		setChooser(chooser);
	}
}
