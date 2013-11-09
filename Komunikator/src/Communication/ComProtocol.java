package Communication;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.*;
import Settings.*;
import View.LogRegWindow;
import View.MainWindow;

public class ComProtocol {
	private boolean logged = false;
	private long senderID=0;
	public ComProtocol() {
		if(Settings.settings!=null) {
			senderID = Settings.settings.getID();
		}
	}
	public void setID(long id) {
		senderID = id;
	}
	
	public Message processInput() {
		//wylogowanie..
		return new Message(senderID, "", Message.Mode.LOGOUT);
	}
	
	public void processInput(Message msg) {
		if(msg.getMode()==Message.Mode.LOGIN_ERROR) {
			logged = false;
			LogRegWindow.instance.showDialog("Problem z logowaniem.");
			return;
			//throw???
		}
		else if(msg.getMode()==Message.Mode.LOGIN_OK) {
			logged = true;
			senderID = Settings.settings.getID();
			try {
				Settings.settings.save();
			} catch(IOException e) {}
			try {
				if(MainWindow.instance==null) {
					MainWindow.instance = new MainWindow();
				}
			} catch(IOException e) {} //tutaj mozna usunac IOEx - zeby MW sam sobie z tym radzil..
			if(LogRegWindow.instance!=null) {
				LogRegWindow.instance.close();
			}
			return;
		}
		else if(msg.getMode()==Message.Mode.NORMAL) {
			//wyslij wiadomosc do watku wyswietlajace je
			MessageFactory.factory.addInMsg(msg);
			return;
		}
		else if(msg.getMode()==Message.Mode.REGISTER_OK) {
			long id = msg.getSid();
			/*Settings.settings.setID(id);
			try {
				Settings.settings.save();
			} catch(IOException e) {}
			*/
			LogRegWindow.instance.showDialog("Rejestracja zakoñczona powodzeniem. \n Twój numer to: "+id);
		}
		else if(msg.getMode()==Message.Mode.REGISTER_ERROR) {
			LogRegWindow.instance.showDialog("Rejestracja zakoñczona niepowodzeniem.");
		}
		else if(msg.getMode()==Message.Mode.CONTACT_LIST) {
			MainWindow.instance.addUserToTable(msg);
		}
		//throw, unexpected
	}
	public Message getMessage() throws NoSuchElementException {
		synchronized(MessageFactory.factory) {
			return MessageFactory.factory.getOutMsg();
		}
	}
}
