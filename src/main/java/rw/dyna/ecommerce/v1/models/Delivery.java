package rw.dyna.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="delivery")
public class Delivery {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Client client;

    @OneToOne
    private Order order;

    @ManyToOne
    private LocationAddress deliveryLocation;

}
