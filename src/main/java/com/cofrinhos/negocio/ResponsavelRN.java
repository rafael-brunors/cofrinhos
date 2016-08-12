package com.cofrinhos.negocio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cofrinhos.dao.ResponsavelDAO;
import com.cofrinhos.model.Responsavel;
import com.cofrinhos.util.DAOException;
import com.cofrinhos.util.RNException;
import com.cofrinhos.util.transactionHibernate.Transactional;

public class ResponsavelRN implements IRN<Responsavel>, Serializable {
	
	private static final long serialVersionUID = -7380185222695636756L;

	@Inject
	private ResponsavelDAO responsavelDAO;
	
	@Override
	public void saveOrUpdate(Responsavel model) throws RNException {
		try{
			Responsavel responsavelExistente = responsavelDAO.obterPorNome(model);
			if(responsavelExistente != null && model.getId() != responsavelExistente.getId()) {
				throw new RNException("Já existe um responsável com o 'Nome' informado.");
			} else {
				responsavelExistente = responsavelDAO.obterPorTelefone(model);
				if(responsavelExistente != null && model.getId() != responsavelExistente.getId()) {
					throw new RNException("Já existe um responsável com 'Telefone' informado.");
				} else {
					responsavelDAO.saveOrUpdate(model);
				}
			}
		} catch (DAOException e) {
			throw new RNException("Não foi possível salvar. Erro.: "+e.getMessage(), e);
		}
	}

	
	@Transactional
	public void delete(Responsavel model) throws RNException {
		try {
			responsavelDAO.delete(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível excluir. Erro.: "+e.getMessage(), e);
		}
	}

	@Override
	public Responsavel findById(Responsavel filtro) {
		return responsavelDAO.findById(filtro);
	}

	@Override
	public List<Responsavel> findAll() {
		return responsavelDAO.findAll();
	}

}