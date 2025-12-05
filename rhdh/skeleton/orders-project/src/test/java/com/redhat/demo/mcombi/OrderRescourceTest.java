package com.redhat.demo.mcombi;

import com.redhat.demo.mcombi.model.Order;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class OrderRescourceTest {
    @Test
    public void testOrderEndpoint() {
        given()
        .contentType("application/json")
        .body(new Order("desc",1,"test"))
        .when()
        .post("/order")
                .then()
                .statusCode(202)
                ;
    }
}

