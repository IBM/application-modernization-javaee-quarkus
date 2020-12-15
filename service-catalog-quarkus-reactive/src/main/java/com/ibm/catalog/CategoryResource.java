package com.ibm.catalog;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import io.vertx.axle.sqlclient.Row;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@Path("/CustomerOrderServicesWeb/jaxrs/Category")
@ApplicationScoped
@Produces("application/json")
public class CategoryResource {
  
    @Inject
    io.vertx.axle.pgclient.PgPool client;
    private static int MAXIMAL_DURATION = 5000;

    @Inject
    private InitDatabase initDatabase;

    @PostConstruct
    public void config() {
        initDatabase.config();
    }
    
    @GET
    public CompletionStage<List<Category>> get() {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Category invoked in Quarkus reactive catalog service");
        
        String statement = "SELECT id, name, parent FROM category";
        return client.preparedQuery(statement)
                .toCompletableFuture()
                .orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {                    
                    System.out.println(throwable);
                    return null;
                }).thenApply(rows -> {
                    List<Category> categories = new ArrayList<>(rows.size());
                    for (Row row : rows) {
                        categories.add(fromRow(row));
                    }
                    return resolveSubCategories(categories);
                });
    }

    private List<Category> resolveSubCategories(List<Category> categories) {
        List<Category> output = new ArrayList<>();
        for (Category category: categories) {
            if (category.parent == Long.valueOf(0)) {
                List<Category> subCategories = new ArrayList<>();
                for (Category cat: categories) {
                    if (cat.parent == category.id) {
                        subCategories.add(cat);
                    }
                }
                category.setSubCategories(subCategories);
                output.add(category);
            }
        }
        return output;
    }

    private static Category fromRow(Row row) {
        Category category = new Category();
        category.id = row.getLong("id");
        category.name = row.getString("name");
        category.parent = row.getLong("parent");       
        return category;
    }
}
