package com.simple.restapi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

@Entity @Table(name = "suppliers")
@Data @NoArgsConstructor @AllArgsConstructor
public class Supplier implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @ManyToMany(mappedBy = "suppliers")
    private Set<Product> products;
}
