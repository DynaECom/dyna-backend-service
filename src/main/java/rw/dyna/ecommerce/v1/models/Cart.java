package rw.dyna.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "cart")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts;

}
