package by.pashkevich.mikhail.client;

import by.pashkevich.mikhail.dto.StatisticDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "statisticClient", url = "${app.vars.statistic.url}")
public interface StatisticClient {
    @PostMapping(value = "/statistic")
    void save(@RequestBody StatisticDto statisticDto);
}
