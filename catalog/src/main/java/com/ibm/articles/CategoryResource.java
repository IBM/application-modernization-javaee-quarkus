package com.ibm.articles;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Path("/CustomerOrderServicesWeb/jaxrs/Category")
@ApplicationScoped
@Produces("application/json")
public class CategoryResource {

    @GET
    public List<CategoryOutput> get() {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Category invoked in Quarkus catalog service");

        List<Category> categories = Category.listAll();
        List<SubCategory> subCategories = SubCategory.listAll();

        List<CategoryOutput> output = new ArrayList<CategoryOutput>();
        categories.forEach(category -> {
            CategoryOutput o = new CategoryOutput();
            o.name = category.name;
            o.id = category.id.intValue();

            List<SubCategoryOutput> suboutput = new ArrayList<SubCategoryOutput>();
            subCategories.forEach(subcategory -> {
                if (category.id.toString().equalsIgnoreCase(subcategory.parent)) {
                    SubCategoryOutput so = new SubCategoryOutput();
                    so.name = subcategory.name;
                    so.id = subcategory.id.intValue();
                    suboutput.add(so);
                }
            });
            o.subCategories = suboutput;

            output.add(o);
        });
        
        return output;
    }


    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                    .build();
        }

    }
}
