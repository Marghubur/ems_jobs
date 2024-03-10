package com.bot.jobs.services;

import com.bot.jobs.Repository.ManageJobRepository;
import com.bot.jobs.db.service.DbManager;
import com.bot.jobs.entitymodel.ServiceType;
import com.bot.jobs.models.Jobs;
import com.bot.jobs.serviceinterface.IManageJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageJobService implements IManageJobService {
    @Autowired
    ManageJobRepository manageJobRepository;
    @Autowired
    DbManager dbManager;

    public List<Jobs> getAllJobsService() {
        return manageJobRepository.getAllJobRepository();
    }

    public Map<String, Object> getJobsByIdService(int jobsId) throws Exception {
        if (jobsId == 0)
            throw new Exception("Invalid job id");

        Jobs job = manageJobRepository.getJobsByIdRepository(jobsId);

        List<ServiceType> serviceTypes = manageJobRepository.getServiceTypes();
        Map<String, Object> jobDetail = new HashMap<>();

        jobDetail.put("ServiceType", serviceTypes);
        jobDetail.put("Job", job);

        return jobDetail;
    }

    public Jobs manageJobsService(Jobs jobs) throws Exception {
        validateJobDetail(jobs);
        if (jobs.getJobId() == 0)
            addJobDetail(jobs);
        else
            updateJobDetail(jobs);

        return jobs;
    }

    private void validateJobDetail(Jobs jobs) throws Exception {
        if (jobs.getJobTypeName().isEmpty() || jobs.getJobTypeName() == null)
            throw new Exception("Invalid job type name");

        if (jobs.getJobTypeDescription().isEmpty() || jobs.getJobTypeDescription() == null)
            throw new Exception("Invalid job type description");

        if (jobs.getTopicName().isEmpty() || jobs.getTopicName() == null)
            throw new Exception("Invalid topic name");

        if (jobs.getGroupId().isEmpty() || jobs.getGroupId() == null)
            throw new Exception("Invalid group type id");

        if (jobs.getJobOccurrenceType() == 1) {
            jobs.setJobDayOfWeek(0);
            jobs.setJobDayOfMonth(0);
        } else if (jobs.getJobOccurrenceType() == 2) {
            jobs.setJobDayOfMonth(0);
        } else if (jobs.getJobOccurrenceType() == 3 || jobs.getJobOccurrenceType() == 4) {
            jobs.setJobDayOfWeek(0);
        }
    }

    private void addJobDetail(Jobs jobs) throws Exception {
        java.util.Date utilDate = new java.util.Date();
        var date = new java.sql.Timestamp(utilDate.getTime());
        int jobId = dbManager.nextIntPrimaryKey(Jobs.class);
        jobs.setJobId(jobId);
        jobs.setCreatedBy(1L);
        jobs.setJobsDetail("{}");
        jobs.setCreatedOn(date);
        dbManager.save(jobs);
    }

    private void updateJobDetail(Jobs jobs) throws Exception {
        java.util.Date utilDate = new java.util.Date();
        var date = new java.sql.Timestamp(utilDate.getTime());
        var existingJobs = manageJobRepository.getJobsByIdRepository(jobs.getJobId());
        if (existingJobs == null)
            throw new Exception("Job detail not found");

        existingJobs.setJobTypeName(jobs.getJobTypeName());
        existingJobs.setJobTypeDescription(jobs.getJobTypeDescription());
        existingJobs.setActiveJob(jobs.isActiveJob());
        existingJobs.setJobStartDate(jobs.getJobStartDate());
        existingJobs.setJobEndDate(jobs.getJobEndDate());
        existingJobs.setJobTime(jobs.getJobTime());
        existingJobs.setJobDayOfMonth(jobs.getJobDayOfMonth());
        existingJobs.setJobDayOfWeek(jobs.getJobDayOfWeek());
        existingJobs.setJobMonthOfYear(jobs.getJobMonthOfYear());
        existingJobs.setJobOccurrenceType(jobs.getJobOccurrenceType());
        existingJobs.setTopicName(jobs.getTopicName());
        existingJobs.setGroupId(jobs.getGroupId());
        existingJobs.setTemplate(jobs.getTemplate());
        existingJobs.setUpdatedBy(1L);
        existingJobs.setUpdatedOn(date);
        dbManager.save(existingJobs);
    }
}