package by.pashkevich.mikhail.client;

import feign.RequestLine;

public interface StatisticClient {
    @RequestLine("POST /statistic")
    void save();
}
