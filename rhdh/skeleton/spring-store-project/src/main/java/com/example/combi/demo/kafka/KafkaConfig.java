package com.example.combi.demo.kafka;

import com.example.combi.demo.model.Order;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration

// Class
public class KafkaConfig {

    @Value( "${spring.kafka.bootstrap-servers}" )
    private String boostrap;

    @Value( "${spring.kafka.consumer.group-id}" )
    private String group;

    @Bean
    public ConsumerFactory<String, Order> ordersConsumerFactory() {

        // Creating a map of string-object type
        Map<String, Object> config = new HashMap<>();

        // Adding the Configuration
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                boostrap);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,
                group);
        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                String.class);
        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        // Returning message in JSON format
        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),
                new JsonDeserializer<>(Order.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Order> ordersListner()
    {
        ConcurrentKafkaListenerContainerFactory<
                String, Order> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ordersConsumerFactory());
        return factory;
    }

}