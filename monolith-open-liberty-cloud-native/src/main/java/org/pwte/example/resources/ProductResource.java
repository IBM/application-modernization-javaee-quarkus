package org.pwte.example.resources;

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


@Path("/jaxrs/Product")
public class ProductResource {

	@EJB ProductSearchService productSearch;
		
		public ProductResource() throws NamingException
		{
			//Work around until Java EE 6
	productSearch = (ProductSearchService) new InitialContext().lookup("java:app/CustomerOrderServices/ProductSearchServiceImpl!org.pwte.example.service.ProductSearchService");

		}
		
		@GET
		@Path("/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getProduct(@PathParam(value="id") int productId)
		{
			try {
				Product product = productSearch.loadProduct(productId);
			    Calendar now = Calendar.getInstance();
			    Calendar tomorrow = (Calendar)now.clone();
			    tomorrow.add(Calendar.DATE, 1);
			    tomorrow.set(Calendar.HOUR, 0);
			    tomorrow.set(Calendar.MINUTE, 0);
			    tomorrow.set(Calendar.SECOND, 0);
			    tomorrow.set(Calendar.MILLISECOND, 0);
			    
			    System.out.println("Expires -> " + tomorrow.getTime());
			    
				return Response.ok(product).header("Expires", tomorrow.getTime()).build(); 
			} catch (ProductDoesNotExistException e) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Product> getProductsByCategory(@QueryParam(value="categoryId") int categoryId)
		{
			if(categoryId <= 0)
			{
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}
			return productSearch.loadProductsByCategory(categoryId);
			
		}
}