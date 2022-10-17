package org.livraria.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.SystemUser;

public class SystemUserDAO {
	private static final String QUERY_FIND_USER_BY_EMAIL = "select u from SystemUser u where u.email = :email";

	@PersistenceContext
	private EntityManager em;

	public void save(SystemUser user) {
		em.persist(user);
	}

	public SystemUser findUserByEmail(String email) {
		return em.createQuery(QUERY_FIND_USER_BY_EMAIL, SystemUser.class).setParameter("email", email).getResultList()
				.stream().findFirst().orElse(null);
	}
	
	public Boolean hasEmail(String email) {
		return findUserByEmail(email) == null ? false : true;
	}
}
