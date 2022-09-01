package org.livraria.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.livraria.domain.entity.Book;

public class BookDAO {
	@PersistenceContext
	private EntityManager manager;
	
	public void save(Book book) {
		manager.persist(book);
	}
	
	public List<Book> list() {
		return manager.createQuery("select b from Book b", Book.class).getResultList();
	}
	
}
