package rw.dyna.ecommerce.v1.dtos;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDTO {
    public UUID productId;
    public int quantity;
}
