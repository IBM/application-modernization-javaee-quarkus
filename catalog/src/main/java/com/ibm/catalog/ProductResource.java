package com.ibm.catalog;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.ibm.catalog.Product;

@Path("/CustomerOrderServicesWeb/jaxrs/Product")
@ApplicationScoped
@Produces("application/json")
public class ProductResource {

    @Inject
    protected EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsByCategory(@QueryParam(value="categoryId") int categoryId) {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Product invoked in Quarkus catalog service");
        
        if(categoryId <= 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Query query = entityManager.createNamedQuery("product.by.cat.or.sub");
		query.setParameter(1, categoryId);
		query.setParameter(2, categoryId);

		return query.getResultList();	    
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    @Transactional
    public Product update(@PathParam("id") Long id, Product updatedProduct) {        
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Product @PUT updateProduct invoked in Quarkus catalog service");
        System.out.println(id);
        System.out.println(updatedProduct.price);

        Product existingProduct = entityManager.find(Product.class, id);
        if (existingProduct == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }    
        existingProduct.price = updatedProduct.price;
        
        entityManager.persist(existingProduct);

		return existingProduct;	    
    }
}
