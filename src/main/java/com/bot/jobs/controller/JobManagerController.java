package com.bot.jobs.controller;

import com.bot.jobs.models.ApiResponse;
import com.bot.jobs.models.CommonKafkaPayload;
import com.bot.jobs.models.Jobs;
import com.bot.jobs.producers.KafkaProducerService;
import com.bot.jobs.serviceinterface.IManageJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/jobs/manager/")
public class JobManagerController {

    @Autowired
    KafkaProducerService kafkaProducerService;
    @Autowired
    IManageJobService iManageJobService;

    @PostMapping("publish")
    public ResponseEntity<String> publish(@RequestBody CommonKafkaPayload payload) {
        kafkaProducerService.sendMessage(payload);
        return ResponseEntity.ok("Message send successfully");
    }

    @GetMapping("getAllJobs")
    public  ResponseEntity<ApiResponse> getAllJobs() throws Exception {
        var result = iManageJobService.getAllJobsService();
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @GetMapping("getJobsById/{jobsId}")
    public  ResponseEntity<ApiResponse> getJobsById(@PathVariable("jobsId") int jobsId) throws Exception {
        var result = iManageJobService.getJobsByIdService(jobsId);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @PostMapping("manageJobs")
    public  ResponseEntity<ApiResponse> manageJobs(@RequestBody Jobs jobs) throws Exception {
        var result = iManageJobService.manageJobsService(jobs);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
