package rw.dyna.ecommerce.v1.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
