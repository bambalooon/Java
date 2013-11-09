package Communication;

import java.util.*;

public class ComProtocol {
	//if user already logged
	private boolean logged = false;
	private long senderID;
	public void processInput(Message msg) throws ConnectionEnd {
		if(!logged) {
			if(msg.getMode()==Message.Mode.LOGIN) {
				senderID = msg.getSid();
				String password = msg.getContent();
				long time = msg.getTime();
				//sprawdzamy w bazie czy istnieje taki uzytkownik
				//jesli tak to rozpoczynamy rozmowe
				//jesli nie to wysylamy wiadomosc ze bledne dane i przerywamy polaczenie
				MessageFactory.factory.addMsg( new Message(senderID, Message.Mode.LOGIN_OK) );
				logged = true;
				return;
				//else LOGIN_ERROR
			}
			return;
		}
		//gdy uzytkownik jest zalogowany
		
		//wylogowanie
		if(msg.getMode()==Message.Mode.LOGOUT) {
			//usuniecie z listy zalogowanych
			//zaznaczenie w bazie kiedy sie wylogowal?			
			throw new ConnectionEnd();
		}
		if(msg.getMode()==Message.Mode.NORMAL) {
			//wyslanie tej wiadomosci w odpowiednie miejsce..
			
			MessageFactory.factory.addMsg(msg);
			return;
		}
		/*
		if(msg.getMode()==Message.Mode.EMPTY) {
			//wyslanie wiadomosci z puli oczekujacej na to
			Message rMsg;
			try {
				rMsg = MessageFactory.factory.getMsg(senderID);
			} catch(NoSuchElementException e) {
				return new Message(Message.Mode.EMPTY);
			}
			return rMsg;
		}
		*/
		//throw? unexpected
	}
	public Message getMessage() throws NoSuchElementException {
		if(!logged) {
			throw new NoSuchElementException();
		}
		return MessageFactory.factory.getMsg(senderID);
	}
}
