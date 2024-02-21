package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Manufacturer;
import rw.dyna.ecommerce.v1.models.Product;
import rw.dyna.ecommerce.v1.models.SubCategory;
import rw.dyna.ecommerce.v1.repositories.IProductRepository;
import rw.dyna.ecommerce.v1.repositories.ISubCategoriesRepository;
import rw.dyna.ecommerce.v1.services.IManufacturerService;
import rw.dyna.ecommerce.v1.services.IProductService;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    private final IManufacturerService manufacturerService;
    private final ISubCategoriesRepository subCategoriesRepository;
    private final IProductRepository productRepository;
    public ProductServiceImpl(IManufacturerService manufacturerService, ISubCategoriesRepository subCategoriesRepository, IProductRepository productRepository) {

        this.manufacturerService = manufacturerService;
        this.subCategoriesRepository = subCategoriesRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(CreateProductDto dto) {
        Manufacturer manufacturer = manufacturerService.findManufacturerById(dto.getManufacturer());
        SubCategory subCategory = subCategoriesRepository.findById(dto.getSubCategory()).orElseThrow(()->new ResourceNotFoundException("Sub category"));
        Product product = new Product(dto, manufacturer, subCategory);
        productRepository.save(product);
        return product;
    }

    @Override
    public String removeProduct(UUID id) {
        productRepository.deleteById(id);
        return "Successfully removed data!";
    }

    @Override
    public Product updateProduct(UUID id, CreateProductDto dto) {
        Product product =  this.findProductById(id);
        SubCategory category = subCategoriesRepository.findById(dto.getSubCategory()).orElseThrow(()-> new ResourceNotFoundException("subCategory"));
        Manufacturer manufacturer = manufacturerService.findManufacturerById(dto.getManufacturer());
        Product newProduct = new Product(dto, manufacturer, category);
        product.setBrand(newProduct.getBrand());
        product.setManufacturer(newProduct.getManufacturer());
        product.setCategory(newProduct.getCategory());
        product.setPrice(newProduct.getPrice());
        product.setCompany(newProduct.getCompany());
        product.setName(newProduct.getName());
        product.setCrossed_price(newProduct.getCrossed_price());
        product.setDiscount(newProduct.getDiscount());
        product.setInStock(newProduct.getInStock());
        product.setWarranty(newProduct.getWarranty());
        product.setCategory(newProduct.getCategory());
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(UUID id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product"));
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product"));
        return product;
    }

    @Override
    public Product soldout(UUID id) {
        Product product = getProductById(id);
        product.setInStock(0);
        productRepository.save(product);
        return product;
    }

}
