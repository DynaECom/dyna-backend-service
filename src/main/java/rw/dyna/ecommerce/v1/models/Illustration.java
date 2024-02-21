package rw.dyna.ecommerce.v1.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.fileHandling.File;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="illustrations")
@NoArgsConstructor
@Getter
@Setter
public class Illustration {
    @Id
    @GeneratedValue
    private UUID id;

    private String color;


    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "illustration_id")
    private Product product;

    public Illustration(String color, String description) {
        this.color = color;
        this.description = description;
    }
}
