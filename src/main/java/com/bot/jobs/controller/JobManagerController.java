package com.bot.jobs.controller;

import com.bot.jobs.models.CommonKafkaPayload;
import com.bot.jobs.producers.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/manager/")
public class JobManagerController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @PostMapping("publish")
    public ResponseEntity<String> publish(@RequestBody CommonKafkaPayload payload) {
        kafkaProducerService.sendMessage(payload);
        return ResponseEntity.ok("Message send successfully");
    }
}
