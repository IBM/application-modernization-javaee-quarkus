package org.pwte.example.resources;

import com.ibm.cardinal.util.*;

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


public class CategoryResource 
{
	
	
	public CategoryResource() throws NamingException
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CategoryResource.java:CategoryResource:CategoryResource");
    }
	
	
	
	
	public Category loadCategory( int categoryId)
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CategoryResource.java:CategoryResource:loadCategory");
    }
	
	
	
	public List<Category> loadTopLevelCategories()
	{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/CategoryResource.java:CategoryResource:loadTopLevelCategories");
    }
	
}

