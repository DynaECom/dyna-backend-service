package rw.dyna.ecommerce.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.dyna.ecommerce.v1.models.SubCategory;

import java.util.UUID;

public interface ISubCategoriesRepository extends JpaRepository<SubCategory, UUID> {
}
