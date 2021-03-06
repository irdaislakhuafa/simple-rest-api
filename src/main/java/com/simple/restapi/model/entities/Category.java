package com.simple.restapi.model.entities;

import javax.persistence.*;

import com.simple.restapi.model.entities.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity @Table(name = "categories")
@Data @NoArgsConstructor @AllArgsConstructor
public class Category extends BaseEntity<String> implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;
}
