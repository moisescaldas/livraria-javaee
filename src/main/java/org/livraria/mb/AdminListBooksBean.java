package org.livraria.mb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.livraria.domain.dao.BookDAO;
import org.livraria.domain.entity.Book;

@Model
public class AdminListBooksBean {
	private List<Book> books;
	
	private BookDAO dao;

	public AdminListBooksBean() {}
	
	@Inject
	public AdminListBooksBean(BookDAO dao) {
		this.dao = dao;
	}
	
	@PostConstruct
	public void setup() {
		books = dao.list();
	}
	
	/*
	 * Getters e Setters
	 */
	public List<Book> getBooks() {
		return books;
	}
	
	@Transactional
	public void remover(Long id) {
		System.out.println("Test" + id);
		dao.removeById(id);
	}
}
