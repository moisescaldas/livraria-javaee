package org.livraria.infra;

public interface MailSender {
	
	public void send(String from, String to, String subject, String body);
	
}
