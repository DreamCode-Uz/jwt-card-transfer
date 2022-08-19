package uz.pdp.jwtcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.jwtcardtransfer.entity.Card;
import uz.pdp.jwtcardtransfer.entity.Income;
import uz.pdp.jwtcardtransfer.entity.Outcome;
import uz.pdp.jwtcardtransfer.payload.OutcomeDTO;
import uz.pdp.jwtcardtransfer.repository.CardRepository;
import uz.pdp.jwtcardtransfer.repository.IncomeRepository;
import uz.pdp.jwtcardtransfer.repository.OutcomeRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class OutcomeService {
    @Value("${outcome.commission.percent}")
    private Double commissionPercent;
    private final OutcomeRepository repository;
    private final CardRepository cardRepository;
    private final IncomeRepository incomeRepository;

    @Autowired
    public OutcomeService(OutcomeRepository repository, CardRepository cardRepository, IncomeRepository incomeRepository) {
        this.repository = repository;
        this.cardRepository = cardRepository;
        this.incomeRepository = incomeRepository;
    }

    public ResponseEntity<?> getOne(String outcomeId, HttpServletRequest request) {
        Optional<Outcome> optionalOutcome = repository.findById(outcomeId);
        if (!optionalOutcome.isPresent()) return new ResponseEntity<>("Outcome not found", HttpStatus.NOT_FOUND);
        Outcome outcome = optionalOutcome.get();
        Card card = cardRepository.getReferenceById(outcome.getFrom().getId());
        if (!card.getUsername().equals(request.getUserPrincipal().getName())) return badRequest().build();
        return ok(outcome);
    }

    public ResponseEntity<?> getAll(HttpServletRequest request) {
        List<Outcome> allByFrom_id = repository.findAllByFrom_Username(request.getUserPrincipal().getName());
        return ok(allByFrom_id);
    }

    public ResponseEntity<?> transferMoney(OutcomeDTO dto, HttpServletRequest request) {
        if (dto.getFrom().equals(dto.getTo())) return badRequest().body("Bank karta raqamlari har ikkalasi bir xil");
        Optional<Card> optionalFromCard = cardRepository.findById(dto.getFrom());
        if (!optionalFromCard.isPresent()) return status(HttpStatus.NOT_FOUND).body("Bank kartasi topilmadi");
        Card fromCard = optionalFromCard.get();
        if (!fromCard.getUsername().equals(request.getUserPrincipal().getName()))
            return status(HttpStatus.BAD_REQUEST).body("Ushbu kartadan sizda pul o'tkazish imkoni mavjud emas");
        Optional<Card> optionalToCard = cardRepository.findById(dto.getTo());
        if (!optionalToCard.isPresent()) return status(HttpStatus.NOT_FOUND).body("Pul o'tkaziluvchi Bank kartasi topilmadi");
        if (dto.getAmount() < 1000) return badRequest().body("Kiritilgan amount kamida 1000 bo'lishi kerak");
        double amount = dto.getAmount() * (commissionPercent / 100) + dto.getAmount();
        if (fromCard.getBalance() < amount) return badRequest().body("Kartada yetarli mablag' mavjud emas");
        fromCard.setBalance(fromCard.getBalance() - amount);
        cardRepository.save(fromCard);
        Card toCard = optionalToCard.get();
        toCard.setBalance(toCard.getBalance() + dto.getAmount());
        cardRepository.save(toCard);
        incomeRepository.save(new Income(fromCard, toCard, dto.getAmount(), new Date()));
        repository.save(new Outcome(fromCard, toCard, dto.getAmount(), new Date(), commissionPercent));
        return status(HttpStatus.CREATED).build();
    }

//    ACTION

}
