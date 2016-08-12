package com.cofrinhos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cofrinhos.model.Cofre;
import com.cofrinhos.negocio.CofreRN;
import com.cofrinhos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cofre.class)
public class CofreConverter implements Converter {
	
	private CofreRN cofreRN;
	
	public CofreConverter() {
		cofreRN = CDIServiceLocator.getBean(CofreRN.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cofre retorno = null;
		
		if (value != null) {
			int id = new Integer(value);
			try {
				retorno = cofreRN.findById(new Cofre(id));
			} catch (Exception e) {
				System.out.println("Ocorreu erro na classe CofreConverter ao pesquisar o cofre no BD!");
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Cofre cofre = (Cofre) value;
			return cofre.getId() == 0 ? null : Integer.toString(cofre.getId());
		}
		
		return "";
	}

}
