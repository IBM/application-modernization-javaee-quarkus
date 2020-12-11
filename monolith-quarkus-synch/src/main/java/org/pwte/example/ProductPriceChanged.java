package org.pwte.example;

import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import org.pwte.example.service.CustomerOrderServicesImpl;
import java.util.concurrent.CompletionStage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.inject.Inject;

@ApplicationScoped
public class ProductPriceChanged {

    @PersistenceContext
	protected EntityManager em;

    @Inject
	CustomerOrderServicesImpl customerOrderServices;		

    @Transactional
    @Incoming("product-price-updated")
    public CompletionStage<?> process(String message) {
        System.out.println("Quarkus - Kafka message received: product-price-updated - " + message);
        try {            
            ManagedExecutor executor = ManagedExecutor.builder()
                .maxAsync(10)
                .propagated(ThreadContext.CDI, 
                            ThreadContext.TRANSACTION)
                .build();
            
            ThreadContext threadContext = ThreadContext.builder()
                        .propagated(ThreadContext.CDI, 
                                    ThreadContext.TRANSACTION)
                        .build();

            return executor.runAsync(threadContext.contextualRunnable(() -> {
                customerOrderServices.updateLineItem(message);           
            }));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }                
    }  
}