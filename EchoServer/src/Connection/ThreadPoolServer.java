package Connection;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import Communication.*;

/**
 * <ol>
 * <li>Listens for connections on a specified port</li>
 * <li>Reads a line from the newly-connected socket.</li>
 * <li>Converts the line to uppercase and responds on the socket.</li>
 * <li>Repeats the previous step until the connection is closed by the client.</li>
 * </ol>
 * 
 * <p>
 * The source code contained in this file is based on the "TCPServer" example
 * program provided by Kurose and Ross in <i>Computer Networking: A Top-Down
 * Approach</i>, Fifth Edition.
 * </p>
 * 
 * @author Robert S. Moore
 * 
 */
public class ThreadPoolServer extends Thread {

	/**
	 * A simple runnable class that performs the basic work of this server.
	 * It will read a line from the client, convert it to uppercase, and then
	 * write it back to the client.
	 * @author Robert Moore
	 *
	 */
	public static class ClientHandler implements Runnable {
		/**
		 * The socket connected to the client.
		 */
		private final Socket clientSock;
		private final MessageFactory messageFactory;
		
		/**
		 * Creates a new ClientHandler thread for the socket provided.
		 * @param clientSocket the socket to the client.
		 */
		public ClientHandler(final Socket clientSocket, final MessageFactory mFactory) {
			this.clientSock = clientSocket;
			messageFactory = mFactory;
		}

		/**
		 * The run method is invoked by the ExecutorService (thread pool).
		 */
		@Override
		public void run() {
			
			ComProtocol protocol = new ComProtocol(messageFactory);
			
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
			
			try {
				oos = new ObjectOutputStream(this.clientSock.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(this.clientSock.getInputStream());
				
				while (true) {
					Message msg = (Message) ois.readObject();
					Message returnMsg;
					synchronized(messageFactory) {
						returnMsg = protocol.processInput(msg);
					}
					if (returnMsg == null) {
						break;
					}
					if(msg.getMode()!=Message.Mode.EMPTY) {
						System.out.println(msg.getSid()+">"+msg.getRid()+": "+msg.getContent());
					}
					oos.writeObject(returnMsg);
				}
			} catch (IOException ioe) {
				// Close both streams, wrappers may not be closed by closing the
				// socket

			} catch(ClassNotFoundException e) {
				//shouldn't happen..?
			}
			try {
				if(ois != null) {
					ois.close();
				}
				if(oos != null) {
					oos.close();
				}
				this.clientSock.close();
				System.err.println("Lost connection to " + this.clientSock.getRemoteSocketAddress());
			} catch (IOException ioe2) {
				// Ignored
			}
		}
	}

	/**
	 * Parses the parameter (listen port) and accepts TCP connections on that
	 * port. Each client is serviced by an independent thread, managed by a CachedThreadPool ExecutorService.
	 * The thread will read a line from the client, convert it to uppercase, and then write the converted
	 * line back to the client until the client disconnects or the server exits.
	 * 
	 * @param args
	 *            <listen port>
	 */

	/**
	 * Pool of worker threads of unbounded size. A new thread will be created
	 * for each concurrent connection, and old threads will be shut down if they
	 * remain unused for about 1 minute.
	 */
	private final ExecutorService workers = Executors.newCachedThreadPool();

	/**
	 * Server socket on which to accept incoming client connections.
	 */
	private ServerSocket listenSocket;

	/**
	 * Flag to keep this server running.
	 */
	private volatile boolean keepRunning = true;
	
	private final MessageFactory messageFactory;

	/**
	 * Creates a new threaded echo server on the specified TCP port.  Calls {@code System.exit(1)} if
	 * it is unable to bind to the specified port.
	 * @param port the TCP port to accept incoming connections on.
	 */
	public ThreadPoolServer(final int port, final MessageFactory factory) {
		messageFactory = factory;
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

	/**
	 * This is executed when ThreadPoolEchoServer.start() is invoked by another thread.  Will listen for incoming connections
	 * and hand them over to the ExecutorService (thread pool) for the actual handling of client I/O.
	 */
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

				ClientHandler handler = new ClientHandler(clientSocket, messageFactory);
				this.workers.execute(handler);

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

	/**
	 * Shuts down this server.  Since the main server thread will time out every 1 second,
	 * the shutdown process should complete in at most 1 second from the time this method is invoked.
	 */
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