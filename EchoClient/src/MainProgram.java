import Settings.UserData;
import Connection.*;
import Communication.*;


public class MainProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8000;
		final UserData user = new UserData();
		final MessageFactory factory = new MessageFactory();
		final UserInterface ui = new UserInterface(factory, user);
		final ComProtocol protocol = new ComProtocol(factory, user);
		final ClientConnection client = new ClientConnection("localhost", port, protocol);
		// Starts the client's independent thread
		client.start();
		ui.start();
		try {
			// Wait for the server to shutdown
			client.join();
			System.out.println("Completed shutdown.");
		} catch (InterruptedException e) {
			// Exit with an error condition
			System.err.println("Interrupted before accept thread completed.");
			System.exit(1);
		}
	}

}
