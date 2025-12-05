package com.redhat.demo.mcombi;
import com.redhat.demo.mcombi.model.Order;
import com.redhat.demo.mcombi.model.OrderEntity;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class OrderConsumer {

    private final Logger logger = Logger.getLogger(OrderConsumer.class);

    @Incoming("orders")
    @Transactional
    public void receive(Record<UUID, Order> record) {
        logger.infof("message received now");

        OrderEntity oe = OrderEntity.findById(record.value().id);
        if (oe==null){
            oe=new OrderEntity();
   //         oe.id=record.value().id;
            oe.description=record.value().description;
            oe.quantity=record.value().quantity;
            oe.itemCategory=record.value().itemCategory;
            oe.persist();
            logger.infof("message persisted successfully");
        }


    }
}