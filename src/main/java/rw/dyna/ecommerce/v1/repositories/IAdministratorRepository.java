package rw.dyna.ecommerce.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.dyna.ecommerce.v1.models.Administrator;

import java.util.UUID;

public interface IAdministratorRepository extends JpaRepository<Administrator, UUID> {
}
