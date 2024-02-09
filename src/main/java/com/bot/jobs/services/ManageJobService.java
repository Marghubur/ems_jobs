package com.bot.jobs.services;

import com.bot.jobs.Repository.ManageJobRepository;
import com.bot.jobs.models.Jobs;
import com.bot.jobs.serviceinterface.IManageJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageJobService implements IManageJobService {
    @Autowired
    ManageJobRepository manageJobRepository;
    public List<Jobs> getAllJobsService() {
        return manageJobRepository.getAllJobRepository();
    }

    public Jobs getJobsByIdService(int jobsId) throws Exception {
        if (jobsId == 0)
            throw  new Exception("Invalid job id");

        return manageJobRepository.getJobsByIdRepository(jobsId);
    }
}
