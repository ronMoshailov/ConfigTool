package special;

import uhr.Zeit;
import enp.utilities.VtRam;

public class Log {

//    if (Var.controller.log != null) {
//        VtRam.init(60, 8404385, VtRam.DEVICE_ROM2);
//    }
    private void addLogPrefix() {
        VtRam.writeDDMM();
        VtRam.writeStr(" ");
        VtRam.writeHHMM();
        int s = Zeit.getSekunde();
        String second = (s < 10 ? "0" : "") + s;
        VtRam.writeStr(":" + second + ": ");
    }
    
    private void addLogSuffix() {
        VtRam.lineFeed();
    }
    
    public void addLogEntry(String content) {
        addLogPrefix();
        VtRam.writeStr(content);
        addLogSuffix();
    }
}
