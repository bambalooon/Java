/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import crossword.*;
import crossword.board.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author BamBalooon
 */
public class Printer implements Printable, ActionListener {
    private Crossword cw;
    private final int size = 30;
    private final int xMargin = 50;
    private final int yMargin = 30;

    public Printer(Crossword cw){
        this.cw=cw;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        Board board = cw.getBoardCopy();
        

        for(int i=0; i<board.getWidth(); i++) {
            for(int j=0; j<board.getHeight(); j++) {
                BoardCell c = board.getCell(i, j);
                if(!c.getContent().equals("")) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setPaint(Color.LIGHT_GRAY);
                    g2.fill(new Rectangle2D.Double(xMargin+i*size, yMargin+j*size, size, size));
                    g2.setPaint(Color.black);
                    g2.draw(new Rectangle2D.Double(xMargin+i*size, yMargin+j*size, size, size));
                }
            }
        }

        Iterator<CwEntry> iter = cw.getROEntryIter();
        if(iter!=null) {
            int horiz = 1;
            int vert = 1;
            LinkedList<String> horizClues = new LinkedList<>();
            LinkedList<String> vertClues = new LinkedList<>();
            int lineH = 20;
            int fontSize = 14;
            int xMv = 20;
            int yMv = 30;
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            int xTextPos = 50;
            int yTextPos = 400;
            while(iter.hasNext()) {
                CwEntry ent = iter.next();
                if(ent.getDir()==0) { //Horiz
                    g.drawString(((Integer)horiz).toString(), xMargin+(ent.getX()-1)*size+xMv, yMargin+ent.getY()*size+yMv-10);
                    horizClues.addLast(ent.getClue());
                    horiz++;
                }
                else {
                    g.drawString(((Integer)vert).toString(), xMargin+ent.getX()*size+xMv-8, yMargin+(ent.getY()-1)*size+yMv-2);
                    vertClues.addLast(ent.getClue());
                    vert++;
                }
            }
            fontSize = 12;
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            g.drawString("POZIOMO:", xTextPos, yTextPos);
            int i=1;
            for(String haslo : horizClues) {
                g.drawString(i+". "+haslo, xTextPos, yTextPos+(i++)*lineH);
            }
            g.drawString("PIONOWO:", xTextPos, yTextPos+i*lineH);
            int j=1;
            for(String haslo : vertClues) {
                g.drawString(j+". "+haslo, xTextPos, yTextPos+(i+(j++))*lineH);
            }
            
        }



        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
    }
    
}
