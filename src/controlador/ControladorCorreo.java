package controlador;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControladorCorreo implements Initializable{
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML public void enviar() throws Exception {
		sendMail("pape.cinthya@gmail.com");
	}
	
	public static void sendMail(String recipiente) throws Exception {
		System.out.println("Preparando...");
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.auth", "true"); 
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
			properties.setProperty("mail.smtp.port", "587");
			
			String myaccount = "pape.cinthya@gmail.com";
			String password = "cinthyapap";
	
		    Session session = Session.getInstance(properties, new Authenticator(){
		    	@Override
		    	protected PasswordAuthentication getPasswordAuthentication() {
		    		return new PasswordAuthentication(myaccount, password);
		    	}
		    });
		    
		    Message message = prepareMessage(session, myaccount, recipiente);
		    
		    Transport.send(message);
		    System.out.println("Enviado!");
			JOptionPane.showMessageDialog(null, "Enviado!");
	}

	private static Message prepareMessage(Session session, String myaccount, String recipiente) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myaccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipiente));
			message.setSubject("Prueba");
			message.setText("Prueba");
			return message;
		}
		catch (Exception e) {
			Logger.getLogger(ControladorCorreo.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}
}