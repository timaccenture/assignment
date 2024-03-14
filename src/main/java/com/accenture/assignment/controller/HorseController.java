package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.service.HorseService;
import com.accenture.assignment.service.implementations.HorseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HorseController {

    private final HorseService horseService;

    @Autowired
    public HorseController(HorseService horseService){
        this.horseService = horseService;
    }
    @PostMapping("/horse")
    public void createHorse(@RequestBody HorseDTO dto) {
        horseService.create(dto);
    }
    @GetMapping("/horse")
    public ResponseEntity<List<HorseDTO>> findAll() {
        return new ResponseEntity<>(horseService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/horse/{id}")
    public ResponseEntity<HorseDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(horseService.getById(id), HttpStatus.OK);
    }
    @DeleteMapping("/horse/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        horseService.deleteById(id);
    }
    @PutMapping("/horse/{id}")
    public ResponseEntity<HorseDTO> updateById(@PathVariable("id") Long id, @RequestBody HorseDTO dto) {
        return new ResponseEntity<>(horseService.updateById(id, dto), HttpStatus.OK);
    }
}
