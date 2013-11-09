package Communication;

import java.util.*;

public class MessageFactory {
	private LinkedList<Message> incomingMessages;
	private LinkedList<Message> outgoingMessages;
	public MessageFactory() {
		incomingMessages = new LinkedList<Message>();
		outgoingMessages = new LinkedList<Message>();
	}
	public void addOutMsg(Message msg) {
		outgoingMessages.add(msg);
	}
	public void addInMsg(Message msg) {
		incomingMessages.add(msg);
	}
	public Message getOutMsg() throws NoSuchElementException {
		return outgoingMessages.pop();
	}
	public Message getInMsg() throws NoSuchElementException {
		return incomingMessages.pop();
	}
}
