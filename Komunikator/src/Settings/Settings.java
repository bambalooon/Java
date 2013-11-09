package Settings;

import java.io.*;
import Exceptions.*;

public class Settings implements Serializable {
	public static Settings settings = null;
	public static final int port = 4000;
	public static final String ip = "87.207.189.244";/*"localhost";"192.168.0.10";"localhost";"192.168.0.10";*/
	private static String settingsFilename = "settings.ser";
	private long ID=0;
	private String password;
	public Settings() throws IOException, NotLoggedException {
		load();
	}
	public Settings(long id, String password) {
		this.ID = id;
		this.password = password;
	}
	public Settings(String password) {
		this.password = password;
	}
	public void setID(long id) {
		this.ID = id;
	}
	public long getID() {
		return ID;
	}
	public String getPWD() {
		return password;
	}
	public void save() throws IOException {
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fout = new FileOutputStream(settingsFilename);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
		} catch(IOException e) {
			if(oos!=null) {
				oos.close();
			}
			throw e;
		}
		
	}
	public void load() throws IOException, NotLoggedException {
		ObjectInputStream ois = null;
		try {
			FileInputStream fin = new FileInputStream(settingsFilename);
			ois = new ObjectInputStream(fin);
			Settings sett = (Settings) ois.readObject();
			ID = sett.getID();
			password = sett.getPWD();
			
		} catch(ClassNotFoundException e) {
			System.out.print("ClassNotFoundExc");
			if(ois!=null) {
				ois.close();
			}
		} catch(FileNotFoundException e) {
			if(ois!=null) {
				ois.close();
			}
			throw new NotLoggedException();
		}
		catch(IOException e) {
			if(ois!=null) {
				ois.close();
			}
			throw e;
		}
	}
}
