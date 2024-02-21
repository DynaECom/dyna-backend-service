package rw.dyna.ecommerce.v1.services;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.models.Illustration;
import java.util.UUID;

public interface IIllustrationService {
    Illustration createIllustration(CreateIllustrationDto dto, MultipartFile file, UUID id);
    String removeIllustration(UUID id);
    Illustration updateIllustration(UUID id, CreateIllustrationDto dto, MultipartFile file);

}
