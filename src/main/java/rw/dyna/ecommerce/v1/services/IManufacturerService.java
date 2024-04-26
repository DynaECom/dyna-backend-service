package rw.dyna.ecommerce.v1.services;


import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateManufacturerDto;
import rw.dyna.ecommerce.v1.models.Manufacturer;

import java.util.List;
import java.util.UUID;

public interface IManufacturerService {

    Manufacturer createManufacturer(CreateManufacturerDto dto) throws Exception;

    Manufacturer getManufacturerByName(String name);

    Manufacturer updateManufacturer(UUID id, CreateManufacturerDto dto) throws Exception;

    String removeManufacturer(UUID id);

    List<Manufacturer> getManufacturers();

    Manufacturer findManufacturerById(UUID id);
}
