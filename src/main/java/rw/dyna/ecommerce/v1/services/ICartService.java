package rw.dyna.ecommerce.v1.services;

import rw.dyna.ecommerce.v1.dtos.CartProductDTO;
import rw.dyna.ecommerce.v1.dtos.CreateCartDTO;
import rw.dyna.ecommerce.v1.models.Cart;

import java.util.UUID;

public interface ICartService {
    Cart createCart(CreateCartDTO dto);

    Cart getCartById(UUID id);

    Cart addProduct(CartProductDTO dto);

    Cart removeProduct(UUID productId);

    Cart getCartByClientId(UUID clientId);

    Cart getCartByLoggedInUser();
}
