package rw.dyna.ecommerce.v1.models;

import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "wishlist")
public class WishList extends InitiatorAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Client client;
}
