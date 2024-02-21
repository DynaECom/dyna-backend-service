package rw.dyna.ecommerce.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.enums.EManufacturerStatus;
import rw.dyna.ecommerce.v1.fileHandling.File;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "manufacturers")
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer extends InitiatorAudit {
    private String name;

    private String description;

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="file_id")
    private File logo;

    private EManufacturerStatus status =  EManufacturerStatus.ACTIVE;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;


    public Manufacturer(String name, String description) {
        this.name=name;
        this.description = description;
    }
}
