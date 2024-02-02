package com.bot.jobs.schedules;

import com.bot.jobs.models.CommonKafkaPayload;
import com.bot.jobs.models.Jobs;
import com.bot.jobs.producers.KafkaProducerService;
import com.bot.jobs.services.MasterDatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DailyScheduledJob {
    @Autowired
    KafkaProducerService kafkaProducerService;
    private final static Logger LOGGER = LoggerFactory.getLogger(DailyScheduledJob.class);
    List<Jobs> jobsList = new ArrayList<>();
    public DailyScheduledJob(@Autowired MasterDatabaseManager masterDatabaseManager) {
        try {
            jobsList = masterDatabaseManager.loadJobsDetail();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 1000)
    public void HourlyJob() throws Exception {
        LOGGER.info("Scheduled task executed at: " + new Date());

        Instant currentUtcInstant = Instant.now();

        // Convert to ZonedDateTime with UTC time zone
        ZonedDateTime currentUtcDateTime = ZonedDateTime.ofInstant(currentUtcInstant, ZoneId.of("UTC"));

        // Extract the hour from the UTC time
        int hour = currentUtcDateTime.getHour();

        var jobs = jobsList.stream()
                        .filter(x  -> {
                            var startDate = x.getJobStartDate().toInstant()
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate();
                            Duration duration = Duration.between(currentUtcDateTime.toLocalDate().atStartOfDay(), startDate.atStartOfDay());
                            boolean startDateFlag = Math.abs(duration.toDays()) >= 0;

                            boolean endDateFlag = true;
                            if (x.getJobEndDate() != null) {
                                var endDate = x.getJobStartDate().toInstant()
                                        .atZone(ZoneId.of("UTC"))
                                        .toLocalDate();
                                duration = Duration.between(endDate.atStartOfDay(), currentUtcDateTime.toLocalDate().atStartOfDay());

                                endDateFlag = Math.abs(duration.toDays()) >= 0;
                            }

                            return startDateFlag
                                    && endDateFlag
                                    && x.getJobTime() == hour
                                    && x.isActiveJob();
                        }).toList();

        jobs.forEach(x -> {
            CommonKafkaPayload payload = new CommonKafkaPayload();
            payload.setServiceName(x.getJobTypeName().toUpperCase());
            kafkaProducerService.sendMessage(payload);
        });
    }
}
