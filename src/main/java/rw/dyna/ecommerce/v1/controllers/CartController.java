package rw.dyna.ecommerce.v1.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.CartProductDTO;
import rw.dyna.ecommerce.v1.dtos.CreateCartDTO;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.ICartService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-products")
    public ResponseEntity<ApiResponse> addMultipleProducts(@Valid @RequestBody CreateCartDTO dto){
        return ResponseEntity.ok(ApiResponse.success(cartService.createCart(dto), "Added to cart successfully!"));
    }
    @GetMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@Valid CartProductDTO dto){
        return ResponseEntity.ok(ApiResponse.success(cartService.addProduct(dto)));
    }
    @GetMapping("/remove-product/{product-id}")
    public ResponseEntity<ApiResponse> removeProduct(@PathVariable("product-id") UUID id){
        return ResponseEntity.ok(ApiResponse.success(cartService.removeProduct(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(ApiResponse.success(cartService.getCartById(id)));
    }

    @GetMapping("/{client-id}")
    public ResponseEntity<ApiResponse> getCartByClient(@PathVariable("client-id") UUID id){
        return ResponseEntity.ok(ApiResponse.success(cartService.getCartByClientId(id)));
    }
    @GetMapping("/by-logged-in-user")
    public ResponseEntity<ApiResponse> getCartByLoggedInUser(){
        return ResponseEntity.ok(ApiResponse.success(cartService.getCartByLoggedInUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(){
        return null;
    }
}
