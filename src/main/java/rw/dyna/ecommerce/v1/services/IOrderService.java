package rw.dyna.ecommerce.v1.services;

import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.PlaceOrderDTO;
import rw.dyna.ecommerce.v1.enums.EOrderStatus;
import rw.dyna.ecommerce.v1.models.Order;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    public Order placeOrder(PlaceOrderDTO dto);
    public Order cancelOrder(UUID orderId);
    Order markAsDelivered(UUID orderId);
    Order getOrderById(UUID id);
    public List<Order> getAllOrders();
    public List<Order> getOrdersByClient(UUID clientId);
    public List<Order> getOrdersByStatus(EOrderStatus status);
}
