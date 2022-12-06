package org.livraria.ws.resource;

import java.math.BigDecimal;
import java.net.URI;

import javax.inject.Inject;
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
import org.livraria.infra.impl.GmailSender;
import org.livraria.ws.client.client.PaymentClient;

@Path("payment")
public class PaymentResource {

	private CheckoutDAO checkoutDAO;
	private PaymentClient paymentClient;

	private GmailSender sender;

	@Context
	private HttpServletRequest req;

	public PaymentResource() {
	}

	@Inject
	public PaymentResource(CheckoutDAO checkoutDAO, PaymentClient paymentClient, GmailSender sender) {
		this.checkoutDAO = checkoutDAO;
		this.paymentClient = paymentClient;
		this.sender = sender;
	}

	@POST
	public void pay(@Suspended AsyncResponse ar, @QueryParam("uuid") final String uuid) {
		Checkout checkout = checkoutDAO.findByUUID(uuid);

		if (checkout != null) {
			URI uri = UriBuilder.fromPath("/index.xhtml")
					.queryParam("msg", "Compra realizada com sucesso!").build();
			BigDecimal total = checkout.getValue();

			try {
				paymentClient.pay(total);
				ar.resume(Response.seeOther(uri).build());
				String mailBody = String.format("Nova compra. Seu código de acompanhamento é %s.", checkout.getUuid());
				sender.send("sesiom.br@gmail.com", checkout.getBuyer().getEmail(), "Nova Compra de Livro", mailBody);
			} catch (RuntimeException ex) {
				ar.resume(ex);
			}
		} else {
			ar.resume(new RuntimeException("Compra não encontrada"));
		}
	}

}
