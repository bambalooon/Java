package Communication;

import java.util.*;

import View.MessageWindow;

public class MessageFactory {
	public static final MessageFactory factory = new MessageFactory();
	private LinkedList<Message> outgoingMessages;
	public MessageFactory() {
		outgoingMessages = new LinkedList<Message>();
	}
	public void addOutMsg(Message msg) {
		outgoingMessages.add(msg);
	}
	public void addInMsg(Message msg) {
		MessageWindow.instance.addMessage(msg);
	}
	public Message getOutMsg() throws NoSuchElementException {
		return outgoingMessages.pop();
	}
}
