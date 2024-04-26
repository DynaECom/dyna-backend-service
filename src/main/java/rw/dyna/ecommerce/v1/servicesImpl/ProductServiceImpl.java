package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Illustration;
import rw.dyna.ecommerce.v1.models.Manufacturer;
import rw.dyna.ecommerce.v1.models.Product;
import rw.dyna.ecommerce.v1.models.SubCategory;
import rw.dyna.ecommerce.v1.repositories.IIllustrationRepository;
import rw.dyna.ecommerce.v1.repositories.IProductRepository;
import rw.dyna.ecommerce.v1.repositories.ISubCategoriesRepository;
import rw.dyna.ecommerce.v1.services.ICloudinaryService;
import rw.dyna.ecommerce.v1.services.IManufacturerService;
import rw.dyna.ecommerce.v1.services.IProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    private final IManufacturerService manufacturerService;
    private final ISubCategoriesRepository subCategoriesRepository;
    private final IIllustrationRepository illustrationRepository;
    private final ICloudinaryService cloudinaryService;
    private final IProductRepository productRepository;
    public ProductServiceImpl(IManufacturerService manufacturerService, ISubCategoriesRepository subCategoriesRepository, IIllustrationRepository illustrationRepository, ICloudinaryService cloudinaryService, IProductRepository productRepository) {
        this.manufacturerService = manufacturerService;
        this.subCategoriesRepository = subCategoriesRepository;
        this.illustrationRepository = illustrationRepository;
        this.cloudinaryService = cloudinaryService;
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(CreateProductDto dto) {
        Manufacturer manufacturer = manufacturerService.findManufacturerById(dto.getManufacturer());
        System.out.println("category: " + dto.getCategory());
        System.out.println("sub-category: " + dto.getSub_category());

        List<SubCategory> subCategories = null;

        for(UUID id: dto.getSub_category()){
            SubCategory subCategory = subCategoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Sub category"));
            subCategories.add(subCategory);
        }
        Product product = new Product(dto, manufacturer, subCategories);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product addIllustrations(MultipartFile[] files, UUID id){
        List<Illustration> illustrations = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file -> {
            try {
                CreateIllustrationDto dto = null;
                if(file.getOriginalFilename().split("-").length > 1) {
                    String[] data = file.getOriginalFilename().split("-");
                    dto = new CreateIllustrationDto(data[0], data[1]);
                }else{
                    String[] data = file.getOriginalFilename().split("-");
                    dto = new CreateIllustrationDto(data[0]);
                }
                Illustration illustration = new Illustration(dto.getColor(), dto.getDescription());

                illustrations.add(illustrationRepository.save(illustration));
                cloudinaryService.uploadImage(file, "illustrations");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Product product = this.getProductById(id);
        product.setIllustrations(illustrations);
        return productRepository.save(product);
    }

    @Override
    public String removeProduct(UUID id) {
        productRepository.deleteById(id);
        return "Successfully removed data!";
    }

    @Override
    public Product updateProduct(UUID id, CreateProductDto dto) {
        Product product =  this.findProductById(id);
        List<SubCategory> subCategories = new ArrayList<>();

        for(UUID subCategoryId : dto.getSub_category()){
            SubCategory subCategory = subCategoriesRepository.findById(subCategoryId).orElseThrow(()->new ResourceNotFoundException("Sub category"));
            subCategories.add(subCategory);
        }

        Manufacturer manufacturer = manufacturerService.findManufacturerById(dto.getManufacturer());
        Product newProduct = new Product(dto, manufacturer, subCategories);
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
