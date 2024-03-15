package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.StableDTO;
import com.accenture.assignment.service.StableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StableController {
    private final StableService stableService;
    @Autowired
    public StableController(StableService stableService) {
        this.stableService = stableService;
    }

    @PostMapping("/stable")
    public void createStable(@RequestBody StableDTO stableDTO) {
        stableService.create(stableDTO);
    }
    @GetMapping("/stable")
    public ResponseEntity<List<StableDTO>> findAll() {
        return new ResponseEntity<>(stableService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/stable/{id}")
    public ResponseEntity<StableDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(stableService.getById(id), HttpStatus.OK);
    }
    @DeleteMapping("/stable/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        stableService.deleteById(id);
    }

    @PutMapping("/stable/{id}")
    public ResponseEntity<StableDTO> updateById(@PathVariable("id") Long id, @RequestBody StableDTO stableDTO) {
        return new ResponseEntity<>(stableService.updateById(id, stableDTO), HttpStatus.OK);
    }
}
