/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.strategy;

import crossword.Crossword;
import crossword.CwEntry;
import crossword.board.Board;

/**
 *
 * @author BamBalooon
 */
public abstract class Strategy {
    public abstract CwEntry findEntry(Crossword cw);
    public abstract void updateBoard(Board b, CwEntry e);
}
