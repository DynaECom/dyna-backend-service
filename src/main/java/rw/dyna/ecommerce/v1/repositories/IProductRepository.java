package rw.dyna.ecommerce.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.dyna.ecommerce.v1.models.Product;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {

}
