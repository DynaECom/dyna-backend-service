package rw.dyna.ecommerce.v1.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createClient(){
        return null;
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
