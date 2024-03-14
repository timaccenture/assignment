package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.model.OwnerEntity;
import com.accenture.assignment.data.repository.OwnerRepository;
import com.accenture.assignment.service.OwnerService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {
    public final OwnerRepository ownerRepository;
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    @Override
    public OwnerEntity getEntityById(Long id) {
        return ownerRepository.findById(id).orElseThrow();
    }
}
