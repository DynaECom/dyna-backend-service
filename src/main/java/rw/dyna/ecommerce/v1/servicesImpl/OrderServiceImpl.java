package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.PlaceOrderDTO;
import rw.dyna.ecommerce.v1.enums.EOrderStatus;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.models.Cart;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.Order;
import rw.dyna.ecommerce.v1.repositories.ICartRepository;
import rw.dyna.ecommerce.v1.repositories.IOrderRepository;
import rw.dyna.ecommerce.v1.services.IClientService;
import rw.dyna.ecommerce.v1.services.IOrderService;
import rw.dyna.ecommerce.v1.services.IProductService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    private final IProductService productService;
    private final IClientService clientService;
    private final IOrderRepository orderRepository;

    private final ICartRepository cartRepository;


    public OrderServiceImpl(IProductService productService, IClientService clientService, IOrderRepository orderRepository, ICartRepository cartRepository) {
        this.productService = productService;
        this.clientService = clientService;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Order placeOrder(PlaceOrderDTO dto) {
        Cart cart = cartRepository.findById(dto.getCartId()).orElseThrow(() -> new BadRequestException("Error occured! Cart not found"));
        Order order = new Order();
        order.setShippingCost(dto.getShippingCost());
        order.setNote(dto.getNote());
        order.setStatus(EOrderStatus.PLACED);
        String uniqueIdentifier = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);
        order.setCode("ORD-" + timestamp + "-" + uniqueIdentifier);
        order.setCart(cart);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(UUID orderId) {
        Order order = this.getOrderById(orderId);
        if(order.getStatus() == EOrderStatus.DELIVERED){
            throw new BadRequestException("Oops, failed to cancel! Order already delivered");
        }
        return orderRepository.save(order);
    }

    @Override
    public Order markAsDelivered(UUID orderId) {
        return null;
    }

    @Override
    public Order getOrderById(UUID id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Error occured! Order not found"));
        return order;
    }
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByClient(UUID clientId) {
        Client client = clientService.getClientById(clientId);
        return orderRepository.findByCart_Client(client);
    }

    @Override
    public List<Order> getOrdersByStatus(EOrderStatus status) {
        return orderRepository.findByStatus(status);
    }
}
