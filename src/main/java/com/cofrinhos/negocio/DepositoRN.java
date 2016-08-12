package com.cofrinhos.negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import com.cofrinhos.dao.DepositoDAO;
import com.cofrinhos.model.Deposito;
import com.cofrinhos.util.DAOException;
import com.cofrinhos.util.RNException;

public class DepositoRN implements IRN<Deposito>, Serializable {
	
	private static final long serialVersionUID = -3099153916466867958L;

	@Inject
	private DepositoDAO depositoDAO;
	
	@Override
	public void saveOrUpdate(Deposito model) throws RNException {
		try{
			depositoDAO.saveOrUpdate(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível salvar. Erro.: "+e.getMessage(), e);
		}
	}

	@Override
	public void delete(Deposito model) throws RNException {
		try {
			depositoDAO.delete(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possível excluir. Erro.: "+e.getMessage(), e);
		}
	}

	@Override
	public Deposito findById(Deposito filtro) {
		return depositoDAO.findById(filtro);
	}

	@Override
	public List<Deposito> findAll() {
		return depositoDAO.findAll();
	}

	public BigDecimal saldo() {
		return depositoDAO.saldo();
	}

}
