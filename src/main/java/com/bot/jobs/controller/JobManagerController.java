package com.bot.jobs.controller;

import com.bot.jobs.models.ApiResponse;
import com.bot.jobs.models.CommonKafkaPayload;
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
    public  ResponseEntity<ApiResponse> getAllJobs() {
        var result = iManageJobService.getAllJobsService();
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }

    @GetMapping("getAllJobs/{jobsId}")
    public  ResponseEntity<ApiResponse> getAllJobs(@PathVariable("jobsId") int jobsId) throws Exception {
        var result = iManageJobService.getJobsByIdService(jobsId);
        return ResponseEntity.ok(ApiResponse.Ok(result));
    }
}
