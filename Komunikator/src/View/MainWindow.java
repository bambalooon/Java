package View;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Communication.Message;
import Connection.ClientConnection;
import Exceptions.NotLoggedException;
import Settings.*;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.io.*;

public class MainWindow {

	private JFrame frame;
	private DefaultListModel<Contact> userList;
	private AddUserDialog addUserDialog;
	
	public static MainWindow instance = null;


	public MainWindow() throws IOException {
		ContactList.contactList= new ContactList(); 
		initialize();
		updateContacts();
		MessageWindow.instance = new MessageWindow();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Numer: "+((Long)Settings.settings.getID()).toString());
		frame.setBounds(100, 100, 250, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		JMenuItem mntmZakocz = new JMenuItem("Zako\u0144cz");
		mnMain.add(mntmZakocz);
		
		JMenu mnNarz = new JMenu("Narz\u0119dzia");
		menuBar.add(mnNarz);
		
		JMenuItem mntmDodajKontakt = new JMenuItem("Dodaj kontakt");
		mntmDodajKontakt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					addUserDialog = new AddUserDialog(ContactList.contactList);
					addUserDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					addUserDialog.setVisible(true);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		mnNarz.add(mntmDodajKontakt);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		userList = new DefaultListModel<Contact>(); 
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		JList<Contact> list = new JList<Contact>(userList);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				JList jlist = (JList)evt.getSource();
				if(evt.getClickCount()==2) {
					int index = jlist.locationToIndex(evt.getPoint());
					MessageWindow.instance.addContactTab(userList.get(index));
					MessageWindow.instance.show();
				}
			}
		});
		scrollPane.setViewportView(list);
		list.setFont(new Font("Verdana", Font.PLAIN, 16));
		list.setFixedCellHeight(30);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new MyCellRenderer());
		
		frame.setVisible(true);
	}
	
	public void close() {
		frame.dispose();
	}
	
	public void updateContacts() {
		userList.removeAllElements();
		for(Contact c : ContactList.contactList.getContacts()) {
			userList.addElement(c);
		}
	}
	
	class MyCellRenderer extends JLabel implements ListCellRenderer {

		  public MyCellRenderer() {
		    setOpaque(true);
		  }

		  public Component getListCellRendererComponent(JList list, Object value, int index,
		      boolean isSelected, boolean cellHasFocus) {
		    setText(value.toString());
		    setHorizontalAlignment(CENTER);
		    

		    if (isSelected) {
		      setBackground(list.getSelectionBackground());
		      setForeground(list.getSelectionForeground());
		    } else {
		      setBackground(list.getBackground());
		      setForeground(list.getForeground());
		    }
		    return this;
		  }
	}
	public void addUserToTable(Message msg) {
		addUserDialog.addUserToTable(msg);
	}
}
