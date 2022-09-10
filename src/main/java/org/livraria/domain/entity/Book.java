package org.livraria.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "TITLE")
	@Length(min = 10)
	private String title;

	
	@Column(name = "DESCRIPTION")
	private String description;

	@Min(50)
	@Column(name = "PAGES")
	private Integer numberOfPages;
	
	@DecimalMin("20")
	@Column(name = "PRICE")
	private BigDecimal price;

	@Size(min = 1)
	@ManyToMany(fetch = FetchType.EAGER )
	@JoinTable(name = "book_author", 
			joinColumns = @JoinColumn(name = "book_id"), 
			inverseJoinColumns = @JoinColumn(name = "author_id")
			)
	private List<Author> authors = new ArrayList<>();

	@Column(name = "RELEASE")
	@NotNull
	@Future
	private Date releaseDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", description=" + description + ", numberOfPages="
				+ numberOfPages + ", price=" + price + ", authors=" + authors + "]";
	}

	public Date getReleaseDate() {
		return releaseDate;
		
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
		
	}

}
