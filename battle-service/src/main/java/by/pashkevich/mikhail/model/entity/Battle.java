package by.pashkevich.mikhail.model.entity;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
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
    private LocalDateTime startBattleDatetime;

    @Column
    private LocalDateTime lastMoveDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User playerX;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User playerO;
}
