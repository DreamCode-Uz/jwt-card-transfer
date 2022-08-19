package uz.pdp.jwtcardtransfer.payload;

import lombok.Data;

@Data
public class CardDTO {
//    @NotBlank(message = "Username must be null and not empty")
//    private String username;
//    @NotBlank(message = "Card number must be null and not empty")
//    @Pattern(regexp = "(\\d{4}[- .]?){4}", message = "Please enter the card number correctly: (example: 8600 0000 0000 0001)")
//    private String number;  // 8600-0000-0000-0001, 8600.0000.0000.0001, 8600 0000 0000 0001, 8600000000000001
    private Double balance;
    private boolean active;
}
