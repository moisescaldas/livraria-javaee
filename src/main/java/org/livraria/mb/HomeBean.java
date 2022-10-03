package org.livraria.mb;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.livraria.domain.dao.BookDAO;
import org.livraria.domain.entity.Book;

@Model
public class HomeBean {
	
	private BookDAO dao;
	
	public HomeBean() {}
	
	@Inject
	public HomeBean(BookDAO dao) {
		this.dao = dao;
	}
	
	public List<Book> lastReleases() {
		return dao.lastReleases();
	}
	
	public List<Book> olderBooks() {
		return dao.olderBooks();
	}
	
}
