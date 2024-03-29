package rw.dyna.ecommerce.v1.models;

import lombok.Getter;
import lombok.Setter;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;

import javax.persistence.*;
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

}
