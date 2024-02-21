package rw.dyna.ecommerce.v1.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyEmailDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String activationCode;
}
