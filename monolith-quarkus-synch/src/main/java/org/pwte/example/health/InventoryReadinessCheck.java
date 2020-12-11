package org.pwte.example.health;

import java.util.Collection;
import java.util.Properties;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.common.KafkaFuture;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class InventoryReadinessCheck implements HealthCheck {

    private static Logger logger = Logger.getLogger(InventoryReadinessCheck.class.getName());
    
    @Inject
    @ConfigProperty(name = "mp.messaging.connector.liberty-kafka.bootstrap.servers")
    String kafkaServer;
    
    @Inject
    @ConfigProperty(name = "mp.messaging.incoming.product-price-updated.group.id")
    String groupId;
    
    @Override
    public HealthCheckResponse call() {
        boolean up = isReady();
        return HealthCheckResponse.named(this.getClass().getSimpleName()).state(up).build();
    }

    private boolean isReady() {
        AdminClient adminClient = createAdminClient();
        return checkIfBarConsumerGroupRegistered(adminClient);
    }
    
    private AdminClient createAdminClient() {
        Properties connectionProperties = new Properties();
        connectionProperties.put("bootstrap.servers", kafkaServer);
        AdminClient adminClient = AdminClient.create(connectionProperties);
        return adminClient;
    }
    
    private boolean checkIfBarConsumerGroupRegistered(AdminClient adminClient) {
        ListConsumerGroupsResult groupsResult = adminClient.listConsumerGroups();
        KafkaFuture<Collection<ConsumerGroupListing>> consumerGroupsFuture = groupsResult.valid();
        try {
            Collection<ConsumerGroupListing> consumerGroups = consumerGroupsFuture.get();
            for (ConsumerGroupListing g : consumerGroups)
                logger.info("groupId: " + g.groupId());
            return consumerGroups.stream().anyMatch(group -> group.groupId().equals(groupId));
        } catch (Exception e) {
            return false;
        }
    }
}
