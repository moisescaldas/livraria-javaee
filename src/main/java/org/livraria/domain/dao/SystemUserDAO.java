package org.livraria.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.SystemUser;

public class SystemUserDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void save(SystemUser user) {
		em.persist(user);
	}
}
