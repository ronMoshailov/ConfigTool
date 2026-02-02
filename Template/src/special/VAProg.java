package special;
import ta172.ParamSet;
import ta172.ParametersJerusalem;
import ta172.Var;
import vt.*;

public class VAProg extends LogikProg {
    Node _tk;
    
	public VAProg(Node kn, String name, int num,
			int umlZeit, int gwpa, int gwpb, int warteZeit,
			int versatzZeit) {
		super(kn, name, num, umlZeit, gwpa, gwpb, warteZeit, versatzZeit);
		_tk = kn;
	}

	public void programmFunktion() {
		if (Var.controller.isAppHaifa() || Var.controller.isAppJerusalem() || Var.controller.isAppTelAviv())
		{
			if (_tk.isRegularProgram() || _tk.isInMaintenance())
            {
				Var.controller.dvi35Parameters.parameters = Var.controller.dvi35Parameters.readParamArray(this); 
            }
			
			if (Var.controller.isAppJerusalem())
			{
				ParametersJerusalem.SetParametersValues();
			}
			else
			{
				ParamSet.setParams((Node)this.getTeilKnoten());
			}
		}
	}
}