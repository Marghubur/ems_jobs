package com.bot.jobs.models;

import lombok.Data;

@Data
public class CommonKafkaPayload {
    private String serviceName;
    private String message;
}
