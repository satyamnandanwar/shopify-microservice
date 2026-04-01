package com.productservice.repository;

import com.productservice.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}