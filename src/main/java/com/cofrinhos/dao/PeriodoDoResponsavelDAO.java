package com.cofrinhos.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;

import com.cofrinhos.model.PeriodoDoResponsavel;
import com.cofrinhos.util.DAOException;

public class PeriodoDoResponsavelDAO implements IDAO<PeriodoDoResponsavel>, Serializable {

	private static final long serialVersionUID = 1415594763988645975L;

	@Inject
	private Session session;
	
	
	@Override
	public void saveOrUpdate(PeriodoDoResponsavel model) throws DAOException {
		session.clear();
		session.merge(model);
	}

	@Override
	public void delete(PeriodoDoResponsavel model) throws DAOException {
		session.delete(model);		
	}

	@Override
	public PeriodoDoResponsavel findById(PeriodoDoResponsavel filtro) {
		return (PeriodoDoResponsavel) session.get(PeriodoDoResponsavel.class, filtro.getId());
	}

	@Override
	public List<PeriodoDoResponsavel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
