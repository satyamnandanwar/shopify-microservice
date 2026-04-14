package com.cartservice.service;

import com.cartservice.dto.AddToCartRequest;
import com.cartservice.entity.Cart;
import com.cartservice.entity.CartItem;
import com.cartservice.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addToCart(String uuid, AddToCartRequest request) {
        Cart cart;

        // Check UUID
        if (uuid == null || uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString();

            cart = new Cart();
            cart.setUuid(uuid);

        } else {
            String finalUuid = uuid;
            cart = cartRepository.findByUuid(uuid)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUuid(finalUuid);
                        return newCart;
                    });
        }

        // TODO: add product logic here

        // 2. Check if product already exists
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(request.getProductId());
            newItem.setBrandId(request.getBrandId());
            newItem.setQuantity(request.getQuantity());
            newItem.setPrice(request.getPrice());
            newItem.setCart(cart);

            cart.getCartItems().add(newItem);
        }

        return cartRepository.save(cart);
    }
    // ✅ Get Cart by UUID
    public Cart getCartByUuid(String uuid) {
        return cartRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    // ✅ Clear Cart
    public void clearCart(String uuid) {
        Cart cart = getCartByUuid(uuid);

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }
}
