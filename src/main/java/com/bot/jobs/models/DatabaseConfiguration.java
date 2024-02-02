package com.bot.jobs.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DatabaseConfiguration {
    @JsonProperty("OrganizationCode")
    String organizationCode;
    @JsonProperty("Code")
    String code;
    @JsonProperty("Schema")
    String schema;
    @JsonProperty("DatabaseName")
    String databaseName;
    @JsonProperty("Server")
    String server;
    @JsonProperty("Port")
    String port;
    @JsonProperty("Database")
    String database;
    @JsonProperty("UserId")
    String userId;
    @JsonProperty("Password")
    String password;
    @JsonProperty("ConnectionTimeout")
    int connectionTimeout;
    @JsonProperty("ConnectionLifetime")
    int connectionLifetime;
    @JsonProperty("MinPoolSize")
    int minPoolSize;
    @JsonProperty("MaxPoolSize")
    int maxPoolSize;
    @JsonProperty("Pooling")
    boolean pooling;
}