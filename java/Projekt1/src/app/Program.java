/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import crossword.dictionary.*;
import java.util.*; //do usuniecia


/**
 *
 * @author BamBalooon
 */
public class Program {
    public static void main(String[] argv) {
        InteliCwDB db = new InteliCwDB("../../files/cwdb.txt");
        //db.print();
        Entry ent = db.getRandom(12);
        System.out.print(ent.getWord());
        LinkedList<Entry> lst = db.findAll("^a.*");
        db.saveDB("test.txt");
    }
}
