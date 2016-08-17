package com.cofrinhos.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.cofrinhos.colecao.ColecaoTotalPorDia;
import com.cofrinhos.model.Arrecadacao;
import com.cofrinhos.util.DAOException;

public class ArrecadacaoDAO implements IDAO<Arrecadacao> {

	@Inject
	Session session;
	
	@Override
	public void saveOrUpdate(Arrecadacao model) throws DAOException {
		session.merge(model);
		session.flush();
		session.clear();
	}

	@Override
	public void delete(Arrecadacao model) throws DAOException {
		model = (Arrecadacao) session.get(Arrecadacao.class, model.getId());
		session.delete(model);
		session.flush();
		session.clear();
	}

	@Override
	public Arrecadacao findById(Arrecadacao filtro) {
		return (Arrecadacao) session.get(Arrecadacao.class, filtro.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Arrecadacao> findAll() {
		if(session.isOpen()) {
			Criteria criteria = session.createCriteria(Arrecadacao.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.desc(Arrecadacao.Fields.DATA_RECOLHIMENTO.toString()));
			return criteria.list();
		}
		return null;
	}

	public BigDecimal saldo() {
		return (BigDecimal) session.createCriteria(Arrecadacao.class)   
		           .setProjection(Projections.sum(Arrecadacao.Fields.VALOR.toString())).uniqueResult();
	}
	

	@SuppressWarnings("unchecked")
	public List<ColecaoTotalPorDia> listaTotalPorDia() {
		return session.createCriteria(Arrecadacao.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property(Arrecadacao.Fields.DATA_RECOLHIMENTO.toString()), ColecaoTotalPorDia.Fields.DATA.toString())
					.add(Projections.sum(Arrecadacao.Fields.VALOR.toString()), ColecaoTotalPorDia.Fields.VALOR.toString())
					.add(Projections.groupProperty(Arrecadacao.Fields.DATA_RECOLHIMENTO.toString())))
				.setResultTransformer(Transformers.aliasToBean(ColecaoTotalPorDia.class))
				.list();
	}
}
