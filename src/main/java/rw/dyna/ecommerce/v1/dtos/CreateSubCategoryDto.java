package rw.dyna.ecommerce.v1.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@Data
public class CreateSubCategoryDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
