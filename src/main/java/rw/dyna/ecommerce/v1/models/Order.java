package rw.dyna.ecommerce.v1.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order")
@Getter
@Setter
public class Order {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

}
