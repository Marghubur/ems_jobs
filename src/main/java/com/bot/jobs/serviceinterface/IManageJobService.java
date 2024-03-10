package com.bot.jobs.serviceinterface;

import com.bot.jobs.models.Jobs;

import java.util.List;
import java.util.Map;

public interface IManageJobService {
    List<Jobs> getAllJobsService();
    Map<String, Object> getJobsByIdService(int jobsId) throws Exception;
    Jobs manageJobsService(Jobs jobs) throws Exception;
}
