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

	public List<Book> lastReleases() {
		return manager.createQuery("select b from Book b where b.releaseDate <= now() order by b.id desc", Book.class)
				.setMaxResults(3).getResultList();
	}

	public List<Book> olderBooks() {
		return manager.createQuery("select b from Book b where b.releaseDate <= now()", Book.class)
				.setMaxResults(20).getResultList();
	}
	
	public void removeById(Long id) {
		Book book = findById(id);

		if (book == null) {
			return;
		}

		manager.remove(book);
	}

	public Book findById(Long id) {
		return manager.find(Book.class, id);
	}

}
