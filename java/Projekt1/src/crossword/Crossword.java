/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.util.*;
import crossword.dictionary.InteliCwDB;
import crossword.dictionary.Entry;

/**
 *
 * @author BamBalooon
 */
public class Crossword {
    private LinkedList<CwEntry> entries;
    private Board b;
    private InteliCwDB cwdb;
    private final long ID=-1;
    public Iterator<CwEntry> getROEntryIter() {
        return entries.iterator();
    }
    public Board getBoardCopy() {
        Board b2 = b;
        return b2;
    }
    public InteliCwDB getCwDB() {
        return cwdb;
    }
    public void setCwDB(InteliCwDB cwdb) {
        this.cwdb = cwdb;
    }
    public Boolean contains(String word) {
        for(CwEntry e : entries) {
            if(e.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }
    public final void addCwEntry(CwEntry cwe, Strategy s) {
        entries.add(cwe);
        s.updateBoard(b, cwe);
    }
    public final void generate(Strategy s) {
        Entry e = null;
        while((e=s.findEntry(this)) != null) {
            addCwEntry((CwEntry)e,s); //tutaj zmienilem.. a moge?
        }
    }
}
