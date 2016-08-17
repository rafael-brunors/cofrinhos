package com.cofrinhos.colecao;

import java.math.BigDecimal;
import java.util.Date;

public class ColecaoTotalPorDia {
	private BigDecimal valor;
	private Date data;
	
	/* Campos usados para pesquisas */
	public enum Fields {
		VALOR("valor"),
		DATA("data");

		private String descricao;

		private Fields(String descricao) {
			this.descricao = descricao;
		}

		public String toString() {
			return descricao;
		}
	}

	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date date) {
		this.data = date;
	}
}
