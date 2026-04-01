package com.productservice.repository;

import com.productservice.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
}