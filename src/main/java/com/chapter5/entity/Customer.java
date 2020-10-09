package com.chapter5.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Cacheable
@Data
@NoArgsConstructor
@Entity
@NamedQuery(name = "Customer.findAll",
        query = "SELECT c FROM Customer c ORDER BY c.id",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

public class Customer
{
    @Id
    @SequenceGenerator(
            name = "customerSequence",
            sequenceName = "customerId_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSequence")
    private Long id;

    private String name;

    @Column(length = 40)
    private String surname;

    @OneToMany(mappedBy = "customer")
    @JsonbTransient
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public List<Order> orders;

}
