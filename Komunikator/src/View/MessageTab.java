package View;

import Communication.*;
import Settings.*;
import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class MessageTab extends JPanel {
	JTabbedPane tabbedPane;
	Contact contact;
	MessageWindow window;
	JTextPane textPane;
	JTextArea textArea;
	public MessageTab(MessageWindow win) {
		window = win; 
		
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		textArea = new JTextArea();
		textArea.setRows(3);
		
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==10) {
					StyledDocument doc = textPane.getStyledDocument();
					try {
						doc.insertString(doc.getEndPosition().getOffset(), "Ja:\n"+textArea.getText()+"\n", null);
						MessageFactory.factory.addOutMsg(new Message(Settings.settings.getID(), contact.getID(), textArea.getText()));
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10) {
					textArea.setText("");
				}
			}
		});
		
		add(textArea, BorderLayout.SOUTH);
		
	}
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		int index = tabbedPane.indexOfComponent(this);
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane, this));
	}
	public void setContact(Contact c) {
		contact = c;
	}
	public void showTab() {
		int index = tabbedPane.indexOfComponent(this);
		tabbedPane.setSelectedIndex(index);
	}
	public void destroy() {
		window.removeTab(contact.getID());
	}
}
