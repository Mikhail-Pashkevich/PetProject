package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {
    List<Battle> findAllByBattleStatus(BattleStatus battleStatus);

    @Query("""
            select b
            from Battle b
            where b.battleStatus = :battleStatus
            and (b.playerX.id is null or b.playerX.id != :userId)
            and (b.playerO.id is null or b.playerO.id != :userId)
            order by lastActivityDatetime
            """)
    List<Battle> findAllByBattleStatusAndWithoutUser(BattleStatus battleStatus, Long userId);
}
