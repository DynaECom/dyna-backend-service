package rw.dyna.ecommerce.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.dyna.ecommerce.v1.models.Cart;

import java.util.UUID;

public interface ICartRepository extends JpaRepository<Cart, UUID> {
}
