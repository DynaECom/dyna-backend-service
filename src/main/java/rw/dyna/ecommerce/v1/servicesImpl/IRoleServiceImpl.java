package rw.dyna.ecommerce.v1.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.enums.Erole;
import rw.dyna.ecommerce.v1.exceptions.ResourceNotFoundException;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.repositories.IRoleRepository;
import rw.dyna.ecommerce.v1.services.IRoleService;

@Service
public class IRoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;

    @Autowired
    public IRoleServiceImpl(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findByName(Erole role) {
        return roleRepository.findByName(role).orElseThrow(()->new ResourceNotFoundException("Role", "name", role.toString()));
    }
}
