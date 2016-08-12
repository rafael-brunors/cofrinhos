package com.cofrinhos.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.cofrinhos.model.PeriodoDoResponsavel;
import com.cofrinhos.model.Responsavel;
import com.cofrinhos.negocio.ResponsavelRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.transactionHibernate.Transactional;

@Named
@RequestScoped
public class CadastroPeriodoDoResponsavelBean implements Serializable {
	
	private static final long serialVersionUID = -9206428532005513980L;

	@Inject
	private ResponsavelRN responsavelRN;
	
	private PeriodoDoResponsavel periodoDoResponsavel;

	private List<Responsavel> responsaveis;
	
	@PostConstruct
	public void init(){
		carregarLista();
		
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if(id != null) {
			String inicio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("inicio");
			String termino = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("termino");
			String idResponsavel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idResponsavel");
			String estabelecimento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estabelecimento");
			
			periodoDoResponsavel = new PeriodoDoResponsavel();
			periodoDoResponsavel.setId(Integer.parseInt(id));
			try {
				periodoDoResponsavel.setInicio(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US).parse(inicio));
				periodoDoResponsavel.setTermino(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US).parse(termino));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			periodoDoResponsavel.setResponsavel(
					responsavelRN.findById(
							new Responsavel(Integer.parseInt(idResponsavel))));
			periodoDoResponsavel.setEstabelecimento(estabelecimento);
		}
		
		if(periodoDoResponsavel == null) {
			periodoDoResponsavel = new PeriodoDoResponsavel();
		}
	}
	
	public void carregarLista() {
		responsaveis = new ArrayList<>();
		responsaveis = (List<Responsavel>) responsavelRN.findAll();
	}
	
	@Transactional
	@RequestScoped
	public void abrirDialogo(PeriodoDoResponsavel periodo, PeriodoDoResponsavel p) {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("contentHeight", 470);
		
		Map<String, List<String>> params = new HashMap<>();
		if(periodo != null) {
			p = periodo;
			params.put("id", Arrays.asList(Integer.toString(periodo.getId())));
			params.put("inicio", Arrays.asList(periodo.getInicio().toString()));
			params.put("termino", Arrays.asList(periodo.getTermino().toString()));
			params.put("idResponsavel", Arrays.asList(Integer.toString(periodo.getResponsavel().getId())));
			params.put("estabelecimento", Arrays.asList(periodo.getEstabelecimento()));
		}
		
		RequestContext.getCurrentInstance().openDialog("/cofrinhododavi/dialogos/CadastroPeriodoResponsavel", opcoes, params);
		
	}
	
	public void ajustaData() {
		if(periodoDoResponsavel.getInicio() != null && periodoDoResponsavel.getTermino() == null) {
			periodoDoResponsavel.setTermino(periodoDoResponsavel.getInicio());
		} else if(periodoDoResponsavel.getTermino() != null) {
			if(periodoDoResponsavel.getTermino().before(periodoDoResponsavel.getInicio())) {
				FacesUtil.addErrorMessage("Data inválida. Data de término é menor que a data de início.");
				periodoDoResponsavel.setTermino(null);
			}
		}
	}
	
	public void salvar(){
		if(periodoDoResponsavel.getId() != 0) {
			RequestContext.getCurrentInstance().closeDialog(periodoDoResponsavel);
		} else {
			FacesUtil.addInfoMessage("Periodo do Responsável salvo com sucesso!");
			RequestContext.getCurrentInstance().closeDialog(periodoDoResponsavel);
			RequestContext.getCurrentInstance().execute("doHiddenClick()");
		}
	}
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public PeriodoDoResponsavel getPeriodoDoResponsavel() {
		return periodoDoResponsavel;
	}

	public void setPeriodoDoResponsavel(PeriodoDoResponsavel periodoDoResponsavel) {
		this.periodoDoResponsavel = periodoDoResponsavel;
	}

	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}
}
