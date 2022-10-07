package org.livraria.mb;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.livraria.domain.dao.BookDAO;
import org.livraria.domain.entity.Book;

@Model
public class BookDetailBean {
	
	private BookDAO dao;
	private Book book;
	private Long id;
	
	public BookDetailBean() {}
	
	@Inject
	public BookDetailBean(BookDAO dao) {
		this.dao = dao;
	}
	
	public void loadBook() {
		book = dao.findById(id);
	}
	
	/*
	 * Getters e Setters
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
}
