package org.livraria.wsclient.dto;

import java.math.BigDecimal;

public class PaymentRequestDTO {
	private BigDecimal value;
	
	public PaymentRequestDTO(BigDecimal value) {
		this.value = value;
	}
	
	/*
	 * Getters and Setters
	 */
	public BigDecimal getValue() {
		return value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
