/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.browser;

import crossword.Crossword;
import java.util.*;
import java.io.*;

/**
 *
 * @author BamBalooon
 */
public class CWReader implements Reader {
    private File directory;
    public CWReader(File dir) {
        directory = dir;
    }
    @Override
    public LinkedList<Crossword> getAllCws() throws ClassNotFoundException, FileNotFoundException, IOException {
        LinkedList<Crossword> list = new LinkedList<>();
        String[] fList = directory.list();
        for(String s : fList) {
            if(getExt(s).equals("ser")) {
                FileInputStream fin = new FileInputStream(directory.getCanonicalFile()+"\\"+s);
                ObjectInputStream ois = new ObjectInputStream(fin);
                Object obj = ois.readObject();
                Crossword cw = (Crossword) obj;
                ois.close();
                list.add(cw);
            }
        }
        return list;
    }
    private String getExt(String filename) {
        int ind = filename.lastIndexOf(".");
        if(ind==-1)
            return "";
        return filename.substring(ind+1).toLowerCase();
    }
}
