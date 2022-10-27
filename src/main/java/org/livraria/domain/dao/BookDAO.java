package org.livraria.domain.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.jpa.QueryHints;
import org.livraria.domain.entity.Book;

public class BookDAO {
	private static final String QUERY_FIND_ALL = "select b from Book b";
	private static final String QUERY_FIND_BY_ID = "select b from Book b where b.id = :id";
	private static final String QUERY_SELECIONAR_HOME_PAGE = "select new Book(b.id, b.title, b.coverUrl) from Book b where b.releaseDate <= now() order by b.id desc";

	@PersistenceContext
	private EntityManager manager;

	public Book findBookById(Long id) {
		TypedQuery<Book> query = manager.createQuery(QUERY_FIND_BY_ID, Book.class);
		query.setParameter("id", id);

		return query.getSingleResult();
	}

	public void save(Book book) {
		manager.persist(book);
	}

	public List<Book> list() {
		return manager.createQuery(QUERY_FIND_ALL, Book.class).getResultList();
	}

	public List<Book> lastReleases() {
		return manager.createQuery(QUERY_SELECIONAR_HOME_PAGE, Book.class).setHint(QueryHints.HINT_CACHEABLE, true)
				.setMaxResults(3).getResultList();
	}

	public List<Book> olderBooks() {
		return manager.createQuery(QUERY_SELECIONAR_HOME_PAGE, Book.class).setMaxResults(20)
				.setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
	}

	public void removeById(Long id) {
		Book book = findBookById(id);

		if (book == null) {
			return;
		}

		manager.remove(book);
	}

}
