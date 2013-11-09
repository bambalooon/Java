/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.browser;

import crossword.Crossword;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author BamBalooon
 */
public interface Writer {
    void Write(Crossword cw) throws FileNotFoundException, IOException;
    long getUniqueID();
}
