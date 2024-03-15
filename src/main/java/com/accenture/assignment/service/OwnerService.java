package com.accenture.assignment.service;

import com.accenture.assignment.data.dtos.OwnerDTO;
import com.accenture.assignment.data.model.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerService {

    void create(OwnerDTO ownerDTO);
    List<OwnerDTO> findAll();
    OwnerDTO getById(Long id);
    void deleteById(Long id);
    OwnerDTO updateById(Long id, OwnerDTO ownerDTO);
    OwnerEntity getEntityById(Long id);
}
