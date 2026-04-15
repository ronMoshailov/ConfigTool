package core.detectors;

import core.Node;
import fehlerhandler.FehlerHandler;

public class MonitoredInput extends Input {

    private boolean gesendet, gesendet1;
    private String switchToOnMsg, switchToOffMsg;
    
    public MonitoredInput(Node node, String name, int id, String switchToOnMsg, String switchToOffMsg) {
        super(node, name, id);
        this.switchToOnMsg = switchToOnMsg;
        this.switchToOffMsg = switchToOffMsg;
        addStau();
    }
    
    public void updateState() {
        super.updateState();

        if (eingangGesetzt() && stau(2000)) {
            if  (!gesendet) {
                gesendet = true;
                gesendet1 = false;
                FehlerHandler.writeMsg(FehlerHandler.errError, true, getTk().getNummer(), switchToOnMsg);
            }
        } else {
            if  (!gesendet1) {
                gesendet1 = true;
                gesendet = false;
                FehlerHandler.writeMsg(FehlerHandler.errInfo, true, getTk().getNummer(), switchToOffMsg);
            }
        }
    }
    
}
