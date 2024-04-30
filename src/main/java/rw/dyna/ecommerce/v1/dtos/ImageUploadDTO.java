package rw.dyna.ecommerce.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadDTO {

    private String imageUrl;

    private String publicId;

}
