package rw.dyna.ecommerce.v1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.*;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.payloads.LoginResponse;
import rw.dyna.ecommerce.v1.security.JwtTokenProvider;
import rw.dyna.ecommerce.v1.services.IAuthenticationService;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.servicesImpl.MailService;
import rw.dyna.ecommerce.v1.utils.ExceptionUtils;
import rw.dyna.ecommerce.v1.utils.Utility;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final IUserServices userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthController(IUserServices userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService, IAuthenticationService authenticationService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto signInDTO) {

        try {
            // Call the login service method
            LoginResponse loginResponse = authenticationService.login(signInDTO);
            // Return a successful response
            return new ResponseEntity<>(new ApiResponse(
                    true,
                    "Login successful",
                    loginResponse
            ), HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping(path = "/logout")
    public ResponseEntity<ApiResponse> logout(@Valid @RequestBody LogOutDTO dto){
        try {
            // Call the logout service method
            authenticationService.logout(dto);
            // Return a successful response
            return new ResponseEntity<>(new ApiResponse(
                    true,
                    "Logout successful"
            ), HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordDto dto) {
        User user = userService.getUserByEmail(dto.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found"));
        }

        // Generate JWT token
        String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());

        // Save token to user entity, if necessary
        user.setResetPasswordToken(token);
        userService.save(user);

        // Send reset password email with JWT token
        mailService.sendResetPassword(user);

        return ResponseEntity.ok(new ApiResponse(true, "Password reset email sent successfully"));
    }
    @PostMapping(path="/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPassword dto){
        String token = dto.getResetToken();
        String email = dto.getEmail();

        // Validate token
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid or expired reset token"));
        }


        // Extract email from token
        String tokenEmail = jwtTokenProvider.getUserEmailFromToken(token);
        System.out.println(tokenEmail);
        System.out.println("id: " + jwtTokenProvider.getUserIdFromToken(token));
        // Ensure the token email matches the request email
        if (!email.equals(tokenEmail)) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email address mismatch"));
        }

        // Retrieve user by email
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found"));
        }

        // Update password and activation code
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setActivationCode(Utility.randomUUID(6, 0, 'N'));
        userService.save(user);

        return ResponseEntity.ok(new ApiResponse(true,"Password successfully reset"));
    }

    }


