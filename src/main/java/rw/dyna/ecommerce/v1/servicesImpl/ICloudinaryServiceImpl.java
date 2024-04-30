package rw.dyna.ecommerce.v1.servicesImpl;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.ImageUploadDTO;
import rw.dyna.ecommerce.v1.services.ICloudinaryService;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ICloudinaryServiceImpl implements ICloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    @Override
    public ImageUploadDTO uploadImage(MultipartFile file, String folderName) throws Exception {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            ImageUploadDTO imageUploadDTO = new ImageUploadDTO();
            imageUploadDTO.setPublicId(publicId);
            imageUploadDTO.setImageUrl(cloudinary.url().secure(true).generate(publicId));
            return imageUploadDTO;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteImage(String public_id) throws Exception {
        cloudinary.uploader().destroy(public_id, null);
    }
}
