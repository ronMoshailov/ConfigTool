package itc;

import m0547.Var;
import fehlerhandler.FehlerHandler;
import sbh.udpnet.UdpNet;
import sbh.udpnet.UdpNetDevice;
import sbh.udpnet.UdpNetMsgStandard;

public abstract class ExtendedUdp {
	
	private static ExtendedUdp[] protocols = null;
	
	protected String interfaceName;
	protected String sourceHost;
	protected String targetHost;
	protected int sourcePort;
	protected int targetPort;
	protected int maxMessageLoss;
	protected UdpNetDevice udpNet;
	protected UdpNetMsgStandard incomingMessage;
	protected UdpNetMsgStandard outgoingMessage;
	protected int missedReadMessagesCounter;
	protected boolean isError;
	
	protected abstract void writeMessage();
	protected abstract void receiveMessage();
	
	private static int i;
	
	public static void sendAll() {
		if (protocols == null || protocols.length == 0)
			return;
		
		for (i = 0; i < protocols.length; i++)
			protocols[i].writeMessage();
	}
	
	public static void receiveAll() {
		if (protocols == null || protocols.length == 0)
			return;
		
		for (i = 0; i < protocols.length; i++)
			protocols[i].receiveMessage();
	}
	
	public ExtendedUdp(String interfaceName, String sourceHost, String targetHost, int sourcePort, int targetPort, int maxMessageLoss) {
		this.udpNet = UdpNet.init(this.sourceHost = sourceHost, this.targetHost = targetHost, this.sourcePort = sourcePort, this.targetPort = targetPort, this.maxMessageLoss = maxMessageLoss);
		this.interfaceName = interfaceName;
		this.incomingMessage = new UdpNetMsgStandard(this.udpNet, 62);
		this.outgoingMessage = new UdpNetMsgStandard(this.udpNet, 62); //new UdpNetMsg(this.udpNet);
		
		if (protocols != null) {
			ExtendedUdp[] backup = new ExtendedUdp[protocols.length];
			for (int i = 0; i < backup.length; i++)
				backup[i] = protocols[i];
			protocols = new ExtendedUdp[backup.length + 1];
			for (int i = 0; i < backup.length; i++)
				protocols[i] = backup[i];
		} else {
			protocols = new ExtendedUdp[1];
		}
		protocols[protocols.length - 1] = this;
		missedReadMessagesCounter = 0;
		isError = false;
		System.out.println(this.interfaceName + " interface initialized [source = " + this.sourceHost + ":" + this.sourcePort + " target=" + this.targetHost + ":" + this.targetPort + "]");
	}
	
	protected boolean write(int[] data) {
		if (data == null || data.length == 0)
			return false;
		
		for (int i = 0; i < data.length; i++) {
		    outgoingMessage.setMsgAt(i, data[i]);
		}
		udpNet.setMessageData(outgoingMessage);
		return udpNet.sendMessage();
	}
	
	boolean isReceiveQueueEmpty;
	int recvMessage;
	protected byte[] read() {
	    isReceiveQueueEmpty = udpNet.isRecvQueueEmpty();
	    recvMessage = udpNet.recvMessage(incomingMessage);
	    
		if (!isReceiveQueueEmpty && (recvMessage >= -1 * maxMessageLoss)) {
			handleError(true);
			return incomingMessage.toByteArray();
		} else {
			handleError(false);
			return null;
		}
	}
	
	private void handleError(boolean wasMessageReceived) {
		if (wasMessageReceived) {
			if (isError) {
				FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), interfaceName + ": connection established");
				missedReadMessagesCounter = 0;
				isError = false;
			}
		} else {
			if (missedReadMessagesCounter < 999000) {
				missedReadMessagesCounter++;
			}
			if (missedReadMessagesCounter > maxMessageLoss && !isError) {
				FehlerHandler.writeMsg(FehlerHandler.errInfo, true, Var.tk1.getNummer(), interfaceName + ": connection lost");
				isError = true;
			}
		}
	}
}
