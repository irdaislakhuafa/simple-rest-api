package com.simple.restapi.model.dao;

import com.simple.restapi.model.entities.Category;
import com.simple.restapi.model.entities.Product;
import com.simple.restapi.model.entities.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface ProductDao extends CrudRepository<Product, Long> {
    List<Product> findByNameContains(String name);

    @Query("select products from Product products where products.name = :name")
    List<Product> findProductsByName(@PathParam("name") String name);

    @Query("select products from Product products where products.name like :name")
    List<Product> findProductsByNameLike(@PathParam("name") String name);

    @Query("select products from Product products where products.category.id = :categoryId")
    List<Product> findProductsByCategoryId(@PathParam("categoryId") Long categoryId);

    @Query("select products from Product products where :supplier member of products.suppliers")
    List<Product> findProductsBySupplier(@PathParam("supplier") Supplier supplier);
}
