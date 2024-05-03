package rw.dyna.ecommerce.v1.services;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.ImageUploadDTO;

public interface ICloudinaryService {
    ImageUploadDTO uploadImage(MultipartFile file , String folderName) throws Exception;

    void deleteImage(String public_id) throws Exception;
}
