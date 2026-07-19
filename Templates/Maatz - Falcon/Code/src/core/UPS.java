package core;

import m0547.Var;
import core.detectors.MonitoredInput;

/**
 * This class handles the definition and initialization of the UPS inputs depending on the application type 
 * @author Ilia Butvinnik
 * @version 2.0.0
 * @since 01/07/2025
 */
public class UPS {
    private static final String upsActiveMsgOn       = "UPS is active (NOT OK)";
    private static final String upsActiveMsgOff      = "UPS is NOT active (OK)";
    private static final String upsWeakMsgOn         = "UPS weak batteries (NOT OK)";
    private static final String upsWeakMsgOff        = "UPS batteries OK (OK)";
    private static final String upsWarningMsgOn      = "UPS warning on (NOT OK)";
    private static final String upsWarningMsgOff     = "UPS warning off (OK)";
    private static final String upsFailureMsgOn      = "UPS failure (NOT OK)";
    private static final String upsFailureMsgOff     = "UPS is OK (OK)";
    private static final String upsInBypassMsgOn     = "UPS bypass mode on";
    private static final String upsInBypassMsgOff    = "UPS bypass mode off";
    private static final String upsNoGridPowerMsgOn  = "UPS no grid power (NOT OK)";
    private static final String upsNoGridPowerMsgOff = "UPS grid power on (OK)";
    private static final String upsDoorClosedMsgOn   = "UPS door closed (OK)";
    private static final String upsDoorClosedMsgOff  = "UPS door open (NOT OK)";
    
    private static final int    upsActiveId          = 246;
    private static final int    upsWeakId            = 247;
    private static final int    upsWarningId         = 248;
    private static final int    upsFailureId         = 249;
    private static final int    upsInBypassId        = 250;
    private static final int    upsNoGridPowerId     = 251;
    private static final int    upsDoorClosedId      = 252;
    
    public MonitoredInput upsActive;
    public MonitoredInput upsWeak;
    public MonitoredInput upsWarning;
    public MonitoredInput upsFailure;
    public MonitoredInput upsInBypass;
    public MonitoredInput upsNoGridPower;
    public MonitoredInput upsDoorClosed;
    
    public void initializeDet() {
        if (Var.controller.getAppType().equals(AppType.Haifa)) {
            upsActive      = new MonitoredInput(Var.tk1, "UPS_Active"   ,      upsActiveId,      upsActiveMsgOn,      upsActiveMsgOff);
            upsFailure     = new MonitoredInput(Var.tk1, "UPS_Failure"  ,     upsFailureId,     upsFailureMsgOn,     upsFailureMsgOff);
        } else if (Var.controller.getAppType().equals(AppType.Jerusalem)) {
            upsActive      = new MonitoredInput(Var.tk1, "UPS_Active"   ,      upsActiveId,      upsActiveMsgOn,      upsActiveMsgOff);
            upsWeak        = new MonitoredInput(Var.tk1, "UPS_Weak"     ,        upsWeakId,        upsWeakMsgOn,        upsWeakMsgOff);
            upsWarning     = new MonitoredInput(Var.tk1, "UPS_Warning"  ,     upsWarningId,     upsWarningMsgOn,     upsWarningMsgOff);
            upsFailure     = new MonitoredInput(Var.tk1, "UPS_Failure"  ,     upsFailureId,     upsFailureMsgOn,     upsFailureMsgOff);
            upsInBypass    = new MonitoredInput(Var.tk1, "UPS_Bypass"   ,    upsInBypassId,    upsInBypassMsgOn,    upsInBypassMsgOff);
            upsNoGridPower = new MonitoredInput(Var.tk1, "UPS_Grid"     , upsNoGridPowerId, upsNoGridPowerMsgOn, upsNoGridPowerMsgOff);
            upsDoorClosed  = new MonitoredInput(Var.tk1, "UPS_Door"     ,  upsDoorClosedId,  upsDoorClosedMsgOn,  upsDoorClosedMsgOff);
        } else if (Var.controller.getAppType().equals(AppType.NetiveiAyalon)) {
            upsActive      = new MonitoredInput(Var.tk1, "UPS_Active"   ,      upsActiveId,      upsActiveMsgOn,      upsActiveMsgOff);
            upsFailure     = new MonitoredInput(Var.tk1, "UPS_Failure"  ,     upsFailureId,     upsFailureMsgOn,     upsFailureMsgOff);
        } else if (Var.controller.getAppType().equals(AppType.NetiveiIsrael)) {
            upsActive      = new MonitoredInput(Var.tk1, "UPS_Active"   ,      upsActiveId,      upsActiveMsgOn,      upsActiveMsgOff);
            upsWeak        = new MonitoredInput(Var.tk1, "UPS_Weak"     ,        upsWeakId,        upsWeakMsgOn,        upsWeakMsgOff);
            upsWarning     = new MonitoredInput(Var.tk1, "UPS_Warning"  ,     upsWarningId,     upsWarningMsgOn,     upsWarningMsgOff);
            upsFailure     = new MonitoredInput(Var.tk1, "UPS_Failure"  ,     upsFailureId,     upsFailureMsgOn,     upsFailureMsgOff);
            upsInBypass    = new MonitoredInput(Var.tk1, "UPS_In-Bypass",    upsInBypassId,    upsInBypassMsgOn,    upsInBypassMsgOff);
            upsNoGridPower = new MonitoredInput(Var.tk1, "UPS_Grid"     , upsNoGridPowerId, upsNoGridPowerMsgOn, upsNoGridPowerMsgOff);
            upsDoorClosed  = new MonitoredInput(Var.tk1, "UPS_Door"     ,  upsDoorClosedId,  upsDoorClosedMsgOn,  upsDoorClosedMsgOff);
        } else if (Var.controller.getAppType().equals(AppType.TelAviv)) {
            upsActive      = new MonitoredInput(Var.tk1, "UPS_Active"   ,      upsActiveId,      upsActiveMsgOn,      upsActiveMsgOff);
            upsFailure     = new MonitoredInput(Var.tk1, "UPS_Failure"  ,     upsFailureId,     upsFailureMsgOn,     upsFailureMsgOff);
            upsDoorClosed  = new MonitoredInput(Var.tk1, "UPS_Door"     ,  upsDoorClosedId,  upsDoorClosedMsgOn,  upsDoorClosedMsgOff);
        } else { // default is haifa protocol                                           
            upsActive      = new MonitoredInput(Var.tk1, "UPS_Active"   ,      upsActiveId,      upsActiveMsgOn,      upsActiveMsgOff);
            upsFailure     = new MonitoredInput(Var.tk1, "UPS_Failure"  ,     upsFailureId,     upsFailureMsgOn,     upsFailureMsgOff);
        }
    }
    
    public void updateState() {
        if (upsActive      != null) { upsActive     .updateState(); }
        if (upsWeak        != null) { upsWeak       .updateState(); }
        if (upsWarning     != null) { upsWarning    .updateState(); }
        if (upsFailure     != null) { upsFailure    .updateState(); }
        if (upsInBypass    != null) { upsInBypass   .updateState(); }
        if (upsNoGridPower != null) { upsNoGridPower.updateState(); }
        if (upsDoorClosed  != null) { upsDoorClosed .updateState(); }
    }
}
