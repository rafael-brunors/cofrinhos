package com.cofrinhos.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cofrinhos.model.Responsavel;
import com.cofrinhos.negocio.ResponsavelRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;
import com.cofrinhos.util.transactionHibernate.Transactional;

@Named
@ViewScoped
public class ListaResponsavelBean implements Serializable {

	private static final long serialVersionUID = 8424614837100036471L;

	@Inject
	private ResponsavelRN responsavelrn;

	private List<Responsavel> responsaveis;

	private Responsavel responsavel;

	private List<Responsavel> filteredResponsaveis;
	
	@PostConstruct
	public void init() {
		carregarLista();
	}

	public void carregarLista() {
		responsaveis = responsavelrn.findAll();
	}

	@Transactional
	public void excluir() {
		try {
			responsavelrn.delete(responsavel);
			carregarLista();
		} catch (RNException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao excluir na classe ListaResponsavelBean");
		}
	}

	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public List<Responsavel> getFilteredResponsaveis() {
		return filteredResponsaveis;
	}

	public void setFilteredResponsaveis(List<Responsavel> filteredResponsaveis) {
		this.filteredResponsaveis = filteredResponsaveis;
	}
}