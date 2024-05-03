package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.dtos.ImageUploadDTO;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Illustration;
import rw.dyna.ecommerce.v1.repositories.IIllustrationRepository;
import rw.dyna.ecommerce.v1.repositories.IProductRepository;
import rw.dyna.ecommerce.v1.services.ICloudinaryService;
import rw.dyna.ecommerce.v1.services.IFileService;
import rw.dyna.ecommerce.v1.services.IIllustrationService;
import rw.dyna.ecommerce.v1.services.IProductService;

import java.util.List;
import java.util.UUID;

@Service
public class IllustrationServiceImpl implements IIllustrationService {

    private final IFileService fileService;

    private final IIllustrationRepository illustrationRepository;

    private final IProductService productService;

    private final IProductRepository productRepository;
    private final ICloudinaryService cloudinaryService;

    @Value("${uploads.directory.illustrations}")
    private String directory;

    public IllustrationServiceImpl(IFileService fileService, IIllustrationRepository illustrationRepository, IProductService productService, IProductRepository productRepository, ICloudinaryService cloudinaryService) {
        this.fileService = fileService;
        this.illustrationRepository = illustrationRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Illustration getById(UUID id){
       return illustrationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Illustration"));
    }

    @Override
    public String removeIllustration(UUID id) throws Exception {
        Illustration illustration = this.getById(id);
        cloudinaryService.deleteImage(illustration.getPublic_Id());
        illustrationRepository.delete(illustration);
        return "Successfully removed data!";
    }

    @Override
    public Illustration updateIllustration(UUID id, CreateIllustrationDto dto, MultipartFile file) throws Exception {
        Illustration illustration = this.getById(id);
        cloudinaryService.deleteImage(illustration.getPublic_Id());

        if(file == null)
            throw new BadRequestException("Please provide an image");
        ImageUploadDTO imageDto = cloudinaryService.uploadImage(file, directory);
        illustration.setImageUrl(imageDto.getImageUrl());
        illustration.setPublic_Id(imageDto.getPublicId());
        illustration.setDescription(dto.getDescription());
        illustration.setColor(dto.getColor());
        return illustrationRepository.save(illustration);
    }

    @Override
    public List<Illustration> getAllIllustrations() {
        return illustrationRepository.findAll();
    }
}
