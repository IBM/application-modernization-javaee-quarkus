package org.pwte.example;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductPriceChanged {

    @Incoming("product-price-updated")
    public String process(String message) {
        System.out.println("Open Liberty - Kafka message received: product-price-updated - " + message);
        return message;
    }   
}