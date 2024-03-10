package com.bot.jobs.schedules;

import com.bot.jobs.db.service.DbManager;
import com.bot.jobs.db.utils.LowLevelExecution;
import com.bot.jobs.models.CommonKafkaPayload;
import com.bot.jobs.models.DbParameters;
import com.bot.jobs.models.Jobs;
import com.bot.jobs.producers.KafkaProducerService;
import com.bot.jobs.services.MasterDatabaseManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DailyScheduledJob {
    @Autowired
    KafkaProducerService kafkaProducerService;
    @Autowired MasterDatabaseManager masterDatabaseManager;
    @Autowired
    LowLevelExecution lowLevelExecution;
    @Autowired
    ObjectMapper objectMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(DailyScheduledJob.class);

    public List<Jobs> getJobsList() {
        try {
            return masterDatabaseManager.loadJobsDetail();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return null;
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 1000)
    public void HourlyJob() throws Exception {
        List<Jobs> jobsList = getJobsList();
        if(jobsList == null) {
            LOGGER.info("No job found.");
            return;
        }

        LOGGER.info("Scheduled task executed at: " + new Date());
        LOGGER.info(String.format("Record found: %s", jobsList.size()));
        jobsList.forEach(x -> {
            CommonKafkaPayload payload = new CommonKafkaPayload();
            payload.setKafkaServiceName(x.getKafkaServiceNameId());
            payload.setMessage(x.getJobsDetail());
            payload.setTopic(x.getTopicName());
            payload.setGroupId(x.getGroupId());
            if (x.getTemplate() != null && !x.getTemplate().isEmpty()) {
                payload.setMessage(x.getTemplate());
            }

            if (x.getKafkaServiceNameId() == 35) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);

                // Use the format method to convert Date to String
                String dateString = String.format("%s-%s-%s %s:%s:%s",
                        zonedDateTime.getYear(),
                        zonedDateTime.getMonth(),
                        x.getJobDayOfMonth(),
                        zonedDateTime.getHour(),
                        zonedDateTime.getMinute(),
                        zonedDateTime.getSecond());
                payload.setMessage("{ \"PaymentRunDate\": \" " + dateString + " \" }");
            }

            kafkaProducerService.sendMessage(payload);
        });
    }
}
