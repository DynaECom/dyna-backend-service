package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.CreateManufacturerDto;
import rw.dyna.ecommerce.v1.enums.EManufacturerStatus;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.fileHandling.File;
import rw.dyna.ecommerce.v1.models.Manufacturer;
import rw.dyna.ecommerce.v1.repositories.ManufacturerRepository;
import rw.dyna.ecommerce.v1.services.ICloudinaryService;
import rw.dyna.ecommerce.v1.services.IFileService;
import rw.dyna.ecommerce.v1.services.IManufacturerService;

import java.util.List;
import java.util.UUID;

@Service
public class ManufacturerServiceImpl implements IManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final IFileService fileService;

    @Value("${uploads.directory.manufacturer_logos}")
    private String directory;

    private final ICloudinaryService cloudinaryService;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, IFileService fileService, ICloudinaryService cloudinaryService) {
        this.manufacturerRepository = manufacturerRepository;
        this.fileService = fileService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Manufacturer createManufacturer(CreateManufacturerDto dto) throws Exception {
        Manufacturer manufacturer = manufacturerRepository.save(new Manufacturer(dto.getName(), dto.getDescription(),cloudinaryService.uploadImage(dto.getFile(), "manufacturer_logos")));
//        File newFile = null;
//        newFile =  fileService.create(dto.getFile(), directory);
//        manufacturer.setLogo(newFile);
        return manufacturer;
    }



    @Override
    public Manufacturer updateManufacturer(UUID id, CreateManufacturerDto dto) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Manufacturer"));
        manufacturer.setName(dto.getName());
        manufacturer.setDescription(dto.getDescription());
        manufacturerRepository.save(manufacturer);
        return manufacturer;
    }

    @Override
    public String removeManufacturer(UUID id) {
        manufacturerRepository.deleteById(id);
        return "Successfully removed data!";
    }

    @Override
    public List<Manufacturer> getManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer findManufacturerById(UUID id) {
        return manufacturerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("manufacturer"));
    }
}
