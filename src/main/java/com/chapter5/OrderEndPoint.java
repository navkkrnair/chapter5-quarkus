package com.chapter5;

import com.chapter5.entity.Customer;
import com.chapter5.entity.Order;
import com.chapter5.repository.CustomerRepository;
import com.chapter5.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("orders")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class OrderEndPoint
{
    private static final Logger log = LoggerFactory.getLogger(OrderEndPoint.class);

    @Inject
    OrderRepository orderRepository;
    @Inject
    CustomerRepository customerRepository;

    @GET
    @Path("/{customerId}")
    public List<Order> getAll(@PathParam("customerId") Long customerId)
    {
        log.info("Getting all Orders for Customer id {}", customerId);
        return orderRepository.findAll(customerId);
    }

    @POST
    @Path("/{customerId}")
    public Response create(Order order, @PathParam("customerId") Long customerId)
    {
        log.info("Creating new {} for Customer id {}", order, customerId);
        Customer c = customerRepository.findCustomerById(customerId);
        orderRepository.createOrder(order, c);
        return Response.status(201)
                .build();
    }

    @PUT
    public Response update(Order order)
    {
        log.info("Updating {}", order);
        orderRepository.updateOrder(order);
        return Response.status(204)
                .build();
    }

    @DELETE
    @Path("/{order}")
    public Response delete(@PathParam("order") Long orderId)
    {
        log.info("Deleting Order {}", orderId);
        orderRepository.deleteOrder(orderId);
        return Response.status(204)
                .build();
    }


}
