package by.pashkevich.mikhail.schedule;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BattleScheduler {
    private final BattleRepository battleRepository;

    private final SettingService settingService;


    @Scheduled(fixedRateString = "#{@getFixedRateSetting}")
    private void changeBattleStatus() {
        Integer moveWaitingTime = settingService.getMoveWaitingTime();
        LocalDateTime lastTimeBeforeInterrupt = LocalDateTime.now().minusNanos(moveWaitingTime);

        List<Battle> battles = battleRepository.findAll()
                .stream()
                .filter(battle -> battle.getLastActivityDatetime().isBefore(lastTimeBeforeInterrupt))
                .peek(battle -> battle.setBattleStatus(BattleStatus.INTERRUPTED))
                .toList();

        battleRepository.saveAll(battles);
    }
}
