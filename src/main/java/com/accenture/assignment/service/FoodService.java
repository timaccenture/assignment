package com.accenture.assignment.service;

import com.accenture.assignment.data.dtos.FoodDTO;

import java.util.List;

public interface FoodService {

    void create(FoodDTO foodDTO);
    List<FoodDTO> findAll();
    FoodDTO getById(Long id);
    void deleteById(Long id);
    FoodDTO updateById(Long id, FoodDTO foodDTO);
}
