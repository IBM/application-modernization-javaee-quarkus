package io.openliberty.guides.system;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductPriceChanged {

    @Incoming("product-price-updated")
    public String process(String message) {
        System.out.println("Kafka message received in Open Liberty Native: product-price-updated - " + message);
        return message;
    }   
}