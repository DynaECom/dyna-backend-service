package rw.dyna.ecommerce.v1.servicesImpl;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    public String uploadImage(MultipartFile file, String folderName) throws Exception {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deleteImage(String image) throws Exception {
        return null;
    }
}
