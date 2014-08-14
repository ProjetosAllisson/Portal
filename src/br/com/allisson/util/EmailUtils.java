package br.com.allisson.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.allisson.facade.ContaEmailFacade;
import br.com.allisson.modelo.Mensagem;

public class EmailUtils {

	private static String HOSTNAME = "portal@allisson.com.br";
	private static String USERNAME = "portal@allisson.com.br";
	private static String PASSWORD = "291194po";
	private static String PORTA       = "587";
	private static String SMTP        = "smtp.allisson.com.br";

	
	public static void enviarEmail(Mensagem mensagem) {
		
		
		
		if (ContaEmailFacade.getDadosContaEmail() != null){
			HOSTNAME = ContaEmailFacade.getDadosContaEmail().getHostname();
			USERNAME = ContaEmailFacade.getDadosContaEmail().getUsername();
			PASSWORD = ContaEmailFacade.getDadosContaEmail().getPassword();
			PORTA    = ContaEmailFacade.getDadosContaEmail().getPorta();
			SMTP     = ContaEmailFacade.getDadosContaEmail().getSmtp();
		}
		
		
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", SMTP);
		props.put("mail.smtp.socketFactory.port", PORTA);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", PORTA);

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(USERNAME,
								PASSWORD);
					}
				});

		/** Ativa Debug para sessão */
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(HOSTNAME)); // Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(mensagem.getDestino());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(mensagem.getTitulo());// Assunto
			
			String msg = "";
			for (String msgs : mensagem.getMensagens()) {
				
				msg = msg + msgs+"\r\n";
					
			}
			
			message.setText(msg);
			
			
			/** Método para enviar a mensagem criada */
			Transport.send(message);

			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}