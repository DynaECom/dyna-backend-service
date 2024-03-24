package rw.dyna.ecommerce.v1.services;

import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.dtos.UpdateUserDto;
import rw.dyna.ecommerce.v1.fileHandling.File;
import rw.dyna.ecommerce.v1.models.Administrator;
import rw.dyna.ecommerce.v1.models.Client;
import java.util.List;
import java.util.UUID;

public interface IAdministratorService {
    Administrator createAdministrator(CreateAccountDto dto);
    Administrator getAdministratorById(UUID id);
    Administrator deleteAdministratorById(UUID id);
    List<Administrator> getAllAdministrators();
    Administrator updateAdministrator(UUID id, UpdateUserDto dto, File file);
    Page<Administrator> getAdministratorPaginated(Pageable pageable);
}
