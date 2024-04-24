package rw.dyna.ecommerce.v1.services;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.ImageModalDTO;
import rw.dyna.ecommerce.v1.models.Image;

public interface ImageService {
    Image uploadImage(ImageModalDTO image);
//    String deleteImage(String image);
}
