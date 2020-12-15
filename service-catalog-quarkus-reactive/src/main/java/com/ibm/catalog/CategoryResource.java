package com.ibm.catalog;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import io.vertx.axle.sqlclient.Row;
import io.vertx.axle.sqlclient.RowIterator;
import io.vertx.axle.sqlclient.Tuple;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Path("/CustomerOrderServicesWeb/jaxrs/Category")
@ApplicationScoped
@Produces("application/json")
public class CategoryResource {

    @Inject
    protected EntityManager em;
    
    @GET
    public List<Category> get() {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Category invoked in Quarkus catalog service");

        Query query = em.createNamedQuery("top.level.category");
        List<Category> categories = query.getResultList();
        return categories;
    }

    @Inject
    io.vertx.axle.pgclient.PgPool client;
    private static int MAXIMAL_DURATION = 5000;

    @Path("/CustomerOrderServicesWeb/jaxrs/Categoryr")
    @GET
    public CompletionStage<List<Category>> getReactive() {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Category getReactive invoked in Quarkus catalog service");

        String statement = "SELECT id, name, parent FROM category";
        return client.preparedQuery(statement)
                .toCompletableFuture()
                .orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {
                    //throw new Exception();
                    int i = 0;
                    return null;
                }).thenApply(rows -> {
                    List<Category> categories = new ArrayList<>(rows.size());
                    for (Row row : rows) {
                        categories.add(fromRow(row));
                    }
                    return categories;
                });
    }


    private static Category fromRow(Row row) {
        Category category = new Category();
        category.id = row.getLong("id");
        category.name = row.getString("title");
        //category.parent = row.getString("parent");
       
        return category;
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
