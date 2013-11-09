import java.awt.EventQueue;

import Communication.Message;
import Communication.MessageFactory;
import Connection.*;
import View.*;
import Settings.*;

import java.io.*;

import javax.swing.JDialog;

import Exceptions.*;

public class MainProgram {
	
	public MainProgram() throws IOException {
		boolean register = false;
		try {
			Settings.settings = new Settings();
		} catch(NotLoggedException ex) {
			register = true;
			LogRegWindow.instance = new LogRegWindow();
		}
		if(!register) {
			MessageFactory.factory.addOutMsg(new Message(Settings.settings.getID(), Settings.settings.getPWD(), Message.Mode.LOGIN));
			MainWindow.instance = new MainWindow();
		}
		new ClientConnection(Settings.ip, Settings.port).start();
	}
	public static void main(String[] args) throws IOException {
		new MainProgram();
	}

}
