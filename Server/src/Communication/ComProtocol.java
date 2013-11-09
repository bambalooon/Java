package Communication;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import Connection.ThreadPoolServer;

public class ComProtocol {
	//if user already logged
	private boolean logged = false;
	private long senderID;
	
	public boolean isLogged() {
		return logged;
	}
	public long getID() {
		return senderID;
	}
	
	public void processInput(Message msg) throws ConnectionEnd, LoginException, RegisterException {
		if(!logged) {
			if(msg.getMode()==Message.Mode.LOGIN) {
				long id = msg.getSid();
				String password = msg.getContent();
				long time = msg.getTime();
				logged = ThreadPoolServer.db.login(id, password);
				if(logged) {
					senderID = id;
				}
				return;
			}
			else if(msg.getMode()==Message.Mode.REGISTER) {
				String[] text = msg.getContent().split(";");
				String username = text[0];
				String pwd = text[1];
				try {
					synchronized(ThreadPoolServer.db) {
						long id = ThreadPoolServer.db.addUser(username, pwd);
						senderID = id;
					}
				} catch(RegisterException e) {
					throw e;
				}
				
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
		else if(msg.getMode()==Message.Mode.NORMAL) {
			//wyslanie tej wiadomosci w odpowiednie miejsce..
			
			MessageFactory.factory.addMsg(msg);
			return;
		}
		else if(msg.getMode()==Message.Mode.GET_CONTACTS) {
			String nick = msg.getContent();
			Map<Long,String> result = ThreadPoolServer.db.search(nick);
			String content = "";
			for(long id : result.keySet()) {
				content += id+":"+result.get(id)+";";
			}
			if(content.length()>0) {
				content = content.substring(0, content.length()-1);
			}
			MessageFactory.factory.addMsg(new Message(msg.getSid(), content, Message.Mode.CONTACT_LIST));
		}
		//throw? unexpected
	}
	public Message getMessage() throws NoSuchElementException {
		if(!logged) {
			throw new NoSuchElementException();
		}
		synchronized(MessageFactory.factory) {
			return MessageFactory.factory.getMsg(senderID);
		}
	}
}
