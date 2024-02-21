package rw.dyna.ecommerce.v1.servicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.dtos.RegisterAdminDto;
import rw.dyna.ecommerce.v1.dtos.UpdateUserDto;
import rw.dyna.ecommerce.v1.enums.EUserStatus;
import rw.dyna.ecommerce.v1.enums.Erole;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;
import rw.dyna.ecommerce.v1.services.IRoleService;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.utils.Utility;


import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserServices {

    @Value("${admin_registration_key}")
    private String adminRegistrationKey;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IUserRepository userRepository;
    private final IRoleService  roleService;
    private final MailService mailService;


    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, IUserRepository userRepository, IRoleService roleService, MailService mailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
    }

    public void validateNewRegistration(User user){
        if(isNotUnique(user)){
            throw new BadRequestException(String.format("User with email '%s' or phone number '%s' already exists", user.getEmail(), user.getPhoneNumber()));
        }
    }

    public boolean isNotUnique(User user){
        Optional<User> userOptional = this.userRepository.findByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber());
        return userOptional.isPresent();
    }

    @Override
    public User registerUser(CreateAccountDto dto) {
        User user = new User();
        String encodePassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Role role = roleService.findByName(Erole.USER);
        user.setEmail(dto.getEmail());
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(encodePassword);
        user.setRoles(Collections.singleton(role));

        if(userRepository.existsByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber())){
            throw new BadRequestException(String.format("User with email '%s' or phone number '%s' already exists", user.getEmail(), user.getPhoneNumber()));
        }
//        try{
//            mailService.sendAccountVerificationEmail(user);
//        }catch (MessagingException e){
//            System.out.println(e);
//        }
        return userRepository.save(user);
    }

    @Override
    public User registerAdmin(RegisterAdminDto dto) {
        if(!dto.getKey().equals(adminRegistrationKey))
            throw new BadRequestException("Invalid registration key!");

        User user = new User();
        String encodePassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Role role = roleService.findByName(Erole.ADMIN);

        user.setEmail(dto.getEmail());
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setPassword(encodePassword);
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setStatus(EUserStatus.ACTIVE);
        user.setRoles(Collections.singleton(role));

        validateNewRegistration(user);

        return userRepository.save(user);
    }

    @Override
    public User getLoggedInUser(){
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser")
                throw new BadRequestException("You are not logged in, try to log in");
            String email;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else {
                email = principal.toString();
            }
            return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
        }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User" ,"email", email));
    }

    @Override
    public void verifyEmail(String email, String activationCode) {
        User user = getUserByEmail(email);
        if (user.getStatus() != EUserStatus.WAIT_EMAIL_VERIFICATION)
            throw new BadRequestException("Your account is " + user.getStatus().toString().toLowerCase(Locale.ROOT));

        if(Objects.equals(user.getActivationCode(), activationCode)){
            user.setStatus(EUserStatus.ACTIVE);
            user.setActivationCode(Utility.randomUUID(6, 0, 'N'));
            userRepository.save(user);
        }else{
            throw new BadRequestException("The provided code is invalid");
        }
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Override
    public String deactivateAccount(String email) {
        User user = getUserByEmail(email);
        user.setActivationCode(email);
        userRepository.save(user);
        return "Account with email " + email + " has been deactivated!";
    }

    @Override
    public User updateUserDetails(UpdateUserDto userdto){
        User user = getLoggedInUser();
       user.setEmail(userdto.getEmail());
       user.setFirstName(userdto.getFirstName());
       user.setLastName(userdto.getLastName());
       user.setPhoneNumber(userdto.getPhoneNumber());
       userRepository.save(user);
       return user;
    }





    @Override
    public User deleteAccount(String email, String password) {
        User user = getUserByEmail(email);
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            userRepository.delete(user);
            return user;
        }
        throw new BadRequestException("Could not perform task!");
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public boolean verifyCode(String email, String activationCode) {

        if(userRepository.existsByEmailAndActivationCode(email, activationCode)){
            return true;
        }else{
            throw new BadRequestException("Invalid code!");
        }
    }


}
