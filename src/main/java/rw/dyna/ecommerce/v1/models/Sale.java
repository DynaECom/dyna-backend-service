package rw.dyna.ecommerce.v1.models;

import rw.dyna.ecommerce.v1.audits.InitiatorAudit;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "sales")
@Entity
public class Sale extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
