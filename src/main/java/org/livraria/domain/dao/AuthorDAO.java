package org.livraria.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.Author;

public class AuthorDAO {
	@PersistenceContext
	private EntityManager manager;
	
	public void save(Author author) {
		manager.persist(author);
	}
	
	public List<Author> list() {
		return manager.createQuery("select a from Author a", Author.class).getResultList();
	}
}
