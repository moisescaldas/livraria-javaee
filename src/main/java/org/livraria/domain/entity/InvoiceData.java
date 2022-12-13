package org.livraria.domain.entity;

import java.math.BigDecimal;

public class InvoiceData {
	private BigDecimal value;
	private String email;
	
	public InvoiceData(Checkout checkout) {
		this.value = checkout.getValue();
		this.email = checkout.getBuyer().getEmail();
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public String getEmail() {
		return email;
	}
}
