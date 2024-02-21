package rw.dyna.ecommerce.v1.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateManufacturerDto  {
    @NotBlank
    private String name;

    private String description;
}
