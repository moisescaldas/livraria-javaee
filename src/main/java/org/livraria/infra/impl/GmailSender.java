package org.livraria.infra.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.model.Message;

import org.apache.commons.codec.binary.Base64;
import org.livraria.infra.GmailAPIClient;
import org.livraria.infra.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GmailSender implements MailSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(GmailSender.class);

	private GmailAPIClient apiClient;

	public GmailSender() {}
	
	@Inject
	public GmailSender(GmailAPIClient apiClient) {
		this.apiClient = apiClient;
	}
	
	@Override
	public void send(String from, String to, String subject, String body) {

		try {
			Message message = createMessageWithEmail(createEmail(from, to, subject, body));

			message = apiClient.getClient().users().messages().send("me", message).execute();
			LOGGER.info("Message id: " + message.getId());
			LOGGER.info(message.toPrettyString());
		} catch (MessagingException | IOException ex) {
			LOGGER.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	private static MimeMessage createEmail(String from, String to, String subject, String body)
			throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);
		email.setText(body);

		return email;
	}

	public static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}
}
