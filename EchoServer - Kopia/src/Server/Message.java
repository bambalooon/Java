package Server;

import java.io.*;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	//message mode
	public enum Mode { NORMAL, LOGIN, LOGOUT, EMPTY };
	
	protected long senderID=0, receiverID=0;
	protected long time;
	protected Mode mode;
	protected String content = "";
	
	//normal message
	public Message(long sid, long rid, String content) {
		time = System.currentTimeMillis();
		senderID = sid;
		receiverID = rid;
		mode = Mode.NORMAL;
		this.content = content;
	}
	
	//login/logout message
	public Message(long sid, String content, Mode mode) {
		senderID = sid;
		this.content = content;
		this.mode = mode;
		time = System.currentTimeMillis();
	}
	
	//empty message
	public Message() {
		time = System.currentTimeMillis();
		mode = Mode.EMPTY;
	}
	
	
	public long getSid() {
		return senderID;
	}
	public long getRid() {
		return senderID;
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
