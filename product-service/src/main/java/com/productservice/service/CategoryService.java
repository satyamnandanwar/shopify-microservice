package com.productservice.service;

import com.productservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    public List<CategoryDto> findAll();
    public CategoryDto findByCategoryId(Integer id);
    public CategoryDto findByCategoryName(String name);
}
