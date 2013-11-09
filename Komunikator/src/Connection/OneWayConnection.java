package Connection;
import java.io.*;
import java.net.Socket;
import java.util.*;

import View.LogRegWindow;

import Communication.ComProtocol;
import Communication.Message;

public class OneWayConnection extends Thread {
	public enum Mode { RECEIVER, SENDER };
	
	private volatile boolean keepRunning = true;
	private ComProtocol protocol;
	private Mode mode;
	private Socket socket;
	private OneWayConnection conn;
	
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	
	
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
		try {
			if(mode==Mode.SENDER) {
				oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			}
		} catch(IOException e) {
			System.out.println("IO Error");
		}
	}
	public void appendConn(OneWayConnection conn) {
		this.conn = conn;
	}
	public boolean isRunning() {
		return keepRunning;
	}
	public void run() {
		try {
			if(mode==Mode.RECEIVER) {
				ois = new ObjectInputStream(socket.getInputStream());
				while(true) {
					try {
						Message msg = (Message) ois.readObject();
						protocol.processInput(msg);
					} catch(ClassNotFoundException e) {}
					catch(IOException e) {}
	        		//event for shutting down..
	        		if(!keepRunning) { 
	                	break;
	                }
				}
			}
			else {
				oos.flush();
				oos.reset();
				while(keepRunning) {
					Message msg = null;
	        		try {
	        			msg = protocol.getMessage();
	            		oos.writeObject(msg);
	            		oos.flush();
						oos.reset();
	        		} catch(NoSuchElementException e) {}
				}
			}
		} catch(IOException e) {
			//polaczenie przerwane
			System.out.println(e.getMessage());
			throw new RuntimeException("IOException in object i/o stream", e);
		}
	}
	public void shutdown() {
		System.out.println("Shutting down the "+(mode==Mode.RECEIVER ? "receiver" : "sender"));
		this.keepRunning = false;
		if(oos!=null) {
			Message msg = protocol.processInput();
			try {
	    		oos.writeObject(msg);
	    		oos.flush();
				oos.reset();
				oos.close();
				oos = null;
			} catch(IOException e) {}
		}
		try {
			if(ois!=null) {
	        	ois.close();
	        	ois = null;
	        }
	        if(ois==null && oos==null) {
	        	socket.close();
	        }
        } catch(IOException e) {
        	System.out.println(e.getMessage());
        }
	}
}
