package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.OwnerDTO;
import com.accenture.assignment.data.dtos.mapper.OwnerMapper;
import com.accenture.assignment.data.model.OwnerEntity;
import com.accenture.assignment.data.repository.OwnerRepository;
import com.accenture.assignment.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    public final OwnerRepository ownerRepository;
    public final OwnerMapper ownerMapper;
    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public void create(OwnerDTO ownerDTO) {
        OwnerEntity ownerEntity = ownerMapper.ownerDTOToEntity(ownerDTO);
        ownerRepository.save(ownerEntity);
    }

    public List<OwnerDTO> findAll() {
        List<OwnerEntity> ownerEntities = ownerRepository.findAll();
        return ownerEntities
                .stream()
                .sorted(Comparator.comparing(OwnerEntity::getName))
                .map(ownerMapper::ownerEntityToDTO)
                .toList();
    }

    @Override
    public OwnerDTO getById(Long id) {
        OwnerEntity ownerEntity = ownerRepository.findById(id).orElseThrow(RuntimeException::new);
        return ownerMapper.ownerEntityToDTO(ownerEntity);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public OwnerDTO updateById(Long id, OwnerDTO ownerDTO) {
        OwnerEntity ownerEntity = ownerRepository.findById(ownerDTO.getId()).orElseThrow(RuntimeException::new);
        ownerEntity.setId(id);
        ownerRepository.save(ownerEntity);
        return ownerMapper.ownerEntityToDTO(ownerEntity);
    }

    @Override
    public OwnerEntity getEntityById(Long id) {
        return ownerRepository.findById(id).orElseThrow();
    }
}
