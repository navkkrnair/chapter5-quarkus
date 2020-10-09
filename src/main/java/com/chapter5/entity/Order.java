package com.chapter5.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Cacheable
@Data
@NoArgsConstructor
@Entity
@Table(name="OrderTable")
@NamedQuery(name = "Order.findAll",
        query = "SELECT o FROM Order o WHERE o.customer.id = :customerId ORDER BY o.item")

public class Order
{
    @Id
    @SequenceGenerator(
            name = "orderSequence",
            sequenceName = "orderId_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequence")
    public Long id;

    @Column(length = 40)
    public String item;

    @Column
    public Long price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonbTransient
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public Customer customer;


}
