package org.livraria.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@Entity
@Cacheable
@XmlRootElement
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SQ_BOOK")
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
	
	@DecimalMin("0")
	@Column(name = "PRICE")
	private BigDecimal price;

	@Size(min = 1)
	@ManyToMany(fetch = FetchType.EAGER )
	@JoinTable(name = "Book_Author", 
			joinColumns = @JoinColumn(name = "SQ_BOOK"), 
			inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID")
			)
	@XmlElement(name = "author")
	@XmlElementWrapper(name = "authors")
	private List<Author> authors = new ArrayList<>();

	@Column(name = "RELEASEDATE")
	@NotNull
	private Calendar releaseDate;

	private String coverUrl;
	
	public Book() {}
	
	public Book(Long id, String title, String coverUrl) {
		this.id = id;
		this.title = title;
		this.coverUrl = coverUrl;
	}
	
	/*
	 * Getters e Setters
	 */
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

	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getCoverUrl() {
		return coverUrl;
	}
	
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
}
