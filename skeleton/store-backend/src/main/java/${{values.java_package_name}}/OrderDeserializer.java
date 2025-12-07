package ${{values.java_package_name}};

import ${{values.java_package_name}}.model.Order;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderDeserializer extends ObjectMapperDeserializer<Order> {
    public OrderDeserializer() {
        super(Order.class);
    }
}
