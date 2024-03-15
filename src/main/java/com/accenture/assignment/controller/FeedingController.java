package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.service.FeedingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/feeding")
    public ResponseEntity<List<FeedingDTO>> findAll() {
        return new ResponseEntity<>(feedingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/feeding/{id}")
    public ResponseEntity<FeedingDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(feedingService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/feeding/{id}")
    public ResponseEntity<FeedingDTO> updateById(@PathVariable("id") Long id, @RequestBody FeedingDTO feedingDTO) {
        return new ResponseEntity<>(feedingService.updateById(id, feedingDTO), HttpStatus.OK);
    }

    @DeleteMapping("/feeding/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        feedingService.deleteById(id);
    }

    @GetMapping("/feeding/a/{localTime}")
    public ResponseEntity<List<HorseDTO>> findHorsesEligibleForFeeding(@PathVariable("localTime") LocalTime localTime) {
        return new ResponseEntity<>(feedingService.checkHorsesEligibleForFeeding(localTime), HttpStatus.OK);
    }

    @PutMapping("/feeding/a/{uuid}")
    public void releaseFoodByUUID(@PathVariable("uuid") UUID uuid) {
        feedingService.releasingFood(uuid);
    }

    @GetMapping("/feeding/a/done/{localTime}/{hours}")
    public ResponseEntity<List<HorseDTO>> findHorsesEligibleForFeedingButNotBeenFed(@PathVariable("localTime") LocalTime localTime, @PathVariable("hours") Integer hours) {
        return new ResponseEntity<>(feedingService.checkHorsesEligibleForFeedingButNotBeenFed(hours, localTime),HttpStatus.OK);
    }

    @GetMapping("/feeding/a/range/{localTime}/{number}")
    public ResponseEntity<List<HorseDTO>> findHorsesByNumberOfMissedFeedingRanges(@PathVariable("localTime") LocalTime localTime, @PathVariable("number") Integer number) {
        return new ResponseEntity<>(feedingService.checkHorsesByNumberOfMissedFeedingRanges(number, localTime),HttpStatus.OK);
    }

    @GetMapping("/feeding/a/ateall/{localTime}")
    public ResponseEntity<List<HorseDTO>> findHorsesWithNotFinishedFeedings(@PathVariable("localTime") LocalTime localTime) {
        return new ResponseEntity<>(feedingService.checkHorsesWithNotFinishedFeedings(localTime), HttpStatus.OK);
    }

    @PutMapping("/feeding/horse/{id}")
    public ResponseEntity<FeedingDTO> addHorseToFeeding(@PathVariable("id") Long feedingId, @RequestBody Long horseId) {
        return new ResponseEntity<>(feedingService.addHorseToFeeding(feedingId, horseId),HttpStatus.OK);
    }

    @PutMapping("/feeding/food/{id}")
    public ResponseEntity<FeedingDTO> addFoodToFeeding(@PathVariable("id") Long feedingId, @RequestBody List<Long> foodIds) {
        return new ResponseEntity<>(feedingService.addFoodToFeeding(feedingId,foodIds),HttpStatus.OK);
    }

}
