package rw.dyna.ecommerce.v1.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.security.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAdminDto {
    @NotBlank
    @Email
    private String  email;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @ValidPassword
    private String password;


    private String key;

    @NotBlank
    private String phoneNumber;

}
