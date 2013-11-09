package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import Communication.Message;
import Communication.MessageFactory;
import Settings.Settings;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LogRegWindow {

	private JFrame frame;
	private JTextField idVal;
	private JPasswordField pwdVal;
	private JTextField nickVal;
	private JPasswordField pwd2Val;
	
	public static LogRegWindow instance = null;


	/**
	 * Create the application.
	 */
	public LogRegWindow() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		frame.getContentPane().add(horizontalBox_2);
		
		JPanel panel = new JPanel();
		horizontalBox_2.add(panel);
		
		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);
		
		JLabel lblMamKonto = new JLabel("Mam konto");
		verticalBox.add(lblMamKonto);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		JLabel lblNumer = new JLabel("Numer:");
		horizontalBox.add(lblNumer);
		
		idVal = new JTextField();
		horizontalBox.add(idVal);
		idVal.setColumns(10);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		
		JLabel pwdLabel = new JLabel("Has\u0142o:");
		horizontalBox_1.add(pwdLabel);
		
		pwdVal = new JPasswordField();
		horizontalBox_1.add(pwdVal);
		
		JButton loginBtn = new JButton("Zaloguj");
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String pwd = new String(pwdVal.getPassword());
				try {
					long id = Long.parseLong(idVal.getText());
					Settings.settings = new Settings(id, pwd);
					MessageFactory.factory.addOutMsg(new Message(id, pwd, Message.Mode.LOGIN));
				} catch(NumberFormatException e) {
					showDialog("B³êdny numer!");
				}
			}
		});
		verticalBox.add(loginBtn);
		
		JPanel panel_1 = new JPanel();
		horizontalBox_2.add(panel_1);
		
		Box verticalBox_1 = Box.createVerticalBox();
		panel_1.add(verticalBox_1);
		
		JLabel lblChcMieKonto = new JLabel("Chc\u0119 mie\u0107 konto");
		verticalBox_1.add(lblChcMieKonto);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_3);
		
		JLabel lblNick = new JLabel("Nick:");
		horizontalBox_3.add(lblNick);
		
		nickVal = new JTextField();
		nickVal.setColumns(10);
		horizontalBox_3.add(nickVal);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_4);
		
		JLabel label_2 = new JLabel("Has\u0142o:");
		horizontalBox_4.add(label_2);
		
		pwd2Val = new JPasswordField();
		horizontalBox_4.add(pwd2Val);
		
		JButton registerBtn = new JButton("Rejestruj");
		registerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String pwd = new String(pwd2Val.getPassword());
				//Settings.settings = new Settings(pwd);
				MessageFactory.factory.addOutMsg(new Message(nickVal.getText(), pwd));
			}
		});
		verticalBox_1.add(registerBtn);
	}
	public void showDialog(String text) {
		JOptionPane.showMessageDialog(null, text);
	}
	public void hide() {
		frame.setVisible(false);
	}
	public void close() {
		LogRegWindow.instance = null;
		this.frame.dispose();
	}
}
