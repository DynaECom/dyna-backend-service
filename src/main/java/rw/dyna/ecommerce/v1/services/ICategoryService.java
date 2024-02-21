package rw.dyna.ecommerce.v1.services;

import org.springframework.stereotype.Service;
import rw.dyna.ecommerce.v1.dtos.CreateCategoryDto;
import rw.dyna.ecommerce.v1.dtos.CreateSubCategoryDto;
import rw.dyna.ecommerce.v1.models.Category;
import rw.dyna.ecommerce.v1.models.SubCategory;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ICategoryService {
    Category createCategory(CreateCategoryDto category);

    Category removeCategory(UUID id);

    Category updateCategory(UUID id, CreateCategoryDto category);

    List<Category> getCategories();

    SubCategory removeSubCategory(UUID id);

    SubCategory updateSubCategory(UUID id, CreateSubCategoryDto dto);

    Set<SubCategory> getSubCategories(UUID id);
}
