package org.livraria.ws.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.livraria.domain.dao.BookDAO;
import org.livraria.domain.entity.Book;

@Path("books")
public class BookResource {
	@Inject
	private BookDAO bookDAO;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Book> lastBooksJson() {
		return bookDAO.lastReleasesServico();
	}

}
