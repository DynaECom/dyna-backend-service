package rw.dyna.ecommerce.v1.controllers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IIllustrationService;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/illustration")
@CrossOrigin
public class IllustrationController {

    private final IIllustrationService illustrationService;

    public IllustrationController(IIllustrationService illustrationService) {
        this.illustrationService = illustrationService;
    }

    @PostMapping(value="/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createIllustration(@Valid @RequestParam("description") String description, @RequestParam("name") String name, @RequestParam("file") MultipartFile file, @RequestParam("id") UUID id){
        CreateIllustrationDto dto = new CreateIllustrationDto(description, name);
        return ResponseEntity.ok().body(ApiResponse.success(illustrationService.createIllustration(dto, file, id)));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity deleteIllustration(@Valid @RequestParam UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(illustrationService.removeIllustration(id)));
    }

    @PutMapping(path = "/update")
    public ResponseEntity updateIllustration(@Valid @RequestParam("description") String description, @RequestParam("name") String name, @RequestParam("file") MultipartFile file, @RequestParam("id") UUID id){
        CreateIllustrationDto dto = new CreateIllustrationDto(description, name);
        return ResponseEntity.ok().body(ApiResponse.success(illustrationService.updateIllustration(id, dto, file)));
    }
}
