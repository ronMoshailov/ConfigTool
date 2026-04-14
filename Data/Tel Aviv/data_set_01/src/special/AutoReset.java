package special;

import ta172.Var;
import vtvar.VtVar;
import vtvar.VtVarStrukt;
import hw.AnlagenZustand;

/**
 * This class is responsible for automatically resetting a controller if there was an error that caused the controller to shut down (1st or 2nd level)
 * There are some safety related parameters, some are constant and some can be adjusted in the code:
 *  - In order for an automatic restart to be able to happen, ALL nodes on the controller must be in shut down mode
 *  - The reset will occur after all nodes have been in shut down mode for a defined period of time.
 *    This is important so that drivers and pedestrians have enough time to adjust to the controller being in failure before attempting a reset (if in 1st shut down level)
 *  - The number of automatic resets is limited, so that if it's a consistent fault, the controller will remain in the required shut down and not just keep resetting itself
 * @author Ilia Butvinnik
 * @since 01/09/2025
 * @version 1.0.0
 */
public class AutoReset {
    private final int DelayBeforeReset;
    private final int MAXIMUM_TIME_FOR_COUNTERS_RESET;
    
    private final int maximumResetRetries;

    private VtVarStrukt parametersResetsLimit;
    private VtVarStrukt parametersResetsCounter;

    private Node node;
    private int nodeIterator;

    private long startupTimeMS = Var.NONE;
    private long errorStartTimeMS = Var.NONE;
    private long currentTimeMS;
    
    private boolean isResetNeeded;
    private boolean isUpdated = false;
    
    private String errorMessage = "";
    private boolean addedToLog = false;
    
    /**
     * 
     * @param delayBeforeReset - the minimum required time between the detection of an error until an automatic reset is attempted (in seconds)
     * @param maxTimeForCountersReset - the time since the controller start (in minutes) until it resets the counters for this module (if no new error occurred)
     */
    public AutoReset(int delayBeforeReset, int maxTimeForCountersReset, int maxResetRetries) {
        DelayBeforeReset = delayBeforeReset;
        MAXIMUM_TIME_FOR_COUNTERS_RESET = maxTimeForCountersReset * 60;
        maximumResetRetries = maxResetRetries; 
    }
    
    /**
     * Initializes the parameters required for this module:
     *   - Limit of resets
     *   - Resets counter
     */
    public void initializeParameters() {
        parametersResetsLimit   = VtVarStrukt.init(Var.tk1, "AutoReset_Limit", new int[] { maximumResetRetries }, true, true, true);
        parametersResetsCounter = VtVarStrukt.init(Var.tk1, "Resets_Counter" , new int[] { 0                   }, true, true, true);
    }
    
    private void markControllerStarted() {
        if (startupTimeMS < 0) {
            int counter = parametersResetsCounter.get(0); 
            startupTimeMS = System.currentTimeMillis();
            if (counter > 0) {
                Var.controller.log.addLogEntry("AUTO-RESET: started after reset #" + counter);
            }
        }
        
        if (errorStartTimeMS < 0 && isResetNeeded) {
            errorStartTimeMS = System.currentTimeMillis();
        }
    }
    
    private boolean isMinOnTimeDone() {
        if (errorStartTimeMS < 0)
            return false;
        
        return currentTimeMS - errorStartTimeMS >= DelayBeforeReset * Var.ONE_SEC;
    }
    
    private boolean isNewErrorTimeFrame() {
        if (startupTimeMS < 0)
            return false;
        
        return currentTimeMS - startupTimeMS >= MAXIMUM_TIME_FOR_COUNTERS_RESET * Var.ONE_SEC;
    }
    
    private boolean isResetNeeded() {
        for (nodeIterator = 0; nodeIterator < Var.controller.nodes.length; nodeIterator++) {
            node = Var.controller.nodes[nodeIterator];
            if (!AnlagenZustand.isAbschaltgrad1(node) && !AnlagenZustand.isAbschaltgrad2(node)) {
                return false;
            }
        }
        return true;
    }
    
    int resetCounter = Var.NONE;
    int memoryResetCounter = Var.NONE;
    private boolean updateCounter() {
        if (!isUpdated) {
            memoryResetCounter = resetCounter;
            isUpdated = true;
        }
        
        if (resetCounter == memoryResetCounter) {
            parametersResetsCounter.prepareSet(0, resetCounter + 1);
            if (!VtVar.isCommitPreparedSetsOngoing()) {
                VtVar.commitPreparedSets();
            }
            return false;
        } else {
            return memoryResetCounter + 1 == resetCounter;
        }
    }
    
    private void resetCounter() {
        if (resetCounter != 0) {
            parametersResetsCounter.prepareSet(0, 0);
            if (!VtVar.isCommitPreparedSetsOngoing()) {
                VtVar.commitPreparedSets();
            }
        }
    }
    
    /**
     * The main method of this class which runs the logic required for the automatic reset
     */
    public void run() {
        isResetNeeded = isResetNeeded();
        
        markControllerStarted();
        currentTimeMS = System.currentTimeMillis();
        resetCounter = parametersResetsCounter.get(0);

        if (!isResetNeeded && isNewErrorTimeFrame()) {
            resetCounter();
        }
        
        if (isMinOnTimeDone()) {
            if (isResetNeeded && parametersResetsCounter.get(0) <= parametersResetsLimit.get(0)) {
                if (updateCounter()) {
                    if (errorMessage == "") {
                        errorMessage = "AUTO-RESET: reset #" + parametersResetsCounter.get(0); 
                    }
                    if (Var.controller.log != null && !addedToLog) {
                        Var.controller.log.addLogEntry(errorMessage);
                        addedToLog = true;
                    }
                    reset(errorMessage);
                }
            }
        }
    }
    
    /**
     * An auxiliary method for resetting the controller and adding a message to the archive
     * @param resetMessage the message to be added to the archive before resetting the controller
     */
    public static void reset(String resetMessage) {
        fehlerhandler.FehlerHandler.writeMsg(fehlerhandler.FehlerHandler.errInfo, true, 1, resetMessage);
        sbh.vtbib.dvi35.DVI35Modul.getId1(9000);
    }
}
