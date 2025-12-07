
package ${{values.java_package_name}}.messaging;


import ${{values.java_package_name}}.model.Order;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class OrderProducer {

    @Inject @Channel("orders-out")
    Emitter<Record<UUID,Order>> emitter;

    public void sendOrderToKafka(Order order) {
        emitter.send(Record.of(UUID.randomUUID(), order));

    }
}
