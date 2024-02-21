package rw.dyna.ecommerce.v1.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.enums.EProductStatus;
import rw.dyna.ecommerce.v1.models.Manufacturer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @NotBlank
    private String name;


    private String Company;

    private String Brand;

    private String warranty;

    @NotBlank
    private Float price;

    private Float crossed_price;

    private Float discount;

    @NotBlank
    private EProductStatus status;

    private Integer instock;

    private UUID category;

    private UUID subCategory;

    private UUID manufacturer;

}
