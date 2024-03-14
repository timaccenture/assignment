package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.service.FeedingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedingController {
    private final FeedingService feedingService;
    @Autowired
    public FeedingController(FeedingService feedingService) {
        this.feedingService = feedingService;
    }

    @PostMapping("/feeding")
    public void createFeeding(@RequestBody FeedingDTO feedingDTO) {
        feedingService.create(feedingDTO);
    }


}
