package com.redhat.demo.mcombi;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
@QuarkusTest
public class OrderResourceTest{

    @Test
    public void testOrderEndpoint() {
        given() 
        .when()
        .get("/order")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

}

