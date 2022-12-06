package org.livraria.ws.resource;

import java.math.BigDecimal;
import java.net.URI;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
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
import org.livraria.infra.MailSender;
import org.livraria.ws.client.client.PaymentClient;

@Path("payment")
public class PaymentResource {

	private CheckoutDAO checkoutDAO;
	private PaymentClient paymentClient;

	private MailSender sender;
	
	@Context
	private HttpServletRequest req;

	@Resource(name = "java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService managedExecutorService;

	public PaymentResource() {
	}

	@Inject
	public PaymentResource(CheckoutDAO checkoutDAO, PaymentClient paymentClient, MailSender sender) {
		this.checkoutDAO = checkoutDAO;
		this.paymentClient = paymentClient;
		this.sender = sender;
	}

	@POST
	public void pay(@Suspended AsyncResponse ar, @QueryParam("uuid") final String uuid) {
		Checkout checkout = checkoutDAO.findByUUID(uuid);

		if (checkout != null) {
			managedExecutorService.submit(() -> {
				URI uri = UriBuilder.fromPath(req.getContextPath() + "/site/index.xhtml")
						.queryParam("msg", "Compra realizada com sucesso!").build();
				BigDecimal total = checkout.getValue();

				try {
					paymentClient.pay(total);
					ar.resume(Response.seeOther(uri).build());
					String mailBody = String.format("Nova compra. Seu código de acompanhamento é %s.", checkout.getUuid());
					
				} catch (RuntimeException ex) {
					ar.resume(ex);
				}
			});
		} else {
			ar.resume(new RuntimeException("Compra não encontrada"));
		}
	}

}
