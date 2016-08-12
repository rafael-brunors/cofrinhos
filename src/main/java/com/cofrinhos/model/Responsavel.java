package com.cofrinhos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import com.cofrinhos.validation.Nome;

@Entity
public class Responsavel implements Serializable{
	
	private static final long serialVersionUID = -7839213564948251986L;

	private int id;
	private String nome;
	private String telefone;
	private String endereco;
	
	public Responsavel() {
	}
	
	public Responsavel(int id) {
		super();
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
	
	@NotBlank
	@Nome
	@Column(unique = true, nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotBlank
	@Column(unique = true, nullable = false, length=14)
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@NotBlank
	@Column(nullable = false)
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Responsavel other = (Responsavel) obj;
		if (id != other.id)
			return false;
		return true;
	}
 }
