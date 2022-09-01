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
	}
	
	public void addFlashMessage(String message) {
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage(null, new FacesMessage(message));
	}
}
