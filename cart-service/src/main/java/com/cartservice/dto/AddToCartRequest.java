package com.cartservice.dto;

import java.math.BigDecimal;

public class AddToCartRequest {
    private Long productId;
    private Long brandId;
    private Integer quantity;
    private BigDecimal price;

//    public AddToCartRequest(long productId, long brandId, Integer quantity, BigDecimal price) {
//        this.productId = productId;
//        this.brandId = brandId;
//        this.quantity = quantity;
//        this.price = price;
//    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
