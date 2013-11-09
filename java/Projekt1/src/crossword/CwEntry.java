/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import crossword.dictionary.Entry;

/**
 *
 * @author BamBalooon
 */
public class CwEntry extends Entry {
    public enum Direction {
        HORIZ, VERT;
    }
    private int x;
    private int y;
    private Direction d;
    public CwEntry(Entry ent, int x, int y, Direction d) { //pradopodobnie dodac x,y,d
        super(ent.getWord(), ent.getClue());
        this.x = x;
        this.y = y;
        this.d = d;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getDir() {
        return d.ordinal();
    }
}
