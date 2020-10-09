package com.chapter5;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.json.Json;
import javax.json.JsonObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.MethodOrderer.*;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class OrderEndPointTest
{

    @Test
    @Order(1)
    void testGetCustomersEndPoint()
    {
        // Test GET
        given()
                .when()
                .get("/customers")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    @Test
    @Order(2)
    public void testCreateNewOrderEndPoint()
    {
        // Create a JSON Object for the Order
        JsonObject objOrder = Json.createObjectBuilder()
                .add("item", "bike")
                .add("price", Long.valueOf(100))
                .build();
        // Test POST Order for Customer #1
        given()
                .contentType("application/json")
                .body(objOrder.toString())
                .when()
                .post("/orders/1")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(3)
    void testUpdateOrderEndPoint()
    {
        // Create new JSON for Order #1
        JsonObject objOrder = Json.createObjectBuilder()
                .add("id", Long.valueOf(1))
                .add("item", "mountain bike")
                .add("price", Long.valueOf(100))
                .build();

        // Test UPDATE Order #1
        given()
                .contentType("application/json")
                .body(objOrder.toString())
                .when()
                .put("/orders")
                .then()
                .statusCode(204);


    }

    @Test
    @Order(4)
    void testGetOrderEndPoint()
    {
        // Test GET for Order #1
        given()
                .when()
                .get("/orders/1")
                .then()
                .statusCode(200)
                .body(containsString("mountain bike"));

    }

    @Test
    @Order(5)
    void testDeleteOrderEndPoint()
    {
        // Test DELETE Order #1
        given()
                .when()
                .delete("/orders/1")
                .then()
                .statusCode(204);

    }
}
