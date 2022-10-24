package org.livraria.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.livraria.domain.beans.ShoppingCart;

@Entity
@Table(name = "Checkouts")
public class Checkout {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "VALUE")
	private BigDecimal value;
	@Column(name = "JSON_CART")
	private String jsonCart;
	@Column(name = "CHECKOUT_UUID")
	private String uuid;
	@ManyToOne
	@JoinColumn(name = "ID_SystemUser")
	private SystemUser buyer;	
	
	protected Checkout() {}
	
	public Checkout(SystemUser buyer, ShoppingCart cart) {
		this.buyer = buyer;
		this.jsonCart = cart.toJsonString();
		this.value = cart.getTotal();
	}
	
	@PrePersist
	public void prePersist() {
		this.uuid = UUID.randomUUID().toString();
	}
	
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	

}
