package rw.dyna.ecommerce.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.dyna.ecommerce.v1.models.Image;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
}
