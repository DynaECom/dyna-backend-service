package rw.dyna.ecommerce.v1.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.*;
import rw.dyna.ecommerce.v1.models.Profile;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IUserServices;
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

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(){
        Profile profile = userService.getLoggedInProfile();
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @DeleteMapping(path="/delete-user")
    public ResponseEntity<ApiResponse> deleteUser(@Valid @RequestBody DeleteAccountDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.deleteAccount(dto.getEmail(), dto.getPassword())));
    }

    @PutMapping(path= "/update-user")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UpdateUserDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.updateUserDetails(dto)));
    }
}
