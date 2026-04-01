package com.productservice.service;

import com.productservice.dto.CategoryDto;
import com.productservice.entity.Category;
import com.productservice.mapper.CategoryMapper;
import com.productservice.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAll() {
        //returning the Category but not the DTO, so we need to convert it through mapper
        //adding dependency to mapper.
        //Java8/Stream API to convert List to List of DTO
        //public List<CategoryDto> findAll() {
        //
        //        // Step 1: Fetch all categories
        //        List<Category> categories = categoryRepository.findAll();
        //
        //        // Step 2: Convert using Stream API
        //        return categories.stream()
        //                .map(CategoryMapper::convertCategoryToDto)
        //                .collect(Collectors.toList());
        //    }
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> dtoList = new ArrayList<>();
        for(Category c:categories){
            CategoryDto categoryDto = CategoryMapper.convertCategoryToDto(c);
            dtoList.add(categoryDto);
        }
        return dtoList;

    }

    @Override
    public CategoryDto findByCategoryId(Integer id) {
        return null;
    }

    @Override
    public CategoryDto findByCategoryName(String name) {
        return null;
    }
}
