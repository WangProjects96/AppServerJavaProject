package bibliotheque;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Alert implements Runnable {

	private static final String SMTP_HOST = "smtp.gmail.com"; // pour envoyer
																// depuis gmail
	private static final String LOGIN_SMTP = "appservbiblio.noreply";
	private static final String PASSWORD_SMTP = "aawang78";
	private static final String PORT = "587";
	private static final String FROM = "appservbiblio.noreply@gmail.com";

	private Livre livre;
	private Abonne abo;

	public Alert(Livre livre, Abonne abo) {
		this.livre = livre;
		this.abo = abo;
	}

	@Override
	public void run() {
		try {
			synchronized (livre) { //mets un verou, et en gros si ca coupe dans livre, le code, le processus peut rien faire
				while (!livre.estDisponible()) {
					livre.wait(); // le wait, on est obligé d'etre verouiller, obligé de mettre dans un while
				}
				envoyerMail(abo.mail());
				abo.supprAlerte(livre);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void envoyerMail(String destinataire) {
		String contenuMes = "Cher abonné n°" + abo.numero() + ", \n\nLe livre n°" + livre.numero()
				+ " est disponible. Dépêchez vous de le réserver !";
		String sujetMes = "Votre livre est disponible !";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.host", LOGIN_SMTP);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", PORT);

		Session session = Session.getDefaultInstance(props);

		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(FROM));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));
			message.setSubject(sujetMes);
			message.setText(contenuMes);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		Transport tr;
		try {
			tr = session.getTransport("smtp");
			tr.connect(SMTP_HOST, LOGIN_SMTP, PASSWORD_SMTP);
			message.saveChanges();
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
			System.out.println("Message envoyé avec succès à : " + destinataire);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void lancer() {
		new Thread(this).start();
	}

}
