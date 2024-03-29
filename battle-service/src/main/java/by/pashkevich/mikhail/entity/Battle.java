package by.pashkevich.mikhail.entity;

import by.pashkevich.mikhail.entity.enums.BattleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
