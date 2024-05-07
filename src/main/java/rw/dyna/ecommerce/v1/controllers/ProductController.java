package rw.dyna.ecommerce.v1.controllers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.payloads.ApiResponse;
import rw.dyna.ecommerce.v1.services.IProductService;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct( @Valid @RequestBody CreateProductDto dto){
        return ResponseEntity.ok().body(ApiResponse.success(productService.createProduct(dto), "Product created successfully!"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(productService.removeProduct(id)));
    }
    @PostMapping(value="/illustration/add/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity addIllustration(@RequestPart("files") MultipartFile[] files, @PathVariable("productId") UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(productService.addIllustrations(files, id)));
    }

    @DeleteMapping(path = "/illustration/delete/{id}")
    public ResponseEntity deleteIllustration(@Valid @PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok().body(ApiResponse.success(productService.removeIllustration(id)));
    }
    @GetMapping("/illustrations/all")
    public ResponseEntity<ApiResponse> getAllIllustrations(){
        return ResponseEntity.ok().body(ApiResponse.success(productService.getAllIllustrations()));
    }
    @PutMapping(path = "/illustration/update/{id}")
    public ResponseEntity updateIllustration(@Valid @RequestPart("color") String color, @Valid @RequestPart("description") String description, @RequestPart("file") MultipartFile file, @PathVariable("id") UUID id) throws Exception {
        CreateIllustrationDto dto = new CreateIllustrationDto(description, color);
        return ResponseEntity.ok().body(ApiResponse.success(productService.updateIllustration(id, dto, file)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") UUID id, @Valid @RequestBody CreateProductDto dto){
        return ResponseEntity.ok().body(ApiResponse.success(productService.updateProduct(id, dto)));
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getProduct(@RequestParam UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(productService.getProductById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        return ResponseEntity.ok().body(ApiResponse.success(productService.getAllProducts()));
    }


}
