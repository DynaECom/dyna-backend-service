package rw.dyna.ecommerce.v1.dtos;
import lombok.*;
import rw.dyna.ecommerce.v1.enums.Erole;
import rw.dyna.ecommerce.v1.security.ValidPassword;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAccountDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @ValidPassword
    private String password;

}
