package com.ibm.catalog;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class ProductPriceChanged {

    @Incoming("product-price-updated")
    @Outgoing("stream-product-price-updated")
    @Broadcast
    public String process(String message) {
        System.out.println("Kafka message received: product-price-updated - " + message);
        return message;
    }   
}