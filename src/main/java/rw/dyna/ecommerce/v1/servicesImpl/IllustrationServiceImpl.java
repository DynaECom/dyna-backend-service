package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.fileHandling.File;
import rw.dyna.ecommerce.v1.models.Illustration;
import rw.dyna.ecommerce.v1.models.Product;
import rw.dyna.ecommerce.v1.repositories.IIllustrationRepository;
import rw.dyna.ecommerce.v1.repositories.IProductRepository;
import rw.dyna.ecommerce.v1.services.IFileService;
import rw.dyna.ecommerce.v1.services.IIllustrationService;
import rw.dyna.ecommerce.v1.services.IProductService;

import java.util.UUID;

@Service
public class IllustrationServiceImpl implements IIllustrationService {


    private final IFileService fileService;

    private final IIllustrationRepository illustrationRepository;

    private final IProductService productService;

    private final IProductRepository productRepository;

    @Value("${uploads.directory.illustrations}")
    private String directory;

    public IllustrationServiceImpl(IFileService fileService, IIllustrationRepository illustrationRepository, IProductService productService, IProductRepository productRepository) {
        this.fileService = fileService;

        this.illustrationRepository = illustrationRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Illustration createIllustration(CreateIllustrationDto dto, MultipartFile file, UUID id) {
        File newFile = null;
        Illustration illustration = illustrationRepository.save(new Illustration(dto.getColor(), dto.getDescription()));
        newFile = fileService.create(file, directory);
        illustration.setFile(newFile);
        Product product = productService.getProductById(id);
        product.getIllustrations().add(illustration);
        productRepository.save(product);
        return illustrationRepository.save(illustration);
    }


    @Override
    public String removeIllustration(UUID id) {
        illustrationRepository.deleteById(id);
        return "Successfully removed data!";
    }

    @Override
    public Illustration updateIllustration(UUID id, CreateIllustrationDto dto, MultipartFile file) {
        File newFile = null;
        Illustration illustration = illustrationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Illustration"));
        illustration.setDescription(dto.getDescription());
        illustration.setColor(dto.getColor());
        newFile = fileService.create(file, directory);
        illustration.setFile(newFile);
        return illustrationRepository.save(illustration);
    }
}
