package pars;
import vtvar.*;

public class Pars_SP07 extends ParameterSatz
{
	// satzgebundene Parameter:
	public ParaInt Max, Min, MinMax_Prm;

	public Pars_SP07(String name, ParaRegistryChooser chooser) {
		super (name, true, true, true);
		setChooser(chooser);
	}
}
