package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.dyna.ecommerce.v1.dtos.RegisterAdminDto;
import rw.dyna.ecommerce.v1.dtos.UpdateUserDto;
import rw.dyna.ecommerce.v1.enums.Erole;
import rw.dyna.ecommerce.v1.exceptions.BadRequestException;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.fileHandling.File;
import rw.dyna.ecommerce.v1.models.Administrator;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.repositories.IAdministratorRepository;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;
import rw.dyna.ecommerce.v1.services.IAdministratorService;
import rw.dyna.ecommerce.v1.services.IFileService;
import rw.dyna.ecommerce.v1.services.IRoleService;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.utils.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AdministratorServiceImpl implements IAdministratorService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IUserServices userService;
    private final IUserRepository userRepository;
    private final IAdministratorRepository administratorRepository;

    private final IFileService fileService;
    private final IRoleService roleService;
    @Value("${admin_registration_key}")
    private String adminRegistrationKey;

    @Value("${uploads_directory")
    private String uploadsDirectory;

    public AdministratorServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, IUserServices userService, IUserRepository userRepository, IAdministratorRepository administratorRepository, IFileService fileService, IRoleService roleService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
        this.fileService = fileService;
        this.roleService = roleService;
    }

    @Override
    public Administrator createAdministrator(RegisterAdminDto dto) {
        Administrator administrator = Mapper.getAdministratorFromDTO(dto, dto.getPassword());
        userService.validateNewRegistration(administrator);
        if(!Objects.equals(dto.getKey(), adminRegistrationKey)) {
            System.out.println(adminRegistrationKey);
            System.out.println("key " + dto.getKey());
            throw new BadRequestException("Invalid registration key");
        }
        String encodePassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Role role = roleService.findByName(Erole.ADMIN);
        administrator.setEmail(dto.getEmail());
        administrator.setLastName(dto.getLastName());
        administrator.setFirstName(dto.getFirstName());
        administrator.setPhoneNumber(dto.getPhoneNumber());
        administrator.setPassword(encodePassword);
        administrator.setRoles(Collections.singleton(role));
        return userRepository.save(administrator);
    }

    @Override
    public Administrator getAdministratorById(UUID id) {
        Administrator administrator  = administratorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("administrator", "id", id.toString()));
        return administrator;
    }

    @Override
    public Administrator deleteAdministratorById(UUID id) {
        Administrator administrator = this.getAdministratorById(id);
        administratorRepository.deleteById(id);
        return administrator;
    }

    @Override
    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }

    @Override
    public Administrator updateAdministrator(UUID id, UpdateUserDto dto, File file) {
        Administrator administrator = this.getAdministratorById(id);
        administrator.setFirstName(dto.getFirstName());
        administrator.setLastName(dto.getLastName());
        administrator.setPhoneNumber(dto.getPhoneNumber());
        administrator.setIdentityDocument(file);

        return administratorRepository.save(administrator);
    }

    @Override
    public Page<Administrator> getAdministratorPaginated(Pageable pageable) {
        return administratorRepository.findAll(pageable);
    }

    @Override
    public Administrator addIdentificationFile(UUID id, MultipartFile file) {
        Administrator administrator = this.getAdministratorById(id);
        File saveFile = fileService.create(file, uploadsDirectory);
        administrator.setIdentityDocument(saveFile);
        return administratorRepository.save(administrator);
    }
}
