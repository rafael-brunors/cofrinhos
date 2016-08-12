package com.cofrinhos.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cofrinhos.util.FacesUtil;

@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.trim().equals("") || value.trim().equals("R$ 0,00")) {
			FacesUtil.addErrorMessageWithLabel("Erro de Conversão: O valor não pode ser zero ou não é um número válido.", context, component); 
			return null;
		} else {
			value = value.replace("R$ ", "");
			value = value.replace(".", "");
			value = value.replace(",", ".");
			return new BigDecimal(value);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null || value.toString().trim().equals("")) {
			return "R$ 0,00";
		} else {
			NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
			return formatoMoeda.format(value);
		}
	}
}
