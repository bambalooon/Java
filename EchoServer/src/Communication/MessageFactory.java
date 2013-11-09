package Communication;

import java.util.*;

public class MessageFactory {
	private Map<Long, LinkedList<Message>> messages;
	public MessageFactory() {
		messages = new HashMap<Long, LinkedList<Message>>();
	}
	public void addMsg(Message msg) {
		long id = msg.getRid();
		LinkedList<Message> l = messages.get(id);
		if(l == null) {
			messages.put(id, l=new LinkedList<Message>());
		}
		l.add(msg);
	}
	public Message getMsg(long userID) throws NoSuchElementException {
		LinkedList<Message> l = messages.get(userID);
		if(l == null || l.size()==0) {
			throw new NoSuchElementException();
		}
		return l.pop();
	}
}
