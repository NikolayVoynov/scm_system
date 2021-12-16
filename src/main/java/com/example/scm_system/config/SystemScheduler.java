package com.example.scm_system.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SystemScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SystemScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 45 9,15 * * *")
    public void currentTime() {
        logger.info("Aviation Safety & Compliance Solutions system cron scheduler: Current Time = {}", dateFormat.format(new Date()));
    }
}
