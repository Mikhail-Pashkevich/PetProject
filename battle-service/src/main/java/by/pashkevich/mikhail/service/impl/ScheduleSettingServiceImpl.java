package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.repository.ScheduleSettingRepository;
import by.pashkevich.mikhail.service.ScheduleSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleSettingServiceImpl implements ScheduleSettingService {
    private final ScheduleSettingRepository scheduleSettingRepository;

    @Override
    public String getFixedRate() {
        return scheduleSettingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException("Can't find any 'fixed rate' config");
                })
                .getFixedRate();
    }

    @Override
    public Integer getMoveWaitingTime() {
        return scheduleSettingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException("Can't find any 'move waiting time' config");
                })
                .getMoveWaitingTime();
    }
}
