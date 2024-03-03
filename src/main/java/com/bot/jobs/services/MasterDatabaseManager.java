package com.bot.jobs.services;

import com.bot.jobs.models.Jobs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.datasource")
@Component("MasterDataConnections")
public class MasterDatabaseManager {
    @Autowired
    ObjectMapper mapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(MasterDatabaseManager.class);
    String driver;
    String url;
    String username;
    String password;

    public List<Jobs> loadJobsDetail() throws Exception {
        getDatasource();
        String query = "sp_joboccurrance_get";
        JdbcTemplate template = getTemplate();
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template)
                .withProcedureName(query);

        Map<String, Object> resultSet = simpleJdbcCall.execute();
        if (resultSet.containsKey("#result-set-1")) {
            List<Jobs> jobs = mapper.convertValue(resultSet.get("#result-set-1"), new TypeReference<List<Jobs>>() {
            });

            LOGGER.info("[DATABASE] Data loaded successfully");
            if(jobs.size() == 0) {
                LOGGER.info("No job found.");
            }

            return jobs;
        }else {
            throw new Exception("Unable to load jobs data. Please contact to admin.");
        }
    }

    private DriverManagerDataSource getDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.getDriver());
        dataSource.setUrl(this.getUrl());
        dataSource.setUsername(this.getUsername());
        dataSource.setPassword(this.getPassword());
        return dataSource;
    }

    private JdbcTemplate getTemplate() {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(getDatasource());
        return template;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}