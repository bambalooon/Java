package Communication;

import java.io.*;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	//message mode
	public enum Mode { NORMAL, LOGIN, LOGOUT, EMPTY, LOGIN_REMINDER, LOGIN_ERROR, LOGIN_OK };
	
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
	
	//login/logout messages
	public Message(long sid, String content, Mode mode) {
		senderID = sid;
		this.content = content;
		this.mode = mode;
		time = System.currentTimeMillis();
	}
	
	//empty or login_reminder message or LOGIN
	public Message(long rid, Mode mode) {
		time = System.currentTimeMillis();
		receiverID = rid;
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
