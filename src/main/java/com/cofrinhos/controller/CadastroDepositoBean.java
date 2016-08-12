package com.cofrinhos.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cofrinhos.model.Deposito;
import com.cofrinhos.negocio.DepositoRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;
import com.cofrinhos.util.transactionHibernate.Transactional;

@Named
@RequestScoped
public class CadastroDepositoBean implements Serializable {
	
	private static final long serialVersionUID = 3117230662533783180L;

	@Inject
	private Deposito deposito;

	@Inject
	private DepositoRN depositoRN;
	
	@Transactional
	public String salvar() {
		try {
			depositoRN.saveOrUpdate(deposito);
			if(deposito.getId() == 0)
				FacesUtil.addInfoMessage("Depósito salvo com sucesso!");
			else
				return "ListaDepositos";
			limpar();
		} catch (RNException e) {
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao salvar na classe CadastroDepositoBean");
		}
		return null;
	}
	
	public void limpar() {
		deposito = new Deposito();
	}
	
	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}
}
