package rw.dyna.ecommerce.v1.models;

import lombok.*;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.enums.EOrderStatus;
import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order extends InitiatorAudit {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private EOrderStatus status;

    @OneToOne
    private Cart cart;

    @Column(name= "code")
    private String code;

    private String note;

    @Column(name = "shipping_cost")
    private long shippingCost;

    @Column(name = "revert_comment")
    private String revertComment;

}
