package rw.dyna.ecommerce.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    @Email
    public String email;

    @NotBlank
    @Pattern(regexp = "^\\+2507[1-8]\\d{7}$", message = "Invalid Rwandan phone number")
    public String phoneNumber;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;
}
