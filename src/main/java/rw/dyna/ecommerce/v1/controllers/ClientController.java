package rw.dyna.ecommerce.v1.controllers;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
import java.util.UUID;

@RequestMapping("/api/v1/client")
@RestController
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createClient(@Valid @RequestBody CreateAccountDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(clientService.createClient(dto),"Account created successfully!"));
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllClients(){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(clientService.getAllClients()));
    }

    /*
    * Get all clients paginated
     */
    @GetMapping("/paginated/{limit}/{page}")
    public ResponseEntity<ApiResponse> getAllClientsPaginated(@PathVariable("limit") int limit, @PathVariable("page") int page){
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(clientService.getClientsPaginated(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getClientById(@PathVariable("id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(clientService.getClientById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") UUID id ){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(clientService.deleteClientById(id)));
    }
}
