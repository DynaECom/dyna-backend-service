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

    @PostMapping("/create/{productId}")
    public ResponseEntity<ApiResponse> createManufacturer(@Valid @RequestPart("description") String description, @RequestPart("name") String name, @RequestPart("file") MultipartFile file) throws Exception {
        CreateManufacturerDto createManufacturerDto =  new CreateManufacturerDto(file, description, name);
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.createManufacturer(createManufacturerDto), "Added manufacturer successfully!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateManufacturer(@Valid @RequestPart("description") String description, @RequestPart("name") String name, @RequestPart("file") MultipartFile file, @PathVariable("id") UUID id) throws Exception {
        CreateManufacturerDto dto = new CreateManufacturerDto(file, description, name);
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.updateManufacturer(id, dto)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteManufacturer(@PathVariable("id") UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.removeManufacturer(id)));
    }

    @GetMapping("get")
    public ResponseEntity<ApiResponse> getManufacturers(){
        return ResponseEntity.ok().body(ApiResponse.success(manufacturerService.getManufacturers()));
    }
}
