/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.browser;

import crossword.Crossword;
import java.io.*;
import java.util.*;

/**
 *
 * @author BamBalooon
 */
public class CWWriter implements Writer {
    private File directory;
    public CWWriter(File dir) {
        directory = dir;
    }
    @Override
    public void Write(Crossword cw) throws FileNotFoundException, IOException {
        long id = getUniqueID();
        Crossword cwOut = new Crossword(id, cw);
        FileOutputStream fout = new FileOutputStream(directory.getCanonicalPath()+"\\"+id+".ser");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject((Object)cwOut);
        oos.close();
    }
    @Override
    public long getUniqueID() {
       Date d = new Date();
       return d.getTime();
    }
}
