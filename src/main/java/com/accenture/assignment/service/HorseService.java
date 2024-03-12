package com.accenture.assignment.service;

import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;

import java.util.List;

public interface HorseService {

    void create(HorseDTO dto);
    List<HorseDTO> findAll();
    HorseDTO getById(Long id);
    HorseEntity getEntityById(Long id);
    void deleteById(Long id);
    HorseDTO updateById(Long id, HorseDTO dto);

}
