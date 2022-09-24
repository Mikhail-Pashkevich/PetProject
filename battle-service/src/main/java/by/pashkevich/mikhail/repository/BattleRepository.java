package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.model.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {
}
