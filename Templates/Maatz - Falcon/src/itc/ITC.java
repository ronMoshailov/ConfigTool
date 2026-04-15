package itc;

import m0547.Var;
import core.Node;
import vt.StgEbene;

public class ITC {
	public  final static int PROGRAM_LINES_NUMBER        =    5;
	private final static int DELAY_BEFORE_REFRESHING_UHR = 1000;
	
	private Node node = null;
	
	private boolean isProgramInputsError;
	private boolean isExternalControl;
	private int programOutput   = 0;
	
	private int receivedProgram = 0;
	private int refreshDelay    = 0; 
	
	private boolean wasItcProgramSet = false;
	
	public ITC(Node node) {
		this.node = node;
	}
	
	public boolean isExternalControl() {
	    return isExternalControl;
	}
	
	public int getRequestedProgram() {
	    return receivedProgram;
	}
	
	public void runITCLogic() {
	    if (!Var.controller.itcProtocol.isActivateProtocol) {
	        return;
	    }
	    
		receivedProgram = Var.controller.itcProtocol.getInputProgram();
		
		if (receivedProgram > 0 && receivedProgram <= Var.controller.getMaxProgramsNumber()) {
			refreshDelay = 0;
			if (isProgramInputsError)
				isProgramInputsError = false;
			
			if (node.clockProgram != receivedProgram) {
			    node.clockProgram = receivedProgram;
			}

			wasItcProgramSet = true;
		} else {
			if (wasItcProgramSet && refreshDelay >= DELAY_BEFORE_REFRESHING_UHR) {
//				node.clockProgram = Var.no;
				wasItcProgramSet = false;
			}
			
			if (!isProgramInputsError)
				isProgramInputsError = true;
			
			refreshDelay += node.getZyklDauer();
		}

		setExternalControlOutput();
		setProgramOutputs();
	}
	
	private boolean isItcInputError() {
		return isProgramInputsError;
	}
	
	private void setExternalControlOutput() {
		if (node.getAktStgEbene() == StgEbene.STG_NUM_UHR && !isItcInputError()) {
			isExternalControl = true;
		} else {
			isExternalControl = false;
		}
	}
	
	private void setProgramOutputs() {
		programOutput = node.getAktProg().getNummer();
		Var.controller.itcProtocol.setData(isProgramInputsError, isExternalControl, programOutput);
	}
	
	public boolean getErrorOutput() {
		return isProgramInputsError;
	}
	
	public boolean getExternalControl() {
		return isExternalControl;
	}
}
