package org.livraria.mb;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.livraria.domain.dao.CountryDAO;
import org.livraria.domain.dao.SystemUserDAO;
import org.livraria.domain.entity.Address;
import org.livraria.domain.entity.Country;
import org.livraria.domain.entity.SystemUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model
public class CheckoutBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutBean.class);
	
	private SystemUser systemUser;
	private Address address;
	private Integer selectedCountryId;
	
	private CountryDAO countryDAO;
	private SystemUserDAO systemUserDAO;
	
	@Inject
	public CheckoutBean(CountryDAO countryDAO, SystemUserDAO systemUserDAO) {
		this.countryDAO = countryDAO;
		this.systemUserDAO = systemUserDAO;
		this.systemUser = new SystemUser();
		this.address = new Address();
		address.setSystemUser(systemUser);
		systemUser.getAddresses().add(address);
	}
	
	@Transactional
	public String checkout() {
		
		if (systemUserDAO.hasEmail(systemUser.getEmail())) {
			return "/site/checkout";
		}
		
		address.setCountry(countryDAO.findCountryById(selectedCountryId));
		systemUserDAO.save(systemUser);
		LOGGER.info("Nova compra realizada pelo cliente {}", systemUser);
		return "/index";
	}
	
	/*
	 * Getters and Seterrs
	 */
	public SystemUser getSystemUser() {
		return systemUser;
	}
	
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public List<Country> getCountries() {
		return countryDAO.list();
	}
	
	public CountryDAO getCountryDAO() {
		return countryDAO;
	}
	
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}
	
	public Integer getSelectedCountryId() {
		return selectedCountryId;
	}
	
	public void setSelectedCountryId(Integer selectedCountryId) {
		this.selectedCountryId = selectedCountryId;
	}
}