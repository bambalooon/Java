package Connection;
import java.io.*;
import java.net.Socket;
import java.util.*;

import Communication.ComProtocol;
import Communication.Message;

public class OneWayConnection extends Thread {
	public enum Mode { RECEIVER, SENDER };
	
	private volatile boolean keepRunning = true;
	private ComProtocol protocol;
	private Mode mode;
	private Socket socket;
	private OneWayConnection conn;
	
	public OneWayConnection(Socket socket, ComProtocol protocol, Mode mode) {
		this.socket = socket;
		this.mode = mode;
		this.protocol = protocol;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				OneWayConnection.this.shutdown();
			}
		});
	}
	public void appendConn(OneWayConnection conn) {
		this.conn = conn;
	}
	public boolean isRunning() {
		return keepRunning;
	}
	public void run() {
       
        ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
		try {
			if(mode==Mode.RECEIVER) {
				ois = new ObjectInputStream(socket.getInputStream());
				while(true) {
					try {
						Message msg = (Message) ois.readObject();
						protocol.processInput(msg);
					} catch(ClassNotFoundException e) {}
	        		//event for shutting down..
	        		if(!keepRunning) { 
	                	break;
	                }
				}
			}
			else {
				oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				oos.flush();
				oos.reset();
				while(true) {
					Message msg;
	        		//event for shutting down..
	        		if(!keepRunning) { 
	        			msg = protocol.processInput();
	            		oos.writeObject(msg);
	            		oos.flush();
						oos.reset();
						System.out.print("e");
	                	break;
	                }
	        		try {
	        			msg = protocol.getMessage();
	            		oos.writeObject(msg);
	            		oos.flush();
						oos.reset();
	        		} catch(NoSuchElementException e) {}
				}
			}
			if(ois!=null) {
	        	ois.close();
	        }
	        if(oos!=null) {
	        	oos.close();
	        }
	        if(!conn.isRunning()) {
	        	socket.close();
	        }
		} catch(IOException e) {
			//polaczenie przerwane
			throw new RuntimeException("IOException in object i/o stream", e);
		}
	}
	public void shutdown() {
		System.out.println("Shutting down the "+(mode==Mode.RECEIVER ? "receiver" : "sender"));
		this.keepRunning = false;
		/*
		try {
			this.join();
		} catch (InterruptedException e) {
			// Ignored, we're exiting anyway
		}
		*/
	}
}
