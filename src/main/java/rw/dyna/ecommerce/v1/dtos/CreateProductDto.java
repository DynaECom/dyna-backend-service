package rw.dyna.ecommerce.v1.dtos;
import lombok.*;
import rw.dyna.ecommerce.v1.enums.EProductStatus;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@NoArgsConstructor
@Data
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

    private UUID sub_category;

    private UUID manufacturer;

}
