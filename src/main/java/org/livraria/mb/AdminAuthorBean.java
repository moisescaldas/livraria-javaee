package org.livraria.mb;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.livraria.domain.dao.AuthorDAO;
import org.livraria.domain.entity.Author;

@Model
public class AdminAuthorBean {

	private Author person;
	private AuthorDAO dao;
	
	public AdminAuthorBean() {}
	
	@Inject
	public AdminAuthorBean(Author person, AuthorDAO dao) {
		this.person = person;
		this.dao = dao;
	}
	
	@Transactional
	public void save() {
		dao.save(this.person);
	}

	public Author getPerson() {
		return person;
	}

}
