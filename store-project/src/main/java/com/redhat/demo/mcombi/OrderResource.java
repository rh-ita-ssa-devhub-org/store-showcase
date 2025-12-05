package com.redhat.demo.mcombi;

import com.redhat.demo.mcombi.model.Order;
import com.redhat.demo.mcombi.model.OrderEntity;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
/*import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;*/

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("/order")
@Produces(MediaType.APPLICATION_JSON)

@OpenAPIDefinition(
        tags = {
                @Tag(name="orders", description="orders operations.")

        },
        info = @Info(
                title="Example API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Example API Support",
                        url = "http://exampleurl.com/contact",
                        email = "techsupport@example.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class OrderResource {

    private final MeterRegistry registry;


    OrderResource(MeterRegistry registry) {
        this.registry = registry;

    }

    @Operation(summary = "List of orders", operationId = "orderList")

    @GET
    public List<OrderEntity> getAllOrders(){

        Log.info("Order List required");
        registry.counter("orders_list_request_total").increment();
        return OrderEntity.findAllOrderEntity();

    }

 //   @DELETE
  /*  @Operation(summary = "Save an order")
    @APIResponse(
            responseCode = "200",
            description = "Gets all fights, or empty list if none",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Order.class, type = SchemaType.DEFAULT))
    )*/
/*
    @Transactional
    public void deleteOrders(){
        OrderEntity.deleteAll();
    }
*/

}
