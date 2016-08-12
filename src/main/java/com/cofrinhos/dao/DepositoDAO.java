package com.cofrinhos.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.cofrinhos.model.Deposito;
import com.cofrinhos.util.DAOException;

public class DepositoDAO implements IDAO<Deposito>, Serializable {
	
	private static final long serialVersionUID = 4646148977510903551L;

	@Inject
	private Session session;

	@Override
	public void saveOrUpdate(Deposito model) throws DAOException {
		session.merge(model);
		session.flush();
		session.clear();
	}

	@Override
	public void delete(Deposito model) throws DAOException {
		model = (Deposito) session.get(Deposito.class, model.getId());
		session.delete(model);	
	}

	@Override
	public Deposito findById(Deposito filtro) {
		return (Deposito) session.get(Deposito.class, filtro.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Deposito> findAll() {
		return session.createCriteria(Deposito.class).list();
	}

	public BigDecimal saldo() {
		return (BigDecimal) session.createCriteria(Deposito.class)
		           .setProjection(Projections.sum(Deposito.Fields.VALOR.toString())).uniqueResult();
	}

}
