package rw.dyna.ecommerce.v1.servicesImpl;

import rw.dyna.ecommerce.v1.dtos.CartProductDTO;
import rw.dyna.ecommerce.v1.dtos.CreateCartDTO;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.enums.ECartStatus;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Cart;
import rw.dyna.ecommerce.v1.models.CartProduct;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.Product;
import rw.dyna.ecommerce.v1.repositories.ICartRepository;
import rw.dyna.ecommerce.v1.services.ICartService;
import rw.dyna.ecommerce.v1.services.IClientService;
import rw.dyna.ecommerce.v1.services.IProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartServiceImpl implements ICartService {
    private final ICartRepository cartRepository;
    private final IProductService productService;

    private final IClientService clientService;

    public CartServiceImpl(ICartRepository cartRepository, IProductService productService, IClientService clientService){
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.clientService = clientService;
    }

    @Override
    public Cart createCart(CreateCartDTO dto, UUID id) {
        Cart cart = new Cart();
        Client client = clientService.getClientById(id);
        cart.setClient(client);
        cart.setStatus(ECartStatus.OPEN);
        cart = cartRepository.save(cart);
        List<CartProduct> cartProductList = new ArrayList<>();
        for(CartProductDTO cartProductDto : dto.getCartProductDTOList()){
            Product product = productService.getProductById(cartProductDto.getProductId());
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setQuantity(cartProductDto.getQuantity());
            cartProduct.setTotalPrice(((product.getPrice() * product.getDiscount()/100) * cartProductDto.getQuantity()));
            cartProduct.setCart(cart);
            cartProductList.add(cartProduct);
        }
        cart.setCartProducts(cartProductList);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(UUID id) {
        return cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart", "id", id.toString()));
    }

    @Override
    public Cart addProduct(CartProductDTO dto, UUID id) {
        Product product = productService.getProductById(id);
        Cart cart = cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart", "id", id.toString()));
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        return null;
    }

    @Override
    public Cart removeProduct(UUID productId, UUID cartId) {
        return null;
    }

    @Override
    public Cart cancelCart(UUID id) {
        return null;
    }

    @Override
    public Cart getCartByClientId(UUID clientId) {
        return null;
    }

    @Override
    public Cart getCartByLoggedInUser() {
        return null;
    }
}
