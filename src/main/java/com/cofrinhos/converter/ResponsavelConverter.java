package com.cofrinhos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cofrinhos.model.Responsavel;
import com.cofrinhos.negocio.ResponsavelRN;
import com.cofrinhos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Responsavel.class)
public class ResponsavelConverter implements Converter {
	
	private ResponsavelRN responsavelRN;
	
	public ResponsavelConverter() {
		responsavelRN = CDIServiceLocator.getBean(ResponsavelRN.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Responsavel retorno = null;
		
		if (value != null) {
			int id = new Integer(value);
			try {
				retorno = responsavelRN.findById(new Responsavel(id));
			} catch (Exception e) {
				System.out.println("Ocorreu erro na classe ResponsavelConverter ao pesquisar o responsavel no BD!");
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Responsavel responsavel = (Responsavel) value;
			return responsavel.getId() == 0 ? null : Integer.toString(responsavel.getId());
		}
		
		return "";
	}

}
