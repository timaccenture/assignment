package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.StableDTO;
import com.accenture.assignment.data.dtos.mapper.StableMapper;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.model.StableEntity;
import com.accenture.assignment.data.repository.HorseRepository;
import com.accenture.assignment.data.repository.StableRepository;
import com.accenture.assignment.service.StableService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class StableServiceImpl implements StableService {
    private final StableRepository stableRepository;
    private final HorseRepository horseRepository;
    private final StableMapper mapper;

    public StableServiceImpl(StableRepository stableRepository, HorseRepository horseRepository, StableMapper stableMapper) {
        this.stableRepository = stableRepository;
        this.horseRepository = horseRepository;
        this.mapper = stableMapper;
    }

    @Override
    public void create(StableDTO dto) {
        StableEntity entity = mapper.stableDTOToEntity(dto);
        stableRepository.save(entity);
    }

    @Override
    public List<StableDTO> findAll() {
        List<StableEntity> stables = stableRepository.findAll();
        return stables
                .stream()
                .sorted(Comparator.comparing(StableEntity::getName))
                .map(mapper::stableEntityToDTO)
                .toList();
    }

    @Override
    public StableDTO getById(Long id) {
        StableEntity entity = stableRepository.findById(id).orElseThrow();
        return mapper.stableEntityToDTO(entity);
    }

    @Override
    public void deleteById(Long id) {
        stableRepository.deleteById(id);
    }

    @Override
    public StableDTO updateById(Long id, StableDTO dto) {
        List<HorseEntity> horseEntityList = new ArrayList<>();
        for (Long horseId: dto.getHorseIds()){
            horseEntityList.add(horseRepository.findById(horseId).orElseThrow(RuntimeException::new));
        }
        StableEntity stableEntity = stableRepository.findById(dto.getId()).orElseThrow(RuntimeException::new);
        stableEntity.setHorses(horseEntityList);
        stableEntity.setId(id);
        return mapper.stableEntityToDTO(stableRepository.save(stableEntity));
    }
}
