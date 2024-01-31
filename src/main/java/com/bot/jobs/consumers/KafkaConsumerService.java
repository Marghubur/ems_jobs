package com.bot.jobs.consumers;

import com.bot.jobs.models.CommonKafkaPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
    @KafkaListener(topics = "daily-jobs-manager", groupId = "myGroup")
    public void consumer(CommonKafkaPayload payload) {
        LOGGER.info(String.format("Message received: %s", payload.getServiceName()));
    }
}
