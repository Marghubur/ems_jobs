package com.bot.jobs.serviceinterface;

import com.bot.jobs.models.Jobs;

import java.util.List;

public interface IManageJobService {
    List<Jobs> getAllJobsService();
    Jobs getJobsByIdService(int jobsId) throws Exception;
    Jobs manageJobsService(Jobs jobs) throws Exception;
}
