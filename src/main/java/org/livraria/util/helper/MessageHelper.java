package org.livraria.util.helper;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class MessageHelper {

	private FacesContext facesContext;
	
	public MessageHelper() {}
	
	@Inject
	public MessageHelper(FacesContext facesContext) {
		this.facesContext = facesContext;
		this.facesContext.getExternalContext().getFlash().setKeepMessages(true);
	}
	
	public void addFlashMessage(String message) {
		facesContext.addMessage(null, new FacesMessage(message));
	}
	
	public void addMessageErro(String clientId, String message) {
		facesContext.addMessage(clientId, new FacesMessage(message));
	}
}
