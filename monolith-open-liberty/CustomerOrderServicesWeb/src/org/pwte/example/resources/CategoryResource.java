package org.pwte.example.resources;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pwte.example.domain.Category;
import org.pwte.example.exception.CategoryDoesNotExist;
import org.pwte.example.service.ProductSearchService;

@Path("/Category")
public class CategoryResource 
{
	@EJB ProductSearchService productSearch;
	
	public CategoryResource() throws NamingException
	{
		//productSearch = (ProductSearchService) new InitialContext().lookup("ejblocal:org.pwte.example.service.ProductSearchService");
		productSearch = (ProductSearchService) 
		new InitialContext().lookup("java:app/CustomerOrderServices/ProductSearchServiceImpl!org.pwte.example.service.ProductSearchService");
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category loadCategory(@PathParam(value="id") int categoryId)
	{
		try {
			return productSearch.loadCategory(categoryId);
		} catch (CategoryDoesNotExist e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> loadTopLevelCategories()
	{
		return productSearch.getTopLevelCategories();
	}
	
}
