package rw.dyna.ecommerce.v1.services;

import org.springframework.beans.factory.annotation.Autowired;
import rw.dyna.ecommerce.v1.enums.Erole;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.repositories.IRoleRepository;

public interface IRoleService {

    public Role findByName(Erole role);
}
