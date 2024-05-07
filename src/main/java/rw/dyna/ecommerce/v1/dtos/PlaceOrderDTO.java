package rw.dyna.ecommerce.v1.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class PlaceOrderDTO {
    private UUID cartId;
    private String note;
    private long shippingCost;
}
