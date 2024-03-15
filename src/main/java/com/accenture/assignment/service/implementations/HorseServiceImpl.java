package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.model.OwnerEntity;
import com.accenture.assignment.data.model.StableEntity;
import com.accenture.assignment.data.repository.HorseRepository;
import com.accenture.assignment.data.repository.OwnerRepository;
import com.accenture.assignment.data.repository.StableRepository;
import com.accenture.assignment.service.HorseService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    private final HorseRepository horseRepository;
    private final OwnerRepository ownerRepository;
    private final StableRepository stableRepository;
    private final HorseMapper mapper;

    public HorseServiceImpl(HorseRepository horseRepository, OwnerRepository ownerRepository, StableRepository stableRepository, HorseMapper horseMapper) {
        this.horseRepository = horseRepository;
        this.ownerRepository = ownerRepository;
        this.stableRepository = stableRepository;
        this.mapper = horseMapper;
    }

    @Override
    public void create(HorseDTO dto) {
        HorseEntity entity = mapper.horseDtoToEntity(dto);
        OwnerEntity ownerEntity = ownerRepository.findById(dto.getOwnerId()).orElseThrow(RuntimeException::new);
        StableEntity stableEntity = stableRepository.findById(dto.getStableId()).orElseThrow(RuntimeException::new);
        entity.setOwner(ownerEntity);
        entity.setStable(stableEntity);
        horseRepository.save(entity);
    }

    @Override
    public List<HorseDTO> findAll() {
        List<HorseEntity> horses = horseRepository.findAll();
        return horses
                .stream()
                .sorted(Comparator.comparing(HorseEntity::getName))
                .map(mapper::horseEntityToDto)
                .toList();
    }

    @Override
    public HorseDTO getById(Long id) {
        HorseEntity entity = horseRepository.findById(id).orElseThrow();
        return mapper.horseEntityToDto(entity);
    }

    @Override
    public void deleteById(Long id) {
        horseRepository.deleteById(id);
    }

    @Override
    public HorseDTO updateById(Long id, HorseDTO dto) {
        OwnerEntity ownerEntity = ownerRepository.findById(dto.getOwnerId()).orElseThrow(RuntimeException::new);
        StableEntity stableEntity = stableRepository.findById(dto.getStableId()).orElseThrow(RuntimeException::new);
        HorseEntity horseEntity = horseRepository.findById(dto.getId()).orElseThrow(RuntimeException::new);
        horseEntity.setOwner(ownerEntity);
        horseEntity.setStable(stableEntity);
        horseEntity.setId(id);
        return mapper.horseEntityToDto(horseRepository.save(horseEntity));
    }
}
