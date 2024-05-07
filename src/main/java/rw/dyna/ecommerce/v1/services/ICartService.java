package rw.dyna.ecommerce.v1.services;

import rw.dyna.ecommerce.v1.dtos.CreateCartDTO;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.models.Cart;

import java.util.UUID;

public interface ICartService {
    Cart createCart(CreateCartDTO dto, UUID id);

    Cart getCartById(UUID id);

    Cart addProduct(CreateProductDto dto, UUID id);

    Cart removeProduct(UUID productId, UUID cartId);

    Cart cancelCart(UUID id);

    Cart getCartByClientId(UUID clientId);

    Cart getCartByLoggedInUser();
}
