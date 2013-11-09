/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.board;

import java.util.*;

/**
 *
 * @author BamBalooon
 */
public class Board implements java.io.Serializable {
    private BoardCell[][] board;
    private int width, height;
    public Board(int w, int h) {
        width = w;
        height = h;
        board = new BoardCell[width][height];
        for(int i=0; i<width; i++) {
        	for(int j=0; j<height; j++) {
        		board[i][j] = new BoardCell();
        	}
        }
    }
    public Board(Board another) {
        this.width = another.width;
        this.height = another.height;
        this.board = new BoardCell[this.width][this.height];
        for(int i=0; i<this.width; i++) {
            for(int j=0; j<this.height; j++) {
                this.board[i][j] = new BoardCell(another.board[i][j]);
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
        String[] let = new String[ (tox-fromx)>(toy-fromy) ? tox-fromx+1 : toy-fromy+1 ]; //ilosc liter tyle ile ma slowo
        LinkedList<Integer> letCnt = new LinkedList<Integer>();
        int k=0;
        letCnt.addLast(0);
        for(int i=fromx; i<=tox; i++) {
            for(int j=fromy; j<=toy; j++) {
            	let[k++] = board[i][j].getContent();
            	if(tox>fromx && board[i][j].isDisabledHoriz()) { //przez to nie znajdzie dobrego wiec bedzie musial obcinac st/en
        			let[k-1] = "%";
        			letCnt.addLast(0);
        		}
        		else if(toy>fromy && board[i][j].isDisabledVert()) {
        			let[k-1] = "%";
        			letCnt.addLast(0);
        		}
        		else if(let[k-1].equals("")) {
            		Integer lt = letCnt.getLast();
            		lt++;
            		letCnt.removeLast();
            		letCnt.addLast(lt);
            	}
        		else {
        			letCnt.addLast(0);
        		}
            }
        }
        pattern+="[a-ząęćźżóśłń]{0,"+letCnt.getFirst()+"}";
        Iterator<Integer> iter = letCnt.iterator();
        iter.next();
        int j=0;
        for(int i=0; i<let.length; i++) {
        	if(!let[i].equals("")) {
        		pattern+=let[i];
        		if(letCnt.size()-2>j++)
        			pattern+="[a-ząęćźżóśłń]{"+iter.next()+"}";
        	}
        }
        pattern+="[a-ząęćźżóśłń]{0,"+letCnt.getLast()+"}$";
        return pattern.toLowerCase();
    }
    public void print() {
    	for(int i=0; i<height; i++) {
    		for(int j=0; j<width; j++) {
    			BoardCell c = board[j][i];
    			String letter = c.getContent();
    			if(letter.equals(""))
    				letter=" ";
    			System.out.print(letter);
    		}
    		System.out.println("");
    	}
    }
    public void printEn() {
    	for(int i=0; i<height; i++) {
    		for(int j=0; j<width; j++) {
    			BoardCell c = board[j][i];
    			if(c.isDisabled())
    				System.out.print("x");
    			else if(c.isDisabledHoriz())
    				System.out.print("v");
    			else if(c.isDisabledVert())
    				System.out.print("h");
    			else
    				System.out.print(" ");
    		}
    		System.out.println("");
    	}
    }
}
