package rw.dyna.ecommerce.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {
    private String productId;
    private String userId;
    private String review;
    private int rating;
}
