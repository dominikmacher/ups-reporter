import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UpsReporter {


	public static void main(String[] args) throws IOException {
		
		Properties prop = new Properties();
		InputStream inputStream = UpsReporter.class.getClassLoader().getResourceAsStream("config.properties");

        if (inputStream == null) {
            System.out.println("ERROR: cannot find properties file");
            return;
        }
        prop.load(inputStream);
				
		final String senderEmailId = prop.getProperty("senderEmailId");
		final String senderPassword = prop.getProperty("senderPassword");
		final String smtpServer = prop.getProperty("smtpServer");
		
				
		/*String command = "ping www.codejava.net";

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}*/

		String emailText = "check check check";
		_sendEmail(smtpServer, senderEmailId, senderPassword, "webmaster@feuerwehr-karlstetten.org", "USV Info", emailText);
	}

	
	private static void _sendEmail(String smtpServer, final String senderEmailId, final String senderPassword, String to, String subject, String text) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpServer);
		// props.put("mail.pop3s.port", "995");
		// props.put("mail.pop3s.starttls.enable", "true");

		try {
			Session emailSession = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(senderEmailId, senderPassword);
						}
					});
			Message message = new MimeMessage(emailSession);
			message.setFrom(new InternetAddress(senderEmailId));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
			System.out.println("Email send successfully.");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error in sending email.");
		}
	}

}
