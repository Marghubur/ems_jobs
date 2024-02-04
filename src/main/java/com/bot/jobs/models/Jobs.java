package com.bot.jobs.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Jobs {
    @JsonProperty("JobId")
    private int jobId;
    @JsonProperty("JobTypeName")
    private String jobTypeName;
    @JsonProperty("JobTypeDescription")
    private String jobTypeDescription;
    @JsonProperty("IsActiveJob")
    private boolean isActiveJob;
    @JsonProperty("IsConcurrentJob")
    private boolean isConcurrentJob;
    @JsonProperty("JobStartDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date jobStartDate;
    @JsonProperty("JobEndDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date jobEndDate;
    @JsonProperty("JobTime")
    private int jobTime;
    @JsonProperty("JobDayOfWeek")
    private int jobDayOfWeek;
    @JsonProperty("JobDayOfMonth")
    private int jobDayOfMonth;
    @JsonProperty("JobMonthOfYear")
    private int jobMonthOfYear;
    @JsonProperty("JobOccurrenceType")
    private int jobOccurrenceType;
    @JsonProperty("TopicName")
    private String topicName;
    @JsonProperty("GroupId")
    private String groupId;
    @JsonProperty("JobsDetail")
    private String jobsDetail;
    @JsonProperty("Template")
    private String template;
    @JsonProperty("CreatedBy")
    private long createdBy;
    @JsonProperty("UpdatedBy")
    private long updatedBy;
    @JsonProperty("CreatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdOn;
    @JsonProperty("UpdatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date UpdatedOn;
}
