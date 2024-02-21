package rw.dyna.ecommerce.v1.services;

import rw.dyna.ecommerce.v1.dtos.CreateAddressDto;
import rw.dyna.ecommerce.v1.models.Address;

import java.util.List;
import java.util.UUID;

public interface IAddressService {
    public Address createAddress(CreateAddressDto address);
    public Address updateAddress(UUID id, CreateAddressDto address);
    public Address removeAddress(UUID id);

    public List<Address> getAddresses(UUID id);

}
