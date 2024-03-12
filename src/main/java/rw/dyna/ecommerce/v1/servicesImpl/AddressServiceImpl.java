package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateAddressDTO;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Address;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.repositories.IAddressRepository;
import rw.dyna.ecommerce.v1.repositories.IUserRepository;
import rw.dyna.ecommerce.v1.services.IAddressService;
import rw.dyna.ecommerce.v1.services.IUserServices;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository addressRepository;
    private final IUserServices userService;
    public AddressServiceImpl(IAddressRepository addressRepository, IUserRepository userRepository, IUserServices userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Override
    public Address createAddress(CreateAddressDTO dto) {
        Address address = new Address(dto);
        User user = userService.getLoggedInUser();
        address.setUser(user);
        address = addressRepository.save(address);
        return address;
    }

    @Override
    public Address updateAddress(UUID id, CreateAddressDTO dto) {
        Address address = addressRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Address", "Cell", dto.getCell()));
        address.setStreetName(dto.getStreetName());
        address.setCountry(dto.getCountry());
        address.setProvince(dto.getProvince());
        address.setDistrict(dto.getDistrict());
        address.setSector(dto.getSector());
        address.setVillage(dto.getVillage());
        address.setCell(dto.getCell());
        addressRepository.save(address);
        return address;
    }

    public Address findById(UUID id){
        return addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Address"));
    }

    @Override
    public Address removeAddress(UUID id) {
        Address address = findById(id);
        addressRepository.delete(address);
        return address;
    }

    @Override
    public List<Address> getAddresses(UUID id) {
        return null;
    }

}
