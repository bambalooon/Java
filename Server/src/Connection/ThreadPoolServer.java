package Connection;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;
import Communication.*;

public class ThreadPoolServer extends Thread {

	public static class ClientHandler implements Runnable {

		public enum Mode { RECEIVER, SENDER };
		
		private final Socket clientSock;
		private final ComProtocol protocol;
		private final Mode mode;
		private ClientHandler handler;
		private boolean isRunning = true;
		private ExecutorService workers;
		
		private ObjectInputStream ois = null;
		private ObjectOutputStream oos = null;
		
		public ClientHandler(final Socket clientSocket, final ComProtocol protocol, final Mode mode, final ObjectInputStream ois, final ObjectOutputStream oos) {
			this.clientSock = clientSocket;
			this.protocol = protocol;
			this.mode = mode;
			this.ois = ois;
			this.oos = oos;
		}
		public void appendHandler(ClientHandler handler) { 
			this.handler = handler;
		}
		public void appendWorkers(final ExecutorService workers) {
			this.workers = workers;
		}
		public boolean isRunning() { //for second handler to check if this one is running to shutdown at the same time
			return isRunning;
		}
		
		@Override
		public void run() {
			
			
			try {
				//przetwarzanie tego co serwer dostal
				if(mode==ClientHandler.Mode.RECEIVER) {
					oos.flush();
					oos.reset();
					boolean keepTrying = true;
					while(keepTrying) {
						Message sMsg = (Message) ois.readObject();
						if(sMsg.getMode()==Message.Mode.LOGIN) {
							try {
								protocol.processInput(sMsg);
								if(protocol.isLogged()) {
									oos.writeObject(new Message(Message.Mode.LOGIN_OK));
									keepTrying = false;
								}
								else {
									oos.writeObject(new Message(Message.Mode.LOGIN_ERROR));
								}
								oos.flush();
								oos.reset();
							} catch(ConnectionEnd e) {} //doesnt happen
							catch(LoginException e) { /*do sth?*/ }
							catch(RegisterException e) {} //doesnt happen
						}
						else if(sMsg.getMode()==Message.Mode.REGISTER) {
							try {
								protocol.processInput(sMsg);
								oos.writeObject(new Message(protocol.getID(), Message.Mode.REGISTER_OK));
								oos.flush();
								oos.reset();
								//keepTrying = false;
							} catch(ConnectionEnd e) {} //doesnt happen
							catch(LoginException e) {} //doesnt happen
							catch(RegisterException e) {
								oos.writeObject(new Message(Message.Mode.REGISTER_ERROR));
								oos.flush();
								oos.reset();
							}
						}
					}
					//start output thread
					workers.execute(handler);
					
					while (handler.isRunning()) {
						Message msg = (Message) ois.readObject();
						System.out.println(msg.getSid()+">"+msg.getRid()+": "+msg.getContent());
						try {
							synchronized(MessageFactory.factory) {
								protocol.processInput(msg);
							}
						} //jesli serwer dostal LOGOUT to rzuci wyjatek zeby wyjsc z petli
						  //nie rzucam catch poza petle bo moga tez byc inne wyjatki
						catch(ConnectionEnd e) {
							break;
						}
						catch(Exception e) {}
					}
				}
				else {
					oos.flush();
					oos.reset();
					
					while (handler.isRunning()) {
						Message msg;
						try {
							synchronized(MessageFactory.factory) {
								msg = protocol.getMessage();
							}
							oos.writeObject(msg);
							oos.flush();
							oos.reset();
						} catch(NoSuchElementException e) {}
					}
				}
			} catch (IOException ioe) {
				// Close both streams, wrappers may not be closed by closing the
				// socket

			} catch(ClassNotFoundException e) {
				//shouldn't happen..?
			}
			try {
				if(oos != null) {
					oos.close();
					oos = null;
				}
				if(ois != null) {
					ois.close();
					ois = null;
				}
				
				isRunning = false;
				if(!handler.isRunning()) {
					this.clientSock.close();
					System.err.println("Lost connection to " + this.clientSock.getRemoteSocketAddress());
				}
			} catch (IOException ioe2) {
				// Ignored
			}
		}
		
	}


	private final ExecutorService workers = Executors.newCachedThreadPool();
	private ServerSocket listenSocket;
	private volatile boolean keepRunning = true;
	public static final DB db = new DB("localhost", "komunikator", "comuser", "pwd");

	public ThreadPoolServer(final int port) {
		// Capture shutdown requests from the Virtual Machine.
		// This can occur when a user types Ctrl+C at the console
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				ThreadPoolServer.this.shutdown();
			}
		});

		try {
			this.listenSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err
					.println("An exception occurred while creating the listen socket: "
							+ e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		// Set a timeout on the accept so we can catch shutdown requests
		try {
			this.listenSocket.setSoTimeout(1000);
		} catch (SocketException e1) {
			System.err
					.println("Unable to set acceptor timeout value.  The server may not shutdown gracefully.");
		}
		
		System.out.println("Accepting incoming connections on port " + this.listenSocket.getLocalPort());
		
		// Accept an incoming connection, handle it, then close and repeat.
		while (this.keepRunning) {
			try {
				// Accept the next incoming connection
				final Socket clientSocket = this.listenSocket.accept();
				System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());
				ComProtocol protocol = new ComProtocol();
				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
				ClientHandler recHandler = new ClientHandler(clientSocket, protocol, ClientHandler.Mode.RECEIVER, ois, oos);
				ClientHandler sendHandler = new ClientHandler(clientSocket, protocol, ClientHandler.Mode.SENDER, ois, oos);
				recHandler.appendHandler(sendHandler);
				sendHandler.appendHandler(recHandler);
				recHandler.appendWorkers(workers);
				this.workers.execute(recHandler);
				//this.workers.execute(sendHandler);

			} catch (SocketTimeoutException te) {
				// Ignored, timeouts will happen every 1 second
			} catch (IOException ioe) {
				System.err
						.println("Exception occurred while handling client request: "
								+ ioe.getMessage());
				// Yield to other threads if an exception occurs (prevent CPU
				// spin)
				Thread.yield();
			}
		}
		try {
			// Make sure to release the port, otherwise it may remain bound for several minutes
			this.listenSocket.close();
		}catch(IOException ioe){
			// Ignored
		}
		System.out.println("Stopped accepting incoming connections.");
	}

	public void shutdown() {
		System.out.println("Shutting down the server.");
		this.keepRunning = false;
		this.workers.shutdownNow();
		try {
			this.join();
		} catch (InterruptedException e) {
			// Ignored, we're exiting anyway
		}
	}
}