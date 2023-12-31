package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class InfoService {
    @Value("${server.port}")
    private Integer port;

    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    public String getPort() {
        logger.info("Was invoked method for get server port");
        return "порт №: " + port;
    }

    public Integer getNumber() {
        logger.info("Was invoked method for get number");
        return IntStream.rangeClosed(0, 1000000)
                .sum();
    }
}
