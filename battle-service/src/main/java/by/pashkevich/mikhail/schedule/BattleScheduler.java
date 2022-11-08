package by.pashkevich.mikhail.schedule;

import by.pashkevich.mikhail.model.entity.Battle;
import by.pashkevich.mikhail.model.entity.enums.BattleStatus;
import by.pashkevich.mikhail.repository.BattleRepository;
import by.pashkevich.mikhail.service.ScheduleSettingService;
import by.pashkevich.mikhail.service.util.datetime.DatetimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BattleScheduler {
    private final BattleRepository battleRepository;

    private final ScheduleSettingService scheduleSettingService;


    @Scheduled(fixedRateString = "#{@getFixedRateSetting}")
    private void changeBattleStatus() {
        Integer moveWaitingTime = scheduleSettingService.getMoveWaitingTime();
        long nanos = DatetimeUtil.toNanos(moveWaitingTime);
        LocalDateTime lastTimeBeforeInterrupt = LocalDateTime.now().minusNanos(nanos);

        List<Battle> battles = battleRepository.findAll()
                .stream()
                .filter(battle -> battle.getLastActivityDatetime().isBefore(lastTimeBeforeInterrupt))
                .peek(battle -> battle.setBattleStatus(BattleStatus.INTERRUPTED))
                .toList();

        battleRepository.saveAll(battles);
    }
}
