/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;

/**
 *
 * @author BamBalooon
 */
public class Entry implements java.io.Serializable {
    private String word;
    private String clue;
    public Entry(String word, String clue) {
        this.word = word;
        this.clue = clue;
    }
    public Entry() {}
    public String getWord() {
        return this.word;
    }
    public String getClue() {
        return this.clue;
    }
}
