package org.livraria.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.SystemUser;

public class SystemUserDAO {
	private static final String QUERY_FIND_USER_BY_EMAIL = "select u from SystemUser u where u.email = :email";
	private static final String QUERY_FIND_USER_BY_CPF = "select u from SystemUser u where u.socialId = :socialId";

	@PersistenceContext
	private EntityManager em;

	public void save(SystemUser user) {
		em.persist(user);
	}

	public SystemUser findUserByEmail(String email) {
		return em.createQuery(QUERY_FIND_USER_BY_EMAIL, SystemUser.class).setParameter("email", email).getResultList()
				.stream().findFirst().orElse(null);
	}

	public SystemUser findUserByCPF(String cpf) {
		return em.createQuery(QUERY_FIND_USER_BY_CPF, SystemUser.class).setParameter("socialId", cpf).getResultList()
				.stream().findFirst().orElse(null);
	}

	public Boolean hasEmail(String email) {
		return findUserByEmail(email) == null ? false : true;
	}

	public Boolean hasSocialId(String socialId) {
		return findUserByCPF(socialId) == null ? false : true;
	}
}
