package Communication;

import java.util.*;
import Settings.*;

public class ComProtocol {
	private boolean logged = false;
	private boolean logging = false;
	private long senderID;
	public ComProtocol() {
		senderID = UserData.currentUser.getID();
	}
	public Message processInput() {
		//wylogowanie..
		return new Message(senderID, "", Message.Mode.LOGOUT);
	}
	
	public void processInput(Message msg) {
		if(msg.getMode()==Message.Mode.LOGIN_ERROR) {
			logging = false;
			logged = false;
			return;
			//throw???
		}
		if(msg.getMode()==Message.Mode.LOGIN_OK) {
			logging = false;
			logged = true;
			return;
		}
		if(msg.getMode()==Message.Mode.NORMAL) {
			//wyslij wiadomosc do watku wyswietlajace je
			MessageFactory.factory.addInMsg(msg);
			return;
		}
		//throw, unexpected
	}
	public Message getMessage() throws NoSuchElementException {
		if(!logged) {
			if(logging) {
				throw new NoSuchElementException();
			}
			logging = true;
			return new Message(senderID, "", Message.Mode.LOGIN);
		}
		return MessageFactory.factory.getOutMsg();
	}
}
