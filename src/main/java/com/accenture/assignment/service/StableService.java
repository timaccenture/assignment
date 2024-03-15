package com.accenture.assignment.service;

import com.accenture.assignment.data.dtos.StableDTO;

import java.util.List;

public interface StableService {

    void create(StableDTO dto);
    List<StableDTO> findAll();
    StableDTO getById(Long id);
    void deleteById(Long id);
    StableDTO updateById(Long id, StableDTO dto);
}
