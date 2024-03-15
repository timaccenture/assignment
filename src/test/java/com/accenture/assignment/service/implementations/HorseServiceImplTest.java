package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.model.OwnerEntity;
import com.accenture.assignment.data.model.StableEntity;
import com.accenture.assignment.data.repository.HorseRepository;
import com.accenture.assignment.data.repository.OwnerRepository;
import com.accenture.assignment.data.repository.StableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HorseServiceImplTest {

    @Mock
    HorseRepository horseRepository;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    StableRepository stableRepository;
    @Mock
    HorseMapper horseMapper;
    @InjectMocks
    HorseServiceImpl horseServiceImpl;

    @Test
    void testCreate() {
        HorseDTO horseDTO = HorseDTO.builder().id(3L).build();
        HorseEntity horseEntity = HorseEntity.builder().id(3L).build();
        OwnerEntity ownerEntity = OwnerEntity.builder().build();
        StableEntity stableEntity = StableEntity.builder().build();

        when(horseRepository.save(horseEntity)).thenReturn(horseEntity);
        when(ownerRepository.findById(horseDTO.getOwnerId())).thenReturn(Optional.of(ownerEntity));
        when(stableRepository.findById(horseDTO.getStableId())).thenReturn(Optional.of(stableEntity));
        when(horseMapper.horseDtoToEntity(horseDTO)).thenReturn(horseEntity);

        horseServiceImpl.create(horseDTO);

        verify(horseRepository).save(horseEntity);
        verify(ownerRepository).findById(horseDTO.getOwnerId());
        verify(stableRepository).findById(horseDTO.getStableId());
        verify(horseMapper).horseDtoToEntity(horseDTO);
    }

    @Test
    void testFindAll() {
        //given
        HorseDTO horseDTO = HorseDTO.builder().id(3L).build();
        HorseEntity horseEntity = HorseEntity.builder().id(3L).build();

        //when
        when(horseRepository.findAll()).thenReturn(List.of(horseEntity));
        when(horseMapper.horseEntityToDto(horseEntity)).thenReturn(horseDTO);
        List<HorseDTO> horses = horseServiceImpl.findAll();

        //then
        assertEquals(horses, List.of(horseDTO));
        verify(horseRepository).findAll();
        verify(horseMapper).horseEntityToDto(horseEntity);
    }

    @Test
    void testGetById() {
        Long horseId = 3L;
        HorseEntity horseEntity = HorseEntity.builder().id(horseId).build();
        HorseDTO horseDTO = HorseDTO.builder().id(horseId).build();

        when(horseRepository.findById(3L)).thenReturn(Optional.of(horseEntity));
        when(horseMapper.horseEntityToDto(horseEntity)).thenReturn(horseDTO);

        HorseDTO returnedDto = horseServiceImpl.getById(horseId);

        assertEquals(horseDTO, returnedDto);
        verify(horseRepository).findById(horseId);
        verify(horseMapper).horseEntityToDto(horseEntity);
    }

    @Test
    void testDeleteById() {
        Long horseId = 3L;
        //when
        horseServiceImpl.deleteById(horseId);
        //then
        verify(horseRepository).deleteById(horseId);
    }

    @Test
    void testUpdateById() {
        //given
        Long horseId = 3L; Long ownerId = 1L; Long stableId = 1L;
        HorseDTO horseDto = HorseDTO.builder().id(horseId).ownerId(ownerId).stableId(stableId).build();
        OwnerEntity ownerEntity = OwnerEntity.builder().id(ownerId).build();
        StableEntity stableEntity = StableEntity.builder().id(stableId).build();
        HorseEntity horseEntity = HorseEntity.builder().id(horseId).owner(ownerEntity).stable(stableEntity).build();
        //when
        when(ownerRepository.findById(horseDto.getOwnerId())).thenReturn(Optional.of(ownerEntity));
        when(stableRepository.findById(horseDto.getStableId())).thenReturn(Optional.of(stableEntity));
        when(horseRepository.findById(horseDto.getId())).thenReturn(Optional.of(horseEntity));
        when(horseRepository.save(horseEntity)).thenReturn(horseEntity);
        when(horseMapper.horseEntityToDto(horseEntity)).thenReturn(horseDto);

        HorseDTO returnedDto = horseServiceImpl.updateById(horseId, horseDto);
        //then
        verify(ownerRepository).findById(horseDto.getOwnerId());
        verify(stableRepository).findById(horseDto.getStableId());
        verify(horseRepository).findById(horseDto.getId());
        verify(horseRepository).save(horseEntity);
        verify(horseMapper).horseEntityToDto(horseEntity);
        assertEquals(horseDto, returnedDto);
        assertEquals(ownerEntity.getId(), returnedDto.getOwnerId());
        assertEquals(stableEntity.getId(), returnedDto.getStableId());
    }


}
