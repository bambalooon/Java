package Communication;

import java.util.*;

public class ComProtocol {
	//if user already logged
	private boolean logged = false;
	private long senderID;
	private MessageFactory mFactory;
	public ComProtocol(MessageFactory factory) {
		mFactory = factory;
	}
	public Message processInput(Message msg) {
		if(!logged) {
			if(msg.getMode()==Message.Mode.LOGIN) {
				senderID = msg.getSid();
				String password = msg.getContent();
				long time = msg.getTime();
				//sprawdzamy w bazie czy istnieje taki uzytkownik
				//jesli tak to rozpoczynamy rozmowe
				//jesli nie to wysylamy wiadomosc ze bledne dane i przerywamy polaczenie
				logged = true;
				return new Message(Message.Mode.LOGIN_OK);
				//else LOGIN_ERROR
			}
			return new Message(Message.Mode.LOGIN_REMINDER);
		}
		//gdy uzytkownik jest zalogowany
		
		//wylogowanie
		if(msg.getMode()==Message.Mode.LOGOUT) {
			//usuniecie z listy zalogowanych
			//zaznaczenie w bazie kiedy sie wylogowal?			
			return null;
		}
		if(msg.getMode()==Message.Mode.NORMAL) {
			//wyslanie tej wiadomosci w odpowiednie miejsce..
			
			//wyslanie wiadomosci z puli oczekujacej na to
			mFactory.addMsg(msg);
			Message rMsg;
			try {
				rMsg = mFactory.getMsg(senderID);
			} catch(NoSuchElementException e) {
				return new Message(Message.Mode.EMPTY);
			}
			return rMsg;
		}
		if(msg.getMode()==Message.Mode.EMPTY) {
			//wyslanie wiadomosci z puli oczekujacej na to
			Message rMsg;
			try {
				rMsg = mFactory.getMsg(senderID);
			} catch(NoSuchElementException e) {
				return new Message(Message.Mode.EMPTY);
			}
			return rMsg;
		}
		//throw? unexpected
		return null;
	}
}
