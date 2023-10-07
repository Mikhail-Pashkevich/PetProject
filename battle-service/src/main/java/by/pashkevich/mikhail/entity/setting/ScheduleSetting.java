package by.pashkevich.mikhail.entity.setting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ScheduleSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * See example values as for {@link org.springframework.scheduling.annotation.Scheduled#fixedRateString
     * org.springframework.scheduling.annotation.Scheduled}
     */
    @Column
    private String fixedRate;

    /**
     * See example values as for {@link java.time.Duration#parse java.time.Duration}
     */
    @Column
    private String moveWaitingTime;
}
