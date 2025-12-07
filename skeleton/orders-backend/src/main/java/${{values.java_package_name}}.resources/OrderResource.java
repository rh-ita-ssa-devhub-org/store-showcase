package ${{values.java_package_name}}.resources;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.redhat.demo.mcombi.messaging.OrderProducer;
import com.redhat.demo.mcombi.model.Order;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.jboss.logging.Logger;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name="orders")
public class OrderResource {

    private final MeterRegistry registry;


    OrderResource(MeterRegistry registry) {
        this.registry = registry;

    }
    @Inject
    OrderProducer producer;
    private final Logger logger = Logger.getLogger(OrderResource.class);
    @POST
    @Operation(summary = "Save an order")
    @APIResponse(
            responseCode = "200",
            description = "Gets all fights, or empty list if none",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Order.class, type = SchemaType.DEFAULT))
    )
    public Response send(Order order) {
        Log.info("Order received");


        if (order.itemCategory.equals("None")){
            logger.error("Unknown category");
            registry.counter("orders_error_total").increment();
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();

        }

        producer.sendOrderToKafka(order);
        // Return an 202 - Accepted response.
        logger.info("request received: " + order.toString());
        registry.counter("orders_processed_request_total").increment();
        return Response.accepted().build();
    }






}

