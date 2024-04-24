package rw.dyna.ecommerce.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.ImageModalDTO;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.repositories.ImageRepository;
import rw.dyna.ecommerce.v1.services.ImageService;

import java.util.Map;

@RestController
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> upload(@RequestParam("name") String name, @RequestParam("file")MultipartFile file) {
        try {
            ImageModalDTO imageModalDTO = new ImageModalDTO(file, name);
            return  ResponseEntity.ok(ApiResponse.success(imageService.uploadImage(imageModalDTO), "Image uploaded successfully" ));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
