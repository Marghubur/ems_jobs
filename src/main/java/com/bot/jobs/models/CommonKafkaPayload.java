package com.bot.jobs.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommonKafkaPayload {
    @JsonProperty("ServiceName")
    private String serviceName;
    @JsonProperty("Message")
    private String message;
}
