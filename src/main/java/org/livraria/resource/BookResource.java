package org.livraria.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.livraria.domain.dao.BookDAO;
import org.livraria.domain.entity.Book;

@Path("books")
public class BookResource {
	@Inject
	private BookDAO bookDAO;
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("json")
	public List<Book> lastBooksJson() {
		return bookDAO.lastReleasesServico();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML})
	@Path("xml")
	@Wrapped(element = "books")
	public List<Book> lastBooksXML() {
		return bookDAO.lastReleasesServico();
	}
	
}
