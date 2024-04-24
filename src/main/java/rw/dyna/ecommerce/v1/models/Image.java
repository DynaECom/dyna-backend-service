package rw.dyna.ecommerce.v1.models;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name_image")
    private String name;

    @Column(name = "url_image")
    private String url;
}
