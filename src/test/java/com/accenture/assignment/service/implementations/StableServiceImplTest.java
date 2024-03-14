package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.StableDTO;
import com.accenture.assignment.data.dtos.mapper.StableMapper;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.model.StableEntity;
import com.accenture.assignment.data.repository.HorseRepository;
import com.accenture.assignment.data.repository.StableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StableServiceImplTest {
    @Mock
    StableRepository stableRepository;
    @Mock
    HorseRepository horseRepository;
    @Mock
    StableMapper stableMapper;
    @InjectMocks
    StableServiceImpl stableServiceImpl;

    StableEntity stableEntity;
    StableDTO stableDTO;
    Long stableId;
    Long horseId1;
    Long horseId2;
    HorseEntity horseEntity1;
    HorseEntity horseEntity2;

    @BeforeEach
    void setUp() {
        horseId1 = 1L; horseId2 = 2L;
        horseEntity1 = HorseEntity.builder().id(horseId1).build();
        horseEntity2 = HorseEntity.builder().id(horseId2).build();
        stableDTO = StableDTO.builder().id(1L).name("reitschule").address("berlin").horseIds(List.of(horseId1,horseId2)).build();
        stableEntity = StableEntity.builder().id(1L).name("reitschule").address("berlin").horses(List.of(horseEntity1,horseEntity2)).build();
        stableId = 1L;
    }
    @Test
    void testCreate() {
        when(stableRepository.save(stableEntity)).thenReturn(stableEntity);
        when(stableMapper.stableDTOToEntity(stableDTO)).thenReturn(stableEntity);

        stableServiceImpl.create(stableDTO);

        verify(stableRepository).save(stableEntity);
        verify(stableMapper).stableDTOToEntity(stableDTO);
    }
    @Test
    void testFindAll() {
        when(stableRepository.findAll()).thenReturn(List.of(stableEntity));
        when(stableMapper.stableEntityToDTO(stableEntity)).thenReturn(stableDTO);

        List<StableDTO> stables = stableServiceImpl.findAll();

        assertEquals(List.of(stableDTO),stables);
        verify(stableRepository).findAll();
        verify(stableMapper).stableEntityToDTO(stableEntity);
    }
    @Test
    void testGetById() {
        when(stableRepository.findById(stableId)).thenReturn(Optional.of(stableEntity));
        when(stableMapper.stableEntityToDTO(stableEntity)).thenReturn(stableDTO);

        StableDTO returnedDto = stableServiceImpl.getById(stableId);

        assertEquals(stableDTO, returnedDto);
        verify(stableRepository).findById(stableId);
        verify(stableMapper).stableEntityToDTO(stableEntity);
    }
    @Test
    void testDeleteById() {
        stableServiceImpl.deleteById(stableId);
        verify(stableRepository).deleteById(stableId);

    }
    @Test
    void testUpdateById() {
        when(horseRepository.findById(horseId1)).thenReturn(Optional.of(horseEntity1));
        when(horseRepository.findById(horseId2)).thenReturn(Optional.of(horseEntity2));
        when(stableRepository.findById(stableDTO.getId())).thenReturn(Optional.of(stableEntity));
        when(stableRepository.save(stableEntity)).thenReturn(stableEntity);
        when(stableMapper.stableEntityToDTO(stableEntity)).thenReturn(stableDTO);

        StableDTO returnedDto = stableServiceImpl.updateById(stableId, stableDTO);

        assertEquals(stableDTO, returnedDto);
        assertEquals(List.of(horseId1, horseId2), returnedDto.getHorseIds());
        verify(horseRepository).findById(horseId1);
        verify(horseRepository).findById(horseId2);
        verify(stableRepository).findById(stableDTO.getId());
        verify(stableRepository).save(stableEntity);
        verify(stableMapper).stableEntityToDTO(stableEntity);
    }
}
