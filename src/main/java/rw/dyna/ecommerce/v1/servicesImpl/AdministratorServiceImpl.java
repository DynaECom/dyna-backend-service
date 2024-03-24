package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.dtos.UpdateUserDto;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.fileHandling.File;
import rw.dyna.ecommerce.v1.models.Administrator;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.repositories.IAdministratorRepository;
import rw.dyna.ecommerce.v1.repositories.IClientRepository;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;
import rw.dyna.ecommerce.v1.services.IAdministratorService;
import rw.dyna.ecommerce.v1.services.IRoleService;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.utils.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AdministratorServiceImpl implements IAdministratorService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IUserServices userService;
    private final IUserRepository userRepository;
    private final IAdministratorRepository administratorRepository;
    private final IRoleService roleService;

    public AdministratorServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, IUserServices userService, IUserRepository userRepository, IAdministratorRepository administratorRepository, IRoleService roleService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
        this.roleService = roleService;
    }

    @Override
    public Administrator createAdministrator(CreateAccountDto dto) {
        Administrator administrator = Mapper.getAdministratorFromDTO(dto, dto.getPassword());
        userService.validateNewRegistration(administrator);
        String encodePassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Role role = roleService.findByName(dto.getRole());
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
}
