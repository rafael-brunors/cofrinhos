package com.cofrinhos.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.cofrinhos.model.Responsavel;
import com.cofrinhos.negocio.ResponsavelRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;
import com.cofrinhos.util.transactionHibernate.Transactional;

@Named
@RequestScoped
public class CadastroResponsavelBean implements Serializable{
	
	private static final long serialVersionUID = -1585154144447839010L;

	@Inject
	private ResponsavelRN responsavelRN;

	@Inject
	private Responsavel responsavel;
	
	@PostConstruct
	public void init() {
		System.out.println("Entrou na método Init()");
		limpar();
	}
	
	private void limpar(){
		responsavel = new Responsavel();
	}
	
	@Transactional
	public void salvar(){
		try {
			boolean novo = true;
			if(responsavel.getId() != 0) {
				novo = false;
			}
			responsavelRN.saveOrUpdate(responsavel);
			if(novo == false)
				FacesContext.getCurrentInstance().getExternalContext().redirect("ListaResponsaveis.xhtml");
			limpar();
			FacesUtil.addInfoMessage("Responsável salvo com sucesso!");
		} catch (RNException e) {
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao salvar na classe CadastroResponsavelBean");
		} catch (IOException e) {
			FacesUtil.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}
}
