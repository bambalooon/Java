package crossword.strategy;

import crossword.Crossword;
import crossword.CwEntry;
import crossword.board.BoardCell;
import crossword.board.Board;
import java.util.*;

import crossword.dictionary.*;
import crossword.exceptions.*;

public class MedStrategy extends Strategy {
	public class Pair {
		int x, y;
		Pair(int _x, int _y) {
			x = _x;
			y = _y;
		}
		Pair() {
			this(0,0);
		}
		int getX() {
			return x;
		}
		int getY() {
			return y;
		}
		public boolean equals(Object obj){
			return (obj instanceof Pair
					&& x==((Pair)obj).getX() 
					&& y==((Pair)obj).getY() );
		}
	}
	LinkedList<Pair> vIntSec = new LinkedList<Pair>(); //miejsca w ktorych mozna szukac nastepnego zaczepu hasla - od hasel pionowych
	LinkedList<Pair> hIntSec = new LinkedList<Pair>();//od hasel poziomych
    public CwEntry findEntry(Crossword cw) {
        Iterator<CwEntry> iter = cw.getROEntryIter();
        Board b = cw.getBoardCopy();
        InteliCwDB cwdb = cw.getCwDB();
        CwEntry ent;
        
        if(!iter.hasNext()) { //1. wywolanie..
        	int x = (int) Math.floor(b.getWidth()/2);
        	int y = 1;
        	Entry tmp = cwdb.getRandom();
        	if(tmp.getWord().length()>(b.getHeight()-1)) {
        		int i;
        		
        		for(i=b.getHeight()-1; i>1; i--) {
        			try {
        				tmp = cwdb.getRandom(i);
        				break;
        			}
        			catch(cwdbException e) {}
        		}
        		if(i<=1)
        			return null; //koniec generowania krzyzowki
        	}
        	ent = new CwEntry(tmp, x, y, CwEntry.Direction.VERT);
        	
        	for(int i=0; i<ent.getWord().length(); i++)
        		vIntSec.add(new Pair(x,y+i));
        }
        else {
        	while(vIntSec.size()>0 || hIntSec.size()>0) {
	        	Random rn = new Random();
	        	Pair p;
	        	int x, y, stx, sty, enx, eny;
	        	if(vIntSec.size()>hIntSec.size()) {
	        		p = vIntSec.get(rn.nextInt(vIntSec.size()));
	        		x = p.getX();
	            	y = p.getY();
	            	stx = 1; enx = b.getWidth()-1;
	            	sty = y; eny = y;
	        	}
	        	else {
	        		p = hIntSec.get(rn.nextInt(hIntSec.size()));
	        		x = p.getX();
	            	y = p.getY();
	            	stx = x; enx = x;
	            	sty = 1; eny = b.getHeight()-1;
	        	}
	        	while( enx>stx || eny>sty )  {
		        	try {
			        	String pattern = b.createPattern(stx, sty, enx, eny);
			        	Entry tmp = cwdb.getRandom(pattern);
			        	if(enx>stx) {
			        		stx = getStartIndex(b, tmp, stx, sty, x, y, false);
			        		ent = new CwEntry(tmp, stx, y, CwEntry.Direction.HORIZ);
			        	}
			        	else {
			        		sty = getStartIndex(b, tmp, stx, sty, x, y, true);
			        		ent = new CwEntry(tmp, x, sty, CwEntry.Direction.VERT);
			        	}
			        	return ent;
		        	}
		        	catch(cwdbException e) { 
		        		if(enx>stx) {
		        			if(stx>=x)
		        				do enx--;
		        					while(!b.getCell(enx, sty).isHorizEndCell() && enx>stx);
		        			else if(enx<=x)
		        				do stx++;
		        					while(!b.getCell(stx, sty).isHorizStartCell() && enx>stx);
		        			else {
		        				if(rn.nextBoolean())
		        					do stx++;
		        						while(!b.getCell(stx, sty).isHorizStartCell() && enx>stx);
		        				else
		        					do enx--;
		        						while(!b.getCell(enx, sty).isHorizEndCell() && enx>stx);
		        			}
		        		}
		        		else {
		        			if(sty>=y)
		        				do eny--;
		        					while(!b.getCell(enx, eny).isVertEndCell() && eny>sty);
		        			else if(eny<=y)
		        				do sty++;
		        					while(!b.getCell(enx, sty).isVertStartCell() && eny>sty);
		        			else {
		        				if(rn.nextBoolean())
		        					do sty++;
		        						while(!b.getCell(enx, sty).isVertStartCell() && eny>sty);
		        				else
		        					do eny--;
		        						while(!b.getCell(enx, eny).isVertEndCell() && eny>sty);
		        			}
		        		}
		        	}
	        	}
	        	p = new Pair(x, y);
	        	if(vIntSec.size()>hIntSec.size()) {
	        		b.getCell(x-1, y).disableHoriz();
	        		b.getCell(x-1, y).disableVert();
	        		if(x+1<b.getWidth()) {
	        			b.getCell(x+1, y).disableHoriz();
		        		b.getCell(x+1, y).disableVert();
	        		}
	        	}
	        	else {
	        		b.getCell(x, y-1).disableHoriz();
	        		b.getCell(x, y-1).disableVert();
	        		if(y+1<b.getHeight()) {
	        			b.getCell(x, y+1).disableHoriz();
		        		b.getCell(x, y+1).disableVert();
	        		}
	        	}
	        	hIntSec.remove(p);
	        	vIntSec.remove(p);
        	}
        	return null;
        }
        return ent;
    }
    public void updateBoard(Board b, CwEntry e) {
        //tu blokada?
    	int x = e.getX();
    	int y = e.getY();
    	int d = e.getDir();
    	String word = e.getWord().toLowerCase();
    	disableEdges(e, b);
    	for(int i=0; i<word.length(); i++) {
    		BoardCell c = new BoardCell();
    		if(d==0) {
    			if(y+1<b.getHeight())
    				b.getCell(x+i, y+1).disableVertStart();
    			b.getCell(x+i, y-1).disableVertEnd();
    			if(y+1<b.getHeight())
    				b.getCell(x+i, y+1).disableHoriz();
    			b.getCell(x+i, y-1).disableHoriz();
    			if(!b.getCell(x+i, y).getContent().equals("")) {
    				for(int j=-1; j<2; j++) {
    					if((x+i+j)<b.getWidth() && y<b.getHeight()) {
							BoardCell c2 = b.getCell(x+i+j, y);
							c2.disableHoriz();
							c2.disableVert();
							hIntSec.remove(new Pair(x+i+j, y));
						}
    				}
    				for(int j=-1; j<2; j++) {
    					if((x+i)<b.getWidth() && (y+j)<b.getHeight()) {
							BoardCell c2 = b.getCell(x+i, y+j);
							c2.disableHoriz();
							c2.disableVert();
							vIntSec.remove(new Pair(x+i, y+j));
						}
    				}
    			}
    			else if(!b.getCell(x+i, y).isDisabledVert()) {
    				Pair p = new Pair(x+i, y);
    				if(!hIntSec.contains(p))
    					hIntSec.add(p);
    			}
    			c.setContent(word.substring(i, i+1));
    			b.setCell(x+i, y, c);
    		}
    		else {
    			if(x+1<b.getWidth())
    				b.getCell(x+1, y+i).disableHorizStart();
    			b.getCell(x-1, y+i).disableHorizEnd();
    			if(x+1<b.getWidth())
    				b.getCell(x+1, y+i).disableVert();
    			b.getCell(x-1, y+i).disableVert();
    			if(!b.getCell(x, y+i).getContent().equals("")) {
    				for(int j=-1; j<2; j++) {
    					if((x+j)<b.getWidth() && (y+i)<b.getHeight()) {
							BoardCell c2 = b.getCell(x+j, y+i);
							c2.disableHoriz();
							c2.disableVert();
							hIntSec.remove(new Pair(x+j, y+i));
						}
    				}
    				for(int j=-1; j<2; j++) {
    					if(x<b.getWidth() && (y+i+j)<b.getHeight()) {
							BoardCell c2 = b.getCell(x, y+i+j);
							c2.disableHoriz();
							c2.disableVert();
							vIntSec.remove(new Pair(x, y+i+j));
						}
    				}
    			}
    			else if(!b.getCell(x, y+i).isDisabledHoriz()) {
    				Pair p = new Pair(x, y+i);
    				if(!vIntSec.contains(p))
    					vIntSec.add(p);
    			}
    			c.setContent(word.substring(i, i+1));
    			b.setCell(x, y+i, c);
    		}
    		
    	}
    }
    private int getStartIndex(Board b, Entry ent, int stx, int sty, int x, int y, boolean dir) {
    	String word = ent.getWord().toLowerCase();
    	int sLen;
    	if(dir) {
    		if((y-sty+1)>word.length()) 
    			sty = y-word.length()+1;
    		sLen = y-sty+1;
    	}
    	else {
    		if((x-stx+1)>word.length()) 
    			stx = x-word.length()+1;
    		sLen = x-stx+1;
    	}
    	
    	
    	for(int i=0; i<sLen; i++) {
    		boolean good = true;
    		for(int j=0; j<word.length(); j++) {
    			if(dir) { //vert
	    			String str = b.getCell(x, sty+i+j).getContent();
	    			if(b.getCell(x, sty+i+j).isDisabledVert() || (!str.equals("") && !str.equals(word.substring(j, j+1)))) {
	    				good = false; break;
	    			}
	    				 
    	    	}
    	    	else {
	    			String str = b.getCell(stx+i+j, y).getContent();
	    			if(b.getCell(stx+i+j, y).isDisabledHoriz() || (!str.equals("") && !str.equals(word.substring(j, j+1)))) {
	    				good = false; break;
	    			}
    	    	}
    		}
    		if(dir && good)
    			return sty+i;
    		else if(good)
    			return stx+i;
    	}
    	return 1; //error
    }
    private void disable1stRowCol(Board b) {
    	for(int i=0; i<b.getWidth(); i++) {
    		b.getCell(i, 0).disableVertStart();
    	}
    	for(int i=0; i<b.getHeight(); i++) {
    		b.getCell(0, i).disableHorizStart();
    	}
    }
    private void disableEdges(CwEntry ent, Board b) {
    	int x = ent.getX();
    	int y = ent.getY();
    	int len = ent.getWord().length();
    	BoardCell c;
    	if(ent.getDir()==0) { //0 - Horiz
    		c = b.getCell(x-1, y);
    		c.disableHoriz();
    		c.disableVert();
    		if(x+len<b.getWidth()) {
	    		c = b.getCell(x+len, y);
	    		c.disableHoriz();
	    		c.disableVert();
    		}
    	}
    	else {
    		c = b.getCell(x, y-1); 
    		c.disableHoriz();
    		c.disableVert();
    		if(y+len<b.getHeight()) {
	    		c = b.getCell(x, y+len);
	    		c.disableHoriz();
	    		c.disableVert();
    		}
    	}
    }
    private LinkedList<BoardCell> getHorizStartCells(LinkedList<BoardCell> list) {
    	LinkedList<BoardCell> hList = new LinkedList<BoardCell>();
    	for(BoardCell c : list) {
    		if(c.isHorizStartCell()) {
    			hList.add(c);
    		}
    	}
    	return hList;
    }
    private LinkedList<BoardCell> getVertStartCells(LinkedList<BoardCell> list) {
    	LinkedList<BoardCell> vList = new LinkedList<BoardCell>();
    	for(BoardCell c : list) {
    		if(c.isVertStartCell()) {
    			vList.add(c);
    		}
    	}
    	return vList;
    }
}
