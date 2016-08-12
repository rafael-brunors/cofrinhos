package com.cofrinhos.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.cofrinhos.model.Responsavel;
import com.cofrinhos.util.DAOException;

public class ResponsavelDAO implements IDAO<Responsavel>, Serializable {

	private static final long serialVersionUID = 6421649798426296407L;
	
	@Inject
	private  Session session;
	
	@Override
	public void saveOrUpdate(Responsavel model) throws DAOException {		
		session.merge(model);
	}

	@Override
	public void delete(Responsavel model) throws DAOException {
		model = (Responsavel) session.get(Responsavel.class, model.getId());
		session.delete(model);		
	}

	@Override
	public Responsavel findById(Responsavel filtro) {
		return (Responsavel) session.get(Responsavel.class, filtro.getId());
	}

	public Responsavel obterPorNome(Responsavel filtro) {
		Criteria criteria = session.createCriteria(Responsavel.class);
		
		criteria.add(Restrictions.eq("nome", filtro.getNome()));
		return (Responsavel) criteria.uniqueResult();
	}
	
	public Responsavel obterPorTelefone(Responsavel filtro) {	
		Criteria criteria = session.createCriteria(Responsavel.class);
		
		criteria.add(Restrictions.eq("telefone", filtro.getTelefone()));
		return (Responsavel) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Responsavel> findAll() {
		return session.createCriteria(Responsavel.class).list();
	}
}
