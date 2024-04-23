package rw.dyna.ecommerce.v1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateCategoryDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private List<UpdateSubCategoryDTO> updateSubCategoryDTOList;
}
