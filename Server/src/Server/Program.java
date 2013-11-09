package Server;

import java.io.*;

public class Program {
	public static void main(String[] args) throws IOException {
		ThreadPooledServer server = new ThreadPooledServer(5000, 2);
		new Thread(server).start();
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
 
        System.out.println("Type a message: ");
        while ((userInput = stdIn.readLine()) != null) {
            if(userInput.equals("stop"))
            	break;
        }
		System.out.println("Stopping server...");
		server.stop();
	}
}
