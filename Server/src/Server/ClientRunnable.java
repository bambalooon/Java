package Server;

import java.net.*;
import java.io.*;

public class ClientRunnable implements Runnable {
	protected Socket clientSocket = null;
	protected ThreadPooledServer server = null;
	public ClientRunnable(Socket socket, ThreadPooledServer server) {
		clientSocket = socket;
		this.server = server;
	}
	public ClientRunnable(ClientRunnable copy) {
		clientSocket = copy.clientSocket;
		server = copy.server;
	}
	public void run() {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            long time = System.currentTimeMillis();
            String inputLine;
            
            while((inputLine = in.readLine()) != null) {
            	long curtime = System.currentTimeMillis();
            	if(inputLine.equals("end") || (curtime-time)>1000)
					break;
			     out.println(inputLine);
            }
            out.close();
            in.close();
            if(inputLine.equals("end")) {
            	clientSocket.close();
            	System.out.print("a");
            }
            else {
            	server.extendThreadLife(new ClientRunnable(this));
            	System.out.print("b");
            }
            
            	
            //System.out.println("Request processed: " + time);
		}
		catch(IOException e) {
			//report somewhere
			e.printStackTrace();
		}
	}
}
