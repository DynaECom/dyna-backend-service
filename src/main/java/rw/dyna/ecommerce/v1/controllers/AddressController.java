package rw.dyna.ecommerce.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.CreateAddressDTO;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IAddressService;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/address")
@CrossOrigin
public class AddressController {

    private final IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(path="/create")
    public ResponseEntity<ApiResponse> createAddress(@Valid @RequestBody CreateAddressDTO dto){
        System.out.println("dto");
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(addressService.createAddress(dto)));
    }

    @DeleteMapping(path="/delete")
    public ResponseEntity<ApiResponse> deleteAddress(@Valid @RequestParam UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(addressService.removeAddress(id)));
    }

    @PutMapping(path= "/update")
    public ResponseEntity<ApiResponse> updateAddress(@Valid @RequestBody CreateAddressDTO dto, @RequestParam UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(addressService.updateAddress(id, dto)));
    }

}
