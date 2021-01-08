package org.pwte.example.resources;

import com.ibm.cardinal.util.*;

import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.pwte.example.domain.Product;
import org.pwte.example.exception.ProductDoesNotExistException;
import org.pwte.example.service.ProductSearchService;



public class ProductResource {

	
		
		public ProductResource() throws NamingException
		{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/ProductResource.java:ProductResource:ProductResource");
    }
		
		
		
		
		public Response getProduct( int productId)
		{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/ProductResource.java:ProductResource:getProduct");
    }

		
		
		public List<Product> getProductsByCategory( int categoryId)
		{
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServicesWeb/src/org/pwte/example/resources/ProductResource.java:ProductResource:getProductsByCategory");
    }
}
