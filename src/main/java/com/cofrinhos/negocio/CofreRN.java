package com.cofrinhos.negocio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cofrinhos.dao.CofreDAO;
import com.cofrinhos.model.Cofre;
import com.cofrinhos.util.DAOException;
import com.cofrinhos.util.RNException;

public class CofreRN  implements IRN<Cofre>,  Serializable {

	private static final long serialVersionUID = 8110741525549867303L;

	@Inject
	private CofreDAO cofreDAO;
	
	public void saveOrUpdate(Cofre model) throws RNException {
		try{
			if(model.getNumeroDoCofre() != 0) {
				Cofre cofreExistente = cofreDAO.obterPorNumeroDoCofre(model); 
				if(cofreExistente == null) {
					cofreDAO.saveOrUpdate(model);
				} else if(model.getId() == cofreExistente.getId()) {
					cofreDAO.saveOrUpdate(model);
				} else {
					throw new RNException("Já existe um cofre com o 'Número Do Cofre' informado.");
				}
			} else {
				throw new RNException("Número do cofre não pode ser zero.");
			}
		} catch (DAOException e) {
			throw new RNException("Não foi possível salvar. Erro.: "+e.getMessage(), e);
		}
	}
	
	public void delete(Cofre model) throws RNException {
		try {
			cofreDAO.delete(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível excluir. Erro.: "+e.getMessage(), e);
		}
	}

	@Override
	public Cofre findById(Cofre filtro) {
		return cofreDAO.findById(filtro);
	}

	@Override
	public List<Cofre> findAll() {
		return cofreDAO.findAll();
	}
}