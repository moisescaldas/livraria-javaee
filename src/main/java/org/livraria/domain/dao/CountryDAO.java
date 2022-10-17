package org.livraria.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.Country;

public class CountryDAO {
	private static final String QUERY_LISTAR = "select c from Country c order by c.iso";
	private static final String QUERY_PROCURAR_PELO_ID = "select c from Country c where c.id = :id";

	@PersistenceContext
	private EntityManager em;

	public CountryDAO() {
	}

	public List<Country> list() {
		return em.createQuery(QUERY_LISTAR, Country.class).getResultList();
	}

	public Country findCountryById(Integer id) {
		return em.createQuery(QUERY_PROCURAR_PELO_ID, Country.class).setParameter("id", id).getResultList().stream()
				.findFirst().orElse(null);
	}
}
