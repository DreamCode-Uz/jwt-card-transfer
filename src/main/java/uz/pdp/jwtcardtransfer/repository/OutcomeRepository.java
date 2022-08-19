package uz.pdp.jwtcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.jwtcardtransfer.entity.Outcome;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, String> {
}
