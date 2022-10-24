package org.livraria.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.Checkout;

public class CheckoutDAO {
	private static final String QUERY_FIND_BY_UUID = "select c from Checkout c where c.uuid = :uuid";

	@PersistenceContext
	private EntityManager em;

	public void save(Checkout checkout) {
		em.persist(checkout);
	}

	public Checkout findByUUID(String uuid) {
		return em.createQuery(QUERY_FIND_BY_UUID, Checkout.class).setParameter("uuid", uuid).getResultList().stream()
				.findFirst().orElse(null);
	}
}
