package rw.dyna.ecommerce.v1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@NoArgsConstructor
@Data
public class UpdateSubCategoryDTO {

    @NotBlank
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
