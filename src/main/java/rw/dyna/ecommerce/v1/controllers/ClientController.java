package rw.dyna.ecommerce.v1.controllers;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.enums.EUserStatus;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IClientService;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.utils.Mapper;

import javax.validation.Valid;

@RequestMapping("/api/v1/client")
@RestController
public class ClientController {

    private final IUserServices userServices;
    private final IClientService clientService;

    public ClientController(IUserServices userServices, IClientService clientService) {
        this.userServices = userServices;
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createClient(@Valid @RequestBody CreateAccountDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(clientService.createClient(dto)));
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllClients(){
        return null;
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse> getAllClientsPaginated(@PathVariable("limit") int limit, @PathVariable("page") int page){
        Pageable pageable = PageRequest.of(page, limit);
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getClientById(){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(){
        return null;
    }
}
