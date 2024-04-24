package rw.dyna.ecommerce.v1.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateManufacturerDto;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IManufacturerService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/manufacturers")
@CrossOrigin
public class ManufacturerController {

    private final IManufacturerService manufacturerService;

    public ManufacturerController(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createManufacturer(@Valid @RequestParam("description") String description, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws Exception {
        CreateManufacturerDto createManufacturerDto =  new CreateManufacturerDto(file, description, name);
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.createManufacturer(createManufacturerDto), "Added manufacturer successfully!"));
    }
    @PutMapping("update")
    public ResponseEntity<ApiResponse> updateManufacturer(@Valid @RequestBody CreateManufacturerDto dto, @RequestParam("id") UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.updateManufacturer(id, dto)));
    }

    @DeleteMapping("update")
    public ResponseEntity<ApiResponse> deleteManufacturer(@RequestParam("id") UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.removeManufacturer(id)));
    }

    @GetMapping("get")
    public ResponseEntity<ApiResponse> getManufacturers(){
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.getManufacturers()));
    }
}
