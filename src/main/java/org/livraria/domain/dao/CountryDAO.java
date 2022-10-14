package org.livraria.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.Country;

public class CountryDAO {
	@PersistenceContext
	private EntityManager em;
	
	public List<Country> list(){
		return em.createQuery("select c from Country c", Country.class).getResultList();
	}
}
