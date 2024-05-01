package rw.dyna.ecommerce.v1.dtos;

import lombok.*;
import rw.dyna.ecommerce.v1.models.SubCategory;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCategoryDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Set<CreateSubCategoryDto> subCategories;
}
