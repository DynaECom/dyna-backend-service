package rw.dyna.ecommerce.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartDTO {

    private List<CartProductDTO> cartProductDTOList;
}
