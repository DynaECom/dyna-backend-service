package rw.dyna.ecommerce.v1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.enums.EUserStatus;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;


import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadByUserId(UUID id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByUsername(String s) throws BadRequestException{
        User user = userRepository.findByEmailOrPhoneNumber(s, s).orElseThrow(() -> new UsernameNotFoundException("user not found with email or mobile of " + s));
        if (user.getStatus() == EUserStatus.WAIT_EMAIL_VERIFICATION)
            throw new BadRequestException("You must verify your email to continue with the app, visit your email");
        else if (user.getStatus() == EUserStatus.DEACTIVATED)
            throw new BadRequestException("Your account is deactivated ask the re activation");
        else if (user.getStatus() == EUserStatus.ACTIVE)
            return new UserSecurityDetails(user);
        else
            throw new BadRequestException("Invalid user type");

    }
}
