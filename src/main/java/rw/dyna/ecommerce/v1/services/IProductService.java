package rw.dyna.ecommerce.v1.services;
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

}


