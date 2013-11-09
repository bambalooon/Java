package javaIn;
 
import java.io.*;
 
public class JIn {
    public static String getString() {
	  String text = null;
	  try{
		InputStreamReader rd = new InputStreamReader(System.in);
		BufferedReader bfr = new BufferedReader(rd);
 
		text = bfr.readLine();
	  }catch(IOException e){e.printStackTrace();}
      return text;	  
    }
    public static int getInt() {
	  String text = getString();
	  return Integer.parseInt(text);
    }
    public static double getDouble() {
	  String text = getString();
	  return Double.parseDouble(text);
    }
}