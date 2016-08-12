package com.cofrinhos.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.cofrinhos.model.Cofre;
import com.cofrinhos.model.PeriodoDoResponsavel;
import com.cofrinhos.model.Responsavel;
import com.cofrinhos.negocio.CofreRN;
import com.cofrinhos.negocio.ResponsavelRN;
import com.cofrinhos.util.FacesUtil;
import com.cofrinhos.util.RNException;
import com.cofrinhos.util.transactionHibernate.Transactional;

@Named
@ViewScoped
public class CadastroCofreBean implements Serializable{
	
	private static final long serialVersionUID = -8721318828946194353L;

	@Inject
	private CofreRN cofreRN;
	
	private String responsavelAtual;
	private String estabelecimentoAtual;

	private Cofre cofre;
	
	private List<PeriodoDoResponsavel> filteredPeriodosDosResponsaveis;
	
	@Inject
	private PeriodoDoResponsavel periodoDoResponsavelSelecionado;
	
	private List<Responsavel> responsaveis;
	
	@Inject
	private ResponsavelRN responsavelRN;
	
	@PostConstruct
	public void init() {
		limpar();
	}

	private void limpar(){
		cofre = new Cofre();
		carregarLista();
	}
	
	@Transactional
	public String salvar() {
		try {
			cofreRN.saveOrUpdate(cofre);
			if(cofre.getId() == 0)
				FacesUtil.addInfoMessage("Cofre salvo com sucesso!");
			else
				return "ListaCofres";
			limpar();
		} catch (RNException e) {
			FacesUtil.addErrorMessage(e);
			System.out.println("Erro ao salvar na classe CadastroCofreBean");
		}
		return null;
	}
	
	public void excluirPeriodo() {
		periodoDoResponsavelSelecionado.setCofre(null);
		cofre.getPeriodosDoResponsavel().remove(periodoDoResponsavelSelecionado);
		periodoDoResponsavelSelecionado = new PeriodoDoResponsavel();
		atualizarResponsavel_EstabelecimentoAtual();
	}
	
	public void atualizarResponsavel_EstabelecimentoAtual() {
		Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
		
        Date dateNow = cal.getTime();
        
        // caso a lista seja vazia ou nula
        responsavelAtual = "";
		estabelecimentoAtual = "";
        
		if(cofre.getPeriodosDoResponsavel() != null) {
			if(!cofre.getPeriodosDoResponsavel().isEmpty()) {
				for (PeriodoDoResponsavel periodo : cofre.getPeriodosDoResponsavel()) {
					if(periodo.getTermino() == null) {
						if(periodo.getInicio().before(dateNow) || periodo.getInicio().equals(dateNow)) {
							//quando há um item na lista e este não tem data de término, ou seja é infinito o período
							responsavelAtual = periodo.getResponsavel().getNome();
							estabelecimentoAtual = periodo.getEstabelecimento();
							break;
						}
					} else if((periodo.getInicio().before(dateNow) || periodo.getInicio().equals(dateNow))
							&& (periodo.getTermino().after(dateNow) || periodo.getTermino().equals(dateNow))) {
						//quando a data do dia está entre ou igual a data de início e término
						responsavelAtual = periodo.getResponsavel().getNome();
						estabelecimentoAtual = periodo.getEstabelecimento();
						break;
					}
				}
				Collections.sort(cofre.getPeriodosDoResponsavel(), new Comparator<PeriodoDoResponsavel>() { 
					public int compare(PeriodoDoResponsavel obj1, PeriodoDoResponsavel obj2) {
						if (obj1.getInicio() == null || obj2.getInicio() == null)
				            return 0;     
						return obj2.getInicio().compareTo(obj1.getInicio());
					}
				});
			}
		}
	}
	
	// ---------------------------------- Métodos do Dialogo --------------------------------------------
	public void carregarLista() {
		responsaveis = (List<Responsavel>) responsavelRN.findAll();
	}
	
	public void abrirDialogo() {
		int index = cofre.getPeriodosDoResponsavel().indexOf(periodoDoResponsavelSelecionado);
		if (index != -1)
			cofre.getPeriodosDoResponsavel().remove(index);
	}
	
	public void ajustaData() {
		if(periodoDoResponsavelSelecionado.getInicio() != null && periodoDoResponsavelSelecionado.getTermino() == null) {
			periodoDoResponsavelSelecionado.setTermino(periodoDoResponsavelSelecionado.getInicio());
		} else if(periodoDoResponsavelSelecionado.getTermino() != null) {
			if(periodoDoResponsavelSelecionado.getTermino().before(periodoDoResponsavelSelecionado.getInicio())) {
				FacesUtil.addErrorMessage("Data inválida. Data de término é menor que a data de início.");
				periodoDoResponsavelSelecionado.setTermino(null);
			}
		}
	}
	
	public void dlgSalvar() {
		boolean adicionar = true;
		if(periodoDoResponsavelSelecionado != null) { //verificar se em algum momento será null
			if(cofre.getPeriodosDoResponsavel() != null) {
				for (PeriodoDoResponsavel periodoDoResponsavel : cofre.getPeriodosDoResponsavel()) {
					if(periodoDoResponsavel.getInicio().equals(periodoDoResponsavelSelecionado.getInicio())) {
						FacesUtil.addErrorMessage("Não foi possível adicionar a lista. Período já existe!");
						adicionar = false;
						break;
					}
					if(periodoDoResponsavelSelecionado.getTermino() != (null)) {
						if(periodoDoResponsavelSelecionado.getTermino().equals(periodoDoResponsavel.getInicio())) {
							FacesUtil.addErrorMessage("Não foi possível adicionar a lista. Período já existe!");
							adicionar = false;
							break;
						}
					}
					if(periodoDoResponsavel.getTermino() != null) {
						if(periodoDoResponsavel.getTermino().equals(periodoDoResponsavelSelecionado.getInicio())) {
							FacesUtil.addErrorMessage("Não foi possível adicionar a lista. Período já existe!");
							adicionar = false;
							break;
						}
					}
					if(periodoDoResponsavel.getTermino() != null && periodoDoResponsavelSelecionado.getTermino() != null) {
						if(periodoDoResponsavel.getTermino().equals(periodoDoResponsavelSelecionado.getTermino())) {
							FacesUtil.addErrorMessage("Não foi possível adicionar a lista. Período já existe!");
							adicionar = false;
							break;
						}
					} // até aqui verifca se é igual a data 
					
					//datas com término infinitos
					if (periodoDoResponsavel.getTermino() == null) {
						if(periodoDoResponsavel.getInicio().before(periodoDoResponsavelSelecionado.getInicio())) { 
							//data de início da lista é menor que a data de início cadastrada
							FacesUtil.addErrorMessage("Não foi possível adicionar a lista. Período já existe!");
							adicionar = false;
							break;
						}
					} else if(periodoDoResponsavel.getInicio().before(periodoDoResponsavelSelecionado.getInicio()) && 
							periodoDoResponsavel.getTermino().after(periodoDoResponsavelSelecionado.getInicio())) {
							//data da lista é maior que a data de início cadastrada
							//data início cadastrada está entre a data de início e término da lista
							FacesUtil.addErrorMessage("Nâo foi possível adicionar a lista. Período já existe!");
							adicionar = false;
							break;
					} else {
						if(periodoDoResponsavelSelecionado.getTermino() != null) {
							if(periodoDoResponsavel.getInicio().before(periodoDoResponsavelSelecionado.getTermino()) && 
									periodoDoResponsavel.getTermino().after(periodoDoResponsavelSelecionado.getTermino())) {
								//data termino cadastrada está entre a data de início e término da lista
								FacesUtil.addErrorMessage("Não foi possível adicionar a lista. Período já existe!");
								adicionar = false;
								break;
							}
						}
						if (periodoDoResponsavelSelecionado.getInicio().before(periodoDoResponsavel.getInicio()) && periodoDoResponsavelSelecionado.getTermino() == null){
							//data de início cadastrado é menor que a data de início da lista e também não existe data de término
							FacesUtil.addErrorMessage("Não foi possível adicionar a lista. Período já existe!");
							adicionar = false;
							break;
						}
					}
				}
			}
			if(adicionar == true) {
				cofre.adicionaPeriodoDoResponsavel(periodoDoResponsavelSelecionado);
				periodoDoResponsavelSelecionado = new PeriodoDoResponsavel(); 
				atualizarResponsavel_EstabelecimentoAtual();
				RequestContext.getCurrentInstance().execute("PF('dlg').hide()");
			}
		}
	}
	
	// ---------------------------------- Métodos do Cofre --------------------------------------------
	public Cofre getCofre() {
		return cofre;
	}

	public void setCofre(Cofre cofre) {
		this.cofre = cofre;
	}

	public List<PeriodoDoResponsavel> getFilteredPeriodosDosResponsaveis() {
		return filteredPeriodosDosResponsaveis;
	}

	public void setFilteredPeriodoDosResponsaveis(
			List<PeriodoDoResponsavel> filteredPeriodosDosResponsaveis) {
		this.filteredPeriodosDosResponsaveis = filteredPeriodosDosResponsaveis;
	}
	
	public String getResponsavelAtual() {
		return responsavelAtual;
	}

	public String getEstabelecimentoAtual() {
		return estabelecimentoAtual;
	}

	// ---------------------------------- Métodos do Dialogo --------------------------------------------
	public void setPeriodoDoResponsavelSelecionado(PeriodoDoResponsavel periodoDoResponsavelSelecionado) {
		this.periodoDoResponsavelSelecionado = periodoDoResponsavelSelecionado;
	}

	public PeriodoDoResponsavel getPeriodoDoResponsavelSelecionado() {
		return periodoDoResponsavelSelecionado;
	}
	
	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}
}