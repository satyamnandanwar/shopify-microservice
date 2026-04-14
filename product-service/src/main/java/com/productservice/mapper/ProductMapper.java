package com.productservice.mapper;

import com.productservice.dto.CategoryDto;
import com.productservice.dto.ProductDto;
import com.productservice.entity.Category;
import com.productservice.entity.Product;
import org.modelmapper.ModelMapper;

public class ProductMapper {

    private static final ModelMapper mapper = new ModelMapper();
    public static ProductDto convertProductToDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }
}
