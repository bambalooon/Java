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
public class EasyStrategy extends Strategy {
    public CwEntry findEntry(Crossword cw) {
        Iterator<CwEntry> iter = cw.getROEntryIter();
        Board b = cw.getBoardCopy();
        InteliCwDB cwdb = cw.getCwDB();
        CwEntry ent;
        if(iter.hasNext()) {
            BoardCell current = b.getStartCells().getFirst();
            String letter = current.getContent();
            String pattern = this.createPattern(letter, b.getWidth());
            Entry entTmp = cwdb.getRandom(pattern);
            //ent = new CwEntry(entTmp, )
            ent = null;
        }
        else {
            ent = new CwEntry(cwdb.getRandom(b.getHeight()), 0, 0, CwEntry.Direction.VERT);
        }
        return ent;
    }
    public void updateBoard(Board b, CwEntry e) {
        String word = e.getWord();
        int x = e.getX();
        int y = e.getY();
        int dir = e.getDir();
        if(b.getCell(x, y).getContent().equals("")) { //start algorytmu
            for(int i=0; i<b.getWidth(); i++) {
                for(int j=0; j<b.getHeight(); j++) {
                    BoardCell c = b.getCell(i, j);
                    if(i==0) {
                        c.disableHoriz();
                        c.disableVert();
                        c.enableHorizStart();
                    }
                    else {
                        c.disableVert();
                        c.disableHorizStart();
                    }
                }    
            } 
        }
        else {
            for(int i=0; i<word.length()-1; i++) {
                BoardCell c;
                if(dir==1) { //pionowo
                    c = b.getCell(x, y+i);
                }
                else {
                    c = b.getCell(x+i, y);
                }
                c.disableHoriz();
            }
        }
        
        for(int i=0; i<word.length(); i++) {
            BoardCell c = new BoardCell();
            c.setContent(Character.toString(word.charAt(i)));
            b.setCell(x, y, c);
            if(dir==1) {
                y++;
            }
            else {
                x++;
            }
        }
    }
    private String createPattern(String st, int len) {
        String pattern = "^"+st.toLowerCase()+"[a-z]{,"+(len-1)+"}$";
        return pattern;
    }
}
