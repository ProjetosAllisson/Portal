package br.com.allisson.bean.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.allisson.modelo.Cliente;

@FacesConverter(value="destinatarioConverter",forClass=Cliente.class)
public class DestinatarioConverter implements Converter{

private static List<Cliente> db;
	
	public static void setDB(List<Cliente> servicoDB){
		db = servicoDB;
	}
	
	public static List<Cliente> getDB(){
		return db;
	}

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		
		if(submittedValue.trim().equals("")){
			return null;
		}else {
			try{
				String str = submittedValue;
				
				for (Cliente cliente : db) {
					if(cliente.getCgc().equals(str)){
						return cliente;
					}
				}
				
			}catch(NumberFormatException exception){
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Conversion Error","Not a Valid"));
				
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent componente, Object value) {
		if (value == null || value.equals("")){
			return "";
		}else {
			try{
				return String.valueOf(((Cliente) value).getCgc());
			}catch (Exception e) {
				return "";
			}
		}
		
	}


}
