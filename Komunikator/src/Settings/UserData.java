package Settings;
import java.io.*;

//bedzie zapisywane do pliku..?
public class UserData implements Serializable {
	public static final UserData currentUser = new UserData();
	private static final long serialVersionUID = 1L;
	private long ID;
	private String password;
	public UserData() {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			ID = Long.parseLong(stdIn.readLine());
		} catch(IOException e) {}
		//ID = (long) Math.floor(Math.random()*1000);
		password = "";
	}
	public long getID() {
		return ID;
	}
}
