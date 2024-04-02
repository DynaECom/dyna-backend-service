package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.dtos.UpdateUserDto;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.LocationAddress;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.repositories.IClientRepository;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;
import rw.dyna.ecommerce.v1.services.IClientService;
import rw.dyna.ecommerce.v1.services.ILocationAddressService;
import rw.dyna.ecommerce.v1.services.IRoleService;
import rw.dyna.ecommerce.v1.services.IUserServices;
import rw.dyna.ecommerce.v1.utils.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements IClientService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IUserServices userService;
    private final IUserRepository userRepository;
    private final IClientRepository clientRepository;
    private final IRoleService roleService;
    private final ILocationAddressService addressService;

    public ClientServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, IUserServices userService, IUserRepository userRepository, IClientRepository clientRepository, IRoleService roleService, ILocationAddressService addressService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.roleService = roleService;
        this.addressService = addressService;
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
    public Client getClientById(UUID id) {
        Client client  = clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("client", "id", id.toString()));
        return client;
    }

    @Override
    public Client deleteClientById(UUID id) {
        Client client = this.getClientById(id);
        clientRepository.deleteById(id);
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client updateClient( UUID id, UpdateUserDto dto) {
        Client client = this.getClientById(id);
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setPhoneNumber(dto.getPhoneNumber());
        return clientRepository.save(client);
    }

    @Override
    public Page<Client> getClientsPaginated(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Client addAddress(UUID userId, long addressId) {
        Client client = this.getClientById(userId);
        LocationAddress address = addressService.findById(addressId);
        List<LocationAddress> clientAddresses = client.getLocationAddressList();
        clientAddresses.add(address);
        client.setLocationAddressList(clientAddresses);

        return clientRepository.save(client);
    }

    @Override
    public Client findByUser(User theUser) {
        return this.getClientById(theUser.getId());
    }
}
