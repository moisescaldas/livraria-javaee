package org.livraria.domain.beans;

import java.math.BigDecimal;
import java.util.Objects;

import org.livraria.domain.entity.Book;

public class ShoppingItem {
	private Book book;
	
	private Long bookId;
	
	public ShoppingItem(Book book) {
		this.book = book;
		this.bookId = this.book.getId();
	}

	/*
	 * Getters e Setters and generated codes
	 */
	
	public Long getBookId() {
		return bookId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public BigDecimal getPrice() {
		return book.getPrice();
	}

	public BigDecimal getTotal(Integer quantity) {
		return getPrice().multiply(BigDecimal.valueOf(quantity));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bookId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingItem other = (ShoppingItem) obj;
		return Objects.equals(bookId, other.bookId);
	}
}
