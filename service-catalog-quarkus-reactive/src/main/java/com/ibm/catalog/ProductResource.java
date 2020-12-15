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
    public CompletionStage<List<Product>> get() {
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Product invoked in Quarkus reactive catalog service");
        
        String statement = "SELECT id, price, name, description, image FROM product";
        return client.preparedQuery(statement)
                .toCompletableFuture()
                .orTimeout(MAXIMAL_DURATION, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {                    
                    System.out.println(throwable);
                    return null;
                }).thenApply(rows -> {
                    List<Product> products = new ArrayList<>(rows.size());
                    for (Row row : rows) {
                        products.add(fromRow(row));
                    }
                    return products;
                });
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


    /*
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    public Product update(@PathParam("id") Long id, Product updatedProduct) {        
        System.out.println("/CustomerOrderServicesWeb/jaxrs/Product @PUT updateProduct invoked in Quarkus reactive catalog service");

        
        Product existingProduct = entityManager.find(Product.class, id);
        if (existingProduct == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }    
        existingProduct.price = updatedProduct.price;
        
        entityManager.persist(existingProduct);

        //sendMessageToKafka(existingProduct.id, existingProduct.price);

        return existingProduct;	    
        
        return null;
    }
    */

    /*
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
    */
}
