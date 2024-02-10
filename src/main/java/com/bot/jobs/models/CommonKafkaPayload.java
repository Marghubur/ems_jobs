package com.bot.jobs.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommonKafkaPayload {
    @JsonProperty("ServiceName")
    private String serviceName;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Topic")
    private String topic;
    @JsonProperty("GroupId")
    private String groupId;
}
