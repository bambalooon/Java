/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import crossword.*;
import crossword.board.Board;
import crossword.board.BoardCell;
import crossword.browser.CwBrowser;
import crossword.strategy.MedStrategy;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author BamBalooon
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }
    
    public class CwPanel extends JPanel {
        private Crossword cw = null;
        private Board board = null;
        private boolean solved = false;
        public JTextField[][] textFields = null;
        private CwBrowser browser = null;
        public final int maxWidth = 12;
        public final int maxHeight = 12;
        public final int size = 40;
        private File directory = null;
        
        public CwPanel() {
            setBorder(BorderFactory.createLineBorder(Color.black));
            textFields = new JTextField[maxWidth][maxHeight];
        }
        
        private void hideTF() {
            for(int i=0; i<maxWidth; i++) {
                for(int j=0; j<maxHeight; j++) {
                    textFields[i][j].setVisible(false);
                }
            }
        }
        
        public void Solve() {
            hideTF();
            solved = true;
        }
        
        public void setDirectory(File file) {
            directory = file;
            if(browser==null) {
                browser = new CwBrowser();
                browser.setList(filesList);
            }
            browser.setDirectory(file);
        }
        
        public void save() throws FileNotFoundException, IOException {
            browser.save(this.getCrossword());
        }
        
        public void load() throws ClassNotFoundException, FileNotFoundException, IOException {
            browser.load();
        }
        
        public File getDirectory() {
            return directory;
        }
               
        public void attachCw(Crossword cw) {
            this.hideTF();
            this.solved =false;
            this.cw = cw;
            this.board = cw.getBoardCopy();
        }
        
        public Crossword getCrossword() {
            return cw;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800,550);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);       
            int fontSize = 20;
            int xMv = 20;
            int yMv = 30;
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            if(board!=null) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(Color.GRAY);
                g2.fillRect(0, 0, getWidth(), getHeight());
                for(int i=0; i<board.getWidth(); i++) {
                    for(int j=0; j<board.getHeight(); j++) {
                        BoardCell c = board.getCell(i, j);
                        if(!c.getContent().equals("")) {
                            g2.setPaint(Color.white);
                            g2.fill(new Rectangle2D.Double(i*size, j*size, size, size));
                            g2.setPaint(Color.black);
                            g2.draw(new Rectangle2D.Double(i*size, j*size, size, size));
                            if(solved) {
                                g.drawString(c.getContent(), i*size+xMv, j*size+yMv);
                            }
                            else {
                                textFields[i][j].setText("");
                                textFields[i][j].setVisible(true);
                                textFields[i][j].setEditable(true);
                            }
                        }
                    }
                }
                g2.setPaint(Color.black);
            }
            Iterator<CwEntry> iter = null;
            if(cw!=null) {
                iter = cw.getROEntryIter();
            }
            if(iter!=null) {
                int horiz = 1;
                int vert = 1;
                LinkedList<String> horizClues = new LinkedList<>();
                LinkedList<String> vertClues = new LinkedList<>();
                int lineH = 20;
                fontSize = 14;
                g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
                while(iter.hasNext()) {
                    CwEntry ent = iter.next();
                    if(ent.getDir()==0) { //Horiz
                        g.drawString(((Integer)horiz).toString(), (ent.getX()-1)*size+xMv+8, ent.getY()*size+yMv-4);
                        
                        horizClues.addLast(ent.getClue());
                        horiz++;
                    }
                    else {
                        g.drawString(((Integer)vert).toString(), ent.getX()*size+xMv-4, (ent.getY()-1)*size+yMv+6);
                        vertClues.addLast(ent.getClue());
                        vert++;
                    }
                }
                String label = "<html>POZIOMO:<br>";
                int i=1;
                for(String haslo : horizClues) {
                    label+=(i++)+". "+haslo+"<br>";
                }
                label+="<br>PIONOWO:<br>";
                i=1;
                for(String haslo : vertClues) {
                    label+=(i++)+". "+haslo+"<br>";
                }
                label+="</html>";
                CluesLabel.setText(label+" ");
            }
        }
    }
    public class CwStatic extends JPanel {
        private final int xMv = -7;
        private final int yMv = 0;
        public CwStatic() {
            CwPanel cp = (CwPanel)cwPanel;
            JTextField[][] textFields = cp.textFields;
            for(int i=0; i<cp.maxWidth; i++) {
                for(int j=0; j<cp.maxHeight; j++) {
                    textFields[i][j] = new JTextField("", 1);
                    textFields[i][j].setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                    textFields[i][j].setHorizontalAlignment(javax.swing.JTextField.CENTER);
                    textFields[i][j].setSize(new java.awt.Dimension(cp.size, cp.size));
                    textFields[i][j].setBounds(i*cp.size+xMv, j*cp.size+yMv, cp.size, cp.size);
                    textFields[i][j].setVisible(false);
                    textFields[i][j].setName("TF"+i+j);
                    add(textFields[i][j]);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        heightSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        widthSpinner = new javax.swing.JSpinner();
        genBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pathField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        setDirBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        filesList = new javax.swing.JList();
        jButton5 = new javax.swing.JButton();
        cwPanel = new CwPanel();
        cwStatic = new CwStatic();
        CluesLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(749, 40));
        jPanel1.setVerifyInputWhenFocusTarget(false);

        jLabel1.setText("wysokość");

        heightSpinner.setName("heightSpinner"); // NOI18N
        heightSpinner.setRequestFocusEnabled(false);
        heightSpinner.setValue(((CwPanel)cwPanel).maxHeight);

        jLabel2.setText("szerokość");

        widthSpinner.setName("widthSpinner"); // NOI18N
        widthSpinner.setValue(((CwPanel)cwPanel).maxWidth);

        genBtn.setText("generuj");
        genBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genBtnActionPerformed(evt);
            }
        });

        jButton1.setText("Drukuj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtn(evt);
            }
        });

        jButton2.setText("Zapisz");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCwBtn(evt);
            }
        });

        jButton3.setText("Rozwiąż");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveCWbtn(evt);
            }
        });

        pathField.setText("Ścieżka..");
        pathField.setEnabled(false);

        jButton4.setText("...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFileChooserBtn(evt);
            }
        });

        setDirBtn.setText("wczytaj");
        setDirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDirBtnActionPerformed(evt);
            }
        });

        filesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(filesList);

        jButton5.setText("wczytaj");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCwBtn(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(genBtn)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setDirBtn))
                    .addComponent(pathField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jButton5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(428, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(widthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(genBtn))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(setDirBtn)
                    .addComponent(jButton2))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        cwPanel.setPreferredSize(new java.awt.Dimension(800, 1055));

        cwStatic.setOpaque(false);

        CluesLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout cwStaticLayout = new javax.swing.GroupLayout(cwStatic);
        cwStatic.setLayout(cwStaticLayout);
        cwStaticLayout.setHorizontalGroup(
            cwStaticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cwStaticLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(CluesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        cwStaticLayout.setVerticalGroup(
            cwStaticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cwStaticLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(CluesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout cwPanelLayout = new javax.swing.GroupLayout(cwPanel);
        cwPanel.setLayout(cwPanelLayout);
        cwPanelLayout.setHorizontalGroup(
            cwPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cwPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cwStatic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cwPanelLayout.setVerticalGroup(
            cwPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cwPanelLayout.createSequentialGroup()
                .addComponent(cwStatic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1201, Short.MAX_VALUE)
            .addComponent(cwPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1201, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cwPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void genBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genBtnActionPerformed
       try {
	        Crossword cw = new Crossword("../../files/cwdb.txt", Integer.parseInt(widthSpinner.getValue().toString()), Integer.parseInt(heightSpinner.getValue().toString()));
	        MedStrategy s = new MedStrategy();
	        cw.generate(s);
	        cw.getBoardCopy().print();
                ((CwPanel)cwPanel).attachCw(cw);
                cwPanel.paint(cwPanel.getGraphics());
    	}
    	catch(Exception e) { System.out.print(e.getMessage()); } 
    }//GEN-LAST:event_genBtnActionPerformed

    private void solveCWbtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveCWbtn
        ((CwPanel)cwPanel).Solve();
        //error when doesnt exist
    }//GEN-LAST:event_solveCWbtn

    private void saveCwBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCwBtn
        try {
            ((CwPanel)cwPanel).save();
        }
        catch(Exception e) {
            
        }
        //error handling - when doesnt have folder
        //show dialog (saved to..)
    }//GEN-LAST:event_saveCwBtn

    private void showFileChooserBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFileChooserBtn
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showDialog(this, "Wybierz");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            pathField.setText(file.getAbsolutePath());
            ((CwPanel)cwPanel).setDirectory(file);
            
        }
    }//GEN-LAST:event_showFileChooserBtn

    private void setDirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setDirBtnActionPerformed
        try {
            ((CwPanel)cwPanel).load();
        }
        catch(FileNotFoundException e) {
            System.out.print("fnfe"+e.getMessage());
        }
        catch(ClassNotFoundException e) {
            System.out.print("cnfe"+e.getMessage());
        }
        catch(IOException e) {
            System.out.print("ioe"+e.getMessage());
        }
        
    }//GEN-LAST:event_setDirBtnActionPerformed

    private void printBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtn
        new Printer(((CwPanel)cwPanel).getCrossword()).actionPerformed(evt);
    }//GEN-LAST:event_printBtn

    private void loadCwBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCwBtn
        Crossword cw = (Crossword) filesList.getSelectedValue();
        ((CwPanel)cwPanel).attachCw(cw);
        cwPanel.paint(cwPanel.getGraphics());
    }//GEN-LAST:event_loadCwBtn

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CluesLabel;
    private javax.swing.JPanel cwPanel;
    private javax.swing.JPanel cwStatic;
    private javax.swing.JList filesList;
    private javax.swing.JButton genBtn;
    private javax.swing.JSpinner heightSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pathField;
    private javax.swing.JButton setDirBtn;
    private javax.swing.JSpinner widthSpinner;
    // End of variables declaration//GEN-END:variables
}
