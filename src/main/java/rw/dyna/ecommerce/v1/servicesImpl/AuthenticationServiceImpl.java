package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.LogOutDTO;
import rw.dyna.ecommerce.v1.dtos.LoginDto;
import rw.dyna.ecommerce.v1.enums.EUserStatus;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.payloads.LoginResponse;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;
import rw.dyna.ecommerce.v1.security.CustomUserDetailsService;
import rw.dyna.ecommerce.v1.security.JwtTokenProvider;
import rw.dyna.ecommerce.v1.security.UserAuthority;
import rw.dyna.ecommerce.v1.security.UserSecurityDetails;
import rw.dyna.ecommerce.v1.services.IAuthenticationService;
import rw.dyna.ecommerce.v1.utils.ExceptionUtils;
import rw.dyna.ecommerce.v1.utils.Hash;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;



    public AuthenticationServiceImpl(IUserRepository userRepository, CustomUserDetailsService customUserDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginResponse login(LoginDto loginDto){
        try {
            User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new BadRequestException("User with provided email not found"));
            if(Hash.isTheSame(loginDto.getPassword() , user.getPassword())){
                if(user.getStatus().equals(EUserStatus.ACTIVE)){
                    UserSecurityDetails userSecurityDetails = (UserSecurityDetails) customUserDetailsService.loadUserByUsername(loginDto.getEmail());
                    List<GrantedAuthority> grantedAuthorities = userSecurityDetails.getGrantedAuthorities();
                    if(grantedAuthorities.isEmpty()){
                        throw new BadRequestException("User has no role");
                    }
                    UserAuthority userAuthority = (UserAuthority) grantedAuthorities.get(0);
                    String role = userAuthority.getAuthority();
                    String token = jwtTokenProvider.createToken(user.getId(), loginDto.getEmail() , role);
                    return new LoginResponse(token , user , user.getRoles());
                }else{
                    if(user.getStatus().equals(EUserStatus.DEACTIVATED)){
                        throw new BadRequestException("Please accept your invitation to activate your account");
                    }else{
                        throw new BadRequestException("Account is not active");
                    }
                }
            }else{
                throw new BadRequestException("Incorrect Email or Password");
            }
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
//            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean verifyAccount(String email, String code) {
        return false;
    }

    @Override
    public User resendVerificationCode(String email) {
        return null;
    }

    @Override
    public void logout(LogOutDTO dto) {
        try{

        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
        }
    }
}
