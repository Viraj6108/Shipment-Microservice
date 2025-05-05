package com.shipment.shipment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class topic {

    @Bean
    public NewTopic shipment()
    {
        return TopicBuilder.name("shipment-shipped").build();
    }
    @Bean
    public NewTopic shipmentCancelled(){return TopicBuilder.name("shipment-cancelled").build();}

    @Bean
    public NewTopic shipmentDelivered (){return TopicBuilder.name("shipment-delivered").build();}

    @Bean
    public NewTopic orderShipment (){return TopicBuilder.name("shipment-order").build();}
}
