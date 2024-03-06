package rw.dyna.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.enums.EProductStatus;
import rw.dyna.ecommerce.v1.fileHandling.File;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity()
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends InitiatorAudit {

    @Id
    @GeneratedValue()
    private UUID id;

    private String name;

    private String company;

    private String brand;

    private String warranty;

    private Float price;

    private Float crossed_price;

    private Float discount;

    private EProductStatus status;

    private Integer inStock;

    @OneToMany
    private List<Illustration> illustrations;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SubCategory category;

    @ManyToOne
    @JoinColumn(name = "manufacturer")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "product", fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Review> review;

    public Product(CreateProductDto dto, Manufacturer manufacturer, SubCategory subCategory) {
        this.name = dto.getName();
        this.company = dto.getCompany();
        this.brand = dto.getBrand();
        this.warranty = dto.getWarranty();
        this.price = dto.getPrice();
        this.crossed_price = dto.getCrossed_price();
        this.discount = dto.getDiscount();
        this.status =  dto.getStatus();
        this.inStock = dto.getInstock();
        this.category = subCategory;
        this.manufacturer = manufacturer;
    }
}
