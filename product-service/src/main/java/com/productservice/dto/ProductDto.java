package com.productservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    private Integer id;
    private String name;
    private SubCategorySummaryDto subCategory;
    private List<BrandDto> brands = new ArrayList<>();

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

    public SubCategorySummaryDto getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategorySummaryDto subCategory) {
        this.subCategory = subCategory;
    }

    public List<BrandDto> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandDto> brands) {
        this.brands = brands;
    }

    public static class SubCategorySummaryDto {
        private Integer id;
        private String name;

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

    public static class BrandDto {
        private Integer id;
        private String name;
        private BigDecimal price;
        private List<SizeDto> sizes = new ArrayList<>();
        private List<ImageDto> images = new ArrayList<>();

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

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public List<SizeDto> getSizes() {
            return sizes;
        }

        public void setSizes(List<SizeDto> sizes) {
            this.sizes = sizes;
        }

        public List<ImageDto> getImages() {
            return images;
        }

        public void setImages(List<ImageDto> images) {
            this.images = images;
        }
    }

    public static class SizeDto {
        private Integer id;
        private String size;
        private String quantity;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

    public static class ImageDto {
        private Integer id;
        private String url;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
