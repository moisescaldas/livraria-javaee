package org.livraria.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.livraria.domain.entity.Book;

public class BookDAO {
	@PersistenceContext
	private EntityManager manager;

	public Book findBookById(Long id) {
		TypedQuery<Book> query = manager.createQuery("select b from Book b where b.id = :id", Book.class);
		query.setParameter("id", id);

		return query.getSingleResult();
	}

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
		return manager.createQuery("select b from Book b where b.releaseDate <= now()", Book.class).setMaxResults(20)
				.getResultList();
	}

	public void removeById(Long id) {
		Book book = findById(id);

		if (book == null) {
			return;
		}

		manager.remove(book);
	}

	public Book findById(Long id) {
		TypedQuery<Book> query = manager.createQuery("select b from Book b where b.id = :id", Book.class);
		return query.setParameter("id", id).getSingleResult();
	}

}
