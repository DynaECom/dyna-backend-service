package rw.dyna.ecommerce.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.dtos.CreateProductDto;
import rw.dyna.ecommerce.v1.enums.EProductStatus;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
    private List<Illustration> illustrations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_subCategories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "subCategory_id"))
    @JsonIgnore
    private List<SubCategory> subCategoriesList;

    @ManyToOne
    @JoinColumn(name = "manufacturer")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "product", fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Review> review;

    public Product(CreateProductDto dto, Manufacturer manufacturer, List<SubCategory> subCategories) {
        this.name = dto.getName();
        this.company = dto.getCompany();
        this.brand = dto.getBrand();
        this.warranty = dto.getWarranty();
        this.price = dto.getPrice();
        this.crossed_price = dto.getCrossed_price();
        this.discount = dto.getDiscount();
        this.status =  dto.getStatus();
        this.inStock = dto.getInstock();
        this.subCategoriesList = subCategories;
        this.manufacturer = manufacturer;
    }
}
