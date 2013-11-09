package Connection;
import java.io.*;
import java.net.*;

import Communication.*;

 
public class ClientConnection {
	
	private int clientPort;
	private String serverIP;
	private Socket socket = null;
	
	public ClientConnection(String ip, int port) {
		clientPort = port;
		serverIP = ip;
		
		try {
            socket = new Socket(serverIP, clientPort);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+serverIP+".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
            + "the connection to: "+serverIP+".");
            System.exit(1);
        }
        ComProtocol protocol = new ComProtocol();
        OneWayConnection sConn = new OneWayConnection(socket, protocol, OneWayConnection.Mode.SENDER);
        OneWayConnection rConn = new OneWayConnection(socket, protocol, OneWayConnection.Mode.RECEIVER);
        sConn.appendConn(rConn);
        rConn.appendConn(sConn);
        sConn.start();
        rConn.start();
	}
}
