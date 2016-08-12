package com.cofrinhos.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.sun.faces.util.MessageFactory;

public class FacesUtil {
	
	public static void addErrorMessage(RNException message) {
		apresentarMensagem(FacesMessage.SEVERITY_ERROR, message.getMessage());
	}
	
	public static void addErrorMessage(String message) {
		apresentarMensagem(FacesMessage.SEVERITY_ERROR, message);
	}
	
	public static void addErrorMessageWithLabel(String message, FacesContext context, UIComponent component) {
		Object label = MessageFactory.getLabel(context, component);
		apresentarMensagem(FacesMessage.SEVERITY_ERROR, label + ": " + message); 
	}
	
	public static void addInfoMessage(RNException message) {
		apresentarMensagem(FacesMessage.SEVERITY_INFO, message.getMessage());
	}
	
	public static void addInfoMessage(String message) {
		apresentarMensagem(FacesMessage.SEVERITY_INFO, message);
	}
	
	public static void addInforMessageWithLabel(String message, FacesContext context, UIComponent component) {
		Object label = MessageFactory.getLabel(context, component);
		apresentarMensagem(FacesMessage.SEVERITY_INFO, label + ": " + message); 
	}
	
	private static void apresentarMensagem(Severity severity, String message) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(severity, message, message));
	}
	
	
}