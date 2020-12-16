package com.ibm.catalog;

import io.vertx.axle.sqlclient.Tuple;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.vertx.axle.sqlclient.Row;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Path("/CustomerOrderServicesWeb/jaxrs/Product")
@ApplicationScoped
@Produces("application/json")
public class ProductResource {

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
    public CompletionStage<List<Product>> getProductsByCategory(@QueryParam(value="categoryId") int categoryId) {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Product invoked in Quarkus reactive catalog service");
        
        CompletionStage<List<Product>> products = getProducts();
        CompletionStage<List<ProductCategory>> productCategories = getProductCategories();  
        Long categoryIdLong = new Long(categoryId);      

        return products
            .thenCombine(productCategories, (productsRead, productCategoriesRead) -> {
                List<Product> output = new ArrayList<Product>(); 
                List<ProductCategory> productCategoriesFiltered = productCategoriesRead.stream().filter(productCategoryRead -> {
                    return productCategoryRead.categoryid.equals(categoryIdLong);
                })
                .collect(Collectors.toList());                           
                
                productCategoriesFiltered.forEach(productCategoryFiltered -> {
                    productsRead.forEach(productRead -> {
                        if (productRead.id.equals(productCategoryFiltered.productid)) {
                            output.add(productRead);
                        }
                    });
                });                
                return output;
            });
    }

    public CompletionStage<List<Product>> getProducts() {
        String statement = "SELECT id, price, name, description, image FROM product";
        return client.preparedQuery(statement)
                .toCompletableFuture()
                .orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {                    
                    System.out.println(throwable);
                    return null;
                })
                .thenApply(rows -> {
                    List<Product> products = new ArrayList<>(rows.size());
                    rows.forEach(row -> products.add(fromRow(row)));
                    return products;
                });
    }

    public CompletionStage<List<ProductCategory>> getProductCategories() {        
        String statement = "SELECT id, productid, categoryid FROM productcategory";
        return client.preparedQuery(statement)
                .toCompletableFuture()
                .orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {                    
                    System.out.println(throwable);
                    return null;
                }).thenApply(rows -> {
                    List<ProductCategory> productCategories = new ArrayList<>(rows.size());
                    rows.forEach(row -> productCategories.add(fromRowProductCategory(row)));
                    return productCategories;
                });
    }

    private static ProductCategory fromRowProductCategory(Row row) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.id = row.getLong("id");
        productCategory.productid = row.getLong("productid");
        productCategory.categoryid = row.getLong("categoryid");
        return productCategory;
    }

    private static Product fromRow(Row row) {
        Product product = new Product();
        product.id = row.getLong("id");
        product.price = row.getBigDecimal("price");
        product.name = row.getString("name");
        product.description = row.getString("description");
        product.image = row.getString("image");
        return product;
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    public CompletionStage<Product> update(@PathParam("id") Long id, Product updatedProduct) {        
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Product @PUT updateProduct invoked in Quarkus reactive catalog service");

        String statement = "UPDATE product SET price = $1 WHERE ID = $2";
        return client.preparedQuery(statement, Tuple.of(updatedProduct.price, id))
                .toCompletableFuture()
                .orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {                    
                    System.out.println(throwable);
                    return null;
                }).thenApply(rows -> {
                    sendMessageToKafka(id, updatedProduct.price);  
                    return updatedProduct;
                });              
    }
    
    @ConfigProperty(name = "kafka.bootstrap.servers")
    String kafkaBootstrapServer;

    @Inject
    Vertx vertx;

    private KafkaProducer<String, String> producer;

    @PostConstruct
    void initKafkaClient() {
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", kafkaBootstrapServer);
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        System.out.println("bootstrapping Kafka with config: " + config);

        producer = KafkaProducer.create(vertx, config);
    }

    public void sendMessageToKafka(Long productId, BigDecimal price) {
        String productIdString = productId.toString();
        String priceString = price.toString();
        try {
            KafkaProducerRecord<String, String> record = KafkaProducerRecord.create("product-price-updated", productIdString + "#" + priceString);
            producer.write(record, done -> System.out.println("Kafka message sent: product-price-updated - " + productIdString + "#" + priceString));
        } catch (Exception e) {
        }
    }
}
