package rw.dyna.ecommerce.v1.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.fileHandling.File;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="illustrations")
@NoArgsConstructor
@Getter
@Setter
public class Illustration extends InitiatorAudit {
    @Id
    @GeneratedValue
    private UUID id;

    private String color;

    private String public_Id;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="file_id")
    private File file;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "illustration_id")
    @JsonIgnore
    private Product product;

    public Illustration(String color, String description) {
        this.color = color;
        this.description = description;
    }
}
