package rw.dyna.ecommerce.v1.services;

import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.dtos.RegisterAdminDto;
import rw.dyna.ecommerce.v1.dtos.UpdateUserDto;
import rw.dyna.ecommerce.v1.models.Profile;
import rw.dyna.ecommerce.v1.models.User;

public interface IUserServices {

    void validateNewRegistration(User user);
    User registerUser(CreateAccountDto user);
    User registerAdmin(RegisterAdminDto dto);
    User getLoggedInUser();
    Profile getLoggedInProfile();
    User getUserByEmail(String email);
    void verifyEmail(String email, String activationCode);
    String deactivateAccount(String email);

    User updateUserDetails(UpdateUserDto user);

    User deleteAccount(String email , String password);

    User save(User user);

    boolean verifyCode(String email, String activationCode);
}
