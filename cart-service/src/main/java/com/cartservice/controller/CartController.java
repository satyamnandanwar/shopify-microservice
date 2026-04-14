package com.cartservice.controller;

import com.cartservice.dto.AddToCartRequest;
import com.cartservice.entity.Cart;
import com.cartservice.service.CartService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ✅ Add to Cart
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestHeader(value = "X-CART-ID", required = false) String uuid,
            @RequestBody AddToCartRequest request
    ) {

        Cart cart = cartService.addToCart(uuid, request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CART-ID", cart.getUuid());

        return ResponseEntity.ok()
                .headers(headers)
                .body("Product added in cart successfully");
    }

    @GetMapping
    public Cart getCart(
            @RequestHeader("X-CART-ID") String uuid
    ) {
        return cartService.getCartByUuid(uuid);
    }

//    // ✅ Get Cart by UUID
//    @GetMapping("/{uuid}")
//    public Cart getCartByUuid(@PathVariable String uuid) {
//        return cartService.getCartByUuid(uuid);
//    }

    // ✅ Clear Cart
    @DeleteMapping("/{uuid}/clear")
    public String clearCart(@PathVariable String uuid) {
        cartService.clearCart(uuid);
        return "Cart cleared successfully";
    }
}