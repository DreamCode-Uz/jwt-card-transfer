package uz.pdp.jwtcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.jwtcardtransfer.config.security.jwt.JwtTokenFilter;
import uz.pdp.jwtcardtransfer.entity.Card;
import uz.pdp.jwtcardtransfer.payload.CardDTO;
import uz.pdp.jwtcardtransfer.repository.CardRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class CardService {

    private final CardRepository repository;

    private final JwtTokenFilter filter;

    @Autowired
    public CardService(CardRepository repository, JwtTokenFilter filter) {
        this.repository = repository;
        this.filter = filter;
    }

    public ResponseEntity<?> getAll() {
        return ok(repository.findAll());
    }

    public ResponseEntity<?> getOne(String cardId) {
        Optional<Card> optionalCard = repository.findById(cardId);
        if (!optionalCard.isPresent()) return notFound().build();
        return ok(optionalCard.get());
    }

    public ResponseEntity<?> createCard(CardDTO dto, HttpServletRequest request) {
        Card card = new Card(
                request.getUserPrincipal().getName(),
                generateCardId(),
                dto.getBalance() != null ? dto.getBalance() : 0D,
                new Date(),
                Date.from(LocalDateTime.now().plusYears(10).toInstant(ZoneOffset.UTC)),
                dto.isActive()
        );
        return status(HttpStatus.CREATED).body(repository.save(card));
    }

    public ResponseEntity<?> editBalance(String cardId, CardDTO dto, HttpServletRequest request) {
        boolean existsByUsernameAndId = repository.existsByUsernameAndId(request.getUserPrincipal().getName(), cardId);
        if (!existsByUsernameAndId)
            return badRequest().body("Afsuski karta sizniki emas(Boshqa tokendan foydalanildi)");
        if (dto.getBalance() == null || dto.getBalance() < 0)
            return badRequest().body("balance bo'yicha mummo yuz berdi");
        Card card = repository.getReferenceById(cardId);
        card.setBalance(dto.getBalance());
        return ok(repository.save(card));
    }

    //    ACTION
    public String generateCardId() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 1; i <= 16; i++) {
            cardNumber.append((int) (Math.random() * 10));
        }
        return cardNumber.toString();
    }
}
