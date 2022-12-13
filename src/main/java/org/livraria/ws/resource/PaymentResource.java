package org.livraria.ws.resource;

import java.math.BigDecimal;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.livraria.domain.dao.CheckoutDAO;
import org.livraria.domain.entity.Checkout;
import org.livraria.ws.client.client.PaymentClient;

@Path("payment")
public class PaymentResource {

	private static final ExecutorService executor = Executors.newFixedThreadPool(10);

	private CheckoutDAO checkoutDAO;
	private PaymentClient paymentClient;
	private JMSContext jmsContext;

	@Resource(lookup = "java:/jms/topics/checkoutsTopic")
	private Destination checkoutsTopic;
	
	@Context
	private HttpServletRequest req;

	
	public PaymentResource() {
	}

	@Inject
	public PaymentResource(CheckoutDAO checkoutDAO, PaymentClient paymentClient, JMSContext jmsContext) {
		this.checkoutDAO = checkoutDAO;
		this.paymentClient = paymentClient;
		this.jmsContext = jmsContext;
	}

	@POST
	public void pay(@Suspended AsyncResponse ar, @QueryParam("uuid") final String uuid) {
		Checkout checkout = checkoutDAO.findByUUID(uuid);
		JMSProducer producer = jmsContext.createProducer();
		
		if (checkout != null) {

			executor.submit(() -> {
				URI uri = UriBuilder.fromPath("/index.xhtml").queryParam("msg", "Compra realizada com sucesso!")
						.build();
				BigDecimal total = checkout.getValue();
				
				try {
					paymentClient.pay(total);
					producer.send(checkoutsTopic, checkout.getUuid());
					
					/*
					 * String mailBody =
					 * String.format("Nova compra. Seu código de acompanhamento é %s.",
					 * checkout.getUuid()); sender.send("sesiom.br@gmail.com",
					 * checkout.getBuyer().getEmail(), "Nova Compra de Livro", mailBody);
					 */					
					
					ar.resume(Response.seeOther(uri).build());
				} catch (RuntimeException ex) {
					ar.resume(ex);
				}
			});
		} else {
			ar.resume(new RuntimeException("Compra não encontrada"));
		}
	}
}
