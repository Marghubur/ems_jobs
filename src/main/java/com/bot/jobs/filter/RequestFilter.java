package com.bot.jobs.filter;

import com.bot.jobs.db.utils.DatabaseConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
@Component
public class RequestFilter implements Filter {
    @Autowired
    DatabaseConfiguration databaseConfiguration;
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            databaseConfiguration.setSchema("mysql");
            databaseConfiguration.setDatabaseName("ems_master");
            databaseConfiguration.setServer("tracker.io");
            databaseConfiguration.setPort("3308");
//            databaseConfiguration.setDatabase(dbResult.getDatabase());
//            databaseConfiguration.setUserId(dbResult.getUserId());
//            databaseConfiguration.setPassword(dbResult.getPassword());
//            databaseConfiguration.setConnectionTimeout(dbResult.getConnectionTimeout());
//            databaseConfiguration.setConnectionLifetime(dbResult.getConnectionLifetime());
//            databaseConfiguration.setPooling(dbResult.getPooling());
//            databaseConfiguration.setMinPoolSize(dbResult.getMinPoolSize());
//            databaseConfiguration.setMaxPoolSize(dbResult.getMaxPoolSize());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unauthorized access. Please try with valid token.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}