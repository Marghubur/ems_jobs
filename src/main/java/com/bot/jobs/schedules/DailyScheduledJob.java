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
                            boolean isOccurrenceFlagEnabled = false;
                            if (x.getJobEndDate() != null) {
                                var endDate = x.getJobStartDate().toInstant()
                                        .atZone(ZoneId.of("UTC"))
                                        .toLocalDate();
                                duration = Duration.between(endDate.atStartOfDay(), currentUtcDateTime.toLocalDate().atStartOfDay());

                                endDateFlag = Math.abs(duration.toDays()) >= 0;
                            }

                            switch (x.getJobOccurrenceType()) {
                                case 1 -> {
                                    if (x.getJobMonthOfYear() != 0 && x.getJobMonthOfYear() != currentUtcDateTime.getMonthValue()) {
                                        break;
                                    }
                                    if (x.getJobDayOfMonth() != 0 && x.getJobDayOfMonth() != currentUtcDateTime.getDayOfMonth()) {
                                        break;
                                    }
                                    if (x.getJobTime() == hour) {
                                        isOccurrenceFlagEnabled = true;
                                    }
                                }
                                case 2 -> {
                                    if (x.getJobMonthOfYear() != 0 && x.getJobMonthOfYear() != currentUtcDateTime.getMonthValue()) {
                                        break;
                                    }
                                    if (x.getJobDayOfWeek() == currentUtcDateTime.getDayOfWeek().getValue()) {
                                        isOccurrenceFlagEnabled = true;
                                    }
                                }
                                case 3 -> {
                                    if (x.getJobMonthOfYear() != 0 && x.getJobMonthOfYear() != currentUtcDateTime.getMonthValue()) {
                                        break;
                                    }
                                    if (x.getJobDayOfMonth() == currentUtcDateTime.getDayOfMonth()) {
                                        isOccurrenceFlagEnabled = true;
                                    }
                                }
                            }

                            return startDateFlag
                                    && isOccurrenceFlagEnabled
                                    && endDateFlag
                                    && x.isActiveJob();
                        }).toList();

        LOGGER.info(String.format("Record found: %s", jobs.size()));
        jobs.forEach(x -> {
            CommonKafkaPayload payload = new CommonKafkaPayload();
            payload.setKafkaServiceName(x.getKafkaServiceNameId());
            payload.setMessage(x.getJobsDetail());
            payload.setTopic(x.getTopicName());
            payload.setGroupId(x.getGroupId());
            if (x.getTemplate() != null && !x.getTemplate().isEmpty()) {
                payload.setMessage(x.getTemplate());
            }

            kafkaProducerService.sendMessage(payload);
        });
    }
}
