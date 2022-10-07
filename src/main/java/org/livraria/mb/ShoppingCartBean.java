package org.livraria.mb;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.livraria.domain.beans.ShoppingCart;
import org.livraria.domain.beans.ShoppingItem;
import org.livraria.domain.dao.BookDAO;

@Model
public class ShoppingCartBean {

	private BookDAO dao;
	private ShoppingCart shoppingCart;
	
	public ShoppingCartBean() {}
	
	@Inject
	public ShoppingCartBean(BookDAO dao, ShoppingCart shoppingCart) {
		this.dao = dao;
		this.shoppingCart = shoppingCart;
	}
	
	public String add(Long id) {
		shoppingCart.add(new ShoppingItem(dao.findById(id)));
		return "/site/carrinho?faces-redirect=true";
	}
	
	public String remove(Long id) {
		shoppingCart.remove(new ShoppingItem(dao.findById(id)));
		return "/site/carrinho?faces-redirect=true";
	}
	
}
