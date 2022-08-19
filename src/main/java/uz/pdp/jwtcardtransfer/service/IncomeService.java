package uz.pdp.jwtcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.jwtcardtransfer.entity.Card;
import uz.pdp.jwtcardtransfer.entity.Income;
import uz.pdp.jwtcardtransfer.repository.CardRepository;
import uz.pdp.jwtcardtransfer.repository.IncomeRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class IncomeService {
    private final IncomeRepository repository;
    private final CardRepository cardRepository;

    @Autowired
    public IncomeService(IncomeRepository repository, CardRepository cardRepository) {
        this.repository = repository;
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> getOne(String cardId, HttpServletRequest request) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (!optionalCard.isPresent()) return notFound().build();
        Card card = optionalCard.get();
        String name = request.getUserPrincipal().getName();
        if (!card.getUsername().equals(name)) return status(401).build();
        List<Income> allByToCardId_id = repository.getAllByToCardId_Id(cardId);
        return ok(allByToCardId_id);
    }
}
