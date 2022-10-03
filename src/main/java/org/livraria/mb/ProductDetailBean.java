package org.livraria.mb;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.livraria.domain.dao.BookDAO;
import org.livraria.domain.entity.Book;

@Model
public class ProductDetailBean {
	
	private BookDAO dao;
	private Book book;
	private Long id;
	
	public ProductDetailBean() {}
	
	@Inject
	public ProductDetailBean(BookDAO dao) {
		this.dao = dao;
	}
	
	public void loadBook() {
		book = dao.findById(id);
	}
	
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
