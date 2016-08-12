package com.cofrinhos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cofre implements Serializable {
	
	private static final long serialVersionUID = 5321595074224715419L;
	
	private int id;
	private int numeroDoCofre;
	
	private List<PeriodoDoResponsavel> periodosDoResponsavel;
	
	public Cofre() {
		super();
	}
	
	public Cofre(int id) {
		super();
		this.id = id;
	}
	
	/* Campos usados para pesquisas */
	public enum Fields {
		NUMERO_DO_COFRE("numeroDoCofre");

		private String descricao;

		private Fields(String descricao) {
			this.descricao = descricao;
		}

		public String toString() {
			return descricao;
		}
	}

	public void adicionaPeriodoDoResponsavel(PeriodoDoResponsavel periodoDoResponsavel) {
		if(this.periodosDoResponsavel == null) {
			this.periodosDoResponsavel = new ArrayList<PeriodoDoResponsavel>();
			System.out.println("Lista Cofre.PeriodoDoResponsavel esteve nula ao adicionar!");
		}
		this.periodosDoResponsavel.add(periodoDoResponsavel);
		periodoDoResponsavel.setCofre(this); // mantém a consistência
    }


	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	public int getId() {
		return id;
	}

	@Column(nullable = false)
	public int getNumeroDoCofre() {
		return numeroDoCofre;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="cofre", orphanRemoval=true)
	public List<PeriodoDoResponsavel> getPeriodosDoResponsavel() {
		return periodosDoResponsavel;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setNumeroDoCofre(int numeroDoCofre) {
		this.numeroDoCofre = numeroDoCofre;
	}

	public void setPeriodosDoResponsavel(List<PeriodoDoResponsavel> periodoDoResponsavel) {
		this.periodosDoResponsavel = periodoDoResponsavel;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cofre other = (Cofre) obj;
		if (id != other.id)
			return false;
		if (periodosDoResponsavel == null) {
			if (other.periodosDoResponsavel != null)
				return false;
		} else if (!periodosDoResponsavel.equals(other.periodosDoResponsavel))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime
				* result
				+ ((periodosDoResponsavel == null) ? 0 : periodosDoResponsavel
						.hashCode());
		return result;
	}
	
}