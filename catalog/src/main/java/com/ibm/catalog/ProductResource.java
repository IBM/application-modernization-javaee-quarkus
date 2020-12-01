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
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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

        Product existingProduct = entityManager.find(Product.class, id);
        if (existingProduct == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }    
        existingProduct.price = updatedProduct.price;
        
        entityManager.persist(existingProduct);

        sendMessageToKafka(existingProduct.id, existingProduct.price);

		return existingProduct;	    
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
