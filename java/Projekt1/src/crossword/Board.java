/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.util.*;

/**
 *
 * @author BamBalooon
 */
public class Board {
    private BoardCell[][] board;
    private int width, height;
    public void Board(int w, int h) {
        width = w;
        height = h;
        board = new BoardCell[width][height];
    }
    public void Board(Board another) {
        this.width = another.width;
        this.height = another.height;
        this.board = new BoardCell[this.width][this.height];
        for(int i=0; i<this.width; i++) {
            for(int j=0; j<this.height; j++) {
                this.board[i][j] = another.board[i][j];
            }
        }
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public BoardCell getCell(int x, int y) {
        return board[x][y];
    }
    public void setCell(int x, int y, BoardCell c) {
        board[x][y] = c;
    }
    public LinkedList<BoardCell> getStartCells() {
        LinkedList<BoardCell> list;
        list = new LinkedList<>();
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                if(board[i][j].isStartCell()) {
                    list.add(board[i][j]);
                }
            }
        }
        return list;
    }
    public String createPattern(int fromx, int fromy, int tox, int toy) {
        String pattern = "^";
        for(int i=fromx; i<=tox; i++) {
            for(int j=fromy; j<=toy; j++) {
                String tmp = board[i][j].getContent();
                if( tmp.equals("")) {
                    pattern+=tmp;
                }
                else {
                    pattern+=".";
                }
            }
        }
        pattern+="$";
        return pattern.toLowerCase();
    }
}
