package org.livraria.listeners.checkout;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;
import org.livraria.domain.dao.CheckoutDAO;
import org.livraria.domain.entity.Checkout;
import org.livraria.infra.MailSender;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topics/checkoutsTopic") })
public class SendCheckoutrouterMailListener implements MessageListener{
	private static final Logger LOGGER = Logger.getLogger(SendCheckoutrouterMailListener.class);
	
	@Inject
	private MailSender sender;
	
	@Inject
	private CheckoutDAO dao;
	
	@Override
	public void onMessage(Message message) {
		TextMessage text = (TextMessage) message;
		try {
			Checkout checkout = dao.findByUUID(text.getText());
			String body = "<html><body>Compra realizada com sucesso. O código de acompanhamento é "
					+ checkout.getUuid() + "</body></html>";
			sender.send("compras@casadocodigo.com.br", checkout.getBuyer().getEmail(), "Nova Compra", body);
		} catch (Exception ex) {
			LOGGER.error("Erro no envio do email: {}", ex);
		}
	}

}
