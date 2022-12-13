package org.livraria.listeners.checkout;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;
import org.livraria.domain.dao.CheckoutDAO;
import org.livraria.domain.entity.Checkout;
import org.livraria.ws.client.client.InvoiceClient;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topics/checkoutsTopic") })
public class GenerateInvoiceListener implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(GenerateInvoiceListener.class);
	
	@Inject
	private InvoiceClient invoiceClient;
	
	@Inject
	private CheckoutDAO dao;
	
	@Override
	public void onMessage(Message message) {
		TextMessage text = (TextMessage) message;
		
		try {
			Checkout checkout = dao.findByUUID(text.getText());
			invoiceClient.invoiceFor(checkout);
		} catch(JMSException ex) {
			LOGGER.error("Falha na geração da nota fiscal: {}", ex);
		}
	}

}
