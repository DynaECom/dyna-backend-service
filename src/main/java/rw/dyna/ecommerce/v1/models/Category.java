package rw.dyna.ecommerce.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table(name="category")
@Getter
@Setter
@Entity
public class Category extends InitiatorAudit {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sub_categories", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "subcategory_id"))
    private Set<SubCategory> subCategories;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

}
