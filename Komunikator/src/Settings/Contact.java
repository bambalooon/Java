package Settings;

import java.io.*;

public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;
	private long ID;
	private String nick;
	boolean logged = false;
	public Contact(long id, String nick) {
		this.ID = id;
		this.nick = nick;
	}
	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	public boolean getLogged() {
		return logged;
	}
	public String getNick() {
		return nick;
	}
	public long getID() {
		return ID;
	}
	public String toString() {
		return getNick();
	}
}
