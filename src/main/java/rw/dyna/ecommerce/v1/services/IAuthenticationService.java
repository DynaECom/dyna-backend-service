package rw.dyna.ecommerce.v1.services;

import rw.dyna.ecommerce.v1.dtos.LogOutDTO;
import rw.dyna.ecommerce.v1.dtos.LoginDto;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.payloads.LoginResponse;

public interface IAuthenticationService {
    LoginResponse login(LoginDto dto);
    boolean verifyAccount(String email, String code);
    User resendVerificationCode(String email);

    void logout(LogOutDTO dto);
}
