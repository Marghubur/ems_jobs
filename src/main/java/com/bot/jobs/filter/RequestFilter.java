package com.bot.jobs.filter;

import com.bot.jobs.db.utils.DatabaseConfiguration;
import com.bot.jobs.services.MasterDatabaseManager;
import jakarta.servlet.*;
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
    MasterDatabaseManager masterDatabaseManager;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            var dbDetails = masterDatabaseManager.getUrl().split("/");
            if (dbDetails.length > 0) {
                databaseConfiguration.setDatabaseName(dbDetails[3]);
                var data = dbDetails[2].split(":");
                databaseConfiguration.setServer(data[0]);
                databaseConfiguration.setPort(data[1]);
                databaseConfiguration.setUserId(masterDatabaseManager.getUsername());
                databaseConfiguration.setPassword(masterDatabaseManager.getPassword());
                var schemas = dbDetails[0].split(":");
                databaseConfiguration.setSchema(schemas[0]);
                databaseConfiguration.setDatabaseName(schemas[1]);
                databaseConfiguration.setDatabase(dbDetails[3]);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unauthorized access. Please try with valid token.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}