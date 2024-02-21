package rw.dyna.ecommerce.v1.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.dyna.ecommerce.v1.models.User;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findByEmail(String email);

     boolean existsByEmailAndActivationCode(String email, String activationCode);

     boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}
