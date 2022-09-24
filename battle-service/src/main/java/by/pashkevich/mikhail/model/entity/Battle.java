package by.pashkevich.mikhail.model.entity;

import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battle {
    private Long id;
    private Field field;
    private BattleStatus battleStatus;
    private LocalDateTime startBattleDatetime;
    private LocalDateTime lastMoveDatetime;
    private Player playerX;
    private Player playerO;
}
