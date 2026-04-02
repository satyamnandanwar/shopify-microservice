package com.productservice.controller;

import com.productservice.dto.ApiResponse;
import com.productservice.dto.CategoryDto;
import com.productservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final CategoryService categoryService;
    public ProductController(CategoryService categoryService) {
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
}


