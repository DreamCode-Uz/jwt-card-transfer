package uz.pdp.jwtcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.jwtcardtransfer.entity.Income;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, String> {
    List<Income> getAllByToCardId_Id(String toCardId_id);
}
