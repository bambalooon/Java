/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;

import java.util.*;
import java.text.*;
import crossword.exceptions.*;

/**
 *
 * @author BamBalooon
 */
public class InteliCwDB extends CwDB implements java.io.Serializable {
    public InteliCwDB() {
        super();
    }
    public InteliCwDB(InteliCwDB cwdb) {
        super();
        dict = cwdb.dict;
    }
    public InteliCwDB(String filename) {
        super(filename);
    }
    public LinkedList<Entry> findAll(String pattern) {
        LinkedList<Entry> temp = new LinkedList<>();
        for(Entry ent : dict) {
        	if(ent.getWord().toLowerCase().matches(pattern)) {
        		temp.add(ent);
        	}
        }
        return temp;
    }
    public Entry getRandom() {
        Random rn = new Random();
        int i = rn.nextInt(dict.size());
        return dict.get(i);
    }
    public Entry getRandom(int length) throws cwdbException {
        LinkedList<Entry> temp = new LinkedList<>();
        for(Entry ent : dict) {
        	if(ent.getWord().length()==length) {
                temp.add(ent);
            }
        }
        Random rn = new Random();
        if(temp.size()==0)
        	throw new cwdbException();
            //return new Entry("", ""); //error
        int i = rn.nextInt(temp.size());
        return temp.get(i);
    }
    public Entry getRandom(String pattern) throws cwdbException {
        LinkedList<Entry> matches = this.findAll(pattern);
        Random rn = new Random();
        if(matches.size()==0) {
            throw new cwdbException();
        }
        int i = rn.nextInt(matches.size());
        return matches.get(i);
    }
    public void add(String word, String clue) {
        Collator myCollator = Collator.getInstance();
        int i=0;
        for(Entry ent : dict) {
        	if(myCollator.compare(word, ent.getWord())<0) {
                dict.add(i, new Entry(word, clue));
                return;
            }
        	i++;
        }
        dict.add(new Entry(word, clue));
    }
    public void print() {
    	for(Entry ent : dict) {
    		System.out.print(ent.getWord()+": "+ent.getClue()+"\n");
    	}
    }
}
