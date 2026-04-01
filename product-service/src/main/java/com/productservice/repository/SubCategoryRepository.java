package com.productservice.repository;

import com.productservice.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
}