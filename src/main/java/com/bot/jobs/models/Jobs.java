package com.bot.jobs.models;

import com.bot.jobs.db.annotations.Column;
import com.bot.jobs.db.annotations.Id;
import com.bot.jobs.db.annotations.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;

@Data
@Table(name = "jobs")
public class Jobs {
    @Id
    @JsonProperty("JobId")
    @Column(name = "JobId")
    private int jobId;
    @JsonProperty("JobTypeName")
    @Column(name = "JobTypeName")
    private String jobTypeName;
    @JsonProperty("KafkaServiceNameId")
    @Column(name = "KafkaServiceNameId")
    private int kafkaServiceNameId;
    @JsonProperty("JobTypeDescription")
    @Column(name = "JobTypeDescription")
    private String jobTypeDescription;
    @JsonProperty("IsActiveJob")
    @Column(name = "IsActiveJob")
    private boolean isActiveJob;
    @JsonProperty("JobStartDate")
    @Column(name = "JobStartDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date jobStartDate;
    @JsonProperty("JobEndDate")
    @Column(name = "JobEndDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date jobEndDate;
    @JsonProperty("JobTime")
    @Column(name = "JobTime")
    private int jobTime;
    @JsonProperty("JobDayOfWeek")
    @Column(name = "JobDayOfWeek")
    private int jobDayOfWeek;
    @JsonProperty("JobDayOfMonth")
    @Column(name = "JobDayOfMonth")
    private int jobDayOfMonth;
    @JsonProperty("JobMonthOfYear")
    @Column(name = "JobMonthOfYear")
    private int jobMonthOfYear;
    @JsonProperty("JobOccurrenceType")
    @Column(name = "JobOccurrenceType")
    private int jobOccurrenceType;
    @JsonProperty("TopicName")
    @Column(name = "TopicName")
    private String topicName;
    @JsonProperty("GroupId")
    @Column(name = "GroupId")
    private String groupId;
    @JsonProperty("JobsDetail")
    @Column(name = "JobsDetail")
    private String jobsDetail;
    @JsonProperty("Template")
    @Column(name = "Template")
    private String template;
    @JsonProperty("CreatedBy")
    @Column(name = "CreatedBy")
    private long createdBy;
    @JsonProperty("UpdatedBy")
    @Column(name = "UpdatedBy")
    private long updatedBy;
    @JsonProperty("CreatedOn")
    @Column(name = "CreatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdOn;
    @JsonProperty("UpdatedOn")
    @Column(name = "UpdatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date UpdatedOn;
}
