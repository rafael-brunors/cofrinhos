package com.cofrinhos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cofrinhos.model.Deposito;
import com.cofrinhos.negocio.DepositoRN;
import com.cofrinhos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Deposito.class)
public class DepositoConverter implements Converter {

	private DepositoRN depositoRN;
	
	public DepositoConverter() {
		depositoRN = CDIServiceLocator.getBean(DepositoRN.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Deposito retorno = null;
		
		if (value != null) {
			int id = new Integer(value);
			try {
				retorno = depositoRN.findById(new Deposito(id));
			} catch (Exception e) {
				System.out.println("Ocorreu erro na classe DepositoConverter ao pesquisar o depósito no BD!");
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Deposito deposito = (Deposito) value;
			return deposito.getId() == 0 ? null : Integer.toString(deposito.getId());
		}
		
		return "";
	}

}
