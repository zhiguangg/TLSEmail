package smtpMail;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * @param folderName pass as 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(String folderName, Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("myalert22017@gmail.com", "Motion Alert"));

	      msg.setReplyTo(InternetAddress.parse("myalert22017@gmail.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      // Create the message part
          BodyPart messageBodyPart = new MimeBodyPart();

          // Now set the actual message
          messageBodyPart.setText("Check the video attached!");

          // Create a multipart message
          Multipart multipart = new MimeMultipart();

          // Set text message part
          multipart.addBodyPart(messageBodyPart);

          // Part two is attachment
          messageBodyPart = new MimeBodyPart();
          
          String filename = getLatestAlert(folderName);
          DataSource source = new FileDataSource(filename);
          messageBodyPart.setDataHandler(new DataHandler(source));
          messageBodyPart.setFileName(filename);
          multipart.addBodyPart(messageBodyPart);

          // Send the complete message parts
          msg.setContent(multipart);
	         
	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	private static String getLatestAlert(String folderName) {
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		long lastModified = 0L;
		String absPath = "";
		
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        if (listOfFiles[i].lastModified() > lastModified && listOfFiles[i].getName().contains(".mp4")) {
	        	lastModified = listOfFiles[i].lastModified();
	        	absPath = listOfFiles[i].getAbsolutePath();
	        }
	      } else if (listOfFiles[i].isDirectory()) { }
	    }
	    return absPath;
	}
}