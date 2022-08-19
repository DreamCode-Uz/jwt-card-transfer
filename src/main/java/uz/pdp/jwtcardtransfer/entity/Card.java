package uz.pdp.jwtcardtransfer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "bank_card")
public class Card {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private String username;
    @Column(name = "card_number", unique = true)
    private String number;
    private Double balance;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;
    private boolean active;

    public Card(String username, String number, Double balance, Date createdDate, Date expireDate, boolean active) {
        this.username = username;
        this.number = number;
        this.balance = balance;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.active = active;
    }
}
