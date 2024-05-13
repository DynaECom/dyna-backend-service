package rw.dyna.ecommerce.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.dyna.ecommerce.v1.models.Cart;
import rw.dyna.ecommerce.v1.models.Client;

import java.util.List;
import java.util.UUID;

public interface ICartRepository extends JpaRepository<Cart, UUID> {
    Cart findByClient(Client client);
}
