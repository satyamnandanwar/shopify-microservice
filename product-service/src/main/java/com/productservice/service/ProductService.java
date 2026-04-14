package com.productservice.service;

import com.productservice.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> searchProducts(String keyword);
}
