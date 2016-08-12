package com.cofrinhos.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cofrinhos.model.Arrecadacao;
import com.cofrinhos.model.Cofre;
import com.cofrinhos.negocio.ArrecadacaoRN;
import com.cofrinhos.negocio.CofreRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;

@Named
@RequestScoped
public class CadastroArrecadacaoBean implements Serializable{
	
	private static final long serialVersionUID = -6298868641845840402L;

	@Inject
	private Arrecadacao arrecadacao;
	
	private List<Cofre> cofres;

	@Inject
	private CofreRN cofreRN;
	
	@Inject
	private ArrecadacaoRN arrecadacaoRN;
	
	@PostConstruct
	public void init() {
		cofres = cofreRN.findAll();
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
		
        Date dateNow = cal.getTime();
        if(arrecadacao == null)
        	arrecadacao = new Arrecadacao();
		arrecadacao.setDataRecolhimento(dateNow);
	}
	
	public void cofreSelecionado() {
		if(arrecadacao == null) {
			init();
		}
		if(arrecadacao.getCofre() == null || arrecadacao.getDataRecolhimento() == null) { //se nenhum cofre ou data foi selecionado 
			arrecadacao.setPeriodo(null);
		} else {
			if(arrecadacao.getCofre().getPeriodosDoResponsavel().size() == 0) {
				arrecadacao.setPeriodo(null);
			} else {
				arrecadacao.setPeriodo(null);
				for(int i = 0; i < arrecadacao.getCofre().getPeriodosDoResponsavel().size(); i++) {
					if(arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getInicio().equals(arrecadacao.getDataRecolhimento())) {
						arrecadacao.setPeriodo(arrecadacao.getCofre().getPeriodosDoResponsavel().get(i));
						break;
					} else if(arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getInicio().before(arrecadacao.getDataRecolhimento())
							&& arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getTermino() == null) {
						arrecadacao.setPeriodo(arrecadacao.getCofre().getPeriodosDoResponsavel().get(i));
						break;
					} else if(!(arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getTermino() == null)) {
				 		if((arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getInicio().before(arrecadacao.getDataRecolhimento())
							&& arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getTermino().after(arrecadacao.getDataRecolhimento()) )
						|| (arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getInicio().equals(arrecadacao.getDataRecolhimento()))
						|| (arrecadacao.getCofre().getPeriodosDoResponsavel().get(i).getTermino().equals(arrecadacao.getDataRecolhimento()))) {
				 			arrecadacao.setPeriodo(arrecadacao.getCofre().getPeriodosDoResponsavel().get(i));
				 			break;
				 		}
					}
				}
			}
		}
	}
	
	public String salvar() {
		try {
			cofreSelecionado();
			arrecadacaoRN.saveOrUpdate(arrecadacao);
			if(arrecadacao.getId() == 0) {
				arrecadacao = new Arrecadacao();
				FacesUtil.addInfoMessage("Cofre salvo com sucesso!");
			} else
				return "ListaArrecadacoes";
		} catch (RNException e) {
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao salvar na classe " + CadastroArrecadacaoBean.class);
		}
		return null;
	}
	
	public Arrecadacao getArrecadacao() {
		return arrecadacao;
	}

	public void setArrecadacao(Arrecadacao arrecadacao) {
		this.arrecadacao = arrecadacao;
	}

	public List<Cofre> getCofres() {
		return cofres;
	}

}
