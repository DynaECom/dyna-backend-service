package rw.dyna.ecommerce.v1.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIllustrationDto {

    @Column
    private String description;

    @NotBlank
    private String color;

    public CreateIllustrationDto(String description) {
        this.description = description;
    }
}
