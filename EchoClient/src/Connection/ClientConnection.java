package Connection;
import java.io.*;
import java.net.*;

import Communication.*;

 
public class ClientConnection extends Thread {
	private int clientPort;
	private String serverIP;
	private volatile boolean keepRunning = true;
	ComProtocol protocol;
	public ClientConnection(String ip, int port, ComProtocol protocol) {
		clientPort = port;
		serverIP = ip;
		this.protocol = protocol;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				ClientConnection.this.shutdown();
			}
		});
	}
	public void run() {
        Socket echoSocket = null;
        
        ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
        try {
            echoSocket = new Socket(serverIP, clientPort);
            oos = new ObjectOutputStream(echoSocket.getOutputStream());
            ois = new ObjectInputStream(echoSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+serverIP+".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
            + "the connection to: "+serverIP+".");
            System.exit(1);
        }
        
        try {
	        Message msg = protocol.processInput();
			oos.writeObject(msg);
	        
	        while (true) {
	        	try {
	        		Message rMsg = (Message) ois.readObject();
	        		Message myMsg;
	        		//event for shutting down..
	        		if(!keepRunning) { 
	        			myMsg = protocol.processInput();
	            		oos.writeObject(myMsg);
	                	break;
	                }
	        		myMsg = protocol.processInput(rMsg);
	            	oos.writeObject(myMsg);
	        	} catch(ClassNotFoundException e) {
	        		//?
	        	} catch(EOFException e) {
	        		System.out.print("Exc");
	        		//?
	        	}
	        	
	        }
	 
	        ois.close();
	        oos.close();
	        echoSocket.close();
        }
        catch(IOException e) {
        	throw new RuntimeException("Connection error", e);
        }
	}
	public void shutdown() {
		System.out.println("Shutting down the client.");
		this.keepRunning = false;
		try {
			this.join();
		} catch (InterruptedException e) {
			// Ignored, we're exiting anyway
		}
	}
}
