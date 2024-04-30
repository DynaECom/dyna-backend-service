package rw.dyna.ecommerce.v1.services;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.models.Illustration;
import java.util.UUID;

public interface IIllustrationService {
    String removeIllustration(UUID id);
    Illustration updateIllustration(UUID id, CreateIllustrationDto dto);

}
