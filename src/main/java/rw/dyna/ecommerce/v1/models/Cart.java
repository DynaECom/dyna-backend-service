package rw.dyna.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.enums.ECartStatus;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "cart")
@Entity
@Data
public class Cart extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts;

    @OneToOne
    private Client client;

    @Transient
    private float totalPrice;

    @OneToOne
    private Order order;
    
}
