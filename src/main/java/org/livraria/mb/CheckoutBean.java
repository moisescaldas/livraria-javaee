package org.livraria.mb;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.livraria.domain.beans.ShoppingCart;
import org.livraria.domain.dao.CheckoutDAO;
import org.livraria.domain.dao.CountryDAO;
import org.livraria.domain.dao.SystemUserDAO;
import org.livraria.domain.entity.Address;
import org.livraria.domain.entity.Checkout;
import org.livraria.domain.entity.Country;
import org.livraria.domain.entity.SystemUser;
import org.livraria.util.helper.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model
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
	private CheckoutDAO checkoutDAO;

	private FacesContext facesContext;
	
	private ShoppingCart shoppingCart;
	private MessageHelper messageHelper;
	
	public CheckoutBean() {}
	
	@Inject
	public CheckoutBean(CountryDAO countryDAO, SystemUserDAO systemUserDAO, MessageHelper messageHelper,
			ShoppingCart shoppingCart, CheckoutDAO checkoutDAO, FacesContext facesContext) {
		this.countryDAO = countryDAO;
		this.systemUserDAO = systemUserDAO;
		this.checkoutDAO = checkoutDAO;
		this.shoppingCart = shoppingCart;
		this.messageHelper = messageHelper;
		this.facesContext = facesContext;
		
		this.systemUser = new SystemUser();
		this.address = new Address();
		address.setSystemUser(systemUser);
		systemUser.getAddresses().add(address);
	}

	@Transactional
	public void checkout() {

		if (systemUserDAO.hasEmail(systemUser.getEmail())) {
			messageHelper.addMessageErro(CLIENT_MESSAGE_ID_EMAIL, CLIENT_MESSAGE_EMAIL);

		} else if (selectedCountryId == 0) {
			messageHelper.addMessageErro(CLIENT_MESSAGE_ID_COUNTRY, CLIENT_MESSAGE_COUNTRY);

		} else if (systemUserDAO.hasSocialId(systemUser.getSocialId())) {
			messageHelper.addMessageErro(CLIENT_MESSAGE_ID_SOCIAL_ID, CLIENT_MESSAGE_SOCIAL_ID);

		} else {
			address.setCountry(countryDAO.findCountryById(selectedCountryId));
			systemUserDAO.save(systemUser);
			Checkout checkout = new Checkout(systemUser, shoppingCart);
			checkoutDAO.save(checkout);
						
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextName = externalContext.getContextName();
			
			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			response.setStatus(307);
			response.setHeader("Location", "/" + contextName + "/services/payment?uuid=" + checkout.getUuid());
			LOGGER.info("Compra " + checkout + " gravada! Redirecionado o fluxo para o serviço de pagamentos!");
		}
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