package rw.dyna.ecommerce.v1.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    String uploadImage(MultipartFile file , String folderName) throws Exception;
    String deleteImage(String image) throws Exception;
}
