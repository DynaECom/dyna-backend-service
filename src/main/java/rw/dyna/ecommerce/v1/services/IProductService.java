package rw.dyna.ecommerce.v1.services;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.models.Product;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    Product createProduct(CreateProductDto dto);

    String removeProduct(UUID id);

    Product updateProduct(UUID id, CreateProductDto dto);

    Product findProductById(UUID id);

    List<Product> getAllProducts();

    Product getProductById(UUID id);

    Product soldout(UUID id);
    String updateIllustration(UUID id, CreateIllustrationDto dto, MultipartFile file) throws Exception;
    Product addIllustrations(MultipartFile[] files, UUID id);
    String removeIllustration(UUID id) throws Exception;
}


