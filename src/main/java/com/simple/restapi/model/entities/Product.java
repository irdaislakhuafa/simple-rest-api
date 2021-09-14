package com.simple.restapi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity @Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Column(length = 100, nullable = false)
    private String name;

    @NotEmpty(message = "Descriptioin is required")
    @Column(length = 1000) 
    private String description;

    private double price;

    @ManyToOne
    private Category category;

    @ManyToMany @JoinTable(
            name = "products_suppliers",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers;
}
