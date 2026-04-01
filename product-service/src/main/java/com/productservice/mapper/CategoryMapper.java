package com.productservice.mapper;

import com.productservice.dto.CategoryDto;
import com.productservice.entity.Category;
import org.modelmapper.ModelMapper;

public class CategoryMapper {

    private static final ModelMapper mapper = new ModelMapper();
    public static CategoryDto convertCategoryToDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }
}
