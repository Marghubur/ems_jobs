package com.bot.jobs.Repository;

import com.bot.jobs.db.service.DbManager;
import com.bot.jobs.models.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManageJobRepository {
    @Autowired
    DbManager dbManager;

    public List<Jobs> getAllJobRepository() {
        return dbManager.queryList("select * from jobs ", Jobs.class);
    }

    public Jobs getJobsByIdRepository(int jobId) {
        return dbManager.queryRaw("select * from jobs where JobId = " + jobId, Jobs.class);
    }

}
