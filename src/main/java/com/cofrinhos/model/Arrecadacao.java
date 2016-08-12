package com.cofrinhos.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Arrecadacao {
	
	private int id;
	private Date dataRecolhimento;
	private BigDecimal valor;
	private PeriodoDoResponsavel periodo;
	private Cofre cofre;

	/* Campos usados para pesquisas */
	public enum Fields {
		DATA_RECOLHIMENTO("dataRecolhimento"),
		VALOR("valor");

		private String descricao;

		private Fields(String descricao) {
			this.descricao = descricao;
		}

		public String toString() {
			return descricao;
		}
	}

	public Arrecadacao() {

	}
	
	public Arrecadacao(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDataRecolhimento() {
		return dataRecolhimento;
	}

	public void setDataRecolhimento(Date dataRecolhimento) {
		this.dataRecolhimento = dataRecolhimento;
	}
	
	@NotNull
	@DecimalMin("0.01")
	@Column(precision = 10, scale = 2, nullable = false)
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "fk_periodo", nullable = false)
	public PeriodoDoResponsavel getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(PeriodoDoResponsavel periodo) {
		this.periodo = periodo;
	}
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "fk_cofre", nullable = false)
	public Cofre getCofre() {
		return cofre;
	}

	public void setCofre(Cofre cofre) {
		this.cofre = cofre;
	}
	
}
