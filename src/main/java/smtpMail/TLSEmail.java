package smtpMail;


import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import smtpMail.EmailUtil;

public class TLSEmail {

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for TLS/STARTTLS: 587
	 */
	public static void main(String[] args) {
		final String fromEmail = "xxxx@gmail.com"; //requires valid gmail id
		final String password = "xxxxxxx"; // correct password for gmail id
		final String toEmail = "xxxxx@gmail.com"; // can be any email id 
		String folder = "C:/Users/inspiron/Documents/GW Security/video/NAJWX";
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		if(args.length > 1 && args[1] != null) {
			folder = args[1];
		}
		EmailUtil.sendEmail(folder, session, toEmail,"GW Camera Detected Motion!", "GW Security Camera Detected Motion");
		
	}

	
}