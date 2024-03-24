package rw.dyna.ecommerce.v1.controllers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IAdministratorService;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/administrator")
public class AdministratorController {
    private final IAdministratorService administratorService;

    public AdministratorController(IAdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAdministrator(@RequestBody CreateAccountDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(administratorService.createAdministrator(dto)));
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllAdministrators(){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(administratorService.getAllAdministrators()));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse> getAllAdministratorsPaginated(@PathVariable("limit") int limit, @PathVariable("page") int page){
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(administratorService.getAdministratorPaginated(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAdministratorById(@PathVariable("id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(administratorService.getAdministratorById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success( administratorService.deleteAdministratorById(id)));
    }
}
