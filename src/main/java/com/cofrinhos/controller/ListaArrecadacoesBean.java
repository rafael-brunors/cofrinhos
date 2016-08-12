package com.cofrinhos.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cofrinhos.model.Arrecadacao;
import com.cofrinhos.negocio.ArrecadacaoRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;

@Named
@ViewScoped
public class ListaArrecadacoesBean implements Serializable{

	private static final long serialVersionUID = 1642338366237000569L;

	private List<Arrecadacao> arrecadacoes;
	
	private List<Arrecadacao> filteredArrecadacoes;
	
	@Inject
	private ArrecadacaoRN arrecadacaoRN;
	
	private Arrecadacao arrecadacaoSelecionada;
	
	@PostConstruct
	public void init() {
		arrecadacoes = arrecadacaoRN.findAll();
	}
	
	public void excluir() {
		try {
			arrecadacaoRN.delete(arrecadacaoSelecionada);
			init();
		} catch (RNException e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao excluir na classe ListaArrecadacaoBean");
		}
	}

	public List<Arrecadacao> getFilteredArrecadacoes() {
		return filteredArrecadacoes;
	}

	public void setFilteredArrecadacoes(List<Arrecadacao> filteredArrecadacoes) {
		this.filteredArrecadacoes = filteredArrecadacoes;
	}

	public List<Arrecadacao> getArrecadacoes() {
		return arrecadacoes;
	}

	public void setArrecadacaoSelecionada(Arrecadacao arrecadacaoSelecionada) {
		this.arrecadacaoSelecionada = arrecadacaoSelecionada;
	}
}
