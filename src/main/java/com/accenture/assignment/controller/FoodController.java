package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.FoodDTO;
import com.accenture.assignment.service.FoodService;
import com.accenture.assignment.service.implementations.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class FoodController {

    private final FoodService foodService;
    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }
    @PostMapping("/food")
    public void createFood(@RequestBody FoodDTO foodDTO) {
        foodService.create(foodDTO);
    }
    @GetMapping("/food")
    public ResponseEntity<List<FoodDTO>> findAll() {
        return new ResponseEntity<>(foodService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/food/{id}")
    public ResponseEntity<FoodDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(foodService.getById(id), HttpStatus.OK);
    }
    @DeleteMapping("/food/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        foodService.deleteById(id);
    }
    @PutMapping("/food/{id}")
    public ResponseEntity<FoodDTO> updateById(@PathVariable("id") Long id, @RequestBody FoodDTO foodDTO) {
        return new ResponseEntity<>(foodService.updateById(id,foodDTO),HttpStatus.OK);
    }
}
