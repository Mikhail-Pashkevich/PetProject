package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.repository.FieldSettingRepository;
import by.pashkevich.mikhail.repository.ScheduleSettingRepository;
import by.pashkevich.mikhail.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {
    private final FieldSettingRepository fieldSettingRepository;
    private final ScheduleSettingRepository scheduleSettingRepository;

    @Override
    public Integer getRowSize() {
        return fieldSettingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException("Can't find any 'row size' config");
                })
                .getRowSize();
    }

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
    public Long getMoveWaitingTime() {
        String moveWaitingTime = scheduleSettingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException("Can't find any 'move waiting time' config");
                })
                .getMoveWaitingTime();

        return Duration.parse(moveWaitingTime).toNanos();
    }
}
