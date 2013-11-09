/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.board;

/**
 *
 * @author BamBalooon
 */
public class BoardCell implements java.io.Serializable {
    public class Options implements java.io.Serializable {
        public class Horiz implements java.io.Serializable {
            Boolean start, inner, end;
            public Horiz() {
                start = true;
                inner = true;
                end = true;
            }
        }       
        public class Vert implements java.io.Serializable {
            Boolean start, inner, end;
            public Vert() {
                start = true;
                inner = true;
                end = true;
            }
        }
        Horiz h;
        Vert v;
        public Options() {
            h = new Horiz();
            v = new Vert();
        }
    }
    private String content;
    private Options options;
    public BoardCell() {
        this.options = new Options();
        this.content = "";
    }
    public BoardCell(BoardCell another) {
        this.content = another.content;
        this.options = new Options();
        this.options.h.start = another.options.h.start;
        this.options.h.inner = another.options.h.inner;
        this.options.h.end = another.options.h.end;
        this.options.v.start = another.options.v.start;
        this.options.v.inner = another.options.v.inner;
        this.options.v.end = another.options.v.end;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent() {
        return content;
    }
    public Boolean isHorizStartCell() { //moge dodac?
    	return options.h.start;
    }
    public Boolean isVertStartCell() {
    	return options.v.start;
    }
    public Boolean isHorizEndCell() { //moge dodac?
    	return options.h.end;
    }
    public Boolean isVertEndCell() {
    	return options.v.end;
    }
    public Boolean isStartCell() {
        return (options.h.start || options.v.start);
    }
    public Boolean isDisabled() {
    	return !(options.h.start || options.h.end || options.h.inner || options.v.start || options.v.end || options.v.inner);
    }
    public Boolean isDisabledHoriz() {
    	return !(options.h.start || options.h.end || options.h.inner);
    }
    public Boolean isDisabledVert() {
    	return !(options.v.start || options.v.end || options.v.inner);
    }
    public void disableHorizStart() {
        options.h.start = false;
    }
    public void disableHorizInner() {
        options.h.inner = false;
    }
    public void disableHorizEnd() {
        options.h.end = false;
    }
    public void enableHorizStart() {
        options.h.start = true;
    }
    public void enableHorizInner() {
        options.h.inner = true;
    }
    public void enableHorizEnd() {
        options.h.end = true;
    }
    public void disableVertStart() {
        options.v.start = false;
    }
    public void disableVertInner() {
        options.v.inner = false;
    }
    public void disableVertEnd() {
        options.v.end = false;
    }
    public void enableVertStart() {
        options.v.start = true;
    }
    public void enableVertInner() {
        options.v.inner = true;
    }
    public void enableVertEnd() {
        options.v.end = true;
    }
    public void disableHoriz() {
        options.h.start = false;
        options.h.inner = false;
        options.h.end = false;
    }
    public void disableVert() {
        options.v.start = false;
        options.v.inner = false;
        options.v.end = false;
    }
}
