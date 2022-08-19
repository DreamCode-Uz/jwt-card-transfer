package uz.pdp.jwtcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.jwtcardtransfer.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    boolean existsByUsernameAndId(String username, String id);
}
