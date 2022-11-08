package by.pashkevich.mikhail.model.entity;

import by.pashkevich.mikhail.exception.IncorrectDataException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.model.entity.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Field field;

    @Column
    @Enumerated(EnumType.STRING)
    private BattleStatus battleStatus;

    @Column
    private LocalDateTime lastActivityDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_x_id")
    private User playerX;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_o_id")
    private User playerO;


    public void setPlayerByValue(User player, Value value) {
        switch (value) {
            case VALUE_X -> playerX = player;
            case VALUE_O -> playerO = player;
            default -> throw new IncorrectDataException("Can't process value: " + value);
        }
    }

    public void setPlayerOnEmptyPlace(User player) {
        if (playerO == null) {
            playerO = player;
        } else {
            playerX = player;
        }
    }

    public Value getValueByUserId(Long userId) {
        if (playerX.getId().equals(userId)) {
            return Value.VALUE_X;
        } else if (playerO.getId().equals(userId)) {
            return Value.VALUE_O;
        } else {
            throw new IncorrectDataException("Can't process userId: " + userId);
        }
    }

    public boolean isExist(User player) {
        return player.equals(playerX) || player.equals(playerO);
    }
}
