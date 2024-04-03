package rw.dyna.ecommerce.v1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.CheckValidCode;
import rw.dyna.ecommerce.v1.dtos.ForgotPasswordDto;
import rw.dyna.ecommerce.v1.dtos.LoginDto;
import rw.dyna.ecommerce.v1.dtos.ResetPassword;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.payloads.JWTAuthenticationResponse;
import rw.dyna.ecommerce.v1.security.JwtTokenProvider;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.servicesImpl.MailService;
import rw.dyna.ecommerce.v1.utils.Utility;

import javax.mail.MessagingException;
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

    @Autowired
    public AuthController(IUserServices userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto signInDTO) {
        String jwt = null;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getEmail(),signInDTO.getPassword()));
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwt = jwtTokenProvider.generateToken(authentication);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        return ResponseEntity.ok(ApiResponse.success(new JWTAuthenticationResponse(jwt)));
    }

//    @PostMapping("/forgot-password")
//    public ResponseEntity<ApiResponse> forgot_password(@Valid @RequestBody ForgotPasswordDto dto) {
//        User user = userService.getUserByEmail(dto.getEmail());
//        user.setActivationCode(Utility.randomUUID(6,0,'N'));
//        this.userService.save(user);
//        mailService.sendResetPassword(user);
//        return ResponseEntity.ok(new ApiResponse(true, "Password reset email sent successfully"));
//    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordDto dto) {
        User user = userService.getUserByEmail(dto.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found"));
        }

        // Generate JWT token
        String token = jwtTokenProvider.generateTokenWithUser(user);

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


