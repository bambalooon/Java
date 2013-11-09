/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import crossword.strategy.Strategy;
import crossword.board.Board;
import java.util.*;
import java.io.*;
import crossword.dictionary.InteliCwDB;
import crossword.dictionary.Entry;

/**
 *
 * @author BamBalooon
 */
public class Crossword implements java.io.Serializable {
    private LinkedList<CwEntry> entries;
    private Board b;
    private InteliCwDB cwdb;
    private final long ID;
    public Crossword(String dbFile, int width, int height) {
        this.ID = -1;
    	entries = new LinkedList<>();
    	b = new Board(width, height);
    	cwdb = new InteliCwDB(dbFile);
    }
    public Crossword(long ID, Crossword cw) {
        this.ID = ID;
        this.entries = cw.entries;
        this.b = cw.b;
        this.cwdb = cw.cwdb;
    }
    public long getID() {
        return ID;
    }
    @Override
    public String toString() {
        return ""+ID;
    }
    public Iterator<CwEntry> getROEntryIter() {
        return entries.iterator();
    }
    public Board getBoardCopy() {
        Board b2 = new Board(b);
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
        while((e=s.findEntry(this)) != null)
            addCwEntry((CwEntry)e,s); //tutaj zmienilem.. a moge?
    }
}
