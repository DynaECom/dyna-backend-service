package rw.dyna.ecommerce.v1.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.PlaceOrderDTO;
import rw.dyna.ecommerce.v1.enums.EOrderStatus;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IOrderService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> placeOrder(@Valid @RequestBody() PlaceOrderDTO dto){
        return ResponseEntity.ok(new ApiResponse(true,orderService.placeOrder(dto) , "Order placed successfully"));
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllOrders(){
        return ResponseEntity.ok(new ApiResponse(true, orderService.getAllOrders(), "Orders retrieved successfully"));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse> getAllClientsPaginated(@PathVariable("limit") int limit, @PathVariable("page") int page){
        Pageable pageable = PageRequest.of(page, limit);
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(new ApiResponse(true, orderService.getOrderById(id), "Order retrieved successfully"));
    }

    @GetMapping("/filter/by-client/{clientId}")
    public ResponseEntity<ApiResponse> getByClientId(@PathVariable("clientId") UUID clientId){
        return ResponseEntity.ok(new ApiResponse(true, orderService.getOrdersByClient(clientId), "Orders retrieved successfully"));
    }
    @GetMapping("/filter/by-status/{status}")
    public ResponseEntity<ApiResponse> filterByStatus(@RequestParam("clientId") EOrderStatus status){
        return ResponseEntity.ok(new ApiResponse(true, orderService.getOrdersByStatus(status), "Orders retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(new ApiResponse(true, orderService.cancelOrder(id), "Order deleted successfully"));
    }
}
