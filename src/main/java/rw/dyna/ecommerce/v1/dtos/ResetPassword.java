package rw.dyna.ecommerce.v1.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ResetPassword {
    @NotBlank
    String email;

    @NotBlank
    String password;
}
