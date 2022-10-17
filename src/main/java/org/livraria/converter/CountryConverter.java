package org.livraria.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.livraria.domain.entity.Country;

@FacesConverter(value = "countryConverter", forClass = Country.class)
public class CountryConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			Country country = (Country) deserializarStringParaObjeto(value);
			
			return country;
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && (value instanceof Country)) {
			return serializarObjetoEmString((Country) value);
		}
		
		return null;
	}
	
	public String serializarObjetoEmString(Country obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return Base64.getEncoder().encodeToString(baos.toByteArray());
			
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public Object deserializarStringParaObjeto(String value) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(value));
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
			
		} catch(IOException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
}
