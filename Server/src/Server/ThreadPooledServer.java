package Server;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;

public class ThreadPooledServer implements Runnable {
	protected int serverPort;
	protected int threadsCount;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected ExecutorService threadPool = null;
	
	public ThreadPooledServer(int port, int tCnt) {
		threadsCount = tCnt;
		threadPool = Executors.newFixedThreadPool(threadsCount);
		serverPort = port;
	}
	public void run() {
		synchronized(this) {
			runningThread = Thread.currentThread();
		}
		openServerSocket();
		while(!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			}
			catch(IOException e) {
				if(isStopped()) {
					System.out.println("Server stopped!");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			threadPool.execute(new ClientRunnable(clientSocket, this));
		}
		threadPool.shutdown();
		System.out.println("Server stopped!");
	}
	private void openServerSocket() {
		try {
			serverSocket = new ServerSocket(serverPort);
		}
		catch(IOException e) {
			throw new RuntimeException("Cannot open port "+serverPort, e);
		}
	}
	private synchronized boolean isStopped() {
		return isStopped;
	}
	public synchronized void stop() {
		isStopped = true;
		try {
			serverSocket.close();
		}
		catch(IOException e) {
			throw new RuntimeException("Error closing server!", e);
		}
	}
	public void extendThreadLife(ClientRunnable client) {
		threadPool.execute(client);
	}
}