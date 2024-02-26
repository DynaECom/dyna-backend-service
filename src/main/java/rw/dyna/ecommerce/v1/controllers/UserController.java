package rw.dyna.ecommerce.v1.controllers;
import io.swagger.annotations.Api;
import org.codehaus.groovy.classgen.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.*;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IUserServices;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/api/v1/users")
@CrossOrigin
public class UserController {
    private final IUserServices userService;

    @Autowired
    UserController(IUserServices userService){
        this.userService = userService;
    }

    @PostMapping(path="/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody CreateAccountDto user){
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userService.registerUser(user), "User registered successfully!"));

    }

    @PostMapping(path="/register-admin")
    public ResponseEntity<ApiResponse>  registerAdmin(@Valid @RequestBody RegisterAdminDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userService.registerAdmin(dto), "Admin account created successfully!"));
    }

    @PostMapping(path = "/verify-email")
    public ResponseEntity<ApiResponse> verifyAccount(@Valid @RequestBody VerifyEmailDto dto){
        userService.verifyEmail(dto.getEmail(), dto.getActivationCode());
        return ResponseEntity.ok(new ApiResponse(true, "Email verification successfull"));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getProfile(){
        User user = userService.getLoggedInUser();
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @DeleteMapping(path="/delete-account")
    public ResponseEntity<ApiResponse> deleteAccount(@Valid @RequestBody DeleteAccountDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.deleteAccount(dto.getEmail(), dto.getPassword())));
    }

    @PutMapping(path= "/update-account")
    public ResponseEntity<ApiResponse> updateAccount(@Valid @RequestBody UpdateUserDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.updateUserDetails(dto)));
    }
}
