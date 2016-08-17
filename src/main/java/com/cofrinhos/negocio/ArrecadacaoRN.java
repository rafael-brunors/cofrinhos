package com.cofrinhos.negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import com.cofrinhos.colecao.ColecaoTotalPorDia;
import com.cofrinhos.dao.ArrecadacaoDAO;
import com.cofrinhos.model.Arrecadacao;
import com.cofrinhos.util.DAOException;
import com.cofrinhos.util.RNException;

public class ArrecadacaoRN implements IRN<Arrecadacao>, Serializable{

	private static final long serialVersionUID = 4217731672634766549L;

	@Inject
	ArrecadacaoDAO arrecadacaoDAO;

	@Override
	public void saveOrUpdate(Arrecadacao model) throws RNException {
		try {
			if(model.getPeriodo() == null)
				throw new RNException("Não é possível salvar com a 'Data de recolhimento' fora de um período cadastrado.");
			arrecadacaoDAO.saveOrUpdate(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível salvar. Erro.: "+e.getMessage(), e);
		}
	}

	@Override
	public void delete(Arrecadacao model) throws RNException {
		try {
			arrecadacaoDAO.delete(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível excluir. Erro.: "+e.getMessage(), e);
		}
	}

	@Override
	public Arrecadacao findById(Arrecadacao filtro) {
		return arrecadacaoDAO.findById(filtro);
	}

	@Override
	public List<Arrecadacao> findAll() {
		return arrecadacaoDAO.findAll();
	}
	
	public BigDecimal saldo() {
		return arrecadacaoDAO.saldo();
	}
	
	public List<ColecaoTotalPorDia> listaTotalPorDia() {
		return arrecadacaoDAO.listaTotalPorDia();
	}

}
