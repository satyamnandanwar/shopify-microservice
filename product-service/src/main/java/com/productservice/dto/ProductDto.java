package com.productservice.dto;

import com.productservice.entity.Brand;
import com.productservice.entity.SubCategory;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class ProductDto {
    private Integer id;
    private String name;

    private SubCategory subCategory;

    private Set<Brand> brands = new LinkedHashSet<>();

    public Set<Brand> getBrands() {
        return brands;
    }

    public void setBrands(Set<Brand> brands) {
        this.brands = brands;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
