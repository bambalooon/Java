import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;


public class SzymonServer {
	static final String dictionary = "polish-dic.txt";
	static final String username = "szymon";
	static final String ip = "149.156.98.73";
	static final int port = 3000;
	String password;
	String ID;
	public SzymonServer() throws IOException {
		LinkedList<String> passes = getPasswords(dictionary);
        
        for(String pass : passes) {
        	Socket echoSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;
        	try {
                echoSocket = new Socket("149.156.98.73", 3000);
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(
                echoSocket.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host: szymon.ia.agh.edu.pl.");
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for "
                + "the connection to: szymon.ia.agh.edu.pl.");
                System.exit(1);
            }
        	out.println("LOGIN szymon;"+pass);
        	String response = in.readLine();
            if(response.length()==10) {
            	this.password = pass;
            	this.ID = response;
            	break;
            }
            out.close();
            in.close();
            echoSocket.close();
        }
        
	}
	public SzymonServer(String password) throws IOException {
		this.password = password;
		Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
    	try {
            echoSocket = new Socket("149.156.98.73", 3000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
            echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: szymon.ia.agh.edu.pl.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
            + "the connection to: szymon.ia.agh.edu.pl.");
            System.exit(1);
        }
    	out.println("LOGIN szymon;"+this.password);
    	String response = in.readLine();
        if(response.length()==10) {
        	this.ID = response;
        }
        out.close();
        in.close();
        echoSocket.close();
	}
	public void logout() throws IOException {
		Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		try {
            echoSocket = new Socket("149.156.98.73", 3000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
            echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: szymon.ia.agh.edu.pl.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
            + "the connection to: szymon.ia.agh.edu.pl.");
            System.exit(1);
        }
		out.println("LOGOUT "+this.ID);
    	String response = in.readLine();
    	System.out.println(response);
	}
	public LinkedList<String> getPasswords(String input) {
		LinkedList<String> passes = new LinkedList<String>();
		try {
			FileInputStream fstream = new FileInputStream(input);  
			// Get the object of DataInputStream  
			DataInputStream in = new DataInputStream(fstream);  
			BufferedReader br = new BufferedReader(new InputStreamReader(in));  
			String strLine;  
			
			//Read File Line By Line  
			while ((strLine = br.readLine()) != null) {  
				 passes.add(strLine);
			}	  
			
			//Close the input stream  
			in.close();  	  
		} catch (Exception e) {//Catch exception if any  
			System.err.println("Error: " + e.getMessage());  
		}
		return passes;
	}
	public void ls() throws IOException {
		Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		try {
            echoSocket = new Socket("149.156.98.73", 3000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
            echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: szymon.ia.agh.edu.pl.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
            + "the connection to: szymon.ia.agh.edu.pl.");
            System.exit(1);
        }
		out.println("LS "+this.ID);
    	String response = in.readLine();
    	System.out.println(response);
	}
	public void getFile(String filename) throws IOException {
		Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		try {
            echoSocket = new Socket("149.156.98.73", 3000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
            echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: szymon.ia.agh.edu.pl.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
            + "the connection to: szymon.ia.agh.edu.pl.");
            System.exit(1);
        }
		out.println("GET "+this.ID+" "+filename);
    	String response = in.readLine();
    	System.out.println(response);
	}
	public void sendFile(String name, String content) throws IOException {
		Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		try {
            echoSocket = new Socket("149.156.98.73", 3000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
            echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: szymon.ia.agh.edu.pl.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
            + "the connection to: szymon.ia.agh.edu.pl.");
            System.exit(1);
        }
		out.println("PUT "+this.ID+" "+name+" "+content);
    	String response = in.readLine();
    	System.out.println(response);
	}
}
