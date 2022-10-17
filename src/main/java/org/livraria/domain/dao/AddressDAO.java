package org.livraria.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.Address;

public class AddressDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void save(Address address){
		em.persist(address);
	}
}
