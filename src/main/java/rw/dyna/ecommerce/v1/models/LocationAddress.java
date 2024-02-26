package rw.dyna.ecommerce.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.enums.ELocationType;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="location_address")
public class LocationAddress  extends InitiatorAudit {

    @Id
    @Column(name="location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private ELocationType locationType;

    @Column(name="name")
    private String name;

    @Column(name="name_french")
    private String nameFrench;

    @Column(name ="name_kiny")
    private String nameKiny;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_id")
    private LocationAddress parentId;

    @ManyToMany
    @JoinTable(name="user_location_address", joinColumns = @JoinColumn(name = "location_address_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user;

}