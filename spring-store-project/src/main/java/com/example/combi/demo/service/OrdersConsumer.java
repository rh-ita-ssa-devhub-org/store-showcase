package com.example.combi.demo.service;


import com.example.combi.demo.model.Order;
import com.example.combi.demo.model.OrderEntity;
import com.example.combi.demo.repository.OrderRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
public class OrdersConsumer {
    private final Logger logger = LoggerFactory.getLogger(OrdersConsumer.class);
    @Autowired
    OrderRepository orderRepository;
    @KafkaListener(topics = "orders", containerFactory = "ordersListner")
    public void consume(Order payload) throws IOException {
        logger.info(String.format("#### -&gt; Consumed message -&gt; %s", payload));


        Optional<OrderEntity> oe = orderRepository.findById(payload.id);
        if (oe.isEmpty()) {
            OrderEntity record=new OrderEntity();
            record.description= payload.description;
            record.quantity = payload.quantity;
            record.id= payload.id;
            record.itemCategory=payload.itemCategory;
            orderRepository.save(record);
            logger.info("message persisted successfully");
        }
    }
}
