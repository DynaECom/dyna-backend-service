package rw.dyna.ecommerce.v1.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIllustrationDto {
private String description;

@NotBlank
private String color;
}
