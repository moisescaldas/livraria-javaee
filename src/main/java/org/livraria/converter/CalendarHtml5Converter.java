package org.livraria.converter;

import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=Calendar.class)
public class CalendarHtml5Converter implements Converter{

	private  static DateTimeConverter dtc = new DateTimeConverter();
	
	static {
		dtc.setPattern("yyyy-MM-dd");
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Date date = (Date) dtc.getAsObject(context, component, value);
		
		if (date == null) {
			return null;
		}
		
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date);
		
		return newDate;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		
		return dtc.getAsString(context, component, ((Calendar) value).getTime());
	}

}
