/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;

import java.util.*;
import java.text.*;

/**
 *
 * @author BamBalooon
 */
public class InteliCwDB extends CwDB {
    public InteliCwDB(String filename) {
        super(filename);
    }
    public LinkedList<Entry> findAll(String pattern) {
        LinkedList<Entry> temp = new LinkedList<>();
        ListIterator<Entry> itr = dict.listIterator();
        while(itr.hasNext()) {
            Entry ent = itr.next();
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
    public Entry getRandom(int length) {
        LinkedList<Entry> temp = new LinkedList<>();
        ListIterator<Entry> itr = dict.listIterator();
        while(itr.hasNext()) {
            Entry ent = itr.next();
            if(ent.getWord().length()==length) {
                temp.add(ent);
            }
        }
        Random rn = new Random();
        if(temp.size()==0) {
            return new Entry("", ""); //error
        }
        int i = rn.nextInt(temp.size());
        return temp.get(i);
    }
    public Entry getRandom(String pattern) {
        LinkedList<Entry> matches = this.findAll(pattern);
        Random rn = new Random();
        if(matches.size()==0) {
            return new Entry("", ""); //error
        }
        int i = rn.nextInt(matches.size());
        return matches.get(i);
    }
    public void add(String word, String clue) {
        Collator myCollator = Collator.getInstance();
        for(int i=0; i<dict.size(); i++) {
            if(myCollator.compare(word, dict.get(i).getWord())<0) {
                dict.add(i, new Entry(word, clue));
                return;
            }
        }
        dict.add(new Entry(word, clue));
    }
    public void print() {
        ListIterator<Entry> itr = dict.listIterator();
        while(itr.hasNext()) {
            Entry ent = itr.next();
            System.out.print(ent.getWord()+": "+ent.getClue()+"\n");
                    
        }
    }
}
