package pars;
import vtvar.*;

public class Pars_SP00 extends ParameterSatz
{
	// satzgebundene Parameter:
	public ParaInt Max, Min, MinMax_Prm;

	public Pars_SP00(String name, ParaRegistryChooser chooser) {
		super (name, true, true, true);
		setChooser(chooser);
	}
}
