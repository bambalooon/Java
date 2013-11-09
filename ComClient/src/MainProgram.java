import Connection.*;


public class MainProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8000;
		final UserInterface ui = new UserInterface();
		new ClientConnection("localhost", port);
		ui.start();
		try {
			// Wait for the server to shutdown
			ui.join();
			System.out.println("Completed shutdown.");
		} catch (InterruptedException e) {
			// Exit with an error condition
			System.err.println("Interrupted before accept thread completed.");
			System.exit(1);
		}
	}

}
