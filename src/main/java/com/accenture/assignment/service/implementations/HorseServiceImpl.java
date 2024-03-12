package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.repository.HorseRepository;
import com.accenture.assignment.service.HorseService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    HorseRepository horseRepository;
    public HorseMapper mapper;

    public HorseServiceImpl(HorseRepository horseRepository) {
        this.horseRepository = horseRepository;
        this.mapper = Mappers.getMapper(HorseMapper.class);
    }

    @Override
    public void create(HorseDTO dto) {
        HorseEntity entity = mapper.horseDTOToEntity(dto);
        horseRepository.save(entity);
    }

    @Override
    public List<HorseDTO> findAll() {
        List<HorseEntity> horses = horseRepository.findAll();
        return horses
                .stream()
                .sorted(Comparator.comparing(HorseEntity::getName))
                .map(mapper::horseEntityToDTO)
                .toList();
    }

    @Override
    public HorseDTO getById(Long id) {
        HorseEntity entity = horseRepository.findById(id).orElseThrow();
        return mapper.horseEntityToDTO(entity);
    }

    @Override
    public HorseEntity getEntityById(Long id) {
        return horseRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        horseRepository.deleteById(id);
    }

    @Override
    public HorseDTO updateById(Long id, HorseDTO dto) {
        HorseEntity entity = mapper.horseDTOToEntity(dto);
        entity.setId(id);
        return mapper.horseEntityToDTO(horseRepository.save(entity));
    }
}
