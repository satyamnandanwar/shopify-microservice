package com.productservice.repository;

import com.productservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
}