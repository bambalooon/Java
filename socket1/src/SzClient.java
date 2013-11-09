import java.net.*;
import java.io.*;
import java.util.*;

public class SzClient {


	public static void main(String[] args) throws IOException {
		SzymonServer ss = new SzymonServer("najakuratniej");
		ss.ls();
		ss.getFile("README");
		ss.getFile("Balon");
		ss.logout();
        
	}
}
