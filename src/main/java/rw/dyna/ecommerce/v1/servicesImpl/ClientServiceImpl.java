package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;
import rw.dyna.ecommerce.v1.services.IClientService;
import rw.dyna.ecommerce.v1.services.IRoleService;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.utils.Mapper;

import java.util.Collections;
import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final IUserServices userService;

    private final IUserRepository userRepository;
    private final IRoleService roleService;

    public ClientServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, IUserServices userService, IUserRepository userRepository, IRoleService roleService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public Client createClient(CreateAccountDto dto) {
        Client client = Mapper.getClientFromDTO(dto, dto.getPassword());
        userService.validateNewRegistration(client);
        String encodePassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Role role = roleService.findByName(dto.getRole());
        client.setEmail(dto.getEmail());
        client.setLastName(dto.getLastName());
        client.setFirstName(dto.getFirstName());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setPassword(encodePassword);
        client.setRoles(Collections.singleton(role));
        return userRepository.save(client);
    }

    @Override
    public Client getClientById() {
        return null;
    }

    @Override
    public Client deleteClientById() {
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        return null;
    }

    @Override
    public Client updateClient() {
        return null;
    }

    @Override
    public Page<Client> getClientsPaginated() {
        return null;
    }
}
