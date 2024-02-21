package rw.dyna.ecommerce.v1.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ApiResponse> createProduct(@RequestBody CreateProductDto dto){
        return ResponseEntity.ok().body(ApiResponse.success(productService.createProduct(dto), "Product created successfully!"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(productService.removeProduct(id)));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestParam UUID id, @Valid @RequestBody CreateProductDto dto){
        return ResponseEntity.ok().body(ApiResponse.success(productService.updateProduct(id, dto)));
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getProduct(@RequestParam UUID id){
        return ResponseEntity.ok().body(ApiResponse.success(productService.getProductById(id)));
    }
}
