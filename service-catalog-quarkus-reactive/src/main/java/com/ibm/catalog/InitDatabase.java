package com.ibm.catalog;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.common.annotation.Blocking;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;
//mport java.util.concurrent.TimeUnit;

@ApplicationScoped
public class InitDatabase {
  
    @Inject    
    io.vertx.mutiny.pgclient.PgPool client;

    @PostConstruct
    //@Blocking
    public void config() {
        initDb();
        addCategories();
        addProducts();
        addProductCategories();
    }

    //@Blocking
    private void initDb() {
        System.out.println("Quarkus reactive - initDB");

        client.query("DROP TABLE IF EXISTS product").execute()
                .flatMap(r -> {
                    System.out.println("Niklas 1");
                    return client.query ("CREATE TABLE product (id SERIAL PRIMARY KEY, price DECIMAL(14,2) NOT NULL, name TEXT NOT NULL, description TEXT NOT NULL, image TEXT NOT NULL)").execute();
                })
                .await().indefinitely()
                ;

        client.query("DROP TABLE IF EXISTS category").execute()
                .flatMap(r -> client.query ("CREATE TABLE category (id SERIAL PRIMARY KEY, name TEXT NOT NULL, parent SERIAL)").execute())
                .await().indefinitely()
                ;

        client.query("DROP TABLE IF EXISTS productcategory").execute()
                .flatMap(r -> client.query ("CREATE TABLE productcategory (id SERIAL PRIMARY KEY, productid SERIAL, categoryid SERIAL)").execute())
                .await().indefinitely()
                ;        
    }    

    //private static int MAXIMAL_DURATION = 5000;

    //@Blocking
    private void addCategories() {
        addCategoryAndWait(Long.valueOf(1), "Entertainment", Long.valueOf(0));  
        addCategoryAndWait(Long.valueOf(2), "Movies", Long.valueOf(1));  
        addCategoryAndWait(Long.valueOf(3), "Music", Long.valueOf(1)); 
        addCategoryAndWait(Long.valueOf(4), "Games", Long.valueOf(1)); 
        addCategoryAndWait(Long.valueOf(5), "Electronics", Long.valueOf(0)); 
        addCategoryAndWait(Long.valueOf(6), "TV", Long.valueOf(5)); 
        addCategoryAndWait(Long.valueOf(7), "Cellphones", Long.valueOf(5)); 
        addCategoryAndWait(Long.valueOf(8), "DVD Players", Long.valueOf(5));     
    }

    //@Blocking
    private void addProducts() {
        addProductAndWait(Long.valueOf(1), new BigDecimal("29.99"), "Return of the Jedi", "Episode 6, Luke has the final confrontation with his father!", "images/Return.jpg");  
        addProductAndWait(Long.valueOf(2), new BigDecimal("29.99"), "Empire Strikes Back", "Episode 5, Luke finds out a secret that will change his destiny", "images/Empire.jpg");
        addProductAndWait(Long.valueOf(3), new BigDecimal("29.99"), "New Hope", "Episode 4, after years of oppression, a band of rebels fight for freedom", "images/NewHope.jpg");
        addProductAndWait(Long.valueOf(10), new BigDecimal("100.00"), "DVD Player", "This Sony Player has crystal clear picture", "images/Player.jpg");
        addProductAndWait(Long.valueOf(20), new BigDecimal("200.00"), "BlackBerry Curve", "This BlackBerry offers rich PDA functions and works with Notes.", "images/BlackBerry.jpg");
        addProductAndWait(Long.valueOf(21), new BigDecimal("150.00"), "Sony Ericsson", "This Sony phone takes pictures and plays Music.", "images/SonyPhone.jpg");
        addProductAndWait(Long.valueOf(30), new BigDecimal("1800.00"), "Sony Bravia", "This is a 40 inch 1080p LCD HDTV", "images/SonyTV.jpg");
        addProductAndWait(Long.valueOf(31), new BigDecimal("1150.00"), "Sharp Aquos", "This is 32 inch 1080p LCD HDTV", "images/SamTV.jpg");
        addProductAndWait(Long.valueOf(40), new BigDecimal("20.00"), "Go Fish: Superstar", "Go Fish release their great new hit, Superstar", "images/Superstar.jpg");
        addProductAndWait(Long.valueOf(41), new BigDecimal("20.00"), "Ludwig van Beethoven", "This is a classic, the 9 Symphonies Box Set", "images/Bet.jpg");
        addProductAndWait(Long.valueOf(51), new BigDecimal("399.99"), "PlayStation 3", "Brace yourself for the marvels of the PlayStation 3 complete with 80GB hard disk storage", "images/PS3.jpg");
        addProductAndWait(Long.valueOf(52), new BigDecimal("169.99"), "Nintendo Wii", "Next-generation gaming with a motion-sensitive controller", "images/wii.jpg");
        addProductAndWait(Long.valueOf(53), new BigDecimal("299.99"), "XBOX 360", "Expand your horizons with the gaming and multimedia capabilities of this incredible system", "images/xbox360.jpg");            
    }    

    //@Blocking
    private void addProductCategories() {
        addProductCategoryAndWait(Long.valueOf(1), Long.valueOf(1), Long.valueOf(2)); 
        addProductCategoryAndWait(Long.valueOf(2), Long.valueOf(2), Long.valueOf(2)); 
        addProductCategoryAndWait(Long.valueOf(3), Long.valueOf(3), Long.valueOf(2)); 
        addProductCategoryAndWait(Long.valueOf(4), Long.valueOf(10), Long.valueOf(8));  
        addProductCategoryAndWait(Long.valueOf(6), Long.valueOf(20), Long.valueOf(7)); 
        addProductCategoryAndWait(Long.valueOf(7), Long.valueOf(21), Long.valueOf(7)); 
        addProductCategoryAndWait(Long.valueOf(8), Long.valueOf(30), Long.valueOf(6)); 
        addProductCategoryAndWait(Long.valueOf(9), Long.valueOf(31), Long.valueOf(6)); 
        addProductCategoryAndWait(Long.valueOf(10), Long.valueOf(40), Long.valueOf(4)); 
        addProductCategoryAndWait(Long.valueOf(11), Long.valueOf(41), Long.valueOf(3)); 
        addProductCategoryAndWait(Long.valueOf(21), Long.valueOf(51), Long.valueOf(4)); 
        addProductCategoryAndWait(Long.valueOf(22), Long.valueOf(52), Long.valueOf(4)); 
        addProductCategoryAndWait(Long.valueOf(23), Long.valueOf(53), Long.valueOf(4));         
    }

    private void addProductAndWait(Long id, BigDecimal price, String name, String description, String image) {
        try {
            addProduct(id, price, name, description, image);
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
    }

    private void addCategoryAndWait(Long id, String name, Long parent) {
        try {
            addCategory(id, name, parent);
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
    }

    private void addProductCategoryAndWait(Long id, Long productId, Long categoryId) {
        try {
            addProductCategory(id, productId, categoryId);
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
    }
    
    public CompletionStage<Product> addProduct(Long id, BigDecimal price, String name, String description, String image) {
        Product product = new Product();
        product.name = name;
        product.price = price;
        product.description = description;
        product.image = image;
        
        return client.preparedQuery("INSERT INTO product (id, price, name, description, image) VALUES ($1, $2, $3, $4, $5) RETURNING (id)").execute(Tuple.of(id, price, name, description, image))
            // to be done: change to Mutiny
            //.toCompletableFuture()
            //.orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
            .onItem().transform(pgRowSet -> {
                product.id = ((RowSet<Row>) pgRowSet).iterator().next().getLong("id");
                return product;
            })
            .subscribeAsCompletionStage()
            // to be done: change to Mutiny
            //.exceptionally(throwable -> {
            //    System.out.println(throwable);
            //    throw new RuntimeException();
            //})
            ;            
    }

    public CompletionStage<Category> addCategory(Long id, String name, Long parent) {
        Category category = new Category();
        category.name = name;
        category.parent = parent;
        return client.preparedQuery("INSERT INTO category (id, name, parent) VALUES ($1, $2, $3) RETURNING (id)").execute(Tuple.of(id, name, parent))
            //.toCompletableFuture()
            //.orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
            .onItem().transform(pgRowSet -> {
                category.id = pgRowSet.iterator().next().getLong("id");
                return category;
            })
            .subscribeAsCompletionStage()
            //.exceptionally(throwable -> {
            //    System.out.println(throwable);
            //    throw new RuntimeException();
            //})
            ;            
    }

    public CompletionStage<ProductCategory> addProductCategory(Long id, Long productId, Long categoryId) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.id = id;
        productCategory.productid = productId;
        productCategory.categoryid = categoryId;
        
        return client.preparedQuery("INSERT INTO productcategory (id, productid, categoryid) VALUES ($1, $2, $3) RETURNING (id)").execute(Tuple.of(id, productId, categoryId))
            //.toCompletableFuture()
            //.orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
            .onItem().transform(pgRowSet -> {
                productCategory.id = pgRowSet.iterator().next().getLong("id");
                return productCategory;
            })
            .subscribeAsCompletionStage()
            //.exceptionally(throwable -> {
            //    System.out.println(throwable);
            //    throw new RuntimeException();
            //})
            ;            
    }
}
