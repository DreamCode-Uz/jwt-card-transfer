package uz.pdp.jwtcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.jwtcardtransfer.entity.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, String> {
}
