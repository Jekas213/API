package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Value("${server.port}")
    private Integer port;

    Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Override
    public String getPort() {
        logger.info("Was invoked method for get server port");
        return "порт №: " + port;
    }
}
