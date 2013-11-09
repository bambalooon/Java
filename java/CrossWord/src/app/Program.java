/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import crossword.strategy.MedStrategy;
import crossword.dictionary.*;
import crossword.*;
import crossword.exceptions.*;
import java.util.*; //do usuniecia


/**
 *
 * @author BamBalooon
 */
public class Program {
    public static void main(String[] argv) {
    	try {
	        //InteliCwDB db = new InteliCwDB("../../files/cwdb.txt");
	        //db.print();
	        //Entry ent = db.getRandom(12);
	        //System.out.print(ent.getWord());
	        Crossword cw = new Crossword("../../files/cwdb.txt", 20, 20);
	        MedStrategy s = new MedStrategy();
	        cw.generate(s);
	        cw.getBoardCopy().print();
	        
	        //db.saveDB("test.txt");
    	}
    	catch(Exception e) { System.out.print(e.getMessage()); }
    }
}
