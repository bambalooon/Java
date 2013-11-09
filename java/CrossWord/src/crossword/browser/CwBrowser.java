/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.browser;

import crossword.Crossword;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author BamBalooon
 */

public class CwBrowser {
    private LinkedList<Crossword> cws = null;
    private File directory = null;
    private JList outList = null;
    private CWWriter write = null;
    private CWReader read = null;
    public CwBrowser() {
        
    }
    public void setDirectory(File file) {
        directory = file;
        read = new CWReader(file);
        write = new CWWriter(file);
    }
    public void setList(JList list) {
        outList = list;
    }
    public void save(Crossword cw) throws FileNotFoundException, IOException {
        write.Write(cw);
    }
    public void load() throws ClassNotFoundException, FileNotFoundException, IOException {
        cws = read.getAllCws();
        outList.removeAll();
        Object[] objs = new Object[cws.size()];
        int i=0;
        for(Crossword cw : cws) {
            objs[i++]=cw;
        }
        outList.setListData(objs);
    }
}
