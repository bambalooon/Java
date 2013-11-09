package Communication;

import java.util.*;
import Settings.*;

public class ComProtocol {
	private boolean logged = false;
	private long senderID;
	private MessageFactory mFactory;
	public ComProtocol(MessageFactory factory, UserData user) {
		mFactory = factory;
		senderID = user.getID();
	}
	public Message processInput() {
		//logowanie i wylogowanie..
		if(!logged) {
			return new Message(senderID, "", Message.Mode.LOGIN);
		}
		return new Message(senderID, "", Message.Mode.LOGOUT);
	}
	
	public Message processInput(Message msg) {
		if(msg.getMode()==Message.Mode.LOGIN_REMINDER) {
			//get UserData!
			senderID = (long) Math.floor(Math.random()*100000);
			
			return new Message(senderID, "", Message.Mode.LOGIN);
		}
		if(msg.getMode()==Message.Mode.LOGIN_ERROR) {
			return null;
			//throw???
		}
		if(msg.getMode()==Message.Mode.LOGIN_OK || msg.getMode()==Message.Mode.EMPTY) {
			if(msg.getMode()==Message.Mode.EMPTY) {
				logged = true;
			}
			//nic nie dostales, wyslij swoje
			try {
				return mFactory.getOutMsg();
			} catch(NoSuchElementException e) {
				return new Message(Message.Mode.EMPTY);
			}
		}
		if(msg.getMode()==Message.Mode.NORMAL) {
			//wyslij wiadomosc do watku wyswietlajace je
			mFactory.addInMsg(msg);
			//wyslij twoja wiadomosc oczekujaca
			try {
				return mFactory.getOutMsg();
			} catch(NoSuchElementException e) {
				return new Message(Message.Mode.EMPTY);
			}
		}
		//throw, unexpected
		return null;
	}
}
