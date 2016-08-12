package com.cofrinhos.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class Deposito implements Serializable{

	private static final long serialVersionUID = 4906452369570797445L;

	private int id;
	private Date data;
	private BigDecimal valor;
	
	/* Campos usados para pesquisas */
	public enum Fields {
		VALOR("valor");

		private String descricao;

		private Fields(String descricao) {
			this.descricao = descricao;
		}

		public String toString() {
			return descricao;
		}
	}
	
	public Deposito() {
	}
	
	public Deposito(int id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue
	@Column(nullable = false, unique = true)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
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
}
