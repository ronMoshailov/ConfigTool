package itc;

import m0547.Var;
import vtvar.VtVarStrukt;

public class ITCProtocol extends ExtendedUdp {
	
	private static final int DATA_START_INDEX = 28;
	
	private int[] sendData = new int[62];
	private byte[] receiveData;
	
	private VtVarStrukt params;
	private final boolean isActiveParam;
	public boolean isActivateProtocol = false;
	
	public ITCProtocol(String interfaceName, String sourceHost, String targetHost, int sourcePort,
			int targetPort, int maxMessageLoss, boolean isActive) {
		super(interfaceName, sourceHost, targetHost, sourcePort, targetPort, maxMessageLoss);
		this.isActiveParam = isActive;
	}

	protected void receiveMessage() {
	    isActivateProtocol = params.get(0) > 0;
		/*
		 * Byte #1 - Received Program 
		 */
	    if (isActivateProtocol) {
	        receiveData = read();
	    }
	}
	
	public void initializeParameters() {
	    params = VtVarStrukt.init(Var.tk1, "PRTCL_ITC", new int[] { this.isActiveParam ? 1 : 0 }, true, true, true);
	}
	
	public int getInputProgram() {
		if (receiveData == null)
			return 0;
		
		return receiveData[DATA_START_INDEX];
	}
	
	private byte isProgramErrorByte, isExternalControlByte, activeProgramByte;  
	public void setData(
			boolean isProgramError,
			boolean isExternalControl,
			int outputProgram)
	{
		/*
		 * Byte #1 - Is Program Error
		 * Byte #2 - Is External Control
		 * Byte #3 - Active Program 
		 */
	    isProgramErrorByte    = (byte)(isProgramError ? 0x01 : 0x00);
	    isExternalControlByte = (byte)(isExternalControl ? 0x01 : 0x00);
	    activeProgramByte     = (byte)(outputProgram & 0xFF);
	    sendData[5] = ((isProgramErrorByte & 0xFF) << 24) | ((isExternalControlByte & 0xFF) << 16) | ((activeProgramByte & 0xFF) << 8);
	}

	protected void writeMessage() {
	    if (isActivateProtocol) {
    		if (write(sendData)) {
    			udpNet.flushQueues();
    		}
	    }
	}
}
