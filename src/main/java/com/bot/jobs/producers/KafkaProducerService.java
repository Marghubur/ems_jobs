package com.bot.jobs.producers;

import com.bot.jobs.models.CommonKafkaPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private KafkaTemplate<String, CommonKafkaPayload> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, CommonKafkaPayload> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(CommonKafkaPayload payload) {
        LOGGER.info(String.format("Message send: %s", payload.getServiceName()));

        Message<CommonKafkaPayload> message = MessageBuilder
                        .withPayload(payload)
                        .setHeader(KafkaHeaders.TOPIC, "daily-jobs-manager")
                        .build();

        kafkaTemplate.send(message);
    }
}
