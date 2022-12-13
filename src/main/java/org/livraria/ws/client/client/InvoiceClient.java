package org.livraria.ws.client.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import org.jboss.logging.Logger;
import org.livraria.domain.entity.Checkout;
import org.livraria.domain.entity.InvoiceData;

public class InvoiceClient {
	private static final Logger LOGGER = Logger.getLogger(InvoiceClient.class);

	private static final String ENDPOINT = "https://8ac8cfea-f6ab-4446-b5b5-bd39c1c3fa63.mock.pstmn.io/invoice";

	public void invoiceFor(Checkout checkout) {
		Client client = ClientBuilder.newClient();
		InvoiceData data = new InvoiceData(checkout);
		Entity<InvoiceData> json = Entity.json(data);
		LOGGER.info("Nota Fiscal Criada: " + client.target(ENDPOINT).request().post(json, String.class));
	}
}
