package com.ibm.catalog;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Path("/CustomerOrderServicesWeb/jaxrs/Product")
@ApplicationScoped
@Produces("application/json")
public class ProductResource {

    @Inject
    protected EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsByCategory(@QueryParam(value="categoryId") int categoryId)
    {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Product invoked in Quarkus catalog service");
        System.out.println(categoryId);
        if(categoryId <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Query query = em.createNamedQuery("product.by.cat.or.sub");
		query.setParameter(1, categoryId);
		query.setParameter(2, categoryId);

		return query.getResultList();	    
    }
}
