package rw.dyna.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review extends InitiatorAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Product product;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review")
    private String review;

    public Review(Product product, Client client, String review, int rating) {
        super();
        this.product = product;
        this.client = client;
        this.review = review;
        this.rating = rating;
    }
}
