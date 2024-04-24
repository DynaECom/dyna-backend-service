package rw.dyna.ecommerce.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ImageModalDTO {

    private MultipartFile file;

    private String name;
}
