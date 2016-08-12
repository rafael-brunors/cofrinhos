package com.cofrinhos.util.transactionHibernate;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;

public class SessionProducer {
	
	@Inject
	private EntityManager em;
	
	@Produces @RequestScoped
	public Session createSession() {
		return (Session) em.getDelegate(); 
	}
}
