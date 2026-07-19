package pars;
import vtvar.*;

public class Pars_Res extends ParameterSatz
{
	// satzgebundene Parameter:
	public ParaInt _01, _02, _03, _04, _05, _06, _07, _08, _09, _10, _11, _12, _13;

	public Pars_Res(String name, ParaRegistryChooser chooser) {
		super (name, true, true, true);
		setChooser(chooser);
	}
}
