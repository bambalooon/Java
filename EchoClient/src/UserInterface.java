import Communication.*;
import Settings.*;
import java.io.*;
import java.util.*;

public class UserInterface extends Thread {
	private MessageFactory mFactory;
	private long id;
	public UserInterface(MessageFactory factory, UserData user) {
		mFactory = factory;
		id = user.getID();
	}
	public void run() {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		
		System.out.println("Type a message: ");
		try {
			long receiverID = Long.parseLong(userInput = stdIn.readLine());
			while((userInput = stdIn.readLine()) != null) {
				try {
					Message msg = mFactory.getInMsg();
					System.out.println(msg.getSid()+">"+msg.getRid()+": "+msg.getContent());
				} catch(NoSuchElementException e) {}
				mFactory.addOutMsg(new Message(id, receiverID, userInput));
			}
		} catch(IOException e) {
			throw new RuntimeException("UserInterface Error", e);
		}
	}
}
