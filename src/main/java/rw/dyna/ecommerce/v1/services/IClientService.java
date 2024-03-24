package rw.dyna.ecommerce.v1.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.models.Client;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    Client createClient(CreateAccountDto dto);
    Client getClientById(UUID id);
    Client deleteClientById(UUID id);
    List<Client> getAllClients();

    Client updateClient( );

    Page<Client> getClientsPaginated(Pageable pageable);

}
