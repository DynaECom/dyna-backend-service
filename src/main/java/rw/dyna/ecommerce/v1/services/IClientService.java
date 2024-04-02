package rw.dyna.ecommerce.v1.services;


import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.dtos.UpdateUserDto;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.User;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    Client createClient(CreateAccountDto dto);
    Client getClientById(UUID id);
    Client deleteClientById(UUID id);
    List<Client> getAllClients();

    Client updateClient(UUID id, UpdateUserDto dto);

    Page<Client> getClientsPaginated(Pageable pageable);

    Client addAddress(UUID userId, long addressId);

    Client findByUser(User theUser);
}
