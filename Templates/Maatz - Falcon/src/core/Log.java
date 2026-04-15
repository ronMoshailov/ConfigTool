package core;

import uhr.Zeit;
import enp.utilities.VtRam;

public class Log {

    
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
