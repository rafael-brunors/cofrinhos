package com.cofrinhos.negocio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cofrinhos.dao.PeriodoDoResponsavelDAO;
import com.cofrinhos.model.PeriodoDoResponsavel;
import com.cofrinhos.util.DAOException;
import com.cofrinhos.util.RNException;

public class PeriodoDoResponsavelRN  implements IRN<PeriodoDoResponsavel>, Serializable {

	private static final long serialVersionUID = -367909137854796090L;

	@Inject
	private PeriodoDoResponsavelDAO dao;
	
	public void saveOrUpdate(PeriodoDoResponsavel model) throws RNException {
		try{
			dao.saveOrUpdate(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível salvar. Erro.: "+e.getMessage(), e);
		}
	}

	public void delete(PeriodoDoResponsavel model) throws RNException {
		try {
			dao.delete(model);
		} catch (DAOException e) {
			throw new RNException("Não foi poss�vel excluir. Erro.: "+e.getMessage(), e);
		}
	}

	@Override
	public PeriodoDoResponsavel findById(PeriodoDoResponsavel filtro) {
		return dao.findById(filtro);
	}

	@Override
	public List<PeriodoDoResponsavel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}