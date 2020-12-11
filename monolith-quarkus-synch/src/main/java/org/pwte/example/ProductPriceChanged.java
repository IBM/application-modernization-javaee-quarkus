package org.pwte.example;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import org.pwte.example.service.CustomerOrderServicesImpl;
import javax.inject.Inject;

@ApplicationScoped
public class ProductPriceChanged {

    @Inject
	CustomerOrderServicesImpl customerOrderServices;		

    @Incoming("product-price-updated")
    public String process(String message) {
        System.out.println("Open Liberty - Kafka message received: product-price-updated - " + message);
        String productId = "";
        String newPrice = "0";
        try {
            productId = message.substring(0, message.indexOf("#"));
            newPrice = message.substring(message.indexOf("#") + 1, message.length());
            customerOrderServices.updateLineItem(productId, newPrice);
        }
        catch (Exception e) {}        
        return message;
    }   
}