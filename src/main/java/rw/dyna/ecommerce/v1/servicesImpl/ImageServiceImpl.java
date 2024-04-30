package rw.dyna.ecommerce.v1.servicesImpl;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.ImageModalDTO;
import rw.dyna.ecommerce.v1.exceptions.AppException;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.models.Image;
import rw.dyna.ecommerce.v1.repositories.ImageRepository;
import rw.dyna.ecommerce.v1.services.ICloudinaryService;
import rw.dyna.ecommerce.v1.services.ImageService;


@Service
public class ImageServiceImpl implements ImageService {

    private final ICloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ICloudinaryService cloudinaryService, ImageRepository imageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imageRepository = imageRepository;
    }

    @Override
    public Image uploadImage(ImageModalDTO imageModal) {
        try {
            if (imageModal.getName().isEmpty()) {
                throw new BadRequestException("Name is required!");
            }
            if (imageModal.getFile() == null) {
                throw new BadRequestException("File is required!");
            }
            Image image = new Image();
            image.setName(imageModal.getName());
            image.setUrl(cloudinaryService.uploadImage(imageModal.getFile(), "folder_1").getImageUrl());
            if (image.getUrl() == null) {
                throw new AppException("Failed to upload image!");
            }
            return imageRepository.save(image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
