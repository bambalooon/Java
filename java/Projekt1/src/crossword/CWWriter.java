/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.util.*;
import java.io.*;

/**
 *
 * @author BamBalooon
 */
public class CWWriter implements Writer {
    private String directory;
    public void CWWriter(String dir) {
        directory = dir;
    }
    public void Write(Crossword cw) {
        try {
            FileOutputStream fout = new FileOutputStream(directory+"/"+getUniqueID());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(cw);
            oos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    public long getUniqueID() {
       Date d = new Date();
       return d.getTime();
    }
}
