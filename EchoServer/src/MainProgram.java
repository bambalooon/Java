import Connection.*;
import Communication.*;


public class MainProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8000;
		final MessageFactory messageFactory = new MessageFactory();
		final ThreadPoolServer server = new ThreadPoolServer(port, messageFactory);
		// Starts the server's independent thread
		server.start();

		try {
			// Wait for the server to shutdown
			server.join();
			System.out.println("Completed shutdown.");
		} catch (InterruptedException e) {
			// Exit with an error condition
			System.err.println("Interrupted before accept thread completed.");
			System.exit(1);
		}
	}

}
