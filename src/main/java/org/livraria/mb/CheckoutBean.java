package org.livraria.mb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;

import org.livraria.domain.beans.ShoppingCart;
import org.livraria.domain.dao.CountryDAO;
import org.livraria.domain.dao.SystemUserDAO;
import org.livraria.domain.entity.Address;
import org.livraria.domain.entity.Country;
import org.livraria.domain.entity.SystemUser;

@Model
public class CheckoutBean {
	private ShoppingCart shoppingCart;

	private SystemUser systemUser;
	private Address address;
	private List<Country> countries;
	
	@PersistenceContext
	private SystemUserDAO systemUserDao;
	@PersistenceContext
	private CountryDAO countryDao;
	
	public CheckoutBean() {}
	
	@Inject
	public CheckoutBean(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
		this.systemUser = new SystemUser();
		this.address = new Address();
		this.systemUser.getAddresses().add(address);
	}
	
	@PostConstruct
	public void loadObjects() {
		this.countries = countryDao.list();
	}
	
	public List<Country> getCountries() {
		return countries;
	}
	
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	
	public SystemUser getSystemUser() {
		return systemUser;
	}
	
	public Address getAddress() {
		return address;
	}
	
}
