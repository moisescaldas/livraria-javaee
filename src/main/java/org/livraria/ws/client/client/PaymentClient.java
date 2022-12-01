package org.livraria.ws.client.client;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import org.livraria.ws.client.dto.PaymentRequestDTO;

public class PaymentClient {
	private static final String SERVICE_URI = "https://af3635dc-44dc-43bd-858c-2d387f32a4a4.mock.pstmn.io/payments";
	
	
	public PaymentClient() {}
	
	public void pay(BigDecimal total) {
		Client client = ClientBuilder.newClient();
		Entity<PaymentRequestDTO> json = Entity.json(new PaymentRequestDTO(total));
		System.out.println(client.target(SERVICE_URI).request().post(json).getStatus());
	}
	
}
