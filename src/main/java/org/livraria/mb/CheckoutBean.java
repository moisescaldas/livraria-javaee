package org.livraria.mb;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;

import org.livraria.domain.beans.ShoppingCart;
import org.livraria.domain.dao.CountryDAO;
import org.livraria.domain.dao.SystemUserDAO;
import org.livraria.domain.entity.Address;
import org.livraria.domain.entity.Country;
import org.livraria.domain.entity.SystemUser;
import org.livraria.util.helper.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.livraria.util.Constantes.URL_CHECKOUT;
import static org.livraria.util.Constantes.URL_REDIRECT_INDEX;;


@Model
@Path("payment")
public class CheckoutBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutBean.class);

	private static final String CLIENT_MESSAGE_ID_EMAIL = "checkoutUserForm:mail";
	private static final String CLIENT_MESSAGE_ID_SOCIAL_ID = "checkoutUserForm:socialId";
	private static final String CLIENT_MESSAGE_ID_COUNTRY = "checkoutUserForm:countryPick";

	private static final String CLIENT_MESSAGE_EMAIL = "E-MAIL já cadastrado";
	private static final String CLIENT_MESSAGE_SOCIAL_ID = "CPF já cadastrado";
	private static final String CLIENT_MESSAGE_COUNTRY = "Deve ser selecionado seu país de origem";
	
	private SystemUser systemUser;
	private Address address;
	private Integer selectedCountryId;

	private CountryDAO countryDAO;
	private SystemUserDAO systemUserDAO;

	private ShoppingCart shoppingCart;
	private MessageHelper messageHelper;
	
	@Inject
	public CheckoutBean(CountryDAO countryDAO, SystemUserDAO systemUserDAO, MessageHelper messageHelper, ShoppingCart shoppingCart) {
		this.countryDAO = countryDAO;
		this.systemUserDAO = systemUserDAO;
		this.shoppingCart = shoppingCart;
		this.messageHelper = messageHelper;

		this.systemUser = new SystemUser();
		this.address = new Address();
		address.setSystemUser(systemUser);
		systemUser.getAddresses().add(address);
	}

	@Transactional
	public String checkout() {

		if (systemUserDAO.hasEmail(systemUser.getEmail())) {
			messageHelper.addMessageErro(CLIENT_MESSAGE_ID_EMAIL, CLIENT_MESSAGE_EMAIL);

		} else if (selectedCountryId == 0) {
			messageHelper.addMessageErro(CLIENT_MESSAGE_ID_COUNTRY, CLIENT_MESSAGE_COUNTRY);

		} else if (systemUserDAO.hasSocialId(systemUser.getSocialId())) {
			messageHelper.addMessageErro(CLIENT_MESSAGE_ID_SOCIAL_ID, CLIENT_MESSAGE_SOCIAL_ID);

		} else {
			address.setCountry(countryDAO.findCountryById(selectedCountryId));
			systemUserDAO.save(systemUser);
			LOGGER.info("Nova compra realizada pelo cliente {}", systemUser);
			return URL_REDIRECT_INDEX;
		}

		return URL_CHECKOUT;
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