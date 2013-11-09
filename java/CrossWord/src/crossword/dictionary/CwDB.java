/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;

import java.util.*;
import java.io.*;

/**
 *
 * @author BamBalooon
 */
public class CwDB {
    protected LinkedList<Entry> dict;
    public CwDB() { dict = dict = new LinkedList<>(); };
    public CwDB(String filename) {
        dict = new LinkedList<>();
        this.createDB(filename);
    }
    public void add(String word, String clue) {
        dict.add(new Entry(word, clue));
    }
    public Entry get(String word) {
    	for(Entry ent : dict) {
    		if(ent.getWord().equals(word)){
    			return ent;
    		}
    	}
        return new Entry("", ""); //error ?? throw..
    }
    public void remove(String word) {
        for(int i=0; i<dict.size(); i++) {
            if(dict.get(i).getWord().equals(word)) {
                dict.remove(i);
                break;
            }
        }
        //throw
    }
    public int getSize() {
        return dict.size();
    }
    protected void createDB(String filename) {
        try {
            InputStream fstream = getClass().getResourceAsStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String wordTemp = "";
            int i=0;
            while((strLine = br.readLine()) != null) {
                if(i%2==0) {
                    wordTemp = strLine;
                }
                else {
                    this.add(wordTemp, strLine);
                }
                i++;
            }
        }
        catch(Exception e) { //wyrzucic na zewnatrz
            System.err.println("Error: "+e.getMessage());
        }
    }
    public void saveDB(String filename) {
        try{
            // Create file 
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            ListIterator<Entry> itr = dict.listIterator();
            while(itr.hasNext()) {
                Entry ent = itr.next();
                out.write(ent.getWord()+"\n");
                out.write(ent.getClue());
                if(itr.hasNext()) {
                    out.write("\n");
                }
            }
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}
