package Settings;

import java.util.*;
import java.io.*;

public class ContactList implements Serializable {
	public static ContactList contactList = null;
	private static String contactListFilename = "contacts.ser";
	private static final long serialVersionUID = 1L;
	private LinkedList<Contact> contacts;
	public ContactList() throws IOException {
		loadList();
		for(Contact c : contacts) {
			c.setLogged(false);
		}
	}
	public void addContact(Contact con) throws IOException {
		contacts.add(con);
		saveList();
	}
	public void rmContact(Contact con) throws IOException {
		contacts.remove(con);
		saveList();
	}
	public Contact getContact(long id) {
		for(Contact c : contacts) {
			if(c.getID()==id) {
				return c;
			}
		}
		return null;
	}
	
	public void saveList() throws IOException {
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fout = new FileOutputStream(contactListFilename);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
		} catch(IOException e) {
			if(oos!=null) {
				oos.close();
			}
			throw e;
		}
		
	}
	public void loadList() throws IOException {
		ObjectInputStream ois = null;
		try {
			FileInputStream fin = new FileInputStream(contactListFilename);
			ois = new ObjectInputStream(fin);
			ContactList cList = (ContactList) ois.readObject();
			contacts = cList.getContacts();
		} catch(ClassNotFoundException e) {
			System.out.print("ClassNotFoundExc");
			if(ois!=null) {
				ois.close();
			}
		} catch(FileNotFoundException e) {
			if(ois!=null) {
				ois.close();
			}
			contacts = new LinkedList<Contact>();
		}
		catch(IOException e) {
			if(ois!=null) {
				ois.close();
			}
			throw e;
		}
	}
	public LinkedList<Contact> getContacts() {
		return contacts;
	}
}
