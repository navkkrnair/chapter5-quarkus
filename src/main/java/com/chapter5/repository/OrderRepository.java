package com.chapter5.repository;

import com.chapter5.OrderEndPoint;
import com.chapter5.entity.Customer;
import com.chapter5.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class OrderRepository
{
    private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);

    @Inject
    EntityManager entityManager;

    public List<Order> findAll(Long customerId)
    {
        log.info("Finding all orders for Customer {}", customerId);
        return (List<Order>)
                entityManager.createNamedQuery("Order.findAll")
                        .setParameter("customerId", customerId)
                        .getResultList();
    }

    public Order findOrderById(Long id)
    {
        log.info("Finding Order for id {}", id);
        Order order = entityManager.find(Order.class, id);
        if (order == null)
        {
            log.info("No Order - Throwing Exception");
            throw new WebApplicationException("Order with id of " + id + " does not exist.", 404);
        }
        return order;
    }

    @Transactional
    public void updateOrder(Order order)
    {
        log.info("Updating {}", order);
        Order orderToUpdate = findOrderById(order.getId());
        orderToUpdate.setItem(order.getItem());
        orderToUpdate.setPrice(order.getPrice());
    }

    @Transactional
    public void createOrder(Order order, Customer c)
    {
        log.info("Creating {} for {}", order, c);
        order.setCustomer(c);
        entityManager.persist(order);
    }

    @Transactional
    public void deleteOrder(Long orderId)
    {
        log.info("OrderRepository: Deleting Order {}", orderId);
        Order o = findOrderById(orderId);
        entityManager.remove(o);
    }


}
