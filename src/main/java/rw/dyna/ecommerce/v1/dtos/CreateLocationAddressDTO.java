package rw.dyna.ecommerce.v1.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateLocationAddressDTO {

    @NotBlank
    public String country;

    @NotBlank
    public String province;

    @NotBlank
    public String district;

    @NotBlank
    public String sector;

    @NotBlank
    public String village;
    @NotBlank
    public String streetName;

    public String cell;
}
