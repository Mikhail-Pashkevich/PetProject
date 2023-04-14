package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.repository.StatisticRepository;
import by.pashkevich.mikhail.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    @Override
    public void save() {
        //TODO: add some logic to save statistic
    }
}
