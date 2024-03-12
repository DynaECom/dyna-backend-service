package rw.dyna.ecommerce.v1.services;

import rw.dyna.ecommerce.v1.dtos.CreateAddressDTO;
import rw.dyna.ecommerce.v1.models.Address;

import java.util.List;
import java.util.UUID;

public interface IAddressService {
    public Address createAddress(CreateAddressDTO address);
    public Address updateAddress(UUID id, CreateAddressDTO address);
    public Address removeAddress(UUID id);

    public List<Address> getAddresses(UUID id);

}
