import Communication.*;
import Settings.*;
import java.io.*;
import java.util.*;

public class UserInterface extends Thread {
	private long id;
	public UserInterface() {
		id = UserData.currentUser.getID();
	}
	public void run() {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		
		System.out.println("Type a message: ");
		try {
			long receiverID = Long.parseLong(userInput = stdIn.readLine());
			while((userInput = stdIn.readLine()) != null) {
				try {
					Message msg = MessageFactory.factory.getInMsg();
					System.out.println(msg.getSid()+">"+msg.getRid()+": "+msg.getContent());
				} catch(NoSuchElementException e) {}
				MessageFactory.factory.addOutMsg(new Message(id, receiverID, userInput));
			}
		} catch(IOException e) {
			throw new RuntimeException("UserInterface Error", e);
		}
	}
}
