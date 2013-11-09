package Settings;
import java.io.*;

//bedzie zapisywane do pliku..?
public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;
	private long ID;
	private String password;
	public UserData() {
		ID = (long) Math.floor(Math.random()*100000);
		password = "";
	}
	public long getID() {
		return ID;
	}
}
