package rw.dyna.ecommerce.v1.services;


import org.springframework.data.domain.Page;
import rw.dyna.ecommerce.v1.dtos.CreateAccountDto;
import rw.dyna.ecommerce.v1.models.Client;

import java.util.List;

public interface IClientService {
    Client createClient(CreateAccountDto dto);
    Client getClientById();
    Client deleteClientById();
    List<Client> getAllClients();

    Client updateClient();

    Page<Client> getClientsPaginated();

}
