package com.productservice.repository;

import com.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = """
        SELECT p.*
        FROM products p
        INNER JOIN sub_category sc ON sc.id = p.sub_category_id
        INNER JOIN brand b ON b.product_id = p.id
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY p.id
        """, nativeQuery = true)
    List<Product> searchProducts(@Param("keyword") String keyword);
}