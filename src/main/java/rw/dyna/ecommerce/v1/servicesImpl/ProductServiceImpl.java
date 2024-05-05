package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.dtos.ImageUploadDTO;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.*;
import rw.dyna.ecommerce.v1.repositories.ICategoriesRepository;
import rw.dyna.ecommerce.v1.repositories.IIllustrationRepository;
import rw.dyna.ecommerce.v1.repositories.IProductRepository;
import rw.dyna.ecommerce.v1.repositories.ISubCategoriesRepository;
import rw.dyna.ecommerce.v1.services.ICategoryService;
import rw.dyna.ecommerce.v1.services.ICloudinaryService;
import rw.dyna.ecommerce.v1.services.IManufacturerService;
import rw.dyna.ecommerce.v1.services.IProductService;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {
    private final IManufacturerService manufacturerService;
    private final ISubCategoriesRepository subCategoriesRepository;
    private final IIllustrationRepository illustrationRepository;
    private final ICloudinaryService cloudinaryService;
    private final IProductRepository productRepository;
    private final ICategoriesRepository categoriesRepository;

    @Value("${uploads.directory.illustrations}")
    private String directory;
    public ProductServiceImpl(IManufacturerService manufacturerService, ISubCategoriesRepository subCategoriesRepository, IIllustrationRepository illustrationRepository, ICloudinaryService cloudinaryService, IProductRepository productRepository, ICategoryService categoryService, ICategoriesRepository categoriesRepository) {
        this.manufacturerService = manufacturerService;
        this.subCategoriesRepository = subCategoriesRepository;
        this.illustrationRepository = illustrationRepository;
        this.cloudinaryService = cloudinaryService;
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public Product createProduct(CreateProductDto dto) {
        Manufacturer manufacturer = manufacturerService.findManufacturerById(dto.getManufacturer());

        Set<SubCategory> subCategories = new HashSet<>();
        Set<Category> categories = new HashSet<>();

        for(UUID id: dto.getCategory()){
            Category category = categoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Sub category"));
            categories.add(category);
        }
        for(UUID id: dto.getSub_category()){
            SubCategory subCategory = subCategoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Sub category"));
            subCategories.add(subCategory);
        }

        Product product = new Product(dto, manufacturer, subCategories, categories);

        productRepository.save(product);
        return product;
    }

    @Override
    public Product addIllustrations(MultipartFile[] files, UUID id){
        List<Illustration> illustrations = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file -> {
            try {
                CreateIllustrationDto dto;
                if(file.getOriginalFilename().split("-").length > 1) {
                    String[] data = file.getOriginalFilename().split("-");
                    dto = new CreateIllustrationDto(data[0], data[1]);
                }else{
                    String[] data = file.getOriginalFilename().split("-");
                    dto = new CreateIllustrationDto(data[0]);
                }
                Illustration illustration = new Illustration(dto.getColor(), dto.getDescription());
                illustration.setProduct(getProductById(id));
                ImageUploadDTO imageUploadDTO = cloudinaryService.uploadImage(file, "illustrations");
                illustration.setImageUrl(imageUploadDTO.getImageUrl());
                illustration.setPublic_Id(imageUploadDTO.getPublicId());

                illustrations.add(illustrationRepository.save(illustration));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Product product = this.getProductById(id);
        product.setIllustrations(illustrations);
        return productRepository.save(product);
    }

    public String removeIllustration(UUID id) throws Exception {
        try {
            Illustration illustration = illustrationRepository.findById(id).get();
            if (illustration == null) {
                throw new BadRequestException("Illustration not found!");
            }
//            cloudinaryService.deleteImage(illustration.getPublic_Id());
            illustrationRepository.deleteById(id);
            return "Successfully removed data!";
        }catch(Exception e){
            e.printStackTrace();
            throw new BadRequestException("Illustration not found!");
        }
    }

    @Override
    public List<Illustration> getAllIllustrations(){
        return illustrationRepository.findAll();
    }
    @Override
    public String removeProduct(UUID id) {
        productRepository.deleteById(id);
        return "Successfully removed data!";
    }

    @Override
    public Product updateProduct(UUID id, CreateProductDto dto) {
        Product product =  this.findProductById(id);
        Set<SubCategory> subCategories = new HashSet<>();

        for(UUID subCategoryId : dto.getSub_category()){
            SubCategory subCategory = subCategoriesRepository.findById(subCategoryId).orElseThrow(()->new ResourceNotFoundException("Sub category"));
            subCategories.add(subCategory);
        }

        Manufacturer manufacturer = manufacturerService.findManufacturerById(dto.getManufacturer());
        Product newProduct = new Product(dto, manufacturer, subCategories);
        product.setBrand(newProduct.getBrand());
        product.setManufacturer(newProduct.getManufacturer());
        product.setSubCategories(newProduct.getSubCategories());
        product.setPrice(newProduct.getPrice());
        product.setCompany(newProduct.getCompany());
        product.setName(newProduct.getName());
        product.setCrossed_price(newProduct.getCrossed_price());
        product.setDiscount(newProduct.getDiscount());
        product.setInStock(newProduct.getInStock());
        product.setWarranty(newProduct.getWarranty());
        product.setSubCategories(newProduct.getSubCategories());
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

    @Override
    public String updateIllustration(UUID id, CreateIllustrationDto dto, MultipartFile file) throws Exception {
        Optional<Illustration> illustrationOptional = illustrationRepository.findById(id);
        Illustration illustration = null;

        if(illustrationOptional.get() != null) {
            illustration = illustrationOptional.get();
            cloudinaryService.deleteImage(illustration.getPublic_Id());
        }
        if(file == null)
            throw new BadRequestException("Please provide an image");
        ImageUploadDTO imageDto = cloudinaryService.uploadImage(file, directory);
        illustration.setImageUrl(imageDto.getImageUrl());
        illustration.setPublic_Id(imageDto.getPublicId());
        illustration.setDescription(dto.getDescription());
        illustration.setColor(dto.getColor());
        return "Data updated successfully!";
    }

}
