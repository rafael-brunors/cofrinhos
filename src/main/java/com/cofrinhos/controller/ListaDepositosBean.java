package com.cofrinhos.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cofrinhos.model.Deposito;
import com.cofrinhos.negocio.DepositoRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;
import com.cofrinhos.util.transactionHibernate.Transactional;

@Named
@ViewScoped
public class ListaDepositosBean implements Serializable{
	
	private static final long serialVersionUID = -891873346550877320L;

	private List<Deposito> depositos;
	
	private List<Deposito> filteredDepositos;
	
	@Inject
	private DepositoRN depositoRN;
	
	@Inject
	private Deposito depositoSelecionado;
	
	@PostConstruct
	public void init() {
		depositos = depositoRN.findAll();
	}
	
	@Transactional
	public void excluir() {
		try {
			depositoRN.delete(depositoSelecionado);
			init();
		} catch (RNException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao excluir na classe ListaDepositosBean");
		}
	}

	public List<Deposito> getDepositos() {
		return depositos;
	}

	public List<Deposito> getFilteredDepositos() {
		return filteredDepositos;
	}

	public void setFilteredDepositos(List<Deposito> filteredDepositos) {
		this.filteredDepositos = filteredDepositos;
	}

	public void setDepositoSelecionado(Deposito depositoSelecionado) {
		this.depositoSelecionado = depositoSelecionado;
	}
}
