package com.productservice.controller;

import com.productservice.dto.ApiResponse;
import com.productservice.dto.CategoryDto;
import com.productservice.dto.ProductDto;
import com.productservice.service.CategoryService;
import com.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategories() {
        List<CategoryDto> categoriesDto = categoryService.findAll();
        ApiResponse<List<CategoryDto>> response = new ApiResponse<>();
        if(categoriesDto!=null){
            response.setData(categoriesDto);
            response.setMessage("All Categories fetched successfully");
            response.setStatus(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setData(null);
        response.setMessage("No Data fetched");
        response.setStatus(500);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/list/search")
    public ResponseEntity<ApiResponse<List<ProductDto>>> searchProducts(
            @RequestParam String keyword
    ) {

        List<ProductDto> productDtos = productService.searchProducts(keyword);
        ApiResponse<List<ProductDto>> response = new ApiResponse<>();
        if(productDtos!=null){
            response.setData(productDtos);
            response.setMessage("All Products fetched successfully");
            response.setStatus(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setData(null);
        response.setMessage("No Data fetched");
        response.setStatus(500);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}


