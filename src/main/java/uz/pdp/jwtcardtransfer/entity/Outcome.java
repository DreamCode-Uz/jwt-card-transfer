package uz.pdp.jwtcardtransfer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "outcome")
public class Outcome {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(optional = false)
    private Card fromCardId;

    @ManyToOne(optional = false)
    private Card toCardId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Date date;

    private Double commissionAmount;

    public Outcome(Card fromCardId, Card toCardId, Double amount, Date date, Double commissionAmount) {
        this.fromCardId = fromCardId;
        this.toCardId = toCardId;
        this.amount = amount;
        this.date = date;
        this.commissionAmount = commissionAmount;
    }
}
