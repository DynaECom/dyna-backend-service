package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CartProductDTO;
import rw.dyna.ecommerce.v1.dtos.CreateCartDTO;
import rw.dyna.ecommerce.v1.enums.ECartStatus;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.*;
import rw.dyna.ecommerce.v1.repositories.ICartRepository;
import rw.dyna.ecommerce.v1.services.ICartService;
import rw.dyna.ecommerce.v1.services.IClientService;
import rw.dyna.ecommerce.v1.services.IProductService;
import rw.dyna.ecommerce.v1.services.IUserServices;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements ICartService {
    private final ICartRepository cartRepository;
    private final IProductService productService;

    private final IClientService clientService;

    private final IUserServices userServices;

    public CartServiceImpl(ICartRepository cartRepository, IProductService productService, IClientService clientService, IUserServices userServices){
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.clientService = clientService;
        this.userServices = userServices;
    }

    @Override
    public Cart createCart(CreateCartDTO dto) {
        Cart cart = new Cart();
        User user = userServices.getLoggedInUser();
        Client client = clientService.getClientById(user.getId());
        cart.setClient(client);
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
    public Cart addProduct(CartProductDTO dto) {
        System.out.println("problem check one");
        User user = userServices.getLoggedInUser();
        Client client = clientService.getClientById(user.getId());
        Product product = productService.getProductById(dto.getProductId());
        Cart cart = client.getCart();
        List<CartProduct> cartProductList = new ArrayList<>();
        if(cart != null){
            cartProductList = cart.getCartProducts();
        }else{
            cart = new Cart();
        }
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setCart(cart);
        cartProduct.setQuantity(dto.getQuantity());
        cartProduct.setTotalPrice(dto.getQuantity() * ((product.getPrice()* product.getDiscount())/100));
        cartProductList.add(cartProduct);
        cart.setCartProducts(cartProductList);
        System.out.println("Problem check 3");
        System.out.println(cartRepository.save(cart));
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProduct(UUID productId) {
        User user = userServices.getLoggedInUser();
        Client client = clientService.getClientById(user.getId());
        Cart cart = cartRepository.findByClient(client);
        CartProduct cartProduct = cart.getCartProducts().stream().filter(cp -> cp.getProduct().getId().equals(productId)).findFirst().orElseThrow(()->new ResourceNotFoundException("CartProduct", "productId", productId.toString()));
        List<CartProduct> cartProductList = cart.getCartProducts();
        cartProductList.remove(cartProduct);
        cart.setCartProducts(cartProductList);
        return cartRepository.save(cart);
    }


    @Override
    public Cart getCartByClientId(UUID clientId) {
        Client client = clientService.getClientById(clientId);
        return cartRepository.findByClient(client);
    }

    @Override
    public Cart getCartByLoggedInUser() {
        User user = userServices.getLoggedInUser();
        Client client = clientService.getClientById(user.getId());
        return cartRepository.findByClient(client);
    }
}
