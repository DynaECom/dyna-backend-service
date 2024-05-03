package rw.dyna.ecommerce.v1.services;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateIllustrationDto;
import rw.dyna.ecommerce.v1.models.Illustration;
import java.util.List;
import java.util.UUID;

public interface IIllustrationService {
    Illustration getById(UUID id);
    String removeIllustration(UUID id) throws Exception;
    Illustration updateIllustration(UUID id, CreateIllustrationDto dto, MultipartFile file) throws Exception;
    List<Illustration> getAllIllustrations();
}
