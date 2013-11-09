package Communication;

import java.io.*;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	//message mode
	public enum Mode { NORMAL, LOGIN, LOGOUT, EMPTY, LOGIN_REMINDER, LOGIN_ERROR, LOGIN_OK, REGISTER, REGISTER_OK, REGISTER_ERROR, GET_CONTACTS, CONTACT_LIST };
	
	private long senderID=0, receiverID=0;
	private long time;
	private Mode mode;
	private String content = "";
	
	//normal message
	public Message(long sid, long rid, String content) {
		time = System.currentTimeMillis();
		senderID = sid;
		receiverID = rid;
		mode = Mode.NORMAL;
		this.content = content;
	}
	
	//login/logout messages/get_contact/contact_list
	public Message(long id, String content, Mode mode) {
		if(mode==Mode.CONTACT_LIST) {
			receiverID = id;
		}
		else {
			senderID = id;
		}
		this.content = content;
		this.mode = mode;
		time = System.currentTimeMillis();
	}
	
	//register ok message
	public Message(long sid, Mode mode) {
		senderID = sid;
		this.mode = mode;
		time = System.currentTimeMillis();
	}
	
	//register message
	public Message(String username, String password) {
		senderID = 0;
		content = username+";"+password;
		time = System.currentTimeMillis();
		mode = Mode.REGISTER;
	}
	
	//empty or login_reminder message or LOGIN
	public Message(Mode mode) {
		this.mode = mode;
	}
	
	public long getSid() {
		return senderID;
	}
	public long getRid() {
		return receiverID;
	}
	public long getTime() {
		return time;
	}
	public Mode getMode() {
		return mode;
	}
	public String getContent() {
		return content;
	}
	
}
