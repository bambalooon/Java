package View;

import java.awt.EventQueue;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Rectangle;

import Communication.*;
import Settings.*;

public class MessageWindow {

	private JFrame frame;
	private Map<Long, MessageTab> tabs;
	private JTabbedPane tabbedPane;
	
	public static MessageWindow instance = null;


	/**
	 * Create the application.
	 */
	public MessageWindow() {
		tabs = new HashMap<Long, MessageTab>();
		initialize();
		frame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
	}
	
	public void addContactTab(Contact c) {
		MessageTab tab = tabs.get(c.getID());
		if(tab==null) {
			tab = new MessageTab(this);
			tab.setContact(c);
			tabs.put(c.getID(), tab);
			tabbedPane.addTab(c.getNick(), null, tab, "");
			tab.setTabbedPane(tabbedPane);
		}
		tab.showTab();
	}
	
	public void show() {
		frame.setVisible(true);
	}
	public void hide() {
		frame.setVisible(false);
	}
	public void removeTab(long id) {
		tabs.remove(id);
		if(tabs.size()==0) {
			hide();
		}
	}
	
	public void addMessage(Message msg) {
		long id = msg.getSid();
		MessageTab tab = tabs.get(id);
		if(tab==null) {
			//create new tab
			Contact c = ContactList.contactList.getContact(id);
			if(c==null) {
				c = new Contact(id, "unknown"+id);
			}
			addContactTab(c);
			tab = tabs.get(id);
		}
		show();
		StyledDocument doc = tab.textPane.getStyledDocument();
		try {
			doc.insertString(doc.getEndPosition().getOffset(), tab.contact.getNick()+":\n"+msg.getContent()+"\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
