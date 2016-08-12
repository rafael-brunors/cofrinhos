package com.cofrinhos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cofrinhos.model.Arrecadacao;
import com.cofrinhos.negocio.ArrecadacaoRN;
import com.cofrinhos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Arrecadacao.class)
public class ArrecadacaoConverter implements Converter {
	
	private ArrecadacaoRN arrecadacaoRN;
	
	public ArrecadacaoConverter() {
		arrecadacaoRN = CDIServiceLocator.getBean(ArrecadacaoRN.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Arrecadacao retorno = null;
		
		if (value != null) {
			int id = new Integer(value);
			try {
				retorno = arrecadacaoRN.findById(new Arrecadacao(id));
			} catch (Exception e) {
				System.out.println("Ocorreu erro na classe ArrecadacaoConverter ao pesquisar a Arrecadação no BD!");
				e.printStackTrace();
			}
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Arrecadacao arrecadacao = (Arrecadacao) value;
			return arrecadacao.getId() == 0 ? null : Integer.toString(arrecadacao.getId());
		}
		
		return "";
	}

}
