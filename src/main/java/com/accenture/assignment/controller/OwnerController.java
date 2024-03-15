package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.OwnerDTO;
import com.accenture.assignment.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/owner")
    public void createOwner(@RequestBody OwnerDTO ownerDTO) {
        ownerService.create(ownerDTO);
    }
    @GetMapping("/owner")
    public ResponseEntity<List<OwnerDTO>> findAll() {
        return new ResponseEntity<>(ownerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<OwnerDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ownerService.getById(id),HttpStatus.OK);
    }
    @DeleteMapping("/owner/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        ownerService.deleteById(id);
    }

    @PutMapping("/owner/{id}")
    public ResponseEntity<OwnerDTO> updateById(@PathVariable("id") Long id, @RequestBody OwnerDTO ownerDTO) {
        return new ResponseEntity<>(ownerService.updateById(id,ownerDTO), HttpStatus.OK);
    }
}
