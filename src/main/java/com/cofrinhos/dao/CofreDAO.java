package com.cofrinhos.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cofrinhos.model.Cofre;
import com.cofrinhos.model.PeriodoDoResponsavel;
import com.cofrinhos.util.DAOException;

public class CofreDAO implements IDAO<Cofre>, Serializable {

	private static final long serialVersionUID = -8945049439418594275L;
	
	@Inject
	public Session session;
	
	@Override
	public void saveOrUpdate(Cofre model) throws DAOException {
		if(model.getId() != 0) {
			Cofre cofre = (Cofre) session.get(Cofre.class, model.getId());
			for (int i = 0; i < cofre.getPeriodosDoResponsavel().size(); i++) {
				boolean excluir = true;
				for (PeriodoDoResponsavel periodoModel : model.getPeriodosDoResponsavel()) {
					if(periodoModel.getId() == cofre.getPeriodosDoResponsavel().get(i).getId()) {
						excluir = false;
						break;
					}
				}
				if(excluir == true) {
					PeriodoDoResponsavel periodo = cofre.getPeriodosDoResponsavel().get(i);
					periodo.setCofre(null);
				}
			}
		}
		
		session.merge(model);
		session.flush();
		session.clear();
	}

	@Override
	public void delete(Cofre model) throws DAOException {
		model = (Cofre) session.get(Cofre.class, model.getId());
		session.delete(model);
		session.flush();
		session.clear();
	}

	@Override
	public Cofre findById(Cofre filtro) {
		return (Cofre) session.get(Cofre.class, filtro.getId());
	}

	public Cofre obterPorNumeroDoCofre(Cofre filtro) {
		Criteria criteria = session.createCriteria(Cofre.class);
		criteria.add(Restrictions.eq("numeroDoCofre", filtro.getNumeroDoCofre()));
		return (Cofre) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cofre> findAll() {
		if(session.isOpen()) {
			Criteria criteria = session.createCriteria(Cofre.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.asc(Cofre.Fields.NUMERO_DO_COFRE.toString()));
			return criteria.list();
		}
		return null;
	}
}
