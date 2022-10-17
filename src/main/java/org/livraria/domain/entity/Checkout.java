package org.livraria.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Checkouts")
public class Checkout {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal value;
	
	private String jsonCart;
	
	@ManyToOne
	@JoinColumn(name = "ID_SystemUser")
	private SystemUser buyer;	
	
	/*
	 * Getters and Setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getJsonCart() {
		return jsonCart;
	}

	public void setJsonCart(String jsonCart) {
		this.jsonCart = jsonCart;
	}

	public SystemUser getBuyer() {
		return buyer;
	}
	
	public void setBuyer(SystemUser buyer) {
		this.buyer = buyer;
	}
	
}
