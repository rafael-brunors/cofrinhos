 package com.cofrinhos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cofrinhos.model.Cofre;
import com.cofrinhos.negocio.CofreRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;
import com.cofrinhos.util.transactionHibernate.Transactional;

@Named
@ViewScoped
public class ListaCofresBean implements Serializable {

	private static final long serialVersionUID = 2849782712159230720L;

	@Inject
	private CofreRN cofreRN;

	private List<Cofre> cofres = new ArrayList<Cofre>();
	
	private List<Cofre> filteredCofres;
	
	private Cofre cofre;	

	@PostConstruct
	@Transactional
	public void init() {
		carregarLista();
	}
	
	private void carregarLista(){		
		cofres = this.cofreRN.findAll();
	}
	
	public void excluir() {
		try {
			cofreRN.delete(cofre);
			carregarLista();
		} catch (RNException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao excluir na classe ListaCofresBean");
		}
	}

	public List<Cofre> getCofres() {
		return cofres;
	}
	
	public List<Cofre> getFilteredCofres() {
		return filteredCofres;
	}

	public void setfilteredCofres(List<Cofre> filteredCofres) {
		this.filteredCofres = filteredCofres;
	}

	public void setCofre(Cofre cofre) {
		this.cofre = cofre;
	}
	
}
