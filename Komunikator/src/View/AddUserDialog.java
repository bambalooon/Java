package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.*;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Vector;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Communication.Message;
import Communication.MessageFactory;
import Settings.*;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class AddUserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField userID;
	private JTextField userNick;
	private ContactList contactList;
	private JTextField userNick2;
	private Box contentBox;

	/**
	 * Create the dialog.
	 */
	public AddUserDialog(ContactList list) {
		contactList = list;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			contentBox = Box.createVerticalBox();
			contentPanel.add(contentBox);
			{
				Box verticalBox_1 = Box.createVerticalBox();
				contentBox.add(verticalBox_1);
				{
					Box horizontalBox = Box.createHorizontalBox();
					horizontalBox.setBorder(new EmptyBorder(0, 0, 10, 0));
					verticalBox_1.add(horizontalBox);
					{
						JLabel lblNumerUytkownika = new JLabel("Numer u\u017Cytkownika:");
						horizontalBox.add(lblNumerUytkownika);
						lblNumerUytkownika.setHorizontalAlignment(SwingConstants.RIGHT);
					}
					{
						userID = new JTextField();
						horizontalBox.add(userID);
						userID.setColumns(10);
					}
				}
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox_1.add(horizontalBox);
					{
						JLabel lblNick = new JLabel("Nick:");
						horizontalBox.add(lblNick);
						lblNick.setHorizontalAlignment(SwingConstants.RIGHT);
					}
					{
						userNick = new JTextField();
						horizontalBox.add(userNick);
						userNick.setColumns(10);
					}
				}
				{
					JButton addBtn = new JButton("Dodaj");
					addBtn.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent arg0) {
							try {
								long id = Long.parseLong(userID.getText());
								String nick = userNick.getText();
								contactList.addContact(new Contact(id, nick));
								MainWindow.instance.updateContacts();
								JOptionPane.showMessageDialog(null, "Kontakt dodany!");
							} catch(NumberFormatException e) {
								JOptionPane.showMessageDialog(null, "B³êdny numer u¿ytkownika.");
								//e.printStackTrace();
							} catch(IOException e) {
								JOptionPane.showMessageDialog(null, "B³¹d zapisu.");
								//System.out.print("err");
							}
						}
					});
					verticalBox_1.add(addBtn);
				}
			}
			{
				Box verticalBox_1 = Box.createVerticalBox();
				verticalBox_1.setBorder(new EmptyBorder(20, 0, 0, 0));
				contentBox.add(verticalBox_1);
				{
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox_1.add(horizontalBox);
					{
						JLabel label = new JLabel("Nick:");
						label.setHorizontalAlignment(SwingConstants.RIGHT);
						horizontalBox.add(label);
					}
					{
						userNick2 = new JTextField();
						userNick2.setColumns(10);
						horizontalBox.add(userNick2);
					}
				}
				{
					JButton searchBtn = new JButton("Szukaj");
					searchBtn.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent arg0) {
							String searchPhrase = userNick2.getText();
							MessageFactory.factory.addOutMsg(new Message(Settings.settings.getID(), searchPhrase, Message.Mode.GET_CONTACTS));
						}
					});
					searchBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
						}
					});
					verticalBox_1.add(searchBtn);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						close();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private void close() {
		this.dispose();
	}
	public void addUserToTable(Message msg) {
		JTable table = new JTable();
		
		final DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Numer");
		model.addColumn("Nick");
		
		String content = msg.getContent();
		String[] rows = content.split(";");
		for(int i=0; i<rows.length; i++) {
			Object[] tmp = rows[i].split(":");
			model.addRow(tmp);
		}
		
		TableButton buttonEditor = new TableButton("+");
		buttonEditor.addTableButtonListener(new TableButtonListener() {
		  @Override
		  public void tableButtonClicked(int row, int col) {
			  long id = Long.parseLong(model.getValueAt(row, 0).toString());
			  String nick = model.getValueAt(row, 1).toString();
			  Contact c = new Contact(id, nick);
			  try {
				  ContactList.contactList.addContact(c);
				  MainWindow.instance.updateContacts();
				  
			  }
			  catch(IOException e) {
				  JOptionPane.showMessageDialog(null, "B³¹d w trakcie zapisu listy kontaktów!");
			  }
		  }     
		}); 

		TableColumn col = new TableColumn(0, 10);
		col.setHeaderValue("");
		col.setCellRenderer(buttonEditor);
		col.setCellEditor(buttonEditor);
		table.setModel(model);
		table.addColumn(col);
		
		JScrollPane spane = new JScrollPane();
		spane.setViewportView(table);
		JOptionPane.showMessageDialog(null, spane);
	}
	
	public interface TableButtonListener extends EventListener {
	  public void tableButtonClicked( int row, int col );
	}
	
	public class TableButton extends JButton implements TableCellRenderer, TableCellEditor {
		  private int selectedRow;
		  private int selectedColumn;
		  Vector<TableButtonListener> listener;

		  public TableButton(String text) {
		    super(text); 
		    listener = new Vector<TableButtonListener>();
		    addActionListener(new ActionListener() { 
		      public void actionPerformed( ActionEvent e ) {
		        for(TableButtonListener l : listener) { 
		          l.tableButtonClicked(selectedRow, selectedColumn);
		      }
		    }});
		  }

		  public void addTableButtonListener( TableButtonListener l ) {
		    listener.add(l);
		  }

		  public void removeTableButtonListener( TableButtonListener l ) { 
		    listener.remove(l);
		  }

		  @Override 
		  public Component getTableCellRendererComponent(JTable table,
		    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		    return this;
		  }

		  @Override
		  public Component getTableCellEditorComponent(JTable table,
		      Object value, boolean isSelected, int row, int col) {
		    selectedRow = row;
		    selectedColumn = col;
		    return this;
		  } 

		  @Override
		  public void addCellEditorListener(CellEditorListener arg0) {      
		  } 

		  @Override
		  public void cancelCellEditing() {
		  } 

		  @Override
		  public Object getCellEditorValue() {
		    return "";
		  }

		  @Override
		  public boolean isCellEditable(EventObject arg0) {
		    return true;
		  }

		  @Override
		  public void removeCellEditorListener(CellEditorListener arg0) {
		  }

		  @Override
		  public boolean shouldSelectCell(EventObject arg0) {
		    return true;
		  }

		  @Override
		  public boolean stopCellEditing() {
		    return true;
		  }
		}
}
