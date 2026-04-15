package com.productservice.mapper;

import com.productservice.dto.ProductDto;
import com.productservice.entity.Brand;
import com.productservice.entity.Image;
import com.productservice.entity.Product;
import com.productservice.entity.Size;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static ProductDto convertProductToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());

        if (product.getSubCategory() != null) {
            ProductDto.SubCategorySummaryDto subCategoryDto = new ProductDto.SubCategorySummaryDto();
            subCategoryDto.setId(product.getSubCategory().getId());
            subCategoryDto.setName(product.getSubCategory().getName());
            dto.setSubCategory(subCategoryDto);
        }

        List<ProductDto.BrandDto> brandDtos = new ArrayList<>();
        for (Brand brand : product.getBrands()) {
            ProductDto.BrandDto brandDto = new ProductDto.BrandDto();
            brandDto.setId(brand.getId());
            brandDto.setName(brand.getName());
            brandDto.setPrice(brand.getPrice());

            List<ProductDto.SizeDto> sizeDtos = new ArrayList<>();
            for (Size size : brand.getSizes()) {
                ProductDto.SizeDto sizeDto = new ProductDto.SizeDto();
                sizeDto.setId(size.getId());
                sizeDto.setSize(size.getSize());
                sizeDto.setQuantity(size.getQuantity());
                sizeDtos.add(sizeDto);
            }
            brandDto.setSizes(sizeDtos);

            List<ProductDto.ImageDto> imageDtos = new ArrayList<>();
            for (Image image : brand.getImages()) {
                ProductDto.ImageDto imageDto = new ProductDto.ImageDto();
                imageDto.setId(image.getId());
                imageDto.setUrl(image.getUrl());
                imageDtos.add(imageDto);
            }
            brandDto.setImages(imageDtos);
            brandDtos.add(brandDto);
        }

        dto.setBrands(brandDtos);
        return dto;
    }
}
