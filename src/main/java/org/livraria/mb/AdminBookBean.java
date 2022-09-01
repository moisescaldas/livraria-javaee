package org.livraria.mb;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.livraria.domain.dao.AuthorDAO;
import org.livraria.domain.dao.BookDAO;
import org.livraria.domain.entity.Author;
import org.livraria.domain.entity.Book;
import org.livraria.util.helper.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model
public class AdminBookBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminBookBean.class);

	private Book product;
	private BookDAO dao;
	private AuthorDAO authorDAO;
	private MessageHelper helper;

	private List<Author> authors;
	private List<Long> selectedIds;

	public AdminBookBean() {
	}

	@Inject
	public AdminBookBean(Book product, BookDAO dao, AuthorDAO authorDAO, MessageHelper helper) {
		this.product = product;
		this.dao = dao;
		this.authorDAO = authorDAO;
		this.helper = helper;
	}

	@PostConstruct
	public void init() {
		this.authors = authorDAO.list();
		LOGGER.info("Carregado Lista de Autores [{}]", this.authors);
	}

	@Transactional
	public String save() {
		this.populate();
		dao.save(product);
		LOGGER.info("Novo produto salvo {}", this.product);
		helper.addFlashMessage("Livro Salvo com SUCESSO!");

		return "/produtos/list?faces-redirect=true";
	}

	public void populate() {
		this.authors.stream().filter(author -> selectedIds.contains(author.getId())).collect(Collectors.toList())
				.forEach(product.getAuthors()::add);
	}

	public void cleanObjects() {
		this.product = new Book();
		this.selectedIds.clear();
	}

	/*
	 * Getters e Setters
	 */
	public Book getProduct() {
		return this.product;
	}

	public void setSelectedIds(List<Long> selectedIds) {
		this.selectedIds = selectedIds;
	}

	public List<Author> getAuthors() {
		return this.authors;
	}

	public List<Long> getSelectedIds() {
		return this.selectedIds;
	}
}
